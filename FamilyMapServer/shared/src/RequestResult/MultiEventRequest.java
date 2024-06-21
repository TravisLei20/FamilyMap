package RequestResult;

/**
 * Returns All events in user family Request
 */
public class MultiEventRequest
{
  /**
   * Constructor
   * @param associatedUsername the current authToken
   */
  public MultiEventRequest(String associatedUsername) {
    this.associatedUsername=associatedUsername;
  }

  String associatedUsername;

  public String getAssociatedUsername() {
    return associatedUsername;
  }

  public void setAssociatedUsername(String associatedUsername) {
    this.associatedUsername=associatedUsername;
  }
}
