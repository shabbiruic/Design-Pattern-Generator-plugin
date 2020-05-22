package Generators;


import Utilities.BuildingBlocks;
import Utilities.Helper;
import Utilities.Method;
import Utilities.Pair;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@NoArgsConstructor
public class FlyweightDesignPattern extends DesignPatternGenerate {
    //it will keep the basic structure of object.
    private InterfaceGenerator productInterface;

    //this are the class which will implement the parent interface
    private ClassGenerator productClasses[];
    //this will get generated which will take object name as input and return the crossponding object
    private ClassGenerator factoryClass;
    public static final Logger logger = LoggerFactory.getLogger(FlyweightDesignPattern.class.getName());

    public FlyweightDesignPattern(InterfaceGenerator parentInterface, String[] concreteClassNames) {
        this.productInterface = parentInterface;
        productClasses = BuildingBlocks.createConcreteClass(concreteClassNames);
        createImplementation();
        logger.debug("constructor got executed successfully");
    }

    //creating the factory for objects which will return the specified object it already present
    // so that no need to create the same object again and again
    public void createImplementation() {
        factoryClass = new ClassGenerator.ClassGeneratorBuilder(productInterface.getName() + conf.getValue("java.factory"), false).setVariables(new Pair[]{new Pair(productInterface.getName() + conf.getValue("java.map"), "HashMap<String," + productInterface.getName() + ">")}).build();
        factoryClass.setMethods(new Method[]{new Method.MethodBuilder(conf.getValue("java.get") + productInterface.getName(), false, productInterface.getName()).setParameter(new Pair[]{new Pair(Helper.lowerFirstCharacterOfString(productInterface.getName()), productInterface.getName())}).build()});

    }

    @Override
    public void generateCode(String outputFolderPath) {
        productInterface.generateCode(outputFolderPath);
        for (ClassGenerator cg : productClasses)
            cg.generateCode(null, new InterfaceGenerator[]{productInterface}, outputFolderPath);
        factoryClass.generateCode(null, null, outputFolderPath);
        logger.debug("flyweight design pattern got successfully generated");
    }

    @Override
    public int generateCodeFromTemplate(String templatePath, String outputFolderPath) {
        logger.debug("generating the flyweight design pattern using template");
        return generateCodeFromTemplate(templatePath, outputFolderPath, FlyweightDesignPattern.class);
    }


}
