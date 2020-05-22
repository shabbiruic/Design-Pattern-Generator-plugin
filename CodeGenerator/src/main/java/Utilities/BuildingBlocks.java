package Utilities;

import Generators.ClassGenerator;
import Generators.Generatable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;

public class BuildingBlocks implements Generatable {

    public static final Logger logger = LoggerFactory.getLogger(BuildingBlocks.class.getName());

    public static void createMemberFields(Pair[] pairs, PrintWriter pw) {
        if (pairs != null && pairs.length > 0)
            for (Pair pair : pairs)

                pw.println(pair.getAccessModifier() + " " + (pair.isFinal() ? "final " : "") + pair.getDataType() + " " + pair.getVariableName() + ";");
        logger.info("member fields get created successfully");
    }

    public static void createMethods(Method[] methods, PrintWriter pw, boolean ofclass) {
        if (methods == null || methods.length == 0)
            return;

        for (Method method : methods) {
            createMethod(method, pw, ofclass);
        }
        logger.info("methods got created successfully");
    }

    public static void createMethod(Method method, PrintWriter pw, boolean ofclass) {
        if (method == null)
        {
            logger.warn("No methods to create");
            return;
        }

        pw.print(conf.getValue("java.public") + " ");
        if (ofclass && method.isAbstract)
            pw.print(conf.getValue("java.abstract") + " ");
        pw.print(" " + method.returnType + " " + method.name + " (");
        if (method.parameter != null) {
            for (int i = 0; i < method.parameter.length - 1; i++) {
                pw.print(" " + method.parameter[i].getDataType() + " " + method.parameter[i].getVariableName() + ", ");
            }
            pw.print(" " + method.parameter[method.parameter.length - 1].getDataType() + " " + method.parameter[method.parameter.length - 1].getVariableName());
        }
        pw.print(" )");
        if (method.isAbstract) {
            pw.print("; \n");
        } else {
            pw.println("{ ");
            if (method.implementation != null) {
                for (String ss : method.implementation)
                    pw.println(ss);
            }
            if (!method.returnType.equals(conf.getValue("java.void")) && method.implementation == null && !method.isConstructor)
                pw.println("return null ;");

            pw.println("}");
        }
        logger.info("method {} got created successfully", method.getName());
    }

    public static ClassGenerator createConcreteClass(String className) {
        return new ClassGenerator.ClassGeneratorBuilder(className, false).build();
    }

    public static ClassGenerator[] createConcreteClass(String[] classNames) {
        ClassGenerator concreteClasses[] = new ClassGenerator[classNames.length];
        for (int i = 0; i < classNames.length; i++) {
            concreteClasses[i] = createConcreteClass(classNames[i]);
        }
        logger.debug("Array of concrete classes got created");
        return concreteClasses;


    }

    public static Method createDefaultConstructor(String ClassName) {
        return new Method.MethodBuilder(ClassName, false, "").setConstructor(true).build();
    }

    public static Pair[] getFinalVariables(Pair[] pairs) {
        Pair newPairs[] = new Pair[pairs.length];
        for (int i = 0; i < pairs.length; i++) {
            Pair temp = pairs[i].clonePair();
            temp.setFinal(true);
            newPairs[i] = temp;
        }
        logger.info("final variables got created successfully");
        return newPairs;
    }

}
