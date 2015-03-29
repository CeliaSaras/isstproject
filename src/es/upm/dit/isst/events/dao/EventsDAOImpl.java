package es.upm.dit.isst.events.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import es.upm.dit.isst.events.model.Event;
import es.upm.dit.isst.events.model.Phase;
import es.upm.dit.isst.events.model.User;


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
		Query q = em.createQuery("select m from Event m");
		List<Event> events = q.getResultList();
		return events;
	}

	@Override
	public void add(String title, String description, Integer numPhase) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			Event event = new Event(title, description);
			em.persist(event);
			em.close();
		}

	}


	@Override
	public List<Event> getEvents(String userId) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em
				.createQuery("select t from Event t where t.id_iniciador = :userId");
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
				.createQuery("select distinct t.id_iniciador from Event t");
		List<String> users = q.getResultList();
		return users;
	}
	
	public void add(Event event){
		//TODO: Le pasas un objeto evento y lo almacena en la base de datos
	}
	public Phase getCurrentPhase(long id){
		//TODO: Le pasas el id del evento y te devuelve la fase actual en la que nos encontramos
		return new Phase(new Date(), new Date(), "");
	}
	public List<User> getParticipants(long id){
		//TODO: Le pasas el id del evento y te devuelve una lista de users con los participantes del evento
		return new ArrayList<User>();
	}
	public User getIniciador(long id){
		//TODO: Le pasas el id del evento y obtenemos el user iniciador del evento
		return new User("","");
	}

	public void update(Event event)
	{
		//TODO: Le pasas el objeto evento y se actualizan los valores en la base de datos
	}

	public int getNumFases(long id)
	{
		//TODO: Le pasas el id del evento y te devuelve el numero de fases de este evento
		return -1;
	}
	
	public List<Phase> getPhases(long id)
	{
		//TODO: Le pasas el id del evento y obtienes las fases de ese evento
		return new ArrayList<Phase>();
	}

}
