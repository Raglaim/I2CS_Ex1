package assignments.Ex1;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  * Introduction to Computer Science 2026, Ariel University,
 *  * Ex1: arrays, static functions and JUnit
 * <p>
 * This JUnit class represents a JUnit (unit testing) for Ex1-
 * It contains few testing functions for the polynomial functions as define in Ex1.
 * Note: you should add additional JUnit testing functions to this class.
 *
 * @author boaz.ben-moshe
 */

class Ex1Test {
	static final double[] P1 ={2,0,3, -1,0}, P2 = {0.1,0,1, 0.1,3};
	static double[] po1 = {2,2}, po2 = {-3, 0.61, 0.2};;
	static double[] po3 = {2,1,-0.7, -0.02,0.02};
	static double[] po4 = {-3, 0.61, 0.2};
	
 	@Test
	/**
	 * Tests that f(x) == poly(x).
	 */
	void testF() {
		double fx0 = Ex1.f(po1, 0);
		double fx1 = Ex1.f(po1, 1);
		double fx2 = Ex1.f(po1, 2);
		assertEquals(fx0, 2, Ex1.EPS);
		assertEquals(fx1, 4, Ex1.EPS);
		assertEquals(fx2, 6, Ex1.EPS);
	}
	@Test
	/**
	 * Tests that p1(x) + p2(x) == (p1+p2)(x)
	 */
	void testF2() {
		double x = Math.PI;
		double[] po12 = Ex1.add(po1, po2);
		double f1x = Ex1.f(po1, x);
		double f2x = Ex1.f(po2, x);
		double f12x = Ex1.f(po12, x);
		assertEquals(f1x + f2x, f12x, Ex1.EPS);
	}
	@Test
	/**
	 * Tests that p1+p2+ (-1*p2) == p1
	 */
	void testAdd() {
		double[] p12 = Ex1.add(po1, po2);
		double[] minus1 = {-1};
		double[] pp2 = Ex1.mul(po2, minus1);
		double[] p1 = Ex1.add(p12, pp2);
		assertTrue(Ex1.equals(p1, po1));
	}
	@Test
	/**
	 * Tests that p1+p2 == p2+p1
	 */
	void testAdd2() {
		double[] p12 = Ex1.add(po1, po2);
		double[] p21 = Ex1.add(po2, po1);
		assertTrue(Ex1.equals(p12, p21));
	}
	@Test
	/**
	 * Tests that p1+0 == p1
	 */
	void testAdd3() {
		double[] p1 = Ex1.add(po1, Ex1.ZERO);
		assertTrue(Ex1.equals(p1, po1));
	}
	@Test
	/**
	 * Tests that p1*0 == 0
	 */
	void testMul1() {
		double[] p1 = Ex1.mul(po1, Ex1.ZERO);
		assertTrue(Ex1.equals(p1, Ex1.ZERO));
	}
	@Test
	/**
	 * Tests that p1*p2 == p2*p1
	 */
	void testMul2() {
		double[] p12 = Ex1.mul(po1, po2);
		double[] p21 = Ex1.mul(po2, po1);
		assertTrue(Ex1.equals(p12, p21));
	}
	@Test
	/**
	 * Tests that p1(x) * p2(x) = (p1*p2)(x),
	 */
	void testMulDoubleArrayDoubleArray() {
		double[] xx = {0,1,2,3,4.1,-15.2222};
		double[] p12 = Ex1.mul(po1, po2);
		for(int i = 0;i<xx.length;i=i+1) {
			double x = xx[i];
			double f1x = Ex1.f(po1, x);
			double f2x = Ex1.f(po2, x);
			double f12x = Ex1.f(p12, x);
			assertEquals(f12x, f1x*f2x, Ex1.EPS);
		}
	}
	@Test
	/**
	 * Tests a simple derivative examples - till ZERO.
	 */
	void testDerivativeArrayDoubleArray() {
		double[] p = {1,2,3}; // 3X^2+2x+1
		double[] pt = {2,6}; // 6x+2
		double[] dp1 = Ex1.derivative(p); // 2x + 6
		double[] dp2 = Ex1.derivative(dp1); // 2
		double[] dp3 = Ex1.derivative(dp2); // 0
		double[] dp4 = Ex1.derivative(dp3); // 0
		assertTrue(Ex1.equals(dp1, pt));
		assertTrue(Ex1.equals(Ex1.ZERO, dp3));
		assertTrue(Ex1.equals(dp4, dp3));
	}
	@Test
	/** 
	 * Tests the parsing of a polynom in a String like form.
	 */
	public void testFromString() {
		double[] p = {-1.1,2.3,3.1}; // 3.1X^2+ 2.3x -1.1
		String sp2 = "3.1x^2 +2.3x -1.1";
		String sp = Ex1.poly(p);
		double[] p1 = Ex1.getPolynomFromString(sp);
		double[] p2 = Ex1.getPolynomFromString(sp2);
		boolean isSame1 = Ex1.equals(p1, p);
		boolean isSame2 = Ex1.equals(p2, p);
		if(!isSame1) {fail();}
		if(!isSame2) {fail();}
		assertEquals(sp, Ex1.poly(p1));
	}
	@Test
	/**
	 * Tests the equality of pairs of arrays.
	 */
	public void testEquals() {
		double[][] d1 = {{0}, {1}, {1,2,0,0}};
		double[][] d2 = {Ex1.ZERO, {1+ Ex1.EPS/2}, {1,2}};
		double[][] xx = {{-2* Ex1.EPS}, {1+ Ex1.EPS*1.2}, {1,2, Ex1.EPS/2}};
		for(int i=0;i<d1.length;i=i+1) {
			assertTrue(Ex1.equals(d1[i], d2[i]));
		}
		for(int i=0;i<d1.length;i=i+1) {
			assertFalse(Ex1.equals(d1[i], xx[i]));
		}
	}
	@Test
	/**
	 * Tests is the sameValue function is symmetric.
	 */
	public void testSameValue2() {
		double x1=-4, x2=0;
		double rs1 = Ex1.sameValue(po1,po2, x1, x2, Ex1.EPS);
		double rs2 = Ex1.sameValue(po2,po1, x1, x2, Ex1.EPS);
		assertEquals(rs1,rs2, Ex1.EPS);
	}
	@Test
	/**
	 * Test the area function - it should be symmetric.
	 */
	public void testArea() {
		double x1=-4, x2=0;
		double a1 = Ex1.area(po1, po2, x1, x2, 100);
		double a2 = Ex1.area(po2, po1, x1, x2, 100);
		assertEquals(a1,a2, Ex1.EPS);
}
	@Test
	/**
	 * Test the area f1(x)=0, f2(x)=x;
	 */
	public void testArea2() {
		double[] po_a = Ex1.ZERO;
		double[] po_b = {0,1};
		double x1 = -1;
		double x2 = 2;
		double a1 = Ex1.area(po_a,po_b, x1, x2, 1);
		double a2 = Ex1.area(po_a,po_b, x1, x2, 2);
		double a3 = Ex1.area(po_a,po_b, x1, x2, 3);
		double a100 = Ex1.area(po_a,po_b, x1, x2, 100);
		double area =2.5;
		assertEquals(a1,area, Ex1.EPS);
		assertEquals(a2,area, Ex1.EPS);
		assertEquals(a3,area, Ex1.EPS);
		assertEquals(a100,area, Ex1.EPS);
	}
	@Test
	/**
	 * Test the area function.
	 */
	public void testArea3() {
		double[] po_a = {2,1,-0.7, -0.02,0.02};
		double[] po_b = {6, 0.1, -0.2};
		double x1 = Ex1.sameValue(po_a,po_b, -10,-5, Ex1.EPS);
		double a1 = Ex1.area(po_a,po_b, x1, 6, 8);
		double area = 58.5658;
		assertEquals(a1,area, Ex1.EPS);
	}

