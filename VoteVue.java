package ProjetJava;

import java.util.Observer;

import ProjetJava.VoteController;
import ProjetJava.Vote;

public abstract class VoteVue implements Observer{
	
	protected Vote model;
	protected VoteController controller;
	
	VoteVue(Vote model,
			VoteController controller) {
		this.model = model;
		this.controller = controller;
		model.addObserver(this);
	}

	public abstract void affiche(String string) ;
}
