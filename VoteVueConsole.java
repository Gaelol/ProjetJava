package ProjetJava;

import java.util.InputMismatchException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import ProjetJava.VoteController;
import ProjetJava.Vote;


public class VoteVueConsole extends VoteVue implements Observer {
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
	
	public static int NombredeVotants() {
		System.out.println("Combien de votants?");
		Scanner scanner = new Scanner(System.in);
		int nombre = scanner.nextInt();
		return nombre;
	}
	
	public static int NombredeParticipants() {
		System.out.println("Combien de participants?");
		Scanner scanner = new Scanner(System.in);
		int nombre2 = scanner.nextInt();
			return nombre2;
	}
	
	private class ReadInput implements Runnable{
		public void run() {

			int maxVal = Integer.MAX_VALUE;
			int nombredeVotants = NombredeVotants();
			int nombredeParticipants =NombredeParticipants();
			int participants[] = new int[nombredeParticipants];
			int Vote[] = new int[nombredeVotants];
			String Part[]= new String[nombredeParticipants];
			String str = "";
			
			for(int i=0;i<nombredeParticipants;i++) {
				participants[i]= 0;
				System.out.println("Quel est le nom du participant?");
				Scanner scanner = new Scanner(System.in);
				String part = scanner.nextLine();
				Part[i] = part;
				str +="  " +(i+1)+")"+Part[i];
			}
			
			for(int i=0;i<nombredeVotants;i++) {
				
				System.out.println("Quel est ton vote?" + str);
				
				Scanner scanner = new Scanner(System.in);
				int vot = scanner.nextInt();
				System.out.println("Tu as voté pour " + vot);
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
			System.out.println("Participant "+ (i+1)  +" " +(participants[i]*100)/nombredeVotants+ "%");
			}
			
			for(int i = 0; i < nombredeVotants; i++){
		         if(Vote[i] < maxVal)
		           maxVal = Vote[i];
			}
			
			affiche("Le gagnant est le candidat numéro "+ maxVal + " : "+Part[(maxVal-1)]);
		}
	}
	
	
public static void main(String [] args) {
		
	 }
	
	@Override
	public void affiche(String string) {
		System.out.println(string);
		
	}
}
