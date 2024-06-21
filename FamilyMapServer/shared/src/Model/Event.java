package Model;

import java.util.Objects;

/**
 * This is the Event container class
 */
public class Event
{
  /**
   * Constructor
   * @param eventType The event type
   * @param personID The person ID
   * @param city The city
   * @param country The country
   * @param latitude The latitude
   * @param longitude The longitude
   * @param year The year
   * @param eventID The event ID
   * @param associatedUsername The username that is associated with the event
   */
  public Event(String eventID, String associatedUsername, String personID, float latitude,
               float longitude, String country, String city, String eventType, int year)
  {
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

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType=eventType;
  }

  public String getPersonID() {
    return personID;
  }

  public void setPersonID(String personID) {
    this.personID=personID;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city=city;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country=country;
  }

  public float getLatitude() {
    return latitude;
  }

  public void setLatitude(float latitude) {
    this.latitude=latitude;
  }

  public float getLongitude() {
    return longitude;
  }

  public void setLongitude(float longitude) {
    this.longitude=longitude;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year=year;
  }

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Event event = (Event) o;
    return Objects.equals(eventID, event.eventID) && Objects.equals(associatedUsername, event.associatedUsername) && Objects.equals(personID, event.personID) && Objects.equals(latitude, event.latitude) && Objects.equals(longitude, event.longitude) && Objects.equals(country, event.country) && Objects.equals(city, event.city) && Objects.equals(eventType, event.eventType) && Objects.equals(year, event.year);
  }
}
