package edu.pucmm.eict.darvybm;

import edu.pucmm.eict.darvybm.controladores.*;
import edu.pucmm.eict.darvybm.modelos.*;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.JavalinRenderer;
import io.javalin.rendering.template.JavalinFreemarker;

import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {

    private static final List<String> names = new ArrayList<>();

    public static void main(String[] args) {

        //Agregando el motor de plantilla FreeMarker (Sujeto a posibles cambios)
        JavalinRenderer.register(new JavalinFreemarker(), ".ftl");
        Carrito.getInstance().addProducto(new Producto(Carrito.getIdProducto(), "Naruto - Tomo II", "Naruto es una serie de manga escrita e ilustrada por Masashi Kishimoto. La obra narra la historia de un ninja adolescente llamado Naruto Uzumaki, quien aspira a convertirse en Hokage, lider de su aldea, con el propisito de ser reconocido como alguien importante dentro de la aldea y entre sus companeros.", "Masashi kishimoto", 1200.45, "https://upload.wikimedia.org/wikipedia/en/9/94/NarutoCoverTankobon1.jpg"));
        Carrito.getInstance().addProducto(new Producto(Carrito.getIdProducto(), "Naruto - Tomo I", "Naruto es una serie de manga escrita e ilustrada por Masashi Kishimoto. La obra narra la historia de un ninja adolescente llamado Naruto Uzumaki, quien aspira a convertirse en Hokage, lider de su aldea, con el propisito de ser reconocido como alguien importante dentro de la aldea y entre sus companeros.", "Masashi kishimoto", 1200.45, "https://img1.ak.crunchyroll.com/i/spire1/8201ee04e3484d2bd36689515ccda4c11567747365_full.jpg"));
        Carrito.getInstance().addProducto(new Producto(Carrito.getIdProducto(), "Naruto - Tomo III", "Naruto es una serie de manga escrita e ilustrada por Masashi Kishimoto. La obra narra la historia de un ninja adolescente llamado Naruto Uzumaki, quien aspira a convertirse en Hokage, lider de su aldea, con el propisito de ser reconocido como alguien importante dentro de la aldea y entre sus companeros.","Masashi kishimoto", 1200.45, "https://m.media-amazon.com/images/I/71U7DuusOdL._AC_UF1000,1000_QL80_.jpg"));
        Carrito.getInstance().addUsuario(new Usuario("admin", "admin", "admin", true));

        //Creando la app usando el Framework Javalin
        var app = Javalin.create(javalinConfig -> {
            javalinConfig.staticFiles.add(staticFileConfig -> {
                staticFileConfig.hostedPath = "/";
                staticFileConfig.directory = "/publico";
                staticFileConfig.location = Location.CLASSPATH;
            });
        }).start(7001);

        //Aplicando las rutas
        new ComprarController(app).rutas();
        new CarritoCompraController(app).rutas();
        new LoginController(app).rutas();
        new VentasProductosController(app).rutas();
        new AdministrarController(app).rutas();

        app.get("/", ctx -> {
            ctx.redirect("/carrito");
        });

        //Peticiones para la sección del carrito (Pruebas)
        app.routes(() -> {
            path("/carrito", () -> {

                //Redirigiendo a todos los usuarios automáticamente a la sección de compras (Incluso si no está registrado)
                get("/", ctx -> {
                    ctx.redirect("/carrito/comprar");
                });
                //En caso de que un usuario no tenga permisos
                get("/401", ctx -> {
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("usuario", ctx.sessionAttribute("usuario"));
                    modelo.put("carrito", ctx.sessionAttribute("carritoCompra"));
                    ctx.render("/templates/401.ftl", modelo);
                });
            });
        });
    }
}