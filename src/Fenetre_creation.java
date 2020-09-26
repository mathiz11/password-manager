import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// classe Fenetre_creation heritant de JFrame et implement ActionListener
public class Fenetre_creation extends JFrame 
                              implements ActionListener
{
	// bouton de la creation du mot de passe
	private JButton bouton;
	// champs texte de la creation d'un nouveau mot de passe
	private JPasswordField inputPassword;
	// affichage de texte
	private Label label1, label2;

	// constructeur de Fenetre_creation
	public Fenetre_creation(String str) 
	{
		// initialisation des attributs de la fenetre
		super(str);
		setSize(600, 450);
		getContentPane().setBackground(new Color(153, 255, 255));
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);

		// creation du bouton creer
		bouton = new JButton("Creer");
		bouton.setBounds(220, 240, 150, 50);
		bouton.setActionCommand("bouton creer");
	    bouton.addActionListener(this);

	    // creation du champs du mot de passse a creer
		inputPassword = new JPasswordField();
		inputPassword.setBounds(145, 175, 300, 40);

		// creation des labels
		label1 = new Label("Creation de compte");
		label2 = new Label("Saisir un mot de passe :");
		label2.setBounds(220, 80, 150, 40);
		label1.setBounds(230, 115, 150, 40);

		// ajout au contenu de la fenetre
		add(label1);
		add(label2);
		add(inputPassword);
		add(bouton);

		setVisible(true);
	}

	//actionneur
	public void actionPerformed(ActionEvent evenement)
	{
		// si l'utilisateur appuye sur creer
		if(evenement.getActionCommand().equals("bouton creer")) 
		{
			// recupere la chaine du champs mot de passe
			String champ = new String(inputPassword.getPassword());
			// si il est inferrieur a 6 caracteres erreur
			if(champ.length() < 6) 
			{
				JOptionPane jop = new JOptionPane();
				jop.showMessageDialog(null, "Le mot de passe doit contenir au moins 6 caractères", "Erreur", JOptionPane.ERROR_MESSAGE);
			} 
			else
			{
				// sauvegarde du mot de passe dans le fichier et lancement de connexion
				sauvegarderMdp(champ);
				Fenetre_connexion window_connexion = new Fenetre_connexion("Gestionnaire de mots de passe");
				dispose();
			}
		}
	}

	public void sauvegarderMdp(String mdp) 
	{
		// attrape l'exception
		try 
		{
			// creer le fichier et ecrit le mot de passe et 0 (nombre de comptes)
			File fichier = new File("serialisation.txt");
			BufferedWriter fW = new BufferedWriter(new FileWriter(fichier));
			fW.write(mdp, 0, mdp.length());
			fW.newLine();
			fW.write("0", 0, 1);
			fW.close();
		} 
		catch(IOException e) 
		{
			System.out.println("Erreur : " + e.getMessage());
		}
	}
}