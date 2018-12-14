package ProjetJava;

import ProjetJava.VoteController;
import ProjetJava.Vote;
import ProjetJava.VoteMVC;
import ProjetJava.VoteVue;
import ProjetJava.VoteVueConsole;
import ProjetJava.VoteVueGUI;

public class VoteMVC {
	public VoteMVC() {
		//Création du modèle
		Vote model = new Vote();
		//TODO

		//Création des contrôleurs : Un pour chaque vue
		//Chaque contrôleur doit avoir une référence vers le modèle pour pouvoir le commander
		VoteController ctrlGUI = new VoteController(model);
		VoteController ctrlConsole = new VoteController(model);
		
		//TODO
		
		Object posX = 0;
		Object posY = 0;
		//Création des vues.
		//Chaque vue doit connaître son contrôleur et avoir une référence vers le modèle pour pouvoir l'observer
		//VoteVue gui = new VoteVueGUI(model,ctrlGUI,posX,posY);
		VoteVue console = new VoteVueConsole(model,ctrlConsole);
		
		//TODO
		
		//On donne la référence à la vue pour chaque contrôleur
		//ctrlGUI.addView(gui);
		ctrlConsole.addView(console);
		
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
