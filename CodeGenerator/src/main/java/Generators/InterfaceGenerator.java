package Generators;

import Utilities.BuildingBlocks;
import Utilities.FileCreatorAndWriter;
import Utilities.Method;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;

@NoArgsConstructor
@Data
// main class which generates all the interfaces
public class InterfaceGenerator implements Generatable {

    private String name;
    private Method methods[];
    //stores details about all the parent interfaces this inherits
    private String parents[];
    public static final Logger logger = LoggerFactory.getLogger(InterfaceGenerator.class.getName());

    public InterfaceGenerator(String name, Method[] methods, String parents[]) {
        this.name = name;
        this.methods = methods;
        this.parents = parents;
    }

    public InterfaceGenerator(InterfaceBuilder ib) {
        this.name = ib.name;
        this.methods = ib.methods;
        this.parents = ib.parents;
        logger.debug("interface object got created successfully");
    }

    @Data
    @NoArgsConstructor
    //builder class to make its creation easy
    public static class InterfaceBuilder {
        private String name;
        private Method methods[];
        private String parents[];


        public InterfaceBuilder(String name, String[] parents) {
            this.name = name;
            this.parents = parents;
        }


        public InterfaceBuilder setMethods(Method[] methods) {
            this.methods = methods;
            return this;
        }


        public InterfaceGenerator build() {
            return new InterfaceGenerator(this);
        }
    }


    // generates actual interface file to specified location
    public void generateCode(String outputFolderPath) {

        FileCreatorAndWriter fcw = new FileCreatorAndWriter(name, outputFolderPath);
        PrintWriter pw = fcw.getPrintWriter();

        logger.debug("code generation for {} interface started",getName());

        pw.print( conf.getValue("java.public")+" "+conf.getValue("java.interface") +" "+ name);
        if (parents != null && parents.length > 0) {
            pw.print(" "+conf.getValue("java.extends")+" ");
            for (int i = 0; i < parents.length - 1; i++) {
                pw.print(parents[i] + " , ");
            }
            pw.print(parents[parents.length - 1]);
        }

        pw.println("{");
        BuildingBlocks.createMethods(methods, pw, false);
        pw.println("}");
        fcw.close();
        logger.debug("code generated successfully for {} interface",getName());

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Method[] getMethods() {
        return methods;
    }

    public void setMethods(Method[] methods) {
        this.methods = methods;
    }

    public String[] getParents() {
        return parents;
    }

    public void setParents(String[] parents) {
        this.parents = parents;
    }
}
