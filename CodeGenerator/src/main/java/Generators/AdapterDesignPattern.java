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
public class AdapterDesignPattern extends DesignPatternGenerate {

    private InterfaceGenerator interfaceGenerator1;
    private InterfaceGenerator interfaceGenerator2;

    public InterfaceGenerator getInterfaceGenerator1() {
        return interfaceGenerator1;
    }

    public void setInterfaceGenerator1(InterfaceGenerator interfaceGenerator1) {
        this.interfaceGenerator1 = interfaceGenerator1;
    }

    public InterfaceGenerator getInterfaceGenerator2() {
        return interfaceGenerator2;
    }

    public void setInterfaceGenerator2(InterfaceGenerator interfaceGenerator2) {
        this.interfaceGenerator2 = interfaceGenerator2;
    }

    public ClassGenerator[] getConcreteClasses1() {
        return concreteClasses1;
    }

    public void setConcreteClasses1(ClassGenerator[] concreteClasses1) {
        this.concreteClasses1 = concreteClasses1;
    }

    public ClassGenerator[] getConcreteClasses2() {
        return concreteClasses2;
    }

    public void setConcreteClasses2(ClassGenerator[] concreteClasses2) {
        this.concreteClasses2 = concreteClasses2;
    }

    public ClassGenerator getAdapterClass() {
        return adapterClass;
    }

    public void setAdapterClass(ClassGenerator adapterClass) {
        this.adapterClass = adapterClass;
    }

    private ClassGenerator concreteClasses1[];
    private ClassGenerator concreteClasses2[];
    private ClassGenerator adapterClass;
    public static final Logger logger = LoggerFactory.getLogger(AdapterDesignPattern.class.getName());


    public AdapterDesignPattern(InterfaceGenerator interfaceGenerator1, InterfaceGenerator interfaceGenerator2, String[] concreteClassesOne, String[] concreteClassesTwo) {
        logger.debug("executing constructor...");
        this.interfaceGenerator1 = interfaceGenerator1;
        this.interfaceGenerator2 = interfaceGenerator2;
        this.concreteClasses1 = BuildingBlocks.createConcreteClass(concreteClassesOne);
        this.concreteClasses2 = BuildingBlocks.createConcreteClass(concreteClassesTwo);
        createImplementation();
    }
    public void createImplementation()
    {
        logger.debug("executing implemention method...");
        String adapterClassName = interfaceGenerator2.getName() + conf.getValue("adapter");
        adapterClass = BuildingBlocks.createConcreteClass(adapterClassName);
        adapterClass.setVariables(new Pair[]{new Pair(Helper.lowerFirstCharacterOfString(interfaceGenerator2.getName()),interfaceGenerator2.getName())});
        Method m = Method.clone(interfaceGenerator1.getMethods()[0]);
        m.setImplementation(new String[]{Helper.lowerFirstCharacterOfString(interfaceGenerator2.getName())+"."+interfaceGenerator2.getMethods()[0].getName()+"();"});
        adapterClass.setMethods( new Method[]{new Method.MethodBuilder(adapterClassName,false,"").setParameter(new Pair[]{new Pair(Helper.lowerFirstCharacterOfString(interfaceGenerator2.getName()),interfaceGenerator2.getName())}).setImplementation(new String[]{conf.getValue("java.this")+"."+ Helper.lowerFirstCharacterOfString(interfaceGenerator2.getName())+"="+ Helper.lowerFirstCharacterOfString(interfaceGenerator2.getName())+";"}).build(),m });

    }

    @Override
    public void generateCode(String outputFolderPath) {

        interfaceGenerator1.generateCode(outputFolderPath);
        interfaceGenerator2.generateCode(outputFolderPath);
        for (ClassGenerator cg : concreteClasses1)
            cg.generateCode(null, new InterfaceGenerator[]{interfaceGenerator1}, outputFolderPath);
        for (ClassGenerator cg : concreteClasses2)
            cg.generateCode(null, new InterfaceGenerator[]{interfaceGenerator2}, outputFolderPath);
        adapterClass.generateCode(null, new InterfaceGenerator[]{interfaceGenerator1}, outputFolderPath);
        logger.debug("code gets generated successfully");
    }

    @Override
    public int generateCodeFromTemplate(String templatePath, String outputFolderPath) {
        return generateCodeFromTemplate(templatePath,outputFolderPath, AdapterDesignPattern.class);
    }
}

