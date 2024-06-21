package RequestResult;

/**
 * New user registers for account result.
 */
public class RegisterResult extends Response
{
  /**
   * fail constructor for register
   * @param success fail
   * @param message error
   */
  public RegisterResult(boolean success, String message) {
    super(success, message);
  }

  /**
   * succeed constructor for register
   * @param success true
   * @param username username
   * @param authtoken authtoken
   * @param personID personID
   */
  public RegisterResult(boolean success, String username, String authtoken, String personID) {
    super(success);
    this.username=username;
    this.authtoken=authtoken;
    this.personID=personID;
  }

  private String username;
  private String authtoken;
  private String personID;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username=username;
  }

  public String getAuthtoken() {
    return authtoken;
  }

  public void setAuthtoken(String authtoken) {
    this.authtoken=authtoken;
  }

  public String getPersonID() {
    return personID;
  }

  public void setPersonID(String personID) {
    this.personID=personID;
  }

}
