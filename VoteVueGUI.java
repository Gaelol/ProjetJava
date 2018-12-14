package ProjetJava;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;

public class VoteVueGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VoteVueGUI frame = new VoteVueGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VoteVueGUI() {
		setTitle("Cr\u00E9ateur de votes");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblBienvenueDansLe = new JLabel("Bienvenue dans le cr\u00E9ateur de votes de Rapha\u00EBl De Mal, Ga\u00EBl Dieuzeide et Cl\u00E9ment Berlanger");
		GridBagConstraints gbc_lblBienvenueDansLe = new GridBagConstraints();
		gbc_lblBienvenueDansLe.gridx = 0;
		gbc_lblBienvenueDansLe.gridy = 0;
		contentPane.add(lblBienvenueDansLe, gbc_lblBienvenueDansLe);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{contentPane}));
	}

}
