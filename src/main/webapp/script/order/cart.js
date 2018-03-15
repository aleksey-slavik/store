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
    deleteOrderItem($(this).data('identity'));
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

function deleteOrderItem(id) {
    $.ajax({
        type: 'DELETE',
        url: 'http://localhost:8080/api/orders/' + sessionOrderId + '/items/' + id,
        async: false,

        success: function () {
            findUserOrderByUsername();
        },

        error: function (xhr, textStatus, errorThrown) {
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

function findUserOrderByUsername() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/orders?customer=' + principal + '&status=OPENED',
        async: false,
        dataType: "json",

        success: function (data, textStatus, xhr) {
            switch (xhr.status) {
                case 204:
                    alert('Your cart is clear!');
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
