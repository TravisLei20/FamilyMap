package RequestResult;

/**
 * Login Result Class
 */
public class LoginResult extends Response
{
  /**
   * fail constructor for login
   * @param success fail
   * @param message error message
   */
  public LoginResult(boolean success, String message) {
    super(success, message);
  }

  /**
   * succeed constructor for login
   * @param success true
   * @param username username
   * @param authToken authToken
   * @param personID PersonID
   */
  public LoginResult(boolean success, String username, String authToken, String personID) {
    super(success);
    this.username=username;
    this.authtoken=authToken;
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
