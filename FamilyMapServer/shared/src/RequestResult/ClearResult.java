package RequestResult;

/**
 * Clear every table result class
 * This class extends the Response class
 */
public class ClearResult extends Response
{
  /**
   * This is the result constructor for clear if the clear failed
   * @param success this will show fail
   * @param message this will show the error message
   */
  public ClearResult(boolean success, String message) {
    super(success, message);
  }

  /**
   * This is the result constructor for clear if the clear succeeded
   * @param success this will show true
   */
  public ClearResult(boolean success) {
    super(success);
  }
}
