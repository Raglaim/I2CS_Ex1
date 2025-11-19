package assignments.Ex1;

import java.lang.ref.Cleaner;
import java.util.Arrays;

/**
 * Introduction to Computer Science 2026, Ariel University,
 * Ex1: arrays, static functions and JUnit
 * https://docs.google.com/document/d/1GcNQht9rsVVSt153Y8pFPqXJVju56CY4/edit?usp=sharing&ouid=113711744349547563645&rtpof=true&sd=true
 *
 * This class represents a set of static methods on a polynomial functions - represented as an array of doubles.
 * The array {0.1, 0, -3, 0.2} represents the following polynomial function: 0.2x^3-3x^2+0.1
 * This is the main Class you should implement (see "add your code below")
 *
 * @author boaz.benmoshe

 */
public class Ex1 {
	/** Epsilon value for numerical computation, it serves as a "close enough" threshold. */
	public static final double EPS = 0.001; // the epsilon to be used for the root approximation.
	/** The zero polynomial function is represented as an array with a single (0) entry. */
	public static final double[] ZERO = {0};
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
	 * This function computes a polynomial representation from a set of 2D points on the polynom.
	 * The solution is based on: //	http://stackoverflow.com/questions/717762/how-to-calculate-the-vertex-of-a-parabola-given-three-points
	 * Note: this function only works for a set of points containing up to 3 points, else returns null.
	 * @param xx
	 * @param yy
	 * @return an array of doubles representing the coefficients of the polynom.
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
	/** Two polynomials functions are equal if and only if they have the same values f(x) for n+1 values of x,
	 * where n is the max degree (over p1, p2) - up to an epsilon (aka EPS) value.
	 * @param p1 first polynomial function
	 * @param p2 second polynomial function
	 * @return true iff p1 represents the same polynomial function as p2.
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
        } else {
            equals(p2,p1);
        }
		return ans;
	}

	/** 
	 * Computes a String representing the polynomial function.
	 * For example the array {2,0,3.1,-1.2} will be presented as the following String  "-1.2x^3 +3.1x^2 +2.0"
	 * @param poly the polynomial function represented as an array of doubles
	 * @return String representing the polynomial function:
	 */
	public static String poly(double[] poly) {
        String ans = "";
        if(poly.length==0) {ans="0";}
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
        return ans;
	}
	/**
	 * Given two polynomial functions (p1,p2), a range [x1,x2] and an epsilon eps. This function computes an x value (x1<=x<=x2)
	 * for which |p1(x) -p2(x)| < eps, assuming (p1(x1)-p2(x1)) * (p1(x2)-p2(x2)) <= 0.
	 * @param p1 - first polynomial function
	 * @param p2 - second polynomial function
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param eps - epsilon (positive small value (often 10^-3, or 10^-6).
	 * @return an x value (x1<=x<=x2) for which |p1(x) - p2(x)| < eps.
	 */
	public static double sameValue(double[] p1, double[] p2, double x1, double x2, double eps) {
		double ans = x1;
        /** add you code below

         /////////////////// */
		return ans;
	}
	/**
	 * Given a polynomial function (p), a range [x1,x2] and an integer with the number (n) of sample points.
	 * This function computes an approximation of the length of the function between f(x1) and f(x2) 
	 * using n inner sample points and computing the segment-path between them.
	 * assuming x1 < x2. 
	 * This function should be implemented iteratively (none recursive).
	 * @param p - the polynomial function
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param numberOfSegments - (A positive integer value (1,2,...).
	 * @return the length approximation of the function between f(x1) and f(x2).
	 */
	public static double length(double[] p, double x1, double x2, int numberOfSegments) {
		double ans = 0;
        double h = Math.abs(x1-x2)/numberOfSegments;
        if(x1 == x2){
            return ans;
        } else if (x1 < x2) {
            for (double i = 0; i < x2-x1; i += h) {
                double y1 = f(p,x1+i);
                double y2 = f(p,x1+i+h);
                double distance = Math.sqrt(Math.pow((y2-y1),2) + Math.pow((h),2));
                ans += distance;
            }
        } else {
            for (double i = 0; i < x1-x2; i += h) {
                double y1 = f(p,x2+i);
                double y2 = f(p,x2+i+h);
                double distance = Math.sqrt(Math.pow((y2-y1),2)+Math.pow((h),2));
                ans += distance;
            }
        }
		return ans;
	}

