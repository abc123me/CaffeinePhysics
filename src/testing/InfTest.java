package testing;

public class InfTest {
	public static void main(String[] args){
		float a = Float.NaN;
		float b = Float.POSITIVE_INFINITY;
		System.out.println("max(" + a + ", " + b + ")=" + Math.max(a, b));
	}
}
