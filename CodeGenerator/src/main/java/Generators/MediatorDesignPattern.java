package Generators;

import Utilities.Method;
import Utilities.Pair;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@NoArgsConstructor
public class MediatorDesignPattern extends DesignPatternGenerate {


    private ClassGenerator mediatorClass;

    private ClassGenerator[] colleagueClasses;
    public static final Logger logger = LoggerFactory.getLogger(MediatorDesignPattern.class.getName());

    public MediatorDesignPattern(String colleagueClassName[]) {
        mediatorClass = new ClassGenerator.ClassGeneratorBuilder(conf.getValue("mediatorDesignPattern"), false).build();
        createImplementation(colleagueClassName);
        logger.debug("constructor executed successfully");
    }

    // creating implementation of colleague classes
    // by creating methods and class variables
    public void createImplementation(String colleagueClassName[]) {
        colleagueClasses = new ClassGenerator[colleagueClassName.length];
        for (int i = 0; i < colleagueClassName.length; i++) {
            Pair pairs[] = {new Pair(conf.getValue("mediator"), mediatorClass.getName())};
            Method methods[] = {new Method.MethodBuilder(colleagueClassName[i], false, "").setConstructor(true).setParameter(pairs).build()};
            Pair clsVariables[] = {new Pair(conf.getValue("mediator"), mediatorClass.getName())};
            colleagueClasses[i] = new ClassGenerator.ClassGeneratorBuilder(colleagueClassName[i], false).setVariables(clsVariables).setMethods(methods).build();
        }
    }


    @Override
    public void generateCode(String outputFolderPath) {
        mediatorClass.generateCode(null, null, outputFolderPath);
        for (ClassGenerator cc : colleagueClasses)
            cc.generateCode(null, null, outputFolderPath);
        logger.debug("code got generated successfully");
    }

    public int generateCodeFromTemplate(String templatePath, String outputFolderPath) {
        logger.debug("generating Mediator design pattern via template");
        return generateCodeFromTemplate(templatePath, outputFolderPath, MediatorDesignPattern.class);
    }

    public ClassGenerator[] getColleagueClasses() {
        return colleagueClasses;
    }
}
