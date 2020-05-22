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
public class ChainOfResponsibilityDesignPattern extends DesignPatternGenerate {

    // this defines the structure of each handler
    private InterfaceGenerator parentInterface;
    //this is the class which each handler will handle or pass it other concern handler
    private ClassGenerator EntityTobeProcessed;
    //this are the handler class
    private ClassGenerator handlerClass[];
    public static final Logger logger = LoggerFactory.getLogger(ChainOfResponsibilityDesignPattern.class.getName());

    public ChainOfResponsibilityDesignPattern(String parentInterfaceName, String[] concreteClassesName, ClassGenerator EntityTobeProcessed) {
        this.handlerClass = BuildingBlocks.createConcreteClass(concreteClassesName);
        this.EntityTobeProcessed=EntityTobeProcessed;
        createImplementation(parentInterfaceName);
        logger.debug("constructor got executed successfully");
    }

    // Creating handler interface which will have two method one is process and another is setNext
    // also all the concrete handler are overriding those methods.
    public void createImplementation(String parentInterfaceName ){
        parentInterface = new InterfaceGenerator.InterfaceBuilder(parentInterfaceName,null).build();
        Pair setNextMethodparameter = new Pair(Helper.lowerFirstCharacterOfString(parentInterfaceName),parentInterfaceName);
        Pair processMethodParameter = new Pair(Helper.lowerFirstCharacterOfString(EntityTobeProcessed.getName()),EntityTobeProcessed.getName());
        Method setNext = new Method.MethodBuilder(conf.getValue("setNext"),true,conf.getValue("java.void")).setParameter(new Pair[]{setNextMethodparameter}).build();
        Method process = new Method.MethodBuilder(conf.getValue("process"),true,conf.getValue("java.void")).setParameter(new Pair[]{processMethodParameter}).build();
        parentInterface.setMethods( new Method[]{setNext,process});

        for(ClassGenerator cc: handlerClass) {
            String classVariable = conf.getValue("nextIn")+parentInterfaceName;
            cc.setVariables(new Pair[]{new Pair(classVariable,parentInterfaceName)});
            Method overridedNextMethod = Method.clone(setNext);
            overridedNextMethod.setAbstract(false);
            overridedNextMethod.setImplementation(new String[]{classVariable+"="+setNextMethodparameter.getVariableName()+";"});
            cc.setMethods(new Method[]{overridedNextMethod});
        }

        logger.debug("createImplementation method for executed successfully");
    }
    @Override
    public void generateCode(String outputFolderPath) {

        EntityTobeProcessed.generateCode(null,null,outputFolderPath);
        parentInterface.generateCode(outputFolderPath);
        for(ClassGenerator cc: handlerClass) {
            cc.generateCode(null, new InterfaceGenerator[]{parentInterface}, outputFolderPath);
        }
    }

    @Override
    public int generateCodeFromTemplate(String templatePath, String outputFolderPath) {
        return generateCodeFromTemplate(templatePath,outputFolderPath, BuilderDesignPattern.class);
    }
}


