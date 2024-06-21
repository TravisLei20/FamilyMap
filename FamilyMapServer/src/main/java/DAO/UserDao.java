package DAO;

import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is the class that controls the user class/table for the users
 */
public class UserDao
{
  /**
   * Constructor
   * @param connection The Connection class
   */
  public UserDao(Connection connection) {
    this.connection=connection;
  }

  private final Connection connection;

  /**
   * Inserts into the user table
   * @param user Inserts the User
   * @throws DataAccessException throws error
   */
  public void UserDao_insert(User user) throws DataAccessException
  {
    String sql = "INSERT INTO Users (PersonID, Username, Password, email," +
            "FirstName, LastName, Gender) VALUES(?,?,?,?,?,?,?)";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      //Using the statements built-in set(type) functions we can pick the question mark we want
      //to fill in and give it a proper value. The first argument corresponds to the first
      //question mark found in our sql String
      stmt.setString(1, user.getPersonID());
      stmt.setString(2, user.getUsername());
      stmt.setString(3, user.getPassword());
      stmt.setString(4, user.getEmail());
      stmt.setString(5, user.getFirstName());
      stmt.setString(6, user.getLastName());
      stmt.setString(7, user.getGender());

      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while inserting an user into the database");
    }
  }

  /**
   * Finds and returns the user according to the given username if it exists
   * @param userName uses this to find the correct User Object
   * @return User Object
   * @throws DataAccessException throws error
   */
  public User UserDao_find(String userName) throws DataAccessException {
    User user;
    ResultSet rs;
    String sql = "SELECT * FROM Users WHERE Username = ?;";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, userName);
      rs = stmt.executeQuery();
      if (rs.next()) {
        user = new User(rs.getString("personID"), rs.getString("username"),
                rs.getString("password"), rs.getString("email"), rs.getString("firstName"),
                rs.getString("lastName"), rs.getString("gender"));
        return user;
      } else {
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding an user in the database");
    }

  }


  /**
   * clears the user table
   * @throws DataAccessException throws error
   */
  public void UserDao_clear() throws DataAccessException {
    String sql = "DELETE FROM Users";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while clearing the user table");
    }
  }
}
