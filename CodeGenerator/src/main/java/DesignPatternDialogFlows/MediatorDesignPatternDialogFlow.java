package DesignPatternDialogFlows;

import Generators.MediatorDesignPattern;
import InputDialogs.ArrayOfString;
import Utilities.DesignPatternNames;
import com.intellij.psi.PsiDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MediatorDesignPatternDialogFlow  extends DesignPatternDialogFlow {

    private ArrayOfString classesNames;
    private static final Logger logger = LoggerFactory.getLogger(MediatorDesignPatternDialogFlow.class.getName());

    //initializes the flow of dialog boxes
    public MediatorDesignPatternDialogFlow(PsiDirectory currentDirectory) {
        super(currentDirectory);

        classesNames = new ArrayOfString(DesignPatternNames.mediatorDesignPattern,"Colleague Class Names",  this);
        classesNames.setPrevious(null);
        classesNames.setNext(null);
        logger.debug("Dialog flow got initialized");
        classesNames.setVisible(true);
    }



    // it collects the user input from all the dialog boxes and pass it to the required generators.
    @Override
    public int generateCode() {

        String colleagueClasses[] = classesNames.getResult();
        if(colleagueClasses!=null&&outputPath!=null) {
            logger.debug("User inputs are taken successfully");
            new MediatorDesignPattern(colleagueClasses).generateCode(outputPath);
            return 1;
        }
        else
        {
            logger.error("Issue with the inputs");
            return -1;
        }
    }

}

