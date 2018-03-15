/**
 * Temporary variable for product data
 */
var currentItem;

//start statement of page when it is loaded
findAllUsers();
$('#buttonDelete').hide();
$('#buttonSave').hide();
$('#buttonClear').hide();

/**
 * Register listener for create button
 */
$('#buttonClear').click(function () {
    clearUserForm();
    return false;
});

/**
 * Register listener for save button
 */
$('#buttonSave').click(function () {
    if ($('#id').val() === '') {
        createUser();
    } else {
        updateUser();
    }

    return false;
});

/**
 * Register listener for delete button
 */
$('#buttonDelete').click(function () {
    deleteUser();
    return false;
});

$('#role_customer').click(function () {
    if ($('#role_customer').is(':checked')) {
        appendAuthority('CUSTOMER');
    } else {
        deleteAuthority(2);
    }
    return false;
});

$('#role_admin').click(function () {
    if ($('#role_admin').is(':checked')) {
        appendAuthority('ADMIN');
    } else {
        deleteAuthority(1);
    }
    return false;
});

/**
 * Register listener for list item
 */
$('#itemList').on('click', 'a', function () {
    findUserById($(this).data('identity'));
    findUserAuthorityById($(this).data('identity'));
});

/**
 * Fill list of users using given data
 *
 * @param data given data
 */
function fillUserList(data) {
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    $('#itemList').find('li').remove();
    $.each(list, function (index, item) {
        $('#itemList').append('<li><a href="#" data-identity="' + item.id + '">' + item.username + '</a></li>');
    });
}

/**
 * Fill user form using given item data
 *
 * @param item given data
 */
function fillUser(item) {
    $('input').filter(':checkbox').prop('checked', false);

    $('#id').val(item.id);
    $('#firstName').val(item.firstName);
    $('#lastName').val(item.lastName);
    $('#username').val(item.username);
    $('#password').val(item.password);
    $('#email').val(item.email);
    $('#enabled').prop("checked", item.enabled);
}

function fillUserAuthority(data) {
    $('#roles').find('input[type=checkbox]').prop('checked', false);

    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    $.each(list, function (index, item) {
        switch (item.title) {
            case 'CUSTOMER':
                $('#role_customer').prop("checked", true);
                break;
            case 'ADMIN':
                $('#role_admin').prop("checked", true);
                break;
        }
    });
}

/**
 * Parse user data from form to json format
 *
 * @returns {string}
 */
function userItemToJSON() {
    return JSON.stringify({
        "id": $('#id').val(),
        "firstName": $('#firstName').val(),
        "lastName": $('#lastName').val(),
        "username": $('#username').val(),
        "password": $('#password').val(),
        "email": $('#email').val(),
        "enabled": $('#enabled').is(':checked')
    });
}

function findAllUsers() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/users?size=20',
        async: false,
        dataType: "json",

        success: function (data) {
            fillUserList(data)
        },

        error: function (xhr, textStatus, errorThrown) {
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

function findUserById(id) {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/users/' + id,
        async: false,
        dataType: "json",

        success: function (data) {
            currentItem = data;
            fillUser(currentItem);
            $('#buttonDelete').show();
            $('#buttonSave').show();
            $('#buttonClear').show();
        },

        error: function (xhr, textStatus, errorThrown) {
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

function findUserAuthorityById(id) {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/users/' + id + '/authorities',
        async: true,
        dataType: "json",

        success: function (data) {
            fillUserAuthority(data);
        },

        error: function (xhr, textStatus, errorThrown) {
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

function createUser() {
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: 'http://localhost:8080/api/users',
        dataType: "json",
        data: userItemToJSON(),
        async: false,

        success: function (data) {
            currentItem = data;
            findAllUsers();
            $('#buttonDelete').show();
            $('#buttonSave').show();
            $('#buttonClear').show();
            $('#id').val(data.id);
        },

        error: function (xhr, textStatus, errorThrown) {
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

function appendAuthority(auth) {
    var jsonAuth = JSON.stringify({
        "title": auth
    });

    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: 'http://localhost:8080/api/users/' + $('#id').val() + '/authorities',
        dataType: "json",
        data: jsonAuth,
        async: false,

        success: function (data) {
            findUserAuthorityById($('#id').val());
            alert('Authority ' + data.title + ' successfully granted!');
        },

        error: function (xhr, textStatus, errorThrown) {
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

function updateUser() {
    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: 'http://localhost:8080/api/users/' + $('#id').val(),
        dataType: "json",
        data: userItemToJSON(),
        async: false,

        success: function (data) {
            currentItem = data;
            alert('User with username ' + data.username + ' successfully updated!');
        },

        error: function (xhr, textStatus, errorThrown) {
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

function deleteUser() {
    $.ajax({
        type: 'DELETE',
        url: 'http://localhost:8080/api/users/' + $('#id').val(),
        async: false,

        success: function () {
            findAllUsers();
            clearUserForm();
        },

        error: function (xhr, textStatus, errorThrown) {
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

function deleteAuthority(authId) {
    $.ajax({
        type: 'DELETE',
        url: 'http://localhost:8080/api/users/' + $('#id').val() + '/authorities/' + authId,
        async: false,

        success: function (data) {
            findUserAuthorityById($('#id').val());
            alert('Authority ' + data.title + ' was successfully withdrawn!');
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
function clearUserForm() {
    $('#buttonDelete').hide();
    $('#buttonSave').hide();
    $('#buttonClear').hide();
    currentItem = {};
    fillUser(currentItem);
}