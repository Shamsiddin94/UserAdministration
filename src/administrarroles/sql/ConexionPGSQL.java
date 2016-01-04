package administrarroles.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionPGSQL {
	public Connection obtenerConexion(String user, String password,
			String host, int port, String dbName) {
		Connection conexion = null;
		try {
			Properties props = new Properties();
			props.setProperty("user", user);
			props.setProperty("password", password);
			Class.forName("org.postgresql.Driver").newInstance();
			conexion = DriverManager.getConnection("jdbc:postgresql://" + host
					+ ":" + port + "/" + dbName, props);
		} catch (Exception e) {
			Logger.getLogger(ConexionPGSQL.class.getName()).log(Level.SEVERE,
					e.getMessage(), e);
		}
		return conexion;
	}
}
