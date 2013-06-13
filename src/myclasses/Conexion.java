package myclasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * Esta clase realiza la Conexion a una base de datos de postgresql, se tiene que
 * tener la libreria de postgres postgresql-8.1-412.jdbc3.jar
 *  @author Alberto Romero Rubí
 */
public class Conexion {
        
    /**
     * @param host Nombre o ip del servidor
     * @param bd Nombre de la base de datos
     * @param userbd Usuario de la base de datos
     * @param passbd Password de la base de datos
     * @return con regresa la conexion
     */
    public static Connection getConexion(String host, String bd, String userbd, String passbd){
    	Connection con = null;
    	
    	try{
            Class.forName("org.postgresql.Driver");
            System.out.println("Cargado el Driver");
            con = DriverManager.getConnection("jdbc:postgresql://" + host + ":5432/" + bd, userbd , passbd);
            System.out.println("Conexion establecida");
        }catch(ClassNotFoundException cnfe){
            System.out.println("Conexion: 1" + cnfe.getMessage());
        }catch(SQLException sqle){
            System.out.println("Conexion: 2" + sqle.getMessage());
        }catch(Exception e){
				System.out.println("Conexion 3" + e.getMessage());
		}
        return con;
    }
    
}