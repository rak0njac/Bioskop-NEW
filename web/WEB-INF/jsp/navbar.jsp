<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-expand-lg navbar-light navbar-fixed-top bg-light mb-5">
    <a class="navbar-brand" href="/index.html">
        <img src="/res/img/connect-c-logo.png" width="30" height="30" class="d-inline-block align-top" alt="">
        <b>Cinematic</b>
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse ml-5" id="navbarText">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/index.html">REPERTOAR</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/onama">O NAMA</a>
            </li>
            <c:if test="${sessionScope.user.tip=='Admin'}">
                <li class="nav-item">
                    <a class="nav-link" href="/admin">ADMIN PANEL</a>
                </li>
            </c:if>
            <c:if test="${sessionScope.user.tip=='Radnik'}">
                <li class="nav-item">
                    <a class="nav-link" href="/radnik">RADNIK PANEL</a>
                </li>
            </c:if>
        </ul>
        <span class="nav-item">
                    <c:if test="${sessionScope.user != null}">
                        <a class="nav-link mr-3" href="/profil">MOJ PROFIL</a>
                    </c:if>
        </span>
        <span class="nav-item">
                <c:choose>
                    <c:when test="${sessionScope.user != null}">
                        <button id="btn-logout" type="button" class="btn btn-dark">
                            ODJAVA
                        </button>
                    </c:when>
                    <c:otherwise>
                        <button id="btn-login" type="button" class="btn btn-dark">
                            PRIJAVA
                        </button>
                    </c:otherwise>
                </c:choose>
        </span>
    </div>
</nav>

<div class="modal fade" tabindex="-1" role="dialog" id="modalLogin">
    <form action="/login" method="post">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Prijava</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                    <label class="control-label" for="login-user">Korisnicko ime:</label>
                    <input class="form-control" type="text" id="login-user" name="user"/>
                    <label class="control-label" for="login-pass">Lozinka:</label>
                    <input class="form-control" type="password" id="login-pass" name="pass"/>

            </div>
            <div class="modal-footer">
                <input type="submit" value="Prijava" class="btn btn-primary"/>
                <button id="btn-registracija" type="button" class="btn btn-secondary">Registracija</button>
            </div>
        </div>
    </div>
    </form>
</div>
