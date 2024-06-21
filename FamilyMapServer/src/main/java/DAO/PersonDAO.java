package DAO;

import Model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the class that controls the Person class/table for the users
 */
public class PersonDAO
{
  /**
   * Constructor
   * @param connection the connection class
   */
  public PersonDAO(Connection connection) {
    this.connection=connection;
  }

  private final Connection connection;

  /**
   * Inserts into the person table
   * @param person The Person Object to insert
   */
  public void PersonDAO_insert(Person person) throws DataAccessException
  {
    String sql = "INSERT INTO Persons (PersonID, AssociatedUsername, FirstName, LastName, Gender, SpouseID, " +
            "FatherID, MotherID) VALUES(?,?,?,?,?,?,?,?)";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      //Using the statements built-in set(type) functions we can pick the question mark we want
      //to fill in and give it a proper value. The first argument corresponds to the first
      //question mark found in our sql String
      stmt.setString(1, person.getPersonID());
      stmt.setString(2, person.getAssociatedUsername());
      stmt.setString(3, person.getFirstName());
      stmt.setString(4, person.getLastName());
      stmt.setString(5, person.getGender());
      stmt.setString(6, person.getSpouseID());
      stmt.setString(7, person.getFatherID());
      stmt.setString(8, person.getMotherID());


      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while inserting an event into the database");
    }
  }

  /**
   * Finds and returns the Person according to the given personID if it exists
   * @param personID the ID used to find the Person Object
   * @return Person Object
   * @throws DataAccessException throws in case of error
   */
  public Person PersonDAO_find(String personID) throws DataAccessException
  {
    Person person;
    ResultSet rs;
    String sql = "SELECT * FROM Persons WHERE PersonID = ?;";
    try (PreparedStatement stmt = connection.prepareStatement(sql))
    {
      stmt.setString(1, personID);
      rs = stmt.executeQuery();

      if (rs.next())
      {
        person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                rs.getString("spouseID"), rs.getString("fatherID"), rs.getString("motherID"));
        return person;
      }
      else
      {
        return null;
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding a person in the database");
    }
  }

  public List<Person> PersonDAO_findWithAssociatedUsername(String associatedUsername) throws DataAccessException
  {
    Person person;
    List<Person> people = new ArrayList<>();
    ResultSet rs;
    String sql = "SELECT * FROM Persons WHERE associatedUsername = ?;";
    try (PreparedStatement stmt = connection.prepareStatement(sql))
    {
      stmt.setString(1, associatedUsername);
      rs = stmt.executeQuery();

      while (rs.next())
      {
        person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                rs.getString("spouseID"), rs.getString("fatherID"), rs.getString("motherID"));
        people.add(person);
      }
      return people;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding a person in the database");
    }
  }

  /**
   * Clears the person table
   * @throws DataAccessException throws if error
   */
  public void PersonDAO_clear() throws DataAccessException {
    String sql = "DELETE FROM Persons";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while clearing the person table");
    }
  }

  public void PersonDAO_delete(String username) throws DataAccessException
  {
    String sql = "DELETE FROM Persons WHERE associatedUsername = ?;";
    try (PreparedStatement stmt = connection.prepareStatement(sql))
    {
      stmt.setString(1, username);
      stmt.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while deleting a person in the database");
    }
  }


  //TODO
  // Might need a marry function
}
