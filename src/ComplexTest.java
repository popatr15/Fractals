import static org.junit.Assert.*;

import org.junit.Test;

public class ComplexTest
{

	public Complex t1;
	public Complex t2;
	public Complex t3;
	
	public ComplexTest(){
		t1 = new Complex(3,4);
		t2= new Complex(-1, 3);
		t3 = new Complex(-4,-6);
	}
	@Test
	public void testEquals()
	{
	
		// assertEquals and assertNotEquals versions....
//		assertEquals (Complex.one, new Complex(1,0));
//		assertNotEquals(Complex.one, new Complex(0,1));
		
		// assertTrue and assertFalse versions....
		assertTrue (Complex.i.equals(new Complex(0,1)));
		assertTrue (new Complex(4,3).equals(new Complex(4,3)));
		assertFalse(Complex.zero.equals(new Complex(0.5, 0.75)));
		assertFalse(new Complex(11,12).equals(new Complex(13, 14)));
	}
	@Test
	public void testMagnitude(){
		
		assertEquals(5, t1.magnitude(),0.01);
		assertNotEquals(t2.magnitude(), 30,0.01);
		assertEquals(0, new Complex(0,0).magnitude(), 0.01);
		assertEquals(7.21,t3.magnitude(),0.01);
		
		
	}
	@Test
	public void testCompareTo(){
		assertEquals(1, t1.compareTo(new Complex(0,0)));
		assertEquals(0, t2.compareTo(t2));
		assertEquals((-1), t3.compareTo(new Complex(10,10)));
	}
	@Test
	public void testPlus(){
		assertEquals(new Complex(2,7), t1.plus(t2));
		assertNotEquals(new Complex(0,0), t3.plus(new Complex(0,0)));
	}
	@Test
	public void testTimes(){
		assertEquals(new Complex(0,0), t1.times(0));
		assertEquals(new Complex(-40,-60), t3.times(10));
		assertEquals(new Complex(12,-34 ), t1.times(t3));
	}
	@Test
	public void testMinus(){
	assertNotEquals(new Complex(0,0),t1.minus(t2));
	assertEquals(new Complex(7,10),t1.minus(t3));
	}

}
