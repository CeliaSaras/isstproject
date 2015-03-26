package es.upm.dit.isst.events.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;


@Entity
public class Phase implements Serializable {


	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long idEvento;
	private Date fechaIni;
	private Date fechaFin;
	private String pregunta;
	private List<String> respuestas;
	private String title;
	private PHASE_STATE state;
	

	public Phase(Date fechaIni, Date fechaFin, String pregunta) {
		this.fechaIni = fechaIni;
		this.fechaFin = fechaFin;
		this.pregunta = pregunta;
//		this.respuestas = respuestas;
		
		respuestas = new ArrayList<String>();
		
	}

	public Long getId() {
		return id;
	}

	public String getDateIni() {
		return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(fechaIni);
	}
	
	public Date getFechaIni(){
		return fechaIni;
	}

	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}
	
	public String getDateFin() {
		return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(fechaFin);
	}
	
	public Date getFechaFin(){
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getPregunta() {
		return pregunta;
	}
	
	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public List<String> getRespuestas() {
		return respuestas;
	}

	public void addRespuesta(String respuesta) {
		respuestas.add(respuesta);
	}

	public PHASE_STATE getState() {
		return state;
	}

	public void setState(PHASE_STATE state) {
		this.state = state;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}

	@Override
	public String toString() {
		return "Phase [fechaIni=" + fechaIni + ", fechaFin=" + fechaFin
				+ ", state=" + state + "]";
	}

	
	
	
	
} 
