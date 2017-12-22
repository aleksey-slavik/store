//create Tabulator on DOM element with id "example-table"
$("#example-table").tabulator({
    layout: "fitColumns",
    //Define Table Columns
    columns: [
        {title: "Name", field: "name", editor: "input"},
        {title: "Brand", field: "brand", editor: "input"},
        {title: "Description", field: "description", editor: "input"},
        {title: "Price", field: "price", editor: "input"}
    ]
});

//load data into the table
var response = $.getJSON("/products",
    function (data) {
        $("#example-table").tabulator("setData", data);
    });
