package DesignPatternDialogFlows;

import Generators.FactoryDesignPattern;
import Generators.InterfaceGenerator;
import InputDialogs.ArrayOfString;
import InputDialogs.FileSelector;
import Utilities.DesignPatternNames;
import Utilities.Helper;
import com.intellij.psi.PsiDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FactoryDesignPatternDialogFlow extends DesignPatternDialogFlow{

    // Different types of Dialogs according to the needed design pattern
    private FileSelector productInterfaceSelector;
    private ArrayOfString productNames;
    private static final Logger logger = LoggerFactory.getLogger(FactoryDesignPatternDialogFlow.class.getName());

    //initializes the flow of dialog boxes
    public FactoryDesignPatternDialogFlow(PsiDirectory currentDirectory) {
        super(currentDirectory);
        productInterfaceSelector = new FileSelector(DesignPatternNames.factoryDesignPattern,"Product Interface",this);
        productNames = new ArrayOfString(DesignPatternNames.factoryDesignPattern,"Product Names",this);
        productInterfaceSelector.setPrevious(null);
        productInterfaceSelector.setNext(productNames);
        productNames.setPrevious(productInterfaceSelector);
        productNames.setNext(null);
        logger.debug("Dialog flow got initialized");
        productInterfaceSelector.setVisible(true);
    }

    // it collects the user input from all the dialog boxes and pass it to the required generators.
    @Override
    public int generateCode() {
        InterfaceGenerator parentInterface = Helper.readGeneratedInterface(productInterfaceSelector.getResult());
        String concreteClasses[] = productNames.getResult();
        if(parentInterface!=null&&concreteClasses!=null&&outputPath!=null)
        {
        logger.debug("User inputs are taken successfully");
        new FactoryDesignPattern(parentInterface,concreteClasses).generateCode(outputPath);
        return 1;
        }
        else{
            logger.error("Issue with the inputs");
            return -1;
        }
    }

}

