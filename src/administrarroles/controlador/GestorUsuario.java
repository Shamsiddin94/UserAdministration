/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administrarroles.controlador;

import administrarroles.modelo.Rol;
import administrarroles.modelo.Usuario;
import administrarroles.sql.InstanciaConexion;
import administrarroles.vista.FrmAdministrarUsuarios;
import administrarroles.vista.FrmNuevoUsuario;
import administrarroles.vista.FrmPrincipal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;

/**
 *
 * @author Eze
 */
public class GestorUsuario extends Observable{
    
    private FrmPrincipal miPrincipal;
    private static GestorUsuario instanciaUser;
    private List<Usuario> usuariosDelSistema = new ArrayList<>();
    
    public static GestorUsuario getInstancia(FrmPrincipal principal)
    {
        if(instanciaUser == null)
        {
            instanciaUser = new GestorUsuario(principal);
        }
        
        return instanciaUser;
    }
    
    private GestorUsuario(FrmPrincipal principal)
    {
        if(principal != null)
        {
            this.miPrincipal = principal;
        }
        materializarUsuarios();
    }
    
    public FrmAdministrarUsuarios mostrarPantallaAdministrarUsuario()
    {
        return new FrmAdministrarUsuarios(this);
    }
    
    public FrmNuevoUsuario mostrarPantallaNuevoUsuario() {
        return new FrmNuevoUsuario(this);
        //FrmAdministrarRoles pantallaAdministrarRoles = new FrmAdministrarRoles(this);
        //pantallaAdministrarRoles.setVisible(true);
    }
    
    public void registrarNuevoUsuario(String nombre, String password, int rolUsuario) {
        try {
            Usuario nuevo = new Usuario(nombre,password,rolUsuario);
            //this.rolesDelSistema.add(nuevo);

            Connection conexion = InstanciaConexion.getInstanciaUnica().getConexion();
            PreparedStatement ps = conexion.prepareStatement("INSERT INTO usuario (username,pass,rol_user) VALUES(?,?,?)");
            ps.setString(1, nuevo.getUsername());
            ps.setString(2, nuevo.getPassword());
            ps.setInt(3, nuevo.getRolUsuario());
            ps.execute();
            conexion.close();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(GestorRol.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void materializarUsuarios() {
        try {
            Connection conexion = InstanciaConexion.getInstanciaUnica().getConexion();
            String sql = "select id,username,rol_user from usuario";
            
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Usuario u = new Usuario(rs.getString(2),rs.getInt(3),rs.getInt(1));
                
                this.usuariosDelSistema.add(u);
            }
            conexion.close();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(GestorUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public List<Usuario> getUsuariosDelSistema() {
        return usuariosDelSistema;
    }
}
