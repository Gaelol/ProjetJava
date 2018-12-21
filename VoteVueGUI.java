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

	private JFrame voteJFrame;
	private final JLabel zoneQuestion = new JLabel("");
	private JTextField textField;
	private JButton submit;
	private int phase = 0;
	private JButton valider;
	private int numeroCandidat = 1;
	private int numeroVotant = 1;

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

		// Construction de la fenÃƒÂªtre/
		voteJFrame = new JFrame("vote MVC");
		voteJFrame.setVisible(true);
		voteJFrame.setResizable(false);
		voteJFrame.setBounds(600, 90, 600, 90);
		voteJFrame.setFocusTraversalKeysEnabled(false);
		voteJFrame.setTitle("Cr\u00E9ateur de votes");
		voteJFrame.getContentPane().setLayout(null);
		zoneQuestion.setFont(new Font("Tahoma", Font.PLAIN, 13));
		zoneQuestion.setHorizontalAlignment(SwingConstants.CENTER);
		zoneQuestion.setBounds(0, 0, 449, 59);
		voteJFrame.getContentPane().add(zoneQuestion);
		zoneQuestion.setText("Combien de Votants y a-t-il?");

		textField = new JTextField();
		textField.setBounds(449, 24, 145, 35);
		voteJFrame.getContentPane().add(textField);
		textField.setColumns(10);

		valider = new JButton("Valider");
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
							controller.vote(d, numeroCandidat - 1);
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
						for (int i = 0; i < controller.nombredeVotants; i++) {
							while (true) {
								try {
									affiche("Donne ton nom,ton age et ton vote espacÃ©, ex:'Gael 20 3");
									String s = textField.getText();
									String decoupe[] = s.split(" ");
									String d = decoupe[0];
									int k = Integer.parseInt(decoupe[1]);
									int j = Integer.parseInt(decoupe[2]);
									controller.votantComplet(d, k, j, i);
									break;
								} catch (InputMismatchException d) {
									affiche("Entrez un nombre s'il vous plait!");
									continue;
								}

							}

						}
						controller.depouillage();
						controller.pourcentagesDetail();
						break;
					}
				}
			}
		});
		valider.setBounds(449, 0, 145, 23);
		voteJFrame.getContentPane().add(valider);
	}

	public void affiche(String msg) {
		zoneQuestion.setText(msg);
	}

	@Override
	public void update(Observable o, Object arg) {

	}
}
