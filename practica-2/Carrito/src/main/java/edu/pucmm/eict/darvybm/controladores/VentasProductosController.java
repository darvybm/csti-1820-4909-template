package edu.pucmm.eict.darvybm.controladores;

import edu.pucmm.eict.darvybm.Carrito;
import edu.pucmm.eict.darvybm.modelos.CarritoCompra;
import edu.pucmm.eict.darvybm.modelos.Usuario;
import edu.pucmm.eict.darvybm.modelos.Ventas;
import io.javalin.Javalin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class VentasProductosController {
    Javalin app;

    public VentasProductosController(Javalin app) {
        this.app = app;
    }

    public void rutas() {
        app.routes(()-> {
            path("carrito/ventasproductos", () -> {

                before("/", ctx -> {
                    Usuario usuario = ctx.sessionAttribute("usuario");
                    if (usuario == null || !usuario.isAdmin()){
                        ctx.redirect("/carrito/401");
                    }
                });
                get("/", ctx -> {
                    ArrayList<Ventas> ventas = Carrito.getInstance().getVentas();
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("datos", ventas);
                    modelo.put("usuario", ctx.sessionAttribute("usuario"));
                    modelo.put("carrito", ctx.sessionAttribute("carritoCompra"));
                    ctx.render("/templates/ventas.ftl", modelo);
                });
            });
        });
    }
}
