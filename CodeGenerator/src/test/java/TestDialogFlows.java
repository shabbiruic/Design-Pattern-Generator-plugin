import DesignPatternDialogFlows.*;
import InputDialogs.*;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiDirectory;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.swing.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TestDialogFlows {



    @Test
    public void checkNameClashWhenFileAlreadyExits()
    {
        DesignPatternDialogFlow designPatternDialogFlow = mock(AdaptorDesignPatternDialogFlow.class);
        when(designPatternDialogFlow.isFileNameValid(anyString())).thenReturn(false);
        StringInput stringInput = new StringInput("check","input",designPatternDialogFlow);
        stringInput.setInputNameText("dummy");
        stringInput.getNextButton().doClick();
        verify(designPatternDialogFlow).isFileNameValid(anyString());
    }

    @Test
    public void IsDuplicateChecked()
    {
        DesignPatternDialogFlow designPatternDialogFlow = mock(CommandDesignPatternDialogFlow.class);
        when(designPatternDialogFlow.isFileNameValid((String[]) anyObject())).thenReturn(false);
        ArrayOfString arrayOfString = new ArrayOfString("test","Array of String",designPatternDialogFlow);
        arrayOfString.setInputNamesTA("dummy,dummy");
        arrayOfString.getNextButton().doClick();
        verify(designPatternDialogFlow).isFileNameValid((String[]) anyObject());

    }

    @Test
    public void checkNameAdditionInHashSet()
    {
        PsiDirectory psiDirectory = mock(PsiDirectory.class);
        AbstractFactoryDesignPatternDialogFlow afdpdf = new AbstractFactoryDesignPatternDialogFlow(psiDirectory);
        FileSelector fs = afdpdf.getProductInterfaces();
        fs.setEntityNameTF("Dummy");
        Assert.assertTrue(afdpdf.filesToCreate.contains("Dummy"));
    }

    @Test
    public void checkNameDeletionInHashSet()
    {
        PsiDirectory psiDirectory = mock(PsiDirectory.class);
        StrategyDesignPatternDialogFlow sdpdf = new StrategyDesignPatternDialogFlow(psiDirectory);
        ArrayOfString aos = sdpdf.getConcreteStrategyNames();
        aos.setInputNamesTA("name1,name2");
        StringInput si = sdpdf.getContextClassName();
        si.getPreviousButton().doClick();
        Assert.assertFalse(sdpdf.filesToCreate.contains("name1"));
        Assert.assertFalse(sdpdf.filesToCreate.contains("name2"));

    }

    @Test
    public void fileSelectorWithoutEntityName()
    {
        DesignPatternDialogFlow designPatternDialogFlow = mock(AdaptorDesignPatternDialogFlow.class);
        FileSelector fs = new FileSelector("dummy","entityDummy",designPatternDialogFlow);
        Assert.assertNull(fs.getResult());

    }



}
