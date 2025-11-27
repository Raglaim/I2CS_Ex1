package assignments.Ex1;

/**
 * Utilities for working with polynomial functions represented as arrays of coefficients.
 * <p>
 * A polynomial {@code f(x)} is represented as an array where the index indicates the degree, e.g.:
 * <ul>
 *   <li>{@code [c, b, a]} represents {@code f(x) = a·x^2 + b·x + c}</li>
 *   <li>{@code [0.1, 0, -3, 0.2]} represents {@code f(x) = 0.2x^3 - 3x^2 + 0.1}</li>
 * </ul>
 * </p>
 *
 * <p>
 * This class is part of:
 * <br>
 * <strong>Introduction to Computer Science 2026, Ariel University</strong><br>
 * Exercise 1: Arrays, static functions, and JUnit
 * </p>
 *
 * <p>
 * Assignment details:
 * https://docs.google.com/document/d/1GcNQht9rsVVSt153Y8pFPqXJVju56CY4/edit?usp=sharing&ouid=113711744349547563645&rtpof=true&sd=true<br>
 * Google Docs specification
 * </a>
 * </p>
 *
 * <h3>Conventions</h3>
 * <ul>
 *   <li>Coefficients are in ascending degree order (index 0 is the constant term).</li>
 *   <li>{@code ZERO} denotes the zero polynomial: {@code [0]}.</li>
 *   <li>{@code EPS} is a small positive tolerance used in numeric comparisons.</li>
 * </ul>
 * </p>
 * @author boaz.benmoshe
 */
public class Ex1 {
    /** Epsilon value for numerical computation; used as a "close enough" threshold (e.g., for root/area/length approximations). */
    public static final double EPS = 0.001; // the epsilon to be used for the root approximation.
    /** The zero polynomial function, represented as an array with a single {@code 0} entry. */
    public static final double[] ZERO = {0};





    // Functions:

	/**
	 * Computes the f(x) value of the polynomial function at x.
	 * @param poly - polynomial function
	 * @param x
	 * @return f(x) - the polynomial function value at x.
	 */
	public static double f(double[] poly, double x) {
		double ans = 0;
		for(int i=0;i<poly.length;i++) {
			double c = Math.pow(x, i);
			ans += c*poly[i];
		}
		return ans;
	}



	/** Given a polynomial function (p), a range [x1,x2] and an epsilon eps.
	 * This function computes an x value (x1<=x<=x2) for which |p(x)| < eps, 
	 * assuming p(x1)*p(x2) <= 0.
	 * This function should be implemented recursively.
	 * @param p - the polynomial function
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param eps - epsilon (positive small value (often 10^-3, or 10^-6).
	 * @return an x value (x1<=x<=x2) for which |p(x)| < eps.
	 */
	public static double root_rec(double[] p, double x1, double x2, double eps) {
		double f1 = f(p,x1);
		double x12 = (x1+x2)/2;
		double f12 = f(p,x12);
		if (Math.abs(f12)<eps) {return x12;}
		if(f12*f1<=0) {return root_rec(p, x1, x12, eps);}
		else {return root_rec(p, x12, x2, eps);}
	}



