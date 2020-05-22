import DesignPatternDialogFlows.*;
import Utilities.DesignPatternNames;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.util.PsiUtilBase;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TriggerGeneration extends AnAction {

    private static final Logger logger = LoggerFactory.getLogger(TriggerGeneration.class.getName());

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        String DesignPatternSelected = event.getPresentation().getText();
        Project project = event.getProject();
        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        try {
            PsiDirectory currentDirectory = PsiUtilBase.getPsiFileInEditor(editor, project).getParent();
        switch(DesignPatternSelected)
        {
            case DesignPatternNames.abstractFactoryDesignPattern:
                new AbstractFactoryDesignPatternDialogFlow(currentDirectory);
                break;

            case DesignPatternNames.adapterDesignPattern:
                new AdaptorDesignPatternDialogFlow(currentDirectory);
                break;

            case DesignPatternNames.bridgeDesignPattern:
                new BridgeDesignPatternDialogFlow(currentDirectory);
                break;

            case DesignPatternNames.builderDesignPattern:
                new BuilderDesignPatternDialogFlow(currentDirectory);
                break;

            case DesignPatternNames.chainOfResponsibilityDesignPattern:
                new ChainOfResponsibilityDesignPatternDialogFlow(currentDirectory);
                break;

            case DesignPatternNames.commandDesignPattern:
                new CommandDesignPatternDialogFlow(currentDirectory);
                break;
            case DesignPatternNames.compositeDesignPattern:
                new CompositeDesignPatternDialogFlow(currentDirectory);
                break;
            case DesignPatternNames.decoratorDesignPattern:
                new DecoratorDesignPatternDialogFlow(currentDirectory);
                break;
            case DesignPatternNames.facadeDesignPattern:
                new FacadeDesignPatternDialogFlow(currentDirectory);
                break;
            case DesignPatternNames.factoryDesignPattern:
                new FactoryDesignPatternDialogFlow(currentDirectory);
                break;
            case DesignPatternNames.flyweightDesignPattern:
                new FlyweightDesignPatternDialogFlow(currentDirectory);
                break;
            case DesignPatternNames.interpreterDesignPattern:
                new InterpreterDesignPatternDialogFlow(currentDirectory);
                break;
            case DesignPatternNames.iteratorDesignPattern:
                new IteratorDesignPatternDialogFlow(currentDirectory);
                break;
            case DesignPatternNames.mediatorDesignPattern:
                new MediatorDesignPatternDialogFlow(currentDirectory);
                break;
            case DesignPatternNames.mementoDesignPatter:
                new MementoDesignPatternDialogFlow(currentDirectory);
                break;
            case DesignPatternNames.observerDesignPattern:
                new ObserverDesignPatternDialogFlow(currentDirectory);
                break;
            case DesignPatternNames.prototypeDesignPattern:
                new PrototypeDesignPatternDialogFlow(currentDirectory);
                break;
            case DesignPatternNames.proxyDesignPattern:
                new ProxyDesignPatternDialogFlow(currentDirectory);
                break;
            case DesignPatternNames.stateDesignPattern:
                new StateDesignPatternDialogFlow(currentDirectory);
                break;
            case DesignPatternNames.strategyDesignPattern:
                new StrategyDesignPatternDialogFlow(currentDirectory);
                break;
            case DesignPatternNames.templateDesignPattern:
                new TemplateDesignPatternDialogFlow(currentDirectory);
                break;
            case DesignPatternNames.visitorDesignPattern:
                new VisitorDesignPatternDialogFlow(currentDirectory);
                break;
        }

        }
        catch(Exception e)
        {
            logger.error("Issue No active file in editor");
        }
    }

    @Override
    public void update(AnActionEvent e)
    {
        Project project = e.getProject();
        String sdkType = ProjectRootManager.getInstance(project).getProjectSdk().getSdkType().toString();
        e.getPresentation().setEnabledAndVisible(project != null && sdkType.equals("JavaSDK") );
    }

}
