<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bioskop.model.Projekcija" %><%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>Registruj admina - Cinematic</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body class="bg-dark">


<jsp:include page="../../navbar.jsp"/>
<div class="container">
  <div class="row  bg-light p-3">
    <div class="col">
      <form action="/admin/korisnici/registruj_admina" method="post" class="form-horizontal" role="form">
        <h2>Registracija</h2>
        <div class="form-group">
          <label for="userName" class="col-sm-3 control-label">Korisničko ime</label>
          <div class="col-sm-9">
            <input type="text" id="userName" name="username" placeholder="Korisnicko ime" class="form-control">
          </div>
        </div>
        <div class="form-group">
          <label for="firstName" class="col-sm-3 control-label">Ime</label>
          <div class="col-sm-9">
            <input type="text" id="firstName" name="ime" placeholder="Ime" class="form-control">
          </div>
        </div>
        <div class="form-group">
          <label for="lastName" class="col-sm-3 control-label">Prezime</label>
          <div class="col-sm-9">
            <input type="text" id="lastName" name="prezime" placeholder="Prezime" class="form-control">
          </div>
        </div>
        <div class="form-group">
          <label for="email" class="col-sm-3 control-label">Email</label>
          <div class="col-sm-9">
            <input type="email" id="email" placeholder="Email" class="form-control" name= "email">
          </div>
        </div>
        <div class="form-group">
          <label for="birthDate" class="col-sm-3 control-label">Datum rođenja</label>
          <div class="col-sm-9">
            <input type="date" id="birthDate" name="datrodj" class="form-control">
          </div>
        </div>
        <div class="form-group">
          <label for="phoneNumber" class="col-sm-3 control-label">Broj telefona</label>
          <div class="col-sm-9">
            <input type="phoneNumber" id="phoneNumber" name="brtel" placeholder="Broj telefona" class="form-control">
          </div>
        </div>
        <button type="submit" class="btn btn-primary btn-block">Registruj admina</button>
      </form>

    </div>
  </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

<script src="../../js/login.js"></script>

</body>
</html>