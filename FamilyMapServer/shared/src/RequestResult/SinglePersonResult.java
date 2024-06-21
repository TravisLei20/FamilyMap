package RequestResult;

import Model.Person;

/**
 * Returns single person result class
 */
public class SinglePersonResult extends Response
{
  /**
   * fail constructor
   * @param success fail
   * @param message error
   */
  public SinglePersonResult(boolean success, String message) {
    super(success, message);
  }

//  /**
//   * succeed constructor
//   * @param success true
//   * @param data person
//   */
//  public SinglePersonResult(boolean success, Person data) {
//    super(success);
//    this.data=data;
//  }
//
//  private Person data;
//
//  public Person getData() {
//    return data;
//  }
//
//  public void setData(Person data) {
//    this.data=data;
//  }

  private String firstName;

  private String lastName;

  private String gender;

  private String personID;

  private String spouse;

  private String fatherID;

  private String motherID;

  private String associatedUsername;

  /**
   * successful constructor
   * @param success true
   * @param firstName first name
   * @param lastName last name
   * @param gender gender
   * @param personID ID
   * @param spouse spouseID
   * @param fatherID fatherID
   * @param motherID mother ID
   * @param associatedUsername associated username
   */
  public SinglePersonResult(boolean success, String firstName, String lastName, String gender, String personID,
                            String spouse, String fatherID, String motherID, String associatedUsername) {
    super(success);
    this.firstName=firstName;
    this.lastName=lastName;
    this.gender=gender;
    this.personID=personID;
    this.spouse=spouse;
    this.fatherID=fatherID;
    this.motherID=motherID;
    this.associatedUsername=associatedUsername;
  }


}
