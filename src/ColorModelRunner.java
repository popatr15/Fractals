
/**
 * @author harlan.howe
 * This is a simple main function that creates an instance of a ColorConverter 
 * subclass and tells it to display its colormap. Use it to debug your ColorConverters!
 */

public class ColorModelRunner
{

	public static void main(String[] args)
	{
		WildColorConverter wcc = new WildColorConverter();
		wcc.showDemo();
	}

}
