package DesignPatternDialogFlows;

import Generators.AbstractFactoryDesignPattern;
import Generators.InterfaceGenerator;
import InputDialogs.ArrayOfString;
import InputDialogs.FileSelector;
import Utilities.DesignPatternNames;
import Utilities.Helper;
import com.intellij.psi.PsiDirectory;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AbstractFactoryDesignPatternDialogFlow extends DesignPatternDialogFlow {

    // Different types of Dialogs according to needed design pattern
    private FileSelector factoryInterfaces;
    private FileSelector productInterfaces;
    private ArrayOfString productNames;

    public FileSelector getProductInterfaces() {
        return productInterfaces;
    }

    public ArrayOfString getProductNames() {
        return productNames;
    }

    private ArrayOfString prefixes;
    private static final Logger logger = LoggerFactory.getLogger(AbstractFactoryDesignPatternDialogFlow.class.getName());

    public AbstractFactoryDesignPatternDialogFlow(PsiDirectory currentDirectory) {
        super(currentDirectory);

        factoryInterfaces = new FileSelector(DesignPatternNames.abstractFactoryDesignPattern,"Factory Interface",this);
        productInterfaces = new FileSelector(DesignPatternNames.abstractFactoryDesignPattern, "Product Interface",this);

        productNames = new ArrayOfString(DesignPatternNames.abstractFactoryDesignPattern,"Product Names", this);

        prefixes = new ArrayOfString(DesignPatternNames.abstractFactoryDesignPattern,"Enter Prefixes",this);

        productInterfaces.setPrevious(null);
        productInterfaces.setNext(productNames);
        productNames.setPrevious(productInterfaces);
        productNames.setNext(factoryInterfaces);
        factoryInterfaces.setPrevious(productNames);
        factoryInterfaces.setNext(prefixes);
        prefixes.setPrevious(factoryInterfaces);
        prefixes.setNext(null);
        productInterfaces.setVisible(true);
        logger.debug("Dialog flow got initialized");
    }


    // it collects the user input from all the dialog boxes and pass it to the required generators.
    public int generateCode() {
        InterfaceGenerator prodInterface = Helper.readGeneratedInterface(productInterfaces.getResult().getFirst());
        InterfaceGenerator factInterface = Helper.readGeneratedInterface(factoryInterfaces.getResult().getFirst());
        prodInterface.setName(productInterfaces.getResult().getSecond());
        factInterface.setName(factoryInterfaces.getResult().getSecond());
        String[] prodNames = productNames.getResult();
        String[] pref = prefixes.getResult();
        if(prodInterface!=null && factInterface!=null && prodNames!=null&&pref!=null) {
            logger.debug("User inputs are taken successfully");
            new AbstractFactoryDesignPattern(prodInterface, factInterface, pref, prodNames).generateCode(outputPath);
            return 1;
        }
        else {
            logger.error("Issue with inputs during creation");
            return -1;
        }
    }

}

