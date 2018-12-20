package ProjetJava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import ProjetJava.VoteController;
import ProjetJava.Vote;

public class VoteVueConsole extends VoteVue implements Observer {
	protected Scanner sc;

	public VoteVueConsole(Vote model, VoteController controller) {
		super(model, controller);
		update(null, null);
		sc = new Scanner(System.in);
		new Thread(new ReadInput()).start();
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println(model);
	}

	private class ReadInput implements Runnable {
		public void run() {
			int nombredeVotants = 0;
			int nombredeParticipants = 0;
			int choix = 0;
			while (true) {

				while (true) {
					try {
						System.out.println("Combien de votants?");
						nombredeVotants = sc.nextInt();
						break;
					} catch (InputMismatchException e) {
						System.err.println("Entrez un nombre s'il vous plait!");
						sc.next();
						continue;
					}
				}

				while (true) {
					try {
						System.out.println("Combien de participants?");
						nombredeParticipants = sc.nextInt();
						break;
					} catch (InputMismatchException e) {
						System.err.println("Entrez un nombre s'il vous plait!");
						sc.next();
						continue;
					}
				}

				while (true) {
					try {
						System.out.println("Quel type de vote? 'Simple(1)' ou 'Detaille(2)'?");
						choix = sc.nextInt();
						break;
					} catch (InputMismatchException e) {
						System.err.println("Entrez 'Simple(1)' ou 'Detaille(2)' s'il vous plait!");
						sc.next();
						continue;
					}
				}
				String url = "jdbc:mysql:localhost:3306/demo";
				String user = "root";
				String password = "password";
				Connection myConn = null;
				Statement myStmt = null;
				ResultSet myRs;
				int avg = 0;
				int maxVal = Integer.MAX_VALUE;
				int participants[] = new int[nombredeParticipants];
				int Vote[] = new int[nombredeVotants];
				String Part[] = new String[nombredeParticipants];
				String str = "";
				String nom = null;
				int age = 0;
				int vot = 8524;
				switch (choix) {

				case 1:

					try {
						myConn = DriverManager.getConnection(url, user, password);
						myStmt = myConn.createStatement();
						String sql = "delete from Vote";
						myStmt.executeUpdate(sql);
					} catch (Exception exc) {
						exc.printStackTrace();
					}

					for (int i = 0; i < nombredeParticipants; i++) {
						participants[i] = 0;
						System.out.println("Quel est le nom du participant numero " + (i + 1) + "?");
						Scanner scanner = new Scanner(System.in);
						String part = scanner.nextLine();
						Part[i] = part;
						str += "  " + (i + 1) + ")" + Part[i];

						try {
							myConn = DriverManager.getConnection(url, user, password);
							myStmt = myConn.createStatement();
							String sql = "insert into Vote " + " (Nom,Num)" + " values ('" + part + "','" + (i + 1)
									+ "')";
							myStmt.executeUpdate(sql);
						} catch (Exception exc) {
							exc.printStackTrace();
						}
					}

					for (int i = 0; i < nombredeVotants; i++) {
						while (true) {
							try {
								affiche("Quel est ton vote?(Si le vote est un mauvais nombre: Vote blanc)" + str);
								vot = sc.nextInt();
								break;
							} catch (InputMismatchException e) {
								System.err.println("Entrez un nombre s'il vous plait!\n");
								sc.next();
								continue;
							}
						}
						affiche("Tu as vote pour " + vot);
						Vote[i] = vot;
					}
					for (int i = 0; i < nombredeParticipants; i++) {
						for (int j = 0; j < nombredeVotants; j++) {
							if (Vote[j] == (i + 1)) {
								participants[i] += 1;
							}
						}
					}

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
						affiche("Participant " + (i + 1) + " " + (participants[i] * 100) / nombredeVotants + "%");
					}

					for (int i = 0; i < nombredeVotants; i++) {
						if (Vote[i] < maxVal)
							maxVal = Vote[i];
					}

					affiche("Le gagnant est le candidat numero " + maxVal + " : " + Part[(maxVal - 1)]);
					break;

				case 2:

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

					for (int i = 0; i < nombredeParticipants; i++) {
						participants[i] = 0;
						System.out.println("Quel est le nom du participant numero " + (i + 1) + "?");
						Scanner scanner = new Scanner(System.in);
						String part = scanner.nextLine();
						Part[i] = part;
						str += "  " + (i + 1) + ")" + Part[i];

						try {
							myConn = DriverManager.getConnection(url, user, password);
							myStmt = myConn.createStatement();
							String sql = "insert into Vote " + " (Nom,Num)" + " values ('" + part + "','" + (i + 1)
									+ "')";
							myStmt.executeUpdate(sql);
						} catch (Exception exc) {
							exc.printStackTrace();
						}
					}
					for (int i = 0; i < nombredeVotants; i++) {
						affiche("Comment t'appelles-tu?");
						nom = sc.next();
						while (true) {
							try {
								affiche("Quel âge as-tu?");
								age = sc.nextInt();
								break;
							} catch (InputMismatchException e) {
								System.err.println("Entrez un nombre s'il vous plait!" + e.getMessage());
								sc.next();
								continue;
							}
						}
					}
					for (int i = 0; i < nombredeVotants; i++) {
						while (true) {
							try {
								affiche("Quel est ton vote?(Si le vote est un mauvais nombre: Vote blanc)" + str);
								vot = sc.nextInt();
								break;
							} catch (InputMismatchException e) {
								System.err.println("Entrez un nombre s'il vous plait!\n");
								sc.next();
								continue;
							}
						}
						affiche("Tu as vote pour " + vot);
						Vote[i] = vot;
					}

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
				for (int i = 0; i < nombredeParticipants; i++) {
					for (int j = 0; j < nombredeVotants; j++) {
						if (Vote[j] == (i + 1)) {
							participants[i] += 1;
						}
					}
				}

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
					affiche("Participant " + (i + 1) + " " + (participants[i] * 100) / nombredeVotants + "%");
				}

				for (int i = 0; i < nombredeVotants; i++) {
					if (Vote[i] < maxVal)
						maxVal = Vote[i];
				}

				try {
					myStmt = myConn.createStatement();
					myRs = myStmt
							.executeQuery("SELECT *, AVG(Age) as avg FROM demo.Votant WHERE Vote =" + maxVal + ";");
					myRs.beforeFirst();
					myRs.next();
					avg = myRs.getInt("avg");
				} catch (Exception exc) {
					exc.printStackTrace();
				}

				affiche("Le gagnant est le candidat numero " + maxVal + " : " + Part[(maxVal - 1)]);
				affiche("La moyenne d'age des votes pour le gagnant est de " + avg + "ans");
				break;
			}

		}

	}

	@Override
	public void affiche(String string) {
		System.out.println(string);
	}
}