    static void main() {
        double[] p = {2,0,-4};
        System.out.println(length(p,2,-2,4));
    }
	
	/**
	 * Given two polynomial functions (p1,p2), a range [x1,x2] and an integer representing the number of Trapezoids between the functions (number of samples in on each polynom).
	 * This function computes an approximation of the area between the polynomial functions within the x-range.
	 * The area is computed using Riemann's like integral (https://en.wikipedia.org/wiki/Riemann_integral)
	 * @param p1 - first polynomial function
	 * @param p2 - second polynomial function
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param numberOfTrapezoid - a natural number representing the number of Trapezoids between x1 and x2.
	 * @return the approximated area between the two polynomial functions within the [x1,x2] range.
	 */
	public static double area(double[] p1,double[]p2, double x1, double x2, int numberOfTrapezoid) {
		double ans = 0;
        /** add you code below

        /////////////////// */
		return ans;
	}
	/**
	 * This function computes the array representation of a polynomial function from a String
	 * representation. Note:given a polynomial function represented as a double array,
	 * getPolynomFromString(poly(p)) should return an array equals to p.
	 * 
	 * @param p - a String representing polynomial function.
	 * @return
	 */
	public static double[] getPolynomFromString(String p) {
		double [] ans = ZERO;//  -1.0x^2 +3.0x +2.0
        String[] words = p.split(" ");
        ans = new double[words.length];
        int i = 0;
        for (String word : words) {
            ans[i] = getcoefficient(word);
            i++;
        }
        reverseArray(ans);
        return ans;
	}
	/**
	 * This function computes the polynomial function which is the sum of two polynomial functions (p1,p2)
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static double[] add(double[] p1, double[] p2) {
        double[] ans = ZERO;//
        if (p1.length >= p2.length) {
            ans = new double[p1.length];
            for (int i = 0; i < p2.length; i++) {
                ans[i] = p1[i] + p2[i];
            }
            System.arraycopy(p1, p2.length, ans, p2.length, p1.length - p2.length);
        } else {
            ans = new double[p2.length];
            for (int i = 0; i < p1.length; i++) {
                ans[i] = p2[i] + p1[i];
            }
            System.arraycopy(p2, p1.length, ans, p1.length, p2.length - p1.length);
        }
        return ans;
    }
	/**
	 * This function computes the polynomial function which is the multiplication of two polynoms (p1,p2)
	 * @param p1
	 * @param p2
	 * @return
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
        } else {
            double [] mul_p = new double[p2.length+p1.length-1];
            for (int i = 0; i < p1.length; i++) {
                for (int j = 0; j < p2.length; j++) {
                    mul_p[i+j] = mul_p[i+j]+(p1[i]*p2[j]);
                }
            }
            ans = new double[mul_p.length];
            System.arraycopy(mul_p,0,ans,0,mul_p.length);
            return ans;
        }
    }

	/**
	 * This function computes the derivative of the p0 polynomial function.
	 * @param po
	 * @return
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










    // Extra functions

    /**
     * This function extracts the leading numeric coefficient from an algebraic term string.
     * Examples: "-1.0x^2" -> -1.0, "+3x" -> 3.0, "0.5x^3" -> 0.5.
     * @param word
     * @return
     */
    public static double getcoefficient(String word) {
        double ans = 0;
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
        ans = Double.parseDouble(number);
        return ans;
    }
    /**
     * Reverses the elements of the given array in place.
     *
     * @param arr the array to reverse; must not be null
     * @return  IllegalArgumentException if arr is null
     *
     * Example:
     * int[] nums = {1, 2, 3, 4};
     * reverseArray(nums);
     * // nums becomes {4, 3, 2, 1}
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
}
