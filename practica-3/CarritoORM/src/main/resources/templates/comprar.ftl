<#ftl encoding='UTF-8'>
<!DOCTYPE html>
<html>
<thead>
<title>Comprar</title>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
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
        <#list datos as producto>
            <#if producto.eliminado = false>
                <div class="col">
                    <a href="/carrito/productoDetalles/${producto.id}">
                        <div class="card shadow-sm">
                            <div id="C-${producto.id}" class="carousel slide" data-bs-ride="carousel">
                                <div class="carousel-inner">
                                    <#list producto.fotosBase64 as imagen>
                                        <div class="carousel-item <#if imagen_index == 0> active</#if>">
                                            <img class="card-img-top" src="data:image/${imagen.type};base64,${imagen.foto}" alt="imagen-${imagen_index}">
                                        </div>
                                    </#list>
                                </div>
                                <a class="carousel-control-prev" data-bs-target="#C-${producto.id}" role="button" data-bs-slide="prev">
                                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                    <span class="visually-hidden">Anterior</span>
                                </a>
                                <a class="carousel-control-next" data-bs-target="#C-${producto.id}" role="button" data-bs-slide="next">
                                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                    <span class="visually-hidden">Siguiente</span>
                                </a>
                            </div>
                            <div class="label-top shadow-sm">${producto.autor}</div>
                            <div class="card-body">
                                <h5 class="card-title">${producto.titulo}.</h5>
                                    <p class="card-title">${producto.descripcion}.</p>
                                <h5 class="price-hp mt-1">${producto.precio}$</h5>
                            </div>
                        </div>
                    </a>
                </div>
            </#if>
        </#list>
    </div>
</div>
</br>
<#if totalPages gt 1>
    <nav aria-label="Page navigation">
        <ul class="pagination justify-content-center text-success">
            <li class="page-item<#if pageNumber?int == 1> disabled</#if>">
                <a class="page-link" href="/carrito/comprar?page=${pageNumber-1}" tabindex="-1">Anterior</a>
            </li>
            <#list 1..totalPages as page>
                <li class="page-item<#if page == pageNumber> active</#if>">
                    <a class="page-link" href="/carrito/comprar?page=${page}">${page}</a>
                </li>
            </#list>
            <li class="page-item<#if pageNumber == totalPages> disabled</#if>">
                <a class="page-link" href="/carrito/comprar?page=${pageNumber+1}" aria-disabled="">Siguiente</a>
            </li>
        </ul>
    </nav>
</#if>
<#if !datos?has_content>
    <div class="container text-center" style="margin-top: 4rem">
        <img class="img-fluid" src="/img/animeCafe.gif" style="object-fit: scale-down; height: 15rem;">
        <div class="mt-3">
            <h1 class="fw-bold text-success">No hay Mangas!</h1>
            <h4 class="text-secondary" style="margin: 0 5rem;">Vuelve pronto...</h4>
        </div>
    </div>
</#if>

<script>
    $('.carousel').carousel({
        interval: 2000,
    })
</script>
</body>
</html>