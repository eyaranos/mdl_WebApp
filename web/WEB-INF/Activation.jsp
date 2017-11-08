<%--
  User: Enzo
  Date: 08-11-17
  Time: 18:53
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Activation</title>
    <%@ include file="/resources/includeCss.html" %>
</head>

<body>
<div class="wrap-all">
    <div class="container-fluid">
        <%@ include file="/resources/header.jsp" %>
    </div>


    <c:out value="${resultActivation}"/>


</div>
<%@ include file="/resources/includeJS.html" %>
</body>
</html>