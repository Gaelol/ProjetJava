package ProjetJava.controller;

/**
 * @author De Mal Raphaël, Dieuzeide Gaël
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import ProjetJava.model.Vote;
import ProjetJava.view.VoteVue;

public class VoteController {
	Vote model;
	VoteVue vue;
	/**
	 * the amount of people able to vote
	 */
	public int nombredeVotants = 0;
	/**
	 * allow to drop a line 
	 */
	public String newLine =System.getProperty("line.separator");
	/**
	 * the amount of people you can vote for
	 */
	
	public int nombredeParticipants = 0;
	/**
	 * initiate the string of the result
	 */
	public String resultat ="";
	/**
	 * the choice the user makes when creating his election when chosing between
	 * 'simple'(1) or 'détaillé'(2)
	 */
	public int choix = 0;
	/**
	 * the url used to acces the database containing all the date of the election
	 * system created
	 */
	public String url = "jdbc:mysql:localhost://3306/demo";
	/**
	 * the username used to log in the database
	 */
	public String user = "root";
	/**
	 * the password used to log in the database
	 */
	public String password = "password";
	/**
	 * the connection with the database
	 */
	public Connection myConn = null;
	/**
	 * Statement to access Database
	 */
	public Statement myStmt = null;
	/**
	 *Response frome the Database
	 */
	public ResultSet myRs;
	/**
	 * the maximum value used to determine who won the elections
	 */
	int maxVal = Integer.MAX_VALUE;
	/**
	 * the String used to show the name of the person to be elected with his id
	 */
	public String str = " ";
	/**
	 * the name of the person that just voted
	 */
	String nom = " ";
	/**
	 * the age of the person that just voted
	 */
	public int age = 0;
	/**
	 * The maximal size of vote
	 */
	public int vote = 12534564;

	/**
	 *  Link model to Controller
	 */
	public VoteController(Vote model) {
		this.model = model;
	}

	/**
	 * Link view to Controller
	 */
	public void addView(VoteVue vue) {
		this.vue = vue;
	}

	/**
	 * sets the amount of people who can vote 
	 * @param Votants the amount entered by the user
	 */
	public void nombredeVotants(int Votants) {
		this.nombredeVotants = Votants;
	}

	/**
	 * sets the amount of people you can vote for 
	 * @param Participants the amount entered by the user
	 */
	public void nombredeParticipants(int Participants) {
		this.nombredeParticipants = Participants;
	}

	/**
	 * sets the choice the user made when choosing between 'simple' mode or 'détaillé'
	 * @param c the choice the user made (1->'simple' and 2->'detaillé')
	 */
	public void choix(int c) {
		this.choix = c;
	}

	/**
	 * an array of ints of size 1000 where each component is someone you can vote for
	 */
	int participants[] = new int[1000];// numero
	/**
	 * an array of ints of size 1000 where each component is a vote 
	 */
	int Vote[] = new int[1000];// vote
	/**
	 * an array of ints of size 1000 where each component is a name of votants
	 */
	String nomdesPart[] = new String[1000];// nom

	/**
	 * define the name of the participants 
	 * implements a string  with the name and the number
	 * Link to Database and add name and values on JDBC
	 */
	public void nomParticipants(String nom, int i) {
		str += "  " + (i + 1) + ")" + nom;
		participants[i] = 0;
		nomdesPart[i] = nom;
		try {
			myConn = DriverManager.getConnection(url, user, password);
			myStmt = myConn.createStatement();
			String sql = "insert into Vote " + " (Nom,Num)" + " values ('" + nom + "','" + (i + 1) + "')";
			myStmt.executeUpdate(sql);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	/**
	 * implements the vote in the array Vote
	 */
	public void vote(int vote, int i) {

		Vote[i] = vote;
		vue.affiche("Tu as vote pour " + vote);

	}

	/**
	 * implements the number of votes of each candidates
	 */
	public void depouillage() {
		for (int i = 0; i < nombredeParticipants; i++) {
			for (int j = 0; j < nombredeVotants; j++) {
				if (Vote[j] == (i + 1)) {
					participants[i] += 1;
				}
			}
		}
	}

	/**
	 * implements the percentage of votant 
	 * implement also it in Db
	 */
	public void pourcentages() {
		for (int i = 0; i < nombredeParticipants; i++) {
			int prc = (participants[i] * 100) / nombredeVotants;
			try {
				myConn = DriverManager.getConnection(url, user, password);
				myStmt = myConn.createStatement();
				String sql = "update Vote " + " set Pourc ='" + prc + "'" + " where Num =" + (i + 1);
				myStmt.executeUpdate(sql);
			} catch (Exception exc) {
				exc.printStackTrace();
			}
			vue.affiche("Participant " + (i + 1) + " " + (participants[i] * 100) / nombredeVotants + "%");
		}
	}

	/**
	 * implements the percentage of votant and the average age
	 * implement it in Db
	 */
	public void pourcentagesDetail() {
		for (int i = 0; i < nombredeParticipants; i++) {
			int prc = (participants[i] * 100) / nombredeVotants;
			try {
				myConn = DriverManager.getConnection(url, user, password);
				myStmt = myConn.createStatement();
				String sql = "update Vote " + " set Pourc ='" + prc + "'" + " where Num =" + (i + 1);
				myStmt.executeUpdate(sql);
			} catch (Exception exc) {
				exc.printStackTrace();
			}
			resultat+=newLine+("Participant " + (i + 1) + " " + (participants[i] * 100) / nombredeVotants + "%");
		}
		for (int i = 0; i < nombredeVotants; i++) {
			if (Vote[i] < maxVal)
				maxVal = Vote[i];
		}
		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("SELECT *, AVG(Age) as avg FROM demo.Votant WHERE Vote =" + maxVal + ";");
			myRs.beforeFirst();
			myRs.next();
			int avg = myRs.getInt("avg");
			resultat +=newLine+("Le gagnant est le candidat numero " + maxVal + " : " + nomdesPart[(maxVal - 1)]);
			resultat +=newLine+("La moyenne d'age des votes pour le gagnant est de " + avg + "ans");
			vue.affiche(resultat);
		} catch (Exception exc) {
			exc.printStackTrace();
		}

	}

	/**
	 * define the winner of the vote and post it
	 */
	public void gagnant() {
		for (int i = 0; i < nombredeVotants; i++) {

			if (Vote[i] == maxVal)
				vue.affiche("Egalite entre:" + nomdesPart[(maxVal - 1)] + " et " + nomdesPart[Vote[i] - 1]);

			if (Vote[i] < maxVal)
				maxVal = Vote[i];
			vue.affiche("Le gagnant est le candidat numero " + maxVal + " : " + nomdesPart[(maxVal - 1)]);
			break;
		}

	}

	
	/**
	 * constructor for a full votant
	 * @param nom as String
	 * @param age as int
	 * @param vot as int
	 * @param i as iterator(int)
	 */
	public void votantComplet(String nom, int age, int vot, int i) {
		this.nom = nom;
		this.age = age;
		Vote[i] = vot;

		try {
			myConn = DriverManager.getConnection(url, user, password);
			myStmt = myConn.createStatement();

			String sql = "insert into Votant " + " (Nom,Vote,Age)" + " values ('" + nom + "','" + vot + "','" + age
					+ "')";
			myStmt.executeUpdate(sql);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	/**
	 * Reset the database
	 */
	public void reinitialiserDb() {
		try {
			myConn = DriverManager.getConnection(url, user, password);
			myStmt = myConn.createStatement();
			String sql = "delete from Vote";
			myStmt.executeUpdate(sql);
			String SQL = "delete from Votant";
			myStmt.executeUpdate(SQL);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

}
