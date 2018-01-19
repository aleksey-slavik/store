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
 * Name of column for searchProduct
 *
 * @type {string}
 */
var searchField = "user";

/**
 * Temporary variable for save data about products
 */
var productList = [];

/**
 * Temporary variable for save user data
 */
var user;

/**
 * Fill list of orders using given data
 *
 * @param data given data
 */
function fillList(data) {
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    $('#itemList').find('li').remove();
    $.each(list, function (index, item) {
        $('#itemList').append('<li><a href="#" data-identity="' + item.id + '">' + item.user.username + '</a></li>');
    });
}

/**
 * Fill order form using given item data
 *
 * @param item given data
 */
function fillItem(item) {
    $('#id').val(item.id);
    $('#username').val(item.user.username);
    $('#status').val(item.status.name);
    $('#totalCost').val(item.totalCost);
    fillProductList(item.items);
    user = item.user;
}

/**
 * Parse order data from form to json format
 *
 * @returns {string}
 */
function itemToJSON() {
    var itemId = $('#id').val();
    return JSON.stringify({
        "id": itemId === '' ? null : itemId,
        "user": user,
        "items": productList,
        "status": $('#status').val(),
        "totalCost": $('#totalCost').val()
    });
}

/**
 * Fill list of products using given order data
 *
 * @param data given data
 */
function fillProductList(data) {
    productList = data == null ? [] : (data instanceof Array ? data : [data]);
    $('#productList').find('li').remove();
    $.each(productList, function (index, item) {
        $('#productList').append('<li><a href="#" data-identity="' + index + '">' + item.product.name + '</a></li>');
    });
}

/**
 * Fill product form using given item data
 *
 * @param item given data
 */
function fillProductItem(item) {
    $('#itemId').val(item.id);
    $('#itemName').val(item.product.name);
    $('#itemBrand').val(item.product.brand);
    $('#itemPrice').val(item.price);
    $('#itemQuantity').val(item.quantity);
}

/**
 * Register listener for product list item
 */
$('#productList').on('click', 'a', function () {
    var number = $(this).data('identity');
    $('#buttonSaveItem').show();
    $('#buttonDeleteItem').show();
    fillProductItem(productList[number]);
});