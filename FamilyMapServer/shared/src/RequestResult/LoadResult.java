package RequestResult;

/**
 * Result Class for load
 */
public class LoadResult extends Response
{
  /**
   * fail constructor for load
   * @param success fail
   * @param message error message
   */
  public LoadResult(boolean success, String message) {
    super(success, message);
  }

  /**
   * succeed constructor for load
   * @param success true
   */
  public LoadResult(boolean success) {
    super(success);
  }
}
