package InputDialogs;

import DesignPatternDialogFlows.DesignPatternDialogFlow;
import Utilities.Configuration;
import com.intellij.openapi.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileSelector extends InputDialog {
    private JTextField selectedFileTF;
    private JButton browseFileB;

    private static final Logger logger = LoggerFactory.getLogger(FileSelector.class.getName());

    public void setEntityNameTF(String entityName) {
        entityNameTF.setText(entityName);
    }

    private JTextField entityNameTF;
    private JLabel entityNameL;
    private JFileChooser fileChooser;

    // creating a content panel with needed UI components and adding it to the main frame which is inherited from
    // the input dialog.
    public FileSelector(String title, String inputEntityLabel, DesignPatternDialogFlow designPatternDialogFlow) {
        super(title,designPatternDialogFlow);
        Panel containPanel = new Panel(new FlowLayout());
        containPanel.setBounds(10,20,280,300);
        containPanel.setPreferredSize(new Dimension(280,300));
        containPanel.setBackground(Color.GRAY);
        add(containPanel);

        entityNameL = new JLabel(inputEntityLabel);
        entityNameL.setBounds(0,55,100,40);
        entityNameL.setPreferredSize(new Dimension(100,40));
        containPanel.add(entityNameL);

        entityNameTF = new JTextField();
        entityNameTF.setPreferredSize(new Dimension(100,40));
        entityNameTF.setBounds(100,55,100,40);
        containPanel.add(entityNameTF);

        selectedFileTF = new JTextField();
        selectedFileTF.setEditable(false);
        selectedFileTF.setBounds(0,205,130,40);
        selectedFileTF.setPreferredSize(new Dimension(130,40));
        containPanel.add(selectedFileTF);

        browseFileB = new JButton("Select the file");
        browseFileB.setBounds(130,205,50,40);
        browseFileB.setPreferredSize(new Dimension(50,40));
        containPanel.add(browseFileB);

        // opens the selection panel pointing to the resource folder whose location is specified in configuration file
        browseFileB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(Configuration.getInstance().getValue("java.pathForGeneratedObject")));
                int result = fileChooser.showOpenDialog(containPanel);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFileTF.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });

        // next button triggering the generation of other dialog flow only when all the provided inputs are correct.
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = entityNameTF.getText().trim();
                if(selectedFileTF.getText().trim().length()>0 && fileName.length()>0 && designPatternDialogFlow.isFileNameValid(getResult().getSecond()))
                {
                    setVisible(false);
                    if (next != null)
                        next.setVisible(true);
                    else {
                        designPatternDialogFlow.generateCode();
                    }


                }
            }
        });
//        frame.getContentPane().setBackground(Color.gray);
        frame.pack();
        logger.info("input dialog box has been created successfully");
    }
    // method is used by design pattern dialog flow to get the user inputs.
    public Pair<File,String> getResult()
    {
        if(selectedFileTF.getText().trim().length()>0 && isValidInput(entityNameTF.getText())) {
            logger.info("result has been fetched successfully");
            return new Pair<File, String>(fileChooser.getSelectedFile(), entityNameTF.getText().trim());
        }
        else {
            logger.warn("input is not provided properly");
            return null;
        }
    }


    //removes the user input from the hashSet when user clicks on the previous button.
    @Override
    public void removeFileInSet() {
        designPatternDialogFlow.removeFileFromSet(getResult().getSecond());

    }

}
