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
            <div class="container">
                <div class="row">
                    <div class="col-md-5" id="inscr-top-col">
                        <p>Si vous ne souhaitez pas compléter un formulaire complet, vous pouvez réduire celui-ci grâce au bouton
                            ci-dessous. Un caution sera débitée dans ce cas.
                        </p>
                        <input type="submit" id="select_form_btn" value="Normale" class="btn btn-primary" />
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-5" id="inscr-main-col">
                        <form id="form-inscription-complet" method="post" action="inscription">
                            <fieldset>

                                <div class="form-group">
                                    <label for="email">Adresse email <span class="requis">*</span></label>
                                    <input id="email" name="email" class="form-control" value="<c:out value="${utilisateur.email}"/>" size="20" maxlength="60" />
                                    <span class="erreur">${form.erreurs['email']}</span>
                                </div>
                                <div class="form-group">
                                    <label for="mdp">Mot de passe <span class="requis">*</span></label>
                                    <input type="password" class="form-control"  id="mdp" name="mdp" value="" size="20" maxlength="20" />
                                    <span class="erreur">${form.erreurs['mdp']}</span>
                                </div>
                                <div class="form-group">
                                    <label for="confirmation">Confirmation du mdp <span class="requis">*</span></label>
                                    <input type="password" class="form-control"   id="confirmation" name="confirmation" value="" size="20" maxlength="20" />
                                    <span class="erreur">${form.erreurs['confirmation']}</span>
                                </div>
                                <div class="form-group">
                                    <label for="nom" class="not_anonyme">Nom </label>
                                    <input id="nom" class="not_anonyme form-control" name="nom" value="<c:out value="${param.nom}"/>" size="20" maxlength="20" />
                                </div>
                                <div class="form-group">
                                    <label for="prenom" class="not_anonyme">Prenom</label>
                                    <input id="prenom" class="not_anonyme form-control" name="prenom" value="<c:out value="${param.prenom}"/>" size="20" maxlength="20" />
                                </div>
                                <div class="form-group">
                                    <label for="adresse" class="not_anonyme">Adresse</label>
                                    <input id="adresse"  class="not_anonyme form-control" name="adresse" value="<c:out value="${param.adresse}"/>" size="20" maxlength="20" />
                                </div>
                                <div class="form-group">
                                    <label for="code_postal" class="not_anonyme">Zip</label>
                                    <input id="code_postal" class="not_anonyme form-control" name="code_postal" value="<c:out value="${param.code_postal}"/>" size="20" maxlength="20" />
                                </div>
                                <div class="form-group">
                                    <label for="ville" class="not_anonyme">Ville</label>
                                    <input id="ville" class="not_anonyme form-control"  name="ville" value="<c:out value="${param.ville}"/>" size="20" maxlength="20" />
                                </div>
                                <div class="form-group">
                                    <label for="pays" class="not_anonyme">Pays</label>
                                    <input id="pays" class="not_anonyme form-control" name="pays" value="<c:out value="${param.pays}"/>" size="20" maxlength="20" />
                                </div>
                                <div class="form-group">
                                    <label for="num_tel" class="not_anonyme">Telephone</label>
                                    <input id="num_tel" class="not_anonyme form-control" name="tel" value="<c:out value="${param.num_tel}"/>" size="20" maxlength="20" />
                                </div>
                                <div class="form-group">
                                    <label for="date_naissance" class="not_anonyme">Date de naissance</label>
                                    <input id="date_naissance" class="not_anonyme form-control" name="date_naissance" value="<c:out value="${param.date_naissance}"/>" size="20" maxlength="20" />
                                </div>

                                <input id="submit_form_inscription" class="btn btn-primary" type="submit" value="Inscription" class="sansLabel" />
                                <br />

                                <p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="/resources/includeJS.html"%>
    <script>
        $( document ).ready( function() {
            $("#select_form_btn").click(function(){
                $(".not_anonyme").fadeToggle("slow");
                if ($("#select_form_btn").val() == "Normale"){
                    $("#select_form_btn").val("Anonyme");
                    setCss($("#submit_form_inscription"));

                }
                else{
                    $("#select_form_btn").val("Normale");
                    unsetCss($("#submit_form_inscription"));
                }
            });
        });

        function setCss(btn){
            btn.css("position", "absolute");
            btn.css("margin-top", "0");
            btn.removeClass("btn-primary");
            btn.addClass("btn-secondary");
            $("#select_form_btn").removeClass("btn-primary");
            $("#select_form_btn").addClass("btn-secondary");
        }

        function unsetCss(btn){
            btn.css("position", "relative");
            btn.css("margin-top", "20px");
            btn.removeClass("btn-secondary");
            btn.addClass("btn-primary");
            $("#select_form_btn").removeClass("btn-secondary");
            $("#select_form_btn").addClass("btn-primary");
        }

    </script>
    </body>
</html>
