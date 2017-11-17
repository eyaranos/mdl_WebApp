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
                    <%-- 3 cas différents pour l'affichage des 2 dernières cololnes --%>
                    <c:if test="${velo.type == 'pending'}">
                        <td><b>${velo.reparateur.nom}</b> est en train de réparer ce vélo</td>
                        <%--TODO : disable this checkbox for everybody but the one who started to fix it--%>
                        <td><form class="form-repa-terminer" action="/work/reparateur/liste"> <input type="checkbox" name="checkbox-repa-terminated" value="${velo.id}" class="checkbox-repa-trigger" <c:if test="${!(velo.reparateur.id == sessionScope.sessionReparateur.id)}">disabled </c:if></form></td>
                    </c:if>

                    <c:if test="${velo.type == 'terminated'}">
                        <td><form class="form-repa-encours"> <input type="checkbox" disabled></form></td>
                        <td><b>${velo.reparateur.nom}</b> a réparé ce velo <%--le ${casB.date_fin_rep}--%></td>
                    </c:if>

                    <c:if test="${empty velo.reparateur}">
                        <td><form class="form-repa-encours" action="/work/reparateur/liste"> <input type="checkbox" name="checkbox-repa-pending" value="${velo.id}" class="checkbox-repa-trigger"></form></td>
                        <td><form class="form-repa-terminer" action="/work/reparateur/liste"> <input type="checkbox" name="checkbox-repa-terminated" value="${velo.id}" class="checkbox-repa-trigger"></form></td>
                    </c:if>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <p style="color:#f8cb76" id="erreur-repa"></p>


</div>
<%@ include file="/resources/includeJS.html" %>
<script>
    $('.checkbox-repa-trigger').on('change', function(){

        var id_velo = $(this).val();
        var keyForServlet = $(this).attr("name");
        $.post('/work/reparateur/liste',{idVelo:id_velo, type:keyForServlet}, function(responseText){
            $('#erreur-repa').text(responseText);
            /*window.location.replace("http://localhost:8081/work/reparateur/liste");*/
        })
    });
</script>
</body>
</html>