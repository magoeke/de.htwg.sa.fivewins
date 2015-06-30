#Five Wins

###A game for Software Architecture

The game is a project for or lecture "SA" – Software Architecture. The goal of the lecture is to implement a plugin interface and embed a persistence layer. The game is forked from [de.htwg.se](https://github.com/magoeke/de.htwg.se).

###The Rules:
At first you decide if you want to play against the ai or a friend. Then you’re asked how big want the gamefield.
We recommend a size between 15 and 19. In each round you always can place one stone. After that it's your opponents turn.
The goal of this game is to get five stones in one row.
Vertical, horizontal or diagonal, all is possible. Just without an edge.

###Several Informations:
This game use the MVC-Architecture pattern. To improve our code and development process we used Sonar, Maven and Jenkins. For more informations about the skeleton look at the Readme from [de.htwg.se](https://github.com/magoeke/de.htwg.se).
Before you start the Game you can choose which database and which plugins you want to use. You can set this up over dependency injection. For dependency injection we used google guice. 

###Plugins
You have the choice between 2 Plugins. 

Plugin Name | Function
--- | ---
Turn Plugin | Every player has 2 turns in a row.
Random Plugin | Sets a random Stone in a random turn.

###Databases
You have the choice between 3 databases.
-	db4o
-	 MySQL
-	couchDB

The databases save the current gamefield. The gamefield gets saved before the next Players turn start.
