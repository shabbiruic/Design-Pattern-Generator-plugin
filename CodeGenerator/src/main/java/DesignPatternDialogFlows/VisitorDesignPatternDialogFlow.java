package DesignPatternDialogFlows;

import Generators.VisitorDesignPattern;
import Generators.InterfaceGenerator;
import InputDialogs.ArrayOfString;
import InputDialogs.FileSelector;
import Utilities.DesignPatternNames;
import Utilities.Helper;
import com.intellij.psi.PsiDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VisitorDesignPatternDialogFlow extends DesignPatternDialogFlow {

    // Different types of Dialogs according to the needed design pattern
    private FileSelector visitorInterfaceSelector;
    private FileSelector elementInterfaceSelector;
    private ArrayOfString visitorNames;
    private ArrayOfString elementNames;
    private static final Logger logger = LoggerFactory.getLogger(VisitorDesignPatternDialogFlow.class.getName());

    //initializes the flow of dialog boxes
    public VisitorDesignPatternDialogFlow(PsiDirectory currentDirectory) {

        super(currentDirectory);
        visitorInterfaceSelector = new FileSelector(DesignPatternNames.visitorDesignPattern,"Visitor Interface", this);
        elementInterfaceSelector = new FileSelector(DesignPatternNames.visitorDesignPattern,"Element Interface", this);
        visitorNames = new ArrayOfString(DesignPatternNames.visitorDesignPattern, "Element Classes",this);
        elementNames = new ArrayOfString(DesignPatternNames.visitorDesignPattern,"Visitor Classes", this);
        visitorInterfaceSelector.setPrevious(null);
        visitorInterfaceSelector.setNext(visitorNames);
        visitorNames.setPrevious(visitorInterfaceSelector);
        visitorNames.setNext(elementInterfaceSelector);
        elementInterfaceSelector.setPrevious(visitorNames);
        elementInterfaceSelector.setNext(elementNames);
        elementNames.setPrevious(elementInterfaceSelector);
        elementNames.setNext(null);
        logger.debug("Dialog flow got initialized");
        visitorInterfaceSelector.setVisible(true);
    }

    //this gets called when user selects this design pattern from tool menu


    // it collects the user input from all the dialog boxes and pass it to the required generators.
    @Override
    public int generateCode() {
        InterfaceGenerator visitorInt = Helper.readGeneratedInterface(visitorInterfaceSelector.getResult().getFirst());
        InterfaceGenerator elementInt = Helper.readGeneratedInterface(elementInterfaceSelector.getResult().getSecond());
        String[] vistNames = visitorNames.getResult();
        String[] elemtNames = elementNames.getResult();
        if (visitorInt != null && elementInt != null && vistNames != null && elemtNames != null && outputPath != null) {
            logger.debug("User inputs are taken successfully");
            new VisitorDesignPattern(visitorInt, elementInt, vistNames, elemtNames).generateCode(outputPath);
            return 1;
        } else {
            logger.error("Issue with inputs");
            return -1;
        }
    }
}