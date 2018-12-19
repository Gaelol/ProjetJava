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


public class VoteVueConsole extends VoteVue implements Observer{
	protected Scanner sc;
	 
	public VoteVueConsole(Vote model,
			VoteController controller) {
		super(model, controller);
		update(null, null);
		sc = new Scanner(System.in);
		new Thread (new ReadInput()).start();	
	}
	
	@Override
	public void update(Observable o, Object arg) {
		System.out.println(model);
	}
	
	
	private class ReadInput implements Runnable{
		public void run()  {
			while(true) {
				System.out.println("Combien de votants?");
				int nombredeVotants = sc.nextInt();
			
				System.out.println("Combien de participants?");
				int nombredeParticipants = sc.nextInt();
				
				System.out.println("Quel type de vote? 'Simple' ou 'Detaille'?");
				String choix = sc.next();
				
				String url = "jdbc:mysql://localhost:3306/demo";
			    String user = "root";
			    String password = "password";
			    Connection myConn = null;
			    Statement myStmt = null;
			    ResultSet myRs;
			    int avg = 0;
			    int maxVal = Integer.MAX_VALUE;
				int participants[] = new int[nombredeParticipants];
				int Vote[] = new int[nombredeVotants];
				String Part[]= new String[nombredeParticipants];
				String str = "";
				
				switch(choix) {
				case "Simple":
				
		     try{
				 myConn =  DriverManager.getConnection(url, user, password);
				 myStmt = myConn.createStatement();
				 String sql = "delete from Vote";
				 myStmt.executeUpdate(sql);
		}
			 catch(Exception exc){
					exc.printStackTrace();
		}
		     

			for(int i=0;i<nombredeParticipants;i++) {
				participants[i]= 0;
				System.out.println("Quel est le nom du participant numero "+(i+1) +"?");
				Scanner scanner = new Scanner(System.in);
				String part = scanner.nextLine();
				Part[i] = part;
				str +="  " +(i+1)+")"+Part[i];
				
				 try{
					 myConn =  DriverManager.getConnection(url, user, password);
					 myStmt = myConn.createStatement();
					 String sql = "insert into Vote " + " (Nom,Num)"
		                    + " values ('"+part+"','"+(i+1)+"')";
				 myStmt.executeUpdate(sql);
				 }
				 catch(Exception exc){
						exc.printStackTrace();
				 }
			}
			
			for(int i=0;i<nombredeVotants;i++) {
				
				System.out.println("Quel est ton vote?" + str);
				int vot = sc.nextInt();
				System.out.println("Tu as vote pour " + vot);
				Vote[i] = vot;
			}
			
			for (int i=0; i<nombredeParticipants;i++) {
				for(int j=0;j<nombredeVotants;j++) {
					if(Vote[j]==(i+1)) {
						participants[i]+=1;
					}
				}
			}
			
			for(int i=0;i<nombredeParticipants;i++) {
				int prc = (participants[i]*100)/nombredeVotants;
				 try{
					 myConn =  DriverManager.getConnection(url, user, password);
					 myStmt = myConn.createStatement();
					 String sql = "update Vote " 
					 + " set Pourc ='"+prc+"'"
		             + " where Num ="+(i+1);
				 myStmt.executeUpdate(sql);
			}
				 catch(Exception exc){
						exc.printStackTrace();
			}
			affiche("Participant "+ (i+1)  +" " +(participants[i]*100)/nombredeVotants+ "%");
			}
			
			for(int i = 0; i < nombredeVotants; i++){
		         if(Vote[i] < maxVal)
		           maxVal = Vote[i];
			}
			
			affiche("Le gagnant est le candidat numero "+ maxVal + " : "+Part[(maxVal-1)]);
			break;
			
			
			
				case "Detaille":
					
					 try{
						 myConn =  DriverManager.getConnection(url, user, password);
						 myStmt = myConn.createStatement();
						 String sql = "delete from Vote";
						 myStmt.executeUpdate(sql);
						 String SQL = "delete from Votant";
						 myStmt.executeUpdate(SQL);
				}
					 catch(Exception exc){
							exc.printStackTrace();
				}
				     

					for(int i=0;i<nombredeParticipants;i++) {
						participants[i]= 0;
						System.out.println("Quel est le nom du participant numero "+(i+1) +"?");
						Scanner scanner = new Scanner(System.in);
						String part = scanner.nextLine();
						Part[i] = part;
						str +="  " +(i+1)+")"+Part[i];
						
						 try{
							 myConn =  DriverManager.getConnection(url, user, password);
							 myStmt = myConn.createStatement();
							 String sql = "insert into Vote " + " (Nom,Num)"
				                    + " values ('"+part+"','"+(i+1)+"')";
						 myStmt.executeUpdate(sql);
						 }
						 catch(Exception exc){
								exc.printStackTrace();
						 }
					}
					for(int i=0;i<nombredeVotants;i++) {
						affiche("Comment t'appelles-tu?");
						String nom = sc.next();
						affiche("Quel âge as-tu?");
						int age = sc.nextInt();
						affiche("Quel est ton vote?" + str);
						int vot = sc.nextInt();
						affiche("Tu as vote pour " + vot);
						Vote[i] = vot;
						
						try{
							 myConn =  DriverManager.getConnection(url, user, password);
							 myStmt = myConn.createStatement();
							 String sql = "insert into Votant " + " (Nom,Vote,Age)"
				                    + " values ('"+nom+"','"+vot+"','"+age+"')";
						 myStmt.executeUpdate(sql);
						 }
						 catch(Exception exc){
								exc.printStackTrace();
						 }
					}
					for (int i=0; i<nombredeParticipants;i++) {
						for(int j=0;j<nombredeVotants;j++) {
							if(Vote[j]==(i+1)) {
								participants[i]+=1;
							}
						}
					}
					
					for(int i=0;i<nombredeParticipants;i++) {
						int prc = (participants[i]*100)/nombredeVotants;
						 try{
							 myConn =  DriverManager.getConnection(url, user, password);
							 myStmt = myConn.createStatement();
							 String sql = "update Vote " 
							 + " set Pourc ='"+prc+"'"
				             + " where Num ="+(i+1);
						 myStmt.executeUpdate(sql);
					}
						 catch(Exception exc){
								exc.printStackTrace();
					}
					affiche("Participant "+ (i+1)  +" " +(participants[i]*100)/nombredeVotants+ "%");
					}
					
					for(int i = 0; i < nombredeVotants; i++){
				         if(Vote[i] < maxVal)
				           maxVal = Vote[i];
					}
					
					try{
						myStmt = myConn.createStatement();
						myRs = myStmt.executeQuery("SELECT *, AVG(Age) as avg FROM demo.Votant WHERE Vote ="+maxVal+";");
						myRs.beforeFirst();
						myRs.next();
						avg = myRs.getInt("avg");
				}
					catch(Exception exc){
							exc.printStackTrace();
				}
					
					
					affiche("Le gagnant est le candidat numero "+ maxVal + " : "+Part[(maxVal-1)]);
					affiche("La moyenne d'age des votes pour le gagnant est de "+avg+"ans");
					affiche("Voulez vous savoir qui a vote pour vous? 'Oui' ou 'Non'");
					String demande =sc.next();
				switch(demande) {
				case "Oui":
					try{
						myStmt = myConn.createStatement();
						myRs = myStmt.executeQuery("SELECT distinct Nom as st FROM demo.Votant WHERE Vote ="+maxVal+";");
						myRs.beforeFirst();
						myRs.next();
						String st = myRs.getString("st");
						affiche(st);
						break;
				}
					catch(Exception exc){
							exc.printStackTrace();
				}
				case "Non": affiche("Comme tu le souhaites");
				}
					break;
					
					
					
					
					
				default:
					affiche("Mauvais choix!");
					break;
				}
			break;
			}
		}
	}

	@Override
	public void affiche(String string) {
		System.out.println(string);
	}
}
