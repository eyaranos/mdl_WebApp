<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<c:if test="${!empty sessionScope.sessionUtilisateur}">
    <%@ include file="/resources/login_header.jsp"%>
</c:if>

<c:if test="${!empty sessionScope.sessionReparateur}">
    <%@ include file="/resources/login_header.jsp"%>
</c:if>


<c:if test="${empty sessionScope.sessionUtilisateur and empty sessionScope.sessionReparateur}">
    <nav class="navbar navbar-inverse fixed-top bg-inverse navCustom">
        <a class="navbar-brand" href="#">Velib </a>

            <ul class="nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link active" href="/">Home <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/connection">Connexion</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/inscription">Inscription</a>
                </li>
            </ul>

    </nav>
</c:if>

