package es.upm.dit.isst.colabEmail;

import java.awt.Event;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.*;

import es.upm.dit.isst.ColabEmail.dao.TodoDAO;
import es.upm.dit.isst.ColabEmail.dao.TodoDAOImpl;
import es.upm.dit.isst.todo.model.Todo;
import static es.upm.dit.isst.colabEmail.util.ServiceLogger.LOGGER;
import static es.upm.dit.isst.todo.util.ServiceLogger.LOGGER;

enum PHASE_STATE {
	NO_STATE(0),
	NOT_STARTED(1),
	IN_PROGRESS(2),
	FINISHED(3);
	
	private int value;
	private static int numberOfElements  = values().length;
	
	
	PHASE_STATE(int value){
		this.value = value;
				
	}
	public PHASE_STATE next()
	{
		int nextValue = this.value+1;
		nextValue %= numberOfElements;
		for (PHASE_STATE state : values())
		{
			if(state.value == nextValue)
				return state;
		}
	}
}


@SuppressWarnings("serial")
public class TXEmailServlet extends HttpServlet {
	
	
	
	private Date currentDate;
	private static final long serialVersionUID = 1L;
	private ColabEmailDAO dao;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		LOGGER.info("Executing Servlet: " + getClass().getName());
		
		currentDate = new Date();	//The current date to compare if the phase is finished
		
		dao = ColabEmailDAOImpl.getInstance();
		
		List<Event> events = dao.getEvents();
		
		events = filterEvents(events);		
		
		for (Event event : events) 
		{
			Phase currentPhase = dao.getCurrentPhase(event.getId());
			
			LOGGER.info("We are in the event: " + event.getTitle() + "with phase: "
					+ currentPhase.getName);
			PHASE_STATE state = currentPhase.getState();
			PHASE_STATE nextState = state.next();
			
			LOGGER.info("Update the state");
			
			currentPhase.setState(nextState);
			
			List<String> participants = dao.getParticipants(event.getId());
			
			for (String participant: participants){
				
				try {
					Properties props = new Properties();
					Session session = Session.getDefaultInstance(props, null);
					Message msg = new MimeMessage(session);
					msg.setFrom(new InternetAddress("noreply@famez-isst-2015.appspotmail.com", event.getTitle()));
					msg.addRecipient(Message.RecipientType.TO,
							new InternetAddress(participant + "@gmail.com", participant));
					
					String msgBody;
					
					switch(nextState)
					{
					case IN_PROGRESS:
						msg.setSubject("A new phase has started: " + currentPhase.getTitle());
						msgBody = "Votación: " + currentPhase.getQuestion();
						msgBody += "<form method=\"post\" action=\"http://1-dot-famez-isst-2015.appspot.com/newMail\"><p>Elija respuesta:<br />"; 
						
						for (String respuesta : currentPhase.getRespuestas()) {
							msgBody += "<input type=\"radio\" name=\"answer\" value=\"" + respuesta + "\"> " 
									+ respuesta + "<br />";
						}
						
						msgBody += "<input type=\"hidden\" name=\"id\" value=\"ElHashDeAlfredoParaIdUsuarioY Evento\">"; //TODO: Hacer hash md5 de id evento, id fase, id participante
						msgBody += "<input type=\"submit\"></p></form>";
						
						break;
					case FINISHED:
						//TODO: Informar del resultado final de la votacion de esa fase
						break;
						
					default:
						break;
					}
					
					msg.setContent(msgBody, "text/html");
					Transport.send(msg);

				} catch(Exception e) {
					e.printStackTrace();
					LOGGER.warning("Exception caught: " + e.toString());
				}
				
				
				
				
			}
			
			if(currentPhase.getState() == PHASE_STATE.FINISHED){
				if(event.getNFase()< event.getNFases())
				{
					event.setNFase(event.getNFase() + 1);
					dao.update(event);
				}
			}
			
			dao.update(currentPhase);
			
		}
		LOGGER.info("Return " + HttpServletResponse.SC_OK);
		resp.setStatus(HttpServletResponse.SC_OK);		
	}
	
	private List<Event> filterEvents(List<Event> events) {
		List<Event> filteredEvents = new ArrayList<Event>();
		for (Event event : events) {
		
			Phase currentPhase = dao.getCurrentPhase(event.getId());
			
			PHASE_STATE oldState = phase.getState();
			
			Date phaseStartDate = phase.getStartDate();
			Date phaseEndDate = phase.getEndDate();
			
			PHASE_STATE newState = PHASE_STATE.NO_STATE;
			
			if(currentDate.before(phaseStartDate))
				newState = PHASE_STATE.NOT_STARTED;
			else if(currentDate.after(phaseStartDate) && currentDate.before(phaseEndDate))
				newState = PHASE_STATE.IN_PROGRESS;
			else if(currentDate.after(phaseEndDate))
				newState = PHASE_STATE.FINISHED;
			
			if(oldState == newState)	//If the phase state has not changed, then we continue
				continue;
			
			assert(newState == oldState.next());		//Assertion
			
			filteredEvents.add(event);
			
		}
		return filteredEvents;
	}
	
}
