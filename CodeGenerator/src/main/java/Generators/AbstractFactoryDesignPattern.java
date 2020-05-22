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
public class AbstractFactoryDesignPattern extends DesignPatternGenerate {


    InterfaceGenerator productInterface;
    ClassGenerator concreteProduct[];
    InterfaceGenerator abstractFactoryInterface;
    ClassGenerator concreteFactory[];
    private static final Logger logger = LoggerFactory.getLogger(AbstractFactoryDesignPattern.class.getName());

    public AbstractFactoryDesignPattern(InterfaceGenerator productInterface, InterfaceGenerator abstractFactoryInterface, String[] prefixOfConcreteObject, String concreteProductNames[]) {
        logger.debug("executing constructor...");
        this.productInterface = productInterface;
        this.abstractFactoryInterface = abstractFactoryInterface;
        createImplementation(prefixOfConcreteObject,concreteProductNames);
    }

    public void createImplementation(String[] prefixOfConcreteObject, String[] concreteProductNames) {

        abstractFactoryInterface.setMethods(new Method[]{new Method.MethodBuilder(conf.getValue("java.get")+productInterface.getName(),true,productInterface.getName()).setParameter(new Pair[]{new Pair(Helper.lowerFirstCharacterOfString(productInterface.getName()),productInterface.getName())}).build()});
        String concreteProductList[] = new String[prefixOfConcreteObject.length * concreteProductNames.length];

        concreteProduct = BuildingBlocks.createConcreteClass(listWithGivenPrefixes(concreteProductNames, prefixOfConcreteObject));

        concreteFactory = BuildingBlocks.createConcreteClass(listWithGivenPrefixes(new String[]{abstractFactoryInterface.getName()}, prefixOfConcreteObject));

    }

    @Override
    public void generateCode(String outputFolderPath) {

        logger.debug("executing generateCode...");
        productInterface.generateCode(outputFolderPath);
        abstractFactoryInterface.generateCode(outputFolderPath);
        for (ClassGenerator cg : concreteProduct)
            cg.generateCode(null, Helper.convertInterfaceObjectToArray(productInterface), outputFolderPath);
        for (ClassGenerator cg : concreteFactory)
            cg.generateCode(null, Helper.convertInterfaceObjectToArray(abstractFactoryInterface), outputFolderPath);
        logger.debug("code generated Successfully....");
    }

    @Override
    public int generateCodeFromTemplate(String templatePath, String outputFolderPath) {
        logger.debug("executing generateCodeFromTemplate...");
        return generateCodeFromTemplate(templatePath, outputFolderPath, AbstractFactoryDesignPattern.class);
    }

    public String[] listWithGivenPrefixes(String[] names, String[] prefixes) {
        String resultantListWithPrefix[] = new String[prefixes.length * names.length];
        int count = 0;
        for (int j = 0; j < prefixes.length; j++) {
            for (int i = 0; i < names.length; i++) {

                resultantListWithPrefix[count] = prefixes[j] + names[i];
                count++;
            }
        }
        return resultantListWithPrefix;
    }
}
