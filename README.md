# <span style="color:orange">I2CS_Ex1</span>
**Functions in Ex1 and all of their descriptions:**

* <span style="color:cyan">f</span> - Computes the f(x) value of the polynomial function at x.

* <span style="color:cyan">PolynomFromPoints</span> - This function computes a polynomial representation from a set of 2D points on the polynom. this function only works for a set of points containing up to 3 points, else returns null.

* <span style="color:cyan">equals</span> - Two polynomials functions are equal if and only if they have the same values f(x) for n+1 values of x, where n is the max degree (over p1, p2) - up to an epsilon (aka EPS) value.

* <span style="color:cyan">poly</span> - Computes a String representing the polynomial function.

* <span style="color:cyan">length</span> - Given a polynomial function (p), a range [x1,x2] and an integer with the number (n) of sample points. This function computes an approximation of the length of the function between f(x1) and f(x2) using n inner sample points and computing the segment-path between them. assuming x1 < x2. This function should be implemented iteratively (none recursive).

* <span style="color:cyan">area</span> - Given two polynomial functions (p1,p2), a range [x1,x2] and an integer representing the number of Trapezoids between the functions (number of samples in on each polynom). This function computes an approximation of the area between the polynomial functions within the x-range.

* <span style="color:cyan">getPolynomFromString</span> - This function computes the array representation of a polynomial function from a String representation.

* <span style="color:cyan">add</span> - This function computes the polynomial function which is the sum of two polynomial functions (p1,p2).

*  <span style="color:cyan">mul</span> - This function computes the polynomial function which is the multiplication of two polynoms (p1,p2).

* <span style="color:cyan">derivative</span> - This function computes the derivative of the p0 polynomial function.

**Extra functions and their descriptions:**

* <span style="color:cyan">getcoefficient</span> - This function extracts the leading numeric coefficient from an algebraic term string.

* <span style="color:cyan">reverseArray</span> - Reverses the elements of the given array in place.