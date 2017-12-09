<%--
  User: Enzo
  Date: 31-10-17
  Time: 15:55
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Mes trajets</title>
    <%@ include file="/resources/includeCss.html" %>
</head>

<body>
<div class="wrap-all">
    <div class="container-fluid">
        <%@ include file="/resources/header.jsp" %>
    </div>

    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Date de début</th>
            <th scope="col">Date de fin</th>
            <th scope="col">Durée</th>
            <th scope="col">Prix</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach var="tra" items="${listeTrajets}" >
            <tr>
                <th scope="row"></th>
                <td>${tra.dateDebut}</td>
                <td>${tra.dateFin}</td>
                <td>${tra.duree} min</td>
                <td>${tra.prix}€</td>
            </tr>
        </c:forEach>

        </tbody>
    </table>
    <c:if test="${empty listeTrajets}">
        <p>Vous n'avez pas encore effectué de trajet</p>
    </c:if>

</div>
<%@ include file="/resources/includeJS.html" %>
</body>
</html>