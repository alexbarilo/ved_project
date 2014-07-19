
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
</head>
<body>
    <form id="invoice-edit" action="ServletInvoiceTable" method="post" enctype="application/x-www-form-urlencoded">
    <c:forEach items="${applicationScope.listOfInvoices}" var="items">
        <c:if test="${items.number == currentInvoice}">
        Invoice number<input type="text" name="number" value="${items.number}"><br>
        Invoice Date<input type="date" name="date" value="${items.date}"><br>
        Invoice Amount<input type="text" name="amount" value="${items.amount}"><br>
        <input type="hidden" name="jsp-identifier" value="invoice-edit">
        </c:if>
    </c:forEach>
    <input type="hidden" name="invoice-to-update" value=${currentInvoice}>
    <input type="submit" value="Save changes">
    </form>
</body>
</html>
