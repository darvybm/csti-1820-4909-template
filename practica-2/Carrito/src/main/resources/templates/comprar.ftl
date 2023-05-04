<!DOCTYPE html>
<html>
<thead>
<title>Comprar</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="/css/productoCard.css">
<link rel="stylesheet" type="text/css" href="/css/navbar.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</thead>
<body>

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
                </ul>
                    <div class="d-flex mt-3 me-0 mt-lg-0">
                        <a href="/carrito/login/iniciarSesion" class="btn btn-outline-success me-2">Iniciar Sesion</a>
                        <a href="/carrito/login/registrarse" class="btn btn-success">Registrarse</a>
                    </div>
                </#if>
            </div>
        </div>
    </div>
</nav>


<!--Banner para presentar la página (En este caso con temática de tienda de Manga)-->

<div class="bg-image text-center"
     style="background-image:linear-gradient(rgba(0,0,0,0.8),rgba(0,0,0,0.8)), url(../img/mangaHeader.jpg); margin-top: 4.5rem">
    <div class="py-4">
        <h1 class="display-2 fw-bold text-success">Animanga <span style="color:white;">House</span></h1>
        <div class="col-lg-6 mx-auto">
            <p class="fs-5 mb-4 text-white">Encuentra todo tipo de Mangas en Animanga House</p>
        </div>
    </div>
</div>
<!--Listado de productos (Mangas)-->

<div class="container-fluid bg-trasparent container-maxW"
     style="position: relative; padding: 0.5rem 1.2rem; margin-top: 2rem">
    <h1 class="fw-bold text-success">P<span style="color:black;">roductos</span></h1>
    <div class="row row-cols-2 row-cols-xs-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-3">
        <#list datos?values as producto>
            <div class="col">
                <div class="card shadow-sm"><img src="${producto.urlFoto}" class="card-img-top" alt="...">
                    <div class="label-top shadow-sm">${producto.autor}</div>
                    <div class="card-body">
                        <div class="card-scroll">
                            <h5 class="card-title">${producto.titulo}.</h5>
                            <p class="card-title">${producto.descripcion}.</p>
                        </div>

                        <h5 class="price-hp mt-1">${producto.precio}$</h5>
                        <div class="d-flex align-items-center justify-content-between">
                            <form class="input-group" method="post" action="/carrito/carritoCompra/add/${producto.id}">
                                <input type="number" name="cantidad" class="form-control" value="1" min="1" max="100" aria-label="Cant">
                                <input type="submit" class="btn btn-success", value="Agregar">
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</div>
<#if !datos?has_content>
    <div class="container text-center" style="margin-top: 4rem">
        <img class="img-fluid" src="/img/animeCafe.gif" style="object-fit: scale-down; height: 15rem;">
        <div class="mt-3">
            <h1 class="fw-bold text-success">No hay Mangas!</h1>
            <h4 class="text-secondary" style="margin: 0 5rem;">Vuelve pronto...</h4>
        </div>
    </div>
</#if>
</body>
</html>