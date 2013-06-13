package myclasses;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase Usuario del IEMS, puede ser profesor o algun usuario de escolares
 * @author Alberto Romero
 * @version 1.0
 */
public class Usuario {
    Connection co=null;

    /**
     * Constructor
     * @param c Conexion de la base de datos
     */
    public Usuario(Connection c){
        co=c;
    }


    /** Método que checa si el login y el password son correctos
     * @param user Login del usuario
     * @param pass Password de usuario
     * @return  True si el usuario es correcto y false en caso contrario
     */
    public boolean getUsuarioCorrecto(String user, String pass){
		  String sql;
        boolean password_igual = false;
        try{

           Statement stmt = co.createStatement();
				sql = "SELECT usuario_id, link_id, usuario, contrasena FROM usuarios where usuario='" + user + "'";
            ResultSet rs = stmt.executeQuery(sql);
				System.out.println(sql);
			
            pass = getDescifraPassword(pass);

			if(rs.next()){
				if (pass.equals(rs.getString("contrasena"))){
               		password_igual = true;
           		}
			}
			rs.close();

		}catch (Exception e){
            System.out.println("entro aqui " + e.getMessage());
        }
            
    	if (password_igual)
    		return true;
    	else
    		return false;
    }

    /**
     * Método que regresa el id_profesor, regresa "ADMINISTRATIVO" si no es profesor(es decir es administrativo)
     * @param user Login o nombre de usuario
     * @return una cadena que especifica el id_usuario
     */
    public String getIdUsuario(String user){
           String id=null;
       try{
           Statement stmt = co.createStatement();
           ResultSet rs = stmt.executeQuery("SELECT link_id, usuario, contrasena FROM usuarios where usuario='" + user +"'");
           rs.next();
           id=rs.getString("link_id");
			  System.out.println(id);
       }catch(Exception e){
           System.out.println(e.getMessage());
       }

		 if(id==null){
		 	id = "ADMINISTRATIVO";
		 }

       return id;

    }

    /**
     * Metodo que regresa el nombre completo del usuario
     * (Ej. ALBERTO ROMERO RUBI)
     * @param user Login o nombre de usuario
     * @return profesor El nombre del usuario o profesor
	 * @throws SQLException
     */
    public String getNombreUsuario(String user){

    	
           String profesor=null;
           
           Statement stmt;
		try {
			stmt = co.createStatement();
		
			ResultSet rs = stmt.executeQuery("SELECT (nombres || ' ' || apellidos) as profesor FROM profesores where id_profesor=" + getIdUsuario(user));
			rs.next();
			profesor=rs.getString("profesor");
		} catch (SQLException e) {
			e.printStackTrace();
		}
           return profesor;
    }

    /**
     * Método que despliega el password a la forma en como se almacena en la base
     * de datos
     * @param p es el password que teclea el usuario de forma normal
     */
    public void despliegaPassword(String p){
        String pas="";
        int ascii=0, valor=0;
        char a = 0;

        System.out.println("------------------------------------------");
        for(int i=0; i<p.length(); i++){
            System.out.println("tamaño: " + p.length());
            ascii= (int) p.charAt(i);
            System.out.println("ascii: " + ascii);
            valor = (int) (ascii ^ 1);
            System.out.println("valor: " + valor);
            a= (char) valor;
            System.out.println("caracter: " + a);

            pas=pas+a;
            System.out.println("cadena: " + pas);
            System.out.println("cadena: " + pas.length());
            System.out.println("-----------------------------------------");


        }
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("final: " + pas);
        System.out.println("cadena: " + pas.length());
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
        
    }


    /**
     * Metodo que convierte el password a la forma en como se almacena en la base
     * de datos
     * @param p es el password como lo escribe el usuario
     * @return pas Regresa el password codificado
     */
    public String getDescifraPassword(String p){
        String pas="";
        int ascii=0, valor=0;
        char a = 0;

        for(int i=0; i<p.length(); i++){
            ascii= (int) p.charAt(i);
            valor = (int) (ascii ^ 1);
            a= (char) valor;
            pas=pas+a;
        }

        return pas;
    }

}