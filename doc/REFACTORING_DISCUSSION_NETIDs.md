**1. List as many design issues as you can in the method (using line numbers to identify the exact code) from large things 
like (potential) duplicated code or if statements that should be generalized through polymorphism, data structures, or 
resource files down to medium sized things like poor error handling or long lambdas methods to small things like 
consistent coding conventions or ignored assignment design requirements (like using Resources instead of magic values). 
For many of these methods, this should be a long list of issues!**
*First method:  CSVParser.determineNeighborType()*
This method's purpose is to get the "neighbor type" for that game based on the name of the game that is read in from the
CSV. After basic, we actually made this method much shorter, so now, it's only 25 lines (instead of 42). The biggest
thing that is wrong with this method would be its extremely large number of if statements, but we didn't really know
how else to determine the neighbor type we wanted for that simulation other than to use many if statements and accordingly
get the neighbor type.

*Second method: Board.getRandomIntFromBound*
This method is just two lines; it simply gets a random integer with a maximum bound.

*Third method: RulesParser.getRulesFromLine*
This method is quite long because of all the different possibilities/games/rules that can be present in a certain rules
file. For instance, a significant portion of the function just splits the line of the text file up and appropriately
interprets the different values, such as the start state, required neighbors, and end state. To make this function
slightly shorter, we could potentially have one function that parses each line, and a separate function that takes the 
scanned values and generates rules from them. However, this isn't too major of a design fix, so we decided to focus on
more high-level design changes for this lab.  

**2. Organize the list of issues based on things that could be fixed together based on a different design choice or using 
similar refactorings and prioritize these groups based on which would provide the most improvement to the overall code 
(not just this method).**
We honestly didn't think that these long methods were indicative of the issues that were present in our code. Rather, we
found that our biggest issue was probably our lack of abstractions in the project, especially with the Cell and Board
classes, so we thought that those two would be the best places to start.


**3. Describe specific overall design changes or refactorings to fix the three most important issues you identified.**
The first change that we thought we could implement to improve the design of our code would be to re structure the Cell
class. Currently, it's a single class with a variety of functions for each different kind fo shape the cell could take on,
and given the potential for repetition that this structure created, we thought it would be best to decompose the Cell
class into three subclasses: a rectangular cell, a rhombus cell, and a hexagonal cell.

The second change would be to decompose the Board class into four boards for the four different game structures that
the board would be adapted for: one for segregation and games of the same type, one for  fire, one for predator and prey,
and one for everything else. This is because these three different game types have three different processes that are 
used to regenerate the board based on how the cells interact with each other, so we thought that it would be best to 
have four different boards to reflect that.


**What we refactored:**
First, we addressed the issues apparent in the simulation checklist. First to get rid of all of the magic values in the 
code, we added final variables to replace them. We still need to completely remove these variables, and directly pulled 
from the properties file, but, currently the code does not take in a resource bundle as the argument in all the 
necessary places to implement this. Then I removed all unnecessary static methods and concrete data structures. 

Then, we discussed how to improve the level of abstraction in our design. Although it took a bit of discussion to figure 
out which implementation would be best, we decide that creating classes to extend board and cell, would be helpful to 
reduce the amount of code in each class to that code that is directly specific to that type of class, whether it be cell 
shape, or game type. By extending the cell class, and using that implementation, it allows us to more easily add new 
game types in the future, by either using one of the existing types if applicable, and if not creating a new extension 
that only overrides the necessary functions. This will also address many of the instances of duplicate code as adding 
subclasses will eliminate the need for functions that are very similar but produce different results. For example, in 
Cell there are various methods for getting neighbors according to the current shape of the cell, to mitigate this, by 
creating subclasses for each of the cell shapes, we can simply override the getNeighbors() method with the appropriate 
values for that given shape. Similarly in board, the update board functionality has multiple methods to allow for the 
different game rules, by adding subclasses of the board that are specific to game type we will be able to reduce the 
amount of code in board in general, and reduce the amount of code in certain methods, by using the variables we define 
explicitly in the subclasses. Though we weren't able to finish this completely, we developed a solid strategy on how to
approach this refactoring, and we plan to do it soon.

We also worked on changing the flow of how model, view and controller components of the code interact with each other. 
So before, an input was made to gui, and view would fix itself and then call the controller to modify the data. However, 
we changed this to view getting input, calling the controller to take care of the input, model modified and then model 
senses change and notifies the view to update on its own.

Finally, we also reconfigured the way in which cell shapes are read in from the properties file to make the flow of
information clearer.