

##
<<<<<<< HEAD
How does a app.model.cell.Cell know what rules to apply for its simulation?

    The cell will be an abstract class with a method that can be called to determine which rule to apply dependent on the neighbors.
##
How does a app.model.cell.Cell know about its neighbors? How can it update itself without affecting its neighbors update?

    app.model.cell.Cell is able to access the neighbors through assigned instance varibales when instantiated.
=======
How does a app.Cell know what rules to apply for its simulation?

    The cell will be an abstract class with a method that can be called to determine which rule to apply dependent on the neighbors.
##
How does a app.Cell know about its neighbors? How can it update itself without affecting its neighbors update?

    app.Cell is able to access the neighbors through assigned instance varibales when instantiated.

##
What behaviors does the Grid itself have? How can it update all of the Cells it contains?

    Update cell state as another instance variable that is associated with the cell.
##
What information about a simulation could be given in a configuration file?

    Specify how many states there, are and the rules for the given interaction and egde cases, size of simulation.
##
How is the GUI updated after all the cells have been updated?

    Have a class for the board that is updated by updating the cells, then give the GUI only access to the board, and use
    some timeline and an elapsed time parameter to step the GUI. Also have a value for old state and new state for updating
    all the cells then all the nodes