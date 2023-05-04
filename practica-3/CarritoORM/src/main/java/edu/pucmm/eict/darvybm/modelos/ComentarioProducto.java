package edu.pucmm.eict.darvybm.modelos;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class ComentarioProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comentario")
    private String comentario;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @Column(name = "usuario")
    private String nombreUsuario;

    @Column(name = "oculto")
    private boolean oculto;

    @Column(name = "fecha")
    private Date fecha;

    public ComentarioProducto(String comentario, Producto producto, String nombreUsuario) {
        this.comentario = comentario;
        this.producto = producto;
        this.nombreUsuario = nombreUsuario;
        this.fecha = new Date();
        this.oculto = false;
    }

    public ComentarioProducto() {

    }

    // getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isOculto() {
        return oculto;
    }

    public void setOculto(boolean oculto) {
        this.oculto = oculto;
    }
}
