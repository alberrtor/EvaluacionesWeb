package myservlets;

import myclasses.Conexion;
import myclasses.Usuario;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
*
* Este servlet o clase genera el reporte de evaluación compendiada utilizando Jasper Report
* @author Alberto Romero Rubí
*/
public class ReporteCompendiada extends HttpServlet{
	
	/**
     * Se utiliza el método doGet porque en la petición es por el metodo GET 
     */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws IOException, ServletException{

		//Definición de Variables
		String matricula = null;
		String usuario_tutor = null;
		String nombre_tutor = null;
		String semestre = null;
		String plantel = null;
		String archivo = null;
		
		String database = null;
		String database_host = null;
		String database_user = null;
		String database_password = null;
		
		
		//saca variables del descriptor de despliegue web.xml
		database = getServletContext().getInitParameter("database");
		database_host = getServletContext().getInitParameter("database_host");
		database_user = getServletContext().getInitParameter("database_user");
		database_password = getServletContext().getInitParameter("database_password");
		semestre = getServletContext().getInitParameter("semestre");
		plantel = getServletContext().getInitParameter("preparatoria");
		archivo = getServletContext().getInitParameter("ruta_absoluta_reportes");
		
		//Realiza la conexion a la bd del iems
		Connection con = Conexion.getConexion(database_host, database, database_user, database_password);
		
		//saca parametros de la página que hace la petición
		matricula = request.getParameter("matricula");
		usuario_tutor = request.getParameter("tutor");
		
		JasperReport masterReport = null;
		Map parametro = new HashMap();
		ServletOutputStream out;
		
		Usuario u = new Usuario(con);
		nombre_tutor = u.getNombreUsuario(usuario_tutor.toUpperCase());
		
		parametro.put("matricula", matricula);
		parametro.put("tutor", nombre_tutor);
		parametro.put("semestre", semestre);
		parametro.put("plantel", plantel);
		
		archivo = archivo + "EvaluacionCompendiada.jasper";
		
		//esta linea no importa, se puede borrar
		System.out.print("----" + semestre + plantel + "---");
		
		try {
			/* Esta es una forma de abrir los reportes en pdf*/
			/*
			response.setContentType ("application/pdf");
			out = response.getOutputStream();
			masterReport = (JasperReport) JRLoader.loadObject("/opt/tomcat/apache-tomcat-7.0.12/webapps/formativas/evaluacion.jasper");
		
			JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport, parametro, base.getConexion());
		
			JRExporter exporter = new JRPdfExporter();
		
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
			exporter.exportReport();
			*/
			
			
			// Esta es otra forma de abrir los reportes en pdf
			
			masterReport = (JasperReport) JRLoader.loadObject(archivo);
		
			byte[] fichero = null;
		
			fichero = JasperRunManager.runReportToPdf (masterReport, parametro, con);
	
			response.setContentType ("application/pdf");
			response.setHeader ("Content-disposition", "inline; filename=informeDemo.pdf");
			response.setHeader ("Cache-Control", "max-age=30");
			response.setHeader ("Pragma", "No-cache");
			response.setDateHeader ("Expires", 0);
			response.setContentLength (fichero.length);
			out = response.getOutputStream ();
			out.write (fichero, 0, fichero.length);
			out.flush ();
			out.close ();
			
		} catch (JRException e) {
			e.printStackTrace();
		} finally{
			try{
				
				if (con != null)
					con.close();
			}catch (SQLException e) {
				e.printStackTrace();
			} 
		}
				
	}
}