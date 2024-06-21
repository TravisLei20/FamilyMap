package RequestResult;

/**
 * Returns single person request class.
 */
public class SinglePersonRequest
{
  /**
   * Constructor
   * @param personID person ID
   */
  public SinglePersonRequest(String personID, String userName) {
    this.personID=personID;
    this.userName=userName;
  }

  private String personID;
  private String userName;

  public String getPersonID() {
    return personID;
  }

  public void setPersonID(String personID) {
    this.personID=personID;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName=userName;
  }
}
