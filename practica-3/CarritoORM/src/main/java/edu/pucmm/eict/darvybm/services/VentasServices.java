package edu.pucmm.eict.darvybm.services;


import edu.pucmm.eict.darvybm.modelos.Ventas;
import jakarta.persistence.*;

import java.util.ArrayList;


public class VentasServices extends GestionDB<Ventas>{
    private static VentasServices instancia;
    public static VentasServices getInstance(){
        if(instancia==null){
            instancia = new VentasServices();
        }
        return instancia;
    }

    public VentasServices() {
        super(Ventas.class);
    }

    public void addVentas(Ventas ventas){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(ventas);
        em.getTransaction().commit();
    }

    public ArrayList<Ventas> getVentas() {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select v from Ventas v");
        return (ArrayList<Ventas>) query.getResultList();
    }
}