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
public class DecoratorDesignPattern extends DesignPatternGenerate {
    //every concrete object will inherit this interface
    InterfaceGenerator parentInterface;
    ClassGenerator concreteClasses[];
    // this will also inherit the parent interface
    ClassGenerator parentDecoratorClass;
    ClassGenerator childDecoratedClasses[];

    Logger logger= LoggerFactory.getLogger(DecoratorDesignPattern.class.getName());
    public DecoratorDesignPattern(InterfaceGenerator parentInterface, String[] concreteClassNames, String[] concreteDecoratorClassNames )
    {
        this.parentInterface = parentInterface;
        concreteClasses = BuildingBlocks.createConcreteClass(concreteClassNames);
        childDecoratedClasses = BuildingBlocks.createConcreteClass(concreteDecoratorClassNames);
        createImplementation();
        logger.debug("object got created successfully");
    }


    //creates the decorator class overrides the parent interface methods.
    public void createImplementation()
    {
        parentDecoratorClass = new ClassGenerator.ClassGeneratorBuilder(conf.getValue("decorator")+parentInterface.getName(),true).build();
        Pair selfDecoratorObject = new Pair(Helper.lowerFirstCharacterOfString(parentDecoratorClass.getName()),parentInterface.getName());
        parentDecoratorClass.setVariables(new Pair[]{selfDecoratorObject});
        Method overrideMethod = Method.clone(parentInterface.getMethods()[0]);
        overrideMethod.setAbstract(false);
        overrideMethod.setImplementation(new String[]{selfDecoratorObject.getVariableName()+"."+overrideMethod.getName()+"();"});
        parentDecoratorClass.setMethods(new Method[]{new Method.MethodBuilder(parentDecoratorClass.getName(),false,"").setParameter(new Pair[]{selfDecoratorObject}).setImplementation(new String[]{"this."+selfDecoratorObject.getVariableName()+"="+selfDecoratorObject.getVariableName()+";"}).setConstructor(true).build(),overrideMethod});

        //Child decorator class is overriding the parent interface methods
        for(ClassGenerator cg:childDecoratedClasses)
        {
            Method overrideMethodDecorator = Method.clone(parentInterface.getMethods()[0]);
            overrideMethodDecorator.setAbstract(false);
            overrideMethodDecorator.setImplementation(new String[]{
                    Helper.lowerFirstCharacterOfString(parentDecoratorClass.getName())+"."+overrideMethodDecorator.getName()+"();","//call other method which implements the extra decorator function"});

            cg.setMethods(new Method[]{new Method.MethodBuilder(cg.getName(),false,"").setConstructor(true).setParameter(new Pair[]{new Pair(Helper.lowerFirstCharacterOfString(parentDecoratorClass.getName()),parentInterface.getName())}).setImplementation(new String[]{"super("+ Helper.lowerFirstCharacterOfString(parentDecoratorClass.getName())+");"}).build(),overrideMethodDecorator});;
        }

        logger.debug("implementation class got executed properly");
    }
    @Override
    public void generateCode(String outputFolderPath) {

        parentInterface.generateCode(outputFolderPath);
        for(ClassGenerator cg:concreteClasses)
            cg.generateCode(null,new InterfaceGenerator[]{parentInterface},outputFolderPath);
        parentDecoratorClass.generateCode(null,new InterfaceGenerator[]{parentInterface},outputFolderPath);
        for(ClassGenerator cg:childDecoratedClasses)
            cg.generateCode(parentDecoratorClass,null,outputFolderPath);

        logger.debug("Decorator design pattern got generated successfully");

    }
    @Override
    public int generateCodeFromTemplate(String templatePath, String outputFolderPath) {
        return generateCodeFromTemplate(templatePath,outputFolderPath, DecoratorDesignPattern.class);
    }
}
