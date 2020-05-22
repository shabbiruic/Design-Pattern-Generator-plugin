package DesignPatternDialogFlows;

import Generators.ClassGenerator;
import Generators.PrototypeDesignPattern;
import InputDialogs.ArrayOfString;
import InputDialogs.FileSelector;
import Utilities.DesignPatternNames;
import Utilities.Helper;
import com.intellij.psi.PsiDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrototypeDesignPatternDialogFlow extends DesignPatternDialogFlow {

    private FileSelector abstractClassSelector;
    private ArrayOfString concreteClassNames;
    private static final Logger logger = LoggerFactory.getLogger(PrototypeDesignPatternDialogFlow.class.getName());

    //initializes the flow of dialog boxes
    public PrototypeDesignPatternDialogFlow(PsiDirectory currentDirectory) {
        super(currentDirectory);
        abstractClassSelector = new FileSelector(DesignPatternNames.prototypeDesignPattern,"Abstract state Class", this);
        concreteClassNames = new ArrayOfString(DesignPatternNames.prototypeDesignPattern,"State Names", this);
        abstractClassSelector.setPrevious(null);
        abstractClassSelector.setNext(concreteClassNames);
        concreteClassNames.setPrevious(abstractClassSelector);
        concreteClassNames.setNext(null);
        logger.debug("Dialog flow got initialized");
        abstractClassSelector.setVisible(true);
    }



    // it collects the user input from all the dialog boxes and pass it to the required generators.
    @Override
    public int generateCode() {
        ClassGenerator abstractClass = Helper.readGeneratedClass(abstractClassSelector.getResult());
        abstractClass.setName(abstractClassSelector.getResult().getSecond());
        String[] concreteClasses = concreteClassNames.getResult();
        if (abstractClass != null && concreteClasses != null && outputPath != null) {
            logger.debug("User inputs are taken successfully");
            new PrototypeDesignPattern(abstractClass, concreteClasses).generateCode(outputPath);
            return 1;
        }
        else{
            logger.error("Issues with Input");
            return -1;
        }
    }

}

