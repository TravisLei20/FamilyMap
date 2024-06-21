package RequestResult;

/**
 * This is the class that all the Result classes inherit from
 * It has the boolean for success and string for the error message
 */
public class Response
{
  private boolean success;
  private String message;

  /**
   * the fail constructor
   * @param success fail
   * @param message error message
   */
  public Response(boolean success, String message) {
    this.success=success;
    this.message=message;
  }

  /**
   * succeed constructor
   * @param success true
   */
  public Response(boolean success) {
    this.success=success;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success=success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message=message;
  }
}
