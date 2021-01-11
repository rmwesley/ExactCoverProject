# INF421-Exact-Cover
The current version has several file path issues (It uses my local machine's paths to other archives)

This is a Student project by professor Vincent Pilaud who taught us during Polytechnique's INF421 course.
The objective was to make a generator for Polyominos from some given data as well as a generator for an exact cover problem matrix and its solver.
This leaves us with 3 steps to generate the problems and arrive at their solution.

The entire program was coded in Java, using as instructions the Dancing Links article of D. Knuth and also a more straightforward, less memory consuming but generally less efficient solution, based on a pseudo code from our project description.
The generator of the Polyominoes was coded by my colleague Sami Amrani.
The project provided us the files Image2d.java and an illustration ExampleImage2d.java for us to visualize our Polyominoes from their grids and check them.
The generator of the exact cover problem along with it's solver was coded by me, Wesley Rodrigues Machado.

The generator of the exact cover problem takes as entries a Polyomino P to be covered and a collection of polyominoes L that should cover it.
The Polyomino's class included some generators for these collections, that should differentiate those that allow rotation of polyomino pieces and those that don't.





It's also important to note that we interacted with each other's codes.
For example, the methods .toMatrix, .GridECSolution and .GridDLSolution were made by me and included in the Polyomino class.
The method .toMatrix took in a collection of polyominoes and returned a matrix representing the exact cover problem of 'this', which in turn one of the two solvers, EC or DL, took as entry.
This ensured that the solvers were generalistic and independant from the Polyomino class, treating only the matrix of the problem.
Thus, if this code were to the used in another application, we would only need to code a "translator" of any given problem to its problem matrix, which could be normally fed to the solver.

There is also a class ExactCoverList, which isn't important to the project, but I independently coded it to analyse its performance (For fun, to be honest, but yeah).
This solver doesn't use matrixes as entry, it instead takes in a big piece X (collection of integers) to be covered and a collection C of other pieces to cover it.
The idea is the same, but without the need of matrixes, instead just using a set of pieces to cover a bigger one.





The code returns a collection of solutions as a HashSet.
To get the total number of solutions, i is only needed to make a call to the .size() method.

There are some test examples provided in the Test file, which can be analysed from the MainClass by simply decommenting the lines (removing the slashes //) and running it.
The given data is contained in the file polyominoesINF421.txt. The examples in the Test class were manually written by us.
