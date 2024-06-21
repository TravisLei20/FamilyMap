package Service;

import DAO.*;
import Model.Authtoken;
import Model.Event;
import Model.Person;
import Model.User;
import RequestResult.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PersonServiceTest
{
  private Database db;

  private final User user = new User("HotRodRed", "Ironman", "Jarvis", "TonyStark@Stark.com",
          "Tony", "Stark", "M");
  private final Authtoken authToken = new Authtoken("authToken", user.getUsername());
  private final Event event = new Event("eventID","Ironman","id",565.2655f,95.6524f,"Germany",
          "somewhereGerman", "test",2022);
  private final Person person = new Person("id","Ironman","name","surname","m",
          "id1","id2","id3");

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
      new PersonDAO(connection).PersonDAO_insert(person);

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
  public void multiPersonPass()
  {
    MultiPersonRequest multiPersonRequest = new MultiPersonRequest(user.getUsername());
    MultiPersonResult multiPersonResult = new MultiPersonService().MultiPersonService_findMultiPerson(multiPersonRequest);
    assertTrue(multiPersonResult.isSuccess());
  }

  @Test
  public void multiPersonFail()
  {
    MultiPersonRequest multiPersonRequest = new MultiPersonRequest("WrongUsername");
    MultiPersonResult multiPersonResult = new MultiPersonService().MultiPersonService_findMultiPerson(multiPersonRequest);
    assertFalse(multiPersonResult.isSuccess());
  }

  @Test
  public void singlePersonPass()
  {
    SinglePersonRequest singlePersonRequest = new SinglePersonRequest(person.getPersonID(),user.getUsername());
    SinglePersonResult singlePersonResult = new SinglePersonService().SinglePersonService_findSinglePerson(singlePersonRequest);
    assertTrue(singlePersonResult.isSuccess());
  }

  @Test
  public void singlePersonFailWrongPersonID()
  {
    SinglePersonRequest singlePersonRequest = new SinglePersonRequest("WrongID",user.getUsername());
    SinglePersonResult singlePersonResult = new SinglePersonService().SinglePersonService_findSinglePerson(singlePersonRequest);
    assertFalse(singlePersonResult.isSuccess());
  }

  @Test
  public void singlePersonFailWrongUsername()
  {
    SinglePersonRequest singlePersonRequest = new SinglePersonRequest(person.getPersonID(),"WrongUsername");
    SinglePersonResult singlePersonResult = new SinglePersonService().SinglePersonService_findSinglePerson(singlePersonRequest);
    assertFalse(singlePersonResult.isSuccess());
  }
}
