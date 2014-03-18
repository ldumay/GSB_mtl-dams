package gsb;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GuiMainPanel extends JFrame {

	private JPanel contentPane;
	private JTextField txtIdentifiant;
	private JTextField txtMotDePasse;
	public String Identifiant;
	public String MotDePasse;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField txtRapport;
	private JTextField txtRapportPraticien;
	private JTextField txtPraticienNumero;
	private JTextField txtPraticienNom;
	private JTextField txtPraticienPrenom;
	private JTextField txtPraticienAdresse;
	private JTextField txtPraticienVille;
	private JTextField txtPraticienCoef;
	private JTextField txtPraticienTypeCode;
	private JTextField txtPraticienCP;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// V�rification de la connexion avec la BDD
		InfosConnexionBDD InfosConnexionBDD = new InfosConnexionBDD();
		
		// D�marrage de l'interface primaire de l'application
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiMainPanel frame = new GuiMainPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GuiMainPanel() {
		setTitle("GSB - PPE4");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 950, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		final JPanel panelLog = new JPanel();
		final JPanel panelMenu = new JPanel();
		final JPanel panelAccueil = new JPanel();
		final JPanel panelRapport = new JPanel();
		final JPanel panelMedicaments = new JPanel();
		final JPanel panelAutresVisiteurs = new JPanel();
		final JPanel panelPraticiens = new JPanel();
		
		panelMenu.setVisible(false);
		panelRapport.setVisible(false);
		panelAccueil.setVisible(false);
		panelMedicaments.setVisible(false);
		panelPraticiens.setVisible(false);
		panelAutresVisiteurs.setVisible(false);
		panelLog.setVisible(true);
		
		panelLog.setBounds(10, 11, 914, 489);
		contentPane.add(panelLog);
		panelLog.setLayout(null);
		
		JLabel lblConnexion = new JLabel("Connexion");
		lblConnexion.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblConnexion.setBounds(798, 328, 106, 23);
		panelLog.add(lblConnexion);
		
		JComboBox logType = new JComboBox();
		logType.setModel(new DefaultComboBoxModel(new String[] {"Choisir un type", "Visiteur", "Praticien"}));
		logType.setBounds(764, 362, 140, 20);
		panelLog.add(logType);
		
		JLabel lblIdentifiant = new JLabel("Identifiant :");
		lblIdentifiant.setBounds(741, 399, 64, 14);
		panelLog.add(lblIdentifiant);
		
		JLabel lblMotDePasse = new JLabel("Mot de passe :");
		lblMotDePasse.setBounds(716, 427, 89, 14);
		panelLog.add(lblMotDePasse);
		
		txtIdentifiant = new JTextField();
		txtIdentifiant.setBounds(815, 396, 89, 20);
		panelLog.add(txtIdentifiant);
		txtIdentifiant.setColumns(10);
		
		txtMotDePasse = new JTextField();
		txtMotDePasse.setBounds(815, 424, 89, 20);
		panelLog.add(txtMotDePasse);
		txtMotDePasse.setColumns(10);
		
		JButton btnValider = new JButton("Valider");
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// R�cup�ration des Inputs
				Identifiant = txtIdentifiant.getText();
				MotDePasse = txtMotDePasse.getText();
				
				// JOptionPane.showMessageDialog(null,"Nom : "+Identifiant+" & Mdp "+MotDePasse);
				
				if( (Identifiant == "") || (MotDePasse == "") ){
					JOptionPane.showMessageDialog(null,"L'identifiant ou le mot de passe est invalide !");
				}
				else{
					String pilote = "com.mysql.jdbc.Driver";
					try {
						Class.forName(pilote);
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost/gsb","root","");
						Statement stmt = con.createStatement();
				
						ResultSet resultat = null;
						
						resultat = stmt.executeQuery("SELECT * FROM visiteur WHERE VIS_NOM='"+ txtIdentifiant.getText() + "'");
						if (resultat.next()) {
							String idClient = resultat.getString("VIS_MATRICULE");
							String date = resultat.getString("VIS_DATEEMBAUCHE");
							String[] dateSplit2 = date.split(" ");
							String[] dateSplit = dateSplit2[0].split("-");
							String jour = dateSplit[2];
							String mois = dateSplit[1];
							String annee = dateSplit[0];
							
							switch(mois){
								case "01" : {mois = "jan"; break;}
								case "02" : {mois = "feb"; break;}
								case "03" : {mois = "mar"; break;}
								case "04" : {mois = "apr"; break;}
								case "05" : {mois = "may"; break;}
								case "06" : {mois = "jun"; break;}
								case "07" : {mois = "jul"; break;}
								case "08" : {mois = "aug"; break;}
								case "09" : {mois = "sep"; break;}
								case "10" : {mois = "oct"; break;}
								case "11" : {mois = "nov"; break;}
								case "12" : {mois = "dec"; break;}
								default : {break;}
							}
							String date_emb = jour + "-" + mois + "-" + annee;
							String mdp = txtMotDePasse.getText();
							
							// V�rification du MDP
							// JOptionPane.showMessageDialog(null,"DONNEES : "+date_emb+" - "+mdp);
							
							if( mdp.equals(date_emb)){
								panelLog.setVisible(false);
								panelMenu.setVisible(true);
								panelAccueil.setVisible(true);
							}
							else{
								JOptionPane.showMessageDialog(null,"Oups, quelques chose n'a pas fonctionner !\nVeulliez r�essay�, SVP !", "Erreur de connexion", JOptionPane.WARNING_MESSAGE);
								// JOptionPane.showMessageDialog(null,txtMotDePasse.getText().length()+" et " +date_emb.length());
							}
						}
						}catch (SQLException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
			}
		});
		btnValider.setBounds(716, 455, 89, 23);
		panelLog.add(btnValider);
		
		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Modifications des Input
				txtIdentifiant.setText("");
				txtMotDePasse.setText("");
			}
		});
		btnAnnuler.setBounds(815, 455, 89, 23);
		panelLog.add(btnAnnuler);
		
		JLabel lblEtatDuServeur = new JLabel("Etat du serveur : ");
		lblEtatDuServeur.setBounds(115, 455, 95, 23);
		panelLog.add(lblEtatDuServeur);
		
		String EtatAff = "OFF";
		boolean EtatConnexion = InfosConnexionBDD.connexion;
		if(EtatConnexion == true){
			EtatAff = "ON";
		}
		
		JLabel lblEtat = new JLabel(EtatAff);
		lblEtat.setBounds(220, 455, 29, 23);
		panelLog.add(lblEtat);
		
		JButton btnAbout = new JButton("A propos");
		btnAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Infos infos = new Infos();
				infos.setVisible(true);
			}
		});
		btnAbout.setBounds(16, 455, 89, 23);
		panelLog.add(btnAbout);
		
		panelMenu.setBounds(10, 11, 198, 489);
		contentPane.add(panelMenu);
		panelMenu.setLayout(null);
		
		JLabel lblTitleMenu = new JLabel("Menu");
		lblTitleMenu.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTitleMenu.setBounds(10, 11, 178, 24);
		panelMenu.add(lblTitleMenu);
		
		JButton btnRapport = new JButton("Rapport de visite");
		btnRapport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelAccueil.setVisible(false);
				panelRapport.setVisible(true);
				panelMedicaments.setVisible(false);
				panelPraticiens.setVisible(false);
				panelAutresVisiteurs.setVisible(false);
			}
		});
		
		JButton btnAccueil = new JButton("Accueil");
		btnAccueil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelAccueil.setVisible(true);
				panelRapport.setVisible(false);
				panelMedicaments.setVisible(false);
				panelPraticiens.setVisible(false);
				panelAutresVisiteurs.setVisible(false);
			}
		});
		btnAccueil.setBounds(10, 56, 178, 37);
		panelMenu.add(btnAccueil);
		btnRapport.setBounds(10, 104, 178, 37);
		panelMenu.add(btnRapport);
		
		JButton btnMedicaments = new JButton("M\u00E9dicaments");
		btnMedicaments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelAccueil.setVisible(false);
				panelRapport.setVisible(false);
				panelMedicaments.setVisible(true);
				panelPraticiens.setVisible(false);
				panelAutresVisiteurs.setVisible(false);
			}
		});
		btnMedicaments.setBounds(10, 152, 178, 37);
		panelMenu.add(btnMedicaments);
		
		JButton btnPraticiens = new JButton("Praticiens");
		btnPraticiens.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelAccueil.setVisible(false);
				panelRapport.setVisible(false);
				panelMedicaments.setVisible(false);
				panelPraticiens.setVisible(true);
				panelAutresVisiteurs.setVisible(false);
			}
		});
		btnPraticiens.setBounds(10, 200, 178, 37);
		panelMenu.add(btnPraticiens);
		
		JButton btnAutresVisiteurs = new JButton("Autres Visiteurs");
		btnAutresVisiteurs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelAccueil.setVisible(false);
				panelRapport.setVisible(false);
				panelMedicaments.setVisible(false);
				panelPraticiens.setVisible(false);
				panelAutresVisiteurs.setVisible(true);
			}
		});
		btnAutresVisiteurs.setBounds(10, 248, 178, 37);
		panelMenu.add(btnAutresVisiteurs);
		
		JButton btnLogOut = new JButton("D\u00E9connexion");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMenu.setVisible(false);
				panelRapport.setVisible(false);
				panelAccueil.setVisible(false);
				panelMedicaments.setVisible(false);
				panelPraticiens.setVisible(false);
				panelAutresVisiteurs.setVisible(false);
				panelLog.setVisible(true);
			}
		});
		btnLogOut.setBounds(10, 441, 178, 37);
		panelMenu.add(btnLogOut);
		
		panelAccueil.setBounds(214, 11, 710, 489);
		contentPane.add(panelAccueil);
		panelAccueil.setLayout(null);
		
		JLabel lblTitleHome = new JLabel("Bienvenue dans l'application de Compte rendu de GSB");
		lblTitleHome.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTitleHome.setBounds(10, 11, 690, 24);
		panelAccueil.add(lblTitleHome);
		
		panelRapport.setBounds(214, 11, 710, 489);
		contentPane.add(panelRapport);
		panelRapport.setLayout(null);
		
		JLabel lblTitleRapport = new JLabel("Rapports");
		lblTitleRapport.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTitleRapport.setBounds(321, 5, 68, 19);
		panelRapport.add(lblTitleRapport);
		
		JComboBox listRapport = new JComboBox();
		listRapport.setModel(new DefaultComboBoxModel(new String[] {"Choisir la date du rendez-vous", "Rapport n�x du 00/00/0000"}));
		listRapport.setBounds(27, 67, 271, 20);
		panelRapport.add(listRapport);
		
		JButton btnRapportValider = new JButton("Valider");
		btnRapportValider.setBounds(308, 66, 89, 23);
		panelRapport.add(btnRapportValider);
		
		JLabel lblRapportNumber = new JLabel("Num\u00E9ro : ");
		lblRapportNumber.setBounds(27, 101, 68, 14);
		panelRapport.add(lblRapportNumber);
		
		txtRapport = new JTextField();
		txtRapport.setBounds(105, 98, 113, 20);
		panelRapport.add(txtRapport);
		txtRapport.setColumns(10);
		
		JLabel lblRapportPraticien = new JLabel("Praticien :");
		lblRapportPraticien.setBounds(27, 126, 68, 14);
		panelRapport.add(lblRapportPraticien);
		
		txtRapportPraticien = new JTextField();
		txtRapportPraticien.setBounds(105, 123, 113, 20);
		panelRapport.add(txtRapportPraticien);
		txtRapportPraticien.setColumns(10);
		
		JLabel lblRapportBilan = new JLabel("Bilan : ");
		lblRapportBilan.setBounds(27, 151, 46, 14);
		panelRapport.add(lblRapportBilan);
		
		JTextArea textRapportBilan = new JTextArea();
		textRapportBilan.setBounds(105, 154, 292, 77);
		panelRapport.add(textRapportBilan);
		
		JLabel lblRapportMotif = new JLabel("Motif : ");
		lblRapportMotif.setBounds(27, 242, 46, 14);
		panelRapport.add(lblRapportMotif);
		
		JTextArea txtRapportMotif = new JTextArea();
		txtRapportMotif.setBounds(105, 242, 292, 77);
		panelRapport.add(txtRapportMotif);
		
		panelMedicaments.setBounds(214, 11, 710, 489);
		contentPane.add(panelMedicaments);
		panelMedicaments.setLayout(null);
		
		JLabel lblTitleMedicaments = new JLabel("M�dicaments");
		lblTitleMedicaments.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTitleMedicaments.setBounds(306, 5, 98, 19);
		panelMedicaments.add(lblTitleMedicaments);
		
		JButton button = new JButton("<");
		button.setBounds(10, 455, 41, 23);
		panelMedicaments.add(button);
		
		JButton button_1 = new JButton(">");
		button_1.setBounds(96, 455, 41, 23);
		panelMedicaments.add(button_1);
		
		JLabel label = new JLabel("0/0");
		label.setBounds(61, 459, 25, 14);
		panelMedicaments.add(label);
		
		JLabel lblNewLabel = new JLabel("PRIX ECHANTILLON : ");
		lblNewLabel.setBounds(10, 429, 127, 14);
		panelMedicaments.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(136, 426, 53, 20);
		panelMedicaments.add(textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 145, 690, 67);
		panelMedicaments.add(scrollPane);
		
		JLabel lblNewLabel_1 = new JLabel("DEPOT LEGAL :");
		lblNewLabel_1.setBounds(10, 45, 98, 14);
		panelMedicaments.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("NOM COMMERCIAL :");
		lblNewLabel_2.setBounds(10, 70, 127, 14);
		panelMedicaments.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("FAMILLE :");
		lblNewLabel_3.setBounds(10, 95, 59, 14);
		panelMedicaments.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("COMPOSITION :");
		lblNewLabel_4.setBounds(10, 120, 98, 14);
		panelMedicaments.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("EFFETS :");
		lblNewLabel_5.setBounds(10, 223, 76, 14);
		panelMedicaments.add(lblNewLabel_5);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 248, 690, 67);
		panelMedicaments.add(scrollPane_1);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 351, 690, 67);
		panelMedicaments.add(scrollPane_2);
		
		JLabel lblNewLabel_6 = new JLabel("CONTRE INDIC. :");
		lblNewLabel_6.setBounds(10, 326, 127, 14);
		panelMedicaments.add(lblNewLabel_6);
		
		textField_1 = new JTextField();
		textField_1.setBounds(107, 42, 144, 20);
		panelMedicaments.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(137, 67, 150, 20);
		panelMedicaments.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(79, 92, 41, 20);
		panelMedicaments.add(textField_3);
		textField_3.setColumns(10);
		panelMedicaments.setVisible(false);
		
		panelPraticiens.setBounds(214, 11, 710, 489);
		contentPane.add(panelPraticiens);
		panelPraticiens.setLayout(null);
		
		JLabel lblTitlePraticiens = new JLabel("Praticiens");
		lblTitlePraticiens.setBounds(317, 5, 76, 19);
		lblTitlePraticiens.setFont(new Font("Tahoma", Font.BOLD, 15));
		panelPraticiens.add(lblTitlePraticiens);
		
		JLabel lblPraticienNumero = new JLabel("Num\u00E9ro : ");
		lblPraticienNumero.setBounds(34, 114, 127, 14);
		panelPraticiens.add(lblPraticienNumero);
		
		txtPraticienNumero = new JTextField();
		txtPraticienNumero.setBounds(182, 111, 200, 20);
		panelPraticiens.add(txtPraticienNumero);
		txtPraticienNumero.setColumns(10);
		
		JLabel lblPraticienNom = new JLabel("Nom : ");
		lblPraticienNom.setBounds(34, 145, 127, 14);
		panelPraticiens.add(lblPraticienNom);
		
		txtPraticienNom = new JTextField();
		txtPraticienNom.setBounds(182, 142, 200, 20);
		panelPraticiens.add(txtPraticienNom);
		txtPraticienNom.setColumns(10);
		
		JLabel lblPraticienPrenom = new JLabel("Pr\u00E9nom : ");
		lblPraticienPrenom.setBounds(34, 176, 127, 14);
		panelPraticiens.add(lblPraticienPrenom);
		
		txtPraticienPrenom = new JTextField();
		txtPraticienPrenom.setBounds(182, 173, 200, 20);
		panelPraticiens.add(txtPraticienPrenom);
		txtPraticienPrenom.setColumns(10);
		
		JLabel lblPraticienAdresse = new JLabel("Adresse :");
		lblPraticienAdresse.setBounds(34, 207, 127, 14);
		panelPraticiens.add(lblPraticienAdresse);
		
		txtPraticienAdresse = new JTextField();
		txtPraticienAdresse.setBounds(182, 204, 200, 20);
		panelPraticiens.add(txtPraticienAdresse);
		txtPraticienAdresse.setColumns(10);
		
		JLabel lblPraticienVille = new JLabel("Ville :");
		lblPraticienVille.setBounds(34, 238, 127, 14);
		panelPraticiens.add(lblPraticienVille);
		
		txtPraticienVille = new JTextField();
		txtPraticienVille.setBounds(182, 235, 200, 20);
		panelPraticiens.add(txtPraticienVille);
		txtPraticienVille.setColumns(10);
		
		txtPraticienCP = new JTextField();
		txtPraticienCP.setBounds(392, 235, 200, 20);
		panelPraticiens.add(txtPraticienCP);
		txtPraticienCP.setColumns(10);
		
		JLabel lblPraticienCoef = new JLabel("Coef. Notori\u00E9t\u00E9");
		lblPraticienCoef.setBounds(34, 269, 127, 14);
		panelPraticiens.add(lblPraticienCoef);
		
		txtPraticienCoef = new JTextField();
		txtPraticienCoef.setBounds(182, 266, 200, 20);
		panelPraticiens.add(txtPraticienCoef);
		txtPraticienCoef.setColumns(10);
		
		JLabel lblPraticienTypeCode = new JLabel("Type code");
		lblPraticienTypeCode.setBounds(34, 300, 127, 14);
		panelPraticiens.add(lblPraticienTypeCode);
		
		txtPraticienTypeCode = new JTextField();
		txtPraticienTypeCode.setBounds(182, 297, 200, 20);
		panelPraticiens.add(txtPraticienTypeCode);
		txtPraticienTypeCode.setColumns(10);
		
		JButton btnPraticienPrecedent = new JButton("<");
		btnPraticienPrecedent.setBounds(21, 363, 51, 23);
		panelPraticiens.add(btnPraticienPrecedent);
		
		JLabel lblPraticienTtl = new JLabel("00/00");
		lblPraticienTtl.setBounds(82, 367, 46, 14);
		panelPraticiens.add(lblPraticienTtl);
		
		JButton btnPraticienSuivant = new JButton(">");
		btnPraticienSuivant.setBounds(138, 363, 51, 23);
		panelPraticiens.add(btnPraticienSuivant);
		panelPraticiens.setVisible(false);
		
		panelAutresVisiteurs.setBounds(214, 11, 710, 489);
		contentPane.add(panelAutresVisiteurs);
		
		JLabel lblTitleAutresVisiteurs = new JLabel("Autres Visiteurs");
		lblTitleAutresVisiteurs.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTitleAutresVisiteurs.setBounds(10, 11, 690, 24);
		panelAutresVisiteurs.add(lblTitleAutresVisiteurs);
		
		// Masquage
		panelMenu.setVisible(false);
		panelAccueil.setVisible(false);
		panelAutresVisiteurs.setVisible(false);
		// panelLog.setVisible(false);
	}
}
