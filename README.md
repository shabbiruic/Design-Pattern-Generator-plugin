## Design Pattern Code Generator Plugin 

---

### Overview

The goal of this project is to create a design pattern code generator **intellij plugin** which will generate the specified designed pattern in java with the minimal user input.

Implementation consist of design pattern generator for all the design pattern to generate the design pattern depending on user input. We have provided the default input for all the design pattern which can be changed if required to generate the custom design pattern else it will generate the default design patterns Implememtations. Also test cases are provided with will test the basic functionality of all the design pattern generators

### Instructions

#### Running the application

1. Clone or download this repository onto your system

2. Open the Command Prompt (if using Windows) or the Terminal (if using Linux/Mac) and browse to the project directory

3. Maintain the Configuration in the application.conf file

	1.key "pathForGeneratedObject" should point to resource folder(Absolute Path) 
	example: ```<SystemDependentPath>\\src\\main\\resources\\```
	2.Also have to specify the ```USER_HOME``` key in **log.properties** file which specifies the absolute path 
	where you want to keep your log files.

4. Build the project using `gardle clean build runIde`
5. Select the Tool option from top menu bar inside that design pattern are classfied into behavioral, Creational and structural.

#### User input details
1. Design pattern are generated in the same directory where the active editor file lies.
2. Design pattern generation menu will be enabled only when java project is open and atleast one file is active in editor(i.e. open in editor)
3. User can't navigate to the next input dialog untill all the mandatory fields are filled in present Input Dialog.
4. When the name clash happens message will pop up and it will not allow user to proceed further untill user resolves the name clash but user can go back using previous button to change the earlier provided names.
5. At any step user can go back and change the previous provided input untill generate button is clicked.
6. By default fileSelector and Directory selector will point to the resource folder location maintained in the applicatoin.conf.
7. Whenever it asks for multiple inputs names like class names you have to pass them in comma seperated format. you can also pass the single string without any comma.
	
### Project Structure

This project makes use of Gradle's multi-project build and is divided into 2 sub-projects:

1. **`Generators`:** Contains all the logic for creating design pattern in **Java** with the valid user inputs.
2. **`DesignPatternDialogFlows`:** Contains details about what kind of input dialog a design pattern will have
	and its sequence. Also it has various input validation checks like for Name Clash with the provided inputs and 
	other checks like next button will only work when all the needed inputs are provided etc.
3. In this I have implemented following design pattern generators.
    1. AbstractFactoryDesignPattern : it will take input as 
        1. ProductInterface : Defines the basic structure of product created by factory  
        2. abstractFactoryInterface : Defines the basis structure of all factory classes. 
        3. Prefix Of Concrete Product:  Depending on which different factory class will be 
        created and different concrete product classes will also get created.
        4. Array of all the Concrete Product Names. 
    2. AdapterDesignPattern : it will take input as 
        1. Product Type 1 Interface : Defines the structure of Product Type 1.
        2. Product Type 2 Interface : Defines the structure of Product Type 2.
        3. Array of Names of all Concrete Product of Type 1.
        4. Array of Names of all Concrete Product of Type 2.
    3. BridgeDesignPattern : it will take input as
        1. Name of Main abstract class which will have loose coupling with other classes object in some of 
        the method implemetation.
        2. Interface defining the structure of Composition Object which has loose coupling with
         Main class object 
        3. Array of names of main abstract class concrete Object. 
        4. Array of names of main composition class concrete Object.
    4. BuilderDesignPattern : it will take input as
        1. A complete Class definition and then according
    5. ChainOfResponsibilityDesignPattern : it will take input as
        1. Name of handler Interface.
        2. Name of all the concrete Handler classes.
        3. Complete Class defination of object to be handled by handler classes
    6. CommandDesignPattern : it will take input as 
        1. Name of Command Interface, 
        2. Name of all the concrete Object for which you want to generate command classes
    7. CompositeDesignPattern : it will take input as
        1. Interface which defines the structure of all the leaf elements.
        2. Array of strings which specifies the name of all the leaf Class.
        3. Name of Composite Class which will have list of leaf objects. 
    8. DecoratorDesignPattern : it will take input as
        1.Interface which proivdes structure for all the basic object
        2. Array of names of all the concrete object inheriting the basic object
        3. Array of names concrete Decorator Class which will add functionality on the top
        of concrete Object.
     9. FacadeDesignPattern : it will take input as
        1. Parent Interface of all the concrete object.
        2. Array of names of all the concrete Classes implementing the parent interface.
     10. FactoryDesignPattern  : it will take input as 
         1. Parent Interface for all the concrete objects
         2. Array of names of all the Concrete Classes implementing the parent interface.
     11. FlyweightDesignPattern: it will take input as 
         1. Parent Interface for all the concrete objects
         2. Array of names of all the Concrete Classes implementing the parent interface.
     12. InterpreterDesignPattern  : it will take input as
         1. Parent Interface for all the concrete objects
         2. Array of names of all the Concrete Classes implementing the parent interface.
     13. IteratorDesignPattern : it will take input as
         1. Interface defining the iterator type
         2. Class defining the object for which you want to create iterator design pattern
     14. MediatorDesignPattern : it will take input as
         1. Array of names of concrete classes which wants to communicates.
     15. MementoDesignPattern :  it will take input as
         1. Class definition of the object which you want to save.
     16. ObserverDesignPatterm : it will take input as
         1. Interface definition of publisher Object 
         2. Interface definition of subscriber Object 
         3. Array of names of concrete Publiser.
         4. Array of names of concrete Subscriber.
         5. Class definition of message which is being created and consumer.
     17. prototypeDesignPattern : it will take input as
         1. Class definition of abstract Class. 
         2. Array of names of all concrete Class extending above abstract class.
     18. ProxyDesignPattern : it will take input as
         1. Parent Interface definition
         2. Concrete Class names Implementing above interface.
      19. StateDesignPattern :  it will take input as
          1. Interface definiting the state structure.
          2. Array of names of all concrete state Classes.
          3. Name of context Class.
      20. StrategyDesignPattern :  it will take input as
           1. Interface defining the structure of strategy. 
           2. Array of names of all concrete Strategy Classes
           3. Name of context Class.
      21. TemplateDesignPatten :  it will take input as
            1. Class definition of template class.
            2. Array of names of all concrete extending template class.
      22. VisitorDesignPattern : it will take input as
            1. Interface defining structure of visitor objects.
            2. Interface defining structure of Element objects.
            3. Array of names of all concrete visitor Classes.
            4. Array of names of all concrete element Classes.
	
