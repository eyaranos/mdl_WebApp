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
            <div class="col-md-12">
                <ul class="nav nav-tabs">
                    <li role ="presentation" class="active"><a data-toggle="tab" href="#profile">Profile</a></li>
                    <li role ="presentation"><a data-toggle="tab" href="#trips">Trips</a></li>
                    <li role ="presentation"><a data-toggle="tab" href="#wallet">Wallet</a></li>
                    <li role ="presentation"><a data-toggle="tab" href="#settings">Settings</a></li>
                </ul>

                <div class="tab-content">
                    <div id="profile" class="tab-pane fade in active">
                        <h3>Profile</h3>

                        <form id="form-profile" method="post" action="">
                            <fieldset>
                                <p>Vous pouvez modifier vos informations personnelles via ce formulaire.</p>

                                <label for="email">Adresse email <span class="requis">*</span></label>
                                <input id="email" name="email" value="<c:out value="${utilisateur.email}"/>" size="20" maxlength="60" />
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

                                <label for="num_tel">Telephone</label>
                                <input id="num_tel" name="tel" value="<c:out value="${utilisateur.numTel}"/>" size="20" maxlength="20" />
                                <br />

                                <%--<label for="date_naissance">Date de naissance</label>
                                <input id="date_naissance" name="date_naissance" value="<c:out value="${utilisateur.date_naissance}"/>" size="20" maxlength="20" />
                                <br />--%>

                                <input type="submit" value="Modifier" class="sansLabel" />
                                <br />

                                <p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
                            </fieldset>
                        </form>

                    </div>
                    <div id="trips" class="tab-pane fade">
                        <h3>My Trips</h3>
                        <p>Some content in trips.</p>
                    </div>
                    <div id="wallet" class="tab-pane fade">
                        <h3>My wallet</h3>
                        <p>Some content in wallet.</p>
                    </div>
                    <div id="settings" class="tab-pane fade">
                        <h3>Settings</h3>
                        <p>Some content in settings.</p>
                    </div>
                </div>
            </div>
        </div>
</div>
<%@ include file="/resources/includeJS.html" %>
</body>
</html>