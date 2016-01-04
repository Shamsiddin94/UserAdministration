/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administrarroles.soporte;

import java.awt.Color;
import javax.swing.JTextField;

/**
 *
 * @author Eze
 */
public class Utilidades {
    
    public static boolean verificarCamposRequeridos(JTextField... campos)
    {
        for (JTextField campo : campos) {
            if (campo.getText().equals("")) {
                campo.setBackground(Color.PINK);
                return false;
            }
            else
            {
                campo.setBackground(Color.GREEN);
            }
        }
        return true;
    }
    
    public static boolean verificarLongitudCampos(JTextField... campos)
    {
        for (JTextField campo : campos)
        {
            if((campo.getText().length() > 5 & (campo.getText().length() < 9)))
            {
                campo.setBackground(Color.GREEN);
            }
            else
            {
                campo.setBackground(Color.PINK);
                return false;
            }
        }
        return true;
    }
    
}
