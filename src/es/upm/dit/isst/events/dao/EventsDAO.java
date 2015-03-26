package es.upm.dit.isst.events.dao;

import java.util.List;

import es.upm.dit.isst.events.model.Event;


public interface EventsDAO {

	public List<Event> listEvents();
	public void add (String title, String description, Integer numPhase);
	public List<Event> getEvents(String userId);
	public void remove (long id);
	public List<String> getUsers();
	
}