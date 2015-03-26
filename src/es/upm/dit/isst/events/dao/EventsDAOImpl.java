package es.upm.dit.isst.events.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import es.upm.dit.isst.events.model.Event;


public class EventsDAOImpl implements EventsDAO {

	private static EventsDAOImpl instance;

	private EventsDAOImpl() {
	}

	public static EventsDAOImpl getInstance(){
		if (instance == null)
			instance = new EventsDAOImpl();
		return instance;
	}


	@Override
	public List<Event> listEvents() {
		EntityManager em = EMFService.get().createEntityManager();
		// read the existing entries
		Query q = em.createQuery("select m from Todo m");
		List<Event> events = q.getResultList();
		return events;
	}

	@Override
	public void add(String title, String description, Integer numPhase) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			Event event = new Event(title, description, numPhase);
			em.persist(event);
			em.close();
		}

	}


	@Override
	public List<Event> getEvents(String userId) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em
				.createQuery("select t from Todo t where t.author = :userId");
		q.setParameter("userId", userId);
		List<Event> events = q.getResultList();
		return events;
	} 

	@Override
	public void remove(long id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Event event = em.find(Event.class, id);
			em.remove(event);
		} finally {
			em.close();
		}
	}

	@Override
	public List<String> getUsers() {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em
				.createQuery("select distinct t.author from Todo t");
		List<String> users = q.getResultList();
		return users;
	}




}
