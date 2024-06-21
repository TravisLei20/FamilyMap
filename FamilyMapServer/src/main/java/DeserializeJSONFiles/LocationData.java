package DeserializeJSONFiles;

/**
 * Contains locations that hold the JSON
 */
public class LocationData extends Location
{
  private Location [] data;

  public void setData(Location[] data)
  {
    this.data = data;
  }

  public int getDataSize()
  {
    return data.length;
  }

  public Location getData(int index) {
    return data[index];
  }
}
