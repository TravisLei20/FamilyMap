package RequestResult;

/**
 * Returns single event request.
 */
public class SingleEventRequest
{
  /**
   * Constructor
   * @param eventID ID of event
   * @param associatedUsername user's current authToken
   */
  public SingleEventRequest(String eventID, String associatedUsername) {
    this.eventID=eventID;
    this.associatedUsername=associatedUsername;
  }

  private String eventID;
  private String associatedUsername;

  public String getEventID() {
    return eventID;
  }

  public void setEventID(String eventID) {
    this.eventID=eventID;
  }

  public String getAssociatedUsername() {
    return associatedUsername;
  }

  public void setAssociatedUsername(String associatedUsername) {
    this.associatedUsername=associatedUsername;
  }
}
