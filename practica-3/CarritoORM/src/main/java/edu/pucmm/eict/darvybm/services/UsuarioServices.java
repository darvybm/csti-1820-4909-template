package edu.pucmm.eict.darvybm.services;

import edu.pucmm.eict.darvybm.modelos.Producto;
import edu.pucmm.eict.darvybm.modelos.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.jasypt.util.text.StrongTextEncryptor;

import java.util.ArrayList;

public class UsuarioServices extends GestionDB<Usuario>{
    private static UsuarioServices instancia;

    private UsuarioServices(){
        super(Usuario.class);
    }

    public static UsuarioServices getInstance(){
        if(instancia==null){
            instancia = new UsuarioServices();
        }
        return instancia;
    }


    public void addUsuario(Usuario usuario){
        EntityManager em = getEntityManager();
        String plainPassword = usuario.getPassword();

        StrongTextEncryptor encriptador = new StrongTextEncryptor();
        encriptador.setPassword("PWeb");
        String encryptedPassword = encriptador.encrypt(plainPassword);
        usuario.setPassword(encryptedPassword);
        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();
    }

    public Usuario getUsuarioByUsuario(String usuario){
        EntityManager em = getEntityManager();
        Usuario user = null;
        try {
            user = em.find(Usuario.class, usuario);
        }
        catch (Exception e){
            user = null;
        }
        return user;
    }

    public ArrayList<Usuario> getUsuarios(){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select u from Usuario u");
        return (ArrayList<Usuario>) query.getResultList();
    }

    public void modificarUsuario(Usuario usuario) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(usuario);
        em.getTransaction().commit();
    }
}
