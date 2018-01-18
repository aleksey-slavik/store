/**
 * Get list of items by given key.
 * If key is empty return all items
 *
 * @param searchKey search key
 */
function search(rootURL, searchField, searchKey, successState, errorState) {
    if (searchKey === '') {
        findAllItems(rootURL, successState, errorState);
    } else {
        findItemByKey(rootURL, searchField, searchKey, successState, errorState);
    }
}

/**
 * Get list of items by given key
 *
 * @param searchKey search key
 */
function findItemByKey(rootURL, searchField, searchKey, successState, errorState) {
    $.ajax({
        type: 'GET',
        url: rootURL + '?' + searchField + '=' + searchKey,
        dataType: "json",
        success: function (data) {
            if (data.length === 0) {
                errorState();
                /*alert('Item with key "' + searchKey + '" not found!');
                search('');
                $('#searchKey').val('');*/
            } else {
                successState();
                /*fillList(data);*/
            }
        }
    });
}

/**
 * Sending GET request to rest service for get all items.
 */
function findAllItems(rootURL, successState, errorState) {
    $.ajax({
        type: 'GET',
        url: rootURL + '?all',
        dataType: "json",
        success: function (data) {
            successState(data);
        },
        error: function (data) {
            errorState(data);
        }
    });
}