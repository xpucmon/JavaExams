$(document).ready(() =>
    $('#newAccountRadio').click(() => {
        $('#headline').text("Create account");
        $('#ba-placeholder').empty().load('create');
    }));

$(document).ready(() =>
    $('#ownBankAccountsRadio').click(() => {
        $('#headline').text("Own bank accounts");
        $('#ba-placeholder').empty().load('own');
    }));

$(document).ready(() =>
    $('#allBankAccountsRadio').click(() => {
        $('#headline').text("All bank accounts");
        $('#ba-placeholder').empty().load('all');
    }));

$(document).ready(function () {
    $('#ownBankAccountsRadio').attr('checked', true);
    $('#headline').text("Own bank accounts");
    $('#ba-placeholder').empty().load('own');
});


$(document).ready(() =>
    $('#newTransactionRadio').click(() => {
        $('#headline').text("New transaction");
        $('#tr-placeholder').empty().load('initiate');
    }));

$(document).ready(function () {
    $('#newTransactionRadio').attr('checked', true);
    $('#headline').text("New transaction");
    $('#tr-placeholder').empty().load('initiate');
});

$(document).on('click', '#cancel', function (e) {
    e.preventDefault();
    parent.history.back();
});


$(document).ready(() =>
    $('#newTransactionRadio').click(() => {
        $('#headline').text("New transaction");
        $('#tr-placeholder').empty().load('initiate');
    }));

function showRecipient(currentObject) {
    if ($(currentObject).is(":checked") && $(currentObject).val() === "TRANSFER") {
        $('#recipientIbanInput').show().focus();
        $('#recipientFirstNameInput').show().focus();
        $('#recipientLastNameInput').show().focus();
    } else {
        $('#recipientIbanInput').hide();
        $('#recipientFirstNameInput').hide();
        $('#recipientLastNameInput').hide();
    }
}

function showRegularities(currentObject) {
    if ($(currentObject).is(":checked")) {
        $('#regularityInput').show().focus();
    } else {
        $('#regularityInput').hide();
    }
}


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