    /**
     * Computes the coefficients of a polynomial that passes through a given set of 2D points.
     * <p>
     * This method supports:
     * <ul>
     *   <li>Two points → returns a linear polynomial (degree 1).</li>
     *   <li>Three points → returns a quadratic polynomial (degree 2).</li>
     * </ul>
     * If the input contains fewer than 2 points or more than 3 points, the method returns {@code null}.
     * </p>
     *
     * <p>
     * The algorithm is based on: <br>
     * href= "http://stackoverflow.com/questions/717762/how-to-calculate-the-vertex-of-a-parabola-ge-points<br>
     * StackOverflow discussion on calculating a parabola from three points.
     * </p>
     *
     * @param xx an array of x-coordinates of the points
     * @param yy an array of y-coordinates of the points (must match {@code xx} in length)
     * @return an array of doubles representing the polynomial coefficients:
     *         <ul>
     *           <li>For two points: {@code [b, m]} where {@code y = m*x + b}</li>
     *           <li>For three points: {@code [c, b, a]} where {@code y = a*x^2 + b*x + c}</li>
     *           <li>{@code null} if input is invalid</li>
     *         </ul>
     */
    public static double[] PolynomFromPoints(double[] xx, double[] yy) {
        double [] ans = null;
        int lx = xx.length;
        int ly = yy.length;
        if(xx!=null && yy!=null && lx==ly && lx>1 && lx<4) {
            if (lx==2){
                double m = (yy[1]-yy[0])/(xx[1]-xx[0]);
                double b = yy[0] - m*xx[0];
                ans = new double[]{b,m};
            } else {
                double denom = (xx[0] - xx[1]) * (xx[0] - xx[2]) * (xx[1] - xx[2]);
                double a = (xx[2] * (yy[1] - yy[0]) +
                        xx[1] * (yy[0] - yy[2]) +
                        xx[0] * (yy[2] - yy[1])) / denom;
                double b = (xx[2] * xx[2] * (yy[0] - yy[1]) +
                        xx[1] * xx[1] * (yy[2] - yy[0]) +
                        xx[0] * xx[0] * (yy[1] - yy[2])) / denom;
                double c = (xx[1] * xx[2] * (xx[1] - xx[2]) * yy[0] +
                        xx[2] * xx[0] * (xx[2] - xx[0]) * yy[1] +
                        xx[0] * xx[1] * (xx[0] - xx[1]) * yy[2]) / denom;
                ans = new double[]{c, b, a};
            }
        }
		return ans;
	}



    /**
     * Checks whether two polynomial functions are equal.
     * <p>
     * Two polynomials are considered equal if and only if they produce the same values
     * for <code>n + 1</code> distinct points, where <code>n</code> is the maximum degree
     * of the two polynomials. Equality is determined up to a small epsilon value (EPS).
     * </p>
     *
     * <ul>
     *   <li>Compares values of both polynomials at multiple points.</li>
     *   <li>Handles cases where polynomials have different lengths by padding with zeros.</li>
     *   <li>Returns {@code true} if all compared values differ by less than EPS.</li>
     * </ul>
     *
     * @param p1 the first polynomial represented as an array of coefficients
     * @param p2 the second polynomial represented as an array of coefficients
     * @return {@code true} if {@code p1} and {@code p2} represent the same polynomial function,
     *         {@code false} otherwise
     */
    public static boolean equals(double[] p1, double[] p2) {
		boolean ans = true;
        if (p1 == null && p2 == null) {
            return ans;
        }
        if (p1.length == p2.length){
            for (int i = 0; i < p1.length; i++) {
                if (Math.abs(f(p1, i) - f(p2, i)) > EPS) {
                    return !ans;
                }
            }
            return ans;
        } if (p1.length > p2.length) {
            double [] new_p2 = new double[p1.length];
            System.arraycopy(p2, 0, new_p2, 0, p2.length);
            for (int i = 0; i < p1.length; i++) {
                if (Math.abs(f(p1, i) - f(new_p2, i)) > EPS) {
                    return !ans;
                }
            }
            return ans;
        } else {return equals(p2,p1);}
	}



    /**
     * Converts a polynomial represented as an array of coefficients into a human-readable string.
     * <p>
     * Each element in the array corresponds to a coefficient, where the index represents the degree:
     * <ul>
     *   <li>Index 0 → constant term</li>
     *   <li>Index 1 → coefficient of x</li>
     *   <li>Index 2 → coefficient of x²</li>
     *   <li>and so on...</li>
     * </ul>
     * </p>
     *
     * <p>
     * For example:
     * <pre>
     *   Input:  {2, 0, 3.1, -1.2}
     *   Output: "-1.2x^3 +3.1x^2 +2.0"
     * </pre>
     * </p>
     *
     * @param poly the polynomial represented as an array of doubles
     * @return a string representation of the polynomial, or {@code ""} if the array is empty
     */
    public static String poly(double[] poly) {
        String ans = "";
        if(poly.length==0) {return ans;}
        else {
            for (int i = poly.length-1; 0 <= i; i-=1) {
                if (poly[i] == 0) {
                }else if(i == 0){
                    if (poly[i] > 0){
                        ans = ans +" +"+ poly[i];
                    } else {
                        ans = ans +" "+ poly[i];
                    }
                    break;
                } else if (i == 1){
                    if (poly[i] > 0){
                        ans = ans +" +"+ poly[i] +"x";
                    } else {
                        ans = ans +" "+ poly[i] +"x";
                    }
                } else {
                    if (poly[i] > 0){
                        ans = ans +" +"+ poly[i] +"x^"+(i);
                    } else {
                        ans = ans +" "+ poly[i] +"x^"+(i);
                    }
                }
            }
            ans = ans.trim();
            return ans;
        }
    }



