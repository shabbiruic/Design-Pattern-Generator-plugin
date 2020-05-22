package Generators;


import Utilities.Configuration;


//this is interface which gets implemented by every class which generates something.
public interface Generatable{

    public Configuration conf = Configuration.getInstance();

}
