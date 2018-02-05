/**
 * Root url path for products rest service
 *
 * @type {string}
 */
var rootURL = "http://localhost:8080/products";

/**
 * Root url path for orders rest service
 *
 * @type {string}
 */
var orderURL = "http://localhost:8080/orders";

/**
 * Temporary variable for product data
 */
var currentItem;

/**
 * Id of order for current user
 */
var sessionOrderId = 0;

//start statement of page when it is loaded
findAllProducts();

/**
 * Register listener for list item
 */
$('#wrapper').on('click', 'a', function () {
    findProductById($(this).data('identity'));
});

/**
 * Fill list of product using given data
 *
 * @param data given data
 */
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

/**
 * Fill product form using given item data
 *
 * @param item given data
 */
function fillProduct(item) {
    $('#id').val(item.id);
    $('#name').val(item.name);
    $('#brand').val(item.brand);
    $('#description').val(item.description);
    $('#price').val(item.price);
}

/**
 * Parse order item data from form to json format
 *
 * @returns {string}
 */
function orderItemToJSON() {
    return JSON.stringify({
        "id": $('#id').val(),
        "product": currentItem,
        "price": currentItem.price,
        "quantity": $('#quantity').val()
    });
}

/**
 * Clear form for insert new data
 */
function clearProductForm() {
    currentItem = {};
    fillProduct(currentItem);
}

/**
 * Sending GET request to rest service for get all items.
 * Implementation of {@link getItem} method.
 */
function findAllProducts() {
    getItem(
        rootURL,
        function (data) {
            fillProductInfoList(data);
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
function findProductById(id) {
    getItem(
        rootURL + '/' + id,
        function (data) {
            currentItem = data;
            fillProduct(currentItem);
            showModalWindow();
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
 * Check opened order of given user.
 */
function checkUserOrderByUsername() {
    getItem(
        orderURL + '/customers/' + principal,
        function (data) {
            sessionOrderId = data.id;
        },
        function () {
            alert('Can not create cart for user: ' + principal);
        }
    );
}

/**
 * Add order item to opened order
 */
function addOrderItem() {
    checkUserOrderByUsername();

    createItem(
        orderURL + '/' + sessionOrderId + '/items',
        orderItemToJSON(),
        function (data) {
            alert(data.product.name + ' successfully added to cart!');
        }
    )
}