package ProjetJava;

/**
 * @author De Mal Raphaël, Dieuzeide Gaël
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class VoteController {
	Vote model;
	VoteVue vue;
	/**
	 * the amount of people able to vote
	 */
	int nombredeVotants = 0;
	/**
	 * the amount of people you can vote for
	 */
	int nombredeParticipants = 0;
	/**
	 * the choice the user makes when creating his election when chosing between
	 * 'simple'(1) or 'détaillé'(2)
	 */
	int choix = 0;
	/**
	 * the url used to acces the database containing all the date of the election
	 * system created
	 */
	String url = "jdbc:mysql://localhost:3306/demo";
	/**
	 * the username used to log in the database
	 */
	String user = "root";
	/**
	 * the password used to log in the database
	 */
	String password = "password";
	/**
	 * TODO
	 */
	Connection myConn = null;
	/**
	 * TODO
	 */
	Statement myStmt = null;
	/**
	 * TODO
	 */
	ResultSet myRs;
	/**
	 * the maximum value used to determin who won the elections
	 */
	int maxVal = Integer.MAX_VALUE;
	/**
	 * the String used to show the name of the person to be elected with his id
	 */
	String str = " ";
	/**
	 * the name of the person that just voted
	 */
	String nom = " ";
	/**
	 * the age of the person that just voted
	 */
	int age = 0;
	/**
	 * TODO
	 */
	int vote = 8524;

	/**
	 * TODO
	 */
	public VoteController(Vote model) {
		this.model = model;
	}

	/**
	 * TODO
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
	 * 
	 */
	String nomdesPart[] = new String[1000];// nom

	/**
	 * 
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
	 * 
	 */
	public void vote(int vote, int i) {

		Vote[i] = vote;
		vue.affiche("Tu as vote pour " + vote);

	}

	/**
	 * 
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
	 * 
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
	 * 
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
			vue.affiche("Participant " + (i + 1) + " " + (participants[i] * 100) / nombredeVotants + "%");
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
			vue.affiche("Le gagnant est le candidat numero " + maxVal + " : " + nomdesPart[(maxVal - 1)]);
			vue.affiche("La moyenne d'age des votes pour le gagnant est de " + avg + "ans");
		} catch (Exception exc) {
			exc.printStackTrace();
		}

	}

	/**
	 * 
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
	 * 
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
	 * 
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
