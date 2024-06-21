package Service;

import DAO.*;
import Model.Authtoken;
import Model.Event;
import Model.User;
import RequestResult.MultiEventRequest;
import RequestResult.MultiEventResult;
import RequestResult.SingleEventRequest;
import RequestResult.SingleEventResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventServiceTest
{
  private Database db;

  private final User user = new User("HotRodRed", "Ironman", "Jarvis", "TonyStark@Stark.com",
          "Tony", "Stark", "M");
  private final Authtoken authToken = new Authtoken("authToken", user.getUsername());
  private final Event event = new Event("eventID","Ironman","id",565.2655f,95.6524f,"Germany",
          "somewhereGerman", "test",2022);


  @BeforeEach
  public void setUp()
  {
    db = new Database();
    try
    {
      db.getConnection();

      Connection connection = db.getConnection();

      new AuthTokenDAO(connection).AuthTokenDAO_insert(authToken);
      new UserDao(connection).UserDao_insert(user);
      new EventDAO(connection).EventDAO_insert(event);

      new GenerateHistoryHelper(5,db,user.getUsername());

      db.closeConnection(true);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      db.closeConnection(false);
    }
  }

  @AfterEach
  public void tearDown()
  {
    db = new Database();
    try
    {
      db.getConnection();
      new ClearDatabaseHelper(db);
      db.closeConnection(true);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      db.closeConnection(false);
    }
  }

  @Test
  public void multiEventPass()
  {
    MultiEventRequest multiEventRequest = new MultiEventRequest(user.getUsername());
    MultiEventResult multiEventResult = new MultiEventService().MultiEventService_findMultiEvent(multiEventRequest);
    assertTrue(multiEventResult.isSuccess());
  }

  @Test
  public void multiEventFail()
  {
    MultiEventRequest multiEventRequest = new MultiEventRequest("WrongUsername");
    MultiEventResult multiEventResult = new MultiEventService().MultiEventService_findMultiEvent(multiEventRequest);
    assertFalse(multiEventResult.isSuccess());
  }

  @Test
  public void singleEventPass()
  {
    SingleEventRequest singleEventRequest = new SingleEventRequest("eventID", user.getUsername());
    SingleEventResult singleEventResult = new SingleEventService().SingleEventService_findEvent(singleEventRequest);
    assertTrue(singleEventResult.isSuccess());
  }

  @Test
  public void singleEventFailWrongID()
  {
    SingleEventRequest singleEventRequest = new SingleEventRequest("WrongID", user.getUsername());
    SingleEventResult singleEventResult = new SingleEventService().SingleEventService_findEvent(singleEventRequest);
    assertFalse(singleEventResult.isSuccess());
  }

  @Test
  public void singleEventFailWrongUsername()
  {
    SingleEventRequest singleEventRequest = new SingleEventRequest("eventID", "WrongUsername");
    SingleEventResult singleEventResult = new SingleEventService().SingleEventService_findEvent(singleEventRequest);
    assertFalse(singleEventResult.isSuccess());
  }

}
