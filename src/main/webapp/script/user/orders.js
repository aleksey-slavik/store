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
        '<th width="10%">Date:</th>' +
        '<th width="70%">Order items:</th>' +
        '<th width="10%">Total cost:</th>' +
        '<th width="10%">Status:</th>' +
        '</tr>');
    $.each(data, function (index, item) {
        var time = new Date(item.createdDate);
        $('#orderTable').append(
            '<tr class="orderTableRow">' +
            '<td align="center">' + time.toLocaleString() + '</td>' +
            '<td>' + fillOrderItemsList(item) + '</td>' +
            '<td align="center">' + item.totalCost + '</td>' +
            '<td align="center">' + item.status + '</td>' +
            '</tr>');
    });
}

function fillOrderItemsList(data) {
    var table = '<table width="100%">';

    $.each(data.items, function (index, item) {
        table += '<tr><td>' + item.name + '(' + item.brand + ') &times; ' + item.quantity + '</td></tr>';
    });

    table += '</table>';
    return table;
}

function findUserOrderByUsername() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/orders?customer=' + principal,
        async: false,
        dataType: "json",

        success: function (data, textStatus, xhr) {
            switch (xhr.status) {
                case 204:
                    alert('Your don\'t have any orders already!');
                    break;
                default:
                    fillUserOrderList(data);
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
