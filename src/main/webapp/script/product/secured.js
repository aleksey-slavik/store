//start statement of page when it is loaded
findAllProducts();
$("#buttonDelete").hide();

/**
 * listener for clear form button
 */
$('#buttonCreate').click(function () {
    clearProductForm();
    return false;
});

/**
 * listener for save button
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
 * listener for delete button
 */
$('#buttonDelete').click(function () {
    deleteProduct();
    return false;
});

/**
 * listener for share permission button
 */
$('#buttonShare').click(function () {
    grantPermission();
    return false;
});

/**
 * Register listener for list item
 */
$('#itemList').on('click', 'a', function () {
    findProductById($(this).data('identity'));
    getPermissionList($(this).data('identity'));
});

$('#sidTable').on('click', 'img', function () {
    removePermission($(this).data('identity'));
});

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
 * Fill list of permissions of given product
 *
 * @param data given data
 */
function fillProductPermissions(data) {
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    $('#sidTable').find('tr').remove();
    $.each(list, function (index, item) {
        $('#sidTable').append(
            '<tr>' +
            '<td>' + item.sid + '</td>' +
            '<td><img class="icon" src="/images/trash.png" alt="remove" title="remove item" data-identity=">' + item.sid + '"></td>' +
            '</tr>');
    });
}

/**
 * Fill product form using given item data
 *
 * @param item given data
 */
function fillProduct(item) {
    $('#productId').val(item.id);
    $('#name').val(item.name);
    $('#brand').val(item.brand);
    $('#description').val(item.description);
    $('#price').val(item.price);
    $('#merchantId').val(item.ownerId);
    $('#merchant').val(item.owner);
}

/**
 * Parse product data from form to json format
 *
 * @returns {string}
 */
function productItemToJSON() {
    return JSON.stringify({
        "productId": $('#productId').val(),
        "name": $('#name').val(),
        "brand": $('#brand').val(),
        "description": $('#description').val(),
        "price": $('#price').val(),
        "ownerId": $('#merchantId').val(),
        "owner": $('#merchant').val()
    });
}

function findAllProducts() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/products?size=20',
        async: false,
        dataType: "json",

        success: function (data) {
            fillProductList(data);
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
            $('#buttonDelete').show();
        },

        error: function (xhr, textStatus, errorThrown) {
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

function createProduct() {
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: 'http://localhost:8080/api/products',
        dataType: "json",
        data: productItemToJSON(),
        async: false,

        success: function (data) {
            alert('Item with id=' + data.id + ' successfully created!');
            findAllProducts();
            $('#buttonDelete').show();
            $('#id').val(data.id);
        },

        error: function (xhr, textStatus, errorThrown) {
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

function updateProduct() {
    var itemId = $('#productId').val();

    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: 'http://localhost:8080/api/products/' + itemId,
        dataType: "json",
        data: productItemToJSON(),
        async: false,

        success: function (data) {
            alert('Item with id=' + data.id + ' successfully updated!');
        },

        error: function (xhr, textStatus, errorThrown) {
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

function deleteProduct() {
    var itemId = $('#productId').val();

    $.ajax({
        type: 'DELETE',
        url: 'http://localhost:8080/api/products/' + itemId,
        async: false,

        success: function (data) {
            alert('Item with id=' + data.id + ' successfully deleted!');
            findAllProducts();
            clearProductForm();
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
function clearProductForm() {
    $('#buttonDelete').hide();
    currentItem = {};
    fillProduct(currentItem);
    fillProductPermissions({});
}

function getPermissionList(id) {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/products/' + id + '/permissions',
        async: false,
        dataType: "json",

        success: function (data) {
            fillProductPermissions(data);
        },

        error: function (xhr, textStatus, errorThrown) {
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

function grantPermission() {
    var productId = $('#id').val();
    var targetUser = $('#share').val();
    var sidJson = JSON.stringify({
        "sid": targetUser
    });

    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: 'http://localhost:8080/api/products/' + productId + '/permissions',
        dataType: 'json',
        data: sidJson,
        async: false,

        success: function () {
            alert('Permissions for update product with id=' + productId + ' were successfully issued to user ' + targetUser);
            $('#share').val('');
            getPermissionList(productId);
        },

        error: function (xhr, textStatus, errorThrown) {
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

function removePermission(user) {
    var productId = $('#id').val();
    console.log(userProductItemToJSON());

    $.ajax({
        type: 'DELETE',
        url: 'http://localhost:8080/api/products/' + productId + '/permissions/' + user,
        async: false,

        success: function () {
            getPermissionList(productId);
            alert('Permissions for update product with id=' + productId + ' were successfully removed from user ' + user);
        },

        error: function (xhr, textStatus, errorThrown) {
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}