package Service;

import DAO.*;
import RequestResult.ClearRequest;
import RequestResult.ClearResult;

/**
 * Deletes ALL data from the database, including user, authtoken, person, and event data
 */
public class ClearService
{
  /**
   * Constructor
   */
  public ClearService(){}

  /**
   * Receives the Request and Returns the Result
   * @param request ClearRequest Object
   * @return returns the Result Object
   */
  public ClearResult ClearService_clear(ClearRequest request)
  {
    Database db = new Database();
    try
    {
      db.openConnection();

      new ClearDatabaseHelper(db);

      db.closeConnection(true);

      return new ClearResult(true, "Clear succeeded.");
    }
    catch (Exception ex)
    {
      ex.printStackTrace();

      db.closeConnection(false);

      return new ClearResult(false, "Error: Something went wrong with the database");
    }
  }
}
