import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ThreadedFractalPane extends JPanel implements ComponentListener, MouseListener, MouseMotionListener
{
	private static final long serialVersionUID = 1L; // don't worry about this.
	private CoordinateConverter coordConverter;
	private ColorConverter  colorMap;
	private int startX, startY;
	private int mouseX, mouseY;
	private final int THRESHOLD = 10;
	private final int MAX_DEPTH = 512;
	private BufferedImage image;
	private ImageGenerator calculationThread;
	private boolean dragging;
	private boolean calculationThreadNeedsReset;
	private final Font complexFont = new Font("Arial",Font.BOLD,8);
	
	public ThreadedFractalPane()
	{
		super();
		this.addComponentListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		coordConverter = new CoordinateConverter();
		colorMap = new WildColorConverter();
		setImage();
		calculationThread = new ImageGenerator();
		calculationThread.start();
		dragging = false;
	}
	
	public void setImage()
	{
		if (getBounds().width>0 && getBounds().height >0)
			image = new BufferedImage(getBounds().width,getBounds().height,BufferedImage.TYPE_INT_ARGB);
		else
			image = null;
		repaint();
	}
	public void componentResized(ComponentEvent e) 
	{
        coordConverter.setScreenSize(getBounds().width, getBounds().height);
        setImage();
        calculationThreadNeedsReset = true;
        repaint();
    }

    
	/**
	 * for a given pixel coordinate pair (x,y) runs through the z = z^2 + c cycle, and determines the color for that pixel
	 * @param x
	 * @param y
	 * @return
	 */
    public Color calculateColorAt(int x, int y)
    {
    	// Note that you have class constants THRESHOLD and MAX_DEPTH set up for you at the top of this file. THRESHOLD indicates the distance
    	//    from (0 + 0·i) at which you determine the sequence will "blow up" - the MAX_DEPTH is the limit on how many iterations you
    	//    are going to try - some sequences will stay inside the threshold forever! The larger this number, the more detail you can see when
    	//    you zoom in, but the longer it will take to generate the fractal, particularly when looking at points "inside" the set that don't 
    	//    "blow up."
    	 Complex c = coordConverter.complexNumberAt(x, y);
   	  
 		Complex z = Complex.zero;
 		
 		//-------------------------------
 		// TODO: write your code here!
 		int colorInt=0;
 		for(int i=0;i<=MAX_DEPTH;i++){
 		z=(z.squared()).plus(c);
 		colorInt=i;
 		if(z.magnitude()>=THRESHOLD)
 			break;
 		}
 		
 		Color col=colorMap.colorFor(colorInt);
 		//-------------------------------
 		return col;
    }
    
	public void paintComponent(Graphics g)
	{

		if (image != null)
			g.drawImage(image, 0, 0, null);
		if (dragging)
		{
			int minRectX = Math.min(startX, mouseX);
			int maxRectX = Math.max(startX, mouseX);
			int minRectY = Math.min(startY, mouseY);
			int maxRectY = Math.max(startY, mouseY);
			g.setColor(new Color((float)Math.random(),(float)Math.random(),(float)Math.random()));
			g.drawRect(minRectX, minRectY, maxRectX-minRectX, maxRectY-minRectY);
			repaint();
		}
		Complex cAtMouse = coordConverter.complexNumberAt(mouseX,mouseY);
		String complexString = cAtMouse.toString();
		g.setFont(complexFont);
		
		int w = g.getFontMetrics().stringWidth(complexString);
		g.setColor(Color.lightGray);
		g.fillRect(mouseX, mouseY-10, w+2, 10);
		g.setColor(Color.BLACK);
		g.drawString(complexString, mouseX + 1, mouseY-2);
		
		
		
	}
	// MouseListener methods
	@Override
	public void mousePressed(MouseEvent e)
	{
		startX = e.getX();
		startY = e.getY();
		mouseX = startX;
		mouseY = startY;
		dragging = true;
		System.out.println("A");
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		if (!dragging)
			return;
		int endX = e.getX();
		int endY = e.getY();
		
		int minX = Math.min(startX, endX);
		int maxX = Math.max(startX, endX);
		int minY = Math.min(startY, endY);
		int maxY = Math.max(startY, endY);
		
		if (minX < maxX && minY < maxY)
		{
			Complex c1= coordConverter.complexNumberAt(minX, minY);
			Complex c2= coordConverter.complexNumberAt(maxX, maxY);
		
			if (!e.isShiftDown()) // you can write the alternate case for zooming out, if you like....
			{
				coordConverter.setMathRange(c1.getRe(), c2.getRe(), c2.getIm(), c1.getIm());
				calculationThreadNeedsReset = true;
				repaint();
			}
		}
		dragging = false;
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		dragging = false;
		
	}
	// MouseMotionListener methods
	@Override
	public void mouseDragged(MouseEvent e)
	{
		System.out.println("C");
		mouseX = e.getX();
		mouseY = e.getY();
		//dragging = true;
		repaint();
	}

	// Our "internal" class - this allows us to do multithreading - so that we see the fractal getting drawn, 
	//    instead of just waiting and "pop" it appears!
	// The upshot of this is that the "run" method is running more-or-less concurrently with the main program, but it
	// does have to sleep() occasionally.
	// Since this class is written inside of our FractalPane class, it automatically has access to all the methods and variables
	// of the FractalPane class.
	
	public class ImageGenerator extends Thread
	{
		int x, y;
		boolean activeDraw;
		int boxSize;
		
		public ImageGenerator()
		{
			calculationThreadNeedsReset = true;
			boxSize = 1;
		}
		public void reset()
		{
			activeDraw = true;
			x=0;
			y=0;
			calculationThreadNeedsReset = false;
		}
		/**
		 * relinquish control for a moment so that other things, like screen updates can happen. We need to call this
		 * every so often...
		 */
		private void pause()
		{	try
        	{
            	sleep(1);
        	}
        	catch (InterruptedException ie)
        	{
        		System.out.println(ie.toString());
        	}
		}
		
		@Override
		public void run()
		{
			Graphics g;
			while(true)
			{
				if (calculationThreadNeedsReset)
					reset();
				if (image == null) // if we don't have an image to draw in, then let the rest of the program run until we do.
				{
					pause();
					continue;
				}
				if (x>=getBounds().width)
				{
					x=0;
					y+=boxSize;
					repaint();// tell the window to update...
					pause();  // and give control back to the main program so that it can.
				}
				if (y>=getBounds().height)
				{
					activeDraw = false;
				}
				if (activeDraw)
				{
					g = image.getGraphics();
					Color col = calculateColorAt(x,y); // here's the part that makes it a fractal.... 
													   //  ...calling this method in the FractalPane class.
					g.setColor(col);
					g.fillRect(x, y, boxSize, boxSize);
					x+=boxSize;
				}
			}
		}
	}
	// implemented methods we aren't using...
	public void componentHidden(ComponentEvent e) {}

    public void componentMoved(ComponentEvent e) {}

    public void componentShown(ComponentEvent e) {}
    
	public void mouseClicked(MouseEvent e) {}
    
	public void mouseEntered(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = e.getY();
		repaint();
	}
	
	public void mouseMoved(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
		repaint();
	}
	
}

