/**
 * Consist methods for correct work of {@link tableCore} methods for order rest service.
 *
 * @author oleksii.slavik
 */

/**
 * Root url path for orders rest service
 *
 * @type {string}
 */
var rootURL = "http://localhost:8080/orders";

/**
 * Name of column for search
 *
 * @type {string}
 */
var searchField = "user";

/**
 * Fill list of orders using given data
 *
 * @param data given data
 */
function fillList(data) {
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    $('#itemList').find('li').remove();
    $.each(list, function (index, item) {
        $('#itemList').append('<li><a href="#" data-identity="' + item.id + '">' + item.user + '</a></li>');
    });
}

/**
 * Fill order form using given item data
 *
 * @param item given data
 */
function fillItem(item) {
    $('#id').val(item.id);
    $('#user').val(item.user);
    $('#items').val(item.items);
    $('#status').val(item.status);
    $('#totalCost').val(item.totalCost);
}

/**
 * Parse product data from form to json format
 *
 * @returns {string}
 */

function itemToJSON() {
    var itemId = $('#id').val();
    return JSON.stringify({
        "id": itemId === '' ? null : itemId,
        "user": $('#user').val(),
        "items": $('#items').val(),
        "status": $('#status').val(),
        "totalCost": $('#totalCost').val()
    });
}