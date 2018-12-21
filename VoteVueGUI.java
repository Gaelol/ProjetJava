package ProjetJava;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.InputMismatchException;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class VoteVueGUI extends VoteVue {

	/**
	 * the creator of the JFrame
	 */
	private JFrame voteJFrame;
	/**
	 * the JLabel where we interact with the user
	 */
	private final JLabel zoneQuestion = new JLabel("");
	/**
	 * the JTextField where communicate with the user
	 */
	private JTextField textField;
	/**
	 * an int used to determine where at witch phase of the app we are
	 */
	private int phase = 0;
	/**
	 * the button the user presses to submit his answer
	 */
	private JButton valider;
	/**
	 * an int used as an iterator in phase 3
	 */
	private int numeroCandidat = 1;
	/**
	 * an int used as an iterator in phase 4
	 */
	private int numeroVotant = 1;
	/**
	 * this method takes a string and makes sure it is parsable
	 * @param input the String to be parsed
	 * @return parsable is true if the String is parsable
	 */
	public static boolean isParsable(String input) {
		boolean parsable = true;
		try {
			Integer.parseInt(input);
		} catch (NumberFormatException e) {
			parsable = false;
		}
		return parsable;
	}

	public VoteVueGUI(Vote model, VoteController guiController, int posX, int posY) {

		super(model, guiController);

		// Construction de la fenetre/
		voteJFrame = new JFrame("vote MVC");
		voteJFrame.setVisible(true);
		voteJFrame.setResizable(false);
		voteJFrame.setBounds(1000, 200, 1000, 200);
		voteJFrame.setFocusTraversalKeysEnabled(false);
		voteJFrame.setTitle("Cr\u00E9ateur de votes");
		voteJFrame.getContentPane().setLayout(null);
		zoneQuestion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		zoneQuestion.setHorizontalAlignment(SwingConstants.CENTER);
		zoneQuestion.setBounds(0, 0, 854, 171);
		voteJFrame.getContentPane().add(zoneQuestion);
		zoneQuestion.setText("Combien de Votants y a-t-il?");

		textField = new JTextField();
		textField.setBounds(854, 77, 140, 35);
		voteJFrame.getContentPane().add(textField);
		textField.setColumns(10);

		valider = new JButton("Valider");
		/**
		 * this method reacts to the button valider being clicked and reacts to it with a switch where every case is a step in the app
		 */
		valider.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent f) {
				switch (phase) {
				case 0:
					controller.reinitialiserDb();
					if (isParsable(textField.getText())) {
						int c = Integer.parseInt(textField.getText());
						controller.nombredeVotants(c);
						phase++;
						affiche("Combien de participants?");
					} else {
						affiche("Vous devez entrer un nombre!");
					}
					break;

				case 1:
					if (isParsable(textField.getText())) {
						int g = Integer.parseInt(textField.getText());
						controller.nombredeParticipants(g);
						affiche("Quel type de vote? Pour simple, tapez '1' ou pour detaillé tapez '2' ?");
						phase++;
					} else {
						affiche("Vous devez entrer un nombre!");
						break;
					}
					break;
				case 2:
					if (isParsable(textField.getText())) {
						int x = Integer.parseInt(textField.getText());
						if (x != 1 && x != 2) {
							affiche("Entrez '1'(Simple) ou '2'(Detaillé) s'il vous plait!");
							break;
						} else {
							controller.choix(x);
						}
					} else {
						affiche("Vous devez entrer un nombre!");
						break;
					}
					affiche("Quel est le nom du participant numero 1?");
					phase++;
					break;

				case 3:
					String z = textField.getText();
					controller.nomParticipants(z, numeroCandidat - 1);
					affiche("Quel est le nom du participant numero " + (numeroCandidat + 1) + "?");
					numeroCandidat++;
					if (numeroCandidat - 1 >= controller.nombredeParticipants) {
						if (controller.choix == 1) {
							affiche("Quel est ton vote?" + controller.str
									+ ". Veuillez entrer le numéro correspondant à la personne pour laquelle vous désirez voter.");
						} else {
							affiche("Veuillez entrer votre nom,votre age et votre vote espacé, ex:'Gael 20 3'");
						}
						phase++;
					}
					break;
				case 4:
					if (controller.choix == 1) {
						if (isParsable(textField.getText())) {
							int d = Integer.parseInt(textField.getText());
							controller.vote(d, numeroVotant - 1);
							numeroVotant++;
							if (numeroVotant - 1 >= controller.nombredeVotants) {
								controller.depouillage();
								controller.pourcentages();
								controller.gagnant();
								break;
							}
						} else {
							affiche("Entrez un nombre s'il vous plait!\n");
						}
					} else {
						affiche("Donne ton nom,ton age et ton vote espacÃ© et sans autre espace supplémentaire, ex:'Gael 20 3\'");
						String s = textField.getText();
						String decoupe[] = s.split(" ");
						String d = decoupe[0];
						int k = Integer.parseInt(decoupe[1]);
						int j = Integer.parseInt(decoupe[2]);

						controller.votantComplet(d, k, j, numeroVotant - 1);
						numeroVotant++;
						if (numeroVotant - 1 >= controller.nombredeVotants) {
							controller.depouillage();
							controller.pourcentagesDetail();
							break;
						}
						break;
					}
				}
			}
		});
		valider.setBounds(854, 56, 140, 23);
		voteJFrame.getContentPane().add(valider);
	}
	/**
	 * this method takes a string and places it in the textZone of the zoneQuestion
	 * @param msg the String being placed in the textZone
	 */
	public void affiche(String msg) {
		zoneQuestion.setText(msg);
	}

	@Override
	public void update(Observable o, Object arg) {

	}
}
