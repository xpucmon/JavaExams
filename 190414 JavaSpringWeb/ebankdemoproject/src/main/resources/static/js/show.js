$(document).ready(function () {
    $('#ownBankAccountsRadio').attr('checked', true);
    $('#ba-headline').text("Own bank accounts");
    $('#ba-placeholder').empty().load('own');
});

$(document).ready(() =>
    $('#newAccountRadio').click(() => {
        $('#ba-headline').text("Create account");
        $('#ba-placeholder').empty().load('create');
    }));

$(document).ready(() =>
    $('#ownBankAccountsRadio').click(() => {
        $('#ba-headline').text("Own bank accounts");
        $('#ba-placeholder').empty().load('own');
    }));

$(document).ready(() =>
    $('#allBankAccountsRadio').click(() => {
        $('#ba-headline').text("All bank accounts");
        $('#ba-placeholder').empty().load('all');
    }));

$(document).ready(function () {
    $('#transactionsRadio').attr('checked', true);
    $('#tr-headline').text("Transaction");
    $('#tr-placeholder').empty().load('own');
});

$(document).ready(() =>
    $('#newTransactionRadio').click(() => {
        $('#tr-headline').text("New transaction");
        $('#tr-placeholder').empty().load('initiate');
    }));

$(document).ready(() =>
    $('#transactionsRadio').click(() => {
        $('#tr-headline').text("Transactions");
        $('#tr-placeholder').empty().load('own');
    }));

$(document).on('click', '#cancel', function (e) {
    e.preventDefault();
    parent.history.back();
});

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