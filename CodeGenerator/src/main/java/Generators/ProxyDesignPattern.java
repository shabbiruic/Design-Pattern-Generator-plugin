package Generators;

import Utilities.BuildingBlocks;
import Utilities.Helper;
import Utilities.Pair;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@NoArgsConstructor
public class ProxyDesignPattern extends DesignPatternGenerate {

    private InterfaceGenerator parentInterface;
    private ClassGenerator concreteClass;
    private ClassGenerator proxyClass;
    public static final Logger logger = LoggerFactory.getLogger(ProxyDesignPattern.class.getName());

    public ProxyDesignPattern(InterfaceGenerator parentInterface, String concreteClassName) {
        this.parentInterface = parentInterface;
        concreteClass = BuildingBlocks.createConcreteClass(concreteClassName);
        proxyClass = BuildingBlocks.createConcreteClass(conf.getValue("java.proxy")+concreteClassName);
        proxyClass.setVariables(new Pair[]{new Pair(conf.getValue("java.modifiers.private"), Helper.lowerFirstCharacterOfString(parentInterface.getName()),parentInterface.getName())});
        logger.info("constructor got executed successfully");
    }

    @Override
    public void generateCode(String outputFolderPath) {
        parentInterface.generateCode(outputFolderPath);
        concreteClass.generateCode(null, Helper.convertInterfaceObjectToArray(parentInterface),outputFolderPath);
        proxyClass.generateCode(null, Helper.convertInterfaceObjectToArray(parentInterface),outputFolderPath);
        logger.debug("code got generated successfully");
    }

    @Override
    public int generateCodeFromTemplate(String templatePath, String outputFolderPath) {
        logger.debug("generating Proxy design pattern via template approach");
        return generateCodeFromTemplate(templatePath,outputFolderPath, ProxyDesignPattern.class);

    }

}
