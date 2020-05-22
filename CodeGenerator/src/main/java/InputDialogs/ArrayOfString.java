package InputDialogs;

import DesignPatternDialogFlows.DesignPatternDialogFlow;
import com.intellij.openapi.ui.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArrayOfString extends InputDialog {

    private JTextArea inputNamesTA;
    private JLabel inputL;

    private final static Logger logger = LoggerFactory.getLogger(ArrayOfString.class.getName());
    // In this creating a content panel with needed UI components and adding it to the main frame which is inherited from
    // the input dialog.
    public ArrayOfString(String title, String inputLabelName, DesignPatternDialogFlow designPatternDialogFlow) {
        super(title,designPatternDialogFlow);
        Panel contentPanel = new Panel(new FlowLayout());
        contentPanel.setBounds(10,20,280,300);
        contentPanel.setPreferredSize(new Dimension(280,300));
        contentPanel.setBackground(Color.gray);
        add(contentPanel);

        inputL = new JLabel();
        inputL.setBounds(0,130,100,40);
        inputL.setPreferredSize(new Dimension(100,40));
        inputL.setText(inputLabelName);
        contentPanel.add(inputL);

        inputNamesTA = new JTextArea();
        inputNamesTA.setBounds(100,25,180,250);
        inputNamesTA.setPreferredSize(new Dimension(180,250));
        contentPanel.add(inputNamesTA);


        // next button triggering the generation of other dialog flow only when all the provided inputs are correct.
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isValidInput(inputNamesTA.getText())&& designPatternDialogFlow.isFileNameValid(getResult())) {
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
        logger.info("ArrayOfString dialog nox has been constructed successfully");
    }

    // method is used by design pattern dialog flow to get the user inputs.
    public String[] getResult() {
        if(inputNamesTA.getText().trim().length()>0) {
            logger.info("input data is fetched successfully");
            return inputNamesTA.getText().split(",");
        }
        else {
            logger.warn("No input is given");
            return null;
        }
    }

    //removes the user input from the hashSet when user clicks on the previous button.
    @Override
    public void removeFileInSet() {
        designPatternDialogFlow.removeFileFromSet(getResult());

    }

    //method which check provided user inputs are valid or not. if its invalid it will create the
    // message dialog.
    public boolean isValidInput(String input)
    {
        if(null == input)
        {
            return false;
        }
        if(input.trim().length()==0)
        {
            Messages.showMessageDialog("Input can't be blank","Provide some AlphaNumeric character",Messages.getErrorIcon());
            return false;
        }
        String inputs[] = input.split(",");
        for(String name: inputs)
        {
            if(super.isValidInput(name))
                continue;
            else
                return false;
        }
        logger.info("input is validated");
        return true;
    }

    public void setInputNamesTA(String names) {
        inputNamesTA.setText(names);
    }
}
