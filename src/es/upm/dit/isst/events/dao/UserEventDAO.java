package es.upm.dit.isst.events.dao;

public interface UserEventDAO {
	public void add(long userId, long eventId);		//TODO: Le pasas el id de usuario y el id de evento y lo guarda 
													//en la bbdd
	public void remove(long userId, long eventId);	//TODO: Le pasas el id de usuario y el id de evento y lo borras 
	//de la bbdd
	
}
