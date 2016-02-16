/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administrarroles.controlador;

import administrarroles.modelo.Rol;
import administrarroles.sql.InstanciaConexion;
import administrarroles.vista.FrmAdministrarRoles;
import administrarroles.vista.FrmNuevoRol;
import administrarroles.vista.FrmPrincipal;
import com.sun.istack.internal.logging.Logger;
import com.sun.media.sound.MidiUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;

/**
 *
 * @author Eze
 */
public class GestorRol extends Observable{
    
    //private Collection<Rol> colectionRol;
    //private Rol[] rolesDelSistema = new Rol[10];
    private List<Rol> rolesDelSistema = new ArrayList<>();
    private FrmPrincipal miPrincipal;
    private static GestorRol instancia;
    
    
    public static GestorRol getInstancia(FrmPrincipal principal)
    {
        if(instancia == null)
        {
            instancia = new GestorRol(principal);
        }
        else
        {
            instancia.miPrincipal = principal;
        }
        
        return instancia;
    }
    
    private GestorRol(FrmPrincipal principal)
    {
        if(principal != null)
        {
            this.miPrincipal = principal;
        }
        materializarRoles();
    }
    
    public void newRol(int idRol, String nombreRol, String descripcionRol)
    {
        Rol r = new Rol(idRol, nombreRol);
        //Rol r = new Rol(idRol, nombreRol, descripcionRol);
        //colectionRol.add(r);
    }

    public List<Rol> getRolesDelSistema() {
        return rolesDelSistema;
    }

    public FrmAdministrarRoles mostrarPantallaAdministrar() {
        return new FrmAdministrarRoles(this);
        //FrmAdministrarRoles pantallaAdministrarRoles = new FrmAdministrarRoles(this);
        //pantallaAdministrarRoles.setVisible(true);
    }

    public void mostrarNuevaVentana() {
        FrmNuevoRol pantallaNuevoRol = new FrmNuevoRol(this);
        this.miPrincipal.mostrarVentana(pantallaNuevoRol);
        //pantallaNuevoRol.setVisible(true);
    }

    public void registrarNuevoRol(String id, String nombre, String descripcion) {
        try {
            Rol nuevo = new Rol(Integer.parseInt(id), nombre);
            nuevo.setDescripcion(descripcion);
            this.rolesDelSistema.add(nuevo);
            
            Connection conexion = InstanciaConexion.getInstanciaUnica().getConexion();
            PreparedStatement ps = conexion.prepareStatement("INSERT INTO rol (id,nombre,descripcion) VALUES(?,?,?)");
            ps.setInt(1, nuevo.getId());
            ps.setString(2, nuevo.getNombre());
            ps.setString(3, nuevo.getDescripcion());
            ps.execute();
            conexion.close();
            
            this.setChanged();
            this.notifyObservers();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(GestorRol.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editarRol(Rol rolSeleccionado) {
        FrmNuevoRol pantallaNuevoRol = new FrmNuevoRol(this, rolSeleccionado);
        this.miPrincipal.mostrarVentana(pantallaNuevoRol);
    }

    public void registrarEdicionRol(String id, String nombre, String descripcion, Rol rolEditando) {
        for(Rol r : rolesDelSistema)
        {
            if(r.getId() == rolEditando.getId())
            {
                try {
                    r.setId(Integer.valueOf(id));
                    r.setNombre(nombre);
                    r.setDescripcion(descripcion);
                    Connection conexion = InstanciaConexion.getInstanciaUnica().getConexion();
                    PreparedStatement ps = conexion.prepareStatement("UPDATE rol SET id=?,nombre=?,descripcion=? WHERE id = ?");
                    ps.setInt(1, r.getId());
                    ps.setString(2, r.getNombre());
                    ps.setString(3, r.getDescripcion());
                    ps.setInt(4, rolEditando.getId());
                    ps.execute();
                    conexion.close();
                    this.setChanged();
                    this.notifyObservers();
                    break;
                } catch (SQLException ex) {
                    java.util.logging.Logger.getLogger(GestorRol.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void materializarRoles() {
        try {
            Connection conexion = InstanciaConexion.getInstanciaUnica().getConexion();
            String sql = "select id,nombre,descripcion from rol";
            
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Rol r = new Rol(rs.getInt(1),rs.getString(2));
                r.setDescripcion(rs.getString("descripcion"));
                
                this.rolesDelSistema.add(r);
            }
            conexion.close();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(GestorRol.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public static String getRolById(int id)
    {
        String nameToReturn = "";
        try {
            Connection conexion = InstanciaConexion.getInstanciaUnica().getConexion();
            String sql = "select nombre from rol WHERE id="+id;
            
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                nameToReturn = rs.getString(1);
            }
            conexion.close();
            return nameToReturn;
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(GestorRol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Error";
    }
}
