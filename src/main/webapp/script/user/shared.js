//start page statement
var currentItem;
findAllSharedProducts();
$('#buttonSave').hide();

$('#buttonSave').click(function () {
    updateSharedProduct();
    return false;
});

$('#itemList').on('click', 'a', function () {
    findSharedProductById($(this).data('identity'));
});

function fillSharedProductList(data) {
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    $('#itemList').find('li').remove();
    $.each(list, function (index, item) {
        $('#itemList').append('<li><a href="#" data-identity="' + item.id+ '">' + item.name + '</a></li>');
    });
}

function fillSharedProduct(item) {
    $('#id').val(item.id);
    $('#name').val(item.name);
    $('#brand').val(item.brand);
    $('#description').val(item.description);
    $('#price').val(item.price);
    $('#ownerId').val(item.ownerId);
    $('#owner').val(item.owner);
}

function sharedProductItemToJSON() {
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

function findAllSharedProducts() {
    $.ajax({
        type: 'GET',
        url: "http://localhost:8080/api/products/shared",
        async: false,
        dataType: "json",

        success: function (data, textStatus, xhr) {
            switch (xhr.status) {
                case 200:
                    fillSharedProductList(data);
                    break;
                case 204:
                    alert("You don't have shared products");
                    break;
            }
        }
    });
}

function findSharedProductById(id) {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/products/' + id,
        async: false,
        dataType: "json",

        success: function (data) {
            currentItem = data;
            fillSharedProduct(currentItem);
            $('#buttonSave').show();
        },

        error: function (xhr, textStatus, errorThrown) {
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

function updateSharedProduct() {
    var productId = $('#id').val();

    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: 'http://localhost:8080/api/products/' + productId,
        dataType: "json",
        data: sharedProductItemToJSON(),
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