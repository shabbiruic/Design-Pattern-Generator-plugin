package Utilities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
public class Pair{
    private String accessModifier;
    private String variableName;
    private String dataType;


    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    private boolean isFinal=false;


    public Pair(String variableName, String dataType) {
        this.variableName = variableName;
        this.dataType = dataType;
        this.accessModifier = Configuration.getInstance().getValue("java.modifiers.public");
    }

    public Pair(String accessModifier, String variableName, String dataType) {
        this.accessModifier = accessModifier;
        this.variableName = variableName;
        this.dataType = dataType;
    }

    public Pair(String accessModifier, String variableName, String dataType, boolean isFinal) {
        this.accessModifier = accessModifier;
        this.variableName = variableName;
        this.dataType = dataType;
        this.isFinal = isFinal;
    }


    public String getAccessModifier() {
        return accessModifier;
    }

    public void setAccessModifier(String accessModifier) {
        this.accessModifier = accessModifier;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return variableName.equals(pair.variableName) &&
                dataType.equals(pair.dataType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(variableName, dataType);
    }

    public Pair clonePair(){
        return new Pair(this.accessModifier, this.variableName, this.dataType, this.isFinal);
    }

}