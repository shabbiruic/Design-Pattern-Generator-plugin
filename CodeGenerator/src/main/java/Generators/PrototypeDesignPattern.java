package Generators;

import Utilities.BuildingBlocks;
import Utilities.Helper;
import Utilities.Method;
import Utilities.Pair;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@NoArgsConstructor
public class PrototypeDesignPattern extends DesignPatternGenerate {

    private ClassGenerator abstractClass;
    private ClassGenerator concreteClass[];
    private ClassGenerator implementationClass;
    public static final Logger logger = LoggerFactory.getLogger(PrototypeDesignPattern.class.getName());

    public PrototypeDesignPattern(ClassGenerator abstractClass, String concreteClassNames[]) {
        this.abstractClass = abstractClass;
        concreteClass = BuildingBlocks.createConcreteClass(concreteClassNames);
        createImplementation();
        logger.info("constructor get executed successfully");
    }

    public void createImplementation()
    {
        Pair pairs[] = new Pair[]{new Pair(abstractClass.getName()+"Map","Map<String,"+ abstractClass.getName()+">")};

        implementationClass = new ClassGenerator.ClassGeneratorBuilder(conf.getValue("prototypeDesignPattern"),false).setVariables(pairs).setMethods(new Method[]{new Method.MethodBuilder(conf.getValue("java.get")+abstractClass.getName(),false,abstractClass.getName()).setParameter(new Pair[]{ new Pair(abstractClass.getName()+conf.getValue("java.name"),"String")}).build()}).build();

        logger.info("create Implementation completed successfully");
    }

    @Override
    public void generateCode(String outputFolderPath) {

        abstractClass.generateCode(null, Helper.convertInterfaceObjectToArray(new InterfaceGenerator.InterfaceBuilder("Cloneable",null).build()),outputFolderPath);
        for(ClassGenerator cc:concreteClass)
            cc.generateCode(abstractClass,null,outputFolderPath);
        implementationClass.generateCode(null,null,outputFolderPath);
        logger.info("codes get generated successfully");
    }
    public int generateCodeFromTemplate(String templatePath, String outputFolderPath) {
        logger.debug("generating code via template for Prototype design pattern");
        return generateCodeFromTemplate(templatePath,outputFolderPath, PrototypeDesignPattern.class);
    }
}
