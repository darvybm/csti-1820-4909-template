package edu.pucmm.eict.darvybm.controladores;

import edu.pucmm.eict.darvybm.modelos.Usuario;
import edu.pucmm.eict.darvybm.services.UsuarioServices;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;
import org.jasypt.util.text.StrongTextEncryptor;

public class LoginController {
    Javalin app;
    public static enum TipoUsuario {
        ADMINISTRADOR, CLIENTE, NO_AUTENTICADO
    }
    private StrongTextEncryptor encriptador;

    public LoginController(Javalin app) {
        this.app = app;
    }

    public Boolean Autenticar(String usuarioID, String password){
        Usuario user = UsuarioServices.getInstance().getUsuarioByUsuario(usuarioID);
        if(user != null){
            encriptador = new StrongTextEncryptor();
            encriptador.setPassword("PWeb");
            if(encriptador.decrypt(user.getPassword()).equals(password)){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return null;
        }
    }

    public void rutas() {
        app.routes(()->{
            app.before(ctx -> {

                //Verificacion de Cookie

                String cookie = ctx.cookie("TiendaManga");

                if (cookie != null){
                    String[] cookieParts = cookie.split("-");
                    if(cookieParts != null && cookieParts.length == 2)
                    {
                        if (cookieParts[0].equals("null") || cookieParts[1].equals("null"))
                        {
                            ctx.sessionAttribute("usuario", null);
                            ctx.sessionAttribute("tipoUsuario", TipoUsuario.NO_AUTENTICADO);
                            return;
                        }
                        else {
                            String usuario = cookieParts[0];
                            String password = cookieParts[1];
                            encriptador = new StrongTextEncryptor();
                            encriptador.setPassword("PWeb");
                            try {
                                usuario = encriptador.decrypt(usuario);
                                password = encriptador.decrypt(password);
                                if (Autenticar(usuario, password) != null && Autenticar(usuario, password)){
                                    ctx.sessionAttribute("STiendaManga", cookie);
                                }
                                else {
                                    //Invalidando la cookie
                                    ctx.cookie("TiendaManga", null);
                                }

                            } catch (Exception e) {
                                //Invalidando la cookie
                                ctx.cookie("TiendaManga", null);
                            }
                        }
                    }
                }
                else {
                    System.out.println("No hay cookie");
                }

                //Verificacion de Session

                String sessionManga = ctx.sessionAttribute("STiendaManga");
                if (sessionManga == null){
                    ctx.sessionAttribute("STiendaManga", "null");
                }
                else {
                    String[] sessionParts = sessionManga.split("-");
                    if(sessionParts != null && sessionParts.length == 2)
                    {
                        if (sessionParts[0].equals("null") || sessionParts[1].equals("null"))
                        {
                            ctx.sessionAttribute("usuario", null);
                            ctx.sessionAttribute("tipoUsuario", TipoUsuario.NO_AUTENTICADO);
                            return;
                        }
                        else {
                            String usuario = sessionParts[0];
                            String password = sessionParts[1];
                            encriptador = new StrongTextEncryptor();
                            encriptador.setPassword("PWeb");
                            try {
                                usuario = encriptador.decrypt(usuario);
                                password = encriptador.decrypt(password);
                                if (Autenticar(usuario, password) != null && Autenticar(usuario, password)){
                                    Usuario user = UsuarioServices.getInstance().getUsuarioByUsuario(usuario);
                                    ctx.sessionAttribute("usuario", user);
                                    if (user.isAdmin()){
                                        ctx.sessionAttribute("tipoUsuario", TipoUsuario.ADMINISTRADOR);
                                    }
                                    else {
                                        ctx.sessionAttribute("tipoUsuario", TipoUsuario.CLIENTE);
                                    }
                                }
                                else {
                                    ctx.sessionAttribute("usuario", null);
                                    ctx.sessionAttribute("tipoUsuario", TipoUsuario.NO_AUTENTICADO);
                                }

                            } catch (Exception e) {
                                ctx.sessionAttribute("usuario", null);
                                ctx.sessionAttribute("tipoUsuario", TipoUsuario.NO_AUTENTICADO);
                                return;
                            }
                        }
                    }
                }



            });


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
                    ctx.cookie("TiendaManga", "null",1);
                    ctx.sessionAttribute("tipoUsuario", TipoUsuario.NO_AUTENTICADO);
                    ctx.sessionAttribute("STiendaManga", null);
                    ctx.sessionAttribute("carritoCompra", null);
                    ctx.redirect("/");
                });
                post("/iniciarSesion", ctx -> {
                    //obteniendo la información enviada.
                    String usuario = ctx.formParam("usuario");
                    String password = ctx.formParam("password");
                    Boolean recordar = false;
                    recordar = ctx.formParam("recordar") != null;

                    if (usuario.equalsIgnoreCase("") || password.equalsIgnoreCase("")){
                        Map<String, Object> modelo = new HashMap<>();
                        modelo.put("vista", "Iniciar Sesion");
                        modelo.put("action", "/carrito/login/iniciarSesion");
                        modelo.put("mensaje", "Debes llenar todos los campos");
                        modelo.put("error", true);
                        ctx.render("/templates/login.ftl", modelo);
                        return;
                    }

                    Usuario user = UsuarioServices.getInstance().getUsuarioByUsuario(usuario);

                    if(Autenticar(usuario, password) != null && Autenticar(usuario, password)){
                        String encryptedUsername = encriptador.encrypt(user.getUsuario());
                        String encryptedPassword = encriptador.encrypt(password);
                        if (recordar)
                        {
                            ctx.cookie("TiendaManga", encryptedUsername + "-" +encryptedPassword, 60*60*24*7);
                            ctx.sessionAttribute("STiendaManga", encryptedUsername + "-" +encryptedPassword);
                        }

                        else
                        {
                            ctx.sessionAttribute("STiendaManga", encryptedUsername + "-" +encryptedPassword);
                        }

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

                    if (UsuarioServices.getInstance().getUsuarioByUsuario(usuario) != null){
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
                        encriptador = new StrongTextEncryptor();
                        encriptador.setPassword("PWeb");
                        String tokenUsuario = encriptador.encrypt(user.getUsuario());
                        String tokenPassword = encriptador.encrypt(user.getPassword());
                        UsuarioServices.getInstance().addUsuario(user);
                        UsuarioServices.getInstance().getUsuarioByUsuario(usuario);
                        if (ctx.formParam("recordar") != null)
                        {
                            ctx.cookie("TiendaManga", tokenUsuario + "-" +tokenPassword, 60*60*24*7);
                            ctx.sessionAttribute("STiendaManga", tokenUsuario + "-" +tokenPassword);
                        }

                        else
                            ctx.sessionAttribute("STiendaManga", tokenUsuario + "-" +tokenPassword);
                        ctx.sessionAttribute("usuario", user);
                        ctx.redirect("/");
                    }
                });
            });
        });
    }

}
