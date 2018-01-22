/**
 * Get list of items by given key
 *
 * @param restURL rest url root
 * @param searchKey search key
 * @param successStatement statement of page if response status is 200
 * @param notFoundStatement statement of page if response status is 404
 */
function findItemByKey(restURL, searchKey, successStatement, notFoundStatement) {
    $.ajax({
        type: 'GET',
        url: rootURL + '?query=' + searchKey,
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
                    alert('You don`t have permissions to get item with key=' + searchKey + '!');
                    break;
                case 404:
                    alert('Item with key=' + searchKey + ' was not found!');
                    notFoundStatement();
                    break;
                default:
                    alert('Some error thrown! Details : ' + xhr.status + ' ' + errorThrown);
                    break;
            }
        }
    });
}