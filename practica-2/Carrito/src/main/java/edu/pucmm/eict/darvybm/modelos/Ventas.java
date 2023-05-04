package edu.pucmm.eict.darvybm.modelos;

import java.util.ArrayList;
import java.util.Date;

public class Ventas {
    private int id = 0;
    private Date fechaCompra;
    private String nombreCliente;
    private ArrayList<CarritoItem> carritoItems;

    public Ventas(int id, Date fechaCompra, String nombreCliente, ArrayList<CarritoItem> carritoItems) {
        this.id = id;
        this.fechaCompra = fechaCompra;
        this.nombreCliente = nombreCliente;
        this.carritoItems = carritoItems;
    }

    public int getId() {
        return id;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public ArrayList<CarritoItem> getCarritoItems() {
        return carritoItems;
    }

    public void setCarritoItems(ArrayList<CarritoItem> carritoItems) {
        this.carritoItems = carritoItems;
    }

    public double getPrecioTotal() {
        return carritoItems.stream().mapToDouble(CarritoItem::getPrecioTotal).sum();
    }
}