    // added tests from here

    //Main Functions Tests - Elad Nagar

    /**
     * Tests PolynomFromPoints:
     * - Handles empty arrays, single point, two points (linear), and three points (quadratic).
     */
    @Test
    public void testPolynomFromPointselad() {
        double[] xx1 = {};
        double[] yy1 = {0};
        double[] polyfrompoints1 = Ex1.PolynomFromPoints(xx1,yy1);
        double[] result1 = null;
        assertTrue(Ex1.equals(result1,polyfrompoints1));
        double[] xx2 = {1};
        double[] yy2 = {5};
        double[] polyfrompoints2 = Ex1.PolynomFromPoints(xx2,yy2);
        double[] result2 = null;
        assertTrue(Ex1.equals(result2,polyfrompoints2));
        double[] xx3 = {1,3};
        double[] yy3 = {2,6};
        double[] polyfrompoints3 = Ex1.PolynomFromPoints(xx3,yy3);
        double[] result3 = {0,2};
        assertTrue(Ex1.equals(result3,polyfrompoints3));
        double[] xx4 = {1,2,3};
        double[] yy4 = {2,3,5};
        double[] polyfrompoints4 = Ex1.PolynomFromPoints(xx4,yy4);
        double[] result4 = {2,-0.5,0.5};
        assertTrue(Ex1.equals(result4,polyfrompoints4));
    }

