package edu.pucmm.eict.darvybm.modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "foto_producto")
public class FotoProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "foto")
    private String foto;

    @Column(name = "type")
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    private Producto producto;

    public FotoProducto(String foto, String type, Producto producto) {
        this.foto = foto;
        this.type = type;
        this.producto = producto;
    }

    public FotoProducto() {

    }

    public Long getId() {
        return id;
    }

    public String getFoto() {
        return foto;
    }

    public Producto getProducto() {
        return producto;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
