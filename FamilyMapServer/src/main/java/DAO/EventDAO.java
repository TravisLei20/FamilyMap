package DAO;

import Model.Event;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * This is the class that controls the Event class/table for the users
 */
public class EventDAO
{
  public EventDAO(Connection connection) {
    this.connection=connection;
  }

  private final Connection connection;

  /**
   * Inserts a new Event into the table
   * @param event inserts event
   * @throws DataAccessException In case of an error occurring
   */
  public void EventDAO_insert(Event event) throws DataAccessException
  {
    String sql = "INSERT INTO Events (EventID, AssociatedUsername, PersonID, Latitude, Longitude, " +
            "Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?)";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      //Using the statements built-in set(type) functions we can pick the question mark we want
      //to fill in and give it a proper value. The first argument corresponds to the first
      //question mark found in our sql String
      stmt.setString(1, event.getEventID());
      stmt.setString(2, event.getAssociatedUsername());
      stmt.setString(3, event.getPersonID());
      stmt.setFloat(4, event.getLatitude());
      stmt.setFloat(5, event.getLongitude());
      stmt.setString(6, event.getCountry());
      stmt.setString(7, event.getCity());
      stmt.setString(8, event.getEventType());
      stmt.setInt(9, event.getYear());

      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while inserting an event into the database");
    }
  }


  /**
   * Finds and returns the Event Object with the given eventID if it exists
   * @param eventID uses this ID to find the correct Event Object
   * @return Returns the Event Object
   * @throws DataAccessException throws if an error occurs
   */
  public Event EventDAO_find(String eventID) throws DataAccessException {
    Event event;
    ResultSet rs;
    String sql = "SELECT * FROM Events WHERE EventID = ?;";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, eventID);
      rs = stmt.executeQuery();
      if (rs.next()) {
        event = new Event(rs.getString("eventID"), rs.getString("associatedUsername"),
                rs.getString("personID"), rs.getFloat("latitude"), rs.getFloat("longitude"),
                rs.getString("country"), rs.getString("city"), rs.getString("eventType"),
                rs.getInt("year"));
        return event;
      } else {
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding an event in the database");
    }
  }

  public List<Event> EventDAO_findWithAssociatedUsername(String associatedUsername) throws DataAccessException
  {
    Event event;
    List<Event> events = new ArrayList<>();
    ResultSet rs;
    String sql = "SELECT * FROM Events WHERE associatedUsername = ?;";
    try (PreparedStatement stmt = connection.prepareStatement(sql))
    {
      stmt.setString(1, associatedUsername);
      rs = stmt.executeQuery();
      while (rs.next())
      {
        event = new Event(rs.getString("eventID"), rs.getString("associatedUsername"),
                rs.getString("personID"), rs.getFloat("latitude"), rs.getFloat("longitude"),
                rs.getString("country"), rs.getString("city"), rs.getString("eventType"),
                rs.getInt("year"));
        events.add(event);
      }
      return events;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding an event in the database");
    }
  }

  /**
   * Clears the Event table
   */
  public void EventDAO_clear() throws DataAccessException {
    String sql = "DELETE FROM Events";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while clearing the event table");
    }
  }

  public void EventDAO_delete(String associatedUsername) throws DataAccessException
  {
    String sql = "DELETE FROM Events WHERE associatedUsername = ?;";
    try (PreparedStatement stmt = connection.prepareStatement(sql))
    {
      stmt.setString(1, associatedUsername);
      stmt.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while deleting an event in the database");
    }
  }

}
