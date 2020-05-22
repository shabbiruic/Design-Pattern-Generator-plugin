package DesignPatternDialogFlows;

import Generators.ClassGenerator;
import Generators.InterfaceGenerator;
import Generators.IteratorDesignPattern;
import InputDialogs.FileSelector;
import Utilities.DesignPatternNames;
import Utilities.Helper;
import com.intellij.psi.PsiDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IteratorDesignPatternDialogFlow extends DesignPatternDialogFlow {

    // Different types of Dialogs according to the needed design pattern
    private FileSelector parentInterfaceSelector;
    private FileSelector dataTypeclassSelector;
    private static final Logger logger = LoggerFactory.getLogger(IteratorDesignPatternDialogFlow.class.getName());

    //initializes the flow of dialog boxes
    public IteratorDesignPatternDialogFlow(PsiDirectory currentDirectory) {

        super(currentDirectory);
        parentInterfaceSelector = new FileSelector(DesignPatternNames.iteratorDesignPattern,"Parent Interface", this);
        dataTypeclassSelector = new FileSelector(DesignPatternNames.iteratorDesignPattern,"Data type class", this);
        parentInterfaceSelector.setPrevious(null);
        parentInterfaceSelector.setNext(dataTypeclassSelector);
        dataTypeclassSelector.setPrevious(parentInterfaceSelector);
        dataTypeclassSelector.setNext(null);
        logger.debug("Dialog flow got initialized");
        parentInterfaceSelector.setVisible(true);
    }



    // it collects the user input from all the dialog boxes and pass it to the required generators.
    @Override
    public int generateCode() {
        InterfaceGenerator parentInterface = Helper.readGeneratedInterface(parentInterfaceSelector.getResult());
        ClassGenerator dataTypeClass = Helper.readGeneratedClass(dataTypeclassSelector.getResult());
        if (parentInterface != null && dataTypeClass != null) {
            logger.debug("User inputs are taken successfully");
            new IteratorDesignPattern(parentInterface, dataTypeClass).generateCode(outputPath);
            return 1;
        }
        {
            logger.error("Issue with the provided inputs");
            return -1;
        }
    }

}

