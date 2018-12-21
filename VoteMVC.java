package ProjetJava;

import ProjetJava.VoteController;
import ProjetJava.Vote;
import ProjetJava.VoteMVC;
import ProjetJava.VoteVue;
import ProjetJava.VoteVueConsole;
import ProjetJava.VoteVueGUI;

public class VoteMVC {
	public VoteMVC() {
		//CrÃ©ation du modÃ¨le
		Vote model = new Vote();
		//TODO

		//CrÃ©ation des contrÃ´leurs : Un pour chaque vue
		//Chaque contrÃ´leur doit avoir une rÃ©fÃ©rence vers le modÃ¨le pour pouvoir le commander
		VoteController consoleController = new VoteController(model);
		VoteController guiController = new VoteController(model);
		
		
		
		//CrÃ©ation des vues.
		//Chaque vue doit connaÃ®tre son contrÃ´leur et avoir une rÃ©fÃ©rence vers le modÃ¨le pour pouvoir l'observer
		VoteVueConsole console = new VoteVueConsole(model,consoleController);
		VoteVueGUI gui = new VoteVueGUI(model,guiController,0,0);
		
		//TODO
		
		//On donne la rÃ©fÃ©rence Ã  la vue pour chaque contrÃ´leur
		guiController.addView(gui);
		consoleController.addView(console);
		
		//TODO
		
		
	}

	public static void main(String args[]) {
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new VoteMVC();
			}
		});
	}
}

