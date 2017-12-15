import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Complex implements Comparable<Complex>
{
	private double Re, Im;
	
	// some constant Complexes that might get used frequently - call them by Complex.one, Complex.i, and Complex.zero respectively.
	public static final Complex one = new Complex(1,0);
	public static final Complex i = new Complex(0,1);
	public static final Complex zero = new Complex(0,0);
	
	public Complex(double real, double imaginary)
	{
		Re = real;
		Im = imaginary;
	}
	
	public Complex()
	{
		this(0,0);
	}
	//------------------------------------------------------------------------------------------------ Accessors
	public double getRe()
	{
		return Re;
	}

	public double getIm()
	{
		return Im;
	}
	
	public String toString()
	{
		NumberFormat formatter = new DecimalFormat("0.000E0"); // we'll use this to put the doubles in scientific notation, with three digits after the decimal.
		
		return "("+formatter.format(Re)+" + "+formatter.format(Im)+"·i)";
		
	}
	//------------------------------------------------------------------------------------------------ Comparisons
	/**
	 * indicates whether "this" Complex is mathematically equivalent to "o". 
	 */
	public boolean equals(Object o)
	{
		if (!(o instanceof Complex))
			return false;  // bail if "o" is some other object than a Complex.
		Complex obj = (Complex)o; // creates a variable "obj" that we _know_ is a Complex (but is just a typecast version of "o").
		//---------------------------------------
		// TODO: write your code here... Replace the following line with code that determines whether "this" and "obj" have matching 
		boolean eq = false;
		if(obj.Re==this.Re&& obj.Im==this.Im)
			eq =true;
		//  real and imaginary parts.
		
		return eq;
		//---------------------------------------
	}

	/**
	 * finds the distance in the complex plane from "this" to (0,0).
	 * @return - the distance to 0,0.
	 */
	public double magnitude()
	{
		// --------------------------------------
		// TODO: write your code here. Replace the following line!
		// Hint: Pythagoras
		double mag = Math.sqrt(Math.pow(this.Re,2)+ Math.pow(this.Im, 2)) ; 
	   return mag;	
	    // --------------------------------------
	}

	@Override
	/**
	 * determines whether "c" or "this" is greater -- in this case, it's based on the magnitude. If "this" is greater, then the method
	 * returns (any) positive integer, such as +1. If "c" is greater, then the method returns (any) negative integer, such as (-1).
	 * If they are of equal magnitude, the method returns a zero.
	 * @param c - a complex number
	 * @return a negative, zero or positive integer, indicating the relative sizes of "this" and "c."
	 */
	public int compareTo(Complex c)
	{
		// --------------------------------------
		// TODO: write your code here, based on "c" compared to "this." Replace the following line.
		if(this.magnitude()>c.magnitude()){
			return 1;
		}
		else if( this.magnitude()<c.magnitude())
			return -1;
		else
			return 0;
		//---------------------------------------
	}
	
	/**
	 * A convenience method to compare this Complex's magnitude to an equivalent Complex with just the real part, r.
	 * @param r - a double that we will represent as the Complex number (r+0i)
	 * @return 
	 */
	public int compareTo(double r)
	{
		return this.compareTo(new Complex(r,0));
	}
	//------------------------------------------------------------------------------------------------ Complex math
	
	/**
	 * @param c - another complex number
	 * @return a new complex that is the sum of this complex and c.
	 */
	public Complex plus(Complex c)
	{
		// --------------------------------------
		// TODO: Write your code here. Create a new complex that is equivalent to the sum of "this" and c.Replace the following line.
		this.Re=this.Re+c.Re;
		this.Im= this.Im+c.Im;
		return this;
		// --------------------------------------
	}
	
	/**
	 * @param r - a real number
	 * @return a new complex that is the sum of this complex and r.
	 */
	public Complex plus(double r)
	{
		return this.plus(new Complex(r,0));
	}
	
	/**
	 * @param m - a real number
	 * @return a new complex that is the product of "this" complex and the real "m."
	 */
	public Complex times(double m)
	{
		// ---------------------------------------
		// TODO: Write your code here. Create a new complex that is equivalent to the product of "this" and "m." Replace the following line.
		// Hint: Don't overthink this. It's not that complicated.
		 Complex result= new Complex();
		 result.Im=this.Im*m;
		 result.Re=this.Re*m;
		 
		return result;
		// ---------------------------------------
	}
	
	/**
	 * @param c - another complex number
	 * @return - a new complex that is the product of "this" and the complex "c."
	 */
	public Complex times(Complex c)
	{
		// ---------------------------------------
		// TODO: Write your code here. Create a new complex that is equivalent to the product of "this" and "c." Replace the following line.
		// Hint: FOIL, and consider what happens when you multiply to imaginaries....
		Complex result = new Complex();
		result.Re= (this.Re*c.Re)+(this.Im*c.Im*-1);
		result.Im = (this.Im*c.Re)+(this.Re*c.Im);
		return result;
		
		// ---------------------------------------
	}
	/**
	 * @return - a new complex that is the square of "this."
	 */
	public Complex squared()
	{
		// ---------------------------------------
		// TODO: Write your code here. Create a new complex that is equivalent to the product of "this" and itself. Replace the following line.
		// Hint: don't reinvent the wheel - call another method you have written already.
		return this.times(this);
		// ---------------------------------------
	}
	
	/**
	 * @param c - another complex number
	 * @return - a new complex that is equivalent to "this" minus c.
	 */
	public Complex minus(Complex c)
	{
		// --------------------------------------
		// TODO: Write your code here. Create a new complex that is equivalent to "this" minus "c." Replace the following line.
		// Hint: don't reinvent the wheel - make use of methods you have already written.
		Complex result=new Complex();
		result.Re=this.Re-c.Re;
		result.Im = this.Im-c.Im;
		return result;
		// --------------------------------------
	}
	
	/**
	 * @param r - a real number
	 * @return - a new complex that is equivalent to "this" minus r.
	 */
	public Complex minus(double r)
	{
		return this.minus(new Complex(r,0));
	}
}
