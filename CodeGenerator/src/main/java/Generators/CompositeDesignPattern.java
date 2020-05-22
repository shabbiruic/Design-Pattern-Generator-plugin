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
public class CompositeDesignPattern extends DesignPatternGenerate {

    // defines the basic structure of every class
    private InterfaceGenerator commonInterface;
    // this are leaf classes which will implement the common interface
    private ClassGenerator leafClasses[];

    //this class will have collection of leaf class object within them.
    private ClassGenerator compositeClass;

    Logger logger = LoggerFactory.getLogger(CompositeDesignPattern.class.getName());

    public CompositeDesignPattern(InterfaceGenerator commonInterface, String[] leafClassNames, String compositeClassName ) {
        this.commonInterface = commonInterface;
        leafClasses = BuildingBlocks.createConcreteClass(leafClassNames);
        compositeClass = BuildingBlocks.createConcreteClass(compositeClassName);
        createImplementation();
        logger.debug("object created successfully");
    }

    //populates the composite class methods and varaibles.
    public void createImplementation()
    {
        compositeClass.setVariables(new Pair[]{new Pair(Helper.lowerFirstCharacterOfString(commonInterface.getName())+conf.getValue("list"),"List<"+commonInterface.getName()+">")});
        Method m= Method.clone(commonInterface.getMethods()[0]);
        m.setImplementation(new String[]{"for("+commonInterface.getName()+" "+ Helper.lowerFirstCharacterOfString(commonInterface.getName())+": "+compositeClass.getVariables()[0].getVariableName() +"){",
                Helper.lowerFirstCharacterOfString(commonInterface.getName())+"."+commonInterface.getMethods()[0].getName()+"();","}"});
        compositeClass.setMethods(new Method[]{m});
    }
    @Override
    public void generateCode(String outputFolderPath) {
        commonInterface.generateCode(outputFolderPath);
        for(ClassGenerator cc:leafClasses)
            cc.generateCode(null,new InterfaceGenerator[]{commonInterface},outputFolderPath);
        compositeClass.generateCode(null,new InterfaceGenerator[]{commonInterface},outputFolderPath);
        logger.debug("composite design pattern got generated successfully");
    }

    @Override
    public int generateCodeFromTemplate(String templatePath, String outputFolderPath) {
        return generateCodeFromTemplate(templatePath,outputFolderPath, CompositeDesignPattern.class);
    }
}
