package DesignPatternDialogFlows;

import Generators.DecoratorDesignPattern;
import Generators.InterfaceGenerator;
import InputDialogs.ArrayOfString;
import InputDialogs.FileSelector;
import Utilities.DesignPatternNames;
import Utilities.Helper;
import com.intellij.psi.PsiDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DecoratorDesignPatternDialogFlow extends DesignPatternDialogFlow {
    // Different types of Dialogs according to the needed design pattern
    private FileSelector parentInterfaceSelector;
    private ArrayOfString concreteClassNames;
    private ArrayOfString decoratorClassNames;
    private static final Logger logger = LoggerFactory.getLogger(DecoratorDesignPatternDialogFlow.class.getName());


    //initializes the flow of dialog boxes
    public DecoratorDesignPatternDialogFlow(PsiDirectory currentDirectory) {
        super(currentDirectory);
        parentInterfaceSelector = new FileSelector(DesignPatternNames.decoratorDesignPattern,"Parent Interface", this);
        concreteClassNames = new ArrayOfString(DesignPatternNames.decoratorDesignPattern, "Concrete Classes Name", this);
        decoratorClassNames = new ArrayOfString(DesignPatternNames.decoratorDesignPattern,"Concrete Decorator Classes Name",this);
        parentInterfaceSelector.setPrevious(null);
        parentInterfaceSelector.setNext(concreteClassNames);
        concreteClassNames.setPrevious(parentInterfaceSelector);
        concreteClassNames.setNext(decoratorClassNames);
        decoratorClassNames.setPrevious(concreteClassNames);
        decoratorClassNames.setNext(null);
        logger.debug("Dialog flow got initialized");
        parentInterfaceSelector.setVisible(true);
    }




    // it collects the user input from all the dialog boxes and pass it to the required generators.
    @Override
    public int generateCode() {

        InterfaceGenerator parentInterface = Helper.readGeneratedInterface(parentInterfaceSelector.getResult().getFirst());
        parentInterface.setName(parentInterfaceSelector.getResult().getSecond());
        String[] concreteClasses = concreteClassNames.getResult();
        String[] decoratorClasses = decoratorClassNames.getResult();
        if (parentInterface != null && concreteClasses != null && decoratorClasses != null && outputPath != null) {
            logger.debug("User inputs are taken successfully");
            new DecoratorDesignPattern(parentInterface, concreteClasses, decoratorClasses).generateCode(outputPath);
        return 1;
         }
        else{
            logger.error("Issue with the inputs");
            return -1;
        }
    }

}

