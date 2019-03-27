CompSci 307: Simulation Project Analysis
===================

> This is the link to the [assignment](http://www.cs.duke.edu/courses/compsci307/current/assign/03_simulation/):

Design Review
=======

--------------------------------------------------
### Status

#### What makes the code well-written and readable
While coding this program, we tried hard to make sure that the code stays readable. We tried to break up all the methods as much as we can to give single
responsibility to a single module. Also we tried to make our method names short and intuitive while keeping it descriptive. Also we packaged our codes to make it
easier to understand the overall structure of our code. It is divided up into four packages, model, view, controller and tests. Each of the names
correspond to TDD development style and MVC framework which gives an external reader of codes a general idea which component would be located at which of the packages.
Also we tried to encapsulate related features closer to each other. If you see in our model package, we have three sub-packages that has related classes such as classes that are
related to board features and cell package has all the classes that are related to cell component of the model. However, I do think that some of our classes and methods can be shorter if we had
implemented better encapsulation. For example, currently the SimulationController is about 340 lines, including comments and javadocumentation. This could have been reduced to
about hundred lines if I had used appropriate inheritance or abstraction, or taken different approach in designing interaction
between the view component and model component. I will elaborate on this later. 
One other thing that make the codes in the view component easy to read is how its nested and encapsulated to meet composite pattern. Each classes are separated by the portion of the view they are responsible
of. However, I think there is a lot of room for improvement in this component in terms of encapsulation and readability. I will also elaborate this later on in this analysis.
#### What makes this project's code flexible or not
There are certain components that makes this code flexible and others that decreases the flexibility of the program. 

One of the features that make the project's code more flexible is the abstraction that is done in the model package. We have abstract class Board.java and Cell.java that
constitute the basis for possible variation and extension on creating different types of board and cells. Also we have adhered to the heuristic of having no public instance variable.
These two features combined helped us adhere to the open-closed principle--this has helped our code to increase in flexibility. 
```java
public abstract class Board {
    private Cell[][] cells;
    private int myWidth;
    private int myHeight;
    private String myGame;
    private int neighborType;
    private final int[] orderToReplace = {2, 1, 0};
    private CSVParser myParser;
    private String edgePolicy;
    }
    
public abstract class Cell{
    private int type;
    private int myX;
    private int myY;
    private int[][] neighbors;
    private int myState;
    private int boardHeight;
    private int boardWidth;
    private int currentChronons;
    private int maxChronons = 10;
    private int currentEnergyLevel;
    private int edgeType = 0; //0 = torodial, 1 = finite, 2 = torodial-flipped
    private GridShapeType myGridShapeType;
    private boolean reproductionFlag = false;
    private final int NUM_CARDINAL_NEIGHBORS = 4;
    private final int NUM_ALL_BUT_LEFT_NEIGHBORS = 5;
    private final int NUM_NORMAL_NEIGHBORS = 8;
    private final int CARDINAL = 2;
    private final int ALL_BUT_LEFT = 3;
    private final int RULES_TYPE_WITH_CHRONONS = 4;
    }
    
public abstract class CellGetter {
    private Cell[][] myCells;
    String csvName;
    private String myType;
    private int myHeight;
    private int myWidth;
    private int maxState;
    private int neighborType;
    private GridShapeType myGridShapeType;
    private String gameName;
    private int errorStatus;
    private String errorType;
    private double[] myProbs;
    private double[] myCounts;
    private int edgePolicy;
    }
    
```
However, looking back at some of the instance variables, I do believe that the abstract Cell.java could have been shorter if it had less instance variables and methods.

Another aspect that makes the overall design flexible is the separation between the view and the model. In the previous project(game) one of the design mistakes I made was making components that was
responsible for both view and the model (with excessive use of the imageView class in javafx package). Having the view and model combined made adding
different view components later on harder as the project progressed. However, this time, we did it differently and tried to have the view and the model as
separated as possible starting from earlier stage of development. As a result, if we were to make a change that affects only the visual aspect of the program, we would only have to modify the classes in view package.
Similarly, if we want to change something in the model, then we modify the model package and leave the view package intact. By having two components separated we were able
to end up with a code base that is self-contained and concise, easy to modify even towards later end of the procject.

#### What dependencies between the code are clear

One of the aspects that we have failed in designing this program is that we were unable to contain the number of dependencies between different classes.
Although most of the classes have relatively 
  

You need to put blank lines to write some text

in separate paragraphs.

You can put blocks of code in here like this:
```java
    /**
     * Returns sum of all values in given list.
     */
    public int getTotal (Collection<Integer> data) {
        int total = 0;
        for (int d : data) {
            total += d;
        }
        return total;
    }
```

Emphasis, aka italics, with *asterisks* or _underscores_.

Strong emphasis, aka bold, with **asterisks** or __underscores__.

Combined emphasis with **asterisks and _underscores_**.


You can put links to commits like this: [My favorite commit](https://coursework.cs.duke.edu/compsci307_2019spring/example_bins/commit/33a37fe42915da319f7ae140c8e66555cf28d2c8)


--------------------------------------------------
### Overall Design

You can also make lists:

* Bullets are made with asterisks

1. You can order things with numbers.

#### As briefly as possible, describe everything that needs to be done to add a new kind of simulation to the project.
#### Describe the overall design of the program, without referring to specific data structures or actual code
#### Justify why your overall code is designed the way it is and any issues the team wrestled with when coming up with the final design.


--------------------------------------------------
### Flexibility

#### Describe what you think makes this project's overall design flexible or not
#### Describe one feature from the assignment specification that was implemented by a team mate in detail:
What is interesting about this code? (why did you choose it?)
Describe the design of this feature in detail? (what parts are closed? what implementation details are encapsulated? what assumptions are made? do they limit its flexibility?)
How flexible is the design of this feature? (is it clear how to extend the code as designed? what kind of change might be hard given this design?)



You need to put blank lines to write some text

in separate paragraphs.

--------------------------------------------------
### Your Design


#### Describe an abstraction (either a class or class hierarchy) you created and how it helped (or hurt) the design.
#### Discuss any Design Checklist issues within your code (justify why they do not need to be fixed or describe how they could be fixed if you had more time).
#### Describe one feature that you implemented in detail:
Provide links to one or more GIT commits that contributed to implementing this feature, identifying whether the commit represented primarily new development, debugging, testing, refactoring, or something else.
Justify why the code is designed the way it is or what issues you wrestled with that made the design challenging.
Are there any assumptions or dependencies from this code that impact the overall design of the program? If not, how did you hide or remove them?


You need to put blank lines to write some text

in separate paragraphs.


--------------------------------------------------------
### Alternate Designs



Here is another way to look at my design:

![This is cool, too bad you can't see it](crc-example.png "An alternate design")