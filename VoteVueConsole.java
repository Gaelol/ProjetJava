package ProjetJava.view;
/**
 * @author De Mal Raphaël, Dieuzeide Gaël
 */
import java.util.InputMismatchException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import ProjetJava.controller.VoteController;
import ProjetJava.model.Vote;

public class VoteVueConsole extends VoteVue implements Observer {
	protected Scanner sc;

	/**
	 * Scanner that open a new thread on the controller
	 * @param model
	 * @param controller
	 */
	public VoteVueConsole(Vote model, VoteController controller) {
		super(model, controller);
		update(null, null);
		sc = new Scanner(System.in);
		new Thread(new ReadInput()).start();
	}

	@Override
	/**
	 * update the the controller
	 */
	public void update(Observable o, Object arg) {
		System.out.println(model);
	}
/**
 * ask :
 * the number of votant
 * the number of candidates
 * the kind of vote
 * the names of the candidate
 * the names vote and age of votantes
 * give back the winner
 *
 */
	private class ReadInput implements Runnable {
		public void run() {
			while (true) {
				try {
					affiche("Combien de votants?");
					int c = sc.nextInt();
					controller.nombredeVotants(c);
					break;
				} catch (InputMismatchException e) {
					System.err.println("Entrez un nombre s'il vous plait!");
					sc.next();
					continue;
				}
			}

			while (true) {
				try {
					System.out.println("Combien de participants?");
					int c = sc.nextInt();
					controller.nombredeParticipants(c);
					break;
				} catch (InputMismatchException e) {
					System.err.println("Entrez un nombre s'il vous plait!");
					sc.next();
					continue;
				}
			}

			while (true) {
				try {
					System.out.println("Quel type de vote? 'Simple(1)' ou 'Detaille(2)'?");
					int c = sc.nextInt();
					controller.choix(c);
					break;
				} catch (InputMismatchException e) {
					System.err.println("Entrez 'Simple(1)' ou 'Detaille(2)' s'il vous plait!");
					sc.next();
					continue;
				}
			}

			switch (controller.choix) {

			case 1:

				controller.reinitialiserDb();

				for (int i = 0; i < controller.nombredeParticipants; i++) {

					System.out.println("Quel est le nom du participant numero " + (i + 1) + "?");
					String c = sc.next();
					controller.nomParticipants(c, i);

				}

				for (int i = 0; i < controller.nombredeVotants; i++) {
					while (true) {
						try {
							affiche("Quel est ton vote?(Si le vote est un mauvais nombre: Vote blanc)\n"
									+ controller.str);
							int c = sc.nextInt();
							controller.vote(c, i);
							break;
						} catch (InputMismatchException e) {
							System.err.println("Entrez un nombre s'il vous plait!\n");
							sc.next();
							continue;
						}
					}
				}

				controller.depouillage();
				controller.pourcentages();
				controller.gagnant();
				break;

			case 2:

				controller.reinitialiserDb();

				for (int i = 0; i < controller.nombredeParticipants; i++) {

					affiche("Quel est le nom du participant numero " + (i + 1) + "?");
					String c = sc.next();
					controller.nomParticipants(c, i);

				}

				for (int i = 0; i < controller.nombredeVotants; i++) {

					while (true) {
						try {
							affiche("Comment t'appelles-tu?");
							String c = sc.next();
							affiche("Quel Ã¢ge as-tu?");
							int k = sc.nextInt();
							affiche("Quel est ton vote?(Si le vote est un mauvais nombre: Vote blanc)\n"
									+ controller.str);
							int j = sc.nextInt();
							controller.votantComplet(c, k, j, i);
							break;
						} catch (InputMismatchException e) {
							System.err.println("Entrez un nombre s'il vous plait!");
							sc.next();
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
/**
 * define the way to print on the views
 */
	@Override
	public void affiche(String string) {
		System.out.println(string);

	}
}
