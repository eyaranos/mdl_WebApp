<%--
  User: Enzo
  Date: 06-11-17
  Time: 16:35
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Liste velos</title>
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
            <th scope="col">ID</th>
            <th scope="col">Dommages</th>
            <th scope="col">Prise en charge</th>
            <th scope="col">Terminer</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach var="velo" items="${liste_velos}" >
                <tr>
                    <th scope="row">${velo.id}</th>
                    <td>
                    <c:forEach var="dommage" items="${velo.listeDommages}" >
                        ${dommage.nom}<br>
                    </c:forEach>
                    </td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    .


</div>
<%@ include file="/resources/includeJS.html" %>
</body>
</html>