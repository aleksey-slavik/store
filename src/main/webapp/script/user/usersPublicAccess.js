/**
 * Root url path for products rest service
 *
 * @type {string}
 */
var rootURL = "http://localhost:8080/users";

/**
 * Temporary variable for product data
 */
var currentItem;

//start statement of page when it is loaded
findUserData();

$('#buttonChange').click(function () {
    if($('#firstName').attr('disabled')) {
        $('#firstName').removeAttr('disabled');
    } else {
        $('#firstName').attr({
            'disabled': 'disabled'
        });
    }
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
 * Parse order item data from form to json format
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
 * Clear form for insert new data
 */
function clearUserForm() {
    currentItem = {};
    fillUserData(currentItem);
}

/**
 * Sending GET request to rest service for get item by given id.
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