var currentItem;
findAllUserProducts();

$('#addProduct').click(function () {
    clearUserForm();
    return false;
});

$('#buttonSave').click(function () {
    if ($('#id').val() === '') {
        createUserProduct();
    } else {
        updateUserProduct();
    }

    return false;
});

$('#buttonDelete').click(function () {
    deleteUserProduct();
    return false;
});

$('#buttonShare').click(function () {
    grantPermission();
    return false;
});

$('#buttonRemove').click(function () {
    removePermission();
    return false;
});

$('#itemList').on('click', 'a', function () {
    findUserProductById($(this).data('identity'));
});

function fillUserProductList(data) {
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    $('#itemList').find('li').remove();
    $.each(list, function (index, item) {
        $('#itemList').append('<li><a href="#" data-identity="' + item.id+ '">' + item.name + '</a></li>');
    });
}

function fillUserProduct(item) {
    $('#id').val(item.id);
    $('#name').val(item.name);
    $('#brand').val(item.brand);
    $('#description').val(item.description);
    $('#price').val(item.price);
    $('#ownerId').val(item.ownerId);
    $('#owner').val(item.owner);
}

function userProductItemToJSON() {
    var itemId = $('#id').val();
    return JSON.stringify({
        "id": itemId === '' ? null : itemId,
        "name": $('#name').val(),
        "brand": $('#brand').val(),
        "description": $('#description').val(),
        "price": $('#price').val(),
        "ownerId": $('#ownerId').val(),
        "owner": $('#owner').val()
    });
}

function findAllUserProducts() {
    $.ajax({
        type: 'GET',
        url: "http://localhost:8080/api/products?owner=" + principal,
        async: false,
        dataType: "json",

        success: function (data, textStatus, xhr) {
            switch (xhr.status) {
                case 200:
                    fillUserProductList(data);
                    break;
                case 204:
                    alert("You don't have products");
                    break;
            }
        }
    });
}

function findUserProductById(id) {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/products/' + id,
        async: false,
        dataType: "json",

        success: function (data) {
            currentItem = data;
            fillUserProduct(currentItem);
            $('#buttonDelete').show();
        },

        error: function (xhr, textStatus, errorThrown) {
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

function createUserProduct() {
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: 'http://localhost:8080/api/products/',
        dataType: "json",
        data: userProductItemToJSON(),
        async: false,

        success: function (data) {
            alert("Product " + data.name + " was successfully created");
            findAllUserProducts();
            $('#buttonDelete').show();
            $('#id').val(data.id);
        },

        error: function (xhr, textStatus, errorThrown) {
            alert('Some error thrown during create product!');
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

function updateUserProduct() {
    var productId = $('#id').val();

    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: 'http://localhost:8080/api/products/' + productId,
        dataType: "json",
        data: userProductItemToJSON(),
        async: false,

        success: function (data) {
            alert('Product ' + data.name + ' successfully updated!');
        },

        error: function (xhr, textStatus, errorThrown) {
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

function deleteUserProduct() {
    var productId = $('#id').val();

    $.ajax({
        type: 'DELETE',
        url: 'http://localhost:8080/api/products/' + productId,
        async: false,

        success: function () {
            findAllUserProducts();
            clearUserForm();
        },

        error: function (xhr, textStatus, errorThrown) {
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

function clearUserForm() {
    $('#buttonDelete').hide();
    currentItem = {};
    fillUserProduct(currentItem);
}

function grantPermission() {
    var productId = $('#id').val();
    var targetUser = $('#share').val();

    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: 'http://localhost:8080/api/products/' + productId + '/permissions/' + targetUser,
        dataType: 'json',
        data: userProductItemToJSON(),
        async: false,

        success: function () {
            alert('Permissions for update product with id=' + productId + ' were successfully issued to user ' + targetUser);
        },

        error: function (xhr, textStatus, errorThrown) {
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

function removePermission() {
    var productId = $('#id').val();
    var targetUser = $('#share').val();

    $.ajax({
        type: 'DELETE',
        contentType: 'application/json',
        url: 'http://localhost:8080/api/products/' + productId + '/permissions/' + targetUser,
        dataType: "json",
        data: userProductItemToJSON(),
        async: false,

        success: function () {
            alert('Permissions for update product with id=' + productId + ' were successfully removed to user ' + targetUser);
        },

        error: function (xhr, textStatus, errorThrown) {
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}