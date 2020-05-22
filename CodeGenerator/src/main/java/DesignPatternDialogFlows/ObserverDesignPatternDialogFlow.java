package DesignPatternDialogFlows;

import Generators.ClassGenerator;
import Generators.InterfaceGenerator;
import Generators.ObserverDesignPattern;
import InputDialogs.ArrayOfString;
import InputDialogs.FileSelector;
import Utilities.DesignPatternNames;
import Utilities.Helper;
import com.intellij.psi.PsiDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObserverDesignPatternDialogFlow extends DesignPatternDialogFlow {

    // Different types of Dialogs according to the needed design pattern
    private FileSelector publisherInterSelector;
    private FileSelector subscriberInterSelector;
    private ArrayOfString publisherClassNames;
    private ArrayOfString subscriberClassNames;
    private FileSelector messageClassSelector;
    private static final Logger logger = LoggerFactory.getLogger(ObserverDesignPatternDialogFlow.class.getName());

    //initializes the flow of dialog boxes
    public ObserverDesignPatternDialogFlow(PsiDirectory currentDirectory) {
        super(currentDirectory);
//        output = new DirectorySelector("Select output Directory Location", this);
        publisherInterSelector = new FileSelector(DesignPatternNames.observerDesignPattern,"Publisher Interface", this);
        subscriberInterSelector = new FileSelector(DesignPatternNames.observerDesignPattern,"Subscriber Interface", this);
        publisherClassNames = new ArrayOfString(DesignPatternNames.observerDesignPattern,"Publisher classes Name", this);
        subscriberClassNames = new ArrayOfString(DesignPatternNames.observerDesignPattern,"Subscriber classes Name", this);
        messageClassSelector = new FileSelector(DesignPatternNames.observerDesignPattern,"Message Class", this);

        publisherInterSelector.setPrevious(null);
        publisherInterSelector.setNext(publisherClassNames);
        publisherClassNames.setPrevious(publisherInterSelector);
        publisherClassNames.setNext(subscriberInterSelector);
        subscriberInterSelector.setPrevious(publisherClassNames);
        subscriberInterSelector.setNext(subscriberClassNames);
        subscriberClassNames.setPrevious(subscriberInterSelector);
        subscriberClassNames.setNext(messageClassSelector);
        messageClassSelector.setPrevious(subscriberClassNames);
        messageClassSelector.setNext(null);
        logger.debug("Dialog flow got initialized");
        publisherInterSelector.setVisible(true);
    }



    // it collects the user input from all the dialog boxes and pass it to the required generators.
    @Override
    public int generateCode() {
        InterfaceGenerator publishInterface = Helper.readGeneratedInterface(publisherInterSelector.getResult());
        InterfaceGenerator subscribInterface = Helper.readGeneratedInterface(subscriberInterSelector.getResult());
        String[] publishClasses = publisherClassNames.getResult();
        String[] subscribClasses = subscriberClassNames.getResult();
        ClassGenerator messageClass = Helper.readGeneratedClass(messageClassSelector.getResult());
        if (publishInterface != null && subscribClasses != null && subscribInterface != null && publishClasses != null && messageClass != null) {
            logger.debug("User inputs are taken successfully");
            new ObserverDesignPattern(publishInterface, subscribInterface, publishClasses, subscribClasses, messageClass).generateCode(outputPath);
            return 1;
        }
        else
        {
            logger.error("Issue with the provided input");
            return -1;

        }
    }


}

