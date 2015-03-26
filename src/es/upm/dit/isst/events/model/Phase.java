package es.upm.dit.isst.events.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.Date;
import java.text.SimpleDateFormat;


@Entity
public class Phase implements Serializable {


	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_participante;
	
	private Date fechaIni;
	private Date fechaFin;
	private String preguntas;
	private String respuestas;
	

	public Phase(Date fechaIni, Date fechaFin, String preguntas, String respuestas) {
		this.fechaIni = fechaIni;
		this.fechaFin = fechaFin;
		this.preguntas = preguntas;
		this.respuestas = respuestas;
	}

	public Long getIdParticipante() {
		return id_participante;
	}

	public String getFechaIni() {
		return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(fechaIni);
	}
	
	public Date getDateIni(){
		return fechaIni;
	}

	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}
	
	public String getFechaFin() {
		return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(fechaFin);
	}
	
	public Date getDateFin(){
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getPreguntas() {
		return preguntas;
	}
	
	public void setPreguntas(String preguntas) {
		this.preguntas = preguntas;
	}

	public String getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(String respuestas) {
		this.respuestas = respuestas;
	}
	
} 
