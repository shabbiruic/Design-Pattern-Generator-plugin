package DesignPatternDialogFlows;

import Generators.InterfaceGenerator;
import Generators.ProxyDesignPattern;
import InputDialogs.FileSelector;
import InputDialogs.StringInput;
import Utilities.DesignPatternNames;
import Utilities.Helper;
import com.intellij.psi.PsiDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProxyDesignPatternDialogFlow  extends DesignPatternDialogFlow {

    private FileSelector proxyInterfaceSelector;
    private StringInput concreteClassName;
    private static final Logger logger = LoggerFactory.getLogger(ProxyDesignPatternDialogFlow.class.getName());

    //initializes the flow of dialog boxes
    public ProxyDesignPatternDialogFlow(PsiDirectory currentDirectory) {

        super(currentDirectory);
        proxyInterfaceSelector = new FileSelector(DesignPatternNames.proxyDesignPattern,"Proxy Interface", this);
        concreteClassName = new StringInput( DesignPatternNames.proxyDesignPattern,"Concrete Classes",this);
        proxyInterfaceSelector.setPrevious(null);
        proxyInterfaceSelector.setNext(concreteClassName);
        concreteClassName.setPrevious(proxyInterfaceSelector);
        concreteClassName.setNext(null);
        logger.debug("Dialog flow got initialized");
        proxyInterfaceSelector.setVisible(true);
    }



    // it collects the user input from all the dialog boxes and pass it to the required generators.
    @Override
    public int generateCode() {
        InterfaceGenerator proxyInterface = Helper.readGeneratedInterface(proxyInterfaceSelector.getResult());
        String concreteClass = concreteClassName.getResult();
        if(proxyInterface!=null&&concreteClass!=null&&outputPath!=null) {
            logger.debug("User inputs are taken successfully");
            new ProxyDesignPattern(proxyInterface, concreteClass).generateCode(outputPath);
            return 1;
        }
        else{
            logger.error("Issue with inputs");
            return -1;
        }

    }

}

