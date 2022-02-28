package RunnerCoachPeli;

import java.util.ArrayList;
import java.util.List;

/**
 * Kisa
 * 
 * @author Jyrki Mäki
 * TODO karsinta ajat
 */
public class Race {
	private String name;
	private int kisanPituus = 10;
	private List<Runner> runners = new ArrayList<Runner>();
	private double level; // raja jota huonompia juoksijoita ei oteta kisaan

	public Race(String name, double level) {
		this.name = name;
		this.level = level;
	}

	public void addRunnerToRace(Runner runner) {
		if (runner.getLevel() >= level) {
			runners.add(runner);
		}
	}
	
	public int getKisanPituus() {
		return kisanPituus;
	}

	public List<Runner> getRunners() {
		return runners;
	}
	
	public double levelLimit() {
		return level;
	}

	@Override
	public String toString() {
		return name;
	}
}

