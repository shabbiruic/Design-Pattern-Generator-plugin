## CS 474 - Object Oriented Languages and Environments
## Homework 3 - Design Pattern Plugin With Name Clash detection 

---

### Overview

The objective of this homework was to check during the design pattern generation that specified user input name 
for the classes and interfaces should not create the Name Clash. If the provided input creates the name clash then user 
should not be allowed to generate the design pattern untill he changes the name/names to non confliciting names.

My implementation consists of main parent ```InputDialog``` Class which is inherited by all types of user input
dialogs which has the common UI element required by all child input Dialogs like previous and next button etc.
And the Logic for checking **Name Clash** reside in the parent class of all the Dialog flows i.e. `DesignPatternDialogFlow` 
in the method named `isFileNameValid`. Furthermore, an exhaustive test suite is 
provided which verifies the correctness of Input Dialog flows for the design patterns along with the provided user input.

### Instructions

#### Running the application

1. Clone or download this repository onto your system

2. Open the Command Prompt (if using Windows) or the Terminal (if using Linux/Mac) and browse to the project directory

3. Maintain the Configuration in the application.conf file

	1.key "pathForGeneratedObject" will point to resource folder(Absolute Path) 
	example: ```<SystemDependentPath>\\src\\main\\resources\\```
	2.Also have to specify the ```USER_HOME``` key in **log.properties** file which specifies the absolute path 
	where you want to keep your log files.

4. Build the project using `gardle clean build runIde`
5. Select the Tool option from top menu bar inside that design pattern are classfied into behavioral, Creational and structural.

#### User input details
1. Design pattern are generated in the same directory where the active editor file lies.
2. Design pattern generation menu will be enabled only when java project is open and atleast one file is active in editor(i.e. open in editor)
3. User can't navigate to the next input dialog untill all the mandatory fields are filled in present Input Dialog.
4. When the name clash happens message will pop up and it will not allow user to proceed further untill user resolves the name clash but user can go 
	back using previous button to change the earlier provided names.
5. At any step user can go back and change the provided input untill generate button is clicked.
	
### Project Structure

This project makes use of Gradle's multi-project build and is divided into 2 sub-projects:

1. **`Generators`:** Contains all the logic for creating design pattern in **Java** with the valid user inputs.
2. **`DesignPatternDialogFlows`:** Contains details about what kind of input dialog a design pattern will have
	and its sequence. Also it has various input validation like checks for Name Clash with the provided inputs and 
	other checks like next button will only work when all the needed inputs are provided etc.
	
### Implementation Details
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



