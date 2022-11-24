# INF421-Exact-Cover
This is a Student project by professor Vincent Pilaud 
who taught us Polytechnique's INF421 course.

The objective was to first make a proper class for polyominoes,
which contain the elements to be covered in the Exact Cover problem.
The pieces to be used in the covering are also polyominoes.
The first task was to implement their constructors and methods.
And then we had to manage to generate all polyominoes of a given size.

As the name of the project implies, the end goal was finding Exact Covers.
As such, we implemented general Exact Cover solvers, which are modular,
that is, independent of the Polyomino class, since they use Generics.
In the Polyomino class we implemented a method to get the EC problem.
The usage is simple. We construct/initialize the EC problem by providing it
with a ground set to be covered and a set of pieces to do the covering with.
We can also solve an EC problem from its representation matrix directly.
The initiliazed problem object is then solved by invoking the covers()
method.
That is, these classes can be used to solve any general Exact Cover problem.
Implementing a getEC method on your custom type parameter is recommended.
covers() returns a collection of solutions as a HashSet.
To get the total number of solutions, a simple call to .size() suffices.

The entire program was coded in Java, following the project's instructions
PDF given to us by our own professor Vincent Pilaud,
as well as the Dancing Links article by PhD D. Knuth.

We were provided with a straightforward algorithm/pseudocode on our project
description, followed by D. Knuth's algorithm, both to be implemented and
compared in efficiency and complexity.
The first solver was less memory consuming but generally slower than the
DL algorithm.

There are many test examples provided in the Test class, simply uncomment
the corresponding lines in Main, rebuild and then run the project to analyze
each of them.
The given project data is contained in the assets folder.
The examples in the Test class were manually written by us.
To be exempt of the need of Eclipse or any IDE, simple building and running
bash shell scripts were provided.
