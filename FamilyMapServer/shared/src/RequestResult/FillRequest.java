package RequestResult;

/**
 * Fills a users tree with fake family history
 */
public class FillRequest
{
  /**
   * Constructor with only username, will automatically fill for four generations
   * @param username username
   */
  public FillRequest(String username) {
    this.username=username;
    this.generationNum = 4;
  }

  /**
   * Constructor with both username and number of generations to fill
   * @param username username
   * @param generationNum how many generations will be generated
   */
  public FillRequest(String username, int generationNum){
    this.username = username;
    this.generationNum = generationNum;
  }

  private String username;
  private int generationNum;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username=username;
  }

  public int getGenerationNum() {
    return generationNum;
  }

  public void setGenerationNum(int generationNum) {
    this.generationNum=generationNum;
  }
}
