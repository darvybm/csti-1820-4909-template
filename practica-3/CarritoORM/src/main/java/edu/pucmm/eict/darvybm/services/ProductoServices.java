package edu.pucmm.eict.darvybm.services;

import edu.pucmm.eict.darvybm.modelos.ComentarioProducto;
import edu.pucmm.eict.darvybm.modelos.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ProductoServices extends GestionDB<Producto>{
    private static ProductoServices instancia;
    private ProductoServices() {
        super(Producto.class);
    }

    public static ProductoServices getInstance(){
        if(instancia==null){
            instancia = new ProductoServices();
        }
        return instancia;
    }

    public ArrayList<Producto> getPaginatedProductos(int pageNumber, int pageSize) {
        int offset = (pageNumber - 1) * pageSize;
        return (ArrayList<Producto>) getProductos()
                .stream()
                .filter(p -> !p.isEliminado())
                .skip(offset)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public void addProducto(Producto producto){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(producto);
        em.getTransaction().commit();
    }

    public ArrayList<Producto> getProductos(){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select p from Producto p");
        return (ArrayList<Producto>) query.getResultList();
    }

    public Producto getProductoById(String id) {
        EntityManager em = getEntityManager();
        Producto producto = null;
        try {
            producto = em.find(Producto.class, id);
        }
        catch (Exception e){
            producto = null;
        }
        return producto;
    }

    public void eliminarProductoById(String id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Producto producto = em.find(Producto.class, id);
        producto.setEliminado(true);
        em.merge(producto);
        em.getTransaction().commit();
    }

    public void modificarProducto(Producto p) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(p);
        em.getTransaction().commit();
    }

    public int getLastId() {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT MAX(p.id) FROM Producto p");
        Integer lastId = (Integer) query.getSingleResult();
        if (lastId == null) {
            // Si no hay registros, se devuelve 0
            return 0;
        }
        return lastId;
    }
    public void eliminarComentarioProductoById(String id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        ComentarioProducto comentario = em.find(ComentarioProducto.class, id);
        comentario.setOculto(true);
        em.merge(comentario);
        em.getTransaction().commit();
    }

    public Producto getProductoByComentarioId(String id) {
        EntityManager em = getEntityManager();
        ComentarioProducto comentario = em.find(ComentarioProducto.class, id);
        return comentario.getProducto();
    }
}