##Status

**Reflect on the entire project by reviewing all the code, especially the parts you did not write. For each question, 
provide specific code examples (both positive and negative) to back up your answers.**

What makes the code well-written and readable (i.e., does it do what you expect, does it require comments to understand)?
Two things that I think made the code extremely readable are the names of the different functions we use and the order
in which they are used, especially in the model and test classes. For instance, in the Board hierarchy, perhaps the most
crucial method is the updateBoard() method, which gets each cell's new state. It's named correctly, and perhaps more
importantly, in the abstract board class, it's placed near the top since it's the function that most significantly
varies between each child in the hierarchy. Different things about fetching the board's state are separate and isolated
from this method's declaration, which helps the code to be more visually modular. Similarly, in the Cell class, a similar,
logical pattern is followed in which the ways in which state is calculated and the neighbors for a cell are gotten are
separated. After this process comes the process of updating the cell's state, which consists of finding the number of 
neighbors in state, keeping a tally, and using the assigned Rules object to accordingly calculate the next step.
In code that was written by all three of us, I feel like we consistently follow an orderly, logical pattern and layout
of our functions that clearly convey what each class and what each function within does in a grander scheme of things. 

**What makes this project's code flexible or not (i.e., what new features do you feel are easy or hard to add)?**
I think that there are various parts of this project that are very flexible, but also some that aren't super flexible, 
and I feel as though this can best be demonstrated by the steps it would require to add new configurations/options for 
reading new game information in from files. For instance, at a low level, one thing that makes our code really flexible 
is our ability to read in different CSV configurations for various games. We can easily read in new layouts for 
arrangements of cells on the board without making any changes, so this process seems mostly closed due to the CellGetter
hierarchy within the process of reading CSVs. In addition, one thing that makes our code extremely flexible is the way
in which we read in rules for different games -- through text files. We were able to, in my opinion extremely cleverly,
engineer a way to read different rules and options for various rules/games from text files. So, if a new game required
different numbers of elements in different states, this would be a relatively closed fix: we could just make the new rules
file for that game, and if it were required, make the new Board subclass with the required dynamic changes (unless it
had similar board properties to another one of our already-existent board subclasses, in which case we could just use
that board). The Rules and RulesParser class would take care of the rest and appropriately configure/set up the game.
Adding this new game may not, however, be the most flexible feature addition we could do. Adding a game
would simply consist of adding the game name as options when necessary in various if conditions, and while it is a relatively
small amount of work to do, it might not be the most ideal solution. One way we could have improved this would have been
to perhaps have different simulations designed for specific games rather than having to so regularly check the name of the
game that was being played, but nonetheless, we still believe that this process is mostly flexible. 

Another flexible element of our project is the view. Given that the visual features are extremely self-contained, adding
new panels, views, or other visual elements would be extremely isolated processes, so long as extra space was not needed
to add the new features. Even if this were the case, we could always expand the window size of the app and re arrange
some of the panels/objects if necessary, but even then, we wouldn't be modifying the hard code of these visual items
and the model behind them. 

Overall, however, as I said before, the code is definitely more flexible than not, because there are more features, in
my opinion, that would be easy to add to than not. Just to name a few beyond what has already been talked about, edge 
policies, neighbor policies, and cell shapes would all also be easy to add to, which contribute to the code's overall
flexibility. 

**What dependencies between the code are clear (e.g., public methods and parameters) and what are through "back channels" 
(e.g., static calls, order of method call, requirements for specific subclass types)? Note, you can use IntelliJ to help 
you find the dependencies within your project.**




##Overall Design

**Reflect on how the program is currently organized.**
As we were instructed to do, we followed a relatively standard MVC approach when designing this project from a high level.
In other words, our entire project is made up of three distinct components that work together - the model, or the backend,
the view, or the frontend, and the controller, the set of elements responsible for communicating between the two. In the
view package, we have all the different views that make up the primary scene and BorderPane of our project, like the BoardView
(which shows the simulation), the GraphView (which shows the number of cells in each state in the simulation), and the
different buttons that the user can use to interact with the simulation. Pretty much everything in the view is self contained,
and it interacts with the model through the controller, the package which contains the SimulationController class and the
class to start the app. The SimulationController generates new views and modifies the components on the main view through
data that is fed into it by the various classes in the model package. The model package contains everything that the user
doesn't see that goes on behind the scenes, from reading information about simulations through CSVs to managing the rules
of different simulations to making the cells that follow and change according to those rules. Within the model package
are sub-packages that describe the roles of various sets of components, like how all the processes involved in 
fetching/making Cell objects are contained within app.model.cell.  

