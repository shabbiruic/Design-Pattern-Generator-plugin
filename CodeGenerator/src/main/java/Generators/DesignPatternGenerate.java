package Generators;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

@Data
@NoArgsConstructor
//this is the parent class which will get inherited by all the concrete design patterns.
public abstract class DesignPatternGenerate implements Generatable {

    public static final Logger logger = LoggerFactory.getLogger(DesignPatternGenerate.class.getName());

    static ObjectMapper mapper = new ObjectMapper();
    public abstract void generateCode(String outputFolderPath);

    // every design pattern will write their implementation by providing class name
    public abstract int generateCodeFromTemplate(String templatePath, String outputFolderPath);

    // generic implemention for all design patterns to read predefined template and generate the specific pattern
    public <T extends DesignPatternGenerate> int generateCodeFromTemplate(String templatePath, String outputFolderPath, Class<T> valueType) {
        try {
            T designPattern = mapper.readValue(new File(templatePath), valueType);
              designPattern.generateCode(outputFolderPath);
              return 1;

        }
        catch(UnrecognizedPropertyException e)
        {
            logger.error(" {} template is not matching with class {} ",outputFolderPath,valueType.getName());
            e.printStackTrace();
        }
        catch (IOException e) {
            logger.error("Issues while reading template from {} and errors are{}",valueType.getClass().getName(),e.getMessage());
            e.printStackTrace();
        }
        finally {
            return -1;
        }
    }

}
