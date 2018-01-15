//root url path for rest service
var rootURL = "http://localhost:8080/users";

var currentItem;

//start statement of page when it is loaded
findAllItems();
$('#buttonDelete').hide();

//register listener for create button
$('#buttonCreate').click(function () {
    clearForm();
    return false;
});

//register listener for search button
$('#buttonSearch').click(function () {
    search($('#searchKey').val());
    return false;
});

// Trigger search when pressing 'Return' on search key input field
$('#searchKey').keypress(function (e) {
    if (e.which === 13) {
        search($('#searchKey').val());
        e.preventDefault();
        return false;
    }
});

//register listener for save button
$('#buttonSave').click(function () {
    if ($('#id').val() === '') {
        createItem();
    } else {
        updateItem();
    }

    return false;
});

//register listener for delete button
$('#buttonDelete').click(function () {
    deleteItem();
    return false;
});

//register listener for list item
$('#itemList').on('click', 'a', function () {
    findItemById($(this).data('identity'));
});

//get list of items by given key
//if key is empty return all items
function search(searchKey) {
    if (searchKey === '') {
        findAllItems();
    } else {
        findItemByKey(searchKey);
    }
}

//get list of items by given key
function findItemByKey(searchKey) {
    $.ajax({
        type: 'GET',
        url: rootURL + '?username=' + searchKey,
        dataType: "json",
        success: fillList
    });
}

// get list of items
function findAllItems() {
    $.ajax({
        type: 'GET',
        url: rootURL + '?all',
        dataType: "json",
        success: fillList
    });
}

//get item by given id
function findItemById(id) {
    $.ajax({
        type: 'GET',
        url: rootURL + '/' + id,
        dataType: "json",
        success: function (data) {
            $('#buttonDelete').show();
            currentItem = data;
            fillItem(currentItem);
        }
    })
}

//create empty form for insert new data
function clearForm() {
    $('#buttonDelete').hide();
    currentItem = {};
    fillItem(currentItem);
}

//create item
function createItem() {
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: rootURL,
        dataType: "json",
        data: formToJSON(),
        success: function (data, status, param) {
            findAllItems();
            alert('Item successfully created!');
            $('#buttonDelete').show();
            $('#id').val(data.id);
        },
        error: function (data, status, error) {
            alert('Error during create item!');
        }
    });
}

//update item
function updateItem() {
    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: rootURL + '/' + $('#id').val(),
        dataType: "json",
        data: formToJSON(),
        success: function (data, status, param) {
            alert('Item successfully updated!');
        },
        error: function (data, status, error) {
            alert('Error during update item!');
        }
    })
}

//delete item
function deleteItem() {
    $.ajax({
        type: 'DELETE',
        url: rootURL + '/' + $('#id').val(),
        success: function (data, status, param) {
            findAllItems();
            clearForm();
            alert('Item successfully deleted!');
        },
        error: function (data, status, error) {
            alert('Error during delete item!');
        }
    })
}

//fill list data
function fillList(data) {
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    $('#itemList').find('li').remove();
    $.each(list, function (index, item) {
        $('#itemList').append('<li><a href="#" data-identity="' + item.id + '">' + item.username + '</a></li>');
    });
}

//fill form data
function fillItem(item) {
    $('#id').val(item.id);
    $('#firstName').val(item.firstname);
    $('#lastName').val(item.lastname);
    $('#username').val(item.username);
    $('#password').val(item.password);
    $('#email').val(item.email);
    $('#role').val(item.role);
}

//parse data from form to json
function formToJSON() {
    var itemId = $('#id').val();
    return JSON.stringify({
        "id": itemId === '' ? null : itemId,
        "firstname": $('#firstName').val(),
        "lastname": $('#lastName').val(),
        "username": $('#username').val(),
        "password": $('#password').val(),
        "email": $('#email').val(),
        "role": $('#role').val()
    });
}