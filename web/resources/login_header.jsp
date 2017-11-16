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
            <li class="nav-item">
                <a class="nav-item nav-link" href="/profile">Profile</a>
            </li>
            <li class="nav-item">
                <a class="nav-item nav-link" href="/map">Maps</a>
            </li>
            <c:if test="${!empty sessionScope.sessionReparateur}">
                <li class="nav-item">
                    <a class="nav-item nav-link" href="/work/reparateur/liste">Liste velos</a>
                </li>
            </c:if>
            <li class="nav-item">
                <a class="nav-item nav-link" href="/deconnection">Deconnexion</a>
            </li>
        </ul>

</nav>
