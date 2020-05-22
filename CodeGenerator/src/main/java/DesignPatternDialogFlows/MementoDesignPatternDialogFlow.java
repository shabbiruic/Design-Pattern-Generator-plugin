package DesignPatternDialogFlows;

import Generators.ClassGenerator;
import Generators.MementoDesignPattern;
import InputDialogs.FileSelector;
import Utilities.DesignPatternNames;
import Utilities.Helper;
import com.intellij.psi.PsiDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MementoDesignPatternDialogFlow  extends DesignPatternDialogFlow {

    private FileSelector classSelector;
    private static final Logger logger = LoggerFactory.getLogger(MementoDesignPatternDialogFlow.class.getName());



    //initializes the flow of dialog boxes
    public MementoDesignPatternDialogFlow(PsiDirectory currentDirectory) {
        super(currentDirectory);
        classSelector = new FileSelector(DesignPatternNames.mementoDesignPatter,"Class Name", this);
        classSelector.setPrevious(null);
        classSelector.setNext(null);
        logger.debug("Dialog flow got initialized");
        classSelector.setVisible(true);
    }


    // it collects the user input from all the dialog boxes and pass it to the required generators.
    @Override
    public int generateCode() {
        ClassGenerator objectClass = Helper.readGeneratedClass(classSelector.getResult().getFirst());
        objectClass.setName(classSelector.getResult().getSecond());
        if(objectClass!=null&&outputPath!=null) {
            logger.debug("User inputs are taken successfully");
            new MementoDesignPattern(objectClass).generateCode(outputPath);
            return 1;
        }
        else
        {
            logger.error("Issue with inputs");
            return -1;
        }
    }

}

