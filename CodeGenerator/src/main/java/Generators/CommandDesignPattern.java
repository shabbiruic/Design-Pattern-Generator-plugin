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
public class CommandDesignPattern extends DesignPatternGenerate {
    //interface which will have an execute method.
    private InterfaceGenerator commandInterface;
    private ClassGenerator[] concreteClass;

    //this class will inherit from command class and every concrete class will have the two concrete command class
    // one for turning it on and other for off.
    private ClassGenerator[] concreateCommandClass;
    Logger logger = LoggerFactory.getLogger(CommandDesignPattern.class.getName());

    public CommandDesignPattern(String commandInterfaceName, String[] concreteClassNames) {
        createImplementation(commandInterfaceName,concreteClassNames);
        logger.debug("constructor got executed successfully");
    }

    //In this we are creating concrete command class for each of the concrete class
    // also populating the execute method of specific command class
    public void createImplementation(String commandInterfaceName, String[] concreteClassNames)
    {
        commandInterface = new InterfaceGenerator.InterfaceBuilder(commandInterfaceName,null).
                setMethods(new Method[]{new Method.MethodBuilder(conf.getValue("execute"),true,conf.getValue("java.void")).build()}).build();

        concreteClass = BuildingBlocks.createConcreteClass(concreteClassNames);
        concreateCommandClass = new ClassGenerator[2*concreteClassNames.length];
        int i=0;
        for(ClassGenerator cg:concreteClass)
        {
            Method on = new Method(Helper.lowerFirstCharacterOfString(cg.getName())+conf.getValue("on"),conf.getValue("java.void"),null,false);
            ClassGenerator concreateCommandOnClass = new ClassGenerator.ClassGeneratorBuilder(cg.getName()+conf.getValue("on"),false).build();
            concreateCommandOnClass.setVariables(new Pair[]{new Pair(Helper.lowerFirstCharacterOfString(cg.getName()),cg.getName())});
            Method onConcreteMethod = Method.clone(commandInterface.getMethods()[0]);
            onConcreteMethod.setImplementation(new String[]{Helper.lowerFirstCharacterOfString(cg.getName())+"."+on.getName()+"();"});    concreateCommandOnClass.setMethods(new Method[]{onConcreteMethod});

            concreateCommandOnClass.setMethods(new Method[]{onConcreteMethod});
            concreateCommandClass[i] = concreateCommandOnClass;
            i++;

            Method off = new Method(Helper.lowerFirstCharacterOfString(cg.getName())+conf.getValue("off"),conf.getValue("java.void"),null,false);

            cg.setMethods(new Method[]{on,off});

            ClassGenerator concreateCommandOffClass = new ClassGenerator.ClassGeneratorBuilder(cg.getName()+conf.getValue("off"),false).build();
            concreateCommandOffClass.setVariables(new Pair[]{new Pair(Helper.lowerFirstCharacterOfString(cg.getName()),cg.getName())});
            Method offConcreteMethod = Method.clone(commandInterface.getMethods()[0]);
            offConcreteMethod.setImplementation(new String[]{Helper.lowerFirstCharacterOfString(cg.getName())+"."+off.getName()+"();"});
            concreateCommandOffClass.setMethods(new Method[]{offConcreteMethod});
            concreateCommandClass[i] = concreateCommandOffClass;
            i++;
        }
        logger.debug("create implementation is executed successfully");
    }


    @Override
    public void generateCode(String outputFolderPath) {
        commandInterface.generateCode(outputFolderPath);
        for(ClassGenerator cc:concreteClass)
            cc.generateCode(null,null,outputFolderPath);
        for(ClassGenerator cc: concreateCommandClass)
            cc.generateCode(null, Helper.convertInterfaceObjectToArray(commandInterface),outputFolderPath);
        logger.debug("code got generated successfully");
    }

    @Override
    public int  generateCodeFromTemplate(String templatePath, String outputFolderPath) {
        logger.debug("generating Command design pattern via template");
        return generateCodeFromTemplate(templatePath,outputFolderPath, CommandDesignPattern.class);
    }
}
