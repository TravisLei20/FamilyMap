package Service;

import DAO.*;

/**
 * Helps clear out the whole database
 */
public class ClearDatabaseHelper
{
  /**
   * Clears out the database
   * @param db the database connection
   */
  public ClearDatabaseHelper(Database db)
  {
    try
    {
      AuthTokenDAO authTokenDAO = new AuthTokenDAO(db.getConnection());
      authTokenDAO.AuthTokenDAO_clear();

      EventDAO eventDAO = new EventDAO(db.getConnection());
      eventDAO.EventDAO_clear();

      PersonDAO personDAO = new PersonDAO(db.getConnection());
      personDAO.PersonDAO_clear();

      UserDao userDao = new UserDao(db.getConnection());
      userDao.UserDao_clear();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      db.closeConnection(false);
    }
  }
}