I really think that the high level design of our project was well done. The different components that make up our app
clearly fall into one of the three categories (MVC), and all transmissions of information from the model to the view
are done through the SimulationController. I think that our model effectively uses hierarchies to take advantage
of similarities between groups of components, but make it so that the initial/foundational code of these components
doesn't have to be changed in order to diversify them. While I think we could have considered making a hierarchy
of simulations based on the game we were playing (as I mentioned above), I still think we followed the MVC approach well
and organized our elements such that our code is very modular and well-defined in its purpose.


**As briefly as possible, describe everything that needs to be done to add a new kind of simulation to the project.**
In order to add a new simulation to the project, we'd first have to make a new Rules text file to encode the rules of that
simulation and add to the Rules Parser any necessary new requirements that may come with the game. Next, depending on 
what type of simulation it was, ie, did it involve probabilities or not, were chronons  necessary or not, we might have 
to make a new Board type to accomodate the new rules and add it as an option in our SimulationController. Lastly, we'd 
have to add the game as an option to our CSVParser to ensure the simulation gets constructed correctly, make the preset
CSV files for that game, and add it as a drop down in the options for new configurations.


**Describe the overall design of the program, without referring to specific data structures or actual code (focus on how 
the classes relate to each other through behavior (methods) rather than their state (instance variables)).**
A simulation is generated with the following process. First, a properties file is chosen by the user to generate a new simulation. 
That properties file contains pretty much all the foundational information about that simulation. First, the CSV file whose 
name is specified in the properties file is read in by a CSV parser, which generates the grid of cells of the correct shape 
and basic configurations. Every step of the way, all the cells in the simulation are individually updated according to 
rules fed into each Cell object from the Rules and RulesParser classes, and once all the cells have updated their state, 
a new board is regenerated with every Cell in its updated state. Once this new board is generated, it is projected onto 
the view of the simulation, at which point the user can customize various options for the simulation's appearance, like 
the color and/or image used for each unique state. It is through this view that users can also generate and load in new 
simulations, which essentially generates a new properties file for a new simulation to be created from, following the same 
process.

**Justify why your overall code is designed the way it is and any issues the team wrestled with when coming up with the final design.**
Our code is designed the way it is because the biggest thing that we took into account was modularity and following the MVC
model, so that all of our components were relatively separate and we could take advantage of all of the advantages that 
modularity presents us with (like flexibility, ease of debugging, readability, etc). One big thing that our team wrestled
with when coming up with the final design was figuring out how to make our code flexible. With the Complete implementation,
there were not only a lot of completely new features that had to be added, but there were also a variety of older features
that had to be built upon. So, the first thing we did was figure out how we wanted to isolate different components to be
able to implement the new features, and how we could maintain the sharing of information between different components
so as to not break down at any one point. This meant keeping data structures consistent, keeping calls to functions of
various objects consistent, and making it so that the way in which information was gotten was robust and able to handle
a variety of different scenarios on both ends of the transmission. For instance, one discussion that took us a lot of time
was figuring out how we wanted to implement different grid shapes. On one hand, we knew that we wanted to make different
objects for different kinds of cells, but then we had to consider how would that affect the process of getting a cell's
new state and, more importantly, how to geometrically make a grid of different shapes when it didn't follow the rectangular
structure that our cells were laid out in. Moreover, we had to ask ourselves how we wanted to specify cell shape and how
we had to make sure that each specification of cell shape worked correctly with the rest of the simulation. Other features
prompted us to have similar discussions, especially about how we had to change something while also keeping intact the 
rest of the simulation, so that was the most common type of issue we were wrestling with. However, since we had already
laid out a solid foundation with our implementation of the MVC approach, one thing we didn't have to worry about was
where the different components fit in. Once we knew how to code everything and make our modular hierarchies, placing them
was easy and we didn't really have to worry about rearranging the flow of information between elements; we just had to
figure out how to repackage the information we were sending to fit the formats we had already been using.

##Flexibility

**Reflect on what makes a design flexible and extensible.**
Simply put, a design is flexible when adding additional features or code requires minimal changes to the code that is 
already there. For instance, with flexible code, adding a different way of being able to read a CSV to configure an
initial layout for the simulation should require minimal modifications to the code that was already written to be able
to read CSVs; instead, the bulk of the work should come from just adding new code to do that. This is best accomplished
through modularity and using hierarchies, such that the components are separate from one another and there are clearly
defined patterns by which they should interact. Requiring this pattern minimizes the difficulty in making different
subclasses within the hierarchy interact with other classes in the program, since they're all transmitting the same type
information in the same way.

