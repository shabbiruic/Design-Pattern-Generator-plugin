package Utilities;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Data
public class Method {

    String name;
    String returnType;
    Pair parameter[];
    String[] implementation;
    boolean isAbstract;
    public static final Logger logger = LoggerFactory.getLogger(Method.class.getName());

    public boolean isConstructor() {
        return isConstructor;
    }

    public void setConstructor(boolean constructor) {
        isConstructor = constructor;
    }

    boolean isConstructor;

    public String[] getImplementation() {
        return implementation;
    }

    public void setImplementation(String[] implementation) {
        this.implementation = implementation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Method method = (Method) o;
        return Objects.equals(name, method.name) &&
                Objects.equals(returnType, method.returnType) &&
                Arrays.equals(parameter, method.parameter);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, returnType);
        result = 31 * result + Arrays.hashCode(parameter);
        return result;
    }

    public static class MethodBuilder{

        String name;
        String returnType;
        Pair parameter[];
        String[] implementation;
        boolean isAbstract;
        boolean isConstructor;

        public MethodBuilder setConstructor(boolean constructor) {
            isConstructor = constructor;
            return this;
        }

        public MethodBuilder(String name,boolean isAbstract,String returnType)
        {
            this.name = name;
            this.isAbstract=isAbstract;
            this.returnType=returnType;

        }

        public MethodBuilder setParameter(Pair[] parameters)
        {
            this.parameter = parameters;
            return this;
        }

        public MethodBuilder setImplementation(String[] implementation)
        {
            this.implementation = implementation;
            return this;
        }

        public Method build()
        {
            return new Method(this);
        }

    }

    public Method(MethodBuilder methodBuilder)
    {
        this.name = methodBuilder.name;
        this.parameter=methodBuilder.parameter;
        this.isAbstract=methodBuilder.isAbstract;
        this.returnType=methodBuilder.returnType;
        this.implementation=methodBuilder.implementation;
        this.isConstructor = methodBuilder.isConstructor;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public Pair[] getParameter() {
        return parameter;
    }

    public void setParameter(Pair[] parameter) {
        this.parameter = parameter;
    }


    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean anAbstract) {
        isAbstract = anAbstract;
    }



    public Method(String name, String returnType, Pair[] parameter, boolean isAbstract) {
        this.name = name;
        this.returnType = returnType;
        this.parameter = parameter;
        this.isAbstract=isAbstract;
    }

    public static Method createConcreteMethod(Method m, Set<Method> methodSet){

        if(methodSet==null||!methodSet.contains(m)) {
            Method cloneMethod = new Method(m.getName(), m.getReturnType(), m.getParameter(), false);
            return cloneMethod;
        }
        return null;
    }
    public static Method clone(Method m)
    {
        if(m!=null)
        {
            Method newMethod = new Method(m.getName(), m.getReturnType(), m.getParameter(), m.isAbstract());
            return newMethod;
        }
        logger.warn("No method to clone");
        return null;
    }

    public static Method[] createConcreteMethod(Method[] m, Set<Method> methodSet){
        if(m!=null) {

            Method cloneMethods[] = new Method[m.length];
            for (int i = 0; i < m.length; i++) {
                cloneMethods[i] = createConcreteMethod(m[i],methodSet);
            }
            return cloneMethods;
        }
        else {
            return null;
        }
    }




}
