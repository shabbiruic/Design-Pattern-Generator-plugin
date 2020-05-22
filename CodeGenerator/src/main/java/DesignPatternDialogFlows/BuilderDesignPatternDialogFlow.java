package DesignPatternDialogFlows;

import Generators.BuilderDesignPattern;
import Generators.ClassGenerator;
import InputDialogs.FileSelector;
import Utilities.DesignPatternNames;
import Utilities.Helper;
import com.intellij.psi.PsiDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BuilderDesignPatternDialogFlow extends DesignPatternDialogFlow {

    // Different types of Dialogs according to needed design pattern
    private FileSelector classSelector;
    private static final Logger logger = LoggerFactory.getLogger(BuilderDesignPatternDialogFlow.class.getName());

    public BuilderDesignPatternDialogFlow(PsiDirectory currentDirectory) {
        super(currentDirectory);
        //initializing the flow of dialog boxes
        classSelector = new FileSelector(DesignPatternNames.builderDesignPattern,"Selected Class", this);
        classSelector.setPrevious(null);
        classSelector.setNext(null);
        logger.debug("Dialog flow got initialized");
        classSelector.setVisible(true);
    }


    // it collects the user input from all the dialog boxes and pass it to the required generators.
    @Override
    public int generateCode() {
        ClassGenerator clazz = Helper.readGeneratedClass(classSelector.getResult().getFirst());
        clazz.setName(classSelector.getResult().getSecond());
        if(clazz!=null&&outputPath!=null) {
            logger.debug("User inputs are taken successfully");
            new BuilderDesignPattern(clazz).generateCode(outputPath);
            return 1;
        }
        else
        {
            logger.error("Issue with inputs during creation");
            return -1;
        }
    }

}

