package Generators;

import Utilities.Helper;
import Utilities.Method;
import Utilities.Pair;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@NoArgsConstructor
public class IteratorDesignPattern extends DesignPatternGenerate {

    //parent datatype which is inherited by all user defined data type.
    private InterfaceGenerator parentDataTypeInterface;

    //it will provide the methods which itertor will implement
    private InterfaceGenerator iteratorInterface;
    //class for which we want to create iterator
    private ClassGenerator dataTypeClass;

    //concrete iterator created for the class will get stored in this
    private ClassGenerator concreteIteratorClass;

    public static final Logger logger = LoggerFactory.getLogger(IteratorDesignPattern.class.getName());

    public IteratorDesignPattern(InterfaceGenerator iteratorInterface, ClassGenerator dataTypeClass) {
        this.parentDataTypeInterface = new InterfaceGenerator.InterfaceBuilder("ParentDataType", null).build();
        this.iteratorInterface = iteratorInterface;
        this.dataTypeClass = dataTypeClass;
        createImplementation();
        logger.debug("constructor got executed successfully");
    }

    //creating concrete iterator class
    public void createImplementation() {
        String concreateIteratorClassName = dataTypeClass.getName() + iteratorInterface.getName();
        concreteIteratorClass = new ClassGenerator.ClassGeneratorBuilder(concreateIteratorClassName, false).setVariables(new Pair[]{new Pair(Helper.lowerFirstCharacterOfString(dataTypeClass.getName()), dataTypeClass.getName() + "[]"), new Pair("position", conf.getValue("java.dataType.int"))}).build();
        concreteIteratorClass.setMethods(new Method[]{new Method.MethodBuilder(concreateIteratorClassName, false, "").setParameter(new Pair[]{new Pair(Helper.lowerFirstCharacterOfString(dataTypeClass.getName()), dataTypeClass.getName() + "[]")}).setImplementation(new String[]{"this." + Helper.lowerFirstCharacterOfString(dataTypeClass.getName()) + "=" + Helper.lowerFirstCharacterOfString(dataTypeClass.getName()) + ";", "position = 0;"}).build()});

    }


    @Override
    public void generateCode(String outputFolderPath) {
        parentDataTypeInterface.generateCode(outputFolderPath);
        iteratorInterface.generateCode(outputFolderPath);
        dataTypeClass.generateCode(null, Helper.convertInterfaceObjectToArray(parentDataTypeInterface), outputFolderPath);
        concreteIteratorClass.generateCode(null, Helper.convertInterfaceObjectToArray(iteratorInterface), outputFolderPath);
        logger.debug("code got generated successfully");
    }

    @Override
    public int generateCodeFromTemplate(String templatePath, String outputFolderPath) {
        logger.debug("genrating Iterator design pattern via template");
        return generateCodeFromTemplate(templatePath, outputFolderPath, IteratorDesignPattern.class);

    }
}
