package Service;

import DAO.*;
import Model.Authtoken;
import Model.Event;
import Model.Person;
import Model.User;
import RequestResult.ClearRequest;
import RequestResult.ClearResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ClearServiceTest
{
  private ClearRequest clearRequest;
  private ClearResult clearResult;
  private Database db;
  private User user;
  private Authtoken authToken;
  private Event event;
  private Person person;


  @BeforeEach
  public void setUp() throws DataAccessException
  {
    db = new Database();
    try
    {
      db.openConnection();

      user = new User("HotRodRed", "Ironman", "Jarvis", "TonyStark@Stark.com",
              "Tony", "Stark", "M");
      authToken = new Authtoken("authToken", user.getUsername());

      person = new Person("id","Ironman","name","surname","m",
              "id1","id2","id3");

      float num =(float) 23.456;
      event = new Event("eventID","Ironman","id",num,num,"Germany",
              "somewhereGerman", "test",2022);


      Connection connection = db.getConnection();

      new UserDao(connection).UserDao_insert(user);
      new AuthTokenDAO(connection).AuthTokenDAO_insert(authToken);
      new EventDAO(connection).EventDAO_insert(event);
      new PersonDAO(connection).PersonDAO_insert(person);

      db.closeConnection(true);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      db.closeConnection(false);
    }
  }

  @Test
  public void clearPass()
  {
    clearRequest = new ClearRequest();
    clearResult = new ClearService().ClearService_clear(clearRequest);
    assertEquals(true,clearResult.isSuccess());
  }

  @Test
  public void clearCheckPass()
  {
    clearRequest = new ClearRequest();
    clearResult = new ClearService().ClearService_clear(clearRequest);
    assertEquals(true,clearResult.isSuccess());

    db = new Database();
    try
    {
      db.openConnection();
      Connection connection = db.getConnection();

      assertNull(new UserDao(connection).UserDao_find(user.getUsername()));
      assertNull(new PersonDAO(connection).PersonDAO_find(person.getPersonID()));
      assertNull(new EventDAO(connection).EventDAO_find(event.getEventID()));
      assertNull(new AuthTokenDAO(connection).AuthTokenDAO_find(authToken.getUsername()));

      db.closeConnection(true);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      db.closeConnection(false);
    }
  }
}
