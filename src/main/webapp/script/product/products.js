var currentItem;
var currentOrderId;

//start statement of page when it is loaded
findAllProducts();

$('#wrapper').on('click', 'a', function () {
    findProductById($(this).data('identity'));
});

function fillProductInfoList(data) {
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    $('#wrapper').find('div').remove();
    $.each(list, function (index, item) {
        $('#wrapper').append(
            '<div class="box">' +
            '<a href="#" data-identity="' + item.id + '">' +
            '<p>' + item.name + '</p>' +
            '<p>' + item.brand + '</p>' +
            '<p>' + item.price + '</p>' +
            '</a>' +
            '</div>');
    });
}

function fillProduct(item) {
    $('#productId').val(item.id);
    $('#name').val(item.name);
    $('#brand').val(item.brand);
    $('#description').val(item.description);
    $('#price').val(item.price);
}

function orderItemToJSON() {
    return JSON.stringify({
        "orderId": currentOrderId,
        "productId": $('#productId').val(),
        "name": currentItem.name,
        "brand": currentItem.brand,
        "price": currentItem.price,
        "quantity": $('#quantity').val()
    });
}

function clearProductForm() {
    currentItem = {};
    fillProduct(currentItem);
}

function findAllProducts() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/products?size=20',
        async: false,
        dataType: "json",

        success: function (data) {
            fillProductInfoList(data);
        },

        error: function (xhr, textStatus, errorThrown) {
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    })
}

function findProductById(id) {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/products/' + id,
        async: false,
        dataType: "json",

        success: function (data) {
            currentItem = data;
            fillProduct(currentItem);
            showModalWindow();
        },

        error: function (xhr, textStatus, errorThrown) {
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    })
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
function showModalWindow() {
    showCover();
    var container = document.getElementById('modal-form-container');

    function closeWindow() {
        clearProductForm();
        hideCover();
        container.style.display = 'none';
        document.onkeydown = null;
    }

    $('#buttonCancel').click(function () {
        closeWindow();
        return false;
    });

    $('#buttonBuy').click(function () {
        addOrderItem();
        closeWindow();
        return false;
    });

    container.style.display = 'block';
}

/**
 * Check opened orders of principal.
 */
function addOrderItem() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/orders?customer=' + principal + '&status=OPENED',
        async: false,
        dataType: "json",

        success: function (data, textStatus, xhr) {
            if (xhr.status == 204) {
                currentOrderId = createNewOrder();
            } else {
                currentOrderId = data.id;
            }

            appendOrderItem(currentOrderId);
        },

        error: function (xhr, textStatus, errorThrown) {
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

/**
 * Add orders item to opened order
 */
function appendOrderItem(id) {
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: 'http://localhost:8080/api/orders/' + id + '/items',
        dataType: "json",
        data: orderItemToJSON(),
        async: false,

        success: function (data) {
            alert(data.product.name + ' successfully added to cart!');
        },

        error: function (xhr, textStatus, errorThrown) {
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}