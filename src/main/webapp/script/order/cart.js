/**
 * Root url path for orders rest service
 *
 * @type {string}
 */
var rootURL = "http://localhost:8080/api/orders";

/**
 * Id of order for current user
 */
var sessionOrderId;

/**
 * Temporary variable for product data
 */
var currentItem;

//start statement of page when it is loaded
findUserOrderByUsername();

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
    $('#orderTable').find('tr').remove();
    $('#orderTable').append(
        '<tr>' +
        '<th width="80%">Product:</th>' +
        '<th width="10%">Quantity:</th>' +
        '<th width="10%">Price:</th>' +
        '</tr>');
    $.each(data.items, function (index, item) {
        $('#orderTable').append(
            '<tr class="orderTableRow">' +
            '<td><a href="#" data-identity="' + item.id + '">' + item.product.name + '(' + item.product.brand + ')</a></td>' +
            '<td align="center"><a href="#" data-identity="' + item.id + '">' + item.quantity + '</a></td>' +
            '<td align="center"><a href="#" data-identity="' + item.id + '">' + item.price + '</a></td>' +
            '</tr>');
    });

    if (data.items.length === 0) {
        alert("Your cart is empty now!");
        $('#buttonBuy').hide();
    }

    $('#buttonBuy').show();
    $('#totalCost').val(data.totalCost);
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
        "id": currentItem.id,
        "product": currentItem.product,
        "price": currentItem.price,
        "quantity": $('#quantity').val()
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
 * Sending DELETE request to rest service for delete order item by given item id.
 * Implementation of {@link deleteItem} method.
 *
 * @param id given item id
 */
function deleteOrderItem(id) {
    deleteItem(
        rootURL + '/' + sessionOrderId + '/items/',
        id,
        function () {
            findUserOrderByUsername();
        }
    )
}

/**
 * Sending PUT request to rest service for update order item by given item id.
 * Implementation of {@link updateItem} method.
 *
 * @param id given item id
 */
function updateOrderItem(id) {
    updateItem(
        rootURL + '/' + sessionOrderId + '/items/',
        id,
        userOrderItemToJSON(),
        function () {
            findUserOrderByUsername();
        }
    )
}

/**
 * Sending GET request to rest service for get opened order for current user if it exist.
 * Implementation of {@link getItem} method.
 */
function findUserOrderByUsername() {
    getItem(
        rootURL + '/customers/' + principal,
        function (data) {
            sessionOrderId = data.id;
            fillUserOrderList(data);
        },
        function () {
            alert('Can not create cart for user: ' + principal);
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
        rootURL + '/' + sessionOrderId + '/items/' + id,
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

/**
 * Show semi-transparent DIV, which shading whole page
 */
function showCover() {
    var coverDiv = document.createElement('div');
    coverDiv.id = 'cover-div';
    document.body.appendChild(coverDiv);
}

/**
 * Remove semi-transparent DIV, which shading whole page
 */
function hideCover() {
    document.body.removeChild(document.getElementById('cover-div'));
}

/**
 * Show modal window with item info
 */
function showOrderItemModalWindow() {
    showCover();
    var container = document.getElementById('modal-form-container');

    function closeWindow() {
        hideCover();
        container.style.display = 'none';
        document.onkeydown = null;
    }

    $('#buttonCancel').click(function () {
        clearUserOrderItemForm();
        closeWindow();
        return false;
    });

    $('#buttonChange').click(function () {
        updateOrderItem(currentItem.id);
        closeWindow();
        return false;
    });

    $('#buttonDelete').click(function () {
        deleteOrderItem(currentItem.id);
        closeWindow();
        return false;
    });

    container.style.display = 'block';
}
