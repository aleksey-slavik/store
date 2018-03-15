$('#register').click(function () {
    createAccount();
    return false;
});

function userDataToJSON() {
    return JSON.stringify({
        "firstName": $('#firstName').val(),
        "lastName": $('#lastName').val(),
        "username": $('#username').val(),
        "password": $('#password').val(),
        "email": $('#email').val(),
        "enabled": true
    });
}


function createAccount() {
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: 'http://localhost:8080/api/users?autoLogin=true',
        dataType: "json",
        data: userDataToJSON(),
        async: false,

        success: function (data) {
            alert('user' + data.username + ' successfully created!');
            window.location.href = "/";
        },

        error: function (xhr, textStatus, errorThrown) {
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}
