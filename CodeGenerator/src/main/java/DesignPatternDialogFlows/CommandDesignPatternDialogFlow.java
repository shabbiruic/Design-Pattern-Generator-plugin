package DesignPatternDialogFlows;

import Generators.CommandDesignPattern;
import InputDialogs.ArrayOfString;
import InputDialogs.StringInput;
import Utilities.DesignPatternNames;
import com.intellij.psi.PsiDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandDesignPatternDialogFlow extends DesignPatternDialogFlow {

    // Different types of Dialogs according to needed design pattern
    private StringInput parentInterfaceName;
    private ArrayOfString classesNames;
    private static final Logger logger = LoggerFactory.getLogger(CommandDesignPatternDialogFlow.class.getName());

    //initializes the flow of dialog boxes
    public CommandDesignPatternDialogFlow(PsiDirectory currentDirectory) {
        super(currentDirectory);
        parentInterfaceName = new StringInput(DesignPatternNames.commandDesignPattern,"Parent Interface",this);
        classesNames = new ArrayOfString(DesignPatternNames.commandDesignPattern,"Concrete Classes Name",this);
        parentInterfaceName.setPrevious(null);
        parentInterfaceName.setNext(classesNames);
        classesNames.setPrevious(parentInterfaceName);
        classesNames.setNext(null);
        logger.debug("Dialog flow got initialized");
        parentInterfaceName.setVisible(true);
    }



    // it collects the user input from all the dialog boxes and pass it to the required generators.
    public int generateCode() {
        String parentInterface = parentInterfaceName.getResult();
        String concreteClasses[] = classesNames.getResult();
//        File outputPath = output.getResult();
        if (parentInterface != null && concreteClasses != null && outputPath != null) {
            logger.debug("User inputs are taken successfully");
            new CommandDesignPattern(parentInterface, concreteClasses).generateCode(outputPath);
            return 1;
        }
        {
            logger.error("Issue with the inputs");
            return -1;
        }
    }

}

