package testit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import RunnerCoachPeli.Runner;
import RunnerCoachPeli.Seasons;

class SeasonsTest {
	
	Seasons seasons = new Seasons();
	Runner valmennettava = new Runner("Topi", 70);
	Runner runner2 = new Runner("Lasse", 90);
	Runner runner3 = new Runner("Kenenisa", 98);

	@Test
	void setterAndAdd() {
		valmennettava.setValmennettavaksi();
		assertTrue(valmennettava.onkoValmennettava());
		
		seasons.addRunner(valmennettava);
		seasons.addRunner(runner2);
		seasons.addRunner(runner3);
		seasons.addRunners2Race();
		
		assertEquals(seasons.getRace().getRunners().size(), 3);
	}
	
	@Test
	void nextMonth() {
		assertEquals(seasons.getMonth(), 1);
		assertEquals(seasons.getSeason(), 1);
		
		seasons.nextMonth(11);
		assertEquals(seasons.getMonth(), 12);
		assertEquals(seasons.getSeason(), 1);
		
		seasons.nextMonth(6);
		assertEquals(seasons.getMonth(), 6);
		assertEquals(seasons.getSeason(), 2);
	}

}
