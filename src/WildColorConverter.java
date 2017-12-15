import java.awt.Color;

public class WildColorConverter extends ColorConverter {

	public WildColorConverter(){
		repeatPeriod = 256;
	}
	@Override
	public Color colorFor(int N) {
		// TODO Auto-generated method stub
		Color wild = new Color(((int)((Math.pow(N,10))%256)),(N*39)%256, (N+300)%256);
		return wild;
	}

}
