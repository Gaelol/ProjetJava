package ProjetJava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class VoteController {
	Vote model;
	VoteVue vue;
	int nombredeVotants = 0;
	int nombredeParticipants = 0;
	int choix = 0;
	String url = "jdbc:mysql://localhost:3306/demo";
	String user = "root";
	String password = "password";
	Connection myConn = null;
	Statement myStmt = null;
	ResultSet myRs;
	int maxVal = Integer.MAX_VALUE;
	String str = " ";
	String nom = " ";
	int age = 0;
	int vote = 8524;
	
	
	public VoteController(Vote model) {
		this.model = model;
	}
	
	public void addView(VoteVue vue) {
		this.vue = vue;
		
	}

	
	public void nombredeVotants(int Votants) {
		this.nombredeVotants = Votants;
	
	}
	
	public void nombredeParticipants(int Participants) {
		this.nombredeParticipants = Participants;
	}
	
	public void choix(int c) {
		this.choix = c;
	}
	int participants[] = new int[1000];//numero
	int Vote[] = new int[1000];//vote
	String nomdesPart[] = new String[1000];//nom
	

	public void nomParticipants(String nom, int i) {
			str += "  " + (i + 1) + ")" + nom;
			participants[i] = 0;
			nomdesPart[i] =nom;
		try {
			myConn = DriverManager.getConnection(url, user, password);
			myStmt = myConn.createStatement();
			String sql = "insert into Vote " + " (Nom,Num)" + " values ('" + nom + "','" + (i + 1) + "')";
			myStmt.executeUpdate(sql);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		}
	
	
	public void vote(int vote,int i) {
		
			Vote[i]=vote;
			vue.affiche("Tu as vote pour " + vote);
		
	}
	
	public void depouillage() {
		for (int i = 0; i < nombredeParticipants; i++) {
			for (int j = 0; j < nombredeVotants; j++) {
				if (Vote[j] == (i + 1)) {
					participants[i] += 1;
				}
			}
		}
	}
	
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
	
	public void gagnant() {
		for (int i = 0; i < nombredeVotants; i++) {
			
			if (Vote[i] == maxVal)
			vue.affiche("Egalite entre:"+nomdesPart[(maxVal - 1)]+" et "+nomdesPart[Vote[i]-1]);
			
			
			if (Vote[i] < maxVal)
				maxVal = Vote[i];
			vue.affiche("Le gagnant est le candidat numero " + maxVal + " : " + nomdesPart[(maxVal - 1)]);
			break;
		}
		
	}
	
	public void votantComplet(String nom,int age,int vot,int i) {
		this.nom=nom;
		this.age = age;
		Vote[i]=vot;
		
		try {
			myConn = DriverManager.getConnection(url, user, password);
			myStmt = myConn.createStatement();

			String sql = "insert into Votant " + " (Nom,Vote,Age)" + " values ('" + nom + "','" + vot
					+ "','" + age + "')";
			myStmt.executeUpdate(sql);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	
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
