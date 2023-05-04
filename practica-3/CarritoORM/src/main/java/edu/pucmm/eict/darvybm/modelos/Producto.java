package edu.pucmm.eict.darvybm.modelos;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descripcion", length = 1000)
    private String descripcion;

    @Column(name = "autor")
    private String autor;

    @Column(name = "precio")
    private double precio;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private Set<FotoProducto> fotosBase64 = new HashSet<>();

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private Set<ComentarioProducto> comentarios = new HashSet<>();

    @Column(name = "eliminado")
    private boolean eliminado;

    public Producto(String titulo, String descripcion, String autor, double precio) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.autor = autor;
        this.precio = precio;
    }

    public Producto() {

    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Set<FotoProducto> getFotosBase64() {
        return fotosBase64;
    }

    public void setFotosBase64(Set<FotoProducto> fotosBase64) {
        this.fotosBase64 = fotosBase64;
    }

    public Set<ComentarioProducto> getComentarios() {
        return comentarios;
    }

    public void setComentarios(Set<ComentarioProducto> comentarios) {
        this.comentarios = comentarios;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
}
