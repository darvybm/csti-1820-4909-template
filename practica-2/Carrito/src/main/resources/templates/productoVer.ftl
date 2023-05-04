<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<thead>
<title>${vista}</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="/css/productoCard.css">
<link rel="stylesheet" type="text/css" href="/css/navbar.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</thead>
<body class="bg-image"
      style="background-image:linear-gradient(rgba(0,0,0,0.8),rgba(0,0,0,0.8)), url(/img/mangaHeader.jpg); object-fit: contain; margin-top:  0">

<!--Scrip para que pueda funcionar el desplegable de bootstrap-->

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous"></script>

<!--Implementación de un Navar (tomando los que ya trae bootstrap por defecto) con alguna que otra personalización-->

<nav class="navbar navbar-expand-lg navbar-dark bg-dark py-3 fixed-top" aria-label="Offcanvas navbar large">
    <div class="container-fluid">
        <a class="navbar-brand me-4 text-success fw-bold" href="/carrito">A<span style="color:white;">H</span></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="offcanvas" data-bs-target="#navbar"
                aria-controls="navbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="offcanvas offcanvas-end text-bg-dark" tabindex="-1" id="navbar" aria-labelledby="navbarLabel">
            <div class="offcanvas-header">
                <h4 class="offcanvas-title fw-bold" id="navbarLabel"><span class="text-success">Animanga</span> House
                </h4>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="offcanvas"
                        aria-label="Close"></button>
            </div>
            <div class="offcanvas-body">
                <ul class="navbar-nav justify-content-start flex-grow-1 pe-3">
                    <li class="nav-item">
                        <a class="nav-link active text-success" href="/carrito/comprar">Comprar</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active text-white" href="/carrito/ventasproductos">Productos Vendidos</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" data-bs-toggle="dropdown" aria-expanded="false">Administrar</a>
                        <ul class="dropdown-menu text-center">
                            <li class="dropdown-item-p">
                                <a class="nav-link active" style="color: black" href="/carrito/administrar/producto">Productos</a>
                            </li>
                            <li class="dropdown-item-p">
                                <a class="nav-link active" style="color: black" href="/carrito/administrar/usuario">Usuarios</a>
                            </li>
                        </ul>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active text-white" href="/carrito/carritoCompra">Carrito de Compras</a>
                    </li>
                    <li class="nav-item">
                        <div class="cart-icon mt-2">
                            <i class="fa fa-shopping-cart"></i>
                            <span class="cart-count"><#if carrito??>${carrito.getCantProductos()}<#else>0</#if></span>
                        </div>
                    </li>
                </ul>
                    <#if usuario??>
                    <div class="d-flex mt-3 me-0 mt-lg-0">
                        <a href="/carrito/login/cerrarSesion" class="btn btn-success">Cerrar Sesion</a>
                    </div>
                    <#else>
                    <div class="d-flex mt-3 me-0 mt-lg-0">
                        <a href="/carrito/login/iniciarSesion" class="btn btn-outline-success me-2">Iniciar Sesion</a>
                        <a href="/carrito/login/registrarse" class="btn btn-success">Registrarse</a>
                    </div>
                </#if>
            </div>
        </div>
    </div>
</nav>

<!--Formulario-->

<div style="margin: 5rem">
    <div class="container" style="margin-top: 8rem; padding: 2rem; background: #D3D3D3; border-radius: 10px; max-width: 50rem">
        <div class="row">
            <div class="col">
                <h1 class="fw-bold text-success">${vista[0]}<span style="color:black;">${vista?substring(1)}</span></h1>
                <form class="col" enctype="application/x-www-form-urlencoded" method="post" action="">
                    <div class="form-group">
                        <label for="Id">ID</label>
                        <input readonly="readonly" value="${id}" type="text" name="id" class="form-control" id="idForm">
                    </div>
                    <div class="row">
                        <div class="form-group col">
                            <label for="Titulo">Titulo</label>
                            <input readonly="readonly" type="text" name="titulo" value="${datos.titulo}"
                                   class="form-control" id="tituloForm">
                        </div>
                        <div class="form-group col">
                            <label for="Autor">Autor</label>
                            <input readonly="readonly" type="text" name="autor" value="${datos.autor}"
                                   class="form-control" id="autorForm">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="Descripcion">Descripcion</label>
                        <textarea readonly="readonly" name="descripcion" class="form-control"
                                  id="descripcionForm">${datos.descripcion}</textarea>
                    </div>
                    <div class="form-group">
                        <label for="Precio">Precio</label>
                        <input readonly="readonly" type="number" name="precio" step="0.01" value="${datos.precio?c}"
                               class="form-control" id="precioForm">
                    </div>
                    <div class="form-group">
                        <label for="UrlFoto">Url Foto de Portada</label>
                        <input readonly="readonly" type="url" name="urlFoto" value="${datos.urlFoto}"
                               class="form-control" id="urlFotoForm">
                    </div>

                    <!-- Los botones para la creación del producto (Manga) -->

                    <div style="margin-top: 1.2rem">
                        <a href="/carrito/administrar/producto" class="btn btn-danger">Cancelar</a>
                    </div>
                </form>
            </div>
            <div class="container col">
                <img style="object-fit: cover; height: 30rem; width: 20rem" src="${datos.urlFoto}" class="img-fluid">
            </div>
        </div>
    </div>
</div>
</body>
</html>