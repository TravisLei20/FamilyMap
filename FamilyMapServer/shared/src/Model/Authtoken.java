package Model;

import java.util.Objects;

/**
 * This is the AuthToken container class
 */
public class Authtoken
{
  /**
   * constructor
   * @param authtoken the authToken
   * @param username the userName
   */
  public Authtoken(String authtoken, String username) {
    this.authtoken=authtoken;
    this.username=username;
  }

  private String authtoken;

  private String username;

  public String getAuthToken() {
    return authtoken;
  }

  public void setAuthToken(String offToken) {
    this.authtoken=offToken;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username=username;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Authtoken authToken = (Authtoken) o;
    return Objects.equals(this.authtoken, authToken.authtoken) && Objects.equals(this.username, authToken.username);
  }
}
