<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bioskop.model.Projekcija" %><%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Repertoar - Cinematic</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body class="bg-dark">


<jsp:include page="../../navbar.jsp"/>
<div class="container">
    <div class="row  bg-light p-3">
        <div class="col">
            <h1>Admin panel</h1>
            <div class="alert alert-danger" role="alert">
                Mole se administratori da budu izuzetno pazljivi prilikom koriscenja administratorskih opcija. Pre izvrsavanja bilo koje radnje, dvaput proverite da li ste sigurni da istu uopste zelite da izvrsite, kao i da li su svi uneti podaci tacni. Svaka zloupotreba podleze krivicnoj odgovornosti.
            </div>
            <h3>Nova projekcija - odaberite film iz liste</h3>
            <table class="table">
                <thead>
                <tr>
                    <th>Naziv</th>
                    <th>Zanr</th>
                    <th>Reziser</th>
                    <th>Godina</th>
                    <th>Trajanje</th>
                    <th>Izmena</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.filmovi}" var="m">
                        <tr>
                            <form action="/admin/projections/addprojection" method="get">
                                <input name="id" type="hidden" value="${m.idFilm}"/>
                                <td>${m.naziv}</td>
                                <td>${m.zanr}</td>
                                <td>${m.reziser}</td>
                                <td>${m.godina}</td>
                                <td>${m.trajanje}</td>
                                <td><input type="submit" class="btn btn-primary" value="Nova projekcija"></td>
                            </form>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<script src="/js/login.js"></script>


</body>
</html>