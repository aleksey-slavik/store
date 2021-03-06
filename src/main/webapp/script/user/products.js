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

$('#itemList').on('click', 'a', function () {
    findUserProductById($(this).data('identity'));
    getPermissionList($(this).data('identity'));
});

$('#sidTable').on('click', 'img', function () {
    removePermission($(this).data('identity'));
});

function fillUserProductList(data) {
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    $('#itemList').find('li').remove();
    $.each(list, function (index, item) {
        $('#itemList').append('<li><a href="#" data-identity="' + item.id + '">' + item.name + '</a></li>');
    });
}

function fillProductPermissions(data) {
    $('#share').val('');
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    $('#sidTable').find('tr').remove();
    $.each(list, function (index, item) {
        $('#sidTable').append(
            '<tr>' +
            '<td>' + item.sid + '</td>' +
            '<td><img class="icon" src="/images/trash.png" alt="remove" title="remove item" data-identity="' + item.sid + '"></td>' +
            '</tr>');
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
    return JSON.stringify({
        "id": $('#id').val(),
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

        error: function (xhr) {
            switch (xhr.status) {
                case 404:
                    alert('Product with id=' + id + ' not found!');
                    break;
            }
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

        error: function (xhr) {
            switch (xhr.status) {
                case 406:
                    alert('Product owner not exist!');
                    break;
                case 409:
                    alert('Permission already granted!');
                    break;
            }
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

        error: function (xhr) {
            switch (xhr.status) {
                case 406:
                    alert('Product owner or product not exist!');
                    break;
            }
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

        error: function (xhr) {
            switch (xhr.status) {
                case 404:
                    alert("Can't remove not existed product with id=" + productId);
                    break;
            }
        }
    });
}

function clearUserForm() {
    $('#buttonDelete').hide();
    currentItem = {};
    fillUserProduct(currentItem);
    fillProductPermissions({});
}

function getPermissionList(id) {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/products/' + id + '/permissions',
        async: false,
        dataType: "json",

        success: function (data, status, xhr) {
            switch (xhr.status) {
                case 200:
                    fillProductPermissions(data);
                    break;
                case 204:
                    $('#sidTable').find('tr').remove();
                    console.log('permission list is empty');
                    break;
            }
        },

        error: function (xhr) {
            switch (xhr.status) {
                case 404:
                    console.log('product with id=' + id + ' not found!');
                    break;
            }
        }
    });
}

function grantPermission() {
    var productId = $('#id').val();
    var targetUser = $('#share').val();
    var sidJson = JSON.stringify({
        "sid": targetUser
    });
    console.log(sidJson);

    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: 'http://localhost:8080/api/products/' + productId + '/permissions',
        dataType: 'json',
        data: sidJson,
        async: false,

        success: function (data) {
            alert('Permissions for update product with id=' + productId + ' were successfully issued to user ' + data.sid);
        },

        error: function (xhr) {
            switch (xhr.status) {
                case 400:
                    alert('Username of granted user not valid!');
                    break;
                case 404:
                    alert('Product with id=' + productId + ' not found!');
                    break;
                case 409 :
                    alert('Conflict during create permission for product with id=' + productId + ' to user ' + targetUser);
                    break;
            }
        }
    });

    $('#share').val('');
    getPermissionList(productId);
}

function removePermission(user) {
    var productId = $('#id').val();

    $.ajax({
        type: 'DELETE',
        url: 'http://localhost:8080/api/products/' + productId + '/permissions/' + user,
        async: false,

        success: function () {
            getPermissionList(productId);
            alert('Permissions for update product with id=' + productId + ' were successfully removed from user ' + user);
        },

        error: function (xhr) {
            switch (xhr.status) {
                case 404:
                    alert('Error during remove permission!');
                    break;
            }
        }
    });
}