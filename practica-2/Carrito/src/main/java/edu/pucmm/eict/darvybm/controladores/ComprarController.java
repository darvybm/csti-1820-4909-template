package edu.pucmm.eict.darvybm.controladores;

import edu.pucmm.eict.darvybm.Carrito;
import edu.pucmm.eict.darvybm.modelos.CarritoCompra;
import edu.pucmm.eict.darvybm.modelos.Usuario;
import io.javalin.Javalin;

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
            ctx.sessionAttribute("carritoCompra", carritoCompra);
            modelo.put("datos", Carrito.getInstance().getProductos());
            modelo.put("usuario", usuario);
            modelo.put("carrito", carritoCompra);
            ctx.render("/templates/comprar.ftl", modelo);
        });
    }

}
