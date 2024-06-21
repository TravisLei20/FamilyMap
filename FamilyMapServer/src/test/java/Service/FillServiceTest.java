package Service;

import DAO.*;
import Model.Authtoken;
import Model.Event;
import Model.User;
import RequestResult.FillRequest;
import RequestResult.FillResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FillServiceTest
{
  private Database db;
  private final User user = new User("HotRodRed", "Ironman", "Jarvis", "TonyStark@Stark.com",
                                         "Tony", "Stark", "M");
  private final Authtoken authToken = new Authtoken("authToken", user.getUsername());
  private final Event event = new Event("eventID",user.getUsername(),"id",565.2655f,95.6524f,"Germany",
          "somewhereGerman", "test",2022);

  @BeforeEach
  public void setUp() throws DataAccessException
  {
    db = new Database();
    try
    {
      db.openConnection();
      Connection connection = db.getConnection();

      new UserDao(connection).UserDao_insert(user);
      new AuthTokenDAO(connection).AuthTokenDAO_insert(authToken);
      new EventDAO(connection).EventDAO_insert(event);

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
  public void addedDefaultPass()
  {
    FillRequest fillRequest = new FillRequest(user.getUsername());
    FillResult fillResult = new FillService().FillService_fill(fillRequest);
    assertTrue(fillResult.isSuccess());
  }

  @Test
  public void addedSpecificPass()
  {
    FillRequest fillRequest = new FillRequest(user.getUsername(),5);
    FillResult fillResult = new FillService().FillService_fill(fillRequest);
    assertTrue(fillResult.isSuccess());
  }

  @Test
  public void attemptUserWrongUsernameFail()
  {
    FillRequest fillRequest = new FillRequest("Wrong");
    FillResult fillResult = new FillService().FillService_fill(fillRequest);
    assertFalse(fillResult.isSuccess());
  }
}
