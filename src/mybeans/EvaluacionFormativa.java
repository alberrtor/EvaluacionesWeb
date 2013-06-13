package mybeans;

/**
*
* Este es un bean que ayuda a generar los datos del reporte de evaluación formativa
* @author Alberto Romero Rubí
*/

public class EvaluacionFormativa {
	
	//Declaración de atributos o propiedades
	private String matricula;
	private String alumno;
	private String modalidad;
	private String grupo;
	private String asignatura;
	private String eval;
	private String evaluacion;
	private String observaciones;
	private String profesor;
	
	/**
     * Se inicializan las propiedades con el constructor 
     */
	public EvaluacionFormativa(String matricula, String alumno, String modalidad, String grupo, String asignatura, String eval, String evaluacion, String observaciones, String profesor){
			
		this.matricula = matricula;
		this.alumno = alumno;
		this.modalidad = modalidad;
		this.grupo = grupo;
		this.asignatura = asignatura;
		this.eval = eval;
		this.evaluacion = evaluacion;
		this.observaciones = observaciones;
		this.profesor = profesor;
	}
	
	public String getMatricula() {
		return matricula;
	}
	
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	public String getAlumno() {
		return alumno;
	}
	
	public void setAlumno(String alumno) {
		this.alumno = alumno;
	}
	
	public String getModalidad() {
		return modalidad;
	}
	
	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}
	
	public String getGrupo() {
		return grupo;
	}
	
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	
	public String getAsignatura() {
		return asignatura;
	}
	
	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura;
	}
	
	public String getEval() {
		return eval;
	}
	
	public void setEval(String eval) {
		this.eval = eval;
	}
	
	public String getEvaluacion() {
		return evaluacion;
	}
	
	public void setEvaluacion(String evaluacion) {
		this.evaluacion = evaluacion;
	}

	public String getObservaciones() {
		return observaciones;
	}
	
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getProfesor() {
		return profesor;
	}
	
	public void setProfesor(String profesor) {
		this.profesor = profesor;
	}

}

