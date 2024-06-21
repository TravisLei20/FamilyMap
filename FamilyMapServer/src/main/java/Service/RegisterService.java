package Service;

import DAO.*;
import Model.Authtoken;
import Model.Person;
import Model.User;
import RequestResult.RegisterRequest;
import RequestResult.RegisterResult;

import java.util.UUID;

/**
 * Creates a new user account (user row in the database)
 * Generates 4 generations of ancestor data for the new user (just like the /fill endpoint if called with a generations value of 4 and this new user’s username as parameters)
 * Logs the user in
 * Returns an authtoken
 */
public class RegisterService
{
  /**
   * Constructor
   */
  public RegisterService(){}

  /**
   * Receives the Request and Returns the Result
   * @param request RegisterRequest Object
   * @return RegisterResult Object
   */
  public RegisterResult RegisterService_register(RegisterRequest request)
  {
    Database db = new Database();
    try
    {
      db.openConnection();

      User checkUsername = new UserDao(db.getConnection()).UserDao_find(request.getUsername());

      if (checkUsername != null)
      {
        db.closeConnection(false);
        return new RegisterResult(false,"Error: Username already used");
      }

      String personID = UUID.randomUUID().toString();
      User user = new User(personID,request.getUsername(), request.getPassword(), request.getEmail(),
              request.getFirstName(), request.getLastName(), request.getGender());
      new UserDao(db.getConnection()).UserDao_insert(user);

      String authTokenID = UUID.randomUUID().toString();
      Authtoken authToken = new Authtoken(authTokenID,request.getUsername());

      new AuthTokenDAO(db.getConnection()).AuthTokenDAO_insert(authToken);

      new GenerateHistoryHelper(4,db, user.getUsername());

      db.closeConnection(true);

      return new RegisterResult(true, request.getUsername(),authTokenID,personID);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      db.closeConnection(false);
      return new RegisterResult(false, "Error in registration");
    }
  }
}
