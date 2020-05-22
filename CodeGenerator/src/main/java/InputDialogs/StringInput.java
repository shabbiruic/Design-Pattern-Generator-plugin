package InputDialogs;

import DesignPatternDialogFlows.DesignPatternDialogFlow;
import com.intellij.ui.components.JBTextField;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@NoArgsConstructor
@Data
public class StringInput extends InputDialog {


    // field for taking user input of single string.
    JBTextField inputNameTF;

    //Assigns the label for user input.
    JLabel inputNameL;

    private static final Logger logger = LoggerFactory.getLogger(StringInput.class.getName());

    // In this creating a content panel with needed UI components and adding it to the main frame which is inherited from
    // the input dialog.
    public StringInput(String title, String inputLabel, DesignPatternDialogFlow designPatternDialogFlow) {
        super(title,designPatternDialogFlow);

        Panel containPanel = new Panel(new FlowLayout());
        containPanel.setBounds(10,20,280,300);
        containPanel.setPreferredSize(new Dimension(280,300));
        containPanel.setBackground(Color.gray);
        add(containPanel);

        inputNameL = new JLabel(inputLabel);
        inputNameL.setBounds(0,130,100,40);
        inputNameL.setPreferredSize(new Dimension(100,40));
        containPanel.add(inputNameL);

        inputNameTF = new JBTextField();
        inputNameTF.setBounds(100,130,180,40);
        inputNameTF.setPreferredSize(new Dimension(180,40));
        containPanel.add(inputNameTF);

        // next button triggering the generation of other dialog flow only when all the provided inputs are correct.
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if( isValidInput(inputNameTF.getText())&& designPatternDialogFlow.isFileNameValid(getResult())){
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
        logger.info("input dialog box is created successfully");
    }

    // this remove the user input from the hashset when user clicks on the previous button.
    @Override
    public void removeFileInSet() {
        designPatternDialogFlow.removeFileFromSet(getResult());
    }

    // method is used by design pattern dialog flow to get the user inputs.
    public String getResult()
    {
        if(inputNameTF!=null &&inputNameTF.getText().length()>0 ) {
            logger.info("result is fetch successfully");
            return inputNameTF.getText();
        }
        else {
            logger.warn("Issue with the inputs");
            return null;
        }
    }

    public void setInputNameText(String inputName) {
        inputNameTF.setText(inputName);
    }
}
