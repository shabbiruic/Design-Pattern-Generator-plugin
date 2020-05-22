package DesignPatternDialogFlows;

import Generators.BridgeDesignPattern;
import Generators.InterfaceGenerator;
import InputDialogs.*;
import Utilities.DesignPatternNames;
import Utilities.Helper;
import com.intellij.psi.PsiDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BridgeDesignPatternDialogFlow extends DesignPatternDialogFlow {

    private StringInput mainAbstractClassName;
    private ArrayOfString mainClassNames;
    private FileSelector compositeInterfaceSelector;
    private ArrayOfString compositeClassNames;
    private static final Logger logger = LoggerFactory.getLogger(BridgeDesignPatternDialogFlow.class.getName());

    public BridgeDesignPatternDialogFlow(PsiDirectory currentDirectory) {

        super(currentDirectory);

        //initialized the flow of dialog boxes
        mainAbstractClassName = new StringInput(DesignPatternNames.bridgeDesignPattern, "Main Abstract Class", this);
        mainClassNames = new ArrayOfString(DesignPatternNames.bridgeDesignPattern, "Concrete main Classes", this);
        compositeInterfaceSelector = new FileSelector(DesignPatternNames.bridgeDesignPattern,"Composite Interface", this);
        compositeClassNames = new ArrayOfString(DesignPatternNames.bridgeDesignPattern, "Composite Classes", this);

        mainAbstractClassName.setPrevious(null);
        mainAbstractClassName.setNext(mainClassNames);
        mainClassNames.setPrevious(mainAbstractClassName);
        mainClassNames.setNext(compositeInterfaceSelector);
        compositeInterfaceSelector.setPrevious(mainClassNames);
        compositeInterfaceSelector.setNext(compositeClassNames);
        compositeClassNames.setPrevious(compositeInterfaceSelector);
        compositeClassNames.setNext(null);
        logger.debug("Dialog flow got initialized");
        mainAbstractClassName.setVisible(true);
    }




    // it collects the user input from all the dialog boxes and pass it to the required generators.
    @Override
    public int generateCode() {

        InterfaceGenerator compositeInterface = Helper.readGeneratedInterface(compositeInterfaceSelector.getResult().getFirst());
        compositeInterface.setName(compositeInterfaceSelector.getResult().getSecond());
        String[] compositeClasses = compositeClassNames.getResult();
        String abstractClass = mainAbstractClassName.getResult();
        String[] mainClasses = mainClassNames.getResult();
        if (compositeInterface != null && abstractClass != null && mainClasses != null) {
            logger.debug("User inputs are taken successfully");
            new BridgeDesignPattern(abstractClass, compositeInterface, mainClasses, compositeClasses).generateCode(outputPath);
            return 1;
        } else {
            logger.error("Issue with provided inputs");
            return -1;
        }
    }

}

