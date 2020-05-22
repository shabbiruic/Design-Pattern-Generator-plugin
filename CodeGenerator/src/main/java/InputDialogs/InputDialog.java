package InputDialogs;

import DesignPatternDialogFlows.DesignPatternDialogFlow;
import com.intellij.openapi.ui.Messages;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@NoArgsConstructor
@Data
public abstract class InputDialog {

    //button for moving to previous input dialog
    protected JButton previousButton;

    //Button for navigating to next input dialog.
    protected JButton nextButton;

    //Stores the link to the next dialog
    protected InputDialog next;

    //Stores links to the previous dialog
    protected InputDialog previous;

    //Frame on which all the UI component will be placed
    protected JFrame frame;

    //stores the link to the design pattern which has evoked this dialog
    DesignPatternDialogFlow designPatternDialogFlow;

    public InputDialog(String title,DesignPatternDialogFlow designPatternDialogFlow) {

        this.designPatternDialogFlow=designPatternDialogFlow;
        frame = new JFrame(title);
        frame.setLayout(new BorderLayout());
        frame.setSize(300,400);
        frame.setPreferredSize(new Dimension(300,400));
        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }
        });
        Panel footer = new Panel(new FlowLayout());
        footer.setBounds(0,340,300,60);
        footer.setPreferredSize(new Dimension(300,60));
        footer.setBackground(Color.gray);
        previousButton = new JButton();
        previousButton.setText("Previous");
        previousButton.setBounds(10,10,100,40);
        previousButton.setPreferredSize(new Dimension(100,40));

        // when ever user clicks on previous button it makes the previous dialog visible and also removes the previously provided
        // inputs from the hashset
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                previous.removeFileInSet();
                previous.frame.setVisible(true);

            }
        });
        nextButton = new JButton();
        nextButton.setText("Next");
        nextButton.setBounds(190,10,100,40);
        nextButton.setPreferredSize(new Dimension(100,40));
        footer.add(previousButton);
        footer.add(nextButton);

        // adding footer at the button of the frame.
        frame.add(footer,BorderLayout.SOUTH);
    }

    // this adds the child panel on the top of the frame
    public void add(Component component)
    {
        frame.add(component,BorderLayout.NORTH);
    }

    //enables the changing of next button to generate button when all the required inputs are taken.
    public void setNext(InputDialog next) {
        if (next != null)
            this.next = next;
        else {
            nextButton.setText("Generate");
        }
    }

    // disables the previous button when input dialog is first in the sequence
    public void setPrevious(InputDialog previous) {
        if(previous==null)
            previousButton.setVisible(false);
        this.previous = previous;
    }

    // check user input validity and triggers the error message pop up if it's invalid.
    public boolean isValidInput(String input)
    {
        if(null==input)
        {
            return false;
        }
        else if(input.trim().length()==0)
        {
            Messages.showMessageDialog("Input can't be blank","Provide some AlphaNumeric character",Messages.getErrorIcon());
            return false;
        }
        else if(input.trim().contains(" "))
        {
            Messages.showMessageDialog(input + " is not valid it contains the space","Space Not Allowed",Messages.getErrorIcon());
            return false;
        }

        return true;
    }

    public void setVisible(boolean b)
    {
        frame.setVisible(b);
    }
    public InputDialog getPrevious()
    {
        return previous;
    }

    public InputDialog getNext()
    {
        return next;
    }

    public abstract void removeFileInSet();

    public JButton getPreviousButton() {
        return previousButton;
    }

    public void setPreviousButton(JButton previousButton) {
        this.previousButton = previousButton;
    }

    public JButton getNextButton() {
        return nextButton;
    }

    public void setNextButton(JButton nextButton) {
        this.nextButton = nextButton;
    }

}
