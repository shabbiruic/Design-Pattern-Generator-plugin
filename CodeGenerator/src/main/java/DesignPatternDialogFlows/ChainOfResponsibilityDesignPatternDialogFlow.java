package DesignPatternDialogFlows;

import Generators.ClassGenerator;
import Generators.ChainOfResponsibilityDesignPattern;
import InputDialogs.*;
import Utilities.DesignPatternNames;
import Utilities.Helper;
import com.intellij.psi.PsiDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChainOfResponsibilityDesignPatternDialogFlow extends DesignPatternDialogFlow {

    // Different types of Dialogs according to needed design pattern
    private StringInput parentInterfaceName;
    private ArrayOfString classesNames;
    private FileSelector entity;
    private static final Logger logger = LoggerFactory.getLogger(ChainOfResponsibilityDesignPatternDialogFlow.class.getName());

    //initializes the flow of dialog boxes
    public ChainOfResponsibilityDesignPatternDialogFlow(PsiDirectory currentDirectory) {

        super(currentDirectory);
        parentInterfaceName = new StringInput(DesignPatternNames.chainOfResponsibilityDesignPattern,"Parent Interface",this);
        classesNames = new ArrayOfString(DesignPatternNames.chainOfResponsibilityDesignPattern,"Concrete Classes",this);
        entity = new FileSelector(DesignPatternNames.chainOfResponsibilityDesignPattern,"Entity Name",this);
        parentInterfaceName.setPrevious(null);
        parentInterfaceName.setNext(classesNames);
        classesNames.setPrevious(parentInterfaceName);
        classesNames.setNext(entity);
        entity.setPrevious(classesNames);
        entity.setNext(null);
        parentInterfaceName.setVisible(true);
        logger.debug("Dialog flow got initialized");
    }

    @Override
    // it collects the user input from all the dialog boxes and pass it to the required generators.
    public int generateCode() {
        String parentInterface = parentInterfaceName.getResult();
        ClassGenerator entityClass = Helper.readGeneratedClass(entity.getResult().getFirst());
        entityClass.setName(entity.getResult().getSecond());
        String concreteClasses[] = classesNames.getResult();
        if(parentInterface!=null&&entityClass!=null&&concreteClasses!=null&&outputPath!=null) {
            logger.debug("User inputs are taken successfully");
            new ChainOfResponsibilityDesignPattern(parentInterface, concreteClasses, entityClass).generateCode(outputPath);
            return 1;
        }
        else{
            logger.error("Issue with inputs");
            return -1;
        }
        }

}

