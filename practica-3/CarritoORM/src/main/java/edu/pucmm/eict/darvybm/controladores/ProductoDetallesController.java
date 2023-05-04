package edu.pucmm.eict.darvybm.controladores;

import edu.pucmm.eict.darvybm.modelos.CarritoCompra;
import edu.pucmm.eict.darvybm.modelos.ComentarioProducto;
import edu.pucmm.eict.darvybm.modelos.Producto;
import edu.pucmm.eict.darvybm.modelos.Usuario;
import edu.pucmm.eict.darvybm.services.ProductoServices;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.get;

public class ProductoDetallesController {
    Javalin app;

    public ProductoDetallesController(Javalin app) {
        this.app = app;
    }

    public void rutas() {
        app.get("carrito/productoDetalles/{id}", ctx -> {
            Usuario usuario = ctx.sessionAttribute("usuario");
            CarritoCompra carritoCompra = ctx.sessionAttribute("carritoCompra");
            Map<String, Object> modelo = new HashMap<>();
            Producto producto = ProductoServices.getInstance().getProductoById(ctx.pathParam("id"));
            ctx.sessionAttribute("carritoCompra", carritoCompra);
            modelo.put("usuario", usuario);
            modelo.put("carrito", carritoCompra);
            modelo.put("producto", producto);
            modelo.put("comentarios", producto.getComentarios());
            ctx.render("/templates/productoDetalles.ftl", modelo);
        });

        app.post("carrito/productoDetalles/comentario/add/{id}", ctx -> {
            String id = ctx.pathParam("id");
            String comentario = ctx.formParam("comentario");
            Producto producto = ProductoServices.getInstance().getProductoById(id);
            Usuario usuario = ctx.sessionAttribute("usuario");
            producto.getComentarios().add(new ComentarioProducto(comentario, producto, usuario.getUsuario()));
            ProductoServices.getInstance().modificarProducto(producto);
            ctx.redirect("/carrito/productoDetalles/" + producto.getId());
        });

        app.before("carrito/productoDetalles/comentario/eliminar/{id}", ctx -> {
            Usuario usuario = ctx.sessionAttribute("usuario");
            if (usuario == null || !usuario.isAdmin()) {
                ctx.redirect("/carrito/401");
            }
        });
        app.get("carrito/productoDetalles/comentario/eliminar/{id}", ctx -> {
            String id = ctx.pathParam("id");
            ProductoServices.getInstance().eliminarComentarioProductoById(id);
            ctx.redirect("/carrito/productoDetalles/" + ProductoServices.getInstance().getProductoByComentarioId(id).getId());
        });
    }
}
