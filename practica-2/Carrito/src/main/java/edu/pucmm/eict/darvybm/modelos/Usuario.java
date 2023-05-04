package edu.pucmm.eict.darvybm.modelos;

public class Usuario {
    private String usuario;
    private String password;
    private String nombre;
    private boolean isAdmin;

    public Usuario(String usuario, String password, String nombre, boolean isAdmin) {
        this.usuario = usuario;
        this.password = password;
        this.nombre = nombre;
        this.isAdmin = isAdmin;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
