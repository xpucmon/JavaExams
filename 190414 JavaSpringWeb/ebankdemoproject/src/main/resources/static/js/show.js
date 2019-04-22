//Bank Accounts funcs
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

//Cards funcs
$(document).ready(function () {
    $('#ownCardsRadio').attr('checked', true);
    $('#c-headline').text("Own cards");
    $('#c-placeholder').empty().load('own');
});

$(document).ready(() =>
    $('#newDebitCardRadio').click(() => {
        $('#c-headline').text("Debit card request");
        $('#c-placeholder').empty().load('debit');
    }));

$(document).ready(() =>
    $('#newCreditCardRadio').click(() => {
        $('#c-headline').text("Credit card request");
        $('#c-placeholder').empty().load('credit');
    }));

$(document).ready(() =>
    $('#ownCardsRadio').click(() => {
        $('#c-headline').text("Own cards");
        $('#c-placeholder').empty().load('own');
    }));

$(document).ready(() =>
    $('#allUsersCardsRadio').click(() => {
        $('#c-headline').text("All users' cards");
        $('#c-placeholder').empty().load('all');
    }));

function showCreditLimit(currentObject) {
    if ($(currentObject).is(":checked") && $(currentObject).val() === "CREDIT_CARD") {
        $('#inputMaxCredit').show().focus();
    } else {
        $('#inputMaxCredit').val('').hide();
    }
}

//Transactions funcs
$(document).ready(function () {
    $('#ownTransactionsRadio').attr('checked', true);
    $('#tr-headline').text("Transaction");
    $('#tr-placeholder').empty().load('own');
});

$(document).ready(() =>
    $('#newTransactionRadio').click(() => {
        $('#tr-headline').text("New transaction");
        $('#tr-placeholder').empty().load('initiate');
    }));

$(document).ready(() =>
    $('#ownTransactionsRadio').click(() => {
        $('#tr-headline').text("Own transactions");
        $('#tr-placeholder').empty().load('own');
    }));

$(document).ready(() =>
    $('#allTransactionsRadio').click(() => {
        $('#tr-headline').text("All transactions");
        $('#tr-placeholder').empty().load('all');
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
        $('#inputRegularity').val('');
    }
}

//Other funcs
$(document).on('click', '#cancel', function (e) {
    e.preventDefault();
    parent.history.back();
});

// $('button#cancel').on('click', function(e){
//     e.preventDefault();
//     window.history.back();
// });

//Using .ajax
// $(document).ready(() =>
//     $('#ownBankAccountsRadio').click(function () {
//         $.ajax({
//             method: "GET",
//             url: "home",
//             cache: false,
//             success: () => {
//                 $('#ba-headline').text("Own bank accounts");
//                 $('#ba-placeholder').empty().load("own-accounts");
//             },
//             error: () => $('#ba-placeholder').text("Loading failed....")
//         })
//     }));