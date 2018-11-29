package ProjetJava;

import java.util.Scanner;

import javax.swing.JOptionPane;

public class Console extends Votant {
	public int nombre() {
		System.out.println("Combien de votants?");
		Scanner scanner = new Scanner(System.in);
		int nombre = scanner.nextInt();
		return nombre;
	}
	
	public int nombre2() {
		System.out.println("Combien de participants?");
		Scanner scanner = new Scanner(System.in);
		int nombre2 = scanner.nextInt();
		return nombre2;
	}
	
	Votant votant;
	
	public void main() {
		int participants[] = new int[4];
		participants[0] = 1;
		participants[1] = 2;
		participants[2] = 3;
		participants[3] = 4;
		int nombre = nombre();
		int nombre2 = nombre2();
		int Vote[] = new int[nombre];
		
		for(int i=0;i<nombre;i++) {
			System.out.println("Les Participants sont : "+ participants);
			System.out.println("Quel est ton vote?");
			
			Scanner scanner = new Scanner(System.in);
			int vot = scanner.nextInt();
			System.out.println("Tu as voté pour " + vot);
			Vote[i] = vot;
		}
		for (int i=0; i<nombre;i++) {
			
		}
		System.out.println();
	}
	
}

