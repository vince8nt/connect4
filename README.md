# connect4
Player vs computer connect-4.
Computer's AI uses Depth First Search with depth 8.
When the 8th move is reached without a guaranteed win or loss,
a non-recursive position rating method is used.

Position rating method:
This method uses common connect-4 strategy based around
1-off 4 in a rows to get a rough
estimate of who the position favors, and by how much.
1-off 4 in a row: (_XXX, XX_X, etc)
If multiple moves can guarentee the same score based on
1-off 4 in a rows, the positions are compaired by average
distance of pieces to the center (col 4). The more centralized
the pieces for a given side, the better.
