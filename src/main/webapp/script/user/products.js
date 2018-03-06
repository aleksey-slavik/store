/**
 * Root url path for products rest service
 *
 * @type {string}
 */
var rootURL = "http://localhost:8080/api/users";

/**
 * Temporary variable for product data
 */
var currentItem;

//start statement of page when it is loaded
findAllUserProducts();

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
        createUserProduct();
    } else {
        updateUserProduct();
    }

    return false;
});

/**
 * Register listener for delete button
 */
$('#buttonDelete').click(function () {
    deleteUserProduct();
    return false;
});

$('#buttonShare').click(function () {
    shareProduct();
    return false;
});

$('#buttonRemove').click(function () {
    return false;
});

/**
 * Register listener for list item
 */
$('#itemList').on('click', 'a', function () {
    findUserProductById($(this).data('identity'));
});

/**
 * Fill list of users using given data
 *
 * @param data given data
 */
function fillUserProductList(data) {
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    $('#itemList').find('li').remove();
    $.each(list, function (index, item) {
        $('#itemList').append('<li><a href="#" data-identity="' + item.productId + '">' + item.name + '</a></li>');
    });
}

/**
 * Fill user form using given item data
 *
 * @param item given data
 */
function fillUserProduct(item) {
    $('#id').val(item.productId);
    $('#name').val(item.name);
    $('#brand').val(item.brand);
    $('#description').val(item.description);
    $('#price').val(item.price);
    $('#ownerId').val(item.ownerId);
    $('#owner').val(item.owner);
}

/**
 * Parse user data from form to json format
 *
 * @returns {string}
 */
function userProductItemToJSON() {
    var itemId = $('#id').val();
    return JSON.stringify({
        "productId": itemId === '' ? null : itemId,
        "name": $('#name').val(),
        "brand": $('#brand').val(),
        "description": $('#description').val(),
        "price": $('#price').val(),
        "ownerId": $('#ownerId').val(),
        "owner": $('#owner').val()
    });
}

/**
 * Sending GET request to rest service for get all items.
 * Implementation of {@link getItem} method.
 */
function findAllUserProducts() {
    getItem(
        "http://localhost:8080/api/products?owner=" + principal,

        function (data) {
            fillUserProductList(data)
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
function findUserProductById(id) {
    getItem(
        'http://localhost:8080/api/products/' + id,

        function (data) {
            currentItem = data;
            fillUserProduct(currentItem);
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
function createUserProduct() {
    createItem(
        'http://localhost:8080/api/products/',
        userProductItemToJSON(),

        function (data) {
            findAllUserProducts();
            $('#buttonDelete').show();
            $('#id').val(data.productId);
        }
    )
}

/**
 * Sending PUT request to rest service for update item.
 * Implementation of {@link updateItem} method.
 */
function updateUserProduct() {
    updateItem(
        'http://localhost:8080/api/products',
        $('#id').val(),
        userProductItemToJSON(),

        function () {
            alert('Item with id=' + $('#id').val() + ' successfully updated!');
        }
    )
}

/**
 * Sending DELETE request to rest service for delete item.
 * Implementation of {@link deleteItem} method.
 */
function deleteUserProduct() {
    deleteItem(
        rootURL,
        $('#id').val(),

        function () {
            findAllUserProducts();
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
    fillUserProduct(currentItem);
}

function shareProduct() {
    var itemId = $('#id').val();
    var targetUser = $('#share').val();
    console.log('http://localhost:8080/api/products/' + itemId + '/permissions/' + targetUser);
    console.log(userProductItemToJSON());

    createItem(
        'http://localhost:8080/api/products/' + itemId + '/permissions/' + targetUser,
        userProductItemToJSON(),
        function () {
            alert('Permissions for update item with id=' + itemId + ' were successfully issued to user ' + targetUser);
        }
    )
}