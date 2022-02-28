package peliSwing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JTextField;
import javax.swing.JTextPane;

import RunnerCoachPeli.Race;
import RunnerCoachPeli.RaceEng;
import RunnerCoachPeli.Runner;
import RunnerCoachPeli.Seasons;

import javax.swing.JLabel;
import javax.swing.JOptionPane;


/**
 * Luokka k‰sittelee Swing-komponentteja
 * @author Jyrki M‰ki
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
	List<List<String>> kisaValiajat;
	int i = 0; // kisan v‰liajan indeksi (km = i + 1)

	public void setTextPanel(JTextPane textP) {
		textPanel = textP;
	}

	public void alustaPeli() {
		valmennettava = new Runner("Topi (valmennettava)", 70);
		valmennettava.setValmennettavaksi();
		Runner runner2 = new Runner("Lasse", 90);
		Runner runner3 = new Runner("Kenenisa", 98);

		ArrayList<Runner> runners = new ArrayList<Runner>();
		runners.add(valmennettava); // TODO siirr‰ lis‰ys vasta siihen vaiheeseen kun kisa valitaan
		runners.add(runner2);
		runners.add(runner3);
		
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
				JOptionPane.showMessageDialog(null, "Taso ei riit‰ kisaan");
			}
	        
	        kisaValiajat = raceEng.raceSim(race);

	        naytaTulos();
						
			kisattu = true;
			} else {
			textPanel.setText("Olet jo kisannut t‰ss‰ kuussa/kisan on k‰ynniss‰");
		}
	}
	
	/**
	 * Laittaa tuloksen
	 * @param str
	 */
	public void naytaTulos() {
        StringBuilder str = new StringBuilder();

		if (i == 0) {
			str.append("Seuraava km painamalla v‰lilyˆnti‰. Lopussa mene takaisin alkuun.\n");
		}
		
        List<String> valiaika;
        
			valiaika = kisaValiajat.get(i);
			
			str.append((i+1) + " km\n");
			
			for (String tulos : valiaika) {
				str.append(tulos);
			}
			str.append("-----------\n");
			textPanel.setText(str.toString());	
	}
	
	/**
	 * N‰ytt‰‰ seuraavan kilometrin v‰liajat
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
	 * Siirtyy seuraavaan kuukauteen tai loukkaantumisen verran kuukausia eteenp‰in
	 */
	public void seuraava() {
		String harj = textFieldHarj.getText();
		int harjoittelu = 0;
		
		try {
		if (!harj.equals("")) {

		harjoittelu = Integer.parseInt(textFieldHarj.getText());

		valmennettava.training(harjoittelu);
		
		if (valmennettava.injury()) { // TODO eripituiset loukkaantumiset
			JOptionPane.showMessageDialog(null, "Loukkaantuminen 2kk");
			seasons.nextMonth(2);
		} else {
			seasons.nextMonth(1);
		}
		
		} else {
			JOptionPane.showMessageDialog(null, "Anna harjoittelu kilometrit");
		}
		paivitaTiedot();
		kisattu = false;
		} catch (NumberFormatException e) {
		JOptionPane.showMessageDialog(null, "Virheellinen syˆte kilometreiss‰");
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
