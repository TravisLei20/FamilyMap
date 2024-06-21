package RequestResult;

import Model.Event;

/**
 * Returns single event result class.
 */
public class SingleEventResult extends Response
{
  /**
   * fail constructor for singleeventresult
   * @param success fail
   * @param message error
   */
  public SingleEventResult(boolean success, String message) {
    super(success, message);
  }


  /**
   * The successful constructor
   * @param success true
   * @param eventType event type
   * @param personID person id
   * @param city city
   * @param country country
   * @param latitude latitude
   * @param longitude longitude
   * @param year year
   * @param eventID event ID
   * @param associatedUsername associated username
   */
  public SingleEventResult(boolean success, String eventType, String personID, String city, String country,
                           float latitude, float longitude, int year, String eventID, String associatedUsername) {
    super(success);
    this.eventType=eventType;
    this.personID=personID;
    this.city=city;
    this.country=country;
    this.latitude=latitude;
    this.longitude=longitude;
    this.year=year;
    this.eventID=eventID;
    this.associatedUsername=associatedUsername;
  }

  private String eventType;

  private String personID;

  private String city;

  private String country;

  private float latitude;

  private float longitude;

  private int year;

  private String eventID;

  private String associatedUsername;

}
