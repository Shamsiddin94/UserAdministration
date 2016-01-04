package administrarroles.sql;

import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

public class InstanciaConexion {

    private static InstanciaConexion instanciaUnica;

    private InstanciaConexion() {
    }

    public static InstanciaConexion getInstanciaUnica() {
        if (instanciaUnica == null) {
            instanciaUnica = new InstanciaConexion();
        }
        return instanciaUnica;
    }

    public Connection getConexion() {
        Properties prop = new Properties();
        try {
            prop.load(InstanciaConexion.class.getClassLoader().getResourceAsStream("administrarroles/sql/config/config.properties"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return new ConexionPGSQL().obtenerConexion(prop.getProperty("dbuser"), prop.getProperty("dbpassword"), prop.getProperty("dbhost"), Integer.parseInt(prop.getProperty("dbport")), prop.getProperty("database"));
    }
}
