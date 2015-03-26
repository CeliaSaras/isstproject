package es.upm.dit.isst.events.dao;

import es.upm.dit.isst.events.model.*;

public interface PhasesDAO {
	public void add(Phase phase);	//TODO: Le pasas el objeto phase y lo guardas en la bbdd
	public void remove (long id);	//TODO: Le pasas el id del objeto phase y lo borras de la bbdd
	public void update(Phase phase);	//TODO: Le pasas el objeto phase y lo actualizas en la bbdd
}
