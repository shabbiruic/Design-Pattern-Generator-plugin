package DesignPatternDialogFlows;

import Generators.AdapterDesignPattern;
import Generators.InterfaceGenerator;
import InputDialogs.*;
import Utilities.DesignPatternNames;
import Utilities.Helper;
import com.intellij.psi.PsiDirectory;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AdaptorDesignPatternDialogFlow  extends DesignPatternDialogFlow{

    // Different types of Dialogs according to needed design pattern

    private FileSelector interfaceSelector1;
    private FileSelector interfaceSelector2;
    private ArrayOfString concreteClasses1;
    private ArrayOfString concreteClasses2;
    private static final Logger logger = LoggerFactory.getLogger(AdaptorDesignPatternDialogFlow.class.getName());

    public AdaptorDesignPatternDialogFlow(PsiDirectory currentDirectory) {

        super(currentDirectory);
        //initialized the flow of dialog boxes
//        output = new DirectorySelector("Select output Directory Location", this);
        interfaceSelector1 = new FileSelector(DesignPatternNames.adapterDesignPattern,"First Element Interface" ,this);
        interfaceSelector2 = new FileSelector(DesignPatternNames.adapterDesignPattern,"Second Element Interface", this);
        concreteClasses1 = new ArrayOfString(DesignPatternNames.adapterDesignPattern,"First Element Classes" ,this);
        concreteClasses2 = new ArrayOfString(DesignPatternNames.adapterDesignPattern, "Second Element Classes",this);

//        output.setNext(interfaceSelector1);
        interfaceSelector1.setPrevious(null);
        interfaceSelector1.setNext(concreteClasses1);
        concreteClasses1.setPrevious(interfaceSelector1);
        concreteClasses1.setNext(interfaceSelector2);
        interfaceSelector2.setPrevious(concreteClasses1);
        interfaceSelector2.setNext(concreteClasses2);
        concreteClasses2.setPrevious(interfaceSelector2);
        concreteClasses2.setNext(null);
        logger.debug("Dialog flow got initialized");
        interfaceSelector1.setVisible(true);
    }


    // it collects the user input from all the dialog boxes and pass it to the required generators.
    public int generateCode() {
        InterfaceGenerator ig1 = Helper.readGeneratedInterface(interfaceSelector1.getResult().getFirst());
        ig1.setName(interfaceSelector1.getResult().getSecond());

        InterfaceGenerator ig2 = Helper.readGeneratedInterface(interfaceSelector2.getResult().getSecond());
        ig2.setName(interfaceSelector2.getResult().getSecond());

        String[] concreteClass1 = concreteClasses1.getResult();
        String[] concreteClass2 = concreteClasses2.getResult();
        if(ig1!=null&&ig2!=null&&concreteClass1!=null&&concreteClass2!=null) {

            new AdapterDesignPattern(ig1, ig2, concreteClass1, concreteClass2).generateCode(outputPath);
            logger.debug("User inputs are taken successfully");
            return 1;
        }
        else {
            logger.error("Issue in creation of design pattern due to wrong inputs");
            return -1;
        }
        }

}

