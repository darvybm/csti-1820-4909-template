package edu.pucmm.eict.darvybm.controladores;

import edu.pucmm.eict.darvybm.Carrito;
import edu.pucmm.eict.darvybm.modelos.Producto;
import edu.pucmm.eict.darvybm.modelos.Usuario;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class AdministrarController {
    Javalin app;

    public AdministrarController(Javalin app) {
        this.app = app;
    }

    public void rutas() {
        app.routes(() -> {
            path("/carrito/administrar", () -> {
                before(ctx -> {
                    Usuario usuario = ctx.sessionAttribute("usuario");
                    if (usuario == null || !usuario.isAdmin()) {
                        ctx.redirect("/carrito/401");
                    }
                });
                get("producto/", ctx -> {
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("datos", Carrito.getInstance().getProductos());
                    modelo.put("usuario", ctx.sessionAttribute("usuario"));
                    modelo.put("carrito", ctx.sessionAttribute("carritoCompra"));
                    ctx.render("/templates/administrarProductos.ftl", modelo);
                });
                get("usuario/", ctx -> {
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("datos", Carrito.getInstance().getUsuarios());
                    modelo.put("usuario", ctx.sessionAttribute("usuario"));
                    modelo.put("carrito", ctx.sessionAttribute("carritoCompra"));
                    ctx.render("/templates/administrarUsuarios.ftl", modelo);
                });
                get("usuario/cambiarAdmin/{usuario}", ctx -> {
                    String user = ctx.pathParam("usuario");
                    Usuario usuario = Carrito.getInstance().getUsuarioByUsuario(user);
                    usuario.setAdmin(!usuario.isAdmin());
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("datos", Carrito.getInstance().getUsuarios());
                    modelo.put("usuario", ctx.sessionAttribute("usuario"));
                    modelo.put("carrito", ctx.sessionAttribute("carritoCompra"));
                    ctx.render("/templates/administrarUsuarios.ftl", modelo);
                });
                get("producto/ver/{id}", ctx -> {
                    Producto producto = Carrito.getInstance().getProductoById(ctx.pathParam("id"));
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Ver Producto - " + producto.getTitulo());
                    modelo.put("vista", "Ver");
                    modelo.put("id", producto.getId());
                    modelo.put("datos", producto);
                    modelo.put("usuario", ctx.sessionAttribute("usuario"));
                    modelo.put("carrito", ctx.sessionAttribute("carritoCompra"));
                    ctx.render("/templates/productoVer.ftl", modelo);
                });
                get("producto/crear", ctx -> {
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Crear Producto");
                    modelo.put("vista", "Crear");
                    modelo.put("action", "/carrito/administrar/producto/crear");
                    modelo.put("id", Carrito.getIdProducto());
                    modelo.put("usuario", ctx.sessionAttribute("usuario"));
                    modelo.put("carrito", ctx.sessionAttribute("carritoCompra"));
                    ctx.render("/templates/productoCrearModificar.ftl", modelo);
                });
                get("producto/modificar/{id}", ctx -> {
                    Producto producto = Carrito.getInstance().getProductoById(ctx.pathParam("id"));
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Modificar Producto - " + producto.getTitulo());
                    modelo.put("vista", "Modificar");
                    modelo.put("action", "/carrito/administrar/producto/modificar");
                    modelo.put("id", producto.getId());
                    modelo.put("datos", producto);
                    modelo.put("usuario", ctx.sessionAttribute("usuario"));
                    modelo.put("carrito", ctx.sessionAttribute("carritoCompra"));
                    ctx.render("/templates/productoCrearModificar.ftl", modelo);
                });
                get("producto/eliminar/{id}", ctx -> {
                    Carrito.getInstance().eliminarProductoById(ctx.pathParam("id"));
                    System.out.print("ELIMINAR" + (ctx.pathParam("id")));
                    ctx.redirect("/carrito/administrar/producto");
                });
                post("producto/crear", ctx -> {
                    //obteniendo la información enviada.
                    String titulo = ctx.formParam("titulo");
                    String autor = ctx.formParam("autor");
                    String descripcion = ctx.formParam("descripcion");
                    String precio = ctx.formParam("precio");
                    String urlFoto = ctx.formParam("urlFoto");                    //
                    if (titulo.equalsIgnoreCase("") || autor.equalsIgnoreCase("") || descripcion.equalsIgnoreCase("") || precio.equalsIgnoreCase("") || urlFoto.equalsIgnoreCase("")){
                        Map<String, Object> modelo = new HashMap<>();
                        modelo.put("titulo", "Crear Producto");
                        modelo.put("vista", "Crear");
                        modelo.put("action", "/carrito/administrar/producto/crear");
                        modelo.put("id", Carrito.getIdProducto());
                        modelo.put("usuario", ctx.sessionAttribute("usuario"));
                        modelo.put("carrito", ctx.sessionAttribute("carritoCompra"));
                        modelo.put("error", "Todos los campos deben ser rellenados");
                        ctx.render("/templates/productoCrearModificar.ftl", modelo);
                        return;
                    }
                    Carrito.getInstance().addProducto(new Producto(Carrito.getIdProducto(), titulo, descripcion, autor, Double.parseDouble(precio), urlFoto));
                    ctx.redirect("/carrito/administrar/producto");
                });
                post("producto/modificar", ctx -> {
                    //obteniendo la información enviada.
                    String id = ctx.formParam("id");
                    String titulo = ctx.formParam("titulo");
                    String autor = ctx.formParam("autor");
                    String descripcion = ctx.formParam("descripcion");
                    double precio = Double.parseDouble(ctx.formParam("precio"));
                    String urlFoto = ctx.formParam("urlFoto");
                    Producto producto = new Producto(Integer.parseInt(id), titulo, descripcion, autor, precio, urlFoto);
                    Carrito.getInstance().modificarProductoById(id, producto);

                    Carrito.getInstance().addProducto(producto);
                    ctx.redirect("/carrito/administrar/producto");
                });
            });
        });
    }

}
