var ctx;

$(function () {
    ctx = {
        ajaxUrl: "ajax/profile/meals/",
        datatableApi: $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
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
                    "desc"
                ]
            ]
        })
    };
    makeEditable();

    $('#filter').submit(function () {
        updateFilteredTable();
        return false;
    });
});

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: "ajax/profile/meals/filter",
        data: $("#filter").serialize()
    }).done(function (data) {
        updateData(data);
        successNoty("Table is filtered.")
    })
}

function clearFilter() {
    $('#filter')[0].reset();
    updateTable();
}

function updateData(data) {
    ctx.datatableApi.clear().rows.add(data).draw();
}