/**
 * Consist methods for correct work of {@link tableCore} methods for user rest service.
 *
 * @author oleksii.slavik
 */

/**
 * Root url path for users rest service
 *
 * @type {string}
 */
var rootURL = "http://localhost:8080/users";

/**
 * Name of column for search
 *
 * @type {string}
 */
var searchField = "username";

/**
 * Fill list of users using given data
 *
 * @param data given data
 */
function fillList(data) {
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
function fillItem(item) {
    $('#id').val(item.id);
    $('#firstName').val(item.firstName);
    $('#lastName').val(item.lastName);
    $('#username').val(item.username);
    $('#password').val(item.password);
    $('#email').val(item.email);
    $('#role').val(item.role.name);
}

/**
 * Parse user data from form to json format
 *
 * @returns {string}
 */
function itemToJSON() {
    var itemId = $('#id').val();
    return JSON.stringify({
        "id": itemId === '' ? null : itemId,
        "firstName": $('#firstName').val(),
        "lastName": $('#lastName').val(),
        "username": $('#username').val(),
        "password": $('#password').val(),
        "email": $('#email').val(),
        "role": $('#role').val()
    });
}