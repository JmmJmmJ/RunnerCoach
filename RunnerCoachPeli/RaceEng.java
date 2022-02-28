package RunnerCoachPeli;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 
 * @Author Jyrki Mäki
 * @version 15.11.2021
 * 
 * TODO eri matkat, taktiikka, ota huomioon rasitus
 * 
 */
public class RaceEng {
	
	private List<Runner> runners = new ArrayList<Runner>();
	private double standarDeviation = 10;
	private int kisanPituus = 10;
	
	/**
	 * Suorittaa yhden kilometrin kisassa
	 * @param race kisa jota juostaan
	 */
	public void runKm(Race race) {
		
		Random r = new Random();
		for (Runner runner : runners) {
			double splitTime = (1760 - 2*runner.getLevel())/kisanPituus + r.nextGaussian()*standarDeviation; 

			double time = runner.getResult() + splitTime;
	
			runner.setResult(time);
		}

		Collections.sort(runners, Runner.timeComp);

	}
	
	
	/**
	 * Tekee listan tuloksista
	 * @return lista tuloksista
	 */
	public List<String> tulokset() {
		int position = 1;
		List<String> results = new ArrayList<String>();
		
		for (Runner runner : runners) {
			String time = convertTime(runner.getResult());

			String tulos = String.format("%d. %s Time: %s%n", position, runner, time);
			results.add(tulos);
			position++;
		}
		return results;
	}
	
	/**
	 * Muuntaa sekunnit minuuteiksi ja sekunnuiksi
	 * @param time
	 * @return
	 */
	public String convertTime(double time) {
		int seconds = (int) time % 60;
		int minutes = (int) time / 60;
		
		return minutes + ":" + seconds;
	}
	
	/**
	 * Simuloi koko kisan
	 * @param race kisa joka juostaan
	 * @return taulukko jonka indeksit ovat kisan kilometrejä. Jokaiseen indeksiin on tallennettu juoksijoiden ajat listaan järjestyksessä kyseisen kilometrin kohdalla
	 */
	public List<List<String>> raceSim(Race race) {
		runners = race.getRunners();
		kisanPituus = race.getKisanPituus();
		
		List<List<String>> results = new ArrayList<List<String>>();

		for (int k = 1; k <= kisanPituus; k++) {
			runKm(race);
			results.add(this.tulokset());
		}
		
		for (Runner runner: runners) {
			runner.setResult(0);
		}
		
		return results;
	}
	
	public void setStdDev(double dev) {
		standarDeviation = dev;
	}

	/**
	 * Testi pääohjelma
	 * 
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		RaceEng raceEng = new RaceEng();
		Race race = new Race("Paikallinen kisa", 10);

		Runner valmennettava = new Runner("Topi", 70);
		valmennettava.setValmennettavaksi();
		Runner runner2 = new Runner("Lasse", 90);
		Runner runner3 = new Runner("Kenenisa", 98);

		race.addRunnerToRace(valmennettava);
		race.addRunnerToRace(runner2);
		race.addRunnerToRace(runner3);
				
		List<List<String>> kisa = raceEng.raceSim(race);
		
		for (List<String> list : kisa) {
			for (String tulos : list) {
				System.out.println(tulos);
			}
		}
				

	}
}
