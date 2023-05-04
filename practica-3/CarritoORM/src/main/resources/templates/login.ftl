<#ftl encoding='UTF-8'>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<thead>
<title>${vista}</title>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

</thead>
<body class="bg-image"
      style="background-image:linear-gradient(rgba(0,0,0,0.8),rgba(0,0,0,0.8)), url(/img/mangaHeader.jpg); object-fit: contain; margin-top:  0">

<!--Scrip para que pueda funcionar el desplegable de bootstrap-->

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous"></script>

<!--Formulario-->

<div style="margin: 5rem;">
    <div class="container"
         style="margin-top: 8rem; padding: 2rem; background: #D3D3D3; border-radius: 10px; max-width: 40rem">
        <h1 class="fw-bold text-success">${vista[0]}<span style="color:black;">${vista?substring(1)}</span></h1>
        <form enctype="application/x-www-form-urlencoded" method="post" action="${action}">
            <#if vista == "Iniciar Sesion">
                <div class="form-group">
                    <label for="Usuario">Usuario</label>
                    <input type="text" name="usuario" class="form-control" id="usuarioForm">
                </div>
                <div class="form-groupl">
                    <label for="Password">Password</label>
                    <input type="password" name="password" class="form-control" id="passwordForm">
                </div>
            <#else>
                <div class="form-group">
                    <label for="Nombre">Nombre</label>
                    <input type="text" name="nombre" class="form-control" id="nombreForm">
                </div>
                <div class="form-group">
                    <label for="Usuario">Usuario</label>
                    <input type="text" name="usuario" class="form-control" id="usuarioForm">
                </div>
                <div class="form-groupl">
                    <label for="Password">Password</label>
                    <input type="password" name="password" class="form-control" id="passwordForm">
                </div>
            </#if>
            </br>
            <div class="form-group form-check">
                <input type="checkbox" class="form-check-input" id="recordar" name="recordar">
                <label class="form-check-label" for="recordar">Recordar mi inicio de sesión</label>
            </div>
            <h4 style="font-weight: 500; margin-top: 0.8rem; font-size: 1rem; color: <#if error??>#dc3545<#else>#6c757d</#if>">${mensaje}</h4>
            <div style="margin-top: 1.2rem">
                <button type="submit" class="btn btn-success">${vista}</button>
                <a href="/" class="btn btn-danger">Cancelar</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>