/**
 * Consist method for create, update, delete and search operations with rest service.
 * For correct work needed
 * variables:
 *   rootURL - url path of rest service (for example, http://localhost:8080/users);
 *   searchField - name of column for search;
 * functions:
 *   fillList(data) - fill list of <li><a href="#" data-identity="' + item.id + '">' + item.searchField + '</a></li> using given data;
 *   fillItem(item) - fill form data using given item;
 *   itemToJSON() - parse form data to json.
 *
 * @author oleksii.slavik
 */

/**
 * Temporary variable for item
 */
var currentItem;

//start statement of page when it is loaded
findAllItems();
$('#buttonDelete').hide();

/**
 * Register listener for create button
 */
$('#buttonCreate').click(function () {
    clearForm();
    return false;
});

/**
 * Register listener for searchProduct button
 */
$('#buttonSearch').click(function () {
    search($('#searchKey').val());
    return false;
});

/**
 * Trigger searchProduct when pressing 'Enter' on searchProduct input field
 */
$('#searchKey').keypress(function (e) {
    if (e.which === 13) {
        search($('#searchKey').val());
        e.preventDefault();
        return false;
    }
});

/**
 * Register listener for save button
 */
$('#buttonSave').click(function () {
    if ($('#id').val() === '') {
        createItem();
    } else {
        updateItem();
    }

    return false;
});

/**
 * Register listener for delete button
 */
$('#buttonDelete').click(function () {
    deleteItem();
    return false;
});

/**
 * Register listener for list item
 */
$('#itemList').on('click', 'a', function () {
    findItemById($(this).data('identity'));
});

/**
 * Get list of items by given key.
 * If key is empty return all items
 *
 * @param searchKey searchProduct key
 */
function search(searchKey) {
    clearForm();

    if (searchKey === '') {
        findAllItems();
    } else {
        findItemByKey(searchKey);
    }
}

/**
 * Get list of items by given key
 *
 * @param searchKey searchProduct key
 */
function findItemByKey(searchKey) {
    $.ajax({
        type: 'GET',
        url: rootURL + '?' + searchField + '=' + searchKey,
        dataType: "json",
        success: function (data) {
            if (data.length === 0) {
                alert('Item with key "' + searchKey + '" not found!');
                search('');
                $('#searchKey').val('');
            } else {
                fillProductList(data);
            }
        }
    });
}

/**
 * Sending GET request to rest service for get all items.
 */
function findAllItems() {
    $.ajax({
        type: 'GET',
        url: rootURL + '?all',
        dataType: "json",
        success: fillProductList
    });
}

/**
 * Sending GET request to rest service for get item by given id.
 *
 * @param id given id
 */
function findItemById(id) {
    $.ajax({
        type: 'GET',
        url: rootURL + '/' + id,
        dataType: "json",
        success: function (data) {
            $('#buttonDelete').show();
            currentItem = data;
            fillProduct(currentItem);
        }
    })
}

/**
 * Clear form for insert new data
 */
function clearForm() {
    $('#buttonDelete').hide();
    currentItem = {};
    fillProduct(currentItem);
}

/**
 * Sending POST request to rest service for create item, which parsed from page form using {@link productItemToJSON()} method
 */
function createItem() {
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: rootURL,
        dataType: "json",
        data: productItemToJSON(),
        success: function (data, status, param) {
            findAllItems();
            alert('Item successfully created!');
            $('#buttonDelete').show();
            $('#id').val(data.id);
        },
        error: function (data, status, error) {
            alert('Error during create item!');
        }
    });
}

/**
 * Sending PUT request to rest service for update item, which parsed from page form using {@link productItemToJSON()} method
 */
function updateItem() {
    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: rootURL + '/' + $('#id').val(),
        dataType: "json",
        data: productItemToJSON(),
        success: function (data, status, param) {
            alert('Item successfully updated!');
        },
        error: function (data, status, error) {
            alert('Error during update item!');
        }
    })
}

/**
 * Sending DELETE request to rest service for delete item.
 */
function deleteItem() {
    $.ajax({
        type: 'DELETE',
        url: rootURL + '/' + $('#id').val(),
        success: function (data, status, param) {
            findAllItems();
            clearForm();
            alert('Item successfully deleted!');
        },
        error: function (data, status, error) {
            alert('Error during delete item!');
        }
    })
}