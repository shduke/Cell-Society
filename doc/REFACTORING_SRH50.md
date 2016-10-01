## Lab Refactoring
@author Sean Hudson (srh50)
#### Design issue

A significant refactoring issue that we discussed for a while and I then implemented
in class was streamlining the method call process for updating the simulations. Before we were having the applicationController call the .step() method of our simulation in the program loop.This was a problem since it did not really allow for multiple simulations or
for much control over the simulation's step method. We concluded that this method for controlling the simulation's update/step method should go in the simulation controller. Before the responsibilities of the simulation controller were not contained within its class. Also the protocol for updating the simulation was not well defined. Some simulations updated differently from others, which was bad on a consistency and readability standpoint. The process for adding a new simulation wasn't as defined as it should have been.

#### Improvement

We created a updateSimulation() method in simulationController that we will have the application controller call. This will then loop through a collection of all the currently running simulations and call their .step() method. This will allows the simulation controller to actually control the simulations. Also, I removed the abstract component of setNextState() in simulation so that different simulations will have more flexibility in how they update their simulation. These changes make the program loop much more transparent and structured. This in-turn adds to the overall extensibility of the program.

#### links
[Refactoring commit](https://git.cs.duke.edu/CompSci308_2016Fall/cellsociety_team05/commit/6ca7cfc92c964c690a320c122a4892a524831a6f)

This links to the commit that contains the changes.