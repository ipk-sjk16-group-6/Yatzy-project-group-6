# Yatzy-project-group-6
Group project for group 6, collaboration between sjk16 and ipk16

## First Merge 05/12/16

Working playerclass fairly done.
In current version creates a dynamic playerlist and adds player 1-6 in index 0-5.

Implementation:

In class Player:
Use setPlayerName() to prompt for name input, use getPlayerName() to get the name.

In class Model:
use setNumberOfPlayers() to create 1-6 objects of class Player

use getNumberOfPlayers() to get a int containing the number of players created

use fillPlayerList() to create a dynamic list filled with created players

creation is currently handled in Controller constructor
