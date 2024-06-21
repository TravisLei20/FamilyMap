package RequestResult;

import Model.Person;

import java.util.List;

/**
 * Returns all family members of user result class
 */
public class MultiPersonResult extends Response
{
  /**
   * fail constructor for multiperson
   * @param success fail
   * @param message error message
   */
  public MultiPersonResult(boolean success, String message) {
    super(success, message);
  }

  /**
   * succeed constructor for multiperson
   * @param success true
   * @param data list of persons
   */
  public MultiPersonResult(boolean success, List<Person> data) {
    super(success);
    this.data=data;
  }

  private List<Person> data;

  public List<Person> getData() {
    return data;
  }

  public void setData(List<Person> data) {
    this.data=data;
  }

}
