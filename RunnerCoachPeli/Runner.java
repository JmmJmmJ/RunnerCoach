package RunnerCoachPeli;


import java.util.Comparator;
import java.util.Random;

/**
 * Juoksija
 * 
 * @Author Jyrki M�ki
 * 
 * TODO speed/endurance
 * Enn�tykset ja tulokset
 */
public class Runner {
	private String name;
	private double level; // juoksijan taso miss� level=1 vastaa 30min 10k ja level=99 vastaa 27min 10k.
							// Yksi taso vastaa noin 1.8s.
	private double threshold = 50; // viikon juoksukilometrit, johon juoksia on sopeutunut. T�ll� tasolla
									// harjoitellessa juoksija ei kehity eik� rasitus nouse. Lasketaan kolmen
									// viimeisen kuukauden perusteella. Aloittaessa 50,50,50
	//private double stress = 0; // TODO juoksijan rasitustaso. Nostaa loukkaantumisriski� ja laskee
								// suoritustasoa kisassa
	private double[] training = { 50, 50, 50 }; // kolmen viimeisen viikon juoksukilometrit
	private boolean valmennettava = false;
	private double lastResult = 0; // viime kisan aika sekunteina

	public Runner(String name, double level) {
		this.name = name;
		this.level = level;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLevel(double level) {
		this.level = level;
	}

	public double getLevel() {
		return level;
	}

	public void setValmennettavaksi() {
		valmennettava = true;
	}

	public boolean onkoValmennettava() {
		return valmennettava;
	}

	public void setResult(double result) {
		lastResult = result;
	}

	public double getResult() {
		return lastResult;
	}
	

	public double getThreshold() {
		return threshold;
	}

	/**
	 * M��rtitt�� juoksijan harjoittelun vaikutuksen
	 * 
	 * @param mileage juoksukilometrit viikossa
	 */
	public void training(double mileage) {
		updateTrainingTable(mileage);
		if (mileage > threshold && level < 99) {
			level = level + Math.log((mileage / threshold - 1) * 100);
		} else { // kuntolaskee kun harjoittelu v�henee
			double drop = 100 * ((1.0 * mileage / threshold - 1) * ((1.0 * mileage / threshold - 1)));
			level = level - drop;
		}
		updateThreshold();
	}

	/**
	 * P�ivitt�� taulukon joka pit�� yll� kolmen viimeisen kuukauden viikottaista
	 * juoksikilometrej�
	 * 
	 * @param mileage viimeisimm�n kuukauden viikottaiset kilometrit
	 */
	private void updateTrainingTable(double mileage) {
		training[0] = training[1];
		training[1] = training[2];
		training[2] = mileage;
	}

	/**
	 * P�ivitt�� juoksukilometri tason mihin juoksija on sopeutunut
	 */
	private void updateThreshold() {
		threshold = 0.1 * training[0] + 0.3 * training[1] + 0.6 * training[2];
	}

	/**
	 * Loukkaantuminen
	 * @return true, jos tulee loukkaantuminen
	 */
	public boolean injury() {
		int probInt = (int) (injuryProb(threshold, training[2])*100);

		Random ran = new Random();
		int nxt = ran.nextInt(100);

		if (nxt < probInt) {
			return true;
		} else {
			return false;
		}

	}
	

	/**
	 * Loukkaantumisen todenn�k�isyys
	 */
	public double injuryProb(double threshold, double training) {
		double prob = 0;
		
		if ( training > threshold ) {
			prob = 3*(threshold / training - 1) * (threshold / training - 1);
			if (prob >= 0.95) {
				prob = 0.95;
		}
		}
		
		return prob;
	}
	

	/**
	 * Vertailee kahden juoksijan kisa tuloksia
	 */
	public static Comparator<Runner> timeComp = new Comparator<Runner>() {
		public int compare(Runner r1, Runner r2) {

			double result1 = r1.getResult();
			double result2 = r2.getResult();

			return Double.compare(result1, result2);
		}
	};

	@Override
	public String toString() {
		return name;
	}
	
	public String getName() {
		return this.name;
	}
	
	// P��ohjelman testej� varten
	public static void testiTulostus(Runner runner) {
		System.out.println("Taso: " + runner.getLevel());
		System.out.println("Kilometrit: " + runner.training[0] + ", " + runner.training[1] + ", " + runner.training[2]);
		System.out.println(runner.threshold);
		System.out.println(runner.injury());
	}


	/**
	 * TODO k�yt� loopia/aliohjelmaa
	 * 
	 * @param args ei k�yt�ss�
	 */
	public static void main(String[] args) {
		Runner runner = new Runner("A", 60);
		testiTulostus(runner);
		
		runner.training(60);
		testiTulostus(runner);
		
		runner.training(70);
		testiTulostus(runner);
		
		runner.training(80);
		testiTulostus(runner);

		runner.training(90);
		testiTulostus(runner);

		runner.training(40);
		testiTulostus(runner);

	}


}