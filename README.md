# <span style="color:orange">I2CS_Ex1 - Static Functions on Polynomials</span>


**Course:** Introduction to Computer Science (Ariel University, 2026)  
**Assignment:** Ex1 – Basic programming: static functions, arrays, and JUnit.

## <span style="color:red">O</span><span style="color:blue">v</span></span><span style="color:red">e</span></span><span style="color:blue">r</span></span><span style="color:red">v</span></span><span style="color:blue">i</span></span><span style="color:red">e</span></span><span style="color:blue">w</span>
This project implements a set of **static functions** over 1D arrays representing polynomials and provides **JUnit tests** for each function.  
The given GUI (`Ex1_GUI.java`) uses `StdDraw` to visualize the polynomial and numeric computations.


## Files in the Project
- `Ex1.java` – Implemented static functions (editable)
- `Ex1Test.java` – JUnit tests for all functions (editable)
- `Ex1_GUI.java` – GUI for visualization (do not modify)
- `StdDraw.java` – Graphics helper (do not modify)

## *Functions in Ex1 and all of their descriptions:*

* `f` - Computes the f(x) value of the polynomial function at x.

* `PolynomFromPoints` - This function computes a polynomial representation from a set of 2D points on the polynom. this function only works for a set of points containing up to 3 points, else returns null.

* `equals` - Two polynomials functions are equal if and only if they have the same values f(x) for n+1 values of x, where n is the max degree (over p1, p2) - up to an epsilon (aka EPS) value.

* `poly` - Computes a String representing the polynomial function.

* `length` - Given a polynomial function (p), a range [x1,x2] and an integer with the number (n) of sample points. This function computes an approximation of the length of the function between f(x1) and f(x2) using n inner sample points and computing the segment-path between them. assuming x1 < x2. This function should be implemented iteratively (none recursive).

* `area` - Given two polynomial functions (p1,p2), a range [x1,x2] and an integer representing the number of Trapezoids between the functions (number of samples in on each polynom). This function computes an approximation of the area between the polynomial functions within the x-range.

* `getPolynomFromString` - This function computes the array representation of a polynomial function from a String representation.

* `add` - This function computes the polynomial function which is the sum of two polynomial functions (p1,p2).

* `mul` - This function computes the polynomial function which is the multiplication of two polynoms (p1,p2).

* `derivative` - This function computes the derivative of the p0 polynomial function.

## *Extra functions and their descriptions:*

* `getcoefficient` - This function extracts the leading numeric coefficient from an algebraic term string.

* `reverseArray` - Reverses the elements of the given array in place.

* `calctrianglearea` - Calculates the area of a triangle using its height and base length.

* `calctrapezoidarea` - Calculates the area of a trapezoid using its height and the lengths of its two bases.

* `getxpower` - This function extracts the power of x from an algebraic term string.



## *Result:*

![result.png](images/result.png)