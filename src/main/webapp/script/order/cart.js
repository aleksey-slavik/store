/**
 * Root url path for orders rest service
 *
 * @type {string}
 */
var rootURL = "http://localhost:8080/orders";

/**
 * Id of order for current user
 */
var sessionOrderId = $.session.get("orderId");

/**
 * Temporary variable for product data
 */
var currentItem;

//start statement of page when it is loaded
findUserOrderByUsername();
$('#buttonBuy').hide();

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
    $('#orderTable').append(
        '<tr>' +
        '<th width="80%">Product:</th>' +
        '<th width="10%">Quantity:</th>' +
        '<th width="10%">Price:</th>' +
        '</tr>');
    $.each(list[0].items, function (index, item) {
        $('#orderTable').append(
            '<tr class="orderTableRow">' +
            '<td><a href="#" data-identity="' + item.id + '">' + item.product.name + '(' + item.product.brand + ')</a></td>' +
            '<td align="center"><a href="#" data-identity="' + item.id + '">' + item.quantity + '</a></td>' +
            '<td align="center"><a href="#" data-identity="' + item.id + '">' + item.price + '</a></td>' +
            '</tr>');
    });

    if (list[0].items.length === 0) {
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
            findUserOrderBySessionId();
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
            findUserOrderBySessionId();
        }
    )
}

/**
 * Sending GET request to rest service for get order by saved in session id of order.
 * Implementation of {@link getItem} method.
 */
function findUserOrderBySessionId() {
    if (sessionOrderId === undefined) {
        findUserOrderByUsername();
    } else {
        getItem(
            rootURL + '/' + sessionOrderId,

            function (data) {
                fillUserOrderList(data);
            },

            function () {
                alert("Order with id=" + sessionOrderId + " was not found!");
            }
        )
    }
}

/**
 * Sending GET request to rest service for get opened order for current user if it exist.
 * Implementation of {@link getItem} method.
 */
function findUserOrderByUsername() {
    getItem(
        rootURL + '/customers/' + principal,

        function (data) {
            $.session.set("orderId", data.id);
            sessionOrderId = data.id;
            fillUserOrderList(data);
        },

        function () {
            createNewUserOrder();
            findUserOrderBySessionId();
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
 * Sending POST request to rest service for create new order for current user.
 * Implementation of {@link createItem} method.
 */
function createNewUserOrder() {
    createItem(
        rootURL + '/customers/' + principal,
        {},

        function (data) {
            $.session.set("orderId", data.id);
            sessionOrderId = data.id;
            fillUserOrderList(data);
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
