public class Point extends java.awt.Point
{
	public Point(){
		super();
	}
	public Point(java.awt.Point p){
		super(p);
	}
	public Point(int x, int y){
		super(x, y);
	}
	@Override
	public String toString(){
		return "(" + this.x + ", " + this.y + ")";
	}
	@Override
	public int hashCode(){
		return (this.x << 16) ^ this.y;
	}
}
