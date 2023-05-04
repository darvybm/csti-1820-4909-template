package edu.pucmm.eict.darvybm;

import edu.pucmm.eict.darvybm.modelos.Producto;
import edu.pucmm.eict.darvybm.modelos.Usuario;
import edu.pucmm.eict.darvybm.modelos.Ventas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Carrito {
    private static int idProducto = 0;
    private static int idVentas = 0;
    private static Carrito carrito;
    private Map<String, Producto> productos;
    private ArrayList<Usuario> usuarios;
    private ArrayList<Ventas> ventas;

    public Carrito() {
        this.productos = new HashMap<String, Producto>();
        this.usuarios = new ArrayList<Usuario>();
        this.ventas = new ArrayList<Ventas>();
    }

    public static Carrito getInstance() {
        if (carrito == null) {
            carrito = new Carrito();
        }
        return carrito;
    }

    public void addProducto(Producto producto) {
        productos.put(String.valueOf(producto.getId()), producto);
        idProducto++;
    }

    public Map<String, Producto> getProductos() {
        return productos;
    }

    public Producto getProductoById(String id) {
        return productos.get(id);
    }

    public void modificarProductoById(String id, Producto producto) {
        productos.put(id, producto);
    }

    public void eliminarProductoById(String id) {
        productos.remove(id);
    }

    public void addUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public Usuario getUsuarioByUsuario(String user) {
        return usuarios.stream()
                .filter(usuario -> usuario.getUsuario().equals(user))
                .findFirst().orElse(null);
    }


    public void addVenta(Ventas venta) {
        ventas.add(venta);
        idVentas++;
    }

    public ArrayList<Ventas> getVentas() {
        return ventas;
    }

    public Ventas getVentaById(String id) {
        return ventas.stream()
                .filter(venta -> venta.getId() == Integer.valueOf(id))
                .findFirst().orElse(null);
    }

    public static int getIdProducto() {
        return idProducto;
    }

    public static int getIdVentas() {
        return idVentas;
    }


}
