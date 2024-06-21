package Service;

import DAO.Database;
import DAO.EventDAO;
import DAO.PersonDAO;
import DAO.UserDao;
import DeserializeJSONFiles.JSONData;
import DeserializeJSONFiles.LocationData;
import DeserializeJSONFiles.NameData;
import GenerateFakeData.GeneratePerson;
import Model.Event;
import Model.Person;
import Model.User;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static DeserializeJSONFiles.JSONData.*;
import static DeserializeJSONFiles.JSONData.getLocationNum;

/**
 * Used to generated history
 */
public class GenerateHistoryHelper
{
  private int peopleAdded = 0;
  private int eventsAdded = 0;

  public int getPeopleAdded()
  {
    return peopleAdded;
  }

  public int getEventsAdded() {
    return eventsAdded;
  }

  /**
   * This is a constructor that generates history
   * @param generation number of generations to make
   * @param db the connection the the database
   * @param username the username that will be associated with the generated history
   */
  public GenerateHistoryHelper(int generation, Database db, String username)
  {
    try
    {
      GeneratePerson fatherSide = new GeneratePerson();
      GeneratePerson motherSide = new GeneratePerson();

      Random random = new Random();

//      Person father = fatherSide.generatePerson(true,generation,username,1960+ random.nextInt()%5);
//      Person mother = motherSide.generatePerson(false,generation,username,1960+ random.nextInt()%5);

      Person father = fatherSide.generatePerson(true,generation,username,1960);
      Person mother = motherSide.generatePerson(false,generation,username,1960);

      List<Event> fatherEvents = fatherSide.getListOfEvent();
      List<Event> motherEvents = motherSide.getListOfEvent();

      List<Person> fatherPersons = fatherSide.getListOfPerson();
      fatherPersons.remove(fatherPersons.size()-1);

      List<Person> motherPersons = motherSide.getListOfPerson();
      motherPersons.remove(motherPersons.size()-1);

      father.setSpouseID(mother.getPersonID());
      mother.setSpouseID(father.getPersonID());

      fatherPersons.add(father);
      motherPersons.add(mother);

//**********************************************************************************************************************
//**********************************************************************************************************************

      JSONData.JSONData_getData();
      int locations = getLocationNum();
      NameData surnames = JSONData.surnameData;
      int index;

      for (;;)
      {
        index = random.nextInt() % locations;
        if (index > -1 && index < locations)
        {
          break;
        }
      }

      String eventID = UUID.randomUUID().toString();
      LocationData locationData =getLocationData();
      String city = locationData.getData(index).getCity();
      String country = locationData.getData(index).getCountry();
      float latitude = locationData.getData(index).getLatitude();
      float longitude = locationData.getData(index).getLongitude();

      int marriageYear = 1980;

      Event fatherMarriage = new Event(eventID,username,father.getPersonID(),latitude,longitude,country,city,
              "marriage",marriageYear);

      eventID = UUID.randomUUID().toString();
      Event motherMarriage = new Event(eventID,username,mother.getPersonID(),latitude,longitude,country,city,
              "marriage",marriageYear);

      fatherEvents.add(fatherMarriage);
      motherEvents.add(motherMarriage);

//**********************************************************************************************************************
//**********************************************************************************************************************


      User user = new UserDao(db.getConnection()).UserDao_find(username);

      Person currPerson = new Person(user.getPersonID(),user.getUsername(),user.getFirstName(),user.getLastName(),
              user.getGender(),null,father.getPersonID(),mother.getPersonID());

      for (;;)
      {
        index = random.nextInt() % locations;
        if (index > -1 && index < locations)
        {
          break;
        }
      }


      eventID = UUID.randomUUID().toString();
      locationData =getLocationData();
      city = locationData.getData(index).getCity();
      country = locationData.getData(index).getCountry();
      latitude = locationData.getData(index).getLatitude();
      longitude = locationData.getData(index).getLongitude();

      Event birthEvent = new Event(eventID,user.getUsername(), user.getPersonID(),latitude,longitude,country,
              city,"birth",1985);

      new PersonDAO(db.getConnection()).PersonDAO_insert(currPerson);
      peopleAdded++;

      new EventDAO(db.getConnection()).EventDAO_insert(birthEvent);
      eventsAdded++;

      for (Person person : fatherPersons)
      {
        new PersonDAO(db.getConnection()).PersonDAO_insert(person);
        peopleAdded++;
      }
      for (Person person : motherPersons)
      {
        new PersonDAO(db.getConnection()).PersonDAO_insert(person);
        peopleAdded++;
      }
      for (Event event : fatherEvents)
      {
        new EventDAO(db.getConnection()).EventDAO_insert(event);
        eventsAdded++;
      }
      for (Event event : motherEvents)
      {
        new EventDAO(db.getConnection()).EventDAO_insert(event);
        eventsAdded++;
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      db.closeConnection(false);
    }

  }
}
