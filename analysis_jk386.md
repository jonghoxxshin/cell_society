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
I'll begin by discussion of dependencies within the code at the highest level, the view. Beyond standard Java libraries,
and of course, JavaFX, the view relies on three central elements: the SimulationController, the class responsible for the
Control aspect of our MVC approach, the Board, which is responsible for holding data about the cells that are then projected
onto the BoardView, and of course, the Cell class, which holds information about each cell, like its shape/type and state
so as to assign it the correct color and correctly draw the correct polygon on the BoardView. Within the view package itself,
the ControlView class, which is responsible for providing the user with visual tools to control the current simulation, 
launches a NewConfigView when the user wishes to create a new simulation and uses the SpeedSlider class to configure
the rate at which the simulation steps through when it is played. The RightView class, which is responsible for configuring
visual options like the colors/images for each state as well as displaying information about the number of cells in each 
state, does the former through the BoardView class and the second by creating a new GraphView. Finally, the MainView
class puts all these visual components together, and thus has dependencies on the BoardView, the ControlView, the GraphView,
and the RightView.

Next, the Control, which is mostly performed by the SimulationController class, relies on the Simulation and PropertiesFileWriter
classes. It uses Simulation objects to generate new simulations/boards as they are loaded in through properties files,
and it uses the PropertiesFileWriter class to write new configurations when the user opts to do so through the GUI elements.

The majority of interdependencies between classes in our project is within the model package. At the initial state of
setting up a configuration is the CSV parser, which creates cells based on the configuration specified by the CSV referenced
in the properties file used to create the simulation. The CSV parser has dependencies on the GridShape and GridShapeType
classes so that it can read in and create cells of a variety of shapes, and it also depends on the abstract Cell class since
it's making new Cell objects according to the configuration outlined in the CSV. The CSVParser also depends on the different
CellGetter objects, which create an initial layout of Cells according to different specifications, like a complete grid,
a set of probabilities of states that the cell may fall into, or a fixed count of states that there must be for the states.
The different CellGetter subclasses are all based on the abstract CellGetter class, so that hierarchy contains a dependency
in that there is a fixed set of criteria that the subclasses need to contain like, perhaps most significantly, the getCells()
function.

The Rules class depends on the RulesParser class to be able to parse the text file that specifies the rules for the given
configuration, and both the Rules class and the RulesParser class depend on the State class, which makes sense given that 
they are responsible for the process of calculating and assigning a cell's state based on a set of specific criteria.

The abstract cell class depends on the GridShape and GridShapeType objects so that each cell has a possible grid shape 
associated with it, along with the State class, so that every cell can also keep track of its own state and the properties 
associated with it. The children of the abstract cell class also have dependencies on the GridShape and GridShapeType objects, 
which makes sense given that the primary way in which the children differ is in their grid shape. Moreover, the subclasses
in this hierarchy must all follow the criteria specified in the abstract Cell class, primarily that they should have the appropriate
functions to get their neighbors of different kinds, which forms a dependency between the parent class and its children, as
does the fact that the children classes use methods defined in the parent class to correctly fetch its neighbors. The Cell
class also uses the Board that it is a part of to fetch the states of its neighbors, and uses a Rules and RulesParser object
to calculate its next state based on the states of its neighbors.

The different board classes all have dependencies upon the Cell class and its subclasses, which makes sense given that
the different Boards need to be able to hold grids of Cells of every kind of shape and appropriately track information
about all the Cells they may contain. They also depend on the CSVParser, since the initial layout of cells and their
states are determined by the CSV that is used for the simulation, and they depend on the Rules class to correctly
calculate the new states of all its cells. 

Finally, the Simulation class is responsible for putting the components together to be rendered on the view, and to do so,
it depends on the Board and Cell classes, and it also uses a Rules object for the game being played by which to refresh
the board when it advances a step and the cells attain their new states.



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
This feature is quite flexible, apart from the one issue that I pointed out above, that it does not easily allow for a
change to be made in which we include simulations with more than three possible states. As was said
above, fixing this wouldn't be too difficult, since we'd just have to modify how the array of states was read in and
how that data was added to the XYSeries objects in the graph.

##Your Design

**Reflect on the coding details of your part of the project.**
I worked mostly on the model of the project, with my biggest contribution to the visuals being the geometric layout
of points of polygons used to create grids of different shapes (and implementing the feature of using different grid
shapes as a whole). I think that overall, I did a good job with the design of my parts of the project, as I was able
to effectively use hierarchies when adding new features to the project, like I did with the different kinds of Cells. 
Of course, certain classes that I made like the PropertiesFileWriter and the CSVParser didn't need to be part of hierarchies, 
so I didn't make hierarchies, but at least in the latter, I tried to take advantage of a hierarchy with the different
kinds of CellGetters.

**Describe an abstraction (either a class or class hierarchy) you created and how it helped (or hurt) the design.**
One hierarchy that I made that I think helped the design was the CellGetter hierarchy. This helped the design because
it fulfilled the feature of being able to set up initial configurations for cells of many types, namely the explicit
grid, probabilities for states, and counts for states. Moreover, this hierarchy did so in such a way that we could easily
introduce new ways to generate an initial configuration, since all we would need to do is make a new kind of CellGetter
object with the getCells() function, create a CSV that specified to generate cells with that new method, and add it to the
possible strings that the CSVParser could encounter in the CSV to correctly instantiate that new CellGetter.  
 
**Discuss any Design Checklist issues within your code (justify why they do not need to be fixed or describe how they 
could be fixed if you had more time). Note, the checklist tool used in the last lab can be useful in automatically finding 
many checklist issues.**
Though it wasn't explicitly mentioned in the Design Checklist, but rather in class, our biggest "issue" would be the
frequent use of a getCells() method in our program. When it could be done, we used a function to instead get the cells
at a specific index, but at certain points, as a group, we agreed that it made much more sense to transfer the entire
board between classes rather than reconstruct it cell by cell. Not only did this make the code more readable and intuitive,
as it was easier to track the flow of information, but perhaps most importantly, bringing the board in all at once allowed
us to refresh the entire board rather than refresh the simulation cell-by-cell, which would have made for an extremely
strange visual.

**Describe one feature that you implemented in detail:**
One feature that I implemented was generating grids of different shapes. The first step that this required was figuring
out how to actually project the shapes onto the grid, since the geometric layout of a grid of rhombuses and hexagons
wasn't exactly intuitive. After a bunch of meticulous geometry and spending too much time staring at drawings of how I wanted
the grid to look, I figured out a mathematical formula for the coordinate values of the points of both polygons relative
to the size of the entire grid and their position within the grid. One crucial realization throughout this process was
that with both rectangles and hexagons, I could look at it as a series of vertical columns which every other column
offset downwards by a portion of the height of the shape of each cell, which helped with the process of converting this
2d array into a non-rectangular grid. After figuring out how to draw each of the shapes relative to their position on the
board, I had to figure out how to get the neighbors of each cell,  since unlike with rectangles, it wasn't as simple as
every combination of 2 of 3 items from +1, -1, and 0. Rather, I had to figure out the relative coordinates of, for instance,
the rhombus that looked like it was just one to the left of the current cell, which ended up actually being two columns away.
A similar process had to be done with hexagons, and once I figured that part out, I initially put all these kinds of neighbors
into the single cell class, and assigned a string to every Cell object that represented its state. If, for instance, its
shapeString were  "hex", the Cell object would use the hexagonal relative coordinates to fetch its neighbors. But, we
soon realized that this design was not only inflexible, but cumbersome, difficult to read, and full of repeated code.
So, instead, I made a hierarchy of different Cell types, rectangular, rhombus, and hexagonal, each with its own unique set
of neighbors, that all extended a Cell abstract parent class. I then had to re work the drawing of the cell on the grid
from checking its shapeString to checking its type, and lastly, I had to add to our properties file a value that specified
what grid shape we wanted in our simulation, and I adjusted the CSVParser to produce the correct type of Cell based on that
string. 


**Provide links to one or more GIT commits that contributed to implementing this feature, identifying whether the commit 
represented primarily new development, debugging, testing, refactoring, or something else.**
One commit that I made in the middle of implementing the hexagonal grid was this one:
https://coursework.cs.duke.edu/compsci307_2019spring/simulation_team11/commit/176a52aa71fb2338a8f745bddff677a0a3b1566c

I particularly enjoyed looking at this commit because it shows how I was still making my way through the geometry-heavy/math-heavy
portion of the implementation of this feature, which was the hardest but perhaps my favorite part of it. It definitely
took a little longer than it could have, due to careless mistakes both in my math and in my typing, so there was a bit
of guesswork involved in terms of finding the final solution, but it all worked out eventually. Of the stages listed
above, this was most likely part of the new development/debugging portion of this feature.

**Justify why the code is designed the way it is or what issues you wrestled with that made the design challenging.**
The biggest issue that I wrestled with that made the design of this feature challenging was, as I mentioned above,
figuring out the geometry and the math behind drawing a grid of different shapes within a fixed board. But, as I also
mentioned above, another issue I wrestled with was making the design as clean and flexible as possible, because initially,
the design was neither of those things. It definitely took me some time to figure out what I wanted the shared methods
to be, and how I wanted to take advantage of the similarities that existed between two of the shapes but not all three
and even between all three of the shapes, because that was perhaps the most important step in making the abstract class
and correctly designing the children. However, I knew that the rhombus and rectangular cells had several features in common,
including their number of neighbors, so I implemented several methods in the abstract class that I then overwrote in the 
HexCell class, for which those same processes were different. I chose to implement this feature using this kind of hierarchy
because, as I said before, it minimized repeated code and made the code significantly more readable and intuitive.  

