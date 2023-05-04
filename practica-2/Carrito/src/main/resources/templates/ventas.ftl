<!DOCTYPE html>
<html lang="">
<thead>
<title>Ventas</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="/css/productoCard.css">
<link rel="stylesheet" type="text/css" href="/css/navbar.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</thead>
<tbody>

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
                        <a class="nav-link active text-white" href="/carrito/comprar">Comprar</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active text-success" href="/carrito/ventasproductos">Productos Vendidos</a>
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

<!--Listado de Ventas realizadas (Facturas Basicamente)-->

<div class="bg-image text-center lg-auto"
     style="background-image: linear-gradient(rgba(44,85,69,0.9),rgba(0,0,0,0.9)), url(/img/animeShop.gif) ; margin: 4.5rem 0rem 1.5rem 0rem; border-radius: 0px; background-repeat: no-repeat; background-size: 100%; background-position-y: 30%">
    <div class="py-4">
        <h1 class="display-2 fw-bold text-success">${usuario.usuario[0]}<span style="color:white;">${usuario.usuario?substring(1)}</span></h1>
        <div class="col-lg-6 mx-auto">
            <p class="fs-5 mb-4 text-white">Usuario Administrador</p>
        </div>
    </div>
</div>


<div class="container-fluid">
    <#if datos?has_content>
    <div style="margin: 5%">
    <h1 class="fw-bold text-success">V<span style="color:black;">entas realizadas</span></h1>
    <#list datos as venta>
        <h3 class="text-secondary mt-4"><span class="text-success">${venta.id} | </span>${venta.nombreCliente}</h3>
        <h3 class="text-success">${venta.fechaCompra?string("dd/MM/yyyy")}</h3>
        <div class="container-fluid" style="padding: 0">
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>Titulo</th>
                        <th>Autor</th>
                        <th>Cantidad</th>
                        <th>Precio Unitario</th>
                        <th>Precio</th>
                        <th>Portada</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list venta.carritoItems as carritoItem>
                        <tr>
                            <td>${carritoItem.getProducto().titulo}</td>
                            <td>${carritoItem.getProducto().autor}</td>
                            <td>${carritoItem.cantidad}</td>
                            <td>${carritoItem.getProducto().precio}$</td>
                            <td>${carritoItem.getPrecioTotal()}$</td>
                            <td><img src="${carritoItem.getProducto().urlFoto}" style="height: 8rem; width: 6rem; object-fit: cover"></td>
                        </tr>
                    </#list>
                    </tbody>
                    <tfoot>
                    <tr style="font-size: 1.2rem; font-weight: bold; color: seagreen">
                        <td colspan="3"></td>
                        <td colspan="4">Precio total: ${venta.getPrecioTotal()}$</td>
                    </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    </#list>
    </div>

    <#else>
        <div class="container text-center">
            <img class="img-fluid" src="/img/animeCorrer.gif" style="object-fit: scale-down; height: 15rem;">
            <div class="mt-3">
                <h1 class="fw-bold text-success">N<span style="color:black;">o hay ventas!</span></h1>
                <h4 class="text-secondary" style="margin: 0 5rem;">Agrega nuevos mangas interesantes o baja algunos precios</h4>
                <a href="/carrito/administrar" class="btn btn-success mt-2">Administrar Productos</a>
            </div>
        </div>
    </#if>
</div>
</tbody>
</html>