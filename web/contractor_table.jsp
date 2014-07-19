<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    <jsp:include page="contractors_list.jsp"/>
    <div id="contractor-name-div">
        <b>Current contractor:</b> ${applicationScope.currentContractor}<br>
    </div>
    <div id="invoices-div">
        <table id="invoice-input-name">
            <tr>
                <td>Number</td>
                <td>Amount</td>
                <td>Date</td>
            </tr>
        </table>
        <form id="invoice-form" action="ServletInvoiceTable" method="post" enctype="application/x-www-form-urlencoded">
            <input type="text" name="number" style="width: 109px">
            <input type="text" name="amount" style="width: 109px">
            <input type="date" name="date" style="width: 109px">
            <input type="hidden" name="jsp-identifier" value="invoices-table">
            <input type="submit" value="Add invoice">
        </form>
        <form id="invoice-table" action="ServletInvoiceTable" method="get" enctype="application/x-www-form-urlencoded">
            <table id="invoices-list">
                <thead>
                    <tr>
                        <th>Invoice Number</th>
                        <th>Invoice Amount</th>
                        <th>Invoice Date</th>
                        <th>Due Date</th>
                        <th>Payment state</th>
                    </tr>
                </thead>
                <c:forEach items="${applicationScope.listOfInvoices}" var="item">
                    <tr>
                        <td>${item.number}</td>
                        <td>${item.amount}</td>
                        <td>${item.dateFormatted}</td>
                        <td>${item.dueDateFormatted}</td>
                        <td>${item.paymentState}</td>
                        <td style="width: 50px"><a href="ServletInvoiceTable?action=delete&invoice_id=${item.number}"/>Delete</td>
                        <td style="width: 50px"><a href="ServletInvoiceTable?action=edit&invoice_id=${item.number}"/>Edit</td>
                    </tr>
                </c:forEach>
            </table>
        </form>
    </div>
    <div id="payments-name">
        <b>Payments</b><br>
    </div>
    <div id="payments-div">
        <table id="payment-input-name">
            <tr>
                <td>Amount</td>
                <td>Date</td>
            </tr>
        </table>
        <form id="payment-form" action="ServletInvoiceTable" method="post" enctype="application/x-www-form-urlencoded">
            <input type="text" name="amount" style="width: 109px">
            <input type="date" name="date" style="width: 109px">
            <input type="hidden" name="jsp-identifier" value="payments-table">
            <input type="submit" value="Add payment">
        </form>
        <table id="payments-list">
            <thead>
                <tr>
                    <th>Amount</th>
                    <th>Payment</th>
                </tr>
            </thead>
            <c:forEach items="${applicationScope.listOfPayments}" var="item">
                <tr>
                    <td>${item.amount}</td>
                    <td>${item.dateFormatted}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>
