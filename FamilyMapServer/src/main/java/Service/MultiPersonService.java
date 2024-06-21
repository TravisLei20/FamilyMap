package Service;

import DAO.Database;
import DAO.PersonDAO;
import Model.Person;
import RequestResult.MultiPersonRequest;
import RequestResult.MultiPersonResult;

import java.util.List;

/**
 * Returns ALL family members of the current user. The current user is determined by the provided authtoken.
 */
public class MultiPersonService
{
  /**
   * Constructor
   */
  public MultiPersonService() {}

  /**
   * Receives the Request and Returns the Result
   * @param request MultiPersonRequest Object
   * @return MultiPersonResult Object
   */
  public MultiPersonResult MultiPersonService_findMultiPerson(MultiPersonRequest request)
  {
    Database db = new Database();
    try
    {
      db.openConnection();

      List<Person> people = new PersonDAO(db.getConnection()).PersonDAO_findWithAssociatedUsername(request.getAssociatedUsername());

      db.closeConnection(true);

      if (people.size() != 0)
      {
        return new MultiPersonResult(true,people);
      }
      else
      {
        return new MultiPersonResult(false,people);
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      db.closeConnection(false);
      return new MultiPersonResult(false, "Error: Something went wrong in multiPerson.");
    }
  }
}