    /**
     * Finds an x-value within a given range where two polynomial functions are approximately equal.
     * <p>
     * The method searches for a point {@code x} in the interval {@code [x1, x2]} such that:
     * <pre>
     *   |p1(x) - p2(x)| < eps
     * </pre>
     * under the assumption that:
     * <pre>
     *   (p1(x1) - p2(x1)) * (p1(x2) - p2(x2)) <= 0
     * </pre>
     * which guarantees that the two functions intersect within the range.
     * </p>
     *
     * <ul>
     *   <li>Uses a recursive approach (binary search) to narrow down the interval.</li>
     *   <li>Stops when the difference between the two functions is less than {@code eps}.</li>
     * </ul>
     *
     * @param p1  the first polynomial represented as an array of coefficients
     * @param p2  the second polynomial represented as an array of coefficients
     * @param x1  the lower bound of the range
     * @param x2  the upper bound of the range
     * @param eps the tolerance for equality (e.g., {@code 1e-3} or {@code 1e-6})
     * @return an {@code x} value in {@code [x1, x2]} where {@code |p1(x) - p2(x)| < eps}
     */
    public static double sameValue(double[] p1, double[] p2, double x1, double x2, double eps) {
		double ans = x1;
        double f1 = ((f(p1,x1)-f(p2,x1)));
        double x12 = (x1+x2)/2;
        double f12 = f(p1,x12) - f(p2,x12);
        if (Math.abs(f12) < eps){return x12;}
        if (f12 * f1 <= 0){return(sameValue(p1, p2, x1, x12, eps));}
        else {return(sameValue(p1, p2, x12, x2, eps));}
	}



    /**
     * Approximates the arc length of a polynomial function over a given interval.
     * <p>
     * The method computes the length of the curve defined by {@code y = f(x)} for {@code x ∈ [x1, x2]}
     * using a piecewise linear approximation. The interval is divided into {@code numberOfSegments} equal parts,
     * and the length is calculated by summing the distances between consecutive sample points.
     * </p>
     *
     * <p>
     * Assumptions:
     * <ul>
     *   <li>{@code x1 < x2} (if not, the method swaps the bounds).</li>
     *   <li>{@code numberOfSegments > 0}.</li>
     * </ul>
     * </p>
     *
     * <p>
     * Formula for each segment:
     * <pre>
     *   distance = sqrt((Δx)^2 + (Δy)^2)
     * </pre>
     * where {@code Δx = h} and {@code Δy = f(x) - f(x+h)}.
     * </p>
     *
     * @param p the polynomial represented as an array of coefficients
     * @param x1 the lower bound of the range
     * @param x2 the upper bound of the range
     * @param numberOfSegments the number of segments to divide the interval into (positive integer)
     * @return the approximate length of the curve between {@code f(x1)} and {@code f(x2)}
     */
    public static double length(double[] p, double x1, double x2, int numberOfSegments) {
		double ans = 0;
        double h = Math.abs(x1-x2)/numberOfSegments;
        if(x1 == x2){
            return ans;
        } else if (x1 < x2) {
            for (double i=x1; i<x2; i+=h) {
                double y1 = f(p,i);
                double y2 = f(p,i+h);
                double distance = Math.sqrt(Math.pow((y2-y1),2) + Math.pow((h),2));
                ans += distance;
            }
            return ans;
        } else {return length(p, x2, x1, numberOfSegments);}
	}



