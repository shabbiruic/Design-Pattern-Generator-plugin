package Utilities;

import Generators.ClassGenerator;
import Generators.InterfaceGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.intellij.openapi.util.Pair;
import java.io.File;
import java.io.IOException;

public class Helper {

    public static final Logger logger = LoggerFactory.getLogger(Helper.class.getName());
    public static ObjectMapper mapper = new ObjectMapper();
    public static Configuration conf = Configuration.getInstance();
    public static String lowerFirstCharacterOfString(String str)
    {
        if(str == null || str.length() == 0)
            return "";

        if(str.length() == 1)
            return str.toLowerCase();

        char[] chArr = str.toCharArray();
        chArr[0] = Character.toLowerCase(chArr[0]);

        return new String(chArr);
    }

    public static InterfaceGenerator[] convertInterfaceObjectToArray(InterfaceGenerator ig)
    {
        return new InterfaceGenerator[]{ig};
    }

    public static ClassGenerator readGeneratedClass(String filePath){
        try {
             ClassGenerator cg = mapper.readValue(new File(filePath),ClassGenerator.class);
            return cg;
        } catch (IOException e) {
            logger.error("Issues while reading generated Class from path {} {}",filePath,e.getMessage());
        }
        return null;
    }

    public static ClassGenerator readGeneratedClass(File file){
        try {
            ClassGenerator cg = mapper.readValue(file,ClassGenerator.class);
            return cg;
        } catch (IOException e) {
            logger.error("Issues while reading generated Class from path {} {}",file.getAbsolutePath(),e.getMessage());
        }
        return null;
    }
    public static ClassGenerator readGeneratedClass(Pair<File,String> result){
        try {
            ClassGenerator cg = mapper.readValue(result.getFirst(),ClassGenerator.class);
            return cg;
        } catch (IOException e) {
            logger.error("Issues while reading generated Class from path {} {}",result.getFirst().getAbsolutePath(),e.getMessage());
        }
        return null;
    }

    public static InterfaceGenerator readGeneratedInterface(File file){
        try {
            InterfaceGenerator ig = mapper.readValue(file,InterfaceGenerator.class);
            return ig;
        } catch (IOException e) {
            logger.error("Issues while reading generated interface from path {} {}",file.getAbsolutePath(),e.getMessage());
            return null;
        }
    }
    public static InterfaceGenerator readGeneratedInterface(Pair<File,String> result){
        try {
            InterfaceGenerator ig = mapper.readValue(result.getFirst(),InterfaceGenerator.class);
            ig.setName(result.getSecond());
            return ig;
        } catch (IOException e) {
            logger.error("Issues while reading generated interface from path {} {}",result.getFirst().getAbsolutePath(),e.getMessage());
            return null;
        }
    }
    public static InterfaceGenerator readGeneratedInterface(String filePath){
        try {
            InterfaceGenerator ig = mapper.readValue(new File(filePath),InterfaceGenerator.class);
            return ig;
        } catch (IOException e) {
            logger.error("Issues while reading generated interface from path {} {}",filePath,e.getMessage());
            return null;
        }
    }

}

