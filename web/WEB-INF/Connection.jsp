<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 19-10-17
  Time: 16:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Accueil</title>
    <%@ include file="/resources/includeCss.html"%>
</head>

<body>
    <div class="wrap-all">
        <div class="container-fluid">
            <%@ include file="/resources/header.jsp"%>
        </div>

        <div class="container-fluid">
            <h3>Connexion</h3>
            <form id= "form-connection" class="form-horizontal" method="post" action="connection">
                <div class="form-group">
                    <label for="email" class="col-sm-2 control-label">Email</label>
                    <div class="col-sm-10">
                        <input type="email" id="email" class="form-control" name="email" value="<c:out value="${utilisateur.email}"/>" size="20" maxlength="60" />
                        <span class="erreur">${form.erreurs['email']}</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="motdepasse" class="col-sm-2 control-label">Password</label>
                    <div class="col-sm-10">
                        <input type="password" id="motdepasse" class="form-control" placeholder="Password" name="motdepasse" value="" size="20" maxlength="20" />
                        <span class="erreur">${form.erreurs['motdepasse']}</span>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <div class="checkbox">
                            <label>
                                <input type="checkbox"> Remember me
                            </label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default">Connexion</button>
                    </div>
                    <p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
                </div>
            </form>

        </div>

    </div>

    <%@ include file="/resources/includeJS.html"%>

</body>
</html>
