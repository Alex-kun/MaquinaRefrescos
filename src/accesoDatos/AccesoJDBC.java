package accesoDatos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

import auxiliares.LeeProperties;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class AccesoJDBC implements I_Acceso_Datos {

	private String driver, urlbd, user, password; // Datos de la conexion
	private Connection conn1;

	public AccesoJDBC() {
		System.out.println("ACCESO A DATOS - Acceso JDBC");

		try {
			HashMap<String, String> datosConexion;

			LeeProperties properties = new LeeProperties("Ficheros/config/accesoJDBC.properties");
			datosConexion = properties.getHash();

			driver = datosConexion.get("driver");
			urlbd = datosConexion.get("urlbd");
			user = datosConexion.get("user");
			password = datosConexion.get("password");
			conn1 = null;

			Class.forName(driver);
			conn1 = DriverManager.getConnection(urlbd, user, password);
			if (conn1 != null) {
				System.out.println("Conectado a la base de datos");
			}

		} catch (ClassNotFoundException e1) {
			System.out.println("ERROR: No Conectado a la base de datos. No se ha encontrado el driver de conexion");
			// e1.printStackTrace();
			System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
			System.exit(1);
		} catch (SQLException e) {
			System.out.println("ERROR: No se ha podido conectar con la base de datos");
			System.out.println(e.getMessage());
			// e.printStackTrace();
			System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
			System.exit(1);
		}
	}

	public int cerrarConexion() {
		try {
			conn1.close();
			System.out.println("Cerrada conexion");
			return 0;
		} catch (Exception e) {
			System.out.println("ERROR: No se ha cerrado corretamente");
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		HashMap<Integer, Deposito> depositos = new HashMap<Integer, Deposito>();
		String query = "SELECT * FROM `depositos` ";
		try {
			Statement stmt = conn1.createStatement();
			ResultSet rset = stmt.executeQuery(query);

			while (rset.next()) {
				int id = rset.getInt(1);
				String nombre = rset.getString(2);
				int valor = rset.getInt(3);
				int cantidad = rset.getInt(4);
				Deposito dep = new Deposito(nombre, valor, cantidad);
				depositos.put(id, dep);
			}
			rset.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return depositos;

	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		HashMap<String, Dispensador> dispensadores = new HashMap<String, Dispensador>();
		String query = "SELECT * FROM `dispensadores` ";
		try {
			Statement stmt = conn1.createStatement();
			ResultSet rset = stmt.executeQuery(query);

			while (rset.next()) {
				int id = rset.getInt(1);
				String clave = rset.getString(2);
				String nombre = rset.getString(3);
				int precio = rset.getInt(4);
				int cantidad = rset.getInt(5);

				Dispensador disp = new Dispensador(clave, nombre, precio, cantidad);
				dispensadores.put(nombre, disp);
			}
			rset.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dispensadores;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		boolean todoOK = true;
		for (HashMap.Entry<Integer, Deposito> entry : depositos.entrySet()) {
			String query = "INSERT INTO `depositos`(`nombre`, `valor`, `cantidad`) VALUES ('" + entry.getValue() + "')";

			Statement stmt;
			try {
				stmt = conn1.createStatement();
				int rset = stmt.executeUpdate(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return todoOK;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		boolean todoOK = true;
		for (HashMap.Entry<String, Dispensador> entry : dispensadores.entrySet()) {
			String query = "INSERT INTO `dispensadores`(`clave`, `nombre`, `precio`, `cantidad`) VALUES ('"
					+ entry.getValue() + "')";

			Statement stmt;
			try {
				stmt = conn1.createStatement();
				int rset = stmt.executeUpdate(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return todoOK;
	}

} // Fin de la clase