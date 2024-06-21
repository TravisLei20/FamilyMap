package GenerateFakeData;

import DeserializeJSONFiles.JSONData;
import DeserializeJSONFiles.LocationData;
import DeserializeJSONFiles.NameData;
import Model.Event;
import Model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static DeserializeJSONFiles.JSONData.*;

/**
 * Generates People
 */
public class GeneratePerson
{
  private final List<Person> listOfPerson;
  private final List<Event> listOfEvent;

  public List<Person> getListOfPerson() {
    return listOfPerson;
  }

  public List<Event> getListOfEvent() {
    return listOfEvent;
  }

  /**
   * Constructor
   */
  public GeneratePerson()
  {
    listOfEvent = new ArrayList<>();
    listOfPerson = new ArrayList<>();
  }

  /**
   * This is the recursive function to create fake history
   * @param male indicates is the current user is male or not
   * @param generations indicates how many more generation to create
   * @param associatedUsername indicates which user is this history for
   * @param birthYear determines the birthyear of the current person
   * @return returns a created person
   */
  public Person generatePerson(boolean male, int generations, String associatedUsername, int birthYear)
  {
    int fnames = getFemaleNameNum();
    int mnames = getMaleNameNum();
    int snames = getSurnameNum();
    int locations = getLocationNum();


    Person mother = null;
    Person father = null;

    Person person = new Person();

    Random random = new Random();

    if (generations > 1)
    {
//      int year;
//      do {
//        year=random.nextInt() % 20;
//      } while (year > - 1);
//      int motherBirthYear = birthYear - 17 - year;
//
//      do {
//        year=random.nextInt() % 20;
//      } while (year > - 1);
//      int fatherBirthYear = birthYear - 17 - year;
//
//      do {
//        year=random.nextInt() % 3;
//      } while (year > - 1);
//
//      int marriageYear = birthYear - year;
//
//      mother = generatePerson(false, (generations - 1), associatedUsername, motherBirthYear);
//      listOfPerson.remove(listOfPerson.size()-1);
//
//      father = generatePerson(true, (generations - 1), associatedUsername, fatherBirthYear);
//      listOfPerson.remove(listOfPerson.size()-1);

      mother = generatePerson(false, (generations - 1), associatedUsername, birthYear-30);
      listOfPerson.remove(listOfPerson.size()-1);

      father = generatePerson(true, (generations - 1), associatedUsername, birthYear-30);
      listOfPerson.remove(listOfPerson.size()-1);

      mother.setSpouseID(father.getPersonID());
      listOfPerson.add(mother);

      father.setSpouseID(mother.getPersonID());
      listOfPerson.add(father);

      int marriageYear = birthYear - 5;

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
      LocationData locationData = getLocationData();
      String city = locationData.getData(index).getCity();
      String country = locationData.getData(index).getCountry();
      float latitude = locationData.getData(index).getLatitude();
      float longitude = locationData.getData(index).getLongitude();


      Event fatherMarriage = new Event(eventID,associatedUsername,father.getPersonID(),latitude,longitude,country,city,"marriage",marriageYear);

      eventID = UUID.randomUUID().toString();
      Event motherMarriage = new Event(eventID,associatedUsername,mother.getPersonID(),latitude,longitude,country,city,"marriage",marriageYear);

      listOfEvent.add(fatherMarriage);
      listOfEvent.add(motherMarriage);
    }

    JSONData.JSONData_getData();

    NameData surnames = JSONData.surnameData;
    NameData maleNameData = JSONData.maleNameData;
    NameData femaleNameData = JSONData.femaleNameData;

    person.setPersonID(UUID.randomUUID().toString());

    if (male)
    {
      if (father == null)
      {

        int index;
        for (;;)
        {
          index = random.nextInt() % snames;
          if (index > -1 && index < snames)
          {
            break;
          }
        }
        person.setLastName(surnames.getName(index));
      }
      else
      {
        person.setLastName(father.getLastName());
      }

      person.setGender("m");

      int index;

      for (;;)
      {
        index = random.nextInt() % mnames;
        if (index > -1 && index < mnames)
        {
          break;
        }
      }

      person.setFirstName(maleNameData.getName(index));
    }
    else
    {
      int index;

      for (;;)
      {
        index = random.nextInt() % snames;
        if (index > -1 && index < snames)
        {
          break;
        }
      }

      person.setLastName(surnames.getName(index));

      person.setGender("f");

      for (;;)
      {
        index = random.nextInt() % fnames;
        if (index > -1 && index < fnames)
        {
          break;
        }
      }

      person.setFirstName(femaleNameData.getName(index));
    }

    person.setAssociatedUsername(associatedUsername);

    if (father != null)
    {
      person.setFatherID(father.getPersonID());
      person.setMotherID(mother.getPersonID());
    }

    // put person in database
    listOfPerson.add(person);

    // events
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

    Event birthEvent = new Event(eventID,associatedUsername, person.getPersonID(),latitude,longitude,country,city,"birth",birthYear);

    listOfEvent.add(birthEvent);

    for (;;)
    {
      index = random.nextInt() % locations;
      if (index > -1 && index < locations)
      {
        break;
      }
    }

    eventID = UUID.randomUUID().toString();
    locationData = getLocationData();
    city = locationData.getData(index).getCity();
    country = locationData.getData(index).getCountry();
    latitude = locationData.getData(index).getLatitude();
    longitude = locationData.getData(index).getLongitude();

    Event deathEvent = new Event(eventID,associatedUsername,person.getPersonID(),latitude,longitude,country,city,"death",(birthYear+70+random.nextInt()%20));

    listOfEvent.add(deathEvent);

    return person;
  }
}
