package edu.pucmm.eict.darvybm.controladores;

import edu.pucmm.eict.darvybm.Carrito;
import edu.pucmm.eict.darvybm.modelos.*;
import io.javalin.Javalin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class CarritoCompraController {
    Javalin app;

    public CarritoCompraController(Javalin app) {
        this.app = app;
    }

    public void rutas(){
        app.routes(() -> {
            path("/carrito/carritoCompra", () -> {
                get("/", ctx -> {
                    CarritoCompra carritoCompra = ctx.sessionAttribute("carritoCompra");
                    Map<String, Object> modelo = new HashMap<>();
                    if (carritoCompra == null){
                        //Preparar lo del "carrito vacío"
                        modelo.put("vacio", true);
                        modelo.put("usuario", ctx.sessionAttribute("usuario"));
                        modelo.put("carrito", new CarritoCompra(ctx.sessionAttribute("usuario")));
                        ctx.render("/templates/carritoCompra.ftl", modelo);
                    }
                    else{
                        modelo.put("vacio", false);
                        modelo.put("usuario", ctx.sessionAttribute("usuario"));
                        modelo.put("carrito", carritoCompra);
                        modelo.put("datos", carritoCompra.getCarritoItems());
                        modelo.put("cantItems", carritoCompra.getCarritoItems().stream().mapToInt(CarritoItem::getCantidad).sum());
                        modelo.put("totalCarrito", carritoCompra.getCarritoItems().stream().mapToDouble(CarritoItem::getPrecioTotal).sum());
                        ctx.render("/templates/carritoCompra.ftl", modelo);
                    }
                });
                post("/add/{id}", ctx -> {
                    Producto producto = Carrito.getInstance().getProductoById(ctx.pathParam("id"));
                    int cantidad = Integer.parseInt(ctx.formParam("cantidad"));
                    CarritoCompra carritoCompra = ctx.sessionAttribute("carritoCompra");
                    if (carritoCompra == null){
                        carritoCompra = new CarritoCompra(ctx.sessionAttribute("usuario"));
                    }

                    // Buscar si ya existe un carrito item con el producto
                    boolean itemExistente = false;
                    for (CarritoItem carritoItem : carritoCompra.getCarritoItems()) {
                        if (carritoItem.getProducto().getId() == producto.getId()) {
                            carritoItem.setCantidad(carritoItem.getCantidad() + cantidad);
                            itemExistente = true;
                            break;
                        }
                    }

                    // Si no existe, se agrega un nuevo item al carrito
                    if (!itemExistente) {
                        carritoCompra.getCarritoItems().add(new CarritoItem(producto, cantidad));
                    }
                    ctx.sessionAttribute("carritoCompra", carritoCompra);
                    ctx.redirect("/carrito/");
                });
                get("/eliminar/{id}", ctx -> {
                    Producto producto = Carrito.getInstance().getProductoById(ctx.pathParam("id"));
                    CarritoCompra carritoCompra = ctx.sessionAttribute("carritoCompra");
                    CarritoItem carritoItem = carritoCompra.getCarritoItems()
                            .stream()
                            .filter(item -> item.getProducto().getId() == producto.getId())
                            .findFirst()
                            .orElse(null);
                    carritoCompra.getCarritoItems().remove(carritoItem);
                    ctx.sessionAttribute("carritoCompra", carritoCompra);
                    ctx.redirect("/carrito/carritoCompra/");
                });
                post("/", ctx -> {
                    Usuario usuario = ctx.sessionAttribute("usuario");
                    if (usuario == null) {
                        ctx.redirect("/carrito/401");
                        return;
                    }
                    CarritoCompra carritoCompra = ctx.sessionAttribute("carritoCompra");
                    Ventas venta = new Ventas(Carrito.getIdVentas(), new Date(), usuario.getNombre(), carritoCompra.getCarritoItems());
                    Carrito.getInstance().addVenta(venta);

                    //Limpiando la sesión
                    carritoCompra = new CarritoCompra(ctx.sessionAttribute("usuario"));
                    ctx.sessionAttribute("carritoCompra", carritoCompra);
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("vacio", true);
                    modelo.put("usuario", usuario);
                    modelo.put("carrito", carritoCompra);
                    ctx.render("/templates/carritoCompra.ftl", modelo);
                });
            });
        });
    }
}
