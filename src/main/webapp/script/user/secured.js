/**
 * Root url path for products rest service
 *
 * @type {string}
 */
var rootURL = "http://localhost:8080/users";

/**
 * Temporary variable for product data
 */
var currentItem;

//start statement of page when it is loaded
findAllUsers();
$('#buttonDelete').hide();

/**
 * Register listener for create button
 */
$('#buttonCreate').click(function () {
    clearUserForm();
    return false;
});

/**
 * Register listener for save button
 */
$('#buttonSave').click(function () {
    if ($('#id').val() === '') {
        createUser();
    } else {
        updateUser();
    }

    return false;
});

/**
 * Register listener for delete button
 */
$('#buttonDelete').click(function () {
    deleteUser();
    return false;
});

/**
 * Register listener for list item
 */
$('#itemList').on('click', 'a', function () {
    findUserById($(this).data('identity'));
});

/**
 * Register listener for search button
 */
$('#buttonSearch').click(function () {
    //searchUser($('#searchKey').val());
    return false;
});

/**
 * Trigger search when pressing 'Enter' on search input field
 */
$('#searchKey').keypress(function (e) {
    /*if (e.which === 13) {
        searchUser($('#searchKey').val());
        e.preventDefault();
        return false;
    }*/
});

/**
 * Fill list of users using given data
 *
 * @param data given data
 */
function fillUserList(data) {
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    $('#itemList').find('li').remove();
    $.each(list, function (index, item) {
        $('#itemList').append('<li><a href="#" data-identity="' + item.id + '">' + item.username + '</a></li>');
    });
}

/**
 * Fill user form using given item data
 *
 * @param item given data
 */
function fillUser(item) {
    $('#id').val(item.id);
    $('#firstName').val(item.firstName);
    $('#lastName').val(item.lastName);
    $('#username').val(item.username);
    $('#password').val(item.password);
    $('#email').val(item.email);
    $('#role').val(item.role);
}

/**
 * Parse user data from form to json format
 *
 * @returns {string}
 */
function userItemToJSON() {
    var itemId = $('#id').val();
    return JSON.stringify({
        "id": itemId === '' ? null : itemId,
        "firstName": $('#firstName').val(),
        "lastName": $('#lastName').val(),
        "username": $('#username').val(),
        "password": $('#password').val(),
        "email": $('#email').val(),
        "role": $('#role').val()
    });
}

/**
 * Get list of items by given key.
 * If key is empty return all items
 *
 * @param searchKey searchProduct key
 */
function searchUser(searchKey) {
    clearUserForm();

    if (searchKey === '') {
        findAllUsers();
    } else {
        findUserByKey(searchKey);
    }
}

/**
 * Get list of items by given key.
 * Implementation of {@link findItemByKey} method.
 *
 * @param searchKey search key
 */
function findUserByKey(searchKey) {
    findItemByKey(
        rootURL,
        searchKey,

        function (data) {
            fillUserList(data);
        },

        function () {
            alert('Item with key=' + searchKey + ' was not found!');
            searchUser('');
            $('#searchKey').val('');
        }
    )
}

/**
 * Sending GET request to rest service for get all items.
 * Implementation of {@link getItem} method.
 */
function findAllUsers() {
    getItem(
        rootURL,

        function (data) {
            fillUserList(data)
        },

        function () {
            //do nothing
        }
    )
}

/**
 * Sending GET request to rest service for get item by given id.
 * Implementation of {@link getItem} method.
 *
 * @param id given id
 */
function findUserById(id) {
    getItem(
        rootURL + '/' + id,

        function (data) {
            currentItem = data;
            fillUser(currentItem);
            $('#buttonDelete').show();
        },

        function () {
            //do nothing
        }
    )
}

/**
 * Sending POST request to rest service for create item.
 * Implementation of {@link createItem} method.
 */
function createUser() {
    createItem(
        rootURL,
        userItemToJSON(),

        function (data) {
            findAllUsers();
            $('#buttonDelete').show();
            $('#id').val(data.id);
        }
    )
}

/**
 * Sending PUT request to rest service for update item.
 * Implementation of {@link updateItem} method.
 */
function updateUser() {
    updateItem(
        rootURL,
        $('#id').val(),
        userItemToJSON(),

        function () {
            alert('Item with id=' + $('#id').val() + ' successfully updated!');
        }
    )
}

/**
 * Sending DELETE request to rest service for delete item.
 * Implementation of {@link deleteItem} method.
 */
function deleteUser() {
    deleteItem(
        rootURL,
        $('#id').val(),

        function () {
            findAllUsers();
            clearUserForm();
        }
    )
}

/**
 * Clear form for insert new data
 */
function clearUserForm() {
    $('#buttonDelete').hide();
    currentItem = {};
    fillUser(currentItem);
}