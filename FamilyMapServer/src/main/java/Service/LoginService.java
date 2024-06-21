package Service;

import DAO.AuthTokenDAO;
import DAO.Database;
import DAO.UserDao;
import Model.Authtoken;
import Model.User;
import RequestResult.LoginRequest;
import RequestResult.LoginResult;


/**
 * Logs the user in
 * Returns an authToken.
 */
public class LoginService
{
  /**
   * Constructor
   */
  public LoginService(){}

  /**
   * Receives the Request and Returns the Result
   * @param request LoginRequest Object
   * @return LoginResult Object
   */
  public LoginResult LoginService_login(LoginRequest request)
  {
    Database db = new Database();
    try
    {
      db.openConnection();

      // could run into errors , seperate
      User user = new UserDao(db.getConnection()).UserDao_find(request.getUsername());

      if (user == null)
      {
        db.closeConnection(false);
        return new LoginResult(false, "Error: Username not found");
      }

      if (request.getPassword().equals(user.getPassword()))
      {
        Authtoken authToken = new AuthTokenDAO(db.getConnection()).AuthTokenDAO_find(request.getUsername());

        db.closeConnection(true);
        return new LoginResult(true, request.getUsername(), authToken.getAuthToken(), user.getPersonID());
      }
      else
      {
        db.closeConnection(false);
        return new LoginResult(false, "Error: Incorrect password");
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      db.closeConnection(false);
      return new LoginResult(false, "Error: in Login");
    }
  }
}
