package DAO;

import Model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDAOTest {

  private Database db;
  private Person bestPerson;
  private PersonDAO pDAO;

  @BeforeEach
  public void setUp() throws DataAccessException {
    // Here we can set up any classes or variables we will need for each test
    // lets create a new instance of the Database class
    db = new Database();
    // and a new event with random data
    bestPerson= new Person("King_Charles", "Ancestor_Of_Charles", "Charles",
            "BearClawson", "M", "Queen_Anna", "Henry_The_Conqueror", "Maria_The_Saint");

    // Here, we'll open the connection in preparation for the test case to use it
    Connection connection = db.getConnection();
    //Then we pass that connection to the EventDAO, so it can access the database.
    pDAO= new PersonDAO(connection);
    //Let's clear the database as well so any lingering data doesn't affect our tests
    pDAO.PersonDAO_clear();
  }

  @AfterEach
  public void tearDown() {
    // Here we close the connection to the database file, so it can be opened again later.
    // We will set commit to false because we do not want to save the changes to the database
    // between test cases.
    db.closeConnection(false);
  }

  @Test
  public void insertPass() throws DataAccessException {
    // Start by inserting an person into the database.
    pDAO.PersonDAO_insert(bestPerson);

    Person compareTest = pDAO.PersonDAO_find(bestPerson.getPersonID());

    assertNotNull(compareTest);

    assertEquals(bestPerson, compareTest);
  }

  @Test
  public void insertFail() throws DataAccessException {

    pDAO.PersonDAO_insert(bestPerson);


    assertThrows(DataAccessException.class, () -> pDAO.PersonDAO_insert(bestPerson));
  }

  @Test
  public void findPass() throws DataAccessException
  {
    pDAO.PersonDAO_insert(bestPerson);
    Person compareTest = pDAO.PersonDAO_find(bestPerson.getPersonID());
    assertNotNull(compareTest);
    assertEquals(bestPerson, compareTest);
  }

  @Test
  public void findFail() throws DataAccessException
  {
    pDAO.PersonDAO_insert(bestPerson);
    assertNull( pDAO.PersonDAO_find("WrongID"));
  }

  @Test
  public void throwNULLPass() throws DataAccessException
  {
    bestPerson = new Person(null, null, null,
            null, null, null, null, null);
    assertThrows(DataAccessException.class, () -> pDAO.PersonDAO_insert(bestPerson));
  }

  @Test
  public void noNULLThrow() throws DataAccessException
  {
    bestPerson = new Person("Homer123", "Homer_Doughnut", "Homer",
            "Simpson", "M", "Marge_Blue", "Abraham_WW2", null);
    assertDoesNotThrow(() -> pDAO.PersonDAO_insert(bestPerson));
  }

  @Test
  public void clearPass() throws DataAccessException
  {
    pDAO.PersonDAO_insert(bestPerson);
    pDAO.PersonDAO_clear();
    assertNull(pDAO.PersonDAO_find(bestPerson.getPersonID()));
  }
}
