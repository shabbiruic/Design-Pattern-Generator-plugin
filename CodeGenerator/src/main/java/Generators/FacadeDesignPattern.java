package Generators;

import Utilities.BuildingBlocks;
import Utilities.Method;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@NoArgsConstructor
public class FacadeDesignPattern extends DesignPatternGenerate {

    private InterfaceGenerator parentInterface;
    private ClassGenerator[] concreteClasses;
    private ClassGenerator designPatternImplClass;
    public static final Logger logger = LoggerFactory.getLogger(FacadeDesignPattern.class.getName());


    public FacadeDesignPattern(InterfaceGenerator parentInterface, String[] concreteClassesName) {
        this.parentInterface = parentInterface;
        this.concreteClasses = BuildingBlocks.createConcreteClass(concreteClassesName);
        createImplementation();
        logger.debug("object got created");
    }

    //In this we are creating a method for every concrete class to get its respective object from facde design pattern
    public void createImplementation()
    {
        Method methods[] = new Method[concreteClasses.length];
        int i=0;
        for(ClassGenerator cc:concreteClasses)
        {
            methods[i] = new Method.MethodBuilder("get"+cc.getName(),false,cc.getName()).build();
            i++;
        }
        designPatternImplClass = new ClassGenerator.ClassGeneratorBuilder(conf.getValue("facadeDesignPattern"),false).setMethods(methods).build();
        logger.debug("implementation is exceuted successfully");
    }

    @Override
    public void generateCode(String outputFolderPath) {
        logger.info("Generating output at {} ", outputFolderPath);
        parentInterface.generateCode(outputFolderPath);
        for(ClassGenerator cc:concreteClasses)
            cc.generateCode(null,new InterfaceGenerator[]{parentInterface},outputFolderPath);
        designPatternImplClass.generateCode(null,null,outputFolderPath);
        logger.debug("Facade design pattern got created");
    }
    @Override
    public int generateCodeFromTemplate(String templatePath, String outputFolderPath) {
        return generateCodeFromTemplate(templatePath,outputFolderPath, FacadeDesignPattern.class);
    }
}