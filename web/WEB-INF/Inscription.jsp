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
        <title>Inscription</title>
            <%@ include file="/resources/includeCss.html"%>
    </head>

    <body>
        <div class="wrap-all">
            <div class="container-fluid">
                <%@ include file="/resources/header.jsp"%>
            </div>

            <form id="form-inscription" method="post" action="inscription">
                <fieldset>
                    <legend>Inscription</legend>
                    <p>Vous pouvez vous inscrire via ce formulaire.</p>

                    <label for="email">Adresse email <span class="requis">*</span></label>
                    <input id="email" name="email" value="<c:out value="${utilisateur.email}"/>" size="20" maxlength="60" />
                    <span class="erreur">${form.erreurs['email']}</span>
                    <br />

                    <label for="mdp">Mot de passe <span class="requis">*</span></label>
                    <input type="password" id="mdp" name="mdp" value="" size="20" maxlength="20" />
                    <span class="erreur">${form.erreurs['mdp']}</span>
                    <br />

                    <label for="confirmation">Confirmation du mdp <span class="requis">*</span></label>
                    <input type="password" id="confirmation" name="confirmation" value="" size="20" maxlength="20" />
                    <span class="erreur">${form.erreurs['confirmation']}</span>
                    <br />

                    <label for="nom">Nom </label>
                    <input id="nom" name="nom" value="<c:out value="${param.nom}"/>" size="20" maxlength="20" />
                    <br />

                    <label for="prenom">Prenom</label>
                    <input id="prenom" name="prenom" value="<c:out value="${param.prenom}"/>" size="20" maxlength="20" />
                    <br />

                    <label for="adresse">Adresse</label>
                        <input id="adresse" name="adresse" value="<c:out value="${param.adresse}"/>" size="20" maxlength="20" />
                    <br />

                    <label for="code_postal">Zip</label>
                    <input id="code_postal" name="code_postal" value="<c:out value="${param.code_postal}"/>" size="20" maxlength="20" />
                    <br />

                    <label for="ville">Ville</label>
                    <input id="ville" name="ville" value="<c:out value="${param.ville}"/>" size="20" maxlength="20" />
                    <br />

                    <label for="pays">Pays</label>
                    <input id="pays" name="pays" value="<c:out value="${param.pays}"/>" size="20" maxlength="20" />
                    <br />

                    <label for="num_tel">Telephone</label>
                    <input id="num_tel" name="tel" value="<c:out value="${param.num_tel}"/>" size="20" maxlength="20" />
                    <br />

                    <label for="date_naissance">Date de naissance</label>
                    <input id="date_naissance" name="date_naissance" value="<c:out value="${param.date_naissance}"/>" size="20" maxlength="20" />
                    <br />

                    <input type="submit" value="Inscription" class="sansLabel" />
                    <br />

                    <p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
                </fieldset>
            </form>

        </div>
        <%@ include file="/resources/includeJS.html"%>
    </body>
</html>
