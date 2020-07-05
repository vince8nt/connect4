# connect4
Player vs computer connect-4.
Computer's AI uses Depth First Search with depth 8.
When the 8th move is reached without a guaranteed win or loss,
a non-recursive position rating method is used.
This method uses common connect-4 strategy to get a rough
estimate of who the position favors, and by how much.
