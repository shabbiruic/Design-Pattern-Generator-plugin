package Generators;


import Utilities.BuildingBlocks;
import Utilities.Helper;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@NoArgsConstructor
public class InterpreterDesignPattern extends DesignPatternGenerate {
    // this will get inherited by all child classes.
    private InterfaceGenerator parentInterface;

    //this are the child classes
    private ClassGenerator concreteClass[];
    public static final Logger logger = LoggerFactory.getLogger(InterpreterDesignPattern.class.getName());

    public InterpreterDesignPattern(InterfaceGenerator parentInterface, String[] concreteClassNames) {
        this.parentInterface = parentInterface;
        concreteClass= BuildingBlocks.createConcreteClass(concreteClassNames);
        logger.debug("constructor executed successfully");
    }


    @Override
    public void generateCode(String outputFolderPath) {
        parentInterface.generateCode(outputFolderPath);
        for(ClassGenerator cg:concreteClass)
            cg.generateCode(null, Helper.convertInterfaceObjectToArray(parentInterface),outputFolderPath);
        logger.debug("interpreter design pattern got generated successfully");
    }

    @Override
    public int generateCodeFromTemplate(String templatePath, String outputFolderPath) {
        logger.debug("Interpreter design pattern is getting generated using template");
        return generateCodeFromTemplate(templatePath,outputFolderPath, InterpreterDesignPattern.class);

    }
}
