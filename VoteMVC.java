package ProjetJava;
/**
 * @author De Mal Raphaël, Dieuzeide Gaël
 */
public class VoteMVC {
	public VoteMVC() {
		// CrÃƒÂ©ation du modÃƒÂ¨le
		Vote model = new Vote();

		// CrÃƒÂ©ation des contrÃƒÂ´leurs : Un pour chaque vue
		// Chaque contrÃƒÂ´leur doit avoir une rÃƒÂ©fÃƒÂ©rence vers le modÃƒÂ¨le pour
		// pouvoir le commander
		VoteController consoleController = new VoteController(model);
		VoteController guiController = new VoteController(model);

		// CrÃƒÂ©ation des vues.
		// Chaque vue doit connaÃƒÂ®tre son contrÃƒÂ´leur et avoir une rÃƒÂ©fÃƒÂ©rence
		// vers le modÃƒÂ¨le pour pouvoir l'observer
		VoteVueConsole console = new VoteVueConsole(model, consoleController);
		VoteVueGUI gui = new VoteVueGUI(model, guiController, 0, 0);

		// On donne la rÃƒÂ©fÃƒÂ©rence ÃƒÂ  la vue pour chaque contrÃƒÂ´leur
		guiController.addView(gui);
		consoleController.addView(console);

	}

	public static void main(String args[]) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new VoteMVC();
			}
		});
	}
}