    /**
     * Approximates the area between two polynomial functions over a given interval using a trapezoidal method.
     * <p>
     * The method divides the interval {@code [x1, x2]} into {@code numberOfTrapezoid} segments and computes the area
     * between {@code p1(x)} and {@code p2(x)} using a Riemann-like integral approach:<br>
     * href="https://en.wikipedia.org/wiki/RiemralRiemann.
     * </p>
     *
     * <p>
     * Algorithm details:
     * <ul>
     *   <li>For each segment, calculate the difference between the two polynomials at the segment endpoints.</li>
     *   <li>If the functions cross within the segment, split the area into two triangles using {@code sameValue()}.</li>
     *   <li>Otherwise, compute the trapezoid area using {@code calctrapezoidarea()}.</li>
     * </ul>
     * </p>
     *
     * @param p1 the first polynomial represented as an array of coefficients
     * @param p2 the second polynomial represented as an array of coefficients
     * @param x1 the lower bound of the range
     * @param x2 the upper bound of the range
     * @param numberOfTrapezoid the number of trapezoids (segments) to divide the interval into (positive integer)
     * @return the approximate area between the two polynomial functions within {@code [x1, x2]}
     */
    public static double area(double[] p1,double[]p2, double x1, double x2, int numberOfTrapezoid) {
        double ans = 0;
        double h = Math.abs(x1-x2)/numberOfTrapezoid;
        if (x1 == x2){
            return ans;
        } else if (x1 < x2) {
            for (double i = x1; i+EPS < x2; i+=h) {
                double f11 = f(p1,i);
                double f12 = f(p1,i+h);
                double f21 = f(p2, i);
                double f22 = f(p2,i+h);
                double base1 = (f11 - f21);
                double base2 = (f12 - f22);
                if ((base1 * base2) < 0){
                    double mid = sameValue(p1, p2, i, i+h, EPS);
                    double area1 = calctrianglearea(base1, mid - i);
                    double area2 = calctrianglearea(base2, mid - i -h);
                    ans += (area1 + area2);
                } else {
                    double area = calctrapezoidarea(base1, base2, h);
                    ans += area;
                }
            }
            return ans;
        } else {return area(p1, p2, x2, x1, numberOfTrapezoid);}
    }



    /**
     * Converts a string representation of a polynomial into an array of coefficients.
     * <p>
     * Each term in the string is parsed to extract its coefficient and degree, and the resulting array
     * is constructed such that:
     * <ul>
     *   <li>Index 0 → constant term</li>
     *   <li>Index 1 → coefficient of x</li>
     *   <li>Index n → coefficient of xⁿ</li>
     * </ul>
     * </p>
     *
     * <p>
     * For example:
     * <pre>
     *   Input:  "-1.0x^2 +3.0x +2.0"
     *   Output: [-1.0, 3.0, 2.0]
     * </pre>
     * </p>
     *
     * <p>
     * Note: Given a polynomial {@code p} represented as a double array,
     * {@code getPolynomFromString(poly(p))} should return an array equal to {@code p}.
     * </p>
     *
     * @param p a string representing a polynomial function (e.g., {@code "-1.0x^2 +3.0x +2.0"})
     * @return an array of doubles representing the polynomial coefficients
     */
    public static double[] getPolynomFromString(String p) {
		double [] ans = ZERO;//  -1.0x^2 +3.0x +2.0
        String[] words = p.split(" ");
        ans = new double[getxpower(p)+1];
        for (String word : words) {
            ans[getxpower(word)] = getcoefficient(word);
        }
        return ans;
	}



    /**
     * Computes the sum of two polynomial functions.
     * <p>
     * The result is a new polynomial whose coefficients are the element-wise sum of {@code p1} and {@code p2}.
     * If the polynomials have different lengths, the shorter one is padded with zeros.
     * </p>
     *
     * <p>
     * For example:
     * <pre>
     *   p1 = [2, 3]        // 3x + 2
     *   p2 = [1, 4, 5]     // 5x² + 4x + 1
     *   Result = [3, 7, 5] // 5x² + 7x + 3
     * </pre>
     * </p>
     *
     * @param p1 the first polynomial represented as an array of coefficients
     * @param p2 the second polynomial represented as an array of coefficients
     * @return a new array representing the sum of {@code p1} and {@code p2}
     */
    public static double[] add(double[] p1, double[] p2) {
        double[] ans = ZERO;//
        if (p1.length >= p2.length) {
            ans = new double[p1.length];
            for (int i = 0; i < p2.length; i++) {
                ans[i] = p1[i] + p2[i];
            }
            System.arraycopy(p1, p2.length, ans, p2.length, p1.length - p2.length);
            return ans;
        } else {return add(p2, p1);}
    }



