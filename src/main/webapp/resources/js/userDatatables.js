var ajaxUrl = "ajax/admin/users/";
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
    makeEditable();
    makeEditableForUsers();
});

function makeEditableForUsers() {
    $(".enabled").change(function () {
        var enabled = $(this).is(':checked');
        var id = $(this).parents('tr').first().attr("id");
        setEnabled(id,enabled)
    });
}

function setEnabled(id,enabled) {
    $.ajax({
        type: "POST",
        url: ajaxUrl+id,
        data: 'enabled=' + enabled,
        success: function () {
            updateTable();
            successNoty("Enabled changed");
        }
    });
}
// function enableDisable() {
//     var checkbox = $(".enabled");
//     var enabled = checkbox.is(':checked');
//     var id = checkbox.parents('tr').first().attr("id");
//     //var id2 = this.parents('tr').first().attr("id");
//     var tmp=0;
//
//
//    // $(this).checked();
//
// }