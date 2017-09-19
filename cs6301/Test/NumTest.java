/**
 *
 */
package cs6301.Test;

import cs6301.g1025.Num;
import junit.framework.TestCase;

import java.math.BigInteger;
import java.util.Random;

/**
 * @author swaroop
 *
 */
public class NumTest extends TestCase {

    static final Num zero = new Num("0");
    static Num smallPos = new Num("50");
    static Num smallNeg = new Num("-50");
    static Num largePos = new Num("100");
    static Num largeNeg = new Num("-100");

    /**
     * @param name
     */
    public NumTest(String name) {
        super(name);
    }

    /*
     * (non-Javadoc)
     *
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * (non-Javadoc)
     *
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test method for
     * {@link cs6301.g1025.Num#subtract(cs6301.g1025.Num, cs6301.g1025.Num)}.
     */
    public void testSubtract() {
        //zero cases
        Num out0 = Num.subtract(smallNeg, smallNeg);
        Num out01 = Num.subtract(smallPos, smallPos);

        Num out1 = Num.subtract(smallPos, largePos);// 50, 100
        Num out2 = Num.subtract(smallNeg, largeNeg);// -50, -100
        Num out7 = Num.subtract(largeNeg, smallNeg);// -100, -50
        Num out8 = Num.subtract(largePos, smallPos);// 100, 50

        Num out3 = Num.subtract(smallPos, largeNeg);// 50, -100
        Num out4 = Num.subtract(smallNeg, largePos);// -50, 100
        Num out5 = Num.subtract(largePos, smallNeg);// 100, -50
        Num out6 = Num.subtract(largeNeg, smallPos);// -100, 50
        assertEquals("0", out0.toString());
        assertEquals("0", out01.toString());
        assertEquals("-50", out1.toString());
        assertEquals("50", out2.toString());
        assertEquals("-50", out7.toString());
        assertEquals("50", out8.toString());
        assertEquals("150", out3.toString());
        assertEquals("-150", out4.toString());
        assertEquals("150", out5.toString());
        assertEquals("-150", out6.toString());


        int n1=1;
        int n2 = (int) Math.pow(2, 15);
        for(int i=0;i<15;i++){
            Random rand = new Random();
            BigInteger num1 = new BigInteger(n1, rand);
            BigInteger num2 = new BigInteger(n2, rand);

            Num newNum1 = new Num(num1.toString());
            Num newNum2 = new Num(num2.toString());

            BigInteger numFinal = num1.subtract(num2);

            Num newFinal = Num.subtract(newNum1, newNum2);
//            System.out.println(numFinal);
//            System.out.println(newFinal);

            assertEquals(numFinal.toString(), newFinal.toString());
            System.out.println("[TEST CASE: "+i+"] PASSED");

            n1=n1*2;
            n2 = n2/2;
        }
    }

    /**
     * Test method for
     * {@link cs6301.g1025.Num#add(cs6301.g1025.Num, cs6301.g1025.Num)}.
     */
    public void testAdd() {
        // same sign
        Num out0 = Num.add(smallNeg, smallPos);

        Num out1 = Num.add(smallPos, largePos);// 50, 100
        Num out2 = Num.add(smallNeg, largeNeg);// -50, -100

        Num out3 = Num.add(smallPos, largeNeg);// 50, -100
        Num out4 = Num.add(smallNeg, largePos);// -50, 100
        Num out5 = Num.add(largePos, smallNeg);// 100, -50
        Num out6 = Num.add(largeNeg, smallPos);// -100, 50

        assertEquals("0", out0.toString());
        assertEquals("150", out1.toString());
        assertEquals("-150", out2.toString());
        assertEquals("-50", out3.toString());
        assertEquals("50", out4.toString());
        assertEquals("50", out5.toString());
        assertEquals("-50", out6.toString());

        int n1=1;
        int n2 = (int) Math.pow(2, 15);
        for(int i=0;i<15;i++){
            Random rand = new Random();
            BigInteger num1 = new BigInteger(n1, rand);
            BigInteger num2 = new BigInteger(n2, rand);

            Num newNum1 = new Num(num1.toString());
            Num newNum2 = new Num(num2.toString());

            BigInteger numFinal = num1.add(num2);

            Num newFinal = Num.add(newNum1, newNum2);
//            System.out.println(numFinal);
//            System.out.println(newFinal);

            assertEquals(numFinal.toString(), newFinal.toString());
            System.out.println("[TEST CASE: "+i+"] PASSED");

            n1=n1*2;
            n2 = n2/2;
        }
    }

    /**
     * Test method for
     * {@link cs6301.g1025.Num#product(cs6301.g1025.Num, cs6301.g1025.Num)}.
     */
    public void testProduct() {
        Num out0 = Num.product(smallPos, zero); // 50 * 0
        Num out1 = Num.product(smallPos, smallNeg);//50 * -50
        Num out2 = Num.product(largePos, largeNeg); //100 * -100
        Num out3 = Num.product(largePos, largePos);//100 * 100
        Num out4 = Num.product(largePos, smallPos);//100 * 50

        assertEquals("0", out0.toString());
        assertEquals("-2500", out1.toString());
        assertEquals("-10000", out2.toString());
        assertEquals("10000", out3.toString());
        assertEquals("5000", out4.toString());

        int n1=1;
        int n2 = (int) Math.pow(2, 15);
        for(int i=0;i<15;i++){
            Random rand = new Random();
            BigInteger num1 = new BigInteger(n1, rand);
            BigInteger num2 = new BigInteger(n2, rand);

            Num newNum1 = new Num(num1.toString());
            Num newNum2 = new Num(num2.toString());

            BigInteger numFinal = num1.multiply(num2);

            Num newFinal = Num.product(newNum1, newNum2);
//            System.out.println(numFinal);
//            System.out.println(newFinal);

            assertEquals(numFinal.toString(), newFinal.toString());
            System.out.println("[TEST CASE: "+i+"] PASSED");

            n1=n1*2;
            n2 = n2/2;
        }
    }

    /**
     * Test method for {@link cs6301.g1025.Num#power(cs6301.g1025.Num, long)}.
     */
    public void testPowerNumLong() {
        fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link cs6301.g1025.Num#divide(cs6301.g1025.Num, cs6301.g1025.Num)}.
     */
    public void testDivide() {
        fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link cs6301.g1025.Num#mod(cs6301.g1025.Num, cs6301.g1025.Num)}.
     */
    public void testMod() {
        fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link cs6301.g1025.Num#power(cs6301.g1025.Num, cs6301.g1025.Num)}.
     */
    public void testPowerNumNum() {
        fail("Not yet implemented");
    }

}
