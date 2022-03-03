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
	private List<Season> seasons = new ArrayList<Season>();
	private List<Runner> runners = new ArrayList<Runner>();

	public Seasons(List<Runner> runners) {
		this.runners = runners;
		
		seasons.add(new Season(1));

		for (Runner runner : runners) {
			seasons.get(seasonN-1).addRunner(runner);
		}
	}
	
	public Seasons() {
		seasons.add(new Season(1));
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
		
		if (seasonN != kausi) {
			seasons.add(new Season(kausi));
			this.addRunners(runners, kausi);
		}

		month = kuukausi;
		seasonN = kausi;
	}
	
	/**
	 * Lisää juoksijat kaudelle
	 * TODO lisää millä perusteella juoksijat valitaan kauteen
	 * @param runners
	 * @param kausi
	 */
	public void addRunners(List<Runner> runners, int kausi) {
		for (Runner runner : runners) {
			seasons.get(kausi-1).addRunner(runner);
		}
	}
	
	public Season getSeasonObj() {
		return seasons.get((seasonN)-1);
	}

	public int getSeason() {
		return seasonN;
	}

	public int getMonth() {
		return month;
	}

	
	/**
	 * Lisää seuraavaan kisaan osallistuvat juoksijat kisaan
	 */
	public void addRunners2Race() {
		seasons.get(seasonN-1).addRunners2Race(month);
	}

	/**
	 * Palauttaa seuraavan kisan
	 * 
	 * @return seuraava kisa
	 */
	public Race getRace() {
		return seasons.get(seasonN-1).getRace(month);
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
