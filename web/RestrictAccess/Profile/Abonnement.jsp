<%--
  User: Enzo
  Date: 19-11-17
  Time: 16:35
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Mes abonnements</title>
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
            <th scope="col">Nom de la formule</th>
            <th scope="col">Prix</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach var="abo" items="${listeAbos}" >
            <tr>
                <th scope="row"></th>
                <td>${abo.date_debut}</td>
                <td>${abo.date_fin}</td>
                <td>${abo.nom}</td>
                <td>${abo.prix}</td>
            </tr>
        </c:forEach>

        </tbody>
    </table>

    <%--TODO : check what to do if getAbonnementEnCours is not null (cfr servlet)--%>
    <button class="btn btn-primary"><a href="/profile/formules" style="color:white">Souscrire un nouvel abonnement !</a></button>

</div>
<%@ include file="/resources/includeJS.html" %>
</body>
</html>