**Are there any assumptions or dependencies from this code that impact the overall design of the program? If not, how 
did you hide or remove them?**
The different subclasses depend on the different GridShapeTypes because each type of cell has a GridShapeType associated
with it, and of course, dependencies exist between the parent class and the subclasses since they share methods and 
instance variables. This code was written with the assumption that more neighbor types would not be needed than the ones
we have already specified, but if more needed to be added, that could easily be done; the relative coordinates would
just need to be recalculated for each shape and an integer would have to be assigned to that new neighbor type to be used
when the Cell was being created by the CSV parser (this is discussed more in depth in the DESIGN.md document regarding
the creation of new features). The parent Cell class also depends on the Board and Rules/RulesParser classes because 
each cell needs those classes to correctly calculate its next state.

##Alternate Designs
**Reflect on alternate designs for the project based on your analysis of the current design.**
Overall, I think that our design was effective, especially given its modularity, which, as I've most likely made clear,
was extremely important to me personally going into this project, in part because I believe modularity was key in allowing us
to work effectively as a group and understand the project as a whole, but for other reasons as well that I've specified above.

**Describe two design decisions made during the project in detail:**
One design decision that we made at the very beginning of the project and stuck with has to do with flow of information 
about cells from file to view. At first, when we sat down to organize the project at an extremely high level, we knew that 
the first thing we had to do was have a CSV parser that could go through a configuration document and create a grid of 
cells accordingly. This grid of cells would then belong to a certain Board class that would also be responsible for delegating 
tasks to each cell, namely, delegating the  process of updating its state. This board would then finally be fed into a view 
at which point it would appear to the user. We believed that this basic flow of information from the bottom up most clearly 
reflected the MVC approach while also keeping the many processes that made up this one, larger process, as modular as possible, 
which could allow the different group members to focus on different portions of the project. This ended up being a good 
design decision, in my opinion, as we never really had to change this basic flow of information, only the components in 
between to better allow for more features.

A second design decision that we made early on that we stuck with has to do with how we formatted the rules for our games.
We knew that we had to have a format that could easily let us generate new games, and we thought that the best way to do
that was through text files. The important question was, of course, how we could turn a text file of rules into actual
code. We ended up coming to a solution where the text file would simply contain a cell's current state, the number of neighbor
states in a current state, and according to that number of neighbor states in that new state, what new state to go to for
that current cell. Of course, this was relatively easy with the game of life and with percolation, since there wasn't much
to the game other than just counts of neighbor states. But, as the games became more complex, our rules files did as well,
and we had to build on top of what we already had. So, we made the conscious design decision to stick with text files since 
it was an approach that allowed us to easily modify and add to our games, and because we believed it was the most elegant
solution to our problem, even if it was difficult to wrap our heads around at times. Once we had this RulesParser, we knew
we had to have a Rules object that could actually hold these counts (and other properties as the games became more complicated),
which is another component of this design decision that we stuck with.

**What alternate designs were considered?**
Given that we thought that the text files were a requirement, we never really considered other options. At the beginning,
we thought that we could make different Simulation sub classes for each type of game, where the rules were embedded into
those different Simulation classes, but not only would this decrease the modularity of the code, we also found it to be
quite counterintuitive and repetitive to re generate entire new simulations when the only thing that changed was the way
in which the cells interacted with each other. 

**What are the trade-offs of the design choice (describe the pros and cons of the different designs)?**
The biggest pro of using text files was that it became very easy to implement games similar to the ones we already had,
since we could just change a few numbers for the requirements of neighbor cells in certain states to generate entirely 
new games without having to touch anything else. However, a major con was the conceptual difficulty of making text files
for games as complicated as predator and prey and fire, which resulted in clean but complicated code to parse those text 
files. However, even if we had used some other method, coding out the rules for those games and others would have still
been extremely complicated, so this may not be as big of a con as it might seem. Of course, as was mentioned before, the
biggest pro to making different kinds of Simulation objects for different games would have been the lack of having to 
create a textual formula for rules. But, one major con would be the lack of modularity, which, and I seem to have stressed
this perhaps too much, was super important in my opinion. 

**Which would you prefer and why (it does not have to be the one that is currently implemented)?**
I prefer the approach that we took. Not only do I believe that it's extremely cool that we reduced these pretty complicated
games to text files, but I think the elegance of having everything to do with generating rules in their own, independent 
cluster of classes was key to the modularity, since it allowed us to easily delegate tasks to each other without having to
worry about working on the same classes. If we decided to control everything in individual simulations, I can't help but
feel as though it would have been more complicated to work on the project at the same time. For instance, it would have been
difficult for Kyle, who worked on rules, to embed those rules into each simulation while I was also figuring out how to 
generate grids of different shapes in each simulation... or something of that sort. Moreover, with our current structure,
I feel as though we were able to maintain a clean, logical flow of information from the config files to the view. With 
the rules as separate classes that dictated how the cells interacted with each other on a cellular level rather than on 
a much higher, overarching level (like at the level of the entire simulation), we were able to cleanly move the data we
got from files out to the simulation without having to constantly worry about how they interacted with each other in every
possible game.