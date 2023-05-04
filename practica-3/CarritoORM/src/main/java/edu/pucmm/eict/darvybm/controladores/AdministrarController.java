package edu.pucmm.eict.darvybm.controladores;

import edu.pucmm.eict.darvybm.modelos.ComentarioProducto;
import edu.pucmm.eict.darvybm.modelos.FotoProducto;
import edu.pucmm.eict.darvybm.modelos.Producto;
import edu.pucmm.eict.darvybm.modelos.Usuario;
import edu.pucmm.eict.darvybm.services.ProductoServices;
import edu.pucmm.eict.darvybm.services.UsuarioServices;
import io.javalin.Javalin;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.*;

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
                    modelo.put("datos", ProductoServices.getInstance().getProductos());
                    modelo.put("usuario", ctx.sessionAttribute("usuario"));
                    modelo.put("carrito", ctx.sessionAttribute("carritoCompra"));
                    ctx.render("/templates/administrarProductos.ftl", modelo);
                });
                get("usuario/", ctx -> {
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("datos", UsuarioServices.getInstance().getUsuarios());
                    modelo.put("usuario", ctx.sessionAttribute("usuario"));
                    modelo.put("carrito", ctx.sessionAttribute("carritoCompra"));
                    ctx.render("/templates/administrarUsuarios.ftl", modelo);
                });
                get("usuario/cambiarAdmin/{usuario}", ctx -> {
                    String user = ctx.pathParam("usuario");
                    Usuario usuario = UsuarioServices.getInstance().getUsuarioByUsuario(user);
                    usuario.setAdmin(!usuario.isAdmin());
                    UsuarioServices.getInstance().modificarUsuario(usuario);
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("datos", UsuarioServices.getInstance().getUsuarios());
                    modelo.put("usuario", ctx.sessionAttribute("usuario"));
                    modelo.put("carrito", ctx.sessionAttribute("carritoCompra"));
                    ctx.render("/templates/administrarUsuarios.ftl", modelo);
                });
                get("producto/ver/{id}", ctx -> {
                    Producto producto = ProductoServices.getInstance().getProductoById(ctx.pathParam("id"));
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
                    modelo.put("id", ProductoServices.getInstance().getLastId()+1);
                    modelo.put("usuario", ctx.sessionAttribute("usuario"));
                    modelo.put("carrito", ctx.sessionAttribute("carritoCompra"));
                    ctx.render("/templates/productoCrearModificar.ftl", modelo);
                });
                get("producto/modificar/{id}", ctx -> {
                    Producto producto = ProductoServices.getInstance().getProductoById(ctx.pathParam("id"));
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
                    ProductoServices.getInstance().eliminarProductoById(ctx.pathParam("id"));
                    System.out.print("ELIMINAR" + (ctx.pathParam("id")));
                    ctx.redirect("/carrito/administrar/producto");
                });
                get("producto/comentario/add/{id}", ctx -> {
                    String id = ctx.pathParam("id");
                    String comentario = ctx.formParam("comentario");
                    Producto producto = ProductoServices.getInstance().getProductoById(id);
                    Usuario usuario = ctx.sessionAttribute("usuario");
                    producto.getComentarios().add(new ComentarioProducto(comentario, producto, usuario.getUsuario()));
                    ctx.redirect("/carrito/comprar/productoDetalles/" + producto.getId());
                });
                post("producto/crear", ctx -> {
                    //obteniendo la información enviada.
                    String id = String.valueOf(ProductoServices.getInstance().getLastId()+1);
                    String titulo = ctx.formParam("titulo");
                    String autor = ctx.formParam("autor");
                    String descripcion = ctx.formParam("descripcion");
                    String precio = ctx.formParam("precio");
                    Set<FotoProducto> fotos = new HashSet<>();

                    if (id.equalsIgnoreCase("") ||titulo.equalsIgnoreCase("") || autor.equalsIgnoreCase("") || descripcion.equalsIgnoreCase("") || precio.equalsIgnoreCase("")){
                        Map<String, Object> modelo = new HashMap<>();
                        modelo.put("id", id);
                        modelo.put("titulo", "Crear Producto");
                        modelo.put("vista", "Crear");
                        modelo.put("action", "/carrito/administrar/producto/crear");
                        modelo.put("usuario", ctx.sessionAttribute("usuario"));
                        modelo.put("carrito", ctx.sessionAttribute("carritoCompra"));
                        modelo.put("error", "Todos los campos deben ser rellenados");
                        ctx.render("/templates/productoCrearModificar.ftl", modelo);
                        return;
                    }
                    Producto producto = new Producto(titulo, descripcion, autor, Double.parseDouble(precio));

                    //Guardando las imagenes

                    ctx.uploadedFiles("imagen1").forEach(uploadedFile -> {
                        try {
                            byte[] bytes = uploadedFile.content().readAllBytes();
                            String encodedString = Base64.getEncoder().encodeToString(bytes);
                            fotos.add(new FotoProducto(encodedString, uploadedFile.contentType(), producto));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                    producto.setFotosBase64(fotos);
                    ProductoServices.getInstance().addProducto(producto);
                    ctx.redirect("/carrito/administrar/producto");
                });
                post("producto/modificar", ctx -> {
                    //obteniendo la información enviada.
                    String id = ctx.formParam("id");
                    String titulo = ctx.formParam("titulo");
                    String autor = ctx.formParam("autor");
                    String descripcion = ctx.formParam("descripcion");
                    double precio = Double.parseDouble(ctx.formParam("precio"));
                    Set<FotoProducto> fotos = ProductoServices.getInstance().getProductoById(id).getFotosBase64();
                    Producto producto = new Producto(titulo, descripcion, autor, precio);
                    producto.setId(Integer.parseInt(id));

                    ctx.uploadedFiles("imagen1").forEach(uploadedFile -> {
                        try {
                            System.out.println("IMAGEN: " + uploadedFile.filename());
                            byte[] bytes = uploadedFile.content().readAllBytes();
                            String encodedString = Base64.getEncoder().encodeToString(bytes);
                            fotos.add(new FotoProducto(encodedString, uploadedFile.contentType(), producto));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    producto.setFotosBase64(fotos);
                    ProductoServices.getInstance().modificarProducto(producto);

                    ctx.redirect("/carrito/administrar/producto");
                });
            });
        });
    }

}
