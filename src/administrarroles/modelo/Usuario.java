/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administrarroles.modelo;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eze
 */
public class Usuario {
    
    private int id;
    private String username;
    private String password;
    private int rolUsuario;

    public Usuario(String nombre, String password, int rol)
    {
        this.username = nombre;
        setPassword(password);
        this.rolUsuario = rol;
        this.id = 0;
    }
    
    public Usuario(String nombre, int rol, int id)
    {
        this.username = nombre;
        this.id = id;
        this.rolUsuario = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        MessageDigest md;
        byte[] messageDigest;
        try {
            md = MessageDigest.getInstance("MD5");
            messageDigest = md.digest(password.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            this.password = number.toString(16);
            while (this.password.length() < 32) {
                this.password = "0" + this.password;
            }
        } catch (NoSuchAlgorithmException ex) {
            this.password = password;
        }
    }

    public int getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(int rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

}
