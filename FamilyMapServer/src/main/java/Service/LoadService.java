package Service;

import DAO.*;
import Model.Authtoken;
import Model.Event;
import Model.Person;
import Model.User;
import RequestResult.LoadRequest;
import RequestResult.LoadResult;

import java.util.UUID;

/**
 * Clears all data from the database (just like the /clear API)
 * Loads the user, person, and event data from the request body into the database.
 */
public class LoadService
{
  /**
   * Constructor
   */
  public LoadService(){}

  /**
   * Receives the Request and Returns the Result
   * @param request the request for load
   * @return the result
   */
  public LoadResult LoadService_load(LoadRequest request)
  {
    Database db = new Database();
    try
    {
      db.openConnection();
      new ClearDatabaseHelper(db);

      User[] users = request.getUsers();
      Person[] persons = request.getPersons();
      Event[] events = request.getEvents();

      int addedUsers = 0;
      int addedPersons = 0;
      int addedEvents = 0;

      for (User user : users)
      {
        new UserDao(db.getConnection()).UserDao_insert(user);
        new AuthTokenDAO(db.getConnection()).AuthTokenDAO_insert(new Authtoken(UUID.randomUUID().toString(), user.getUsername()));
        addedUsers++;
      }
      for (Person person : persons)
      {
        new PersonDAO(db.getConnection()).PersonDAO_insert(person);
        addedPersons++;
      }
      for (Event event : events)
      {
        new EventDAO(db.getConnection()).EventDAO_insert(event);
        addedEvents++;
      }

      StringBuilder str = new StringBuilder();
      str.append("Successfully added ");
      str.append(addedUsers);
      str.append(" users, ");
      str.append(addedPersons);
      str.append(" persons, and ");
      str.append(addedEvents);
      str.append(" events to the database");

      db.closeConnection(true);

      return new LoadResult(true, str.toString());
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      db.closeConnection(false);
      return new LoadResult(false, "Error: Something went wrong in Load.");
    }
  }
}
