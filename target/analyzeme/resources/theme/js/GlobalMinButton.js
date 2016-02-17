/**
 * Created by Sergey on 16.02.2016.
 */

function GlobalMin(fileName) {
    alert("Button returns fixed value from servlet");

    //AJAX request for getting minimum of Data
    $(document).on("click", "#GlobalMinButton", function () {
        $.ajax({
            type: "GET",
            async: true,
            url: "GlobalMinServlet",
            data: {'fileName': fileName},
            success: function (data, textStatus, request) {
                alert(request.getResponseHeader('minimum'));
            },
            error: function (request, textStatus, errorThrown) {
                alert("Error");
            }
        });
    })


}