    /**
     * Tests equals method:
     * - Checks equality for empty arrays, null, EPS tolerance, and inequality for different lengths.
     */
    @Test
    public  void testequalselad() {
        double[] p0 = {};
        assertTrue(Ex1.equals(p0,p0));
        assertTrue(Ex1.equals(null, null));
        assertFalse(Ex1.equals(p0, null));
        double[] p1 = {2,0,3.1,-1.2};
        double[] p2 = {2+Ex1.EPS/2,0,3.1,-1.2};
        double[] p3 = {2+Ex1.EPS*2,0,3.1,-1.2};
        assertTrue(Ex1.equals(p1,p2));
        assertFalse(Ex1.equals(p1,p3));
        double[] p4 = {1,2,3};
        assertFalse(Ex1.equals(p4,p1));
    }

    /**
     * Tests poly method:
     * - Converts polynomial arrays to string representation and verifies formatting.
     */
    @Test
    public void testpolyelad() {
        double[] p1 = {};
        String str_p1 = Ex1.poly(p1);
        String result1 = "0.0";
        assertEquals(result1, str_p1);
        double[] p2 = {10};
        String str_p2 = Ex1.poly(p2);
        String result2 = "10.0";
        assertEquals(result2, str_p2);
        double[] p3 = {2,0,3.1,-1.2};
        String str_p3 = Ex1.poly(p3);
        String result3 = "-1.2x^3 +3.1x^2 +2.0";
        String resultnot3 = " -1.2x^3 +3.1x^2 +2.0 ";
        assertEquals(result3, str_p3);
        assertNotEquals(resultnot3, str_p3);

    }

    /**
     * Tests sameValue:
     * - Finds intersection point in a small interval and validates result within EPS.
     */
    @Test
    public void  testsameValueelad1() {
        double[] p1 = {-4, 9, 4, 0, -3};
        double[] p2 = {-4, 8, 0, 0, -7};
        double result = Ex1.sameValue(p1, p2, -0.2366, -0.2368, Ex1.EPS);
        assertEquals(-0.2367, result, Ex1.EPS);
    }

    /**
     * Tests sameValue with multiple intervals:
     * - Ensures correct root finding or zero when no intersection.
     */
    @Test
    public void  testsameValueelad2() {
        double[] p1 = {1,-2,1,-3};
        double[] p2 = {1,-4,-1,3};
        double result1 = Ex1.sameValue(p1, p2, -0.25, -1, Ex1.EPS);
        double result2 = Ex1.sameValue(p1, p2, -0.25, 0.5, Ex1.EPS);
        double result3 = Ex1.sameValue(p1, p2, -0.5, 1, Ex1.EPS);
        assertEquals(-0.43426, result1, Ex1.EPS);
        assertEquals(0, result2, Ex1.EPS);
        assertEquals(0.767592, result3, Ex1.EPS);

    }

    /**
     * Tests length method:
     * - Computes curve length over an interval, handles zero-length interval, and different polynomials.
     */
    @Test
    public void testlenghtelad() {
        double[] p_1 = {0,1};
        double x1_1 = 0;
        double x2_1 = 10;
        int numberOfSegments_1 = 10;
        double result_11 = Ex1.length(p_1,x1_1,x2_1,numberOfSegments_1);
        double result_12 = Ex1.length(p_1,x1_1,x1_1,numberOfSegments_1);
        assertEquals(14.142, result_11, Ex1.EPS);
        assertEquals(0, result_12, Ex1.EPS);
        double[] p_2 = {2,0,-4};
        double x1_2 = 2;
        double x2_2 = -2;
        int numberOfSegments_2 = 4;
        double result_2 = Ex1.length(p_2,x1_2,x2_2,numberOfSegments_2);
        assertEquals(32.329, result_2, Ex1.EPS);
    }

    /**
     * Tests area method:
     * - Calculates area between two polynomials, handles reversed interval and zero-area case.
     */
    @Test
    public void testereaelad() {
        double [] p1 = Ex1.mul(P1,po1);
        double [] p2 = Ex1.add(P2,po2);
        double meet1 = Ex1.sameValue(p1,p2, -5,0, Ex1.EPS);
        double meet2 = Ex1.sameValue(p1,p2, 0,5, Ex1.EPS);
        double result11 = Ex1.area(p1,p2, meet1,meet2,10000);
        double result12 = Ex1.area(p1,p2, meet2,meet1,10000);
        assertEquals(23.43183, result11, Ex1.EPS);
        assertEquals(23.43183, result12, Ex1.EPS);
        double result2 = Ex1.area(p1,p2, 0,0,10000);
        assertEquals(0, result2, Ex1.EPS);
    }

    /**
     * Tests getPolynomFromString:
     * - Converts string to polynomial array, handles constants, zero, and high-degree terms.
     */
    @Test
    public void testgetPolynomFromStringelad() {
        double[] str_p1 = Ex1.getPolynomFromString("-1.0x^2 +3.0x +2.0");
        double [] result_p1 = {2,3,-1};
        assertTrue(Ex1.equals(str_p1,result_p1));
        double[] str_p2 = Ex1.getPolynomFromString("-10.0");
        double [] result_p2 = {-10};
        assertTrue(Ex1.equals(str_p2,result_p2));
        double[] str_p3 = Ex1.getPolynomFromString("0");
        double [] result_p3 = {0};
        assertTrue(Ex1.equals(str_p3,result_p3));
        double[] str_p4 = Ex1.getPolynomFromString("10");
        double [] result_p4 = {10};
        assertTrue(Ex1.equals(str_p4,result_p4));
        double[] str_p5 = Ex1.getPolynomFromString("-20x^4 +9.0x^3 +1.0x");
        double [] result_p5 = {0,1,0,9,-20};
        assertTrue(Ex1.equals(str_p5,result_p5));
        double[] str_p6 = Ex1.getPolynomFromString("-0.001x^10 +0");
        double [] result_p6 = {0,0,0,0,0,0,0,0,0,0,-0.001};
        assertTrue(Ex1.equals(str_p6,result_p6));
        double[] str_p7 = Ex1.getPolynomFromString("x^100 +67x^67 +0");
        double [] result_p7 = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,67,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1};
        assertTrue(Ex1.equals(str_p7,result_p7));
    }

    /**
     * Tests add method:
     * - Adds polynomials of different lengths and verifies correctness for multiple combinations.
     */
    @Test
    public void testaddelad() {
        double[] p1 = {-2,0,1};
        double[] p2 = {-1,0,0,1};
        double[] p3 = {4,0,-4,0,1};
        double[] p1p2 = Ex1.add(p1, p2);
        double[] p1p3 = Ex1.add(p1, p3);
        double[] p2p3 = Ex1.add(p2, p3);
        double[] p1p2p3 = Ex1.add(p1p2,p3);
        double[] result12 = {-3,0,1,1};
        double[] result13 = {2,0,-3,0,1};
        double[] result23 = {3,0,-4,1,1};
        double[] result123 = {1,0,-3,1,1};
        assertTrue(Ex1.equals(p1p2, result12));
        assertTrue(Ex1.equals(p1p3, result13));
        assertTrue(Ex1.equals(p2p3, result23));
        assertTrue(Ex1.equals(p1p2p3, result123));
    }

    /**
     * Tests mul method:
     * - Multiplies polynomials, including zero polynomial, and validates results.
     */
    @Test
    public void testmulelad() {
        double[] p0 = {0};
        double[] p1 = {-2,0,1};
        double[] p2 = {-1,0,0,1};
        double[] p3 = {4,0,-4,0,1};
        double[] p0p1 = Ex1.mul(p0, p1);
        double[] p1p2 = Ex1.mul(p1, p2);
        double[] p1p3 = Ex1.mul(p3, p1);
        double[] p2p3 = Ex1.mul(p2, p3);
        double[] p1p2p3 = Ex1.mul(p1p2,p3);
        double[] result0 = {0};
        double[] result12 = {2,0,-1,-2,0,1};
        double[] result13 = {-8,0,12,0,-6,0,1};
        double[] result23 = {-4,0,4,4,-1,-4,0,1};
        double[] result123 = {8,0,-12,-8,6,12,-1,-6,0,1};
        assertTrue(Ex1.equals(p0p1, result0));
        assertTrue(Ex1.equals(p1p2, result12));
        assertTrue(Ex1.equals(p1p3, result13));
        assertTrue(Ex1.equals(p2p3, result23));
        assertTrue(Ex1.equals(p1p2p3, result123));
    }

    /**
     * Tests derivative method:
     * - Handles empty and constant polynomials, computes derivatives for higher-degree polynomials.
     */
    @Test
    public void testderivativeelad() {
        double[] p00 = {};
        double[] p0 = {5};
        double[] p1 = {-2,0,1};
        double[] p2 = {-1,0,0,1};
        double[] p3 = {4,0,-4,0,1};
        double[] dp00 = Ex1.derivative(p00);
        double[] dp0 = Ex1.derivative(p0);
        double[] dp1 = Ex1.derivative(p1);
        double[] dp2 = Ex1.derivative(p2);
        double[] dp3 = Ex1.derivative(p3);
        double[] result0 = {0};
        double[] result1 = {0,2};
        double[] result2 = {0,0,3};
        double[] result3 = {0,-8,0,4};
        assertTrue(Ex1.equals(dp00, result0));
        assertTrue(Ex1.equals(dp0, result0));
        assertTrue(Ex1.equals(dp1, result1));
        assertTrue(Ex1.equals(dp2, result2));
        assertTrue(Ex1.equals(dp3, result3));
    }

    //Extra Functions Tests - Elad Nagar

    /**
     * Tests getcoefficient:
     * - Extracts the leading coefficient from polynomial strings.
     * - Handles negative, positive, and large power cases.
     */
    @Test
    public void testgetcoefficientelad() {
        String p1 = "-1.0x^2 +3.0x +2.0";
        String p2 = "10";
        String p3 = "x^100 +67x^67 +0";
        assertEquals(-1, Ex1.getcoefficient(p1));
        assertEquals(10, Ex1.getcoefficient(p2));
        assertEquals(1, Ex1.getcoefficient(p3));

    }

    /**
     * Tests reversearray:
     * - Reverses arrays of different lengths.
     * - Handles empty arrays and single-element arrays.
     */
    @Test
    public void testreverearrayelad() {
        double[] arr1 = {};
        double[] rev1 = Ex1.reversearray(arr1);
        double[] result1 = {};
        assertTrue(Ex1.equals(result1,rev1));
        double[] arr2 = {5};
        double[] rev2 = Ex1.reversearray(arr2);
        double[] result2 = {5};
        assertTrue(Ex1.equals(result2,rev2));
        double[] arr3 = {1,2,3,4,5};
        double[] rev3 = Ex1.reversearray(arr3);
        double[] result3 = {5,4,3,2,1};
        assertTrue(Ex1.equals(result3,rev3));
        double[] arr4 = {10,20,30,40};
        double[] rev4 = Ex1.reversearray(arr4);
        double[] result4 = {40,30,20,10};
        assertTrue(Ex1.equals(result4,rev4));
    }

    /**
     * Tests calctrianglearea:
     * - Calculates area of a triangle using base and height.
     */
    @Test
    public void testcalctriangleareaelad() {
        double base = 10;
        double height = 5;
        double area = Ex1.calctrianglearea(base, height);
        assertEquals(25, area, Ex1.EPS);
    }

    /**
     * Tests calctrapezoidarea:
     * - Calculates area of a trapezoid using two bases and height.
     */
    @Test
    public void testcalctrapezoidareaelad() {
        double base1 = 10;
        double base2 = 10;
        double height = 5;
        double area = Ex1.calctrapezoidarea(base1, base2, height);
        assertEquals(50, area, Ex1.EPS);
    }

    /**
     * Tests getxpower:
     * - Extracts the power of x from a term string.
     * - Handles terms with x^n, x, and constants.
     */
    @Test
    public void testgetxpowerelad() {
        String term1 = "-1.0x^2";
        String term2 = "3.0x";
        String term3 = "2.0";
        assertEquals(2, Ex1.getxpower(term1));
        assertEquals(1, Ex1.getxpower(term2));
        assertEquals(0, Ex1.getxpower(term3));
    }
}
