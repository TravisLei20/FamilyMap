package DAO;

import Model.Authtoken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is the class that controls the AuthToken class/table for the users
 */
public class AuthTokenDAO
{
  /**
   * Constructor
   * @param connection connection class
   */
  public AuthTokenDAO(Connection connection)
  {
    this.connection = connection;
  }

  private final Connection connection;

  /**
   * Inserts a new AuthToken
   * @param token the AuthToken Object
   * @throws DataAccessException throws error
   */
  public void AuthTokenDAO_insert(Authtoken token) throws DataAccessException
  {
    String sql = "INSERT INTO AuthToken (AuthToken, Username) VALUES(?,?)";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      //Using the statements built-in set(type) functions we can pick the question mark we want
      //to fill in and give it a proper value. The first argument corresponds to the first
      //question mark found in our sql String
      stmt.setString(1, token.getAuthToken());
      stmt.setString(2, token.getUsername());

      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while inserting an AuthToken into the database");
    }
  }

  /**
   * Finds the AuthToken according to the username if it exists
   * @param username uses the username find the AuthToken
   * @return Returns the AuthToken Object
   * @throws DataAccessException throws error
   */
  public Authtoken AuthTokenDAO_find(String username) throws DataAccessException {
    Authtoken authToken;
    ResultSet rs;
    String sql = "SELECT * FROM AuthToken WHERE Username = ?;";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, username);
      rs = stmt.executeQuery();
      if (rs.next()) {
        authToken = new Authtoken(rs.getString("AuthToken"), rs.getString("Username"));
        return authToken;
      } else {
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding an authToken in the database");
    }
  }

  public Authtoken AuthTokenDAO_findWithAuthToken(String givenAuthToken) throws DataAccessException {
    Authtoken authToken;
    ResultSet rs;
    String sql = "SELECT * FROM AuthToken WHERE authToken = ?;";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, givenAuthToken);
      rs = stmt.executeQuery();
      if (rs.next()) {
        authToken = new Authtoken(rs.getString("AuthToken"), rs.getString("Username"));
        return authToken;
      } else {
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding an authToken in the database");
    }
  }

  /**
   * Clears all the tables of AuthToken
   * @throws DataAccessException throws error
   */
  public void AuthTokenDAO_clear() throws DataAccessException {
    String sql = "DELETE FROM AuthToken";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while clearing the authToken table");
    }
  }


}
