simulation
====

This project implements a cellular automata simulator.

Names: Jaiveer Katariya, Kyle Harvey, Jongho Shin

### Timeline

Start Date: Feb 17, 2019

Finish Date: March 8, 2019

Hours Spent: 100 in total

### Primary Roles
Jongho: Visualization/GUI components
Kyle: Rules and implementation of games
Jaiveer: Configurations/file interaction

### Resources Used
Java API/Docs, various JavaFX tutorials, Stack Overflow
https://www.vogella.com/tutorials/JavaAlgorithmsShuffle/article.html
https://www.vogella.com/tutorials/JavaAlgorithmsShuffle/article.html

### Running the Program

app.Main class: Located in app.controller.Main, calls launch()

Data files needed: Various configurations used for games, located in data folder which is marked as project's resources
route, including CSV files for different board layouts, properties files for different appearances/configurations, and
text files for rules for different games.

Interesting data files: The rules files are definitely our most interesting/clever data files!

Features implemented:
- Games required by project specification (Fire, Game of Life, Percolation, Predator/Prey, Rock/Paper/Scissors, 
  Segregation)
- Ability to speed up, slow down animation, advance by single step
- Ability to generate simulations with three different-shaped grids (rectangles, rhombi, hexagons)
- Ability to write one's own simulations/configurations through a GUI component or load in other, preset configurations
- Ability to render layouts based on preset grids, probabilities, or counts for specific states
- Ability to customize colors, or images of states in simulations, with/without outline

Assumptions or Simplifications:
- That the user will possess the appropriately-located data folder when running the program


Known Bugs:
None

Extra credit/features:
- Buttons also implemented in spanish

### Notes


### Impressions
Thought that the project was definitely a significant step up from the game in terms of complexity and size, but a good
introduction to MVC and other design principles we learned in class as we progressed through the project.
