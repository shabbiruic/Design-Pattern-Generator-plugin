package DesignPatternDialogFlows;

import Generators.TemplateDesignPattern;
import Generators.ClassGenerator;
import InputDialogs.ArrayOfString;
import InputDialogs.FileSelector;
import Utilities.DesignPatternNames;
import Utilities.Helper;
import com.intellij.psi.PsiDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateDesignPatternDialogFlow  extends DesignPatternDialogFlow {

    private FileSelector templateClassSelector;
    private ArrayOfString concreteTemplateNames;
    private static final Logger logger = LoggerFactory.getLogger(TemplateDesignPatternDialogFlow.class.getName());

    //initializes the flow of dialog boxes
    public TemplateDesignPatternDialogFlow(PsiDirectory currentDirectory) {

        super(currentDirectory);
        templateClassSelector = new FileSelector(DesignPatternNames.templateDesignPattern,"Template Interface", this);
        concreteTemplateNames = new ArrayOfString(DesignPatternNames.templateDesignPattern,"Template Classes",this);
        templateClassSelector.setPrevious(null);
        templateClassSelector.setNext(concreteTemplateNames);
        concreteTemplateNames.setPrevious(templateClassSelector);
        concreteTemplateNames.setNext(null);
        logger.debug("Dialog flow got initialized");
        templateClassSelector.setVisible(true);
    }



    // it collects the user input from all the dialog boxes and pass it to the required generators.
    @Override
    public int generateCode() {
        ClassGenerator tempClass = Helper.readGeneratedClass(templateClassSelector.getResult().getFirst());
        String[] templateClasses = concreteTemplateNames.getResult();
        if(tempClass !=null && templateClasses!=null) {
        logger.debug("User inputs are taken successfully");
        new TemplateDesignPattern(tempClass,templateClasses).generateCode(outputPath);
        return 1;
    } else {
        logger.error("Issue with inputs");
        return -1;
    }
    }

}

