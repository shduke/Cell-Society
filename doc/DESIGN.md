# cellsociety 

## Team Members:
Sean Hudson
Michael Schroeder 
Kayla Schulz

### Introduction
Our team is trying to create a flexible program that will simulate cellular
automata for a variety of different simulations and views. We are hoping to create a design
that is most flexible in the multitude of simulations (or potential simulations) it can run
and the xml config files we could encounter.  We want to create a program that
minimizes the dependencies between the classes. In terms of the architecture of our design,
we intend to keep the abstract parent classes closed, but the child classes will be open
for more flexibility when creating/changing the program.

### Overview

![alt text](CellSocietyPlan.JPG "Our Cell Society Plan")

The program will be launched with the main class, which instantiates the controller and starts the game loop. The controller creates the Application View, and opens the window. The user can choose an XML file, which the SimulationController will use to create and run the simulation. The SimulationController updates the Simulation by calling Simulation.update(). This iterates through the Simulation’s Grid, and updates the state of the cells according to their current state and that specific Simulation’s rules and parameters. The Simulation updates its SimulationView according to the new state.

### User Interface

##Please place picture


When the program is started, a view with the main toolbar and a blank simulation (or grid) section. The user will then select a simulation from a drop-down menu and load XML file in. At that point, the user will choose to run the simulation. While the simulation is running, the user will have the option to speed up or slow down the animation, pause, resume, or step through each phase of the animation. The ‘simulation toolbar’ will be populated with specific data points for each simulation. In terms of erroneous situations that could be encountered, the user will get a notification if a an XML file has not been loaded or if the data inside of the XML file does not comply.

### Design Details

# public class Main
* Runs the program
* Creates an instance of the Controller class 

# public class ApplicationController
* Class that ties the whole program together
* Has an instance of the application view, which is the GUI.
* Responsible for tieing the simulation view with the Application view
* Handles input by Delegating the the XML parser class
* Holds a reference to the simulation controller
* Runs the program loop

public class ApplicationView
* Acts as the GUI for the program
* Has a static top toolbar view that holds the speed up, play, pause, step and restart buttons
* Holds a view pane that will be filled with the simulation view
* Will pass top toolbar input to the controller for handling

public class SimulationController
* Handles the simulation and simulation swapping
* Handles the interaction between the simulation and the simulation view
* Has methods for controlling the simulation based on input from the top toolbar and simulation toolbar

public class SimulationView
* Displays the simulation specific UI like the grid and the simulation toolbar
* Will pass simulation toolbar input into the simulation controller for handling

abstract public class Simulation
* Methods for handling the simulation config files
* Methods for handling the simulation specific grid generation
* Methods for updating the grid

public class <type>Simulation extends Simulation
* Handle simulation specific methods
* Holds simulation specific variables
* Inherits general Simulation methods

public class Grid
* Generates the layout based on the config file’s specifications
	* Handle different grid sizes
	* Handle different cell shapes
* holds all the current cells

public class Cell
* Holds the state of the cell
* Holds simulation specific information in each cell
* Method for handling being updated

Abstract public class config
* Methods for creating the simulation view items that are shared between all types of simulations
* Handle simulation label creation

public class <type>Config
* Holds simulation specific configurations



### Design Considerations

When we began to structure our design, we went through many different options. Our first step was to list the major classes we would need to create the project. This list included simulations (a parent class with all of the different types of simulations as child classes underneath), a grid class, our main class, a controller class that would handle the GUI and load the simulation, and a view class that would handle the particular view for each simulation. After discussing the program in very broad terms, we decided take it further and write out a flow-chart connecting the classes together. 

We spent a significant amount of time trying to iron out the major details on how the program would be structured. After main launched the program, we wanted to pass the new responsibilities to the controller. One of our major discussions included how the controller would function. Would the controller control the view or would the model control the view? For now, we ended up deciding that the model would control the simulation view, while the view of the toolbar (a view that does not change throughout the entire duration of the program) would be controlled by the controller.  Another focal point of our discussions was deciding how the handle multiple simulations. In order to make it as simple as possible to add a new simulation, we wanted to make it where another developer could access as few areas of the program as possible and successfully add their own simulation. Currently, our plan would be to have the developer add two classes - one underneath the simulation view package and another under the simulation package.

Although we have our flow for the program drawn out, we still have differences in how we envision the the simulation and different controllers working together. Before we begin writing the code, our team will need to decide how we want the controllers and subclasses to handle different inputs and changes to the simulations.

### Team Responsibilities
Sean will handle the XML files, XML parser and 2 of the simulations for the back-end. Michael will handle the rest of the simulations for the back-end and the controller. Kayla will handle the front-end material such as the UI, the toolbar, and the cell class. Overall, Sean and Michael will work on the back-end while Kayla will work on the front-end.