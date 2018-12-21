package ProjetJava;

/**
 * @author De Mal Raphaël, Dieuzeide Gaël
 */
import java.awt.Dimension;
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

	public VoteVueGUI(Vote model, VoteController guiController, int posX, int posY) {

		super(model, guiController);

		// Construction de la fenÃƒÂªtre
		voteJFrame = new JFrame("vote MVC");
		voteJFrame.setVisible(true);
		voteJFrame.setResizable(false);
		voteJFrame.setPreferredSize(new Dimension(500, 90));
		voteJFrame.setFocusTraversalKeysEnabled(false);
		voteJFrame.setTitle("Cr\u00E9ateur de votes");
		voteJFrame.getContentPane().setLayout(null);
		zoneQuestion.setFont(new Font("Tahoma", Font.PLAIN, 13));
		zoneQuestion.setHorizontalAlignment(SwingConstants.CENTER);
		zoneQuestion.setBounds(0, 0, 348, 59);
		voteJFrame.getContentPane().add(zoneQuestion);
		zoneQuestion.setText("Combien de votants?");

		textField = new JTextField();
		textField.setBounds(349, 24, 145, 35);
		voteJFrame.getContentPane().add(textField);
		textField.setColumns(10);

		submit = new JButton("Valider");
		submit.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent arg0) {
				switch (phase) {
				case 0:
					while (true) {
						try {
							affiche("Combien de votants?");
							int c = Integer.parseInt(textField.getText());
							controller.nombredeVotants(c);
							break;
						} catch (InputMismatchException e) {
							affiche("Entrez un nombre s'il vous plait!");
							continue;
						}
					}
					phase++;
					break;

				case 1:
					while (true) {
						try {
							affiche("Combien de participants?");
							int c = Integer.parseInt(textField.getText());
							controller.nombredeParticipants(c);
							break;
						} catch (InputMismatchException e) {
							affiche("Entrez un nombre s'il vous plait!");
							continue;
						}
					}
					phase++;
					break;

				case 2:
					while (true) {
						try {
							affiche("Quel type de vote? Pour simple, tapez '1' ou pour Detaillé tapez '2' ?");
							int c = Integer.parseInt(textField.getText());
							controller.choix(c);
							break;
						} catch (InputMismatchException e) {
							affiche("Entrez '1'(Simple) ou '2'(Detaillé) s'il vous plait!");
							continue;
						}
					}
					phase++;
					break;

				case 3:
					for (int i = 0; i < controller.nombredeParticipants; i++) {

						affiche("Quel est le nom du participant numero " + (i + 1) + "?");
						String c = textField.getText();
						controller.nomParticipants(c, i);

					}
					phase++;
					break;

				case 4:
					if (controller.choix == 1) {
						for (int i = 0; i < controller.nombredeVotants; i++) {
							while (true) {
								try {
									affiche("Quel est ton vote?(Si le vote est un mauvais nombre: Vote blanc)\n"
											+ controller.str);
									int c = Integer.parseInt(textField.getText());
									controller.vote(c, i);
									break;
								} catch (InputMismatchException e) {
									affiche("Entrez un nombre s'il vous plait!\n");
									continue;
								}
							}
						}
						controller.depouillage();
						controller.pourcentages();
						controller.gagnant();
					} else {
						for (int i = 0; i < controller.nombredeVotants; i++) {
							while (true) {
								try {
									affiche("Donne ton nom,ton age et ton vote espacÃ©, ex:'Gael 20 3");
									String s = textField.getText();
									String decoupe[] = s.split(" ");
									String c = decoupe[0];
									int k = Integer.parseInt(decoupe[1]);
									int j = Integer.parseInt(decoupe[2]);
									controller.votantComplet(c, k, j, i);
									break;
								} catch (InputMismatchException e) {
									affiche("Entrez un nombre s'il vous plait!");
									continue;
								}

							}

						}
						controller.depouillage();
						controller.pourcentagesDetail();
					}
				}
			}
		});
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
	}

	public void affiche(String msg) {
		zoneQuestion.setText(msg);
	}

	@Override
	public void update(Observable o, Object arg) {

	}

}
