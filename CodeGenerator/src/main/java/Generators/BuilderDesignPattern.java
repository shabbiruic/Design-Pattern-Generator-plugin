package Generators;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class BuilderDesignPattern extends DesignPatternGenerate {
    //class for which you want to create the builder class.
    private ClassGenerator inputClass;
    public BuilderDesignPattern(ClassGenerator inputClass) {
        this.inputClass = inputClass;
    }

    @Override
    public void generateCode(String outputFolderPath) {
        inputClass.generateCode(null,null,outputFolderPath);
    }

    @Override
    public int generateCodeFromTemplate(String templatePath, String outputFolderPath) {
        return generateCodeFromTemplate(templatePath,outputFolderPath, BuilderDesignPattern.class);
    }
}
