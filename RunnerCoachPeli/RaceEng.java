package RunnerCoachPeli;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 
 * @Author Jyrki Mäki
 * @version 3.3.2022
 * 
 * TODO eri matkat, taktiikka, ota huomioon rasitus
 * 
 */
public class RaceEng {
	
	private List<Runner> runners = new ArrayList<Runner>();
	private double standarDeviation = 5;
	private int kisanPituus = 10;
	
	/**
	 * Suorittaa yhden kilometrin kisassa
	 * @param race kisa jota juostaan
	 */
	public void runKm(Race race) {
		
		List<Result> results = new ArrayList<Result>();
		Random r = new Random();
		
		double optimalTime = 0.41*kisanPituus*kisanPituus + 156.3*kisanPituus - 30;
		
		for (Runner runner : runners) {
			double effectOflevel = 1.5*(100-runner.getLevel());
			double effectOfStress = runner.getStress()/50;
			double variation = r.nextGaussian()*standarDeviation;
			
			double splitTime = (optimalTime + effectOflevel + effectOfStress) / kisanPituus + variation;
			
			double time = runner.getResult() + splitTime;
			runner.setResult(time);
			
			Result result = new Result(runner, time);
			results.add(result);
		}
			race.addResult(results);
	}
	
	/**
	 * Simuloi koko kisan
	 * @param race kisa joka juostaan
	 * @return taulukko jonka indeksit ovat kisan kilometrejä. Jokaiseen indeksiin on tallennettu juoksijoiden ajat listaan järjestyksessä kyseisen kilometrin kohdalla
	 */
	public void raceSim(Race race) {
		runners = race.getRunners();
		kisanPituus = race.getKisanPituus();
		
		for (int k = 1; k <= kisanPituus; k++) {
			runKm(race);
		}
		
		for (Runner runner: runners) {
			runner.setResult(0);
		}
		
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
		
		raceEng.raceSim(race);
				
		List<List<Result>> kisa = race.getResults();
		
		for (List<Result> list : kisa) {
			for (Result tulos : list) {
				System.out.println(tulos.getRunner() + ", time: " + tulos.getTime());
			}
		}
				

	}
}
