/**
 * Created by Alex on 07.02.15.
 */

function checkContractorForm() {
    var contractor  = document.getElementsByName("contractors-name").item(0).value;
    var contractNumber = document.getElementsByName("contract-number").item(0).value;

    if (contractor === "" || contractor.length > 20) {
        alert("The field 'Contractor's name' is empty or length is longer then 20 characters");
        return false;
    }

    if (contractNumber === "" || contractNumber.length > 20) {
        alert("The field 'Contract number' is empty or length is longer then 20 characters");
        return false;
    }
}

function checkInvoiceForm() {
    var invoiceNumber = document.getElementById("invoice-number").value;
    var invoiceAmount = document.getElementById("invoice-amount").value;
    var invoiceDate = document.getElementById("invoice-date").value;

    if (invoiceNumber === "" || invoiceNumber.length > 15) {
        alert("The field 'Invoice number' is empty or length is longer then 15 characters");
        return false;
    }

    if (invoiceAmount === "" || Number(invoiceAmount) > 9999999) {
        alert("Please input invoice amount");
        return false;
    } else if (isNaN(Number(invoiceAmount))) {
        alert("You entered invalid amount");
        return false;
    }

    if (invoiceDate === "") {
        alert("Please enter a invoice date");
        return false;
    }
}

function checkPaymentForm() {
    var paymentAmount = document.getElementById("payment-amount").value;
    var paymentDate = document.getElementById("payment-date").value;

    if (paymentAmount === "" || paymentAmount > 9999999) {
        alert("Please input payment amount");
        return false;
    } else if (isNaN(Number(paymentAmount))) {
        alert("You entered invalid amount");
        return false;
    }

    if (paymentDate === "") {
        alert("Please enter payment date");
        return false;
    }
}
