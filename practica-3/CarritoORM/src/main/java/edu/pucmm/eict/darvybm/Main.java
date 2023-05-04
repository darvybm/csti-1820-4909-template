package edu.pucmm.eict.darvybm;

import edu.pucmm.eict.darvybm.controladores.*;
import edu.pucmm.eict.darvybm.modelos.*;
import edu.pucmm.eict.darvybm.services.BookGenerator;
import edu.pucmm.eict.darvybm.services.BootstrapServices;
import edu.pucmm.eict.darvybm.services.ProductoServices;
import edu.pucmm.eict.darvybm.services.UsuarioServices;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.JavalinRenderer;
import io.javalin.rendering.template.JavalinFreemarker;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {

    private static String modoConexion = "";
    public static void main(String[] args) throws Exception {

        if(args.length >= 1){
            modoConexion = args[0];
            System.out.println("Modo de Operacion: "+modoConexion);
        }

        //Iniciando la base de datos.
        if(modoConexion.isEmpty()) {
            BootstrapServices.getInstancia().init();
        }

        //Agregando el motor de plantilla FreeMarker (Sujeto a posibles cambios)
        JavalinRenderer.register(new JavalinFreemarker(), ".ftl");

        //Agregando usuario Administrador
        Usuario usuario = UsuarioServices.getInstance().getUsuarioByUsuario("admin");
        if (usuario == null){
            UsuarioServices.getInstance().addUsuario(new Usuario("admin", "admin", "admin", true));
        }


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
        new ProductoDetallesController(app).rutas();

        app.get("/", ctx -> {
            ctx.redirect("/carrito");
        });

        //Peticiones para la sección del carrito (Pruebas)
        app.routes(() -> {
            path("/carrito", () -> {

                //Redirigiendo a todos los usuarios automáticamente a la sección de compras (Incluso si no está registrado)
                get("/", ctx -> {
                    ctx.redirect("/carrito/comprar?page=1");
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

        if (ProductoServices.getInstance().getProductos().isEmpty()){

            Producto producto = new Producto("Naruto - Tomo II", "Naruto es una serie de manga escrita e ilustrada por Masashi Kishimoto. La obra narra la historia de un ninja adolescente llamado Naruto Uzumaki, quien aspira a convertirse en Hokage, lider de su aldea, con el propisito de ser reconocido como alguien importante dentro de la aldea y entre sus companeros.", "Masashi kishimoto", 1200.45);

            producto.getFotosBase64().add(new FotoProducto(urlToBase64("https://i.blogs.es/bc1dd2/naruto/1366_2000.png"), "PNG", producto));
            producto.getFotosBase64().add(new FotoProducto(urlToBase64("https://upload.wikimedia.org/wikipedia/en/9/94/NarutoCoverTankobon1.jpg"), "PNG", producto));
            ProductoServices.getInstance().addProducto(producto);

            //Generando libros de pruebas

            BookGenerator bookGenerator = new BookGenerator();

            bookGenerator.generate30Books();

        }



    }

    //Solo para usar imagenes web
    public static String urlToBase64(String imageUrl) throws Exception {
        URL url = new URL(imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        InputStream inputStream = connection.getInputStream();
        byte[] bytes = inputStream.readAllBytes();
        String base64 = Base64.getEncoder().encodeToString(bytes);
        return base64;
    }
}