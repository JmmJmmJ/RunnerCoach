package RunnerCoachPeli;

import java.util.Comparator;

/**
 * Kisan tulos
 * 
 * @Author Jyrki Mäki
 */

public class Result {
	private Runner runner;
	private double time;
	
	public Result(Runner runner, double time) {
		this.runner = runner;
		this.time = time;
	}
	
	public Runner getRunner() {
		return this.runner;
	}
	
	public double getTime() {
		return this.time;
	}
	
	/**
	 * Vertailee kahden juoksijan kisa tuloksia
	 */
	public static Comparator<Result> timeComp = new Comparator<Result>() {
		public int compare(Result r1, Result r2) {

			double result1 = r1.getTime();
			double result2 = r2.getTime();

			return Double.compare(result1, result2);
		}
	};
}
