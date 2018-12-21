package ProjetJava;

/**
 * @author De Mal Raphaël, Dieuzeide Gaël
 */
import java.time.LocalDate;

import javax.swing.JOptionPane;

public class Votant {
	/**
	 * the name of the person
	 */
	protected String nom;
	/**
	 * the forename of the person
	 */
	protected String prenom;
	/**
	 * The birth of the person
	 */
	protected LocalDate dateNaissance;
	/**
	 * The vote of the person
	 */
	protected int vote;
	/**
	 * The age of the person
	 */
	protected int age;

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public LocalDate getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public int getVote() {
		return vote;
	}

	public void setVote(int vote) {
		this.vote = vote;
	}

	/**
	 * this method gives the age of the person based on his birthday and on the
	 * current date
	 * 
	 * @return an int representing the age of the person now
	 */
	public int age() {
		return (LocalDate.now().getYear() - dateNaissance.getYear());
	}

	@Override
	public String toString() {
		return nom + " " + prenom + "(" + age + " ans) a votÃƒÂ© pour le candidat numÃƒÂ©ro " + vote;
	}

	/**
	 * This is a constructor ask the name forename, age and vote
	 * 
	 * @param nom
	 * @param prenom
	 * @param age
	 * @param vote
	 */
	public Votant() {
		this.nom = JOptionPane.showInputDialog("Quel est votre nom?");
		this.prenom = JOptionPane.showInputDialog("Quel est votre prÃƒÂ©nom?");
		this.age = Integer.parseInt(JOptionPane.showInputDialog("Quel est votre age?"));
		this.vote = Integer.parseInt(JOptionPane.showInputDialog("Pour quel candidat(e) votez-vous?"));
	}

	/**
	 * This is a constructor with the name,forename and birthdate
	 * 
	 * @param nom
	 * @param prenom
	 * @param dateNaissance
	 */
	public Votant(String nom, String prenom, LocalDate dateNaissance) {
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
	}
}
