/**
 * Root url path for products rest service
 *
 * @type {string}
 */
var rootURL = "http://localhost:8080/products";

/**
 * Temporary variable for product data
 */
var currentItem;

//start statement of page when it is loaded
findAllProducts();

/**
 * Register listener for searchProduct button
 */
$('#buttonSearch').click(function () {
    searchProduct($('#searchKey').val());
    return false;
});

/**
 * Trigger searchProduct when pressing 'Enter' on searchProduct input field
 */
$('#searchKey').keypress(function (e) {
    if (e.which === 13) {
        searchProduct($('#searchKey').val());
        e.preventDefault();
        return false;
    }
});

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
    var itemId = $('#id').val();
    return JSON.stringify({
        "id": itemId === '' ? null : itemId,
        "price": $('#price').val(),
        "quantity": $('quantity').val()
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
 * Get list of items by given key.
 * If key is empty return all items
 *
 * @param searchKey searchProduct key
 */
function searchProduct(searchKey) {
    clearProductForm();

    if (searchKey === '') {
        findAllProducts();
    } else {
        findProductByKey(searchKey);
    }
}


/**
 * Get list of items by given key.
 * Implementation of {@link findItemByKey} method.
 *
 * @param searchKey search key
 */
function findProductByKey(searchKey) {
    findItemByKey(
        rootURL,
        searchKey,
        function (data) {
            fillProductInfoList(data);
        },
        function () {
            searchProduct('');
            $('#searchKey').val('');
        }
    )
}

/**
 * Sending GET request to rest service for get all items.
 * Implementation of {@link getItem} method.
 */
function findAllProducts() {
    getItem(
        rootURL + '?all',
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
        hideCover();
        container.style.display = 'none';
        document.onkeydown = null;
    }

    $('#buttonCancel').click(function () {
        closeWindow();
        return false;
    });

    $('#buttonBuy').click(function () {
        //todo add save item in user cart
        return false;
    });

    container.style.display = 'block';
}