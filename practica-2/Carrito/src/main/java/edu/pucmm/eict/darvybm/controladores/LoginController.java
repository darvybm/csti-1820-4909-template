package edu.pucmm.eict.darvybm.controladores;

import edu.pucmm.eict.darvybm.Carrito;
import edu.pucmm.eict.darvybm.modelos.CarritoCompra;
import edu.pucmm.eict.darvybm.modelos.Usuario;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class LoginController {
    Javalin app;

    public LoginController(Javalin app) {
        this.app = app;
    }

    public void rutas() {
        app.routes(()->{
            path("/carrito/login", () -> {
                get("/", ctx -> {
                    ctx.redirect("/carrito/login/iniciarSesion");
                });
                get("/iniciarSesion", ctx -> {
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("vista", "Iniciar Sesion");
                    modelo.put("action", "/carrito/login/iniciarSesion");
                    modelo.put("mensaje", "Inicia sesion ahora y disfruta de los mejores mangas en la puerta de tu casa!");
                    ctx.render("/templates/login.ftl", modelo);
                });
                get("/registrarse", ctx -> {
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("vista", "Registrarse");
                    modelo.put("action", "/carrito/login/registrarse");
                    modelo.put("mensaje", "Registrate ahora y disfruta de los mejores mangas en la puerta de tu casa!");
                    ctx.render("/templates/login.ftl", modelo);
                });
                get("/cerrarSesion", ctx -> {
                    ctx.sessionAttribute("usuario", null);
                    ctx.sessionAttribute("carritoCompra", null);
                    ctx.redirect("/");
                });
                post("/iniciarSesion", ctx -> {
                    //obteniendo la información enviada.
                    String usuario = ctx.formParam("usuario");
                    String password = ctx.formParam("password");
                    Usuario user = Carrito.getInstance().getUsuarioByUsuario(usuario);

                    if(user != null && user.getPassword().equals(password)){
                        ctx.sessionAttribute("usuario", user);
                        ctx.redirect("/");
                    }
                    else {
                        //Crear --No existe usuario
                        Map<String, Object> modelo = new HashMap<>();
                        modelo.put("vista", "Iniciar Sesion");
                        modelo.put("action", "/carrito/login/iniciarSesion");
                        modelo.put("mensaje", "No existe ese usuario, intenta otra vez o registrate");
                        modelo.put("error", true);
                        ctx.render("/templates/login.ftl", modelo);
                    }
                });
                post("/registrarse", ctx -> {
                    String nombre = ctx.formParam("nombre");
                    String usuario = ctx.formParam("usuario");
                    String password = ctx.formParam("password");
                    ctx.sessionAttribute("usuario", null);

                    if (Carrito.getInstance().getUsuarioByUsuario(usuario) != null){
                        Map<String, Object> modelo = new HashMap<>();
                        modelo.put("vista", "Registrarse");
                        modelo.put("action", "/carrito/login/registrarse");
                        modelo.put("mensaje", "Este nombre de usuario ya existe");
                        modelo.put("error", true);
                        ctx.render("/templates/login.ftl", modelo);
                        return;
                    }
                    else if (nombre.equalsIgnoreCase("") || usuario.equalsIgnoreCase("") || password.equalsIgnoreCase("")){
                        Map<String, Object> modelo = new HashMap<>();
                        modelo.put("vista", "Registrarse");
                        modelo.put("action", "/carrito/login/registrarse");
                        modelo.put("mensaje", "Debes de llenar todos los campos");
                        modelo.put("error", true);
                        ctx.render("/templates/login.ftl", modelo);
                        return;
                    }
                    else {
                        Usuario user = new Usuario(usuario, password, nombre, false);
                        Carrito.getInstance().addUsuario(user);
                        ctx.sessionAttribute("usuario", user);
                        ctx.redirect("/");
                    }
                });
            });
        });
    }

}
