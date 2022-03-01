package testit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import RunnerCoachPeli.Race;
import RunnerCoachPeli.RaceEng;
import RunnerCoachPeli.Runner;

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
		
        List<List<String>> kisaValiajat = raceEng.raceSim(race);
        assertEquals(kisaValiajat.get(0).get(0).contains("1. D"), true); 
        assertEquals(kisaValiajat.get(0).get(1).contains("2. C"), true);        
        assertEquals(kisaValiajat.get(0).get(2).contains("3. B"), true);        
        assertEquals(kisaValiajat.get(0).get(3).contains("4. A"), true);        

	}

}
