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
public class StateDesignPattern extends DesignPatternGenerate {


    private InterfaceGenerator stateInterface;
    private ClassGenerator stateClass[];
    private ClassGenerator contextClass;
    public static final Logger logger = LoggerFactory.getLogger(StateDesignPattern.class.getName());

    public StateDesignPattern(InterfaceGenerator stateInterface, String[] stateClassName, String contextClassName) {
        this.stateInterface = stateInterface;
        stateClass = BuildingBlocks.createConcreteClass(stateClassName);
        createImplementation(contextClassName);
        logger.info("constructor got executed successfully");

    }
    public void createImplementation(String contextClassName)
    {
        contextClass = new ClassGenerator.ClassGeneratorBuilder(contextClassName,false).build();
        contextClass.setVariables(new Pair[]{new Pair(Helper.lowerFirstCharacterOfString(stateInterface.getName()),stateInterface.getName())});
        contextClass.setMethods(new Method[]{new Method.MethodBuilder(conf.getValue("java.set")+stateInterface.getName(),false,conf.getValue("java.void")).setImplementation(new String[]{
                conf.getValue("java.this")+"."+ Helper.lowerFirstCharacterOfString(stateInterface.getName())+"="+ Helper.lowerFirstCharacterOfString(stateInterface.getName())+";"}).setParameter(new Pair[]{new Pair(Helper.lowerFirstCharacterOfString(stateInterface.getName()),stateInterface.getName())}).build(), new Method.MethodBuilder(conf.getValue("action"),false,conf.getValue("java.void")).setImplementation(new String[]{Helper.lowerFirstCharacterOfString(stateInterface.getName())+"."+stateInterface.getMethods()[0].getName()+"();"}).build()});
        logger.info("create Implementation get executed successfully");
    }

    @Override
    public void generateCode(String outputFolderPath) {
        stateInterface.generateCode(outputFolderPath);
        for(ClassGenerator cg:stateClass)
            cg.generateCode(null, Helper.convertInterfaceObjectToArray(stateInterface),outputFolderPath);
        contextClass.generateCode(null,null,outputFolderPath);
        logger.debug("code got generated successfully");
    }

    @Override
    public int generateCodeFromTemplate(String templatePath, String outputFolderPath) {
        logger.debug("generating State Design pattern via template approach");
        return generateCodeFromTemplate(templatePath,outputFolderPath, StateDesignPattern.class);
    }
}
