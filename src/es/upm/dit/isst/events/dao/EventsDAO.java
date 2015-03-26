package es.upm.dit.isst.events.dao;

import java.util.List;
import es.upm.dit.isst.events.model.*;


public interface EventsDAO {

	//Revisar
	public List<Event> listEvents();
	public void add (String title, String description, Integer numPhase);
	public List<Event> getEvents(String userId);
	public void remove (long id);
	public List<String> getUsers();
	
	//Implementar
	public void add(Event event);
	public Phase getCurrentPhase(long id);
	public List<User> getParticipants(long id);
	public User getIniciador(long id);
	public void update(Event event);
	public int getNumFases(long id);
	public List<Phase> getPhases(long id);
}