## Implementation Details

### Design pattern Generators Details
- I have implemented the Design Pattern Generator with the following approach.
Every design pattern consists of either a class or a interface. So I have used **composite design** pattern 
in modeling all the design pattern. 
- Similarly all class will consist of methods, variables and other parameter like whether it is 
abstract or not and same is the case with interface. 
- While generating the code for the class we also need the information about its parent classes and 
interfaces but they are not part of class itself but provides methods and variables. So to have 
link and loose coupling between them I have used the **bridge design** pattern approach 
while generating the code for the java Class files so that it gets the information 
about the method that it has to implement during the code generation. 
- If I keep the parent class and interfaces into all the class then every concrete class will 
have the complete information about the parent class and also it becomes really difficult later 
 to change some thing in the parent class as all child classes also needs to be changed 
 but if we use bridge design pattern then we can avoid that tight coupling. 
- Since the class in itself is a bigger entity and I wanted to construct it in fragments so I 
have used the **builder pattern** which helps me in creating the class object iteratively.
- For supplying configuration information to all the different design pattern generator I have 
used the **singleton design pattern** approach and created a Singleton configuration class whose
object I used whenever I need to fetch the configuration. 
- **Prototype design pattern** made the method overriding/implementation from the parent class/Interfaces 
easier as I can easily get the complete skeleton of parent methods using Prototype design pattern 
and I just  have to supply the implementation of it.
- Used **Memento design pattern** to save(In json) the complete basic object( also called as template which 
are Class and Interface in my case) that are needed for design pattern generation these 
basic class and interfaces can be changed if user needs some custom design pattern to be generated.
- **Template design pattern** help me in writing the generic method which can be used by all 
the design pattern generator to inherit the implementation from Design pattern Generator
( Parent Class) to generate its complete code using already saved template.

#### Class structure looks like this 
````
private String name;
private Pair variables[];
private boolean isAbstract;.
private Method methods[];.
private boolean isBuilderNeeded;
/*and only one method called generateCode which take three parameters named as 
parent Class, array of parent interfaces and a location where you want to generated the 
class file.*/
public int generateCode(ClassGenerator parentClazz, InterfaceGenerator[] parentInterfaces, String outputFolderLocation);
````

