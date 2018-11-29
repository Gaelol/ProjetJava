package ProjetJava;

import java.util.Scanner;

public class Console extends Votant {
	
	public static int Nombre() {
		System.out.println("Combien de votants?");
		Scanner scanner = new Scanner(System.in);
		int nombre = scanner.nextInt();
		return nombre;
	}
	
	public static int Nombre2() {
		System.out.println("Combien de participants?");
		Scanner scanner = new Scanner(System.in);
		int nombre2 = scanner.nextInt();
		return nombre2;
	}
	
	public static void main(String [] args) {
		int participants[] = new int[4];
		int maxVal = Integer.MAX_VALUE;
		participants[0] = 0;
		participants[1] = 0;
		participants[2] = 0;
		participants[3] = 0;
		int nombre = Nombre();
		//int nombre2 = Nombre2();
		int Vote[] = new int[nombre];
		
		for(int i=0;i<nombre;i++) {
			
			System.out.println("Quel est ton vote?(1.2.3.4)");
			
			Scanner scanner = new Scanner(System.in);
			int vot = scanner.nextInt();
			System.out.println("Tu as voté pour " + vot);
			Vote[i] = vot;
		}
		for (int i=0; i<nombre;i++) {
			if (Vote[i] == 1) {
				participants[0] +=1;
			}
			if (Vote[i] == 2) {
				participants[1] +=1;
			}
			if (Vote[i] == 3) {
				participants[2] +=1;
			}
			if (Vote[i] == 4) {
				participants[3] +=1;
			}
			
		}
		System.out.println("Participant 1 "+(participants[0]*100)/nombre+ "%");
		System.out.println("Participant 2 "+(participants[1]*100)/nombre+ "%");
		System.out.println("Participant 3 "+(participants[2]*100)/nombre+ "%");
		System.out.println("Participant 4 "+(participants[3]*100)/nombre+ "%");
		
		for(int i = 0; i < nombre; i++){
	         if(Vote[i] < maxVal)
	           maxVal = Vote[i];
		}
		System.out.println("Le gagnant est le candidat numéro "+ maxVal );
	 }
	}