    /**
     * Computes the product of two polynomial functions.
     * <p>
     * The result is a new polynomial whose coefficients are obtained by multiplying each term of {@code p1}
     * with each term of {@code p2} and summing terms with the same degree.
     * </p>
     *
     * <p>
     * For example:
     * <pre>
     *   p1 = [2, 3]          // 3x + 2
     *   p2 = [1, 4]          // 4x + 1
     *   Result = [2, 11, 12] // 12x² + 11x + 2
     * </pre>
     * </p>
     *
     * @param p1 the first polynomial represented as an array of coefficients
     * @param p2 the second polynomial represented as an array of coefficients
     * @return a new array representing the product of {@code p1} and {@code p2}
     */
    public static double[] mul(double[] p1, double[] p2) {
        double [] ans = ZERO;//
        if (p1 == ZERO || p2 == ZERO){
            return ans;
        }
        if (p1.length >= p2.length){
            double [] mul_p = new double[p1.length+p2.length-1];
            for (int i = 0; i < p2.length; i++) {
                for (int j = 0; j < p1.length; j++) {
                    mul_p[i+j] = mul_p[i+j]+(p2[i]*p1[j]);
                }
            }
            ans = new double[mul_p.length];
            System.arraycopy(mul_p,0,ans,0,mul_p.length);
            return ans;
        } else {return mul(p2, p1);}
    }



    /**
     * Computes the derivative of a polynomial function.
     * <p>
     * The derivative is calculated by multiplying each coefficient by its degree and reducing the degree by one.
     * For example:
     * <pre>
     *   Input:  [2, 3, 4]       // 4x² + 3x + 2
     *   Output: [3, 8]          // 8x + 3
     * </pre>
     * </p>
     *
     * <p>
     * Special cases:
     * <ul>
     *   <li>If the polynomial has length ≤ 1 (constant or empty), the derivative is {@code [0]}.</li>
     * </ul>
     * </p>
     *
     * @param po the polynomial represented as an array of coefficients
     * @return an array representing the derivative of the polynomial
     */
    public static double[] derivative (double[] po) {
        double [] ans = ZERO;//
        if (po.length <= 1){
        }else{
            ans = new double[po.length-1];
            for (int i = 1; i < po.length; i++) {
                ans [i-1] = po[i]*i;
            }
        }
        return ans;
	}





    // Extra functions:

    /**
     * Extracts the numeric coefficient from an algebraic term string.
     * <p>
     * The method parses the leading numeric part of the term, handling optional signs and decimal points.
     * If no explicit coefficient is found (e.g., {@code "x^2"}), the default value is {@code 1.0}.
     * </p>
     *
     * <p>
     * Examples:
     * <ul>
     *   <li>{@code "-1.0x^2"} → {@code -1.0}</li>
     *   <li>{@code "+3x"} → {@code 3.0}</li>
     *   <li>{@code "0.5x^3"} → {@code 0.5}</li>
     *   <li>{@code "x"} → {@code 1.0}</li>
     * </ul>
     * </p>
     *
     * @param word a string representing a single term of a polynomial (e.g., {@code "-1.0x^2"})
     * @return the numeric coefficient as a {@code double}
     */
    public static double getcoefficient(String word) {
        double ans = 1;
        int i = 0;
        char ch = word.charAt(0);
        if (ch == '-' || ch =='+'){
            i++;
        }
        while(i<word.length()){
            ch = word.charAt(i);
            if (Character.isDigit(ch)){
                i++;
            } else if (ch == '.') {
                i++;
            } else {
                break;
            }
        }

        String number = word.substring(0,i);
        if (number.isEmpty()){
            return ans;
        }
        ans = Double.parseDouble(number);
        return ans;
    }



