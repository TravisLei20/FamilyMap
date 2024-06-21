package Model;

import java.util.Objects;

/**
 * This is the Container User Class
 */
public class User
{
  /**
   * Constructor
   * @param username username of user
   * @param password password of user
   * @param email the email of the user
   * @param firstName first name of user
   * @param lastName last name of user
   * @param gender gender of user
   * @param personID ID of user
   */
  public User(String personID, String username, String password, String email, String firstName, String lastName, String gender) {
    this.username=username;
    this.password=password;
    this.email=email;
    this.firstName=firstName;
    this.lastName=lastName;
    this.gender=gender;
    this.personID=personID;
  }

  private String username;

  private String password;

  private String email;

  private String firstName;

  private String lastName;

  private String gender;

  private String personID;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username=username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password=password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email=email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName=firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName=lastName;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender=gender;
  }

  public String getPersonID() {
    return personID;
  }

  public void setPersonID(String personID) {
    this.personID=personID;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(personID, user.personID) && Objects.equals(username, user.username)
            && Objects.equals(password, user.password) && Objects.equals(email, user.email)
            && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName)
            && Objects.equals(gender, user.gender);
  }
}
