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
public class BridgeDesignPattern extends DesignPatternGenerate {
    //this is the main object abstract class from which concrete object are created.
    ClassGenerator mainAbstractClass;

    //This are the object which are part of main class object but are different for different type of main object.
    InterfaceGenerator compositionInterface;
    ClassGenerator mainClasses[];
    ClassGenerator compositionClasses[];
    public static final Logger logger = LoggerFactory.getLogger(BridgeDesignPattern.class.getName());

    public BridgeDesignPattern(String mainAbstractClassName, InterfaceGenerator compositionInterface, String[] mainClassesName, String[] compositionClassesName) {
        logger.debug("Constructor get called...");
        mainAbstractClass = new ClassGenerator(mainAbstractClassName,true);
        this.compositionInterface = compositionInterface;
        mainClasses = BuildingBlocks.createConcreteClass(mainClassesName);
        compositionClasses = BuildingBlocks.createConcreteClass(compositionClassesName);
        CreateImplementation();
        logger.debug("Object got generated Successfully...");
    }

//    This method populates the other member fields and details that are not provided my user
//    but are needed for design pattern generation
    public void CreateImplementation()
    {
        Method abstractMethodConstructor = new Method.MethodBuilder(mainAbstractClass.getName(),false,"").setConstructor(true).build();
        Pair compositeInterfacePair[] =   new Pair[]{new Pair(Helper.lowerFirstCharacterOfString(compositionInterface.getName()),compositionInterface.getName())};
        abstractMethodConstructor.setParameter(compositeInterfacePair);
        abstractMethodConstructor.setImplementation(new String[]{conf.getValue("java.this")+"."+compositeInterfacePair[0].getVariableName()+"="+compositeInterfacePair[0].getVariableName()+";"});
        Method compositionInterfaceMethod = Method.clone(compositionInterface.getMethods()[0]);
        mainAbstractClass.setMethods(new Method[]{abstractMethodConstructor,compositionInterfaceMethod});
        mainAbstractClass.setVariables(compositeInterfacePair);
        //loop which initializes and adds details to every concrete main class
        // like overrides abstract methods and constructor creation.
        for(ClassGenerator cg:mainClasses)
        {
            Pair pairs[] = mainAbstractClass.getVariables();
            Method m1 = Method.clone(mainAbstractClass.getMethods()[1]);
            m1.setAbstract(false);
            m1.setImplementation(new String[]{pairs[0].getVariableName()+"."+m1.getName()+"();"});

            Method m2 = new Method.MethodBuilder(cg.getName(),false,"").setConstructor(true).setParameter(new Pair[]{
                    new Pair(Helper.lowerFirstCharacterOfString(compositionInterface.getName()),compositionInterface.getName())
            }).setImplementation(new String[]{conf.getValue("java.super")+"("+ Helper.lowerFirstCharacterOfString(compositionInterface.getName())+");"}).build();
            cg.setMethods(new Method[]{m1,m2});
        }

        }
    @Override
    public void generateCode(String outputFolderPath) {
        mainAbstractClass.generateCode(null,null,outputFolderPath);
        compositionInterface.generateCode(outputFolderPath);
        for(ClassGenerator cg:mainClasses)
            cg.generateCode(mainAbstractClass,null,outputFolderPath);
        for(ClassGenerator cg:compositionClasses)
            cg.generateCode(null,new InterfaceGenerator[]{compositionInterface},outputFolderPath);

    }
    @Override
    public int generateCodeFromTemplate(String templatePath, String outputFolderPath) {
        return generateCodeFromTemplate(templatePath,outputFolderPath, BridgeDesignPattern.class);
    }
}
