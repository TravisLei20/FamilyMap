package Service;

import DAO.AuthTokenDAO;
import DAO.DataAccessException;
import DAO.Database;
import DAO.UserDao;
import Model.Authtoken;
import Model.User;
import RequestResult.RegisterRequest;
import RequestResult.RegisterResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterServiceTest
{
  private Database db;

  @BeforeEach
  public void setUp() throws DataAccessException
  {
    db = new Database();
    try
    {
      db.openConnection();

      User user = new User("HotRodRed", "Ironman", "Jarvis", "TonyStark@Stark.com",
              "Tony", "Stark", "M");
      Authtoken authToken = new Authtoken("authToken", user.getUsername());

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
  public void registerPass()
  {
    RegisterRequest registerRequest = new RegisterRequest("Homer2468","forbiddenDoughnutahhhhhhh",
            "homer@gmail.com","Homer", "Simpson", "m");
    RegisterResult registerResult = new RegisterService().RegisterService_register(registerRequest);
    assertTrue(registerResult.isSuccess());
  }

  @Test
  public void registerFail()
  {
    RegisterRequest registerRequest = new RegisterRequest("Ironman", "Jarvis", "TonyStark@Stark.com",
            "Tony", "Stark", "M");
    RegisterResult registerResult = new RegisterService().RegisterService_register(registerRequest);
    assertFalse(registerResult.isSuccess());
  }
}
