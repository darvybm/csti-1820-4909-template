<#ftl encoding='UTF-8'>
<!DOCTYPE html>
<html>
<thead>
<title>Administrar</title>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
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
                        <a class="nav-link active text-white" href="/carrito/comprar">Comprar</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active text-white" href="/carrito/ventasproductos">Productos Vendidos</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle text-success" href="#" data-bs-toggle="dropdown" aria-expanded="false">Administrar</a>
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

<!--Listado de productos (Mangas)-->

<div class="container-fluid bg-trasparent container-maxW" style="position: relative; margin-top: 8rem;">
    <a href="/carrito/administrar/producto/crear" class="btn btn-success" style=" margin-bottom: 2rem;">
        <i class="fa fa-plus mx-2"></i> Agregar producto
    </a>
    <h1 class="fw-bold text-success">P<span style="color:white;">roductos</span></h1>
    <div class="row row-cols-2 row-cols-xs-2 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-3">
        <#list datos as producto>
            <#if producto.eliminado = false>
                <div class="col">
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
                            <div class="card-scroll">
                                <h5 class="card-title">${producto.titulo}.</h5>
                                <p class="card-title">${producto.descripcion}.</p>
                            </div>
                            <h5 class="price-hp mt-1">${producto.precio}$</h5>
                            <div class="d-flex align-items-center justify-content-between">
                                <div class="d-flex justify-content-between">
                                    <a href="/carrito/administrar/producto/ver/${producto.id}" class="btn btn-success mx-1"><i
                                                class="fa fa-eye fa-lg"></i></a>
                                    <a href="/carrito/administrar/producto/modificar/${producto.id}" class="btn btn-primary mx-1"><i
                                                class="fa fa-refresh fa-lg"></i></a>
                                    <a href="/carrito/administrar/producto/eliminar/${producto.id}" class="btn btn-danger mx-1"><i
                                                class="fa fa-trash fa-lg"></i></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </#if>
        </#list>
    </div>
</div>
<#if !datos?has_content>
    <div class="container text-center" style="margin-top: 4rem">
        <img class="img-fluid" src="/img/animeCorrer.gif" style="object-fit: scale-down; height: 15rem;">
        <div class="mt-3">
            <h1 class="fw-bold text-success">No hay Mangas!</h1>
            <h4 class="text-secondary" style="margin: 0 5rem;">Agrega un nuevo manga</h4>
            <a href="/carrito/administrar/producto/crear" class="btn btn-success mt-2">Agregar!</a>
        </div>
    </div>
</#if>
</body>
<script>
    $('.carousel').carousel({
        interval: 2000,
    })
</script>
</html>