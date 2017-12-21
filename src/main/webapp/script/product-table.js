//create Tabulator on DOM element with id "example-table"
$("#example-table").tabulator({
    layout: "fitColumns",
    //Define Table Columns
    columns: [
        {title: "Name", field: "name", width: 150},
        {title: "Brand", field: "brand"},
        {title: "Description", field: "description"},
        {title: "Price", field: "price"}
    ],
    //trigger when the row is clicked
    rowClick: function (e, row) {
        window.location.href = "/productItem?id=" + row.getData().id;
    }
});

//load data into the table
var response = $.getJSON("/products",
    function (data) {
        $("#example-table").tabulator("setData", data);
    });
