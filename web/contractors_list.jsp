<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title></title>
    <link href="/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
    <div id="list-div">

        <form id="contractors-datainput" action="ServletContractorsList" method="POST" enctype="application/x-www-form-urlencoded">
            Contractor's name<br>
            <input type="text" name="contractors-name"><br>
            Contract No<br>
            <input type="text" name="contract-number">
            <input type="submit" name="action" value="Add">
        </form>

        <form id="select-contractors" action="ServletContractorsList" method="GET" enctype="application/x-www-form-urlencoded">
            <input type="submit" name="action" value="Del contractor">
            <input type="submit" name="action" value="Get report"><br>
            <select name="contractor-selection" id="select" size="25">
                <c:forEach items="${applicationScope.listOfContractors}" var="list">
                <option>${list.contractorsName}</option>
                </c:forEach>
            </select>
        </form>

    </div>
</body>
</html>
