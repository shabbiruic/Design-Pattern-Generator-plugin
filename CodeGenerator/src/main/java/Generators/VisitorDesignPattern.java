package Generators;

import Utilities.BuildingBlocks;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@NoArgsConstructor
public class VisitorDesignPattern extends DesignPatternGenerate {
    private InterfaceGenerator visitorInterface;
    private InterfaceGenerator elementInterface;
    private ClassGenerator[] concreteVisitors;
    private ClassGenerator[] concreteElements;
    public static  final Logger logger = LoggerFactory.getLogger(VisitorDesignPattern.class.getName());

    public VisitorDesignPattern(InterfaceGenerator visitorInterface, InterfaceGenerator elementInterface, String[] visitorClassNames, String[] elementClassName) {
        this.visitorInterface = visitorInterface;
        this.elementInterface = elementInterface;
        this.concreteVisitors = BuildingBlocks.createConcreteClass(visitorClassNames);
        this.concreteElements = BuildingBlocks.createConcreteClass(elementClassName);
        logger.info("constructor got executed successfully");
    }
    @Override
    public void generateCode(String outputFolderPath) {
        visitorInterface.generateCode(outputFolderPath);
        elementInterface.generateCode(outputFolderPath);
        for(ClassGenerator ce:concreteElements)
            ce.generateCode(null,new InterfaceGenerator[]{elementInterface},outputFolderPath);
        for(ClassGenerator cv:concreteVisitors)
            cv.generateCode(null,new InterfaceGenerator[]{visitorInterface},outputFolderPath);
        logger.debug("code got generated successfully");
    }

    public int generateCodeFromTemplate(String templatePath, String outputFolderPath) {
        logger.debug("generating Visitor Design Pattern  via template approach");
        return generateCodeFromTemplate(templatePath,outputFolderPath, TemplateDesignPattern.class);
    }
}

