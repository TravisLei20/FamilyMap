package RequestResult;

/**
 * The fill result class
 */
public class FillResult extends Response
{
  /**
   * fail result constructor for fill
   * @param success fail
   * @param message error message
   */
  public FillResult(boolean success, String message) {
    super(success, message);
  }

  /**
   * succeed result constructor for fill
   * @param success true
   */
  public FillResult(boolean success) {
    super(success);
  }
}
