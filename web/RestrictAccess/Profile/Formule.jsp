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
                    <td>${formule.prix}€</td>
                </c:forEach>
            </tr>
            <tr>
                <td>Durée</td>
                <c:forEach var="formule" items="${listeFormules}" >
                    <td>${formule.duree}</td>
                </c:forEach>
            </tr>

            <tr>
                <td>Description</td>
                <c:forEach var="formule" items="${listeFormules}" >
                    <td>${formule.description}</td>
                </c:forEach>
            </tr>

            <tr>
                <td>Votre choix</td>
                <c:forEach var="formule" items="${listeFormules}" >
                    <td><form class="form-formule" action="/profile/formules"><input type="checkbox" value="${formule.id}" <c:if test="${noCreditCard != null}">disabled</c:if> class="checkbox-formule" name="checkbox-formule"></form></td>
                </c:forEach>
            </tr>
        </tbody>
    </table>

    <c:if test="${erreur_abonnement == 1}">
        <button id="btn-choose-formule" type="button" class="btn btn-primary" data-toggle="modal" data-target="#modal-confirm" disabled>Choisir</button>
    </c:if>

    <c:if test="${erreur_abonnement == 0}">
        <p style="color:#f8c74b;">Vous avez déjà un abonnement en cours...</p>
    </c:if>

    <c:if test="${noCreditCard != null}">
        <p style="color:darkred;">${noCreditCard}</p>
        <p>Vous pouvez en ajouter une en suivant ce <a href="/profile/add/creditCard">lien</a> </p>
    </c:if>
    <p id="warning-text" style="color:#f8c74b;display:none;">Vous devez séléctionner une formule avant...</p>



    <!-- Modal -->
    <div class="modal fade" id="modal-confirm" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Confirmer votre abonnement</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                   Veuillez confirmer votre choix. Vous allez etre débité et vous pourrez accéder à notre service immédiatemment !
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="button" id="btn-choose-formule-confirm" class="btn btn-danger">Confirmer</button>
                </div>
            </div>
        </div>
    </div>



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

        $('#btn-choose-formule-confirm').on('click', function(){

            var id_formule =  $('input[name="checkbox-formule"]:checked').val();
            $.post('/profile/formules',{id_formule:id_formule}, function(response){

               if ((response.localeCompare("400")) == 0){

                   $('.modal-body').replaceWith("<div style=\"color:darkred;\" class=\"modal-body\">\n" +
                       "Une erreur est survenue... Veuillez actualiser la page et réessayer." + "</div>");
               }
               else{
                   $('.modal-body').replaceWith("<div style=\"color:#0b8b10;\" class=\"modal-body\">\n" +
                       "Merci pour votre achat ! Cliquez <a href=\"localhost:8081/profile/abonnements\">ici</a> pour vous redirigez vers vos abonnements </div>");

                   $('#btn-choose-formule-confirm').removeClass("btn-danger");
                   $('#btn-choose-formule-confirm').addClass("btn-success");
               }


            })
        });
    });

</script>


</body>
</html>