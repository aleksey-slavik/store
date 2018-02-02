/**
 * Root url path for orders rest service
 *
 * @type {string}
 */
var rootURL = "http://localhost:8080/orders";

/**
 * Root url path for order items rest service
 *
 * @type {string}
 */
var orderItemRootURL = "http://localhost:8080/orderItems";

/**
 * Temporary variable for product data
 */
var currentItem;

//start statement of page when it is loaded
findUserOrders();

/**
 * Register listener for list item
 */
$('#orderTable').on('click', 'a', function () {
    findUserOrderItemById($(this).data('identity'));
});

/**
 * Fill list of order using given data
 *
 * @param data given data
 */
function fillUserOrderList(data) {
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    $('#orderTable').find('tr').remove();
    $.each(list, function (index, item) {
        $('#orderTable').append(
            '<tr>' +
            '<td><a href="#" data-identity="\' + item.id + \'">' + item.product.name + '(' + item.product.brand + ')</a></td>' +
            '<td><a href="#" data-identity="\' + item.id + \'">' + item.quantity + '</a></td>' +
            '<td><a href="#" data-identity="\' + item.id + \'">' + item.price + '</a></td>' +
            '</tr>');
    });
}

/**
 * Fill order item form using given item data
 *
 * @param item given data
 */
function fillUserOrderItem(item) {
    $('#id').val(item.id);
    $('#productId').val(item.product.id);
    $('#productName').val(item.product.name);
    $('#productBrand').val(item.product.brand);
    $('#quantity').val(item.quantity);
    $('#price').val(item.price);
}

/**
 * Parse order item data from form to json format
 *
 * @returns {string}
 */
function userOrderItemToJSON() {
    return JSON.stringify({
        "id": $('#id').val(),
        "product.id": $('#productId').val(),
        "price": $('#price').val(),
        "quantity": $('quantity').val()
    });
}

/**
 * Clear form for insert new data
 */
function clearUserOrderItemForm() {
    currentItem = {};
    fillUserOrderItem(currentItem);
}

/**
 * Sending GET request to rest service for get all items.
 * Implementation of {@link getItem} method.
 */
function findUserOrders() {
    getItem(
        rootURL + '/customer/' ,//todo search by username
        function (data) {
            fillUserOrderList(data);
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
function findUserOrderItemById(id) {
    getItem(
        orderItemRootURL + '/' + id,
        function (data) {
            currentItem = data;
            fillUserOrderItem(currentItem);
            showOrderItemModalWindow();
        },
        function () {
            //do nothing
        }
    )
}
/*
/!**
 * Show semi-transparent DIV, which shading whole page
 *!/
function showCover() {
    var coverDiv = document.createElement('div');
    coverDiv.id = 'cover-div';
    document.body.appendChild(coverDiv);
}

/!**
 * Remove semi-transparent DIV, which shading whole page
 *!/
function hideCover() {
    document.body.removeChild(document.getElementById('cover-div'));
}

/!**
 * Show modal window with item info
 *!/
function showOrderItemModalWindow() {
    showCover();
    var container = document.getElementById('modal-form-container');

    function closeWindow() {
        hideCover();
        container.style.display = 'none';
        document.onkeydown = null;
    }

    $('#buttonCancel').click(function () {
        closeWindow();
        return false;
    });

    $('#buttonBuy').click(function () {
        return false;
    });

    container.style.display = 'block';
}*/
