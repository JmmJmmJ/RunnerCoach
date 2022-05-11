package RunnerCoachPeli;

import java.util.ArrayList;
import java.util.List;

/**
 * Kausi Muodostaa kauden (atribuuttina kauden numero).
 * 
 * @Author Jyrki M‰ki
 */
public class Season {

	private List<Race> races = new ArrayList<Race>();
	private List<Runner> runners = new ArrayList<Runner>();

	public Season(int season) {
		for (int month = 1; month <= 12; month++) {
			if (season % 4 == 0 && month == 8) {
				Race race = new Race("Olympialaiset", 90);
				races.add(race);
			} else if (month == 6) {
				Race race2 = new Race("Kalevan kisat", 50);
				races.add(race2);
			} else {
				Race race2 = new Race("Paikallinen kisa", 10);
				races.add(race2);
			}
		}
	}

	/**
	 * Lis‰‰ seuraavaan kisaan juoksijat
	 * 
	 * @param month
	 * @return palauttaa seuraavan kisan
	 */
	public void addRunners2Race(int month) {
		Race nextRace = races.get(month - 1);

		runners.forEach(runner -> {
			nextRace.addRunnerToRace(runner);
		});
	}

	/**
	 * Palauttaa kisan
	 * 
	 * @param month mink‰ kuukauden kisa palautetaan
	 * @return kisa
	 */
	public Race getRace(int month) {
		Race nextRace = races.get(month - 1);
		return nextRace;
	}

	/**
	 * Lis‰‰ juoksijan kauteen
	 * 
	 * @param runner
	 */
	public void addRunner(Runner runner) {
		runners.add(runner);
	}
	
	public List<Runner> getRunners() {
		return runners;
	}

}
