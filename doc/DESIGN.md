# cellsociety 

## Team Members:
Sean Hudson (srh50)
Michael Schroeder (ms548)
Kayla Schulz (kms114)

### Introduction
Our team is trying to create a flexible program that will simulate cellular
automata for a variety of different simulations and views. Our goal is to create a design that is most flexible for a multitude of simulations (or potential simulations) it can run and the xml config files we could encounter.  We want to create a program that minimizes the dependencies between the classes. In terms of the architecture of our design, we intend to keep the abstract parent classes closed, but the child classes will be open for more flexibility when creating/changing the program. Additionally, we want to make the user experience good to where a user can run different simulations under different parameters and environments and observe how each one works.

### Overview

![alt text](data/CellSocietyPlan.JPG "Our Cell Society Plan")

The program will be launched with the main class, which instantiates the application controller and starts the game loop. The controller creates the Application View, and opens the window. The program will automatically load a default simulation, but the user can also choose an XML file, if he/she would like. The application controller will pass the responsibility on to the simulation controller, which will go through and update the simulation to create and run the simulation. The SimulationController updates the Simulation by calling Simulation.step(). Each simulation has its own step method, where it specifies how the program should run (all inherited from the simulation superclass with the abstract method of step). This iterates through the Simulation’s Grid, and updates the state of the cells according to their current state and that specific Simulation’s rules and parameters. Although we stuck with our design relatively well, we did not end up implementing a SimulationView class like we had originally planned (that can be seen in the picture). Instead, we ran all of those items through the applicationView and the simulation class. The two of those interacted together to form what would have been our SimulationView class. 

### User Interface

![alt text](data/CellSocietyUI.JPG "Our Cell Society User Interface")

When the program is started, a view with the main toolbar and a default simulation will be loaded (currently the default simulation is Foraging Ants). If the user chooses, they can immediately change the simulation running by loading in a new xml file. At that point, the user will choose to run the simulation. While the simulation is running, the user will have the option to speed up or slow down the animation, pause, or step through each phase of the animation. The ‘simulation toolbar’ will be populated with sliders that will allow the user to change certain parameters for the simulation. At the bottom of the screen, the user will be able to see a graph that tracks how many cells are in each state at each iteration, or step, through the program. In terms of erroneous situations that could be encountered, the user will get a notification if a an XML file has not been loaded or if the data inside of the XML file does not comply. In terms of how our UI changed from our plan to the end of the project, it did not change that much except for two small changes. Instead of having both a pause and a resume button, both of those are encapsulated in the same button, it just changes text depending on whether the simulation is 'paused' or 'playing'. We also decided to not include a reset button for the simulations. The user has the ability to load in a new xml file, or the same one, which will effectively restart the simulation if they would like. We decided to not include this button so we did not clutter the UI with unnecessary buttons. We wanted the program to be as simple for the average user as possible.

### Design Details

# public class Main
* Runs the program
* Creates an instance of the ApplicationController class 

# public class ApplicationController
* Class that ties the whole program together
* Runs the program loop
* Resets the view my setting the border pane
* Holds the responses to user input (through the buttons or attempting to load a new file in)
* Holds a reference to the simulation controller

# public class Toolbar
* Creates the static top toolbar view that holds the speed up, pause/play, step and load xml file buttons
* Utilizes the resource file to set up the button text

# public class SimulationController
* Handles the simulation and simulation swapping
* Iterates through to update the simulation by calling the abstract simulation class 
* Catches errors that are thrown from the xmlparser when the simulation loaded and is not in the correct format, by raising an alert and prompting the user to enter a different file

# abstract public class Simulation
* Methods for handling the simulation config files
* Methods for handling the simulation specific grid generation
* Methods for initializing and updating the gridView
* Initializes the cells and handling neighbors (all of those methods go into more specificity for each particular simulation)

# public class <type>Simulation extends Simulation
* Handle simulation specific methods
* Holds simulation specific variables
* Inherits general Simulation methods
* Handles the particular neighbor cases and setting the next state for a particular cell

# public class Grid
* Generates the layout based on the config file’s specifications
	* Handle different grid sizes
	* Handle different cell shapes
* holds all the current cells

# public class Cell
* Holds the state of the cell
* Holds simulation specific information in each cell
* Method for handling being updated
* Holds the particular location within the grid for each cell
* Sets the color by getting the current state and determining what color it should be

# public class XMLParser
* Parses through the xml file loaded and creates the simulation with the parameters passed in from the file
* Throws an exception to the simulation controller if an incompatible file is loaded in

### Design Considerations

When we began to structure our design, we went through many different options. Our first step was to list the major classes we would need to create the project. This list included simulations (a parent class with all of the different types of simulations as child classes underneath), a grid class, our main class, a controller class that would handle the GUI and load the simulation, and a view class that would handle the particular view for each simulation. After discussing the program in very broad terms, we decided take it further and write out a flow-chart connecting the classes together. 

We spent a significant amount of time trying to iron out the major details on how the program would be structured. After main launched the program, we wanted to pass the new responsibilities to the controller. One of our major discussions included how the controller would function. Would the controller control the view or would the model control the view? For now, we ended up deciding that the controller would have control over most of the program and we would run everything from the application controller.  Another focal point of our discussions was deciding how the handle multiple simulations. In order to make it as simple as possible to add a new simulation, we wanted to make it where another developer could access as few areas of the program as possible and successfully add their own simulation. In order for a developer to add a new simulation, they need to add it into the xmlParser and create a subclass of simulation that handles the specifications of the new simulation. Additionally, they would need to create subclasses for each cell they would want inside of his/her simulation that would handle the state of the cell and neighbor. We thought this would create the cleanest code, but would make it relatively clear cut for a developer adding a new simulation to the project.

Although we have our flow for the program drawn out, we still have differences in how we envision the the simulation and different controllers working together. Before we begin writing the code, our team will need to decide how we want the controllers and subclasses to handle different inputs and changes to the simulations.

### Team Responsibilities

Sean will handle the XML files, XML parser and 2 of the simulations for the back-end. He will also handle the grid and the different grid shapes we encountered. Michael will handle the rest of the simulations for the back-end and the controller. Kayla will handle the front-end material such as the UI, the toolbar, and the simulation graph. Overall, Sean and Michael will work on the back-end while Kayla will work on the front-end.