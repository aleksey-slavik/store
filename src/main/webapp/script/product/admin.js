/**
 * Consist methods for correct work of methods for product rest service.
 *
 * @author oleksii.slavik
 */

/**
 * Root url path for products rest service
 *
 * @type {string}
 */
var rootURL = "http://localhost:8080/products";

/**
 * Name of column for search
 *
 * @type {string}
 */
var searchField = "name";

/**
 * Temporary variable for product data
 */
var currentItem;

//start statement of page when it is loaded
findAllProducts();

/**
 * Fill list of product using given data
 *
 * @param data given data
 */
function fillProductList(data) {
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    $('#itemList').find('li').remove();
    $.each(list, function (index, item) {
        $('#itemList').append('<li><a href="#" data-identity="' + item.id + '">' + item.name + '</a></li>');
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
 * Parse product data from form to json format
 *
 * @returns {string}
 */
function productItemToJSON() {
    var itemId = $('#id').val();
    return JSON.stringify({
        "id": itemId === '' ? null : itemId,
        "name": $('#name').val(),
        "brand": $('#brand').val(),
        "description": $('#description').val(),
        "price": $('#price').val()
    });
}

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
 * Get list of items by given key
 *
 * @param searchKey searchProduct key
 */
function findProductByKey(searchKey) {
    $.ajax({
        type: 'GET',
        url: rootURL + '?' + searchField + '=' + searchKey,
        dataType: "json",

        success: function (data, textStatus, xhr) {
            switch (xhr.status) {
                case 200:
                    fillProductList(data);
                    break;
                case 204:
                    alert('Empty response during searchProduct item with name=' + searchKey + '!');
                    searchProduct('');
                    $('#searchKey').val('');
                    break;
            }
        },

        error: function (xhr, textStatus, errorThrown) {
            switch (xhr.status) {
                case 403:
                    alert('You don`t have permissions to get item with name=' + searchKey + '!');
                    break;
                case 404:
                    alert('Item with name=' + searchKey + ' was not found!');
                    searchProduct('');
                    $('#searchKey').val('');
                    break;
                default:
                    alert('Some error thrown! Details : ' + xhr.status + ' ' + errorThrown);
                    break;
            }
        }
    });
}

/**
 * Sending GET request to rest service for get all items.
 */
function findAllProducts() {
    $.ajax({
        type: 'GET',
        url: rootURL + '?all',
        dataType: "json",

        success: function (data, textStatus, xhr) {
            switch (xhr.status) {
                case 200:
                    fillProductList(data);
                    break;
                case 204:
                    alert('Empty response during getting all items!');
                    break;
            }
        },

        error: function (xhr, textStatus, errorThrown) {
            switch (xhr.status) {
                case 403:
                    alert('You don`t have permissions to access to list of items!');
                    break;
                default:
                    alert('Some error thrown! Details : ' + xhr.status + ' ' + errorThrown);
                    break;
            }
        }
    });
}

/**
 * Sending GET request to rest service for get item by given id.
 *
 * @param id given id
 */
function findProductById(id) {
    $.ajax({
        type: 'GET',
        url: rootURL + '/' + id,
        dataType: "json",

        success: function (data, textStatus, xhr) {
            switch (xhr.status) {
                case 200:
                    currentItem = data;
                    fillProduct(currentItem);
                    showModalWindow();
                    break;
                case 204:
                    alert('Empty response during find item with id=' + id + '!');
                    break;
            }
        },

        error: function (xhr, textStatus, errorThrown) {
            switch (xhr.status) {
                case 403:
                    alert('You don`t have permissions to get item with id=' + id + '!');
                    break;
                case 404:
                    alert('Item with id=' + id + ' was not found!');
                    break;
                default:
                    alert('Some error thrown! Details : ' + xhr.status + ' ' + errorThrown);
                    break;
            }
        }
    })
}

/**
 * Clear form for insert new data
 */
function clearProductForm() {
    currentItem = {};
    fillProduct(currentItem);
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