package DesignPatternDialogFlows;

import Generators.InterfaceGenerator;
import Generators.InterpreterDesignPattern;
import InputDialogs.ArrayOfString;
import InputDialogs.FileSelector;
import Utilities.DesignPatternNames;
import Utilities.Helper;
import com.intellij.psi.PsiDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InterpreterDesignPatternDialogFlow  extends DesignPatternDialogFlow {

    // Different types of Dialogs according to needed design pattern
    private FileSelector parentInterfaceSelector;
    private ArrayOfString classesNames;
    private static final Logger logger = LoggerFactory.getLogger(InterpreterDesignPatternDialogFlow.class.getName());

    public InterpreterDesignPatternDialogFlow(PsiDirectory currentDirectory) {
        super(currentDirectory);

        parentInterfaceSelector = new FileSelector(DesignPatternNames.interpreterDesignPattern,"Parent Interface",this);
        classesNames = new ArrayOfString(DesignPatternNames.interpreterDesignPattern,"Concrete Classes Name",this);
        parentInterfaceSelector.setPrevious(null);
        parentInterfaceSelector.setNext(classesNames);
        classesNames.setPrevious(parentInterfaceSelector);
        classesNames.setNext(null);
        logger.debug("Dialog flow got initialized");
        parentInterfaceSelector.setVisible(true);
    }



    // it collects the user input from all the dialog boxes and pass it to the required generators.
    @Override
    public int generateCode() {

        InterfaceGenerator parentInterface = Helper.readGeneratedInterface(parentInterfaceSelector.getResult());
        String concreteClasses[] = classesNames.getResult();
        if(parentInterface!=null&&concreteClasses!=null&&outputPath!=null) {
            logger.debug("User inputs are taken successfully");
            new InterpreterDesignPattern(parentInterface, concreteClasses).generateCode(outputPath);
            return 1;
        }
        else
        {
            logger.error("Issue with the input");
            return -1;
        }
    }

}

