package Service;

import DAO.AuthTokenDAO;
import DAO.DataAccessException;
import DAO.Database;
import DAO.UserDao;
import Model.Authtoken;
import Model.User;
import RequestResult.LoginRequest;
import RequestResult.LoginResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginServiceTest
{
  private LoginRequest loginRequest;
  private LoginResult loginResult;
  private Database db;
  private User user;
  private Authtoken authToken;


  @BeforeEach
  public void setUp() throws DataAccessException
  {
    db = new Database();
    try
    {
      db.openConnection();

      user = new User("HotRodRed", "Ironman", "Jarvis", "TonyStark@Stark.com",
              "Tony", "Stark", "M");
      authToken = new Authtoken("authToken", user.getUsername());

      Connection connection = db.getConnection();

      new UserDao(connection).UserDao_insert(user);
      new AuthTokenDAO(connection).AuthTokenDAO_insert(authToken);

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
    try
    {
      db = new Database();
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
  public void loginPass()
  {
    loginRequest = new LoginRequest("Ironman","Jarvis");
    loginResult = new LoginService().LoginService_login(loginRequest);
    assertEquals(true,loginResult.isSuccess());
  }

  @Test
  public void loginFail()
  {
    loginRequest = new LoginRequest("Catwoman","purr");
    loginResult = new LoginService().LoginService_login(loginRequest);
    assertEquals(false,loginResult.isSuccess());
  }
}
