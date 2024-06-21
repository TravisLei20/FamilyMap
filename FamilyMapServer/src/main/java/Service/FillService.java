package Service;

import DAO.Database;
import DAO.EventDAO;
import DAO.PersonDAO;
import DAO.UserDao;
import RequestResult.FillRequest;
import RequestResult.FillResult;

/**
 * Populates the server's database with generated data for the specified username.
 * The required "username" parameter must be a user already registered with the server.
 * If there is any data in the database already associated with the given username, it is deleted.
 * The optional "generations" parameter lets the caller specify the number of generations of ancestors to be generated, and must be a non-negative integer (the default is 4, which results in 31 new persons each with associated events).
 * More details can be found in the earlier section titled Family History Information Generation
 */
public class FillService
{
  /**
   * Constructor
   */
  public FillService(){}

  /**
   * Receives the Request and Returns the Result
   * @param request FillRequest Object
   * @return FillResult Object
   */
  public FillResult FillService_fill(FillRequest request)
  {
    Database db = new Database();
    try
    {
      db.openConnection();

      if (new UserDao(db.getConnection()).UserDao_find(request.getUsername()) != null)
      {
        new PersonDAO(db.getConnection()).PersonDAO_delete(request.getUsername());
        new EventDAO(db.getConnection()).EventDAO_delete(request.getUsername());

        if (request.getGenerationNum() < 0)
        {
          request.setGenerationNum(4);
        }

        GenerateHistoryHelper gen = new GenerateHistoryHelper(request.getGenerationNum(), db, request.getUsername());

        db.closeConnection(true);

        StringBuilder str = new StringBuilder();
        str.append("Successfully added ");
        str.append(gen.getPeopleAdded());
        str.append(" persons and ");
        str.append(gen.getEventsAdded());
        str.append(" events to the database.");

        return new FillResult(true,str.toString());
      }
      else
      {
        db.closeConnection(false);
        return new FillResult(false,"Error: Wrong username");
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      db.closeConnection(false);
      return new FillResult(false, "Error: Something went wrong in fill");
    }
  }
}
