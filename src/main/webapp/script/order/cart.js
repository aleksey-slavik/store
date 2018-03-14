/**
 * Id of orders for current user
 */
var sessionOrderId;

/**
 * Temporary variable for product data
 */
var currentItem;

//start statement of page when it is loaded
findUserOrderByUsername();

$('#orderTable').on('click', 'img', function () {
    //todo remove order item
    console.log('remove item with id=' + $(this).data('identity'));
});

/**
 * Fill list of orders using given data
 *
 * @param data given data
 */
function fillUserOrderList(data) {
    $('#orderTable').find('tr').remove();
    $('#orderTable').append(
        '<tr>' +
        '<th width="75%">Product:</th>' +
        '<th width="10%">Price:</th>' +
        '<th width="10%">Quantity:</th>' +
        '<th width="5%">Remove:</th>' +
        '</tr>');
    $.each(data.items, function (index, item) {
        $('#orderTable').append(
            '<tr class="orderTableRow">' +
            '<td>' + item.name + '(' + item.brand + ')</td>' +
            '<td align="center">' + item.price + '</td>' +
            '<td align="center">' + item.quantity + '</td>' +
            '<td align="center"><img class="icon" src="/images/trash.png" alt="remove" title="remove item" data-identity="' + item.productId + '"></td>' +
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
 * Fill orders item form using given item data
 *
 * @param item given data
 */
function fillUserOrderItem(item) {
    $('#productId').val(item.productId);
    $('#orderId').val(item.orderId);
    $('#name').val(item.name);
    $('#brand').val(item.brand);
    $('#quantity').val(item.quantity);
    $('#price').val(item.price);
}

/**
 * Parse orders item data from form to json format
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
 * Sending DELETE request to rest service for delete orders item by given item id.
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
 * Sending PUT request to rest service for update orders item by given item id.
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

function findUserOrderByUsername() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/orders?owner=' + principal + '&status=OPENED',
        async: false,
        dataType: "json",

        success: function (data, textStatus, xhr) {
            switch (xhr.status) {
                case 204:
                    alert('You don\'t have opened order!');
                    break;
                default:
                    sessionOrderId = data[0].id;
                    fillUserOrderList(data[0]);
                    break;
            }
        },

        error: function (xhr, textStatus, errorThrown) {
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

function findUserOrderItemById(id) {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/orders/' + sessionOrderId + '/items/' + id,
        async: false,
        dataType: "json",

        success: function (data) {
            console.log(data);
            currentItem = data;
            fillUserOrderItem(data);
            showOrderItemModalWindow();
        },

        error: function (xhr, textStatus, errorThrown) {
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}
