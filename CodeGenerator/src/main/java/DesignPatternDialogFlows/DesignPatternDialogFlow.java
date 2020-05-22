package DesignPatternDialogFlows;


import Utilities.Configuration;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public abstract class DesignPatternDialogFlow {

    // it stores the detail about the current directory
    public PsiDirectory currentDirectory;

    // points to the path where generated files will be placed.
    public String outputPath;
    public Configuration conf = Configuration.getInstance();


    //Stores the names which user has provided for creation.
    public HashSet<String> filesToCreate = new HashSet<>();

    private static final Logger logger = LoggerFactory.getLogger(DesignPatternDialogFlow.class.getName());

    public DesignPatternDialogFlow(PsiDirectory currentDirectory) {
        this.currentDirectory=currentDirectory;
        outputPath = currentDirectory.getVirtualFile().getPath();
    }

    //this removes the previous given input names from hash map when user clicks on previous button.
    public void removeFileFromSet(String[] filesName)
    {
        for(String fileName:filesName)
        {
            removeFileFromSet(fileName);
        }
    }
    public void removeFileFromSet(String fileName)
    {
        filesToCreate.remove(fileName);
        logger.info("file name is remove successfully");
    }

    //check whether array of names are valid or not depending on whether they create the name clash or not.
    public boolean isFileNameValid(String[] filesName)
    {
        List<String> filesNamelst = Arrays.asList(filesName);
        Set<String> fileNameSet = new HashSet(filesNamelst);
        if(filesNamelst.size()>fileNameSet.size()) {
            Messages.showMessageDialog("Duplicate File Name in input","NAME CLASH",Messages.getErrorIcon());
            logger.warn("Name clash has occurred");
            return false;
        }
        for(String fileName:filesName)
        {
            if(filesToCreate.contains(fileName) || currentDirectory.findFile(fileName+conf.getValue("java.fileExtension"))!=null) {
                Messages.showMessageDialog(fileName + " already present in target directory","NAME CLASH",Messages.getErrorIcon());
                logger.warn("Name clash has occurred");
                return false;
            }
        }
        filesToCreate.addAll(fileNameSet);
        logger.info("Name clash check has been done");
        return true;
    }

    //checks whether the provided names will create name clash or not.
    public boolean isFileNameValid(String fileName)
    {
        if (currentDirectory.findFile(fileName+conf.getValue("java.fileExtension"))!=null || filesToCreate.contains(fileName) ) {
            Messages.showMessageDialog(fileName + " already present in target directory","NAME CLASH",Messages.getErrorIcon());
            logger.info("Name clash has occurred");
            return false;
        }
            filesToCreate.add(fileName);
            return true;

    }

    // implemented by every design pattern dialog flow depending on kind of design pattern.
    public abstract int generateCode();
}
