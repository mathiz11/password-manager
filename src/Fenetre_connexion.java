import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// classe Fenetre_connexion heritant de JFrame et qui implement ActionListener
public class Fenetre_connexion extends JFrame 
							   implements ActionListener
{
	// bouton de connexion
	private JButton bouton;
	// champs du mot de passe 
	private JPasswordField inputPass;
	// lable mot de passe
	private Label label;

	// constructeur de Fenetre_connexion
	public Fenetre_connexion(String str) 
	{
		//initialisation des attributs de la fenetre
		super(str);
		setSize(600, 450);
		getContentPane().setBackground(new Color(153, 255, 255));
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);

		//initialisation du bouton et mis sous ecoute
		bouton = new JButton("Se connecter");
		bouton.setBounds(220, 240, 150, 50);
		bouton.setActionCommand("bouton connexion");
	    bouton.addActionListener(this);

	    // initialisation du champs de mot de passe
		inputPass = new JPasswordField("");
		inputPass.setBounds(145, 175, 300, 40);

		//creation du label
		label = new Label("Saisir votre mot de passe :");
		label.setBounds(215, 120, 200, 30);

		add(label);
		add(inputPass);
		add(bouton);
		
		setVisible(true);
	}

	// actionneur
	public void actionPerformed(ActionEvent evenement)
	{
		// si le bouton connexion est appuyé
		if(evenement.getActionCommand().equals("bouton connexion")) 
		{
			// si ca correspond avec le mot de passe dans le fichier alors on arrive a la fenetre accueil
			if(correspondance(new String(inputPass.getPassword()))) 
			{
				Fenetre_accueil window_accueil = new Fenetre_accueil("Gestionnaire de mots de passe");
				dispose();
			} 
			else
			{
				// sinon fin du programme
				dispose();
			}
		}
	}

	// si le string passer en parametre est egale a celui dans le fichier
	public boolean correspondance(String champ) {
		String mdp = "";
		try {
			File fichier = new File("serialisation.txt");
			BufferedReader fR = new BufferedReader(new FileReader(fichier));
			mdp = fR.readLine(); 
			fR.close();
		} catch(IOException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		return (champ.compareTo(mdp) == 0)? true : false;
	}
}