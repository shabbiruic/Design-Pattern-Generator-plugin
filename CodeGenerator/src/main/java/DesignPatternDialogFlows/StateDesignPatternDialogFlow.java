package DesignPatternDialogFlows;

import Generators.StateDesignPattern;
import Generators.InterfaceGenerator;
import InputDialogs.*;
import Utilities.DesignPatternNames;
import Utilities.Helper;
import com.intellij.psi.PsiDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StateDesignPatternDialogFlow extends DesignPatternDialogFlow {

    // Different types of Dialogs according to the needed design pattern
    private FileSelector StateInterfaceSelector;
    private ArrayOfString stateClassNames;
    private StringInput contextClassName;
    private static final Logger logger = LoggerFactory.getLogger(StateDesignPatternDialogFlow.class.getName());

    //initializes the flow of dialog boxes
    public StateDesignPatternDialogFlow(PsiDirectory currentDirectory) {

        super(currentDirectory);
        StateInterfaceSelector = new FileSelector(DesignPatternNames.stateDesignPattern,"State Interface", this);
        stateClassNames = new ArrayOfString(DesignPatternNames.stateDesignPattern,"State Names", this);
        contextClassName = new StringInput(DesignPatternNames.stateDesignPattern,"Context Classes" ,this);

        StateInterfaceSelector.setPrevious(null);
        StateInterfaceSelector.setNext(stateClassNames);
        stateClassNames.setPrevious(StateInterfaceSelector);
        stateClassNames.setNext(contextClassName);
        contextClassName.setPrevious(stateClassNames);
        contextClassName.setNext(null);
        logger.debug("Dialog flow got initialized");
        StateInterfaceSelector.setVisible(true);
    }



    // it collects the user input from all the dialog boxes and pass it to the required generators.
    @Override
    public int generateCode() {
        InterfaceGenerator stateInt = Helper.readGeneratedInterface(StateInterfaceSelector.getResult());
        String[] concreteStates = stateClassNames.getResult();
        String contextClass = contextClassName.getResult();
        if (stateInt != null && concreteStates != null && contextClass != null) {
            logger.debug("User inputs are taken successfully");
            new StateDesignPattern(stateInt, concreteStates, contextClass).generateCode(outputPath);
            return 1;
        }
        else{
            logger.error("Issue with the Input");
            return -1;
        }
    }
}

