<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Repertoar - Cinematic</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body class="bg-dark">


<jsp:include page="../navbar.jsp"/>
<div class="container">
    <div class="row  bg-light p-3">
        <div class="col">
            <c:forEach items="${requestScope.projekcije}" var='p'>
                <h1>${p.film.naziv} - ${p.vremePocetka.toLocalDate()} ${p.vremePocetka.toLocalTime()}</h1>
                <div class="row">
                    <div class="col">
                        <h3>Rezervisano</h3>
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Korisnik</th>
                                    <th>Tip</th>
                                    <th>Cena</th>
                                    <th>Potvrdi</th>
                                    <th>Otkazi</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${p.karte}" var="k">
                                    <c:if test="${k.korisnik != null}">
                                        <tr>
                                            <td>${k.korisnik.username}</td>
                                            <td>${k.sediste.tip}</td>
                                            <td>${k.cena}</td>
                                            <td><button class="btn btn-primary">Potvrdi</button></td>
                                            <td><button class="btn btn-danger">Otkazi</button></td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </tbody>
                        </table>
                        <button class="btn btn-danger">OBRISI SVE</button>
                    </div>
                    <div class="col">
                        <h3>Raspolozivo</h3>
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Tip</th>
                                <th>Cena</th>
                                <th>Stampaj</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${p.karte}" var="k">
                                <c:if test="${k.korisnik == null}">
                                    <tr>
                                        <td>${k.sediste.tip}</td>
                                        <td>${k.cena}</td>
                                        <td><button class="btn btn-primary">Stampaj</button></td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <hr>
            </c:forEach>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>


</body>
</html>