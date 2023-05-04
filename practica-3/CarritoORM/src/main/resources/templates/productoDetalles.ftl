<#ftl encoding='UTF-8'>
<!DOCTYPE html>
<html>
<thead>
<title>Detalles - ${producto.titulo}</title>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
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
     style="background-image:linear-gradient(rgba(0,0,0,0.8),rgba(0,0,0,0.8)), url(/img/mangaHeader.jpg); margin-top: 4.5rem">
    <div class="py-3">
        <h1 class="display-2 fw-bold text-success">Animanga <span style="color:white;">House</span></h1>
        <div class="col-lg-6 mx-auto">
            <p class="fs-5 mb-4 text-white">Encuentra todo tipo de Mangas en Animanga House</p>
        </div>
    </div>
</div>
<!--Listado de productos (Mangas)-->
<div class="container" style="margin-top: 2rem; padding: 0;">
    <div class="row flex-sm-row-reverse" style="margin: 0; padding: 0">
        <div class="col-12 col-lg-3 carousel-col">
            <div id="C-${producto.id}" class="carousel slide" data-bs-ride="carousel">
                <div class="carousel-inner">
                    <#list producto.fotosBase64 as imagen>
                        <div class="carousel-item <#if imagen_index == 0> active</#if>">
                            <img style="object-fit: cover; height: 20rem; width: 100%;" src="data:image/${imagen.type};base64,${imagen.foto}" alt="imagen-${imagen_index}">
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
            <div class="row mt-3">
                <div class="col-12">
                    <div class="d-flex flex-row flex-nowrap overflow-auto">
                        <#list producto.fotosBase64 as imagen>
                            <div class="p-2">
                                <a data-bs-target="#C-${producto.id}" data-bs-slide-to="${imagen_index}" href="#">
                                    <img style="object-fit: cover; height: 6rem; width: 8rem;" src="data:image/${imagen.type};base64,${imagen.foto}" alt="imagen-${imagen_index}">
                                </a>
                            </div>
                        </#list>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-12 col-lg-9">
            <div class="card">
                <div class="card-body">
                    <h4 class="card-title">${producto.titulo} - ${producto.autor}</h4>
                    <p class="card-text flex-grow-1">${producto.descripcion}</p>
                    <h5 class="card-title">Precio: ${producto.precio}$</h5>
                    <p class="card-text">Cantidad de reviews: Por asignar</p>
                    <div class="d-flex align-items-center justify-content-between">
                        <form class="input-group" method="post" action="/carrito/carritoCompra/add/${producto.id}">
                            <input type="number" name="cantidad" class="form-control" value="1" min="1" max="100" aria-label="Cant" required>
                            <input type="submit" class="btn btn-success", value="Agregar al Carrito">
                        </form>
                    </div>
                </div>
            </div>

            <#if usuario??>
                <div class="card my-4">
                    <div class="card-body">
                        <h5 class="card-title">Deja un comentario</h5>
                        <form method="post" action="/carrito/productoDetalles/comentario/add/${producto.id}">
                            <div class="form-group">
                                <textarea class="form-control" id="comentario" name="comentario" rows="3" required></textarea>
                            </div>
                            <button type="submit" class="btn btn-success mt-3">Enviar</button>
                        </form>
                    </div>
                </div>
            </#if>

            <#if comentarios?has_content>
                <h2 class="fw-bold text-success mt-3">C<span style="color:black;">omentarios</span></h2>
                <#list comentarios as c>
                    <#if c.oculto = false>
                        <div class="card my-4">
                        <div class="card-body">
                            <h4 class="card-title">${c.nombreUsuario}</h4>
                            <h7 style = "color: #8bc34a" class="card-title">${c.fecha?string("dd/MM/yyyy")}</h7>
                            <hr>
                            <div class="form-group">
                                <p class="" rows="3">${c.comentario}</p>
                            </div>
                            <#if usuario?? && usuario.admin = true>
                                <a href="/carrito/productoDetalles/comentario/eliminar/${c.id}">
                                    <div class="btn btn-danger">Eliminar Comentario Inapropiado</div>
                                </a>
                            </#if>
                        </div>
                    </div>
                    </#if>
                </#list>
                <#else>
                    <h2 class="fw-bold text-success mt-3">N<span style="color:black;">o hay comentarios</span></h2>
            </#if>

        </div>
    </div>
</div>

<script>
    $('.carousel').carousel({
        interval: 2000,
    })
</script>
</body>
</html>