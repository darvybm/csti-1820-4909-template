package edu.pucmm.eict.darvybm.modelos;

import java.util.ArrayList;

public class CarritoCompra {
    private Usuario usuario;
    private ArrayList<CarritoItem> carritoItems;

    public CarritoCompra(Usuario usuario) {
        this.usuario = usuario;
        this.carritoItems = new ArrayList<CarritoItem>();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ArrayList<CarritoItem> getCarritoItems() {
        return carritoItems;
    }

    public void setCarritoItems(ArrayList<CarritoItem> carritoItems) {
        this.carritoItems = carritoItems;
    }

    public int getCantProductos() {
        return carritoItems.stream().mapToInt(CarritoItem::getCantidad).sum();
    }
}
