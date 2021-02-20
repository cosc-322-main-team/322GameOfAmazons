# About Monte Carlo Tree Search

## Monte Carlo Tree Search, in general

Monte Carlo Tree Search is a technique based on “the analysis of the most promising moves, expanding the search tree based on random sampling of the search space” (https://en.wikipedia.org/wiki/Monte_Carlo_tree_search). This technique seems promising because it “has been used successfully to play games such as Go, Tantrix, Battleship, Havannah, and Arimaa." (https://en.wikipedia.org/wiki/Monte_Carlo_method). All these games are strategy board game which resemble, to a varying extent, the game of Amazons.

The “Principle of Operation” section of the page https://en.wikipedia.org/wiki/Monte_Carlo_tree_search gives a good overview of how this technique works. The basic idea is to traverse the current search tree until we reach a node from which no simulated game (called playout) has been run yet. This means that node has no child, making it a leaf. Then, the leaf is expanded. The process of expansion is the following: many times (each time being one playout), the outcome of the game is simulated by picking random legal moves for each opponent starting at the node being expanded, until the game ends. Then, depending on the outcome, a step of backpropagation is run, where the value of each node is updated. The value of the nodes is increased if the simulation resulted in a win for that player, and decreased if it was a loss.

The diagram below helps understand the mechanics of that technique.

![A digram showing the four steps of Monte Carlo Tree Search](https://upload.wikimedia.org/wikipedia/commons/thumb/2/21/MCTS-steps.svg/2880px-MCTS-steps.svg.png)

## Monte Carlo Tree Search, as applied to the Game of Amazons

We found [a paper] which seems to be especially well-suited to our needs (https://dspace.jaist.ac.jp/dspace/bitstream/10119/8867/7/paper.pdf). In it, the author, Julien Kloetzer, dedicates a whole section to applying the MCTS method to the game of Amazons. In that section, before detailing his algorithm, he states that: "[I]t is possible to apply Monte-Carlo with a good confidence to compute a broad number of values" (Kloetzer, 20). This gives us hope as to the potential of using the MCTS method for our project.
The author then explains his algorithm. While the details of it are beyond the scope of this report, we expect to inspire our final algorithm off of the pseudocode he provides on page 21, which we have copied below.

(NOTE TO GUY: Can you please replace this note with a screenshot of the algorithm in the pdf we are handing in?)

What the algorithm above does is the following. It starts at a given position, which is a game state. Then, it iterates over all possible moves m from that position. For each move, which results in a position P, it then plays N number of games, where N is an input to the algorithm. For each playout, it picks random moves starting from the same position P. Every time it reaches the end of a playout, it increments the value of the move m. The amount by which the value of the move m is incremented depends on whether the playout resulted in a win, a loss, or a draw. Finally, it divides the value of the move m by the number of simulated games, so that the value of the move m becomes a probability of winning. Finally, it plays the move with the highest probability of winning.

# Other techniques we considered using, and why we decided to go with MCTS

Another technique we have considered using is the min-distance evaluation function detailed in [Dr. Martin Muller's paper](http://library.msri.org/books/Book42/files/muller.pdf). This technique is interesting and, according to the author, effective: "A similar pattern became apparent in the Second Computer-Amazons championship in which [our program] participated and took third place". As the author explains, however, this algorithm also has disadvantages: "However, against aggressive human players and programs, such as Jens Lieberum’s Amazong, Arrow gets into trouble quickly". For this reason, we thought it would be interesting to explore using Monte Carlo Tree Search.
Additionally, there does not seem to be a very large amount of information to be found online when searching for uses of the Monte Carlo Tree Search method to play the Game of Amazons. We managed to find two papers in addition to the aforementioned one: https://link.springer.com/chapter/10.1007/978-3-540-87608-3_2 and https://www.sciencedirect.com/science/article/pii/S1877050915025417, and that second one actually applies the MCTS method to a variant of the game called Knight-Amazons. Therefore, we thought it would be interesting to give this technique a try.
