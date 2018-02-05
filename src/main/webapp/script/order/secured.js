/**
 * Root url path for orders rest service
 *
 * @type {string}
 */
var rootURL = "http://localhost:8080/orders";

/**
 * Temporary variable for order data
 */
var currentOrder;

/**
 * Temporary variable for order item data
 */
var currentOrderItem;

//start statement of page when it is loaded
findAllOrders();
$('#buttonDelete').hide();

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
 * Register listener for list of order items
 */
$('#orderItemList').on('click', 'a', function () {
    findOrderItemById($(this).data('identity'));
});

/**
 * Fill list of order items using given data
 *
 * @param data given data
 */
function fillOrderItemList(data) {
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    $('#orderItemList').find('li').remove();
    $.each(list, function (index, item) {
        $('#orderItemList').append('<li><a href="#" data-identity="' + item.id + '">' + item.product.name + '</a></li>');
    });
}

/**
 * Fill list of order using given data
 *
 * @param data given data
 */
function fillOrderList(data) {
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    $('#itemList').find('li').remove();
    $.each(list, function (index, item) {
        $('#itemList').append('<li><a href="#" data-identity="' + item.id + '">' + item.user.username + '</a></li>');
    });
}

/**
 * Fill order item form using given item data
 *
 * @param item given data
 */
function fillOrder(item) {
    $('#id').val(item.id);
    $('#user').val(item.user.username);
    $('#status').val(item.status);
    $('#totalCost').val(item.totalCost);
}

function fillOrderItem(item) {
    $('#name').val(item.product.name);
    $('#brand').val(item.product.brand);
    $('#price').val(item.price);
    $('#quantity').val(item.quantity);
}

/**
 * Parse order item data from form to json format
 *
 * @returns {string}
 */
function orderToJSON() {
    return JSON.stringify({
        "id": currentOrder.id,
        "user": currentOrder.user,
        "totalCost":  $('#totalCost').val(),
        "status": $('#status').val(),
        "items": currentOrder.items
    });
}

/**
 * Sending GET request to rest service for get all items.
 * Implementation of {@link getItem} method.
 */
function findAllOrders() {
    getItem(
        rootURL,
        function (data) {
            fillOrderList(data)
        },
        function () {
            //do nothing
        }
    )
}

/**
 * Sending GET request to rest service for get order by given id.
 * Implementation of {@link getItem} method.
 *
 * @param id given order id
 */
function findOrderById(id) {
    getItem(
        rootURL + '/' + id,
        function (data) {
            currentOrder = data;
            fillOrder(currentOrder);
            fillOrderItemList(currentOrder.items);
            $('#buttonDelete').show();
        },
        function () {
            alert('Order with id=' + id + ' not found!');
        }
    )
}

/**
 * Sending GET request to rest service for get order item by given id.
 * Implementation of {@link getItem} method.
 *
 * @param id given order item id
 */
function findOrderItemById(id) {
    getItem(
        rootURL + '/' + currentOrder.id + '/items/' + id,
        function (data) {
            currentOrderItem = data;
            fillOrderItem(currentOrderItem);
            showOrderItemModalWindow();
        },
        function () {
            alert('Order with id=' + id + ' not found!');
        }
    )
}

/**
 * Sending PUT request to rest service for update current order.
 * Implementation of {@link updateItem} method.
 */
function updateOrder() {
    updateItem(
        rootURL,
        currentOrder.id,
        orderToJSON(),
        function (data) {
            currentOrder = data;
            alert('Order with id=' + currentOrder.id + ' updated!');
        }
    )
}

/**
 * Sending PUT request to rest service for delete current order.
 * Implementation of {@link deleteItem} method.
 */
function deleteOrder() {
    deleteItem(
        rootURL,
        currentOrder.id,
        function (data) {
            clearOrderForm();
            findAllOrders();
            alert('Order with id=' + data.id + ' deleted!');
        }
    )
}

/**
* Clear form for insert new data
*/
function clearOrderForm() {
    currentOrder = {};
    fillOrder(currentOrder);
}

/**
 * Clear order item form
 */
function clearOrderItemForm() {
    currentOrderItem = {};
    fillOrderItem(currentOrderItem);
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
        closeWindow();
        return false;
    });

    $('#buttonSaveItem').click(function () {
        //todo update
        findOrderById(currentOrder.id);
        closeWindow();
        return false;
    });

    $('#buttonDeleteItem').click(function () {
        //todo delete
        findOrderById(currentOrder.id);
        closeWindow();
        return false;
    });

    container.style.display = 'block';
}

/*
/!**
 * Parse order item data from form to json format
 *
 * @returns {string}
 *!/
function orderToJSON() {
    return JSON.stringify({
        "id": currentOrder.id,
        "user": currentOrder.user,
        "status": $('#status').val(),
        "items": currentOrder.items
    });
}

function clearOrderItemForm() {
    currentItem = {};
    fillOrder(currentItem);
}

/!**
 * Sending DELETE request to rest service for delete order item by given item id.
 * Implementation of {@link deleteItem} method.
 *
 * @param id given item id
 *!/
function deleteOrderItem(id) {
    deleteItem(
        rootURL + '/' + currentOrder.id + '/items/',
        id,
        function () {
            findOrderById(currentOrder.id);
        }
    )
}

/!**
 * Sending PUT request to rest service for update order item by given item id.
 * Implementation of {@link updateItem} method.
 *
 * @param id given item id
 *!/
function updateOrderItem(id) {
    updateItem(
        rootURL + '/' + currentOrder.id + '/items/',
        id,
        orderItemToJSON(),
        function () {
            findOrderById(currentOrder.id);
        }
    )
}

/!**
 * Sending GET request to rest service for get order by saved in session id of order.
 * Implementation of {@link getItem} method.
 *!/
function findOrderById() {
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

/!**
 * Sending GET request to rest service for get opened order for current user if it exist.
 * Implementation of {@link getItem} method.
 *!/
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

/!**
 * Sending GET request to rest service for get item by given id.
 * Implementation of {@link getItem} method.
 *
 * @param id given id
 *!/
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

/!**
 * Sending POST request to rest service for create new order for current user.
 * Implementation of {@link createItem} method.
 *!/
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
}*/
