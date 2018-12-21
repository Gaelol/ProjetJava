package ProjetJava;

/**
 * @author De Mal Raphaël, Dieuzeide Gaël
 */
import java.time.LocalDate;
import java.util.Observable;

public class Vote extends Observable {
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

	/**
	 * @return the name of the person
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * 
	 * @param nom the name of the person to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the forename of the person
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * 
	 * @param prenom the forename of the person to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * @return the date of birth of the person
	 */
	public LocalDate getDateNaissance() {
		return dateNaissance;
	}

	/**
	 * 
	 * @param dateNaissance the date of birth of the person to set
	 */
	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	/**
	 * @return the id of the participant the person voted for
	 */
	public int getVote() {
		return vote;
	}

	/**
	 * 
	 * @param vote id of the participant the person voted for to set
	 */
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

	/**
	 * This is a constructor with the name,forename and birthdate
	 * 
	 * @param nom           the name of the person
	 * @param prenom        his forename
	 * @param dateNaissance his date of birth
	 */
	public Vote() {
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
	}

}
