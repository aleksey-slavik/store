/**
 * Consist methods for correct work of {@link tableCore} methods for product rest service.
 *
 * @author oleksii.slavik
 */

/**
 * Root url path for products rest service
 *
 * @type {string}
 */
var rootURL = "http://localhost:8080/products";

/**
 * Name of column for search
 *
 * @type {string}
 */
var searchField = "name";

/**
 * Fill list of product using given data
 *
 * @param data given data
 */
function fillList(data) {
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    $('#itemList').find('li').remove();
    $.each(list, function (index, item) {
        $('#itemList').append('<li><a href="#" data-identity="' + item.id + '">' + item.name + '</a></li>');
    });
}

/**
 * Fill product form using given item data
 *
 * @param item given data
 */
function fillItem(item) {
    $('#id').val(item.id);
    $('#name').val(item.name);
    $('#brand').val(item.brand);
    $('#description').val(item.description);
    $('#price').val(item.price);
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
        "name": $('#name').val(),
        "brand": $('#brand').val(),
        "description": $('#description').val(),
        "price": $('#price').val()
    });
}