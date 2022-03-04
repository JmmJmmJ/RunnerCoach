package testit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import RunnerCoachPeli.Race;
import RunnerCoachPeli.RaceEng;
import RunnerCoachPeli.Result;
import RunnerCoachPeli.Runner;
import RunnerCoachPeli.Seasons;

class RaceEngTests {

	RaceEng raceEng = new RaceEng();
	
	Runner runner1 = new Runner("A", 50);
	Runner runner2 = new Runner("B", 70);
	Runner runner3 = new Runner("C", 80);
	Runner runner4 = new Runner("D", 99);
	
	Race race = new Race("kisa", 0);
	

	
	@Test
	void test() {
		raceEng.setStdDev(0);
		
		race.addRunnerToRace(runner1);
		race.addRunnerToRace(runner2);
		race.addRunnerToRace(runner3);
		race.addRunnerToRace(runner4);
		
		raceEng.raceSim(race);
		
        List<List<Result>> kisaValiajat = race.getResults();
        assertEquals(kisaValiajat.get(0).get(0).getRunner().getName().contains("A"), true);
        assertEquals(kisaValiajat.get(0).get(1).getRunner().getName().contains("B"), true); 
        assertEquals(kisaValiajat.get(0).get(2).getRunner().getName().contains("C"), true); 
        assertEquals(kisaValiajat.get(0).get(3).getRunner().getName().contains("D"), true); 
        
        assertEquals(kisaValiajat.size(), race.getKisanPituus());
        assertEquals(kisaValiajat.get(0).size(), 4);
	}
	
	Runner valmennettava = new Runner("Topi", 70);
	Runner runnerL = new Runner("Lasse", 90);
	Runner runnerK = new Runner("Kenenisa", 98);
	Runner runnerJ = new Runner("Juha", 80);
	
	@Test
	void testTrainingAndInjuries() {
		valmennettava.setValmennettavaksi();
		
		ArrayList<Runner> runners = new ArrayList<Runner>();
		runners.add(valmennettava);
		runners.add(runnerL);
		runners.add(runnerK);
		runners.add(runnerJ);
		
		Seasons seasons = new Seasons(runners);

		int training = 60;
		int increase = 2;
		int injuries = 0;
		double levelSum = 0;
		int months = 100;
		
		for (int i = 0; i < months; i++) {
			Random r = new Random();
			levelSum = levelSum + valmennettava.getLevel();
			valmennettava.training(training + r.nextInt(20));
			training = training + increase;

			// injuries only one month
			if (valmennettava.injury()) {
				seasons.nextMonth(1);
				valmennettava.training(0);
				injuries++;
			}
			seasons.nextMonth(1);
		}
		System.out.println("Months: " + months);
		System.out.println("Avg. level: " + levelSum/months);
		System.out.println("Injuries: " + injuries);
		
		assertTrue(levelSum/months < 100);
		assertTrue(levelSum/months > 0);
		assertTrue(injuries > 0);
		assertTrue(injuries < 50);		
		
	}

}
