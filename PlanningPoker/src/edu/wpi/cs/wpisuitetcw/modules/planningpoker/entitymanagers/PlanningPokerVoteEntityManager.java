/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.entitymanagers;

import java.util.List;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerVote;
import edu.wpi.cs.wpisuitetng.Session; //Not sure
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;


/**
 * @author Nick Kalamvokis and Matt Suarez
 *
 */

public class PlanningPokerVoteEntityManager implements
EntityManager<PlanningPokerVote> {
	
	/** The database */
	Data db;

	/**
	 * Constructs the entity manager. This constructor is called by
	 * {@link edu.wpi.cs.wpisuitetng.ManagerLayer#ManagerLayer()}. To make sure
	 * this happens, be sure to place add this entity manager to the map in the
	 * ManagerLayer file.
	 * 
	 * @param db
	 *            a reference to the persistent database
	 */
	public PlanningPokerVoteEntityManager(Data db) {
		this.db = db;
	}
	
	
	// Need review, not sure what to do with content and saving to database
public PlanningPokerVote makeEntity(Session s, String content) throws BadRequestException, ConflictException, WPISuiteException {
	
	PlanningPokerVote newVote = new PlanningPokerVote();
	
	// try to save the vote to the database
	// throw WPISuiteException if this doesn't work
	if (!db.save(newVote, s.getProject())) {
		throw new WPISuiteException();
	}
	
	
	// return the new vote
	return newVote;
}
	
	/* Retrieve */	
	/**
	 * Retrieves the entity with the given unique identifier, id.
	 * @param id	the unique identifier value
	 * @return	the entity with the given ID
	 * @throws NotFoundException if entity does not exist
	 */
	public PlanningPokerVote[] getEntity(Session s, String id) throws NotFoundException, WPISuiteException;
	
	/**
	 * Retrieves all entities of Model class T
	 * @return	an ArrayList<T> with all instances of T
	 */
	public PlanningPokerVote[] getAll(Session s) throws WPISuiteException{
		List<Model> votes = db.retrieveAll(new PlanningPokerVote(),
				s.getProject());
		
		// return the list of votes as an array
		return votes.toArray(new PlanningPokerVote[0]);
	}
	
	
	/* Update */
	/**
	 * 
	 * @param s the session of the User executing this action
	 * @param content - JSON representation of the model updates
	 * @return the updated model
	 * @throws WPISuiteException
	 */
	public PlanningPokerVote update(Session s, String content) throws WPISuiteException;
	
	/**
	 * Saves the given model of class T to the database
	 * @param model	the Model to update.
	 */
	public void save(Session s, T model) throws WPISuiteException;
	
	/* Delete */
	/**
	 * Deletes the entity with the given unique identifier, id.
	 * @param id	the unique identifier for the entity
	 */
	public boolean deleteEntity(Session s, String id) throws WPISuiteException;
	
	/* Advanced Get*/
	/**
	 * This method is accessed through the API by a GET request to the AdvancedAPI
	 * @param s	The session that requested this action
	 * @param args[] A String array containing the entire path of the GET that initiated this action
	 */
	public String advancedGet(Session s, String[] args) throws WPISuiteException;
	
	/**
	 * Deletes all entities of Model class T
	 */
	public void deleteAll(Session s) throws WPISuiteException;
	
	/* Utility Methods */
	/**
	 * 
	 * @return	the number of records of this Manager's Model class T
	 */
	public int Count() throws WPISuiteException;

	/* Advanced Put*/
	/**
	 * **************
	 * A note about the content body.  Must not contain any line breaks.  Only the first line is passed 
	 * to this function.                                            
	 * **************
	 * This method is accessed through the API by a PUT request to the AdvancedAPI
	 * @param s	The session that requested this action
	 * @param args[] A String array containing the entire path of the PUT that initiated this action
	 * @param content The content body of the request
	 */
	public String advancedPut(Session s, String[] args, String content) throws WPISuiteException;

	/*Advanced Post*/
	/**
	 * 
	 * **********************
	 * A note about advanced post.  the content body should not contain any line breaks.
	 * only the first line will be passed through to the function.
	 * **********************
	 * This method is similar to the Advanced Put method, except that where ADvanced Put recieves the entire path
	 * as a String array, Advanced Post only receives the third path argument, for example API/advanced/module/model/argument
	 * The reasoning behind this was to allow module developers who required more methods a way to multiplex this 
	 * one call into multiple different functions.  By using the argument as a key in a switch statement or if else block, 
	 * they can process the content payload through an unlimited number of functions.
	 * 
	 * @param s - The session that requested this action
	 * @param string - A path identifier [/module/model/identifier]
	 * @param content - The String payload of the request
	 * @return - a String to be sent back as the body of the request
	 */
	public String advancedPost(Session s, String string, String content) throws WPISuiteException;

	@Override
	public void save(Session s, PlanningPokerVote model)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		
	}
}
