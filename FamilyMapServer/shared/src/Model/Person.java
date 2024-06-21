package Model;

import java.util.Objects;

/**
 * This is the Container Person Class
 */
public class Person
{
  public Person() {}

  /**
   * Constructor
   * @param personID The Person ID
   * @param associatedUsername The associated username
   * @param firstName The first name of the person
   * @param lastName The last name of the person
   * @param gender The gender of the person
   * @param spouseID The spouse ID (if exists)
   * @param fatherID The father ID (if exists)
   * @param motherID The mother ID (if exists)
   */
  public Person(String personID, String associatedUsername, String firstName, String lastName, String gender, String spouseID,
                String fatherID, String motherID) {
    this.firstName=firstName;
    this.lastName=lastName;
    this.gender=gender;
    this.personID=personID;
    this.spouseID=spouseID;
    this.fatherID=fatherID;
    this.motherID=motherID;
    this.associatedUsername=associatedUsername;
  }

  private String firstName;

  private String lastName;

  private String gender;

  private String personID;

  private String spouseID;

  private String fatherID;

  private String motherID;

  private String associatedUsername;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName=firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName=lastName;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender=gender;
  }

  public String getPersonID() {
    return personID;
  }

  public void setPersonID(String personID) {
    this.personID=personID;
  }

  public String getSpouseID() {
    return spouseID;
  }

  public void setSpouseID(String spouseID) {
    this.spouseID=spouseID;
  }

  public String getFatherID() {
    return fatherID;
  }

  public void setFatherID(String fatherID) {
    this.fatherID=fatherID;
  }

  public String getMotherID() {
    return motherID;
  }

  public void setMotherID(String motherID) {
    this.motherID=motherID;
  }

  public String getAssociatedUsername() {
    return associatedUsername;
  }

  public void setAssociatedUsername(String associatedUsername) {
    this.associatedUsername=associatedUsername;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Person person = (Person) o;
    return Objects.equals(personID, person.personID) && Objects.equals(associatedUsername, person.associatedUsername)
            && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName)
            && Objects.equals(gender, person.gender) && Objects.equals(spouseID, person.spouseID)
            && Objects.equals(fatherID, person.fatherID) && Objects.equals(motherID, person.motherID);
  }
}
