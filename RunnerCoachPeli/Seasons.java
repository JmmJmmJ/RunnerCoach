package RunnerCoachPeli;

import java.util.ArrayList;
import java.util.List;

/**
 * Ylläpitää kausia
 * @author Jyrki Mäki
 */
public class Seasons {
	private int seasonN = 1;
	private int month = 1;
	private Season season;
	private List<Runner> runners = new ArrayList<Runner>();

	public Seasons(List<Runner> runners) {
		this.runners = runners;
		season = new Season(1);

		for (Runner runner : runners) {
			season.addRunner(runner);
		}
	}
	
	public Seasons() {
		season = new Season(1);
	}
	
	/**
	 * Siirtyy seuraaviin kuukausiin
	 * 
	 * @param months montako kuukautta siirrytään eteenpäin
	 */
	public void nextMonth(int months) {
		int kuukausi = (months+month) % 12;
		if (kuukausi == 0 ) {
			kuukausi = 12;
		}
		int kausi = (seasonN*12+month+months-1)/12;

		month = kuukausi;
		seasonN = kausi;
	}

	/**
	 * Lisää kaudelle osallistuvat juoksijat kisaan
	 */
	public void addRunner(Runner runner) {
		season.addRunner(runner);
	}

	public int getSeason() {
		return seasonN;
	}

	public int getMonth() {
		return month;
	}

	/**
	 * Lisää kisaan osallistuvat juoksijat kisaan
	 */
	public void addRunners2Race() {
		season.addRunners2Race(month);
	}

	/**
	 * Palauttaa seuraavan kisan
	 * 
	 * @return seuraava kisa
	 */
	public Race getRace() {
		return season.getRace(month);
	}

	public void setSeason(int season) {
		seasonN = season;
	}

	public void setMonth(int month) {
		this.month = month;
	}
	
	public static void main(String[] args) {
		Runner runner1 = new Runner("Lasse", 90);
		Runner runner2 = new Runner("Kenenisa", 98);
				
		List<Runner> runners2 = new ArrayList<Runner>();
		runners2.add(runner1);
		runners2.add(runner2);
					
		Seasons seasons = new Seasons(runners2);
		
		int k = 0;
		while (k < 100) {
			System.out.println(seasons.getMonth());
			seasons.nextMonth(1);
			seasons.getRace();
			k++;
		}
		
	}
}
