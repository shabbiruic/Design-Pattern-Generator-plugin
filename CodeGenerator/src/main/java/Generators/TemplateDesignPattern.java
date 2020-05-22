package Generators;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@NoArgsConstructor
public class TemplateDesignPattern extends DesignPatternGenerate {

    private ClassGenerator template;
    private ClassGenerator[] concreteClasses;
    public static final Logger logger = LoggerFactory.getLogger(TemplateDesignPattern.class.getName());
    public TemplateDesignPattern(ClassGenerator template, String[] concreteClassName)
    {
        this.template = template;
        concreteClasses = new ClassGenerator[concreteClassName.length];
        for(int i=0;i<concreteClassName.length;i++)
            concreteClasses[i]= new ClassGenerator.ClassGeneratorBuilder(concreteClassName[i],false).build();
        logger.info("constructor got executed successfully");
    }

    @Override
    public void generateCode(String outputFolderPath) {
        template.generateCode(null,null,outputFolderPath);
        for(ClassGenerator cc:concreteClasses)
            cc.generateCode(template,null,outputFolderPath);
        logger.debug("code got generated successfully");

    }
    public int generateCodeFromTemplate(String templatePath, String outputFolderPath) {
        logger.debug("generating Template design pattern via template approach");
        return generateCodeFromTemplate(templatePath,outputFolderPath, TemplateDesignPattern.class);
    }
}
