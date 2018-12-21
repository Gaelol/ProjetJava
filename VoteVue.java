package ProjetJava.view;
/**
 * @author De Mal Raphaël, Dieuzeide Gaël
 */
import java.util.Observer;

import ProjetJava.controller.VoteController;
import ProjetJava.model.Vote;

public abstract class VoteVue implements Observer {
	/**
	 * the view is linked with model and the controller
	 */
	protected Vote model;
	protected VoteController controller;
/**
 * Create the view  and add an observer on the model and controller
 * @param model
 * @param controller
 */
	VoteVue(Vote model, VoteController controller) {
		this.model = model;
		this.controller = controller;
		model.addObserver(this);
	}
/**
 * Create an abstract method affiche
 * @param string
 */
	public abstract void affiche(String string);
}
