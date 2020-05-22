package DesignPatternDialogFlows;

import Generators.StrategyDesignPattern;
import Generators.InterfaceGenerator;
import InputDialogs.*;
import Utilities.DesignPatternNames;
import Utilities.Helper;
import com.intellij.psi.PsiDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StrategyDesignPatternDialogFlow  extends DesignPatternDialogFlow{

    // Different types of Dialogs according to the needed design pattern
    private FileSelector strategyInterface;

    public ArrayOfString getConcreteStrategyNames() {
        return concreteStrategyNames;
    }

    private ArrayOfString concreteStrategyNames;

    public StringInput getContextClassName() {
        return contextClassName;
    }

    private StringInput contextClassName;
    private static final Logger logger = LoggerFactory.getLogger(StrategyDesignPatternDialogFlow.class.getName());

    //initializes the flow of dialog boxes
    public StrategyDesignPatternDialogFlow(PsiDirectory currentDirectory) {
        super(currentDirectory);
        strategyInterface = new FileSelector(DesignPatternNames.strategyDesignPattern,"Strategy Interface", this);
        concreteStrategyNames = new ArrayOfString(DesignPatternNames.strategyDesignPattern,"Strategy Classes",this);
        contextClassName = new StringInput(DesignPatternNames.strategyDesignPattern,"Context Classes Name" ,this);
        strategyInterface.setPrevious(null);
        strategyInterface.setNext(concreteStrategyNames);
        concreteStrategyNames.setPrevious(strategyInterface);
        concreteStrategyNames.setNext(contextClassName);
        contextClassName.setPrevious(concreteStrategyNames);
        contextClassName.setNext(null);
        logger.debug("Dialog flow got initialized");
        strategyInterface.setVisible(true);
    }

    // it collects the user input from all the dialog boxes and pass it to the required generators.
    @Override
    public int generateCode() {
        InterfaceGenerator stratInterface = Helper.readGeneratedInterface(strategyInterface.getResult());
        String[] concreteStratNames = concreteStrategyNames.getResult();
        String contextClazzName = contextClassName.getResult();
        if (stratInterface != null && concreteStratNames != null && outputPath != null) {
            logger.debug("User inputs are taken successfully");
            new StrategyDesignPattern(stratInterface, concreteStratNames, contextClazzName).generateCode(outputPath);
            return 1;
        } else {
            logger.error("Issue with the input");
            return -1;
        }
    }
}