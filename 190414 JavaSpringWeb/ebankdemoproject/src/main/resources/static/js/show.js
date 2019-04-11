$(document).ready(() =>
    $('#newAccountRadio').click(() => {
        $('#headline').text("Create account");
        $('#placeholder').empty().load('create');
    }));

$(document).ready(() =>
    $('#ownBankAccountsRadio').click(() => {
        $('#headline').text("Own bank accounts");
        $('#placeholder').empty().load('own');
    }));

$(document).ready(() =>
    $('#allBankAccountsRadio').click(() => {
        $('#headline').text("All bank accounts");
        $('#placeholder').empty().load('all');
    }));

$(document).on('click', '#cancel', function(e) {
    e.preventDefault();
    parent.history.back();
});
//
// $('button#cancel').on('click', function(e){
//     e.preventDefault();
//     window.history.back();
// });

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