package accesoDatos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

/*
 * Todas los accesos a datos implementan la interfaz de Datos
 */

public class FicherosTexto implements I_Acceso_Datos {

	File fDis; // FicheroDispensadores
	File fDep; // FicheroDepositos

	public FicherosTexto() {
		System.out.println("ACCESO A DATOS - FICHEROS DE TEXTO");
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {

		HashMap<Integer, Deposito> depositosCreados = new HashMap<Integer, Deposito>();
		// Leer del fichero y guardar en el hash map
		try{
			FileReader fr = new FileReader("Ficheros/datos/depositos.txt");
			BufferedReader bf = new BufferedReader(fr);
			String linea;
			while ((linea = bf.readLine()) != null) {
				String[] cadenas = linea.split(";");
				Integer clave = Integer.parseInt(cadenas[1]);
				Deposito dep = new Deposito(cadenas[0],Integer.parseInt(cadenas[1]), Integer.parseInt(cadenas[2]));
				//dep.setCantidad(cantidad);
				depositosCreados.put(clave, dep);
			}
		} catch(IOException e){
			e.printStackTrace();
		}

		return depositosCreados;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {

		HashMap<String, Dispensador> dispensadoresCreados = new HashMap<String, Dispensador>();
		try{
			
			FileReader fr = new FileReader("Ficheros/datos/dispensadores.txt");
			BufferedReader bf = new BufferedReader(fr);
			String linea;
			while ((linea = bf.readLine()) != null) {
				String[] cadenas = linea.split(";");
				String clave = cadenas[0];
				Integer num1 = Integer.parseInt(cadenas[2]);
				Integer num2 = Integer.parseInt(cadenas[3]);
				Dispensador disp = new Dispensador(cadenas[0], cadenas[1], num1, num2);
				//dep.setCantidad(cantidad);
				dispensadoresCreados.put(clave, disp);
			}
		} catch(IOException e){
			e.printStackTrace();
		}
		return dispensadoresCreados;

	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		boolean todoOK = true;
		try {
			PrintWriter pw = new PrintWriter("Ficheros/datos/depositos.txt");

			for (HashMap.Entry<Integer, Deposito> entry : depositos.entrySet()) {
			   // System.out.println("clave=" + entry.getKey() + ", valor=" + entry.getValue());
			    pw.println(entry.getKey() + ";"+ entry.getValue());
			}
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return todoOK;

	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		boolean todoOK = true;
		try {
			PrintWriter pw = new PrintWriter("Ficheros/datos/dispensador.txt");

			for (HashMap.Entry<String, Dispensador> entry : dispensadores.entrySet()) {
			   // System.out.println("clave=" + entry.getKey() + ", valor=" + entry.getValue());
			    pw.println(entry.getKey() + ";"+ entry.getValue());
			}
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return todoOK;
	}

} // Fin de la clase