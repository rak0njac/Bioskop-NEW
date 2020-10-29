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
            <h3>Brisanje multiplexa</h3>
            <table class="table">
                <thead>
                <tr>
                    <th>Naziv</th>
                    <th>Brisanje</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.multiplexi}" var="m">
                        <tr>
                            <form action="/admin/mplex/managemplex" method="post">
                                <input type="hidden" name="action" value="delete">
                                <input name="id" type="hidden" value="${m.idMultiplex}"/>
                                <td>${m.naziv}</td>
                                <td><input type="submit" class="btn btn-danger" value="Obrisi"></td>
                            </form>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <hr>
            <h3>Dodavanje novog multiplexa</h3>
            <div>
                <form id="frm-multiplex" action="/admin/mplex/managemplex" method="post">
                    <input type="hidden" name="action" value="add">
                    <label for="naziv-multiplex">Naziv multiplexa:</label>
                    <input class="form-control mb-2" id="naziv-multiplex" name="naziv" type="text">
                    <div id="sale" class="row">
                    </div>
                </form>
                <button id="btn-dodaj-salu" class="btn btn-primary">Dodaj salu</button><br>
                <button id="btn-dodaj-multiplex" class="btn btn-primary btn-lg mt-3">Dodaj multiplex</button>
                <span id="error-multiplex" style="color:red;"></span>
            </div>
        </div>

    </div>
</div>

<div class="modal fade" tabindex="-1" role="dialog" id="modal-dodaj-salu">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title"></h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <b>Tipovi sedista:</b><br>
                    <div id="sedista">
                        <div class="sediste-group mb-1">
                            Od <span class="sediste-od">1</span> do: <input type="number" min="2" value="2" class="sediste-do" style="width: 40px;">
                            Tip: <input type="text" class="tip-sedista"/>
                            <button style="display: none" class="btn btn-danger btn-ukloni-sediste">-</button>
                        </div>
                    </div>
                    <button class="btn btn-primary btn-dodaj-sediste">+</button>
                    <span id="error" style="color: #ff0000"></span>
                </div>
                <div class="modal-footer">
                    <button id="btn-dodaj-salu-submit" type="button" class="btn btn-primary">Gotovo</button>
                </div>
            </div>
        </div>
</div>


<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<script src="/js/login.js"></script>

<script>
    var defaultModal = "                        <div class=\"sediste-group mb-1\">\n" +
        "                            Od <span class=\"sediste-od\">1</span> do: <input type=\"number\" min=\"2\" value=\"2\" class=\"sediste-do\" style=\"width: 40px;\">\n" +
        "                            Tip: <input type=\"text\" class=\"tip-sedista\"/>\n" +
        "                            <button style=\"display: none\" class=\"btn btn-danger btn-ukloni-sediste\">-</button>\n" +
        "                        </div>"

    var brojSala = 0

    $("#btn-dodaj-salu").click(function () {
        $("#modal-dodaj-salu").modal()
        $(".modal-title").text("Sala broj " + Number(brojSala + 1))
        $("#sedista").html(defaultModal)
    })

    $(".btn-dodaj-sediste").click(function () {
        var newGroup = $(".sediste-group").last().clone()
        var sedisteDo = $(newGroup).find(".sediste-do").val()

        console.log(sedisteDo)

        $(newGroup).find(".sediste-od").text(Number(sedisteDo) + 1)
        $(newGroup).find(".sediste-do").val(Number(sedisteDo) + 1)
        $(newGroup).find(".sediste-do").prop("min", Number(sedisteDo) + 1)
        $(newGroup).find(".tip-sedista").val("")
        $(newGroup).find(".btn-ukloni-sediste").show()

        $(".sediste-do").prop("disabled", true)
        $(".btn-ukloni-sediste").hide()

        $("#sedista").append(newGroup)
    })

    $("#sedista").on("click", ".btn-ukloni-sediste", function(){
        $(this).parents(".sediste-group").remove()
        var lastGroup = $(".sediste-group").last()
        if($(".sediste-group").length !== 1)
        {
            $(lastGroup).find(".btn-ukloni-sediste").show()
            $(lastGroup).find(".sediste-do").prop("disabled", false)
        }
    });

    $("#btn-dodaj-salu-submit").click(function () {
        var ok = true;
        $(".tip-sedista").each(function () {
            if($(this).val() === ""){
                $("#error").text("Niste popunili sva polja!")
                ok = false;
            }
        })
        if(ok === true){
            brojSala++;
            var htmlStart = "       <div class=\"col kartica\">\n" +
                "                        <div style='min-height: 155px;' class=\"card\">\n" +
                "                            <div class=\"card-body\">\n" +
                "                                <h5 class=\"card-title\">Sala broj " + brojSala + "</h5>\n" +
                "                                <p class=\"card-text\">\n"
            var htmlMiddle = ""
            var htmlEnd = "                       </p>\n" +
                "                                <a href='javascript:void(0)' data-broj-sale='" + brojSala + "' class=\"btn btn-danger btn-ukloni-salu\">Ukloni</a>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                    </div>"
            var htmlForm = "<div class='div-input' data-broj-sale='" + brojSala + "'>"
            $(".sediste-group").each(function () {
                var tip = $(this).find(".tip-sedista").val()
                var sedisteOd = $(this).find(".sediste-od").text()
                var sedisteDo = $(this).find(".sediste-do").val()
                htmlMiddle += tip + " (" + sedisteOd + " - " + sedisteDo + ")<br>"

                for(var i = -1; i < sedisteDo - sedisteOd; i++)
                {
                    htmlForm += "<input type=\"hidden\" name=\"sediste" + brojSala + "[]\" value=\""+ tip + "\">\n"
                    //$("#frm-multiplex").append("<input type=\"hidden\" name=\"sediste" + brojSala + "[]\" value=\""+ tip + "\">\n")
                }
            })
            htmlForm += "<input type=\"hidden\" name=\"sala[]\" value=\""+ brojSala +"\">\n"
            //$("#frm-multiplex").append("<input type=\"hidden\" name=\"sala[]\" value=\""+ brojSala +"\">\n")
            htmlForm += "</div>"

            $(".btn-ukloni-salu").hide()

            $("#sale").append(htmlStart + htmlMiddle + htmlEnd)
            $("#sale").append(htmlForm)

            $("#modal-dodaj-salu").modal('hide')
        }
    })

    $("#sale").on("click", ".btn-ukloni-salu", function(){
        $(this).parents("div[class*='kartica']").remove()
        $(".kartica").find(".btn-ukloni-salu").last().show()
    });

    $("#btn-dodaj-multiplex").click(function () {
        if($("#naziv-multiplex").val() === ""){
            $("#error-multiplex").text("Morate uneti naziv multiplexa!")
        }
        else if($(".card").length === 0)
        {
            $("#error-multiplex").text("Morate dodati bar jednu salu!")
        }
        else $("#frm-multiplex").submit()
    })
</script>

</body>
</html>