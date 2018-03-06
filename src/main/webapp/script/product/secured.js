//start statement of page when it is loaded
findAllProducts();
$("#buttonDelete").hide();

/**
 * Register listener for create button
 */
$('#buttonCreate').click(function () {
    clearProductForm();
    return false;
});

/**
 * Register listener for save button
 */
$('#buttonSave').click(function () {
    if ($('#id').val() === '') {
        createProduct();
    } else {
        updateProduct();
    }

    return false;
});

/**
 * Register listener for delete button
 */
$('#buttonDelete').click(function () {
    deleteProduct();
    return false;
});

$('#buttonShare').click(function () {
    grantPermission();
    return false;
});

/**
 * Register listener for list item
 */
$('#itemList').on('click', 'a', function () {
    findProductById($(this).data('identity'));
});

/**
 * Register listener for searchProduct button
 */
$('#buttonSearch').click(function () {
    //searchProduct($('#searchKey').val());
    return false;
});

/**
 * Trigger searchProduct when pressing 'Enter' on searchProduct input field
 */
$('#searchKey').keypress(function (e) {
    /*if (e.which === 13) {
        searchProduct($('#searchKey').val());
        e.preventDefault();
        return false;
    }*/
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
            fillProductList(data);
        },
        function () {
            searchProduct('');
            $('#searchKey').val('');
        }
    )
}

/**
 * Fill list of product using given data
 *
 * @param data given data
 */
function fillProductList(data) {
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    $('#itemList').find('li').remove();
    $.each(list, function (index, item) {
        $('#itemList').append('<li><a href="#" data-identity="' + item.productId + '">' + item.name + '</a></li>');
    });
}

/**
 * Fill product form using given item data
 *
 * @param item given data
 */
function fillProduct(item) {
    $('#id').val(item.productId);
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
        "productId": itemId === '' ? null : itemId,
        "name": $('#name').val(),
        "brand": $('#brand').val(),
        "description": $('#description').val(),
        "price": $('#price').val()
    });
}

/**
 * Sending GET request to rest service for get all items.
 * Implementation of {@link getItem} method.
 */
function findAllProducts() {
    getItem(
        rootURL,
        function (data) {
            fillProductList(data);
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
            $('#buttonDelete').show();
        },
        function () {
            //do nothing
        }
    )
}

/**
 * Sending POST request to rest service for create item.
 * Implementation of {@link createItem} method.
 */
function createProduct() {
    createItem(
        rootURL,
        productItemToJSON(),
        function (data) {
            findAllProducts();
            $('#buttonDelete').show();
            $('#id').val(data.id);
        }
    )
}

/**
 * Sending PUT request to rest service for update item.
 * Implementation of {@link updateItem} method.
 */
function updateProduct() {
    var itemId = $('#id').val();

    updateItem(
        rootURL,
        itemId,
        productItemToJSON(),
        function () {
            alert('Item with id=' + itemId + ' successfully updated!');
        }
    )
}

/**
 * Sending DELETE request to rest service for delete item.
 */
function deleteProduct() {
    var itemId = $('#id').val();

    deleteItem(
        rootURL,
        itemId,
        function () {
            findAllProducts();
            clearProductForm();
        }
    )
}

/**
 * Clear form for insert new data
 */
function clearProductForm() {
    $('#buttonDelete').hide();
    currentItem = {};
    fillProduct(currentItem);
}