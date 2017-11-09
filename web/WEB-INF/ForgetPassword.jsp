<%--
  User: Enzo
  Date: 09-11-17
  Time: 17:00
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Mot de passe oublié ?</title>
    <%@ include file="/resources/includeCss.html" %>
</head>

<body>
<div class="wrap-all">
    <div class="container-fluid">
        <%@ include file="/resources/header.jsp" %>
    </div>


    <div class="container-fluid">
        <div class="row">
            <div class="col-md-6" style="margin: 100px 0 0 30%;">

                <form  class="form-horizontal" method="post" action="forgetPassword">
                    <div class="form-group">
                        <label for="email" class="col-sm-2 control-label">Email</label>
                        <div class="col-sm-10">
                            <input type="email" id="email" class="form-control" name="email" size="20" maxlength="60" />
                            <span class="erreur">${erreurs['email']}</span>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-primary">Envoyer</button>
                        </div>
                        <p class="${empty erreurs ? 'succes' : 'erreur'}">${resultat}</p>
                    </div>
                </form>
            </div>
        </div>
    </div>


</div>
<%@ include file="/resources/includeJS.html" %>
</body>
</html>