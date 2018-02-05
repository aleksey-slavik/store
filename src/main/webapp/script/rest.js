/**
 * Sending GET request to rest service for get items.
 *
 * @param restURL rest url with parameters
 * @param successStatement statement of page if response status is 200
 * @param notFoundStatement statement of page if response status is 404
 */
function getItem(restURL, successStatement, notFoundStatement) {
    $.ajax({
        type: 'GET',
        url: restURL,
        async: false,
        dataType: "json",

        success: function (data, textStatus, xhr) {
            switch (xhr.status) {
                case 200:
                    successStatement(data);
                    break;
                default:
                    alert('Response status: ' + xhr.status + '!');
                    break;
            }
        },

        error: function (xhr, textStatus, errorThrown) {
            switch (xhr.status) {
                case 403:
                    alert('You don`t have permissions!');
                    break;
                case 404:
                    alert('Items not found!');
                    notFoundStatement();
                    break;
                default:
                    alert('Some error thrown! Details : ' + xhr.status + ' ' + errorThrown);
                    break;
            }
        }
    });
}

/**
 * Sending POST request to rest service for create item.
 *
 * @param restURL rest url
 * @param itemData item data in JSON format
 * @param successStatement statement of page if response status is 200
 */
function createItem(restURL, itemData, successStatement) {
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: restURL,
        dataType: "json",
        data: itemData,

        success: function (data, textStatus, xhr) {
            switch (xhr.status) {
                case 200:
                    successStatement(data);
                    break;
                default:
                    alert('Response status: ' + xhr.status + '!');
                    break;
            }
        },

        error: function (xhr, textStatus, errorThrown) {
            switch (xhr.status) {
                case 403:
                    alert('You don`t have permissions to create items!');
                    break;
                default:
                    alert('Some error thrown! Details : ' + xhr.status + ' ' + errorThrown);
                    break;
            }
        }
    });
}

/**
 * Sending PUT request to rest service for update item.
 *
 * @param restURL rest url
 * @param itemId id of given item
 * @param itemData item data in JSON format
 * @param successStatement statement of page if response status is 200
 */
function updateItem(restURL, itemId, itemData, successStatement) {
    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: restURL + '/' + itemId,
        dataType: "json",
        data: itemData,

        success: function (data, textStatus, xhr) {
            switch (xhr.status) {
                case 200:
                    successStatement(data);
                    break;
                default:
                    alert('Response status: ' + xhr.status + '!');
                    break;
            }
        },

        error: function (xhr, textStatus, errorThrown) {
            switch (xhr.status) {
                case 403:
                    alert('You don`t have permissions to update items!');
                    break;
                default:
                    alert('Some error thrown! Details : ' + xhr.status + ' ' + errorThrown);
                    break;
            }
        }
    });
}

/**
 * Sending DELETE request to rest service for delete item.
 *
 * @param restURL rest url
 * @param itemId id of given item
 * @param successStatement statement of page if response status is 200
 */
function deleteItem(restURL, itemId, successStatement) {
    $.ajax({
        type: 'DELETE',
        url: restURL + '/' + itemId,

        success: function (data, textStatus, xhr) {
            switch (xhr.status) {
                case 200:
                    successStatement(data);
                    break;
                default:
                    alert('Response status: ' + xhr.status + '!');
                    break;
            }
        },

        error: function (xhr, textStatus, errorThrown) {
            switch (xhr.status) {
                case 403:
                    alert('You don`t have permissions to delete item with id=' + itemId + '!');
                    break;
                default:
                    alert('Some error thrown! Details : ' + xhr.status + ' ' + errorThrown);
                    break;
            }
        }
    });
}