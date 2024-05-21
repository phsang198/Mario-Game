import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * Class that contains methods to read a CSV file and a properties file.
 * You may edit this as you wish.
 */
public class IOUtils {

    /***
     * Method that reads a CSV file and return a 2D String array
     * @param csvFile: the path to the CSV file
     * @return 2D String array
     */
    public static List<Integer[][]> readCsv(String csvFile, String objectName)
     {
        List<Integer[][]> lst = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(csvFile));
            while (scanner.hasNextLine()) 
            {
                String line = scanner.nextLine();

                String[] parts = line.split(",");

                String obname = parts[0].trim();
                if (obname.equals(objectName))
                {
                    Integer[][] XY = new Integer[1][2];
                    XY[0][0] = Integer.parseInt(parts[1].trim());
                    XY[0][1] = Integer.parseInt(parts[2].trim());
                    lst.add(XY) ; 
                }

            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return lst ; 
    }

    /***
     * Method that reads a properties file and return a Properties object
     * @param configFile: the path to the properties file
     * @return Properties object
     */
    public static Properties readPropertiesFile(String configFile) {
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(configFile));
        } catch(IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        return appProps;
    }
}