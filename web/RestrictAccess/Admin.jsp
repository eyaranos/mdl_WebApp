<%--
  User: Enzo
  Date: 10-12-17
  Time: 19:30
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Admin</title>
    <%@ include file="/resources/includeCss.html" %>
</head>

<body>
<div class="wrap-all">
    <div class="container-fluid">
        <%@ include file="/resources/header.jsp" %>
    </div>

    <div>
        <form>
            <a href="" name="startAlgo">Démarrer l'algorithme</a><br>
            <a href="" name="getTrajet">GET trajets</a><br>
            <a href="" name="getNbClient">GET nombre de clients</a><br>
            <a href="" name="getNbClientAbo">GET nombre de clients avec un abonnement</a><br>
            <a href="" name="getEmploye">GET les employes</a><br>
            <a href="" name="getAllVeloForStation">GET velos dans station</a><br>
        </form>
    </div>

    <div>
        <h5>Reponse : </h5>
        <p id="reponse"></p>
    </div>


</div>
<%@ include file="/resources/includeJS.html" %>

<script>
    $('form a').on('click', function(event){

        event.preventDefault();
        var name =  $(this).attr('name');
        $.post('/admin',{name:name}, function(response) {
            $('#reponse').text(response);
        })
    });
</script>
</body>
</html>