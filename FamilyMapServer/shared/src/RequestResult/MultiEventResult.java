package RequestResult;


import Model.Event;

import java.util.List;

/**
 * Returns All events in user family Result
 */
public class MultiEventResult extends Response
{
  /**
   * fail constructor for mulitevent
   * @param success fail
   * @param message error
   */
  public MultiEventResult(boolean success, String message) {
    super(success, message);
  }

  /**
   * succeed constructor for multieven
   * @param success true
   * @param data events list
   */
  public MultiEventResult(boolean success, List<Event> data) {
    super(success);
    this.data=data;
  }

  private List<Event> data; // Might be String array because of json array

  public List<Event> getData() {
    return data;
  }

  public void setData(List<Event> data) {
    this.data=data;
  }

}
