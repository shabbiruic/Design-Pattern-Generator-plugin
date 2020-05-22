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
public class FactoryDesignPattern extends DesignPatternGenerate {

    private InterfaceGenerator productInterface;
    private ClassGenerator concreteProduct[];
    private ClassGenerator designPatternImplClass;
    public static final Logger logger = LoggerFactory.getLogger(FactoryDesignPattern.class.getName());

    public FactoryDesignPattern(InterfaceGenerator parentInterface, String[] concreteClassesNames) {
        this.productInterface = parentInterface;
        this.concreteProduct = BuildingBlocks.createConcreteClass(concreteClassesNames);
        createFactoryImplementation();
        logger.debug("constructor got executed successfully");
    }

    //create a factory class
    public void createFactoryImplementation()
    {

        Method m[] = {new Method("get"+ productInterface.getName(), productInterface.getName(),new Pair[]{new Pair(Helper.lowerFirstCharacterOfString(productInterface.getName()), conf.getValue("java.dataType.string"))},false)};
        designPatternImplClass = new ClassGenerator(conf.getValue("factoryDesignPattern"),false);
        designPatternImplClass.setMethods(m);

    }
    @Override
    public void generateCode(String outputFolderPath)
    {
        productInterface.generateCode(outputFolderPath);
        for(ClassGenerator cg: concreteProduct)
            cg.generateCode(null,new InterfaceGenerator[]{productInterface},outputFolderPath);
        designPatternImplClass.generateCode(null,null,outputFolderPath);
        logger.debug("Factory design pattern generated successfully");
    }
    @Override
    public int generateCodeFromTemplate(String templatePath, String outputFolderPath) {
        logger.debug("generating factory design pattern using template");
        return generateCodeFromTemplate(templatePath,outputFolderPath, FactoryDesignPattern.class);
    }
}
