# Double-Half-Moon
Two arcs, or “half moons”are created with a user-inputted width and radius by generating 2000 points for each half moon on the graph. Perceptrons are then trained with the coordinates of these points to produce desired linear lines separating the half moons. 

Two new sets of half moons are generated with the same width and radius. This test set uses the trained perceptron to generate a linear line to separate the half moons. However, since this line may not be perfect, everything above and below the line are painted with different colors to visualize the error.