Generic structure of my design patterns looks like
````
private InterfaceGenerator I1;
private InterfaceGenerator I2;
.
.
.
.
private ClassGenerator C1;
private ClassGenerator C2;
.
.
.
void generateCode(String outputFolderPath)
{
}
// this method will not be always present but only when some extra information has to 
// be explicitly added for design pattern creation.
void createImplementation(){

//this method just provide class name then calls the parent class which has im
plementation.
public int generateCodeFromTemplate(String templatePath, String outputFolderPath) {
}

````
- They all will have the `generateCode()` method which takes the location of file 
where to store the generated code.

- They will also have a method called `generateCodeFromTemplate()` from template
 which will call the parent method 'generateCodeFromTemplate()' by supplying 
 there class name so that while reading the code it knows to which object it 
 should me mapped.

- Certain design pattern generator class will also have the create implementation 
method which will do the task of overriding or implementing the methods for 
certain class, creating class variables etc depending on design pattern need.

- I have created the `FileCreatorAndWriter` class which takes create of creating 
a file and writing the code into it line by line.

- Used the helper class which has method like

    1. `readGeneratedInterface(String filePath)` given the file name reads 
    the stored interface and creates the object out of it 
    which can be used as a building block for design pattern generator.

    2. `readGeneratedClass(String filePath)` same functionality as above 
    except it reads the saved class object and generates the object out of it.

- BuildingBlocks class provide the basic methods that are needed during the design 
pattern code generation like method code generation, 
Creating concrete class from interfaces or from abstract class


### User Interface Details
- All the user Input Dialog inherits the parent dialog class called `InputDialog` which provides the basic footer panel which
	has buttons like previous and next, It also provide the basic listeners for these buttons.
- User Inputs are taken from only three kinds of Input Dialogs which are:
	1. **`StringInput`** : it takes single string as input.
	2. **`FileSelector`** : it allow the user to browser the .json file which has predefine structure for classes or interfaces.
	3. **`ArrayOfString`**: it takes comma separated multiple string as input design for taking multiple classes names which all
	implements the same interface or extends the same class.
- `DesignPatternDialogFlow` which is parent for all design pattern dialog flow has the logic for detecting name clash with the
	provided user input which uses the `PsiDirectory` object to validate whether input names already exits or not in present 
	directory.
- `DesignPatternDialogFlow` creates the hashSet from the user inputs to check name clashes.
- `TriggerGeneration' class which trigger the specific design pattern generation dialog flow passes the `PsiDirectory` Object
	while creating the design pattern dialog flow object which in turn provides the information about the present directory 
	in which user is.

#### class structure 
- Class structure of **DesignPatternDialogFlow** which is parent for all Design Pattern Dialog flows

```
public abstract class DesignPatternDialogFlow {

    // it stores the detail about the current directory
    public PsiDirectory currentDirectory;

    // points to the path where generated files will be placed.
    public String outputPath;
    public Configuration conf = Configuration.getInstance();

    //Stores the names which user has provided for creation.
    public HashSet<String> filesToCreate = new HashSet<>();

    //Constructor to set current Directory and outPath
    public DesignPatternDialogFlow(PsiDirectory currentDirectory) {
        this.currentDirectory=currentDirectory;
        outputPath = currentDirectory.getVirtualFile().getPath();
    }
    
    //overrided nethod to remove the previous given input names from hash map when user clicks on previous button.
    public void removeFileFromSet(String[] filesName)
    {
        //Implementation
    }
    

    //overrided method to check whether array of names are valid or not depending on whether they create the name clash or not.
    public boolean isFileNameValid(String[] filesName)
    {
       //Implemetation
    }
    // implemented by every design pattern dialog flow depending on kind of design pattern.
    public abstract int generateCode();
}
```
-  Class structure of **InputDialog** which is parent for all Input Dialogs

```


@NoArgsConstructor
@Data
public abstract class InputDialog {

    //button for moving to previous input dialog box.
    protected JButton previousButton;

    //Button for navigating to next input dialog box.
    protected JButton nextButton;

    //Stores the link to the next Input dialog box.
    protected InputDialog next;

    //Stores links to the previous Input dialog box.
    protected InputDialog previous;

    //Frame on which all the UI component will be placed
    protected JFrame frame;

    //stores the link to the design pattern which has evoked this dialog
    DesignPatternDialogFlow designPatternDialogFlow;

     // Constructor which sets the Dialog box titles and sequence of flow
    public InputDialog(String title,DesignPatternDialogFlow designPatternDialogFlow) {
    // Implementation
    }

    // this adds the child panel on the top of the frame
    public void add(Component component)
    {
        frame.add(component,BorderLayout.NORTH);
    }

    //enables the changing of next button to generate button when all the required inputs are taken.
    public void setNext(InputDialog next) {
      //Implementation Details
    }

    // disables the previous button when input dialog is first in the sequence
    public void setPrevious(InputDialog previous) {
        //Implementation Details
    }

    // check user input validity and triggers the error message pop up if it's invalid.
    public boolean isValidInput(String input)
    {
       //Implementation Details
    }

    // which makes Dialog box visible
    public void setVisible(boolean b)
    {
    //Implementation Details
    }
    
    // Gives previous Dialog box pointer
    public InputDialog getPrevious()
    {
        //Implementation Details
    }
    //Gives next Dialog box pointer.
    public InputDialog getNext()
    {
        //Implementation Details;
    }

    public abstract void removeFileInSet();

 // other Getters and Setters

}

```

