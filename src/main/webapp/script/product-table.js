//create Tabulator on DOM element with id "example-table"
$("#example-table").tabulator({
    height: 150, // set height of table, this enables the Virtual DOM and improves render speed dramatically (can be any valid css height value)
    layout: "fitColumns", //fit columns to width of table (optional)
    columns: [ //Define Table Columns
        {title: "Name", field: "name", width: 150},
        {title: "Brand", field: "brand"},
        {title: "Description", field: "description"},
        {title: "Price", field: "price", align: "center"}
    ]
});

//load sample data into the table
var response = $.getJSON("/products",
    function (data) {
        $("#example-table").tabulator("setData", data);
    });
