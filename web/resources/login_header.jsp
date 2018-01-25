<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-toggleable-md navbar-inverse fixed-top bg-inverse navCustom">
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <a class="navbar-brand" href="#">Velib</a>

        <ul class="nav mr-auto">
            <li class="nav-item">
                <a class="nav-item nav-link active" href="/">Home <span class="sr-only">(current)</span></a>
            </li>
            <c:if test="${!empty sessionScope.sessionUtilisateur}">
                <li class="nav-item">
                    <a class="nav-item nav-link" href="/profile">Profil</a>
                </li>
            </c:if>
            <c:if test="${!empty sessionScope.sessionUtilisateur}">
                <li class="nav-item">
                    <a class="nav-item nav-link" href="/map">Carte</a>
                </li>
            </c:if>
            <c:if test="${!empty sessionScope.sessionReparateur}">
                <li class="nav-item">
                    <a class="nav-item nav-link" href="/map/employe">Carte</a>
                </li>
            </c:if>
            <c:if test="${!empty sessionScope.sessionReparateur}">
                <li class="nav-item">
                    <a class="nav-item nav-link" href="/work/reparateur/liste">Liste des velos</a>
                </li>
            </c:if>
            <c:if test="${!empty sessionScope.sessionReparateur}">
                <li class="nav-item">
                    <a class="nav-item nav-link" href="/admin">Admin</a>
                </li>
            </c:if>
            <li class="nav-item">
                <a class="nav-item nav-link" href="/deconnection">Deconnexion</a>
            </li>
        </ul>
        <p>
            <c:if test="${!empty sessionScope.sessionReparateur}">
                ${sessionScope.sessionReparateur.email}
            </c:if>
            <c:if test="${!empty sessionScope.sessionUtilisateur}">
                ${sessionScope.sessionUtilisateur.email}
            </c:if>
        </p>
</nav>
