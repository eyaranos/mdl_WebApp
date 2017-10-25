<%--
  User: Enzo
  Date: 24-10-17
  Time: 18:17
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Profile</title>
    <%@ include file="/resources/includeCss.html" %>
</head>

<body>
<div class="wrap-all">
    <div class="container-fluid">
        <%@ include file="/resources/header.jsp" %>
    </div>


    <p>Vous êtes connecté(e) avec l'adresse ${sessionScope.sessionUtilisateur.mail}, vous avez bien accès à l'espace restreint.</p>


</div>
<%@ include file="/resources/includeJS.html" %>
</body>
</html>