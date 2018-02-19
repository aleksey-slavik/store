/**
 * Root url path for products rest service
 *
 * @type {string}
 */
var rootURL = "http://localhost:8080/api/users";

/**
 * Temporary variable for product data
 */
var currentItem;

//start statement of page when it is loaded
findUserData();
$('#buttonSave').hide();
$('#buttonCancel').hide();

/**
 * Register listener for change disable/enable statement button
 */
$('#buttonChange').click(function () {
    changeStatement('firstName');
    changeStatement('lastName');
    changeStatement('password');
    $('#buttonChange').hide();
    $('#buttonSave').show();
    $('#buttonCancel').show();
    return false;
});

/**
 * Register listener for update user data button
 */
$('#buttonSave').click(function () {
   updateUserData();
   return false;
});

/**
 * Register listener for cancel button
 */
$('#buttonCancel').click(function () {
    fillUserData(currentItem);
    changeStatement('firstName');
    changeStatement('lastName');
    changeStatement('password');
    $('#buttonChange').show();
    $('#buttonSave').hide();
    $('#buttonCancel').hide();
    return false;
});

/**
 * Fill user form using given item data
 *
 * @param item given data
 */
function fillUserData(item) {
    $('#id').val(item.id);
    $('#firstName').val(item.firstName);
    $('#lastName').val(item.lastName);
    $('#username').val(item.username);
    $('#password').val(item.password);
    $('#email').val(item.email);
    $('#role').val(item.role);
}

/**
 * Parse orders item data from form to json format
 *
 * @returns {string}
 */
function userDataToJSON() {
    return JSON.stringify({
        "id": $('#id').val(),
        "firstName": $('#firstName').val(),
        "lastName": $('#lastName').val(),
        "username": $('#username').val(),
        "password": $('#password').val(),
        "email": $('#email').val(),
        "role": $('#role').val()
    });
}

/**
 * Sending GET request to rest service for get item.
 * Implementation of {@link getItem} method.
 */
function findUserData() {
    getItem(
        rootURL + '/?username=' + principal,

        function (data) {
            currentItem = data[0];
            fillUserData(currentItem);
        },

        function () {
            alert('User with username "' + principal + '" not found!');
        }
    )
}

/**
 * Change disabled/enabled statement of <input> with given id
 *
 * @param divId given id
 */
function changeStatement(divId) {
    var div = $('#' + divId);

    if (div.attr('disabled')) {
        div.removeAttr('disabled');
    } else {
        div.attr({
            'disabled': 'disabled'
        });
    }
}

/**
 * Sending PUT request to rest service for update item.
 * Implementation of {@link updateItem} method.
 */
function updateUserData() {
    updateItem(
        rootURL,
        $('#id').val(),
        userDataToJSON(),
        function (data) {
            currentItem = data;
            alert('User data successfully updated!');
            $('#buttonChange').show();
            $('#buttonSave').hide();
            $('#buttonCancel').hide();
            changeStatement('firstName');
            changeStatement('lastName');
            changeStatement('password');
        }
    )
}