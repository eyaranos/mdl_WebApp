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
                        <td><form class="form-repa-terminer" action="/work/reparateur/liste"> <input type="checkbox" name="checkbox-repa-terminated" value="${velo.id}" class="checkbox-repa-trigger" <c:if test="${!(velo.reparateur.id == sessionScope.sessionReparateur.id)}">disabled </c:if></form></td>
                    </c:if>

                    <c:if test="${velo.type == 'terminated'}">
                        <td><form class="form-repa-encours"> <input type="checkbox" disabled></form></td>
                        <td><b>${velo.reparateur.nom}</b> a réparé ce velo </td>
                    </c:if>

                    <c:if test="${empty velo.reparateur}">
                        <td><form class="form-repa-encours" action="/work/reparateur/liste"> <input type="checkbox" name="checkbox-repa-pending" value="${velo.id}" class="checkbox-repa-trigger"></form></td>
                        <td><form class="form-repa-terminer" action="/work/reparateur/liste"><input type="checkbox" name="checkbox-repa-terminated" value="${velo.id}" class="checkbox-repa-trigger"></form></td>
                    </c:if>

                    <td><textarea name="commentaire_repa" class="commentaire_repa" cols="30" rows="5" placeholder="Commentaire : "></textarea></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <c:if test="${empty liste_velos}">
        <p>Pas de vélo à réparé...</p>
    </c:if>

    <p style="color:#f8cb76" id="erreur-repa"></p>
    <p style="color:#0b8b10" id="ok-repa"></p>


</div>
<%@ include file="/resources/includeJS.html" %>

<script>
    $('.checkbox-repa-trigger').on('change', function(){

        var id_velo = $(this).val();
        var keyForServlet = $(this).attr("name");
        var commentaire = $('.commentaire_repa').val();
       var ajax = $.post('/work/reparateur/liste',{idVelo:id_velo, type:keyForServlet, commentaire:commentaire}, function(responseText){
           $('#ok-repa').text(responseText);
           setTimeout(function() {
               window.location.href = "";
           }, 2000);
        })
            .fail(function(){
                $('#erreur-repa').text(responseText);
            });
    });

    $(".commentaire_repa").on('change', function(){

        if ($(this).val().length == 0){
            $('input[type=text], textarea').prop('disabled', false);
        }
        else{
            //textarea not empty
            $('input[type=text], textarea').not(this).prop('disabled', true);
        }
    })
</script>

</body>
</html>