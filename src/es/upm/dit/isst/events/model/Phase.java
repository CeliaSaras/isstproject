package es.upm.dit.isst.events.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.Date;
//import java.util.List;
import java.util.HashMap;
import java.util.Set;
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
//	private List<String> respuestas;
	private String title;
	private PHASE_STATE state;
	private HashMap<String, Integer> respuestasMap;
	private boolean tenemosRespuesta;
	

	public Phase(Date fechaIni, Date fechaFin, String pregunta) {
		this.fechaIni = fechaIni;
		this.fechaFin = fechaFin;
		this.pregunta = pregunta;
//		this.respuestas = respuestas;
		
//		respuestas = new ArrayList<String>();
		tenemosRespuesta = false;
		
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

	public Set<String> getRespuestas() {	//Esto es como una lista, osea en el codigo para recorrerla, haces for(String respuesta: getRespuestas())
		return respuestasMap.keySet();
	}

	public void addRespuesta(String respuesta) {	//Añadir una nueva posible respuesta p.e McDonalds
		respuestasMap.put(respuesta, 0);
	}
	public boolean incrementarContadorRespuesta(String respuesta)	//Si alguien vota MCDonalds, haces incrementarContadorRespuesta("MCDonalds")
	{
		Integer contador = respuestasMap.get(respuesta);
		if(contador == null)
			return false;
		respuestasMap.put(respuesta, contador + 1);
		return true;
	}
	public int getContadorRespuesta(String respuesta)
	{
		Integer contador = respuestasMap.get(respuesta);
		if(contador == null)
			return 0;
		return respuestasMap.get(respuesta);
	}
	
	public int getTotalRespuestas(){
		int total = 0;
		for (int aux: respuestasMap.values())
			total+=aux;
		return total;
	}
	
	public int getPorcentajeRespuesta(String respuesta)
	{
		return (int)(100*((double)getContadorRespuesta(respuesta)) / (double)getTotalRespuestas());
	}
	
	public String getMostVotedResponse()
	{	
		String aux ="";
		for (String key: respuestasMap.keySet())
		{
			if(getContadorRespuesta(aux) < getContadorRespuesta(key))
				aux=key;
		}
		return aux;
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
	
	

	public boolean getTenemosRespuesta() {
		return tenemosRespuesta;
	}

	public void setTenemosRespuesta(boolean tenemosRespuesta) {
		this.tenemosRespuesta = tenemosRespuesta;
	}

	@Override
	public String toString() {
		return "Phase [fechaIni=" + fechaIni + ", fechaFin=" + fechaFin
				+ ", state=" + state + "]";
	}

	//Hash de respuestas de cada fase
		
} 
