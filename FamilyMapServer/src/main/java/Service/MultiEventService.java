package Service;

import DAO.Database;
import DAO.EventDAO;
import Model.Event;
import RequestResult.MultiEventRequest;
import RequestResult.MultiEventResult;

import java.util.List;

/**
 * Returns ALL events for ALL family members of the current user. The current user is determined from the provided auth token.
 */
public class MultiEventService
{
  /**
   * Constructor
   */
  public MultiEventService(){}

  /**
   * Receives the Request and Returns the Result
   * @param request MultiEventRequest Object
   * @return MultiEventResult Object
   */
  public MultiEventResult MultiEventService_findMultiEvent(MultiEventRequest request)
  {
    Database db = new Database();
    try
    {
      db.openConnection();

      List<Event> events = new EventDAO(db.getConnection()).EventDAO_findWithAssociatedUsername(request.getAssociatedUsername());

      db.closeConnection(true);

      if (events.size() != 0)
      {
        return new MultiEventResult(true,events);
      }
      else
      {
        return new MultiEventResult(false,events);
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      db.closeConnection(false);
      return new MultiEventResult(false, "Error: Something went wrong in multiEvent.");
    }
  }
}
