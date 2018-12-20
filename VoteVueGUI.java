package ProjetJava;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.eclipse.wb.swing.FocusTraversalOnArray;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class VoteVueGUI extends JFrame {

	private JPanel contentPane;
	private JLabel lblVeuillezSuivreLes;
	private JLabel questionUtilisateur;
	private JPanel cadreQuestion;
	private JButton btnSuivant;
	private JLabel erreursPossibles;
	private JTextField reponse;
	private JRadioButton boutonSimple;
	private JRadioButton boutonDetaille;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	private String contReponse;
	private int nbrVotants;
	private int nbrParticipants;
	private int numeroParticipant = 1;
	private int etapeQuestion = 0;
	private String prenomsParticipants[];
	private String nomsParticipants[];
	private Boolean estSimple = true;
	private int phase = 0;

	
	public static boolean isParsable(String input){
	    boolean parsable = true;
	    try{
	        Integer.parseInt(input);
	    }catch(NumberFormatException  e){
	        parsable = false;
	    }
	    return parsable;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VoteVueGUI frame = new VoteVueGUI();
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
	public VoteVueGUI() {
		setTitle("Cr\u00E9ateur de votes");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{472, 86, 0};
		gbl_contentPane.rowHeights = new int[]{14, 37, 23, 155, 10, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		lblVeuillezSuivreLes = new JLabel("Veuillez suivre les instructions suivantes pour cr\u00E9er vos \u00E9lections person\u00E9lis\u00E9es : ");
		GridBagConstraints gbc_lblVeuillezSuivreLes = new GridBagConstraints();
		gbc_lblVeuillezSuivreLes.insets = new Insets(0, 0, 5, 5);
		gbc_lblVeuillezSuivreLes.gridx = 0;
		gbc_lblVeuillezSuivreLes.gridy = 0;
		contentPane.add(lblVeuillezSuivreLes, gbc_lblVeuillezSuivreLes);
		
		btnSuivant = new JButton("Suivant");
		btnSuivant.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				switch(phase) {
					case 0:	contReponse = reponse.getText();
							if(isParsable(contReponse)) {
								nbrVotants = Integer.parseInt(contReponse);
								erreursPossibles.setText("");
								phase++;
								questionUtilisateur.setText("Combien de personnes peuvent-elles voter?");
							} else {
								erreursPossibles.setText("Vous devez entrer un nombre!");
							}
							break;
					case 1: contReponse = reponse.getText();
							if(isParsable(contReponse)) {
								nbrParticipants = Integer.parseInt(contReponse);
								erreursPossibles.setText("");
								phase++;
								questionUtilisateur.setText("Veuillez choisir le mode de vote simple ou détaillé");
								boutonSimple.setEnabled(true);
								boutonDetaille.setEnabled(true);
							} else {
								erreursPossibles.setText("Vous devez entrer un nombre!");
							}
							break;
					case 2: if(boutonDetaille.isSelected()) {
								estSimple = false;
							}
							boutonSimple.setEnabled(false);
							boutonDetaille.setEnabled(false);
							phase++;
							questionUtilisateur.setText("Quel est le nom du 1er participant?");
							break;
					case 3: if(numeroParticipant<=1) {
								if(etapeQuestion<=0) {
									contReponse = reponse.getText();
									nomsParticipants = new String[] {contReponse};
									etapeQuestion++;
									questionUtilisateur.setText("Quel est le prénom du 1er participant?");
								} else {
									contReponse = reponse.getText();
									prenomsParticipants = new String[] {contReponse};
									etapeQuestion=0;
									numeroParticipant++;
									questionUtilisateur.setText("Quel est le nom du "+ numeroParticipant +"eme participant?");
								}
							} else if(numeroParticipant>1) {
								if(etapeQuestion==0) {
									contReponse = reponse.getText();
									nomsParticipants = new String[] {contReponse};
									etapeQuestion++;
									questionUtilisateur.setText("Quel est le prénom du "+ numeroParticipant +"eme participant?");
								} else {
									contReponse = reponse.getText();
									prenomsParticipants = new String[] {contReponse};
									etapeQuestion=0;
									numeroParticipant++;
									if(numeroParticipant-1>=nbrParticipants) {
										phase++;
										break;
									} else {
										questionUtilisateur.setText("Quel est le nom du "+ numeroParticipant +"eme participant?");
									}
								}
							}
							break;
				}
				
			}
		});
		
		cadreQuestion = new JPanel();
		cadreQuestion.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Instructions", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_cadreQuestion = new GridBagConstraints();
		gbc_cadreQuestion.fill = GridBagConstraints.HORIZONTAL;
		gbc_cadreQuestion.insets = new Insets(0, 0, 5, 5);
		gbc_cadreQuestion.gridx = 0;
		gbc_cadreQuestion.gridy = 1;
		contentPane.add(cadreQuestion, gbc_cadreQuestion);
		GridBagLayout gbl_cadreQuestion = new GridBagLayout();
		gbl_cadreQuestion.columnWidths = new int[]{449, 0};
		gbl_cadreQuestion.rowHeights = new int[]{0, 0};
		gbl_cadreQuestion.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_cadreQuestion.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		cadreQuestion.setLayout(gbl_cadreQuestion);
		
		questionUtilisateur = new JLabel("Combien y a-t-il de personnes a elire?");
		GridBagConstraints gbc_questionUtilisateur = new GridBagConstraints();
		gbc_questionUtilisateur.gridx = 0;
		gbc_questionUtilisateur.gridy = 0;
		cadreQuestion.add(questionUtilisateur, gbc_questionUtilisateur);
		cadreQuestion.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{questionUtilisateur}));
		
		reponse = new JTextField();
		reponse.setHorizontalAlignment(SwingConstants.CENTER);
		reponse.setFont(new Font("Tahoma", Font.PLAIN, 11));
		reponse.setText("repondez ici");
		GridBagConstraints gbc_reponse = new GridBagConstraints();
		gbc_reponse.insets = new Insets(0, 0, 5, 0);
		gbc_reponse.fill = GridBagConstraints.HORIZONTAL;
		gbc_reponse.gridx = 1;
		gbc_reponse.gridy = 1;
		contentPane.add(reponse, gbc_reponse);
		reponse.setColumns(10);
		GridBagConstraints gbc_btnSuivant = new GridBagConstraints();
		gbc_btnSuivant.insets = new Insets(0, 0, 5, 5);
		gbc_btnSuivant.gridx = 0;
		gbc_btnSuivant.gridy = 2;
		contentPane.add(btnSuivant, gbc_btnSuivant);
		
		boutonSimple = new JRadioButton("Simple");
		boutonSimple.setSelected(true);
		boutonSimple.setEnabled(false);
		buttonGroup.add(boutonSimple);
		GridBagConstraints gbc_simple = new GridBagConstraints();
		gbc_simple.anchor = GridBagConstraints.SOUTHWEST;
		gbc_simple.insets = new Insets(0, 0, 5, 0);
		gbc_simple.gridx = 1;
		gbc_simple.gridy = 2;
		contentPane.add(boutonSimple, gbc_simple);
		
		erreursPossibles = new JLabel("");
		GridBagConstraints gbc_erreursPossibles = new GridBagConstraints();
		gbc_erreursPossibles.insets = new Insets(0, 0, 5, 5);
		gbc_erreursPossibles.gridx = 0;
		gbc_erreursPossibles.gridy = 3;
		contentPane.add(erreursPossibles, gbc_erreursPossibles);
		
		boutonDetaille = new JRadioButton("Detaill\u00E9");
		boutonDetaille.setEnabled(false);
		buttonGroup.add(boutonDetaille);
		GridBagConstraints gbc_detaille = new GridBagConstraints();
		gbc_detaille.anchor = GridBagConstraints.NORTHWEST;
		gbc_detaille.insets = new Insets(0, 0, 5, 0);
		gbc_detaille.gridx = 1;
		gbc_detaille.gridy = 3;
		contentPane.add(boutonDetaille, gbc_detaille);
		
		JLabel lblBienvenueDansLe = new JLabel("Bienvenue dans le cr\u00E9ateur de votes de Rapha\u00EBl De Mal, Ga\u00EBl Dieuzeide et Cl\u00E9ment Berlanger");
		lblBienvenueDansLe.setFont(new Font("Tahoma", Font.PLAIN, 8));
		GridBagConstraints gbc_lblBienvenueDansLe = new GridBagConstraints();
		gbc_lblBienvenueDansLe.anchor = GridBagConstraints.SOUTH;
		gbc_lblBienvenueDansLe.insets = new Insets(0, 0, 0, 5);
		gbc_lblBienvenueDansLe.gridx = 0;
		gbc_lblBienvenueDansLe.gridy = 4;
		contentPane.add(lblBienvenueDansLe, gbc_lblBienvenueDansLe);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{contentPane}));
	}
	
}
