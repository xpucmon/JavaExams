$(document).ready(() =>
    $('#newAccountRadio').click(() => {
        $('#headline').text("Create account");
        $('#placeholder').empty().load('create');
    }));

$(document).ready(() =>
    $('#bankAccountsRadio').click(() => {
        $('#headline').text("Your accounts");
        $('#placeholder').empty().load('all');
    }));

//Using .ajax
// $(document).ready(() =>
//     $('#virusesRadio').click(function () {
//         $.ajax({
//             method: "GET",
//             url: "show",
//             cache: false,
//             success: () => {
//                 $('#headline').text("All Viruses");
//                 $('#placeholder').empty().load("show-viruses");
//             },
//             error: () => $('#placeholder').text("Loading failed....")
//         })
//     }));