package DesignPatternDialogFlows;

import Generators.FacadeDesignPattern;
import Generators.InterfaceGenerator;
import InputDialogs.ArrayOfString;
import InputDialogs.FileSelector;
import Utilities.DesignPatternNames;
import Utilities.Helper;
import com.intellij.psi.PsiDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FacadeDesignPatternDialogFlow extends DesignPatternDialogFlow {

    // Different types of Dialogs according to the needed design pattern
    private FileSelector parentInterfaceSelector;
    private ArrayOfString classesNames;
    private static final Logger logger = LoggerFactory.getLogger(FacadeDesignPatternDialogFlow.class.getName());

    //initializes the flow of dialog boxes
    public FacadeDesignPatternDialogFlow(PsiDirectory currentDirectory) {
        super(currentDirectory);
        parentInterfaceSelector = new FileSelector(DesignPatternNames.facadeDesignPattern,"Parent Interface", this);
        classesNames = new ArrayOfString(DesignPatternNames.facadeDesignPattern, "Concrete Classes Name", this);
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

        InterfaceGenerator parentInterface = Helper.readGeneratedInterface(parentInterfaceSelector.getResult().getFirst());
        parentInterface.setName(parentInterfaceSelector.getResult().getSecond());
        String[] concreteClasses = classesNames.getResult();
        if (parentInterface != null && concreteClasses != null && outputPath != null) {
            logger.debug("User inputs are taken successfully");
            new FacadeDesignPattern(parentInterface, concreteClasses).generateCode(outputPath);
            return 1;
        } else {
            logger.error("Issue with the inputs");
            return -1;
        }
    }
}



