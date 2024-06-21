package RequestResult;

/**
 * Returns all family members of current user request
 */
public class MultiPersonRequest
{
  /**
   * Constructor
   * @param associatedUsername user's current authToken
   */
  public MultiPersonRequest(String associatedUsername) {
    this.associatedUsername=associatedUsername;
  }

  private String associatedUsername;

  public String getAssociatedUsername() {
    return associatedUsername;
  }

  public void setAssociatedUsername(String associatedUsername) {
    this.associatedUsername=associatedUsername;
  }
}
