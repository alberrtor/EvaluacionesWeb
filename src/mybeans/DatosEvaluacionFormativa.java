package mybeans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Vector;


/**
*
* Este es un bean que ayuda a generar los datos del reporte de evaluación formativa
* @author Alberto Romero Rubí
*/
public class DatosEvaluacionFormativa {
	
	/**
     * Método que regresa las evalucions formativas
     * @param semestre
     * @param matricula
     * @param con Conexion de la base de datos
     * @return una un objeto de tipo Collection con las evaluaciones formativas
     */
	public static Collection<EvaluacionFormativa> generaEvaluacionFormativa(String semestre, String matricula, Connection con){
		Vector<EvaluacionFormativa> collection = new Vector<EvaluacionFormativa>();
		
		Statement stmt;
		
		try {
			stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery(
				"select " + 
					"a.matricula as matricula, " + 
					"(a.nombres || ' ' || a.apellidos) as alumno, " + 
					"m.descripcion as modalidad, " + 
					"g.clave as grupo, " + 
					"g.descripcion as asignatura, " + 
					"o.consecutivo as objetivos_evaluados, " + 
					"e.recomendacion as recomendacion, " + 
					"(p.nombres || ' ' || p.apellidos) as profesor " +
				"from " + 
					"alumnos a, profesores p, grupos g, modalidades m, evaluaciones_historico e, alumnos_x_grupo ag, planteles pl, objetivos_x_asignatura o " + 
				"where " +  
					"g.id_grupo=ag.id_grupo and " + 
					"g.id_profesor=p.id_profesor and " + 
					"ag.matricula=a.matricula and " + 
					"g.id_modalidad=m.id_modalidad and " + 
					"e.id_grupo=g.id_grupo and " +
					"e.id_objetivo=o.id_objetivo and " +  
					"e.matricula=a.matricula and " + 
					"e.id_plantel=pl.id_plantel and " + 
					"(g.id_modalidad=1 or g.id_modalidad=20 or " +
					// se configura la fecha de modulos
					"(g.id_modalidad=2 and ag.fecha_alta > '2011-09-30' and ag.fecha_alta < '2011-10-06')) and " +
									
					"g.semestre='" + semestre + "' and " + 
					"e.matricula='" + matricula + "' " +
					

				"order by asignatura"
			);
			
			
			String matricula_ant = null;
			String alumno_ant = null;
			String modalidad_ant = null;
			String grupo_ant =  null;
			String asignatura_ant = null;
			String objetivos_evaluados_ant = null;
			String recomendacion_ant = null;
			String profesor_ant = null;
			
			//entra a la consulta
			while(rs.next()){
				//si es el primer registro inicializa las variables ant(equivala a anterio)
				if (rs.isFirst()){
					matricula_ant = rs.getString("matricula");
					alumno_ant = rs.getString("alumno");
					modalidad_ant = rs.getString("modalidad");
					grupo_ant =  rs.getString("grupo");
					asignatura_ant = rs.getString("asignatura");
					objetivos_evaluados_ant = rs.getString("objetivos_evaluados");
					recomendacion_ant = "OBJETIVO " + rs.getString("objetivos_evaluados") + ": " + rs.getString("recomendacion") + "\n";
					profesor_ant = rs.getString("profesor");
					
					if(rs.isLast()){
						collection.add(new EvaluacionFormativa(matricula_ant, alumno_ant, modalidad_ant, grupo_ant, asignatura_ant, objetivos_evaluados_ant, objetivos_evaluados_ant, recomendacion_ant, profesor_ant));
					}
				//si no es el primer registro	
				}else{
					//checa que si la asignatura anterior es igual que la actual
					if (asignatura_ant.equals(rs.getString("asignatura"))){
						//si es el ultimo registro lo mete en el collection
						if (rs.isLast()){
							objetivos_evaluados_ant = objetivos_evaluados_ant + ", " + rs.getString("objetivos_evaluados");
							recomendacion_ant = recomendacion_ant + "OBJETIVO " + rs.getString("objetivos_evaluados") + ": " + rs.getString("recomendacion") + "\n";
							
							collection.add(new EvaluacionFormativa(matricula_ant, alumno_ant, modalidad_ant, grupo_ant, asignatura_ant, objetivos_evaluados_ant, objetivos_evaluados_ant, recomendacion_ant, profesor_ant));
						//si no es el ultimo registro concatena los objetivos evaluados y las recomendaciones	
						}else{
							
							objetivos_evaluados_ant = objetivos_evaluados_ant + ", " + rs.getString("objetivos_evaluados");
							recomendacion_ant = recomendacion_ant + "OBJETIVO " + rs.getString("objetivos_evaluados") + ": " + rs.getString("recomendacion") + "\n";
						}
						
					//si la asignatura_ant no es igual a la actual, mete el registro en collection
					}else{
						
						if(rs.isLast()){
							collection.add(new EvaluacionFormativa(matricula_ant, alumno_ant, modalidad_ant, grupo_ant, asignatura_ant, objetivos_evaluados_ant, objetivos_evaluados_ant, recomendacion_ant, profesor_ant));
							
							//inicaliza nuevamente las variables ant(anterior) con los registros actuales
							matricula_ant = rs.getString("matricula");
							alumno_ant = rs.getString("alumno");
							modalidad_ant = rs.getString("modalidad");
							grupo_ant =  rs.getString("grupo");
							asignatura_ant = rs.getString("asignatura");
							objetivos_evaluados_ant = rs.getString("objetivos_evaluados");
							recomendacion_ant = "OBJETIVO " + rs.getString("objetivos_evaluados") + ": " + rs.getString("recomendacion") + "\n";
							profesor_ant = rs.getString("profesor");
							
							collection.add(new EvaluacionFormativa(matricula_ant, alumno_ant, modalidad_ant, grupo_ant, asignatura_ant, objetivos_evaluados_ant, objetivos_evaluados_ant, recomendacion_ant, profesor_ant));
						}else{
							
							collection.add(new EvaluacionFormativa(matricula_ant, alumno_ant, modalidad_ant, grupo_ant, asignatura_ant, objetivos_evaluados_ant, objetivos_evaluados_ant, recomendacion_ant, profesor_ant));
							
							//inicaliza nuevamente las variables ant(anterior) con los registros actuales
							matricula_ant = rs.getString("matricula");
							alumno_ant = rs.getString("alumno");
							modalidad_ant = rs.getString("modalidad");
							grupo_ant =  rs.getString("grupo");
							asignatura_ant = rs.getString("asignatura");
							objetivos_evaluados_ant = rs.getString("objetivos_evaluados");
							recomendacion_ant = "OBJETIVO " + rs.getString("objetivos_evaluados") + ": " + rs.getString("recomendacion") + "\n";
							profesor_ant = rs.getString("profesor");
							
							
						}
					}
				}
			}
		} catch (SQLException e) {
				e.printStackTrace();
		}
					
		return collection;
		
		/*
		collection.add(new EvaluacionFormativa("xxx", "2", "3", "4", "5", "6", "7", "xxx", "9"));
		collection.add(new EvaluacionFormativa("1", "2", "3", "4", "5", "6", "7", "yyyy", "9"));
		collection.add(new EvaluacionFormativa("1", "2", "3", "4", "5", "6", "7", "zzzzzzzzzzz", "9"));
		return collection;
		*/
	}
}

