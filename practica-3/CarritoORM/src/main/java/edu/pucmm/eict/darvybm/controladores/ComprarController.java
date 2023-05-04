package edu.pucmm.eict.darvybm.controladores;

import edu.pucmm.eict.darvybm.modelos.CarritoCompra;
import edu.pucmm.eict.darvybm.modelos.Producto;
import edu.pucmm.eict.darvybm.modelos.Usuario;
import edu.pucmm.eict.darvybm.services.ProductoServices;
import io.javalin.Javalin;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.get;

public class ComprarController {
    Javalin app;

    public ComprarController(Javalin app) {
        this.app = app;
    }

    public void rutas() {
        app.get("carrito/comprar", ctx -> {
            Usuario usuario = ctx.sessionAttribute("usuario");
            CarritoCompra carritoCompra = ctx.sessionAttribute("carritoCompra");
            Map<String, Object> modelo = new HashMap<>();
            if (usuario != null) {
                if (carritoCompra == null) {
                    carritoCompra = new CarritoCompra(usuario);
                }
            }
            int pageNumber = 1;
            if (ctx.queryParam("page") != null) {
                pageNumber = Integer.parseInt(ctx.queryParam("page"));
            }
            int pageSize = 10;
            ArrayList<Producto> productos = ProductoServices.getInstance().getPaginatedProductos(pageNumber, pageSize);
            int totalPages = (int) Math.ceil((double) ProductoServices.getInstance().getProductos().size() / pageSize);
            ctx.sessionAttribute("carritoCompra", carritoCompra);
            modelo.put("datos", productos);
            modelo.put("usuario", usuario);
            modelo.put("carrito", carritoCompra);
            modelo.put("totalPages", totalPages);
            modelo.put("pageNumber", pageNumber);
            ctx.render("/templates/comprar.ftl", modelo);
        });
    }
}
