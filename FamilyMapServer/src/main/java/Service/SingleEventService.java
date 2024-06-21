package Service;

import DAO.Database;
import DAO.EventDAO;
import Model.Event;
import RequestResult.SingleEventRequest;
import RequestResult.SingleEventResult;

/**
 * Returns the single Event object with the specified ID (if the event is associated with the current user).
 * The current user is determined by the provided authtoken.
 */
public class SingleEventService
{
  /**
   * Constructor
   */
  public SingleEventService() {
  }

  /**
   * Receives the Request and Returns the Result
   * @param request SingleEventRequest Object
   * @return SingleEventResult Object
   */
  public SingleEventResult SingleEventService_findEvent(SingleEventRequest request)
  {
    Database db = new Database();
    try
    {
      db.openConnection();

      Event event = new EventDAO(db.getConnection()).EventDAO_find(request.getEventID());

      if (event == null)
      {
        db.closeConnection(false);
        return new SingleEventResult(false, "Error: Event not found");
      }

      if (!(request.getAssociatedUsername().equals(event.getAssociatedUsername())))
      {
        db.closeConnection(false);
        return new SingleEventResult(false, "Error: Event not associated to user.");
      }

      db.closeConnection(true);
      return new SingleEventResult(true, event.getEventType(),event.getPersonID(),event.getCity(),
              event.getCountry(),event.getLatitude(),event.getLongitude(),event.getYear(),event.getEventID(),
              event.getAssociatedUsername());

    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      db.closeConnection(false);
      return new SingleEventResult(false, "Error: in SingleEvent");
    }
  }
}
