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
 * Temporary variable for item
 */
var currentItem;

//start statement of page when it is loaded
findAllItems();

/**
 * Fill list of product using given data
 *
 * @param data given data
 */
function fillList(data) {
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
function fillItem(item) {
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
function itemToJSON() {
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
 * Register listener for search button
 */
$('#buttonSearch').click(function () {
    search($('#searchKey').val());
    return false;
});

/**
 * Trigger search when pressing 'Enter' on search input field
 */
$('#searchKey').keypress(function (e) {
    if (e.which === 13) {
        search($('#searchKey').val());
        e.preventDefault();
        return false;
    }
});

/**
 * Register listener for list item
 */
$('#wrapper').on('click', 'a', function () {
    findItemById($(this).data('identity'));
});

/**
 * Get list of items by given key.
 * If key is empty return all items
 *
 * @param searchKey search key
 */
function search(searchKey) {
    clearForm();

    if (searchKey === '') {
        findAllItems();
    } else {
        findItemByKey(searchKey);
    }
}

/**
 * Get list of items by given key
 *
 * @param searchKey search key
 */
function findItemByKey(searchKey) {
    $.ajax({
        type: 'GET',
        url: rootURL + '?' + searchField + '=' + searchKey,
        dataType: "json",

        success: function (data, textStatus, xhr) {
            switch (xhr.status) {
                case 200:
                    fillList(data);
                    break;
                case 204:
                    alert('Empty response during search item with name=' + searchKey + '!');
                    search('');
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
                    search('');
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
function findAllItems() {
    $.ajax({
        type: 'GET',
        url: rootURL + '?all',
        dataType: "json",

        success: function (data, textStatus, xhr) {
            switch (xhr.status) {
                case 200:
                    fillList();
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
function findItemById(id) {
    $.ajax({
        type: 'GET',
        url: rootURL + '/' + id,
        dataType: "json",

        success: function (data, textStatus, xhr) {
            switch (xhr.status) {
                case 200:
                    currentItem = data;
                    fillItem(currentItem);
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
function clearForm() {
    currentItem = {};
    fillItem(currentItem);
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

    $('#buttonSend').click(function () {
        return false;
    });

    container.style.display = 'block';
}