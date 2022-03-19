package wbPeli;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import peliSwing.PeliSwing;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * Käyttöliittymä WindowBuilderilla
 * @author Jyrki Mäki
 * 
 * TODO Kysy valmennettavan nimi. Tietoja välilehti ei päivity seuraava napista.
 * Tällä hetkellä vain yksinkertainen tallennus
 */
public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPane;
	private final JTextField textFieldHarj = new JTextField();;
	private final JTextPane textPanel = new JTextPane();
	private final JLabel lblKausi = new JLabel("Kausi: 1");
	private final JLabel lblKK = new JLabel("kk: 1");
	
	private final PeliSwing peliswing = new PeliSwing();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
					frame.alustaPeli();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		peliswing.setTextPanel(textPanel);
		peliswing.setTextFieldHarj(textFieldHarj);
		peliswing.setLblKausi(lblKausi);
		peliswing.setLblKk(lblKK);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 350);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("Menu");
		menuBar.add(menu);
		
		JMenuItem avaaMenu = new JMenuItem("Avaa");
		avaaMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				peliswing.avaa();
			}
		});
		menu.add(avaaMenu);
		
		JMenuItem tallennaMenu = new JMenuItem("Tallenna");
		tallennaMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				peliswing.tallenna();
			}
		});
		menu.add(tallennaMenu);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton btnKisa = new JButton("Kisa");
		btnKisa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				peliswing.aloitaKisa();
			}
		});
		panel.add(btnKisa);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		textPanel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_SPACE){
					peliswing.seuraavaKm();
				}
			}
		});
		
		textPanel.setEditable(false);
		scrollPane.setViewportView(textPanel);
		
		JButton btnTietoja = new JButton("Tietoja");
		btnTietoja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				peliswing.naytaTiedot();
			}
		});
		panel.add(btnTietoja);
		
		JButton btnSeuraava = new JButton("Seuraava");
		btnSeuraava.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				peliswing.seuraava();
			}
		});
		panel.add(btnSeuraava);
		
		panel.add(textFieldHarj);
		textFieldHarj.setColumns(10);
		
		JLabel labelHarjoittelu = new JLabel("km/vk");
		panel.add(labelHarjoittelu);
		
		panel.add(lblKausi);
		
		panel.add(lblKK);
		
	}
	
	protected void alustaPeli() {
		peliswing.alustaPeli();
	}

}
