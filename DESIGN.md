CompSci 307: Simulation Project Design (Team 11)
================================================



## Design Review


### Design Goals: provides the high-level design goals of your project




### How to Add New Features: explains, in detail, how to add new features to your project
There are a wide range of potential features that could be implemented in our project: new cell shapes, new edge policies,
new neighbor arrangements, new simulation types, and new visual features to name a few.

* New Cell Shape:


* New Edge Policy:
The implementation of a new edge policy is relatively simple. In the abstract class Cell, currently there are
three methods that allow for the implementation of a specific edge policy: getNeighbors() (torodial),
getNeighborsFinite(), and getNeighborsFlipped(). To implement a new edge policy, a new method must be written in
Cell in the same manner as the existing methods listed above. Once a method has been written, something like
getNeighborsNewEdgePolicy(). Then in order to use this edge policy in a simulation, one must add a conditional
statement into chooseAppropriateNeighbors(), that checks if the nth policy is selected make a call to
getNeighborsNewEdgePolicy(). Lastly, edge policy is directly specified in the properties file for any given,
thus to implement this policy you must create a new properties file or modify an existing one so that
edge_policy = NewPolicy. The data for edge policy in the properties file is parsed within the abstract Class Board.
So, in order to implement this parsing, one must modify the method getEdgeType(), with a conditional that checks
if the string matches the name of the new policy, and if so returns an integer n, that represents our nth policy.

* New Neighbor Arrangement:


* New Simulation Types:
To implement a new simulation such as the cellular automata, Seeds, you must first identify a few
characteristics about the new simulation. First you must write a new txt file to specify the rules of the new simulation.
The rules files are formatted with three initial lines, and then all subsequent lines represent the rules for that given
simulation. The first three lines are as follows:
    Simulation Name
    type:n (the type of Rules to Parse)
    0,1,2,...  (a comma seperated list of all the states)
Type is used in the method getRulesFromLine() in RulesParser to be able to correctly parse the rules. Currently, there
are four options in use, 1,2,3,4. For example game of life which will only have one destination state, so it's rules
are in format 1: (destination_state required_neighbor_state:number_in_required_neighbor_state destination_state); whereas,
PredatorPrey is in format 4: (destination_state required_neighbor_state:number_in_required_neighbor_state destination_state1,destination_state2)
So when adding a new simulation one must first identify which rules format will be sufficient and if none of the current
formats are sufficient, create a new format, and modify getRulesFromLine() accordingly to parse the rules.

In addition to the new rules txt file that must be created, one must also create a new csv config file for the new simulation
as well as a new properties file for the simulation. The CSV files are all the same format regardless of the type of game:
    Simulation Name
    height,width
    grid
which is then followed by a comma delineated 2D array, that represent the starting state of each of the cells in the grid.
The properties files also follow a common format, some of which may or may not be necessary for the new simulation:
    name_of_creator
    type_of_game
    description
    name_of_csv
    color0
    color1
    color2
    shape
    probability
    edge_policy
    neighbor_type
All of the board types are determined based on the type_of_game string, and then correspond to a subclass of the abstract
class Board. One of two options will be appropriate: either one of the existing classes will suffice for the new simulation
or a new class must be made, that is an extension of the Board class. The subclasses of Board mainly are used to distinguish
how the simulation's board will be updated. For example since Seeds is very similar to Game of Life, it is likely that one
would be able to use the same board class that Game of Life uses, GenericBoard. If there is no existing simulation that has
update rules that will suffice for it, a new subclass must be made. If a new subclass was created the method getBoardType()
and getBoard() in SimulationController must be modified to function with the new type of board.
name_of_creator, type_of_game, description, name_of_csv, color0, color1, color2 are self explanatory and take the according
strings. edge_policy and neighbor_type can be selected from the existing options based on what is satisfactory for the simulation.
For example currently if the simulation require torodial or finite edges, one may use edge_policy=torodial, and edge_policy=finite
respectively. For neighbor type, we have enumerated our options with 1 representing a moore neighborhood, 2 being the von
Neumann neighborhood and 3 being a modified version of the moore neighborhood.


* New Visual Features:



### Justification of Design: justifies major design choices, including trade-offs (i.e., pros and cons), made in your project

One design choice we implemented was the use of abstract classes for the Board and Cell objects. By creating classes to
extend board and cell, we were able to reduce the amount of code in each class to that code that is directly specific to
that type of class, whether it be cell shape, or game type. By extending the cell class, and using that implementation,
it allowed us to more easily add new game types, by either using one of the existing types if applicable,
and if not by creating a new subclass that only overrides the necessary functions. This will also address many of the
instances of duplicate code as adding subclasses will eliminate the need for functions that are very similar but produce
different results. For example, in Cell there are various methods for getting neighbors according to the current shape
of the cell, to mitigate this, by creating subclasses for each of the cell shapes, we were able to simply override the
getNeighbors() method with the appropriate values for that given shape. Similarly in board, the update board
functionality had multiple methods to allow for the different game rules, by adding subclasses of the board that are
specific to game type we were able to reduce the amount of code in board in general, and reduce the amount of code in
certain methods especially the number of conditionals, by using the variables we define explicitly in the subclasses.


### Assumptions: states any assumptions or decisions made to simplify or resolve ambiguities in your the project's functionality