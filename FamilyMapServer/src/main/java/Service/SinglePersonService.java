package Service;

import DAO.Database;
import DAO.PersonDAO;
import Model.Person;
import RequestResult.SinglePersonRequest;
import RequestResult.SinglePersonResult;

/**
 * Returns the single Person object with the specified ID (if the person is associated with the current user).
 * The current user is determined by the provided authtoken.
 */
public class SinglePersonService
{
  /**
   * Constructor
   */
  public SinglePersonService() {
  }

  /**
   * Receives the Request and Returns the Result
   * @param request SinglePersonRequest Object
   * @return SinglePersonResult Object
   */
  public SinglePersonResult SinglePersonService_findSinglePerson(SinglePersonRequest request)
  {
    Database db = new Database();
    try
    {
      db.openConnection();

      Person person = new PersonDAO(db.getConnection()).PersonDAO_find(request.getPersonID());

      if (person == null)
      {
        db.closeConnection(false);
        return new SinglePersonResult(false, "Error: Person not found");
      }

      if (!(request.getUserName().equals(person.getAssociatedUsername())))
      {
        db.closeConnection(false);
        return new SinglePersonResult(false, "Error: Person not associated to user.");
      }

      db.closeConnection(true);
      return new SinglePersonResult(true, person.getFirstName(),person.getLastName(),person.getGender(),
              person.getPersonID(),person.getSpouseID(),person.getFatherID(),person.getMotherID(),person.getAssociatedUsername());

    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      db.closeConnection(false);
      return new SinglePersonResult(false, "Error: in SinglePerson");
    }
  }
}
