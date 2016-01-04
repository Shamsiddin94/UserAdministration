/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administrarroles;

import administrarroles.controlador.GestorRol;
import administrarroles.vista.FrmPrincipal;

/**
 *
 * @author Eze
 */
public class AdministrarRoles {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        /*
        JFrame ventana1 = new JFrame("Primer Ventana 2015");
        JTextField txtNombre = new JTextField();
        JLabel etiqueta = new JLabel("Nombre: ");
        ventana1.setLayout(new FlowLayout());
        ventana1.add(etiqueta);
        ventana1.add(txtNombre);
        
        ventana1.setVisible(true);
        ventana1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        */
        
//        GestorRol gestor = new GestorRol();
//        gestor.mostrarPantallaAdministrar();
        FrmPrincipal principal = new FrmPrincipal();
        principal.setVisible(true);
    }
    
}
