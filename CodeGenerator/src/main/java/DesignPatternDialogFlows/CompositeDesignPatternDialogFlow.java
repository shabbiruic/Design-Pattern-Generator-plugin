package DesignPatternDialogFlows;

import Generators.CompositeDesignPattern;
import Generators.InterfaceGenerator;
import InputDialogs.*;
import Utilities.DesignPatternNames;
import Utilities.Helper;
import com.intellij.psi.PsiDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompositeDesignPatternDialogFlow  extends DesignPatternDialogFlow {

    // Different types of Dialogs according to needed design pattern
    private FileSelector parentInterfaceSelector;
    private ArrayOfString leafClassNames;
    private StringInput compositeClassNames;
    private static final Logger logger = LoggerFactory.getLogger(CompositeDesignPatternDialogFlow.class.getName());

    //initializes the flow of dialog boxes
    public CompositeDesignPatternDialogFlow(PsiDirectory currentDirectory) {
        super(currentDirectory);

        parentInterfaceSelector = new FileSelector(DesignPatternNames.compositeDesignPattern,"parent Interface", this);
        leafClassNames = new ArrayOfString(DesignPatternNames.compositeDesignPattern,"Leaf Classes Names", this);
        compositeClassNames = new StringInput(DesignPatternNames.compositeDesignPattern, "Composite Class", this);
        parentInterfaceSelector.setPrevious(null);
        parentInterfaceSelector.setNext(leafClassNames);
        leafClassNames.setPrevious(parentInterfaceSelector);
        leafClassNames.setNext(compositeClassNames);
        compositeClassNames.setPrevious(leafClassNames);
        compositeClassNames.setNext(null);
        logger.debug("Dialog flow got initialized");
        parentInterfaceSelector.setVisible(true);
    }



    // it collects the user input from all the dialog boxes and pass it to the required generators.
    @Override
    public int generateCode() {
        InterfaceGenerator parentInterface = Helper.readGeneratedInterface(parentInterfaceSelector.getResult().getFirst());
        String[] leafClasses = leafClassNames.getResult();
        String compositeClasses = compositeClassNames.getResult();
        if (parentInterface != null && leafClasses != null && compositeClasses != null && outputPath != null) {
            logger.debug("User inputs are taken successfully");
            new CompositeDesignPattern(parentInterface, leafClasses, compositeClasses).generateCode(outputPath);
            return 1;
        } else {
            logger.error("Issue with the inputs");
            return -1;

        }
    }

}

