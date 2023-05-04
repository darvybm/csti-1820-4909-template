package edu.pucmm.eict.darvybm.modelos;

import edu.pucmm.eict.darvybm.services.CockroachServices;
import jakarta.persistence.*;

import java.sql.SQLException;
import java.util.UUID;

@Entity
public class CarritoItem {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "cantidad")
    private int cantidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id")
    private Ventas venta;

    public CarritoItem(Producto producto, int cantidad) {
        this.id = UUID.randomUUID();
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public CarritoItem() {

    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioTotal() {
        return producto.getPrecio() * (double) cantidad;
    }

    public Ventas getVenta() {
        return venta;
    }

    public void setVenta(Ventas venta) {
        this.venta = venta;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

}
