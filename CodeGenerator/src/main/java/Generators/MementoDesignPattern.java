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
public class MementoDesignPattern extends DesignPatternGenerate {

    //class which you want to be saved
    private ClassGenerator objectTobeSaved;
    // class which will keep the objects for future use
    private ClassGenerator mementoClass;

    // it will provide methods to add and get object from memento class
    private ClassGenerator caretakerClass;

    public static final Logger logger = LoggerFactory.getLogger(MementoDesignPattern.class.getName());

    public MementoDesignPattern(ClassGenerator objectTobeSaved) {
        this.objectTobeSaved = objectTobeSaved;
        createImplementation();
        logger.debug("constructor got executed successfully");
    }

    //creating implementation for memento and caretaker classes
    public void createImplementation() {
        String implementation[] = new String[objectTobeSaved.getVariables().length];
        int i = 0;
        for (Pair p : objectTobeSaved.getVariables()) {
            implementation[i] = "this." + p.getVariableName() + "=" + Helper.lowerFirstCharacterOfString(objectTobeSaved.getName()) + "." + p.getVariableName() + ";";
            i++;
        }

        mementoClass = new ClassGenerator.ClassGeneratorBuilder(objectTobeSaved.getName() + conf.getValue("memento"), false).build();
        mementoClass.setVariables(BuildingBlocks.getFinalVariables(objectTobeSaved.getVariables()));
        mementoClass.setMethods(new Method[]{new Method.MethodBuilder(mementoClass.getName(), false, "").setConstructor(true).setImplementation(implementation).setParameter(new Pair[]{new Pair(Helper.lowerFirstCharacterOfString(objectTobeSaved.getName()), objectTobeSaved.getName())}).build(), new Method.MethodBuilder(conf.getValue("java.get") + objectTobeSaved.getName(), false, objectTobeSaved.getName()).build()});

        caretakerClass = new ClassGenerator.ClassGeneratorBuilder(conf.getValue("careTaker") + objectTobeSaved.getName(), false).build();
        caretakerClass.setVariables(new Pair[]{new Pair(conf.getValue("memento") + conf.getValue("list"), conf.getValue("list") + "<" + mementoClass.getName() + ">")});
        caretakerClass.setMethods(new Method[]{new Method.MethodBuilder(conf.getValue("add"), false, "void").setParameter(new Pair[]{new Pair(Helper.lowerFirstCharacterOfString(mementoClass.getName()), mementoClass.getName())}).build(), new Method.MethodBuilder(conf.getValue("java.get"), false, mementoClass.getName()).build()});
    logger.debug("create implementation is called successfully");
    }


    @Override
    public void generateCode(String outputFolderPath) {
        objectTobeSaved.generateCode(null, null, outputFolderPath);
        mementoClass.generateCode(null, null, outputFolderPath);
        caretakerClass.generateCode(null, null, outputFolderPath);
        logger.debug("code got generated successfully");
    }

    @Override
    public int generateCodeFromTemplate(String templatePath, String outputFolderPath) {
        logger.debug("genrating Memento design pattern via template");
        return generateCodeFromTemplate(templatePath, outputFolderPath, MementoDesignPattern.class);

    }
}
