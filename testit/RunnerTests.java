package testit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import RunnerCoachPeli.Runner;

class RunnerTests {

	Runner runner = new Runner("A", 60);

	@Test
	void shouldUpdateThreshold() {
		runner.training(70);
		runner.training(70);
		runner.training(70);

		assertEquals(runner.getThreshold(), 70, 0.1);
	}
	
	@Test
	void shouldUpdateLevel() {
		// given (default threshold 50)
		double training = 60;

		// when
		int i = 0;
		while (i < 100 ) {
		runner.training(training);
		i++;
		}
		double result = runner.getLevel();

		// then
		assertTrue(result > 60);
		assertTrue(result < 100);
	}
	
	@Test
	void injuryProb() {
		double result;
		result = runner.injuryProb(50, 50, 0);
		assertTrue(result > -0.01);
		assertTrue(result < 0.2);
		
		result = runner.injuryProb(50, 60, 0);
		assertTrue(result > 0);
		assertTrue(result < 0.2);
		
		result = runner.injuryProb(50, 100, 0);
		assertTrue(result > 0.5);
		assertTrue(result < 1);
		
		result = runner.injuryProb(50, 10, 0);
		assertTrue(result < 0.001);
		
	}

}
