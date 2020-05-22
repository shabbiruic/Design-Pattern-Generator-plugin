package DesignPatternDialogFlows;

import Generators.InterfaceGenerator;
import Generators.FlyweightDesignPattern;
import InputDialogs.ArrayOfString;
import InputDialogs.FileSelector;
import Utilities.DesignPatternNames;
import Utilities.Helper;
import com.intellij.psi.PsiDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlyweightDesignPatternDialogFlow  extends DesignPatternDialogFlow {

    // Different types of Dialogs according to the needed design pattern
    private FileSelector productInterfaceSelector;
    private ArrayOfString productNames;
    private static final Logger logger = LoggerFactory.getLogger(FlyweightDesignPatternDialogFlow.class.getName());

    //initializes the flow of dialog boxes
    public FlyweightDesignPatternDialogFlow(PsiDirectory currentDirectory) {
        super(currentDirectory);

        productInterfaceSelector = new FileSelector(DesignPatternNames.flyweightDesignPattern,"Product Interface",this);
        productNames = new ArrayOfString(DesignPatternNames.flyweightDesignPattern,"Product Names", this);
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
        if(parentInterface!=null&&concreteClasses!=null&&outputPath!=null ) {
            logger.debug("User inputs are taken successfully");
            new FlyweightDesignPattern(parentInterface, concreteClasses).generateCode(outputPath);
            return 1;
        }
        else
        {
            logger.error("Issue with the input");
            return -1;
        }
        }

}

