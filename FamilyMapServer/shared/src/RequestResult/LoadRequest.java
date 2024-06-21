package RequestResult;

import Model.Event;
import Model.Person;
import Model.User;

/**
 * Clears all the tables and then inserts all the given json arrays into the tables
 */
public class LoadRequest
{
  /**
   * Constructor
   * @param users User Object Array will be converted to json
   * @param persons Person Object Array will be converted to json
   * @param events Event Object Array will be converted to json
   */
  public LoadRequest(User[] users, Person[] persons, Event[] events) {
    this.users=users;
    this.persons=persons;
    this.events=events;
  }

  private User [] users;
  private Person [] persons;
  private Event [] events;

  public User[] getUsers() {
    return users;
  }

  public void setUsers(User[] users) {
    this.users=users;
  }

  public Person[] getPersons() {
    return persons;
  }

  public void setPersons(Person[] persons) {
    this.persons=persons;
  }

  public Event[] getEvents() {
    return events;
  }

  public void setEvents(Event[] events) {
    this.events=events;
  }
}
