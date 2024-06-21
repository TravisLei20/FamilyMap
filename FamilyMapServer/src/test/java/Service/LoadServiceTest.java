package Service;

import DAO.*;
import Model.Authtoken;
import Model.Event;
import Model.Person;
import Model.User;
import RequestResult.LoadRequest;
import RequestResult.LoadResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class LoadServiceTest
{
  private Database db;

  private final User user = new User("HotRodRed", "Ironman", "Jarvis", "TonyStark@Stark.com",
          "Tony", "Stark", "M");
  private final Authtoken authToken = new Authtoken("authToken", user.getUsername());
  private final Person person = new Person("id","Ironman","name","surname","m",
          "id1","id2","id3");
  private final Event event = new Event("eventID","Ironman","id",565.2655f,95.6524f,"Germany",
          "somewhereGerman", "test",2022);
  private final User user1 = new User("Superman123", "Superman", "laserVision", "super@gmail.com",
          "Something", "glasses", "M");
  private final User user2 = new User("Bat", "Batman", "Alf", "Batman@yahoo.com",
          "Bruce", "Wayne", "M");
  private final Person person1 = new Person("King_Charles", "Ancestor_Of_Charles", "Charles",
          "BearClawson", "M", "Queen_Anna", "Henry_The_Conqueror", "Maria_The_Saint");
  private final Person person2 = new Person("Homer123", "Homer_Doughnut", "Homer",
          "Simpson", "M", "Marge_Blue", "Abraham_WW2", null);
  private final Event event1 = new Event("Biking_123A", "Gale", "Gale123A",
          35.9f, 140.1f, "Japan", "Ushiku",
          "Biking_Around", 2016);
  private final Event event2 = new Event("flying", "Tim", "tim123",
          35.9f, 140.1f, "Europe", "Paris",
          "Biking_Around", 2016);


  @BeforeEach
  public void setUp()
  {
    db = new Database();
    try
    {
      db.getConnection();

      User user = new User("HotRodRed", "Ironman", "Jarvis", "TonyStark@Stark.com",
              "Tony", "Stark", "M");
      Authtoken authToken = new Authtoken("authToken", user.getUsername());

      Person person = new Person("id","Ironman","name","surname","m",
              "id1","id2","id3");

      Event event = new Event("eventID","Ironman","id",565.2655f,95.6524f,"Germany",
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
  public void loadedPass()
  {
    User user1 = new User("HotRodRed", "Ironman", "Jarvis", "IamIronman@stark.com",
            "Tony", "Stark", "M");
    User user2 = new User("Bat", "Batman", "Alf", "Batman@yahoo.com",
            "Bruce", "Wayne", "M");
    Person person1 = new Person("King_Charles", "Ancestor_Of_Charles", "Charles",
            "BearClawson", "M", "Queen_Anna", "Henry_The_Conqueror", "Maria_The_Saint");
    Person person2 = new Person("Homer123", "Homer_Doughnut", "Homer",
            "Simpson", "M", "Marge_Blue", "Abraham_WW2", "something");
    Event event1 = new Event("Biking_123A", "Gale", "Gale123A",
            35.9f, 140.1f, "Japan", "Ushiku",
            "Biking_Around", 2016);
    Event event2 = new Event("flying", "Tim", "tim123",
            35.9f, 140.1f, "Europe", "Paris",
            "Biking_Around", 2016);

    User[] users = new User[2];
    Person[] persons = new Person[2];
    Event[] events = new Event[2];

    users[0] = user1;
    users[1] = user2;
    persons[0] = person1;
    persons[1] = person2;
    events[0] = event1;
    events[1] = event2;

    LoadRequest loadRequest = new LoadRequest(users,persons,events);
    LoadResult loadResult = new LoadService().LoadService_load(loadRequest);

    db = new Database();
    try
    {
      db.getConnection();
      Connection connection = db.getConnection();

      assertTrue(loadResult.isSuccess());

      assertEquals(user1,new UserDao(connection).UserDao_find(user1.getUsername()));
      assertEquals(user2,new UserDao(connection).UserDao_find(user2.getUsername()));

      assertEquals(person1,new PersonDAO(connection).PersonDAO_find(person1.getPersonID()));
      assertEquals(person2,new PersonDAO(connection).PersonDAO_find(person2.getPersonID()));

      assertEquals(event1,new EventDAO(connection).EventDAO_find(event1.getEventID()));
      assertEquals(event2,new EventDAO(connection).EventDAO_find(event2.getEventID()));

      db.closeConnection(true);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      db.closeConnection(false);
    }
  }

  @Test
  public void loadClearPass()
  {
    User[] users = new User[2];
    Person[] persons = new Person[2];
    Event[] events = new Event[2];

    users[0] = user1;
    users[1] = user2;
    persons[0] = person1;
    persons[1] = person2;
    events[0] = event1;
    events[1] = event2;

    LoadRequest loadRequest = new LoadRequest(users,persons,events);
    LoadResult loadResult = new LoadService().LoadService_load(loadRequest);

    db = new Database();
    try
    {
      db.getConnection();
      Connection connection = db.getConnection();

      assertTrue(loadResult.isSuccess());

      assertNull(new UserDao(connection).UserDao_find(user.getUsername()));
      assertNull(new EventDAO(connection).EventDAO_find(event.getEventID()));
      assertNull(new PersonDAO(connection).PersonDAO_find(person.getPersonID()));
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
