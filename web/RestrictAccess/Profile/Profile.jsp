<%--
  User: Enzo
  Date: 29-10-17
  Time: 10:52
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Profile -  ${sessionScope.sessionUtilisateur.email}</title>
    <%@ include file="/resources/includeCss.html" %>
</head>

<body>
<div class="wrap-all">
    <div class="container-fluid">
        <%@ include file="/resources/header.jsp" %>
    </div>



    <div class="container-fluid">
        <div class="row">
            <div class="col-md-3">
                <%@ include file="/resources/profile_header.jsp" %>
            </div>

            <div class="col-md-6" id="profile-main-col">

                <h3>Profil</h3>

                <form id="form-profile" method="post" action="">
                    <fieldset>
                        <p>Vous pouvez modifier vos informations personnelles via ce formulaire.</p>

                        <label for="email">Adresse email <span class="requis">*</span></label>
                        <input id="email" name="email" disabled value="<c:out value="${utilisateur.email}"/>" size="20" maxlength="60" />
                        <input id="email" name="email" style="visibility:hidden;position:absolute;" value="<c:out value="${utilisateur.email}"/>" size="20" maxlength="60" />
                        <span class="erreur">${form.erreurs['email']}</span>
                        <br>
                        <label for="mdp">Mot de passe <span class="requis">*</span></label>
                        <input type="password" id="mdp" name="mdp" value="<c:out value="${utilisateur.mdp}"/>" size="20" maxlength="20" />
                        <span class="erreur">${form.erreurs['mdp']}</span>
                        <br />

                        <label for="confirmation">Confirmation du mdp <span class="requis">*</span></label>
                        <input type="password" id="confirmation" name="confirmation" value="<c:out value="${utilisateur.mdp}"/>" size="20" maxlength="20" />
                        <span class="erreur">${form.erreurs['confirmation']}</span>
                        <br />

                        <label for="nom">Nom </label>
                        <input id="nom" name="nom" value="<c:out value="${utilisateur.nom}"/>" size="20" maxlength="20" />
                        <br />

                        <label for="prenom">Prenom</label>
                        <input id="prenom" name="prenom" value="<c:out value="${utilisateur.prenom}"/>" size="20" maxlength="20" />
                        <br />

                        <label for="adresse">Adresse</label>
                        <input id="adresse" name="adresse" value="<c:out value="${utilisateur.adresse}"/>" size="20" maxlength="20" />
                        <br />

                        <label for="code_postal">Zip</label>
                        <input id="code_postal" name="code_postal" value="<c:out value="${utilisateur.codePostal}"/>" size="20" maxlength="20" />
                        <br />

                        <label for="ville">Ville</label>
                        <input id="ville" name="ville" value="<c:out value="${utilisateur.ville}"/>" size="20" maxlength="20" />
                        <br />

                        <label for="pays">Pays</label>
                        <input id="pays" name="pays" value="<c:out value="${utilisateur.pays}"/>" size="20" maxlength="20" />
                        <br />

                        <label style="visibility:hidden" for="num_tel">Telephone</label>
                        <input style="visibility:hidden" id="num_tel" name="tel" value="<c:out value="${utilisateur.num}"/>" size="20" maxlength="20" />
                        <br />

                        <input type="submit" value="Modifier" class="sansLabel" />
                        <br />
                        <p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>
    <%@ include file="/resources/includeJS.html" %>
</body>
</html>