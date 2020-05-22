package Generators;

import Exceptions.MandatoryFieldMissingException;
import Utilities.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Data
public class ClassGenerator implements Generatable {
    //name of class
    private String name;
    //member fields of class
    private Pair variables[];
    private boolean isAbstract;
    // all the information about the class methods which are defined in this class itself.
    private Method methods[];
    // if specified as true during generation builder class will also get genrated.
    private boolean isBuilderNeeded;
    public static final Logger logger = LoggerFactory.getLogger(ClassGenerator.class.getName());


    private ClassGenerator(ClassGeneratorBuilder builder) {
        this.name = builder.name;
        this.isAbstract = builder.isAbstract;
        this.methods = builder.methods;
        this.isBuilderNeeded = builder.isBuilderNeeded;
        this.variables = builder.variables;
    }

    public Pair[] getVariables() {
        return variables;
    }

    public void setVariables(Pair[] variables) {
        this.variables = variables;
    }

    @NoArgsConstructor
    @Data
    // inner builder class for class generation as so that we can iteratively create the class generator object.
    public static class ClassGeneratorBuilder {
        private String name;
        private Pair variables[];
        private boolean isAbstract;
        private Method methods[];
        private boolean isBuilderNeeded = false;

        public ClassGeneratorBuilder(String name, boolean isAbstract) {
            this.name = name;
            this.isAbstract = isAbstract;
        }


        public ClassGeneratorBuilder setIsBuilderNeeded(boolean isBuilderNeeded) {
            this.isBuilderNeeded = isBuilderNeeded;
            return this;
        }

        public ClassGeneratorBuilder setMethods(Method methods[]) {
            this.methods = methods;
            return this;
        }

        public ClassGeneratorBuilder setVariables(Pair variables[]) {
            this.variables = variables;
            return this;
        }

        public ClassGenerator build() {
            return new ClassGenerator(this);
        }
    }

    public Method[] getMethods() {
        return methods;
    }

    public void setMethods(Method[] methods) {
        this.methods = methods;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean anAbstract) {
        isAbstract = anAbstract;
    }


    public ClassGenerator(String className, boolean isAbstract) {
        this.name = className;
        this.isAbstract = isAbstract;
    }


    // this method generates the code and writes it to specific class to which printWriter points
    // If also takes the parent class and interface into account so as to implement their abstract methods only if
    // they are not defined in this class.
    public void generateCodeCore(PrintWriter pw, ClassGenerator parentClazz, InterfaceGenerator[] parentInterfaces) {
        logger.debug("code generation for {} class started", getName());
        pw.print(conf.getValue("java.public") + " ");

        if (isAbstract)
            pw.print(conf.getValue("java.abstract") + " ");
        pw.print(conf.getValue("java.clazz") + " ");

        pw.print(name + " ");
        if (parentClazz != null) {
            pw.print(conf.getValue("java.extends") + " ");
            pw.print(parentClazz.getName() + " ");
        }
        if (parentInterfaces != null && parentInterfaces.length > 0) {
            pw.print(conf.getValue("java.implements") + " ");

            for (int i = 0; i < parentInterfaces.length - 1; i++)
                pw.print(parentInterfaces[i].getName() + ", ");

            pw.print(parentInterfaces[parentInterfaces.length - 1].getName());
        }
        pw.println("{");

        if (isBuilderNeeded) {
            String builderClassName = this.getName() + Configuration
                    .getInstance().getValue("java.builder");

            Method builderMethods[] = new Method[variables.length + 1];

            for (int i = 0; i < variables.length; i++) {
                Pair[] variable = {variables[i]};
                builderMethods[i] = new Method(conf.getValue("java.set") + variables[i].getVariableName(), builderClassName, variable, false);
            }
            builderMethods[variables.length] = new Method(conf.getValue("java.build"), this.getName(), null, false);
            new ClassGenerator.ClassGeneratorBuilder(builderClassName, false).setVariables(this.getVariables()).setMethods(builderMethods).setIsBuilderNeeded(false).build().generateCodeCore(pw, null, null);
        }
        BuildingBlocks.createMemberFields(variables, pw);
        BuildingBlocks.createMethods(methods, pw, true);
        Set<Method> methodSet = new HashSet<Method>();
        if (methods != null) {
            for (Method m : methods)
                methodSet.add(m);
        }
        if (parentInterfaces != null)
            for (InterfaceGenerator pi : parentInterfaces)
                BuildingBlocks.createMethods(Method.createConcreteMethod(pi.getMethods(), methodSet), pw, false);

        if (parentClazz != null) {
            if (parentClazz.methods != null) {
                for (Method m : parentClazz.methods) {
                    if (m.isAbstract()) {
                        BuildingBlocks.createMethod(Method.createConcreteMethod(m, methodSet), pw, true);
                    }
                }
            }
        }
        pw.println("}");
        logger.debug("Data Successfully appended into the file {}", getName());
    }

    //Wrapper method of code generation which return 1 if exceuted successfully.
    public int generateCode(ClassGenerator parentClazz, InterfaceGenerator[] parentInterfaces, String outputFolderLocation) {

        try {
            if (outputFolderLocation == null || outputFolderLocation.length() == 0) {
                throw new MandatoryFieldMissingException("outputFolderLocation");
            }
        } catch (MandatoryFieldMissingException e) {
            logger.error("output folder location was not provided {}", e.getMessage());
            return -1;
        }

        FileCreatorAndWriter fcw = new FileCreatorAndWriter(name, outputFolderLocation);
        PrintWriter pw = fcw.getPrintWriter();
        generateCodeCore(pw, parentClazz, parentInterfaces);
        fcw.close();
        logger.info("{}.java file got created successfully",getName());
        return 1;
    }
}




