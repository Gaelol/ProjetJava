package ProjetJava.test;

import ProjetJava.controller.VoteController;
import ProjetJava.model.Vote;
import ProjetJava.view.VoteVueConsole;
import ProjetJava.view.VoteVueGUI;

/**
 * @author De Mal Raphaël, Dieuzeide Gaël
 */
public class VoteMVC {
	public VoteMVC() {
		/**
		 * model creation
		 */
		Vote model = new Vote();
		
		/**
		 * Create controller : one for each vue
		 * Each controller needs a reference to the model for commanding him
		 */
	
		VoteController consoleController = new VoteController(model);
		VoteController guiController = new VoteController(model);
		
		/**
		 * view creation
		 * each view need to know the controler and to have a reference to model for watching him
		 */
		
		VoteVueConsole console = new VoteVueConsole(model, consoleController);
		VoteVueGUI gui = new VoteVueGUI(model, guiController, 0, 0);

		/**
		 * give reference and vue to the controller
		 */
		guiController.addView(gui);
		consoleController.addView(console);

	}

	public static void main(String args[]) {
		/**
		 * Schedule a job for the event-dispatching thread:
		 * creating and showing this application's GUI.
		 */
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new VoteMVC();
			}
		});
	}
}
