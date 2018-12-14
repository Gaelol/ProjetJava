package ProjetJava;

import java.util.Scanner;

import ProjetJava.Vote;

public class VoteController {
	Vote model;
	VoteVue vue;
	
	public VoteController(Vote model) {
		this.model = model;
	}
	
	//TODO
	
	public void addView(VoteVue vue) {
		this.vue = vue;
		
	}
}
