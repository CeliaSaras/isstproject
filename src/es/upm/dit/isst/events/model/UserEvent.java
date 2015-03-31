package es.upm.dit.isst.events.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class UserEvent implements Serializable {


	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	private Long eventId;
	
	private String hash;
	
	

	public UserEvent(String hash) {
		this.hash = hash;
	}

	public Long getUserId() {
		return userId;
	}
	
	public Long getEventId() {
		return eventId;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
	
} 
