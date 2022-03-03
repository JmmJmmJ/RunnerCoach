package testit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import RunnerCoachPeli.Runner;
import RunnerCoachPeli.Seasons;

class SeasonsTest {
	
	Runner valmennettava = new Runner("Topi", 70);
	Runner runner2 = new Runner("Lasse", 90);
	Runner runner3 = new Runner("Kenenisa", 98);
	Runner runner4 = new Runner("Juha", 80);
	
	@Test
	void setterAndAdd() {
		valmennettava.setValmennettavaksi();

		ArrayList<Runner> runners = new ArrayList<Runner>();
		runners.add(valmennettava);
		runners.add(runner2);
		runners.add(runner3);
		runners.add(runner4);
		
		assertTrue(valmennettava.onkoValmennettava());
		
		Seasons seasons = new Seasons(runners);
		
		seasons.addRunners2Race();

		assertEquals(seasons.getRace().getRunners().size(), 4);
	}
	
	@Test
	void nextMonth() {
		valmennettava.setValmennettavaksi();

		ArrayList<Runner> runners = new ArrayList<Runner>();
		runners.add(valmennettava);
		runners.add(runner2);
		runners.add(runner3);
		runners.add(runner4);
		
		Seasons seasons = new Seasons(runners);
	
		assertEquals(seasons.getMonth(), 1);
		assertEquals(seasons.getSeason(), 1);
		
		seasons.nextMonth(11);
		assertEquals(seasons.getMonth(), 12);
		assertEquals(seasons.getSeason(), 1);
		
		seasons.nextMonth(6);
		assertEquals(seasons.getMonth(), 6);
		assertEquals(seasons.getSeason(), 2);
		assertEquals(seasons.getSeasonObj().getRunners().size(), 4);
	}

}
