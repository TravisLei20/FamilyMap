package DeserializeJSONFiles;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

/**
 * class that gets the JSON data from the local given files
 */
public class JSONData
{
  static public LocationData locationData;
  static public NameData maleNameData;
  static public NameData femaleNameData;
  static public NameData surnameData;

  static private final int locationNum = 975;
  static private final int maleNameNum = 144;
  static private final int femaleNameNum = 147;
  static private final int surnameNum = 152;

  /**
   * Constructor class that reads the JSON files from the project to create fake history
   */
  public static void JSONData_getData()
  {
    Reader reader;
    Gson gson = new Gson();
    {
      try
      {
        reader = new FileReader("json/locations.json");
        locationData = gson.fromJson(reader, LocationData.class);

        reader = new FileReader("json/mnames.json");
        maleNameData = gson.fromJson(reader, NameData.class);

        reader = new FileReader("json/fnames.json");
        femaleNameData = gson.fromJson(reader, NameData.class);

        reader = new FileReader("json/snames.json");
        surnameData = gson.fromJson(reader, NameData.class);
      }
      catch (FileNotFoundException e)
      {
        e.printStackTrace();
      }
    }
  }

  public static LocationData getLocationData() {
    return locationData;
  }

  public static NameData getMaleNameData() {
    return maleNameData;
  }

  public static NameData getFemaleNameData() {
    return femaleNameData;
  }

  public static NameData getSurnameData() {
    return surnameData;
  }

  public static int getLocationNum() {
    return locationNum;
  }

  public static int getMaleNameNum() {
    return maleNameNum;
  }

  public static int getFemaleNameNum() {
    return femaleNameNum;
  }

  public static int getSurnameNum() {
    return surnameNum;
  }
}
