package peliSwing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.swing.JTextField;
import javax.swing.JTextPane;

import RunnerCoachPeli.Race;
import RunnerCoachPeli.RaceEng;
import RunnerCoachPeli.Result;
import RunnerCoachPeli.Runner;
import RunnerCoachPeli.Seasons;

import javax.swing.JLabel;
import javax.swing.JOptionPane;


/**
 * Luokka käsittelee Swing-komponentteja
 * @author Jyrki Mäki
 */
public class PeliSwing {
	
	private JTextPane textPanel;
	private JTextField textFieldHarj;
	private Seasons seasons;
	private RaceEng raceEng;
	private Runner valmennettava;
	private JLabel lblKausi;
	private JLabel lblKK;
	boolean kisattu = false; // Onko kuukaden kisa jo juostu
	List<List<Result>> kisaValiajat;
	int i = 0; // kisan väliajan indeksi (km = i + 1)

	public void setTextPanel(JTextPane textP) {
		textPanel = textP;
	}

	public void alustaPeli() {
		valmennettava = new Runner("Topi (valmennettava)", 70);
		valmennettava.setValmennettavaksi();
		Runner runner2 = new Runner("Lasse", 90);
		Runner runner3 = new Runner("Kenenisa", 98);
		Runner runner4 = new Runner("Juha", 80);

		ArrayList<Runner> runners = new ArrayList<Runner>();
		runners.add(valmennettava); // TODO siirrä lisäys vasta siihen vaiheeseen kun kisa valitaan
		runners.add(runner2);
		runners.add(runner3);
		runners.add(runner4);
		
		seasons = new Seasons(runners);
		raceEng = new RaceEng();
	}


	public void naytaTiedot() {
		textPanel.setText(String.format("Kausi: %d Kuukausi: %d%nSeuraava kisa: %s%nTaso: %.0f", seasons.getSeason(), seasons.getMonth(), seasons.getRace(), valmennettava.getLevel()));
	}

	
	/**
	 * Aloittaa kisan
	 * TODO ilmoita onko valmennettava mukana
	 */
	public void aloitaKisa() {
		if (!kisattu) {
	        
			seasons.addRunners2Race();
			Race race = seasons.getRace();
			
			if (valmennettava.getLevel() < race.levelLimit()) {
				JOptionPane.showMessageDialog(null, "Taso ei riitä kisaan");
			}
	        
			raceEng.raceSim(race);
	        kisaValiajat = race.getResults();
	        
	        for (List<Result> results: kisaValiajat) {
			Collections.sort(results, Result.timeComp);
	        }

	        naytaTulos();
						
			kisattu = true;
			} else {
			textPanel.setText("Olet jo kisannut tässä kuussa/kisan on käynnissä");
		}
	}
	
	/**
	 * Laittaa tuloksen
	 * @param str
	 */
	public void naytaTulos() {
        StringBuilder str = new StringBuilder();

		if (i == 0) {
			str.append("Seuraava km painamalla välilyöntiä. Lopussa mene takaisin alkuun.\n");
		}
		
        List<Result> valiaika;
        
			valiaika = kisaValiajat.get(i);
			
			str.append((i+1) + " km\n");
			
			for (Result result : valiaika) {
				str.append(String.format("%-30s%s\n", result.getRunner(), convertTime(result.getTime())));
			}
			str.append("-----------\n");
			textPanel.setText(str.toString());	
	}
	
	/**
	 * Näyttää seuraavan kilometrin väliajat
	 */
	public void seuraavaKm() {
		if (i+1 < kisaValiajat.size()) {
		i++;
		naytaTulos();
		} else {
		i = 0;	
		naytaTulos();
		}
	}

	/**
	 * Siirtyy seuraavaan kuukauteen tai loukkaantumisen verran kuukausia eteenpäin
	 */
	public void seuraava() {
		String harj = textFieldHarj.getText();
		int harjoittelu = 0;
		i = 0;
		
		try {
		if (!harj.equals("")) {

		harjoittelu = Integer.parseInt(textFieldHarj.getText());

		valmennettava.training(harjoittelu);
		
		if (valmennettava.injury()) { // TODO eripituiset loukkaantumiset
			JOptionPane.showMessageDialog(null, "Loukkaantuminen 1kk");
			seasons.nextMonth(1);
			valmennettava.training(0);
			seasons.nextMonth(1);

		} else {
			seasons.nextMonth(1);
		}
		
		} else {
			JOptionPane.showMessageDialog(null, "Anna harjoittelu kilometrit");
		}
		paivitaTiedot();
		kisattu = false;
		} catch (NumberFormatException e) {
		JOptionPane.showMessageDialog(null, "Virheellinen syöte kilometreissä");
	}
	}
	
	public void paivitaTiedot() {
		lblKausi.setText("Kausi: " + String.valueOf(seasons.getSeason()));
		lblKK.setText(String.valueOf("kk: " + seasons.getMonth()));
	}

	public void setTextFieldHarj(JTextField textField) {
		textFieldHarj = textField;
	}

	public void setLblKausi(JLabel lblK) {
		lblKausi = lblK;
	}

	public void setLblKk(JLabel lblkk) {
		lblKK = lblkk;
	}
	
	/**
	 * Muuntaa sekunnit minuuteiksi ja sekunneiksi
	 * @param time
	 * @return
	 */
	public String convertTime(double time) {
		int seconds = (int) time % 60;
		int minutes = (int) time / 60;

		return minutes + ":" + String.format("%02d", seconds);
	}
	
	public void avaa() {
		 try {
		        File myObj = new File("save.txt");
		        Scanner myReader = new Scanner(myObj);
		        while (myReader.hasNextLine()) {
		          String data = myReader.nextLine();
		      	String palat[]= data.split("/");
		      		valmennettava.setName(palat[0]);
		      		valmennettava.setLevel(Double.parseDouble(palat[1]));
		      		seasons.setSeason(Integer.parseInt(palat[2]));
		      		seasons.setMonth(Integer.parseInt(palat[3]));
		      		paivitaTiedot();
		        }
		        myReader.close();
		      } catch (FileNotFoundException e) {
		        e.printStackTrace();
		      }
		    }
	
	public void tallenna() {
	    try {
	        FileWriter myWriter = new FileWriter("save.txt");
	        myWriter.write(String.format("%s/%s/%s/%s", valmennettava.getName(), valmennettava.getLevel(), seasons.getSeason(), seasons.getMonth()));
	        myWriter.close();
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	    }
	

}
