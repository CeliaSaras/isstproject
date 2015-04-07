package es.upm.dit.isst.events.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Event implements Serializable {


	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long id_iniciador;
	
	private Integer numPhase;
	private String title;
	private String description;
	
	public Event(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public Long getIdIniciador() {
		return id_iniciador;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getNumPhase(){
		return numPhase;
	}
	
	public void setNumPhase(Integer numPhase) {
		this.numPhase = numPhase;
	}

	@Override
	public String toString() {
		return "Event [title=" + title + ", description=" + description + "]";
	}

	
} 
