//Shorter version
$(document).ready(() =>
    $('#capitalsRadio').click(() => {
        $('#headline').text("All Capitals");
        $('#placeholder').empty().load('show-capitals');
    }));

//Using .ajax
$(document).ready(() =>
    $('#virusesRadio').click(function () {
        $.ajax({
            method: "GET",
            url: "show",
            cache: false,
            success: () => {
                $('#headline').text("All Viruses");
                $('#placeholder').empty().load("show-viruses");
            },
            error: () => $('#placeholder').text("Loading failed....")
        })
    }));