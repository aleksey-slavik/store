var currentOrder;

//start statement of page when it is loaded
findAllOrders();
$('#buttonDelete').hide();
$('#buttonCreate').hide();
$('#buttonSave').hide();

/**
 * Register listener for create button
 */
$('#buttonCreate').click(function () {
    clearOrderForm();
    return false;
});

/**
 * Register listener for save button
 */
$('#buttonSave').click(function () {
    updateOrder();
    return false;
});

/**
 * Register listener for delete button
 */
$('#buttonDelete').click(function () {
    deleteOrder();
    return false;
});

/**
 * Register listener for list of items
 */
$('#itemList').on('click', 'a', function () {
    findOrderById($(this).data('identity'));
});

/**
 * Fill list of orders items using given data
 *
 * @param data given data
 */
function fillOrderItemList(data) {
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    $('#orderTable').find('tr').remove();
    $('#orderTable').append(
        '<tr>' +
        '<th width="40%">Product:</th>' +
        '<th width="20%">Price:</th>' +
        '<th width="20%">Quantity:</th>' +
        '<th width="20%">Cost:</th>' +
        '</tr>');
    $.each(list, function (index, item) {
        var cost = item.price * item.quantity;
        $('#orderTable').append(
            '<tr class="orderTableRow">' +
            '<td align="center">' + item.name + '(' + item.brand + ')</td>' +
            '<td align="center">' + item.price + '</td>' +
            '<td align="center">' + item.quantity + '</td>' +
            '<td align="center">' + cost + '</td>' +
            '</tr>');
    });
}

/**
 * Fill list of orders using given data
 *
 * @param data given data
 */
function fillOrderList(data) {
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    $('#itemList').find('li').remove();
    $.each(list, function (index, item) {
        $('#itemList').append('<li><a href="#" data-identity="' + item.id + '">' + item.customer + '</a></li>');
    });
}

function fillOrder(item) {
    var created = new Date(item.createdDate);
    $('#id').val(item.id);
    $('#userId').val(item.customerId);
    $('#user').val(item.customer);
    $('#created').val(item == null ? '' : created.toLocaleString());
    $('#status').val(item.status);
    $('#totalCost').val(item.totalCost);
}

/**
 * Parse orders data from form to json format
 *
 * @returns {string}
 */
function orderToJSON() {
    return JSON.stringify({
        "id": $('#id').val(),
        "customerId": $('#userId').val(),
        "customer": $('#user').val(),
        "createdDate": currentOrder.createdDate,
        "totalCost": $('#totalCost').val(),
        "status": $('#status').val(),
        "items": currentOrder.items
    });
}

function findAllOrders() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/orders?size=20',
        async: false,
        dataType: "json",

        success: function (data) {
            fillOrderList(data);
        },

        error: function (xhr, textStatus, errorThrown) {
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

function findOrderById(id) {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/orders/' + id,
        async: false,
        dataType: "json",

        success: function (data) {
            currentOrder = data;
            fillOrder(currentOrder);
            fillOrderItemList(currentOrder.items);
            $('#buttonDelete').show();
            $('#buttonCreate').show();
            $('#buttonSave').show();
        },

        error: function (xhr, textStatus, errorThrown) {
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

function updateOrder() {
    var orderId = $('#id').val();

    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: 'http://localhost:8080/api/orders/' + orderId,
        dataType: "json",
        data: orderToJSON(),
        async: false,

        success: function (data) {
            currentOrder = data;
            alert('Order with id=' + data.id + ' updated!');
        },

        error: function (xhr, textStatus, errorThrown) {
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

function deleteOrder() {
    var orderId = $('#id').val();

    $.ajax({
        type: 'DELETE',
        url: 'http://localhost:8080/api/orders/' + orderId,
        async: false,

        success: function (data) {
            clearOrderForm();
            findAllOrders();
            alert('Order with id=' + data.id + ' deleted!');
        },

        error: function (xhr, textStatus, errorThrown) {
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

/**
 * Clear form for insert new data
 */
function clearOrderForm() {
    currentOrder = {};
    fillOrder({});
    fillOrderItemList({});
    $('#buttonDelete').hide();
    $('#buttonCreate').hide();
    $('#buttonSave').hide();
}
