package accesoDatos;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.cfg.Configuration;

import javassist.bytecode.analysis.Util;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;
import utils.HibernateUtil;




public class AccesoHibernate implements I_Acceso_Datos {
	Session session;
	
	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {

		HibernateUtil hu = new HibernateUtil();
		session = hu.getSessionFactory().openSession();
		
		HashMap<Integer, Deposito> deposito = new HashMap<Integer, Deposito>();
		System.out.println("Inicio consulta Hibernate Depositos");
    	
        Query q= session.createQuery("SELECT * FROM depositos");
        List results = q.list();
        
        Iterator depositosIterator= results.iterator();

        while (depositosIterator.hasNext()){
            Deposito dep = (Deposito)depositosIterator.next();
        		System.out.println ( "		Id: " + dep.getId() + " - NombreMoneda: " + dep.getNombreMoneda());  
        		//HashMap<Integer, Deposito> deposito;
        		deposito.put(dep.getId(), dep);
        }

    	System.out.println("Fin Consulta Depositos");

		return deposito;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		// TODO Auto-generated method stub
		HashMap<String, Dispensador> dispensador = new HashMap<String, Dispensador>();
		
		HibernateUtil hu = new HibernateUtil();
		session = hu.getSessionFactory().openSession();
		
		System.out.println("Inicio Consulta Simple Dispensadores");
    	
        Query q= session.createQuery("SELECT * FROM dispensadores");
        List results = q.list();
        
        Iterator dispensadorIterator= results.iterator();

        while (dispensadorIterator.hasNext()){
            Dispensador disp = (Dispensador)dispensadorIterator.next();
        		System.out.println ( "		Id: " + disp.getId() + " - Nombre: " + disp.getNombreProducto());  
        		dispensador.put(disp.getNombreProducto(), disp);
        }

    	System.out.println("Fin Consulta Dispensadores");

		return dispensador;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		
		 Deposito dep = new Deposito();
		 dep.setId(1);
		 dep.setNombreMoneda("");
		 dep.setCantidad(10);
		 dep.setValor(10);
		 SessionFactory sf = new Configuration().configure().buildSessionFactory();
		 Session s=sf.openSession();
		s.beginTransaction();
		s.save(dep);
		s.getTransaction().commit();
		s.close();
		System.exit(0);

		return false;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		// TODO Auto-generated method stub
		 Dispensador disp = new Dispensador();
		 disp.setId(1);
		 disp.setNombreProducto("");
		 disp.setCantidad(5);
		 disp.setPrecio(10);
		 SessionFactory sf = new Configuration().configure().buildSessionFactory();
		 Session s=sf.openSession();
		s.beginTransaction();
		s.save(disp);
		s.getTransaction().commit();
		s.close();
		System.exit(0);
		return false;
	}

}
