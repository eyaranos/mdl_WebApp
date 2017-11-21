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
    <title>Nos formules</title>
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
            <th>Intitulé</th>
            <c:forEach var="formule" items="${listeFormules}" >
                <th scope="col">${formule.nom}</th>
            </c:forEach>
        </tr>
        </thead>
        <tbody>
            <tr>
                <td>Prix</td>
                <c:forEach var="formule" items="${listeFormules}" >
                    <td>${formule.prix}</td>
                </c:forEach>
            </tr>
            <tr>
                <td>Durée</td>
                <c:forEach var="formule" items="${listeFormules}" >
                    <td>${formule.duree}</td>
                </c:forEach>
            </tr>

            <tr>
                <td>Votre choix</td>
                <c:forEach var="formule" items="${listeFormules}" >
                    <td><form class="form-formule" action="/profile/formules"><input type="checkbox" value="${formule.id}" class="checkbox-formule" name="checkbox-formule"></form></td>
                </c:forEach>
            </tr>
        </tbody>
    </table>

   <button id="btn-choose-formule" type="submit" class="btn btn-primary" disabled <c:if test="${noCreditCard != null}">disabled</c:if> >Choisir</button>
    <c:if test="${noCreditCard != null}">
        <p style="color:darkred;">${noCreditCard}</p>
        <p>Vous pouvez en ajouter une en suivant ce <a href="/profile/add/creditCard">lien</a> </p>
    </c:if>
    <p id="warning-text" style="color:#f8c74b;display:none;">Vous devez séléctionner une formule avant...</p>

    <c:if test="${erreur-abonnement != 0}">
        <p style="color:#f8c74b;">${erreur-abonnement}</p>
    </c:if>

</div>
<%@ include file="/resources/includeJS.html" %>
<script>

    $( document ).ready( function() {

        $(".checkbox-formule").on('change', function(){
            //si un checkbox est coché on autorise le boutton + only one checkbox checked process
            if($(this).is(':checked')){
            $("#btn-choose-formule").prop("disabled", false);

            $('input:checkbox').not(this).prop('checked', false);
            }
            else{
                $("#btn-choose-formule").prop("disabled", true);
            }
        });

        /*TODO : display alert message. NOT WORKING because disabled button doesnt take the hover effect*/
        $("#btn-wrapper").hover(function(){
           if($("#btn-choose-formule").is(":disabled")){

                $("#warning-text").show();
           }
           else{
               $("#warning-text").hide();
           }
        });

        $('#btn-choose-formule').on('click', function(){

            var id_formule =  $('input[name="checkbox-formule"]:checked').val();
            $.post('/profile/formules',{id_formule:id_formule}, function(){})
        });
    });

</script>


</body>
</html>