package edu.pucmm.eict.darvybm.modelos;

import edu.pucmm.eict.darvybm.controladores.UUIDShortener;
import edu.pucmm.eict.darvybm.services.CockroachServices;
import jakarta.persistence.*;

import java.sql.SQLException;
import java.util.*;

@Entity
@Table(name = "Ventas")
public class Ventas {

    @Id
    @GeneratedValue
    private UUID id;
    @Column(name = "shortid")
    private String shortid;
    @Column(name = "fecha_compra")
    private Date fechaCompra;
    @Column(name = "nombre_cliente")
    private String nombreCliente;
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CarritoItem> carritoItems = new HashSet<>();
    public Ventas(String nombreCliente) {
        this.fechaCompra = new Date();
        this.nombreCliente = nombreCliente;
        this.id = UUID.randomUUID();
        this.shortid = UUIDShortener.Shorten(id);
    }



    public Ventas() {

    }

    public UUID getId() {
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

    public Set<CarritoItem> getCarritoItems() {
        return carritoItems;
    }

    public void setCarritoItems(Set<CarritoItem> carritoItems) {
        this.carritoItems = carritoItems;
    }

    public double getPrecioTotal() {
        return carritoItems.stream().mapToDouble(CarritoItem::getPrecioTotal).sum();
    }
}