**Describe what you think makes this project's overall design flexible or not (i.e., what new features do you feel it is
able to support adding easily).**
I think that the aspect of our project that most significantly contributes to its flexibility is its modularity. The
different components that perform different tasks are well defined and distinct from one another, so modifying any one
part without breaking the rest of the simulation would be relatively easy. For instance, I think the way we handle the
construction of new cells is extremely flexible, since our method of reading cells from CSVs can easily be added to,
along with the way we handle cells of different shapes: adding a triangular cell wouldn't take too much work, and would
require minimal modification to the classes that were alrready there. The visual aspect of our code is also extremely
flexible, since the view is composed of variosu methods that independently create all of our components, so adding
a new panel to display the player's name and the description of their simulation, for instance, would be very easy because
we'd just need to make that visual element and appropriately place it in the GUI (as was discussed above). One thing that
I think would be difficult to add would be to generate simulations from a new kind of file, like a text file, instead
of a properties file. This is because we have deeply rooted throughout our code dependencies on a properties file that
is fetched by the SimulationController, and pretty much all of our objects have constructors that rely on that properties
file. If we had to somehow change that, that would require re making all of our constructors to also work with a text file,
which would require heavy modifications of the code that is already there, in addition to a substantial amount of new code
to parse through that new code.

**Describe one feature from the assignment specification that was implemented by a team mate in detail:**
One feature that I'll describe that was implemented by a team mate is the graph view which shows what percentage of the
cells in the simulation are in each state. First, the current board performs the getStateData function, which gets
the counts of the cells in each state and packages it into a map. This map is then read by the SimulationController
and fed into the currently active GraphView's addToData function, which then appropriately adds the data to the graph
and regenerates it with the newly added points. The graph itself is added to the main view (or MainView) in the constructor
of the MainView that is called by the SimulationController.

**What is interesting about this code? (why did you choose it?)**
I chose to talk about this feature because even though it seemed extremely complicated to implement at first (at least
it did to me), it ended up being relatively simple. This was for three reasons. Firstly, it ended up being simple because
our code was modular enough that we knew exactly where to get the data about the board from, since the Board class
had the well defined purpose of keeping track of the states of cells in the simulation. This made life easy because we
already had the tools necessary to gather the data; all we had to do was collect it and display it. Secondly, it ended up being a
simple implementation because of how well we followed the MVC approach. We already had a well-defined pathway by which
the model interacted with the view: the SimulationController. So, it was easy to figure out where the interaction between
the Board and the GraphView should take place, since the SimulationController was already handling so many similar kinds
of interactions. Thirdly, and finally, this aspect of the code was easy to implement because the view was so flexible
and modular. We already had a bunch of open space on the right side of the app, so Jongho's decision to put the GraphView
in the RightView, in which the user could also choose the color/images of the Cells, was an intuitive one. Once the GraphView
was created, it just had to be added to the RightView, which was already in the MainView, so minimal amounts of the code 
that was already there had to be modified. More specifically, pretty much none of the other visual components needed
to change as a result of the GraphView, which demonstrates how flexible and modular this part of our code was.

**Describe the design of this feature in detail? (what parts are closed? what implementation details are encapsulated? 
What assumptions are made? do they limit its flexibility?)**
I chose to talk about this feature because of how well it fit in with our program's design, so in order to discuss how
it fit in well, I had to discuss its design, which can be found above. But, to reiterate the central points, the GraphView
itself is definitely closed, so if we wanted to add new features to the view around the GraphView, it most likely
wouldn't require any modifications; this is practically a given due to its isolated, modular implementation. The app's
view itself also features a closed/flexible design, which, again, is why it was relatively simple to add the GraphView;
we didn't have to change much around it. We did make the assumption that there would only be three states that the Cells
could occupy, which limits its flexibility since we only check and display the the percentages of three states. If we had
to increase the number of states, the fix wouldn't be too significant. For instance, we could just dynamically add new 
XYSeries objects for the other possible states as they came up in the list and then add them to the graph, which could
allow our graph to handle an unlimited number of states.


**How flexible is the design of this feature? (is it clear how to extend the code as designed? What kind of change might 
be hard given this design?)**


##Your Design

**Reflect on the coding details of your part of the project.**

**Describe an abstraction (either a class or class hierarchy) you created and how it helped (or hurt) the design.**

 
**Discuss any Design Checklist issues within your code (justify why they do not need to be fixed or describe how they 
could be fixed if you had more time). Note, the checklist tool used in the last lab can be useful in automatically finding 
many checklist issues.**


**Describe one feature that you implemented in detail:**


**Provide links to one or more GIT commits that contributed to implementing this feature, identifying whether the commit 
represented primarily new development, debugging, testing, refactoring, or something else.**


**Justify why the code is designed the way it is or what issues you wrestled with that made the design challenging.**


**Are there any assumptions or dependencies from this code that impact the overall design of the program? If not, how 
did you hide or remove them?**


##Alternate Designs
**Reflect on alternate designs for the project based on your analysis of the current design.**


**Describe two design decisions made during the project in detail:**


**What alternate designs were considered?**


**What are the trade-offs of the design choice (describe the pros and cons of the different designs)?**


**Which would you prefer and why (it does not have to be the one that is currently implemented)?**
