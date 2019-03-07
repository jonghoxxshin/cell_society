**1. List as many design issues as you can in the method (using line numbers to identify the exact code) from large things 
like (potential) duplicated code or if statements that should be generalized through polymorphism, data structures, or 
resource files down to medium sized things like poor error handling or long lambdas methods to small things like 
consistent coding conventions or ignored assignment design requirements (like using Resources instead of magic values). 
For many of these methods, this should be a long list of issues!**


**2. Organize the list of issues based on things that could be fixed together based on a different design choice or using 
similar refactorings and prioritize these groups based on which would provide the most improvement to the overall code 
(not just this method).**


**3. Describe specific overall design changes or refactorings to fix the three most important issues you identified.**



** What we refactored: **
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