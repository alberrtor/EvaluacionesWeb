package myservlets;

import myclasses.Usuario;
import myclasses.Conexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AutentificaUsuario extends HttpServlet{
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
               throws IOException, ServletException{

		String usuario;
		String password;

		String database = null;
		String database_host = null;
		String database_user = null;
		String database_password = null;

		//Saca parametros del web.xml
		database = getServletContext().getInitParameter("database");
		database_host = getServletContext().getInitParameter("database_host");
		database_user = getServletContext().getInitParameter("database_user");
		database_password = getServletContext().getInitParameter("database_password");
		
		//Realiza la conexion a la bd del iems
		Connection con = Conexion.getConexion(database_host, database, database_user, database_password);
		
		Usuario u = new Usuario(con);

		usuario = request.getParameter("usuario");
		usuario = usuario.toUpperCase();
		password = request.getParameter("password");
		
		System.out.println(usuario);
		System.out.println(password);
		
		if(u.getUsuarioCorrecto(usuario, password)){
			//Si no es administrativo
			if(!u.getIdUsuario(usuario).equals("ADMINISTRATIVO")){
				RequestDispatcher view = request.getRequestDispatcher("/principal.jsp");
				view.forward(request, response);
	
			}else{
				RequestDispatcher view = request.getRequestDispatcher("/no_autorizado.html");
				view.forward(request, response);
			}
		}else{
			RequestDispatcher view = request.getRequestDispatcher("/usuario_incorrecto.html");
			view.forward(request, response);
		}
		
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
