package es.upm.dit.isst.colabEmail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.*;

import static es.upm.dit.isst.colabEmail.util.ServiceLogger.LOGGER;
import es.upm.dit.isst.events.model.*;
import es.upm.dit.isst.events.dao.*;


public class TXEmailServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private Date currentDate;
	private EventsDAO eventDao;
	private PhasesDAO phaseDao;
	private UserEventDAO userEventDAO;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		LOGGER.info("Executing Servlet: " + getClass().getName());
		
		currentDate = new Date();	//The current date to check if the phase is finished
		eventDao = EventsDAOImpl.getInstance();
//		phaseDao = PhasesDAOImpl.getInstance();		//TODO: Get static instance
//		userEventDAO = UserEventDAO.getInstance();	//TODO: Get static instance
		List<Event> events = eventDao.listEvents();
		
		events = filterEvents(events);		
		
		for (Event event : events) 
		{
			Phase currentPhase = eventDao.getCurrentPhase(event.getId());
			
			LOGGER.info("We are in the event: " + event.getTitle() + "with phase: "
					+ currentPhase.getTitle());

			PHASE_STATE nextState = currentPhase.getState().next();
			
			LOGGER.info("Update the state");
			
			currentPhase.setState(nextState);
			
			List<User> participants = eventDao.getParticipants(event.getId());
			
			for (User participant: participants){
				
				try {
					Properties props = new Properties();
					Session session = Session.getDefaultInstance(props, null);
					Message msg = new MimeMessage(session);
					msg.setFrom(new InternetAddress("noreply@famez-isst-2015.appspotmail.com", event.getTitle()));
					msg.addRecipient(Message.RecipientType.TO,
							new InternetAddress(participant.getEmail() + "@gmail.com", participant.getEmail()));
					
					String msgBody="";
					
					switch(nextState)
					{
					case IN_PROGRESS:
						
						String myHash = hashUserEventPhase(participant.getId(), event.getId(), currentPhase.getId());
						
						LOGGER.info("Creating hash: " + myHash);
						
						userEventDAO.addHash(participant.getId(), event.getId());
						
						msg.setSubject("A new phase has started: " + currentPhase.getTitle());
						msgBody = "Votación: " + currentPhase.getPregunta() + "\n";
						msgBody += "<form method=\"post\" action=\"http://1-dot-famez-isst-2015.appspot.com/newMail\"><p>Elija respuesta:<br />"; 
						for (String respuesta : currentPhase.getRespuestas()) {
							msgBody += "<input type=\"radio\" name=\"answer\" value=\"" + respuesta + "\"> " 
									+ respuesta + "<br />";
						}
						
						msgBody += "<input type=\"hidden\" name=\"id\" value=\"" 
						+ myHash + "\">";
						msgBody += "<input type=\"submit\"></p></form>";
						msgBody += "\nYou have until " + currentPhase.getDateFin() + " to vote";
						break;
					case FINISHED:
						msg.setSubject("A phase finished: " + currentPhase.getTitle());
						msgBody = "Question: " + currentPhase.getPregunta() + "\n";
						msgBody = "Answers: \n";
						for (String respuesta : currentPhase.getRespuestas()) {
							msgBody += respuesta + ": " + currentPhase.getPorcentajeRespuesta(respuesta) + "\n";	
						}
						msgBody+="The most voted response is " + currentPhase.getMostVotedResponse();
						
						break;
					default:
						LOGGER.severe("This case should never happen!!!");
					}
					
					msg.setContent(msgBody, "text/html");
					Transport.send(msg);

				} catch(Exception e) {
					e.printStackTrace();
					LOGGER.warning("Exception caught: " + e.toString());
				}
				
			}
			
			if(currentPhase.getState() == PHASE_STATE.FINISHED){
				if(event.getNumPhase()< eventDao.getNumFases(event.getId()))
				{
					event.setNumPhase(event.getNumPhase() + 1);
					eventDao.update(event);
				}
			}
			
			phaseDao.update(currentPhase);
			
		}
		LOGGER.info("Return " + HttpServletResponse.SC_OK);
		resp.setStatus(HttpServletResponse.SC_OK);		
	}
//	
//	private List<Phase> filterPhases(List<Phase> phases) {
//		List<Phase> filteredPhases = new ArrayList<Phase>();
//		for (Phase phase : phases) {
//		
//			
//			PHASE_STATE oldState = phase.getState();
//			
//			Date phaseStartDate = phase.getFechaIni();
//			Date phaseEndDate = phase.getFechaFin();
//			
//			PHASE_STATE newState = PHASE_STATE.NO_STATE;
//			
//			if(currentDate.before(phaseStartDate))
//				newState = PHASE_STATE.NOT_STARTED;
//			else if(currentDate.after(phaseStartDate) && currentDate.before(phaseEndDate))
//				newState = PHASE_STATE.IN_PROGRESS;
//			else if(currentDate.after(phaseEndDate))
//				newState = PHASE_STATE.FINISHED;
//			
//			if(oldState == newState)	//If the phase state has not changed, then we continue
//				continue;
//			
////			assert(newState == oldState.next());		//Assertion
//			
//			if(newState != oldState.next())
//			{
//				LOGGER.severe("State changed unespectly\n" + phase);
//				continue;
//			}
//			
//			//Update the state of the phase
//			
//			filteredPhases.add(phase);
//			
//		}
//		return filteredPhases;
//	}
	
	private List<Event> filterEvents(List<Event> events) {
		List<Event> filteredEvents = new ArrayList<Event>();
		for (Event event : events) {
		
			Phase currentPhase = eventDao.getCurrentPhase(event.getId());
			
			PHASE_STATE oldState = currentPhase.getState();
			
			Date phaseStartDate = currentPhase.getFechaIni();
			Date phaseEndDate = currentPhase.getFechaFin();
			
			PHASE_STATE newState = PHASE_STATE.NO_STATE;
			
			if(currentDate.before(phaseStartDate))
				newState = PHASE_STATE.NOT_STARTED;
			else if(currentDate.after(phaseStartDate) && currentDate.before(phaseEndDate))
				newState = PHASE_STATE.IN_PROGRESS;
			else if(currentDate.after(phaseEndDate))
				newState = PHASE_STATE.FINISHED;
			
			if(oldState == newState)	//If the phase state has not changed, then we continue
				continue;
			
//			assert(newState == oldState.next());		//Assertion
			
			if(newState != oldState.next())
			{
				LOGGER.severe("State changed unespectly\n" + currentPhase + "\n" + event);
				continue;
			}
			
			//Update the state of the phase
			
			filteredEvents.add(event);
			
		}
		return filteredEvents;
	}
	
	private String hashUserEventPhase(long userId, long eventId, long phaseId)
	{
		String concat = "&" + userId + "@" + eventId + "_" + phaseId + "/";
		
		
		try {
			byte[] stringBytes = concat.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hash = md.digest(stringBytes);
			return new String(hash, "UTF-8");
		} catch (NoSuchAlgorithmException e) {
			LOGGER.severe("Hash not found: " + e);
		}catch (UnsupportedEncodingException e) {
			LOGGER.severe("Encoding exception: " + e);
		}
		
		return null;
		
	}
	
}