    /**
     * Reverses the elements of the given array in place.
     * <p>
     * If the array is {@code null}, the method returns {@code null} without modification.
     * </p>
     *
     * <p>
     * Example:
     * <pre>
     *   double[] nums = {1, 2, 3, 4};
     *   reverseArray(nums);
     *   // nums becomes {4, 3, 2, 1}
     * </pre>
     * </p>
     *
     * @param arr the array to reverse; may be {@code null}
     * @return the same array with elements reversed, or {@code null} if the input was {@code null}
     */
    public static double[] reverseArray(double[] arr) {
        if (arr == null) return arr;
        int i = 0, j = arr.length - 1;
        while (i < j) {
            double temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            i++;
            j--;
        }
        return arr;
    }



    /**
     * Calculates the area of a triangle using its base and height.
     * <p>
     * The formula used is:
     * <pre>
     *   area = (base * height) / 2
     * </pre>
     * The result is always positive because the method applies {@code Math.abs()}.
     * </p>
     *
     * <p>
     * Example:
     * <pre>
     *   calctrianglearea(10, 5); // returns 25.0
     * </pre>
     * </p>
     *
     * @param base   the length of the base of the triangle (should be positive)
     * @param height the height of the triangle (should be positive)
     * @return the computed area of the triangle as a {@code double}
     */
    public static double calctrianglearea(double base, double height){
        double area = Math.abs((base * height) / 2);
        return area;
    }



    /**
     * Calculates the area of a trapezoid using its two bases and height.
     * <p>
     * The formula used is:
     * <pre>
     *   area = ((base1 + base2) * height) / 2
     * </pre>
     * The result is always positive because the method applies {@code Math.abs()}.
     * </p>
     *
     * <p>
     * Example:
     * <pre>
     *   calctrapezoidarea(10, 6, 4); // returns 32.0
     * </pre>
     * </p>
     *
     * @param base1  the length of the first base (should be positive)
     * @param base2  the length of the second base (should be positive)
     * @param height the height of the trapezoid (should be positive)
     * @return the computed area of the trapezoid as a {@code double}
     */
    public static double calctrapezoidarea(double base1, double base2, double height){
        double area = Math.abs(((base1 + base2) * height) / 2);
        return area;
    }


    /**
     * Extracts the exponent (power) of {@code x} from an algebraic term string.
     * <p>
     * The method identifies the degree of the term based on the presence of {@code x} and {@code ^}.
     * If no {@code x} is present, the power is {@code 0}. If {@code x} is present without {@code ^}, the power is {@code 1}.
     * </p>
     *
     * <p>
     * Examples:
     * <ul>
     *   <li>{@code "-1.0x^2"} → {@code 2}</li>
     *   <li>{@code "+3x"} → {@code 1}</li>
     *   <li>{@code "5.0"} → {@code 0}</li>
     * </ul>
     * </p>
     *
     * @param poly a string representing a single term of a polynomial (e.g., {@code "-1.0x^2"})
     * @return the power of {@code x} as an integer (0 if no {@code x} is present)
     */
    public static int getxpower(String poly){
        poly = poly+"  ";
        int power = 0;
        for (int i = 0; i < poly.length(); i++) {
            if (poly.charAt(i) == 'x'){
                if (poly.charAt(i+1) == '^') {
                    int x = 0;
                    double[] pow_arr = new double[0];
                    while (Character.isDigit(poly.charAt(i+2))) {
                        double[] new_pow_arr = new double[pow_arr.length + 1];
                        System.arraycopy(pow_arr, 0, new_pow_arr, 0, pow_arr.length);
                        pow_arr = new_pow_arr;
                        pow_arr[x] = Integer.parseInt("" + poly.charAt(i + 2));
                        x++;
                        i++;
                    }
                    reverseArray(pow_arr);
                    for (int j = 0; j < pow_arr.length; j++) {
                        power += (int) (pow_arr[j] * Math.pow(10, j));
                    }
                    return power;
                } else {
                    return 1;
                }
            }
        }
        return power;
    }
}