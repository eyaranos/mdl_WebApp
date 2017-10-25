<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<c:if test="${!empty sessionScope.sessionUtilisateur}">
    <%@ include file="/resources/login_header.jsp"%>
</c:if>


<c:if test="${empty sessionScope.sessionUtilisateur}">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="#">MDL</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <a class="nav-item nav-link active" href="/">Home <span class="sr-only">(current)</span></a>
                <a class="nav-item nav-link" href="/connection">Connexion</a>
                <a class="nav-item nav-link" href="/inscription">Inscription</a>
            </div>
        </div>
    </nav>
</c:if>
