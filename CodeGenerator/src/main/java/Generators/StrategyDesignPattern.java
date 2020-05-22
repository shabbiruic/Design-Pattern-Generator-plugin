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
public class StrategyDesignPattern extends DesignPatternGenerate {

    private InterfaceGenerator strategyInterface;
    private ClassGenerator concreteStrategy[];
    private ClassGenerator contextClass;
    public static final Logger logger = LoggerFactory.getLogger(StrategyDesignPattern.class.getName());
    public StrategyDesignPattern(InterfaceGenerator strategyInterface, String concreteStrategyNames[], String contextClassName) {
        this.strategyInterface = strategyInterface;
        concreteStrategy = BuildingBlocks.createConcreteClass(concreteStrategyNames);
        createImplementation(contextClassName);
        logger.info("constructor got executed successfully");
    }

//Todo write a helper method which will add all the pairs as method parameters list
    public void createImplementation(String contextClassName)
    {
        contextClass = new ClassGenerator(contextClassName,false);
        contextClass.setVariables(new Pair[]{new Pair(Helper.lowerFirstCharacterOfString(strategyInterface.getName()),strategyInterface.getName())});
        Method m = Method.clone(strategyInterface.getMethods()[0]);
        m.setAbstract(false);
        m.setImplementation(new String[]{Helper.lowerFirstCharacterOfString(strategyInterface.getName())+"."+m.getName()+"("+
                m.getParameter()[0].getVariableName()+","+m.getParameter()[1].getVariableName()+");"});
        contextClass.setMethods(new Method[]{new Method.MethodBuilder(conf.getValue("java.set")+strategyInterface.getName(),false,"void").setParameter(new Pair[]{new Pair(Helper.lowerFirstCharacterOfString(strategyInterface.getName()),strategyInterface.getName())}).setImplementation(new String[]{"this."+ Helper.lowerFirstCharacterOfString(strategyInterface.getName())+"="+ Helper.lowerFirstCharacterOfString(strategyInterface.getName())+";"}).build(),m});
        logger.info("create Implementation get executed successfully");
    }
    @Override
    public void generateCode(String outputFolderPath) {
        strategyInterface.generateCode(outputFolderPath);
        for(ClassGenerator cg:concreteStrategy)
            cg.generateCode(null, Helper.convertInterfaceObjectToArray(strategyInterface),outputFolderPath);
        contextClass.generateCode(null,null,outputFolderPath);
        logger.debug("code got generated successfully");
    }

    @Override
    public int generateCodeFromTemplate(String templatePath, String outputFolderPath) {
        logger.debug("generating  strategy design pattern via template approach");
        return generateCodeFromTemplate(templatePath,outputFolderPath, StrategyDesignPattern.class);

    }
}
