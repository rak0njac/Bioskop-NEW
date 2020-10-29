<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Repertoar - Cinematic</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body class="bg-dark">


<jsp:include page="navbar.jsp"/>
<div class="container">
    <div class="row  bg-light p-3">
        <div class="col-8">
            <h1>Moje rezervacije</h1>
            <table class="table">
                <thead>
                <tr>
                    <th>
                        Film
                    </th>
                    <th>
                        Datum i vreme
                    </th>
                    <th>
                        Tip sedišta
                    </th>
                    <th>
                        Cena
                    </th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.rezervacije}" var="r">
                        <tr>
                            <td>${r.projekcija.film.naziv}</td>
                            <td>${r.projekcija.vremePocetka}</td>
                            <td>${r.sediste.tip}</td>
                            <td>${r.cena}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col-4">
            <h1>Moji podaci</h1>
            <div class="alert alert-danger" role="alert">
                Molimo vas da podatke poput imena i prezimena kao i datuma rodjenja menjate iskljucivo ako ste pogresili prilikom registracije. U suprotnom, rezervacije ce vam biti odbijene prilikom uvida u licnu kartu.
            </div>
                Korisnicko ime: ${sessionScope.user.username}<br>
                Broj poena: ${sessionScope.user.brPoena}<br>
            <br>
            <h5>Izmena podataka:</h5>
            <form action="/profil" method="post" class="form-horizontal">
                <input type="hidden" name="idKorisnik" value="${sessionScope.user.idKorisnik}">
                <div class="form-group">
                    <label for="imePrezime" class="control-label">Ime i prezime:</label>
                    <div class="col-sm-9">
                        <input type="text" id="imePrezime" name="imePrezime" value="${sessionScope.user.imePrezime}" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label for="datRodj" class="control-label">Datum rodjenja:</label>
                    <div class="col-sm-9">
                        <input type="date" id="datRodj" name="datRodj" value="${sessionScope.user.datRodj}" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label for="email" class="control-label">E-mail:</label>
                    <div class="col-sm-9">
                        <input type="email" id="email" name="email" value="${sessionScope.user.email}" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label for="brTel" class="control-label">Broj telefona:</label>
                    <div class="col-sm-9">

                    <input type="text" id="brTel" name="brTel" value="${sessionScope.user.brTel}" class="form-control">
                </div>
                </div>
                <button type="submit" class="btn btn-primary btn-block" name="submit" value="izmena-podataka">Sacuvaj</button>
                <h5 class="mt-3">Promena lozinke</h5>
                <div class="form-group">
                    <label for="oldPass" class="control-label">Stara lozinka:</label>
                    <div class="col-sm-9">
                        <input type="text" id="oldPass" name="oldPass" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label for="newPass" class="control-label">Nova lozinka:</label>
                    <div class="col-sm-9">
                        <input type="text" id="newPass" name="newPass" class="form-control">
                    </div>
                </div>
                <button type="submit" class="btn btn-primary btn-block" name="submit" value="izmena-lozinke">Sacuvaj</button>
            </form>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<script src="/js/login.js"></script>

</body>
</html>