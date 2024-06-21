package Service;

import DAO.AuthTokenDAO;
import DAO.Database;

import Model.Authtoken;

/**
 * The AuthorizationHelper class is used to get the AuthToken from the database with the authTokenID is given.
 */
public class AuthorizationHelper
{
  /**
   * Constructor
   */
  public AuthorizationHelper(){}

  /**
   * Used to retrieve and return an AuthToken from the Database
   * @param authToken the given authToken
   * @return the AuthToken from the database
   */
  public Authtoken AuthorizationHelper_auth(String authToken)
  {
    Database db = new Database();

    try
    {
      db.openConnection();

      Authtoken authtoken1= new AuthTokenDAO(db.getConnection()).AuthTokenDAO_findWithAuthToken(authToken);

      db.closeConnection(true);

      return authtoken1;
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      db.closeConnection(false);
      return null;
    }
  }
}
