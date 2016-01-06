/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administrarroles.controlador;

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
    
    public void mostrarPantallaNuevoUsuario() {
        FrmNuevoUsuario pantallaNuevoUsuario = new FrmNuevoUsuario(this);
        this.miPrincipal.mostrarVentana(pantallaNuevoUsuario);
        //return new FrmNuevoUsuario(this);
        //FrmAdministrarRoles pantallaAdministrarRoles = new FrmAdministrarRoles(this);
        //pantallaAdministrarRoles.setVisible(true);
    }
    
    public void registrarNuevoUsuario(String nombre, String password, int rolUsuario) {
        try {
            Usuario nuevo = new Usuario(nombre,password,rolUsuario);
            //Busca el ultimo id y agrega el usuario a la lista
            int lastIdUsuario = usuariosDelSistema.get(usuariosDelSistema.size() - 1).getId();
            nuevo.setId((lastIdUsuario + 1));
            this.usuariosDelSistema.add(nuevo);
            
            Connection conexion = InstanciaConexion.getInstanciaUnica().getConexion();
            PreparedStatement ps = conexion.prepareStatement("INSERT INTO usuario (username,pass,rol_user) VALUES(?,?,?)");
            ps.setString(1, nuevo.getUsername());
            ps.setString(2, nuevo.getPassword());
            ps.setInt(3, nuevo.getRolUsuario());
            ps.execute();
            conexion.close();
            
            this.setChanged();
            this.notifyObservers();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(GestorRol.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void registrarEdicionUsuario(String nombre, String password, int rol, Usuario usuarioEditando)
    {
        for(Usuario u : usuariosDelSistema)
        {
            if(u.getId() == usuarioEditando.getId())
            {
                try {
                    u.setUsername(nombre);
                    u.setPassword(password);
                    u.setRolUsuario(rol);
                    Connection conexion = InstanciaConexion.getInstanciaUnica().getConexion();
                    PreparedStatement ps = conexion.prepareStatement("UPDATE usuario SET username=?,pass=?,rol_user=? WHERE id = ?");
                    ps.setString(1, u.getUsername());
                    ps.setString(2, u.getPassword());
                    ps.setInt(3, u.getRolUsuario());
                    ps.setInt(4, usuarioEditando.getId());
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
    
    public void editarUsuario(Usuario usuarioSeleccionado) {
        FrmNuevoUsuario pantallaNuevoUsuario = new FrmNuevoUsuario(this, usuarioSeleccionado);
        this.miPrincipal.mostrarVentana(pantallaNuevoUsuario);
    }
    
    public Usuario getUsuarioById(int idUsuario)
    {
        Usuario usuarioToReturn = null;
        try {
            Connection conexion = InstanciaConexion.getInstanciaUnica().getConexion();
            String sql = "select id,username,rol_user from usuario WHERE id="+idUsuario;
            
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                usuarioToReturn = new Usuario(rs.getString(2), rs.getInt(3), rs.getInt(1));
            }
            conexion.close();
            return usuarioToReturn;
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(GestorRol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuarioToReturn;
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
