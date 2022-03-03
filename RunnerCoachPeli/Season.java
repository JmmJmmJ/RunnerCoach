package RunnerCoachPeli;

import java.util.ArrayList;
import java.util.List;

/**
 * Kausi Muodostaa kauden (atribuuttina kauden numero).
 * 
 * @Author Jyrki Mäki
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
	 * Lisää seuraavaan kisaan juoksijat
	 * 
	 * @param month
	 * @return palauttaa seuraavan kisan
	 */
	public void addRunners2Race(int month) {
		Race nextRace = races.get(month - 1);

		for (Runner runner : runners) {
			nextRace.addRunnerToRace(runner);
		}
	}

	/**
	 * Palauttaa kisan
	 * 
	 * @param month minkä kuukauden kisa palautetaan
	 * @return kisa
	 */
	public Race getRace(int month) {
		Race nextRace = races.get(month - 1);
		return nextRace;
	}

	/**
	 * Lisää juoksijan kauteen
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
