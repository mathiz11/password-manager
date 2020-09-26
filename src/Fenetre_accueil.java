import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

// classe Fenetre_accueil heritant de JFram et qui implemente ActionListener
public class Fenetre_accueil extends JFrame 
                             implements ActionListener 
 {
 	// bouton d'ajout de compte, de recherche et de sauvegarde des comptes
	private JButton bouton_ajouter, bouton_rechercher, bouton_sauvegarder;
	// vecteur contenant l'ensembles des comptes de mot de passe
	private static Vector<Compte> listeComptes;
	// mot de passe du compte principal
	private String motDePasse;
	// model qui permet de récupérer toutes les données des comptes dans un tableau 2D
	private Model mod;
	// tableau ou seront affichés les comptes
	private JTable tableau;

	// getter : retourne le vecteur de comptes
	public Vector<Compte> getListeComptes() { return listeComptes; }

	// constructeur Fenetre_accueil 
	public Fenetre_accueil(String str) 
	{
		// initialisation du titre
		super(str);
		// initialisation de la taille de la fenetre
		setSize(800, 550);
		// ne rien faire en cas de fermeture de la fenetre
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// afficher la fenetre au mileu
		setLocationRelativeTo(null);
		
		// initialisation des comptes à partir d'un fichier ou se trouvent les comptes
		listeComptes = chargerComptes();

		// initialisation du bouton ajouter et met sous ecoute ses actions
		bouton_ajouter = new JButton("Ajouter");
		bouton_ajouter.setActionCommand("bouton ajouter");
		bouton_ajouter.addActionListener(this);

		// initialisation du bouton rechercher et met sous ecoute ses actions
		bouton_rechercher = new JButton("Rechercher");
		bouton_rechercher.setActionCommand("bouton rechercher");
		bouton_rechercher.addActionListener(this);

		// initialisation du bouton sauvegarder et met sous ecoute ses actions
		bouton_sauvegarder = new JButton("Sauvegarder");
		bouton_sauvegarder.setActionCommand("bouton sauvegarder");
		bouton_sauvegarder.addActionListener(this);

		// creation du panel regroupant tous les boutons
		JPanel panel = new JPanel();
		panel.add(bouton_ajouter);
		panel.add(bouton_rechercher);
		panel.add(bouton_sauvegarder);
		
		// creation du tableau d'Object qui correspond au case du tableau
		// initialisation de lignes (nombre de comptes) et colonnes (nombre d'attributs de comptes)
		Object[][] data = new Object[listeComptes.size()][5];
		// remplissage du tableau d'Object
		remplirTableau(data);
		// titre du tableau (attributs de comptes)
		String[] title = {"Descriptif", "Utilisateur", "Mot de passe", "Date d'expiration", "Suppression"};
		// initialisation du modele
		mod = new Model(data, title);
		// initialisation du tableau à partir du modèle
		tableau = new JTable(mod);
		// creation du bouton de suppression dans le tableau
		tableau.getColumn("Suppression").setCellEditor(new DeleteButtonEditor(new JCheckBox(), listeComptes));
		
		// ajout du tableau et du layout dans le corps cde la fenetre
		getContentPane().add(new JScrollPane(tableau), BorderLayout.CENTER);
		getContentPane().add(panel, BorderLayout.SOUTH);

		// fenetre mis sous ecoute
		addWindowListener(new WindowAdapter() 
		{
			// lorsqu'elle se ferme
			public void windowClosing(WindowEvent e) 
			{
				// si le vecteur du programme est different du contenu des compte du fichier
				if(!verificationSauvegarde()) 
				{
					// on demande si il veut sauver ses modifications
					if( JOptionPane.showConfirmDialog(null, "Souhaitez-vous enregistrer les modifications ?", "Quitter",
						JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE) == JOptionPane.YES_OPTION ) 
					{
						sauvegarderComptes();
					}
				}
				// on ferme la fenetre
            	dispose();
			}
    	});
		// on rend visible la fenetre
		setVisible(true);
	}

	// fonction qui permet de charger les comptes dans le vecteur 
	public Vector<Compte> chargerComptes() {
		// attrape l'exception
		try {
			// fichier ou se trouvent les le mot de passe et les comptes
			File fichier = new File("serialisation.txt");
			BufferedReader fR = new BufferedReader(new FileReader(fichier));

			motDePasse = fR.readLine(); 
			// 2eme ligne du fichier est composé du nombre de comptes
			int nbrComptes = Integer.parseInt(fR.readLine());
			Vector<Compte> comptes = new Vector<Compte>();

			// on charge les comptes
			for(int i = 0; i < nbrComptes; i++) 
			{
				Compte c = Compte.charger(fR);

				if(c != null) 
				{
					comptes.add(c);
				} 
				else 
				{
					System.out.println("Erreur chargement fichier 'serialisation.txt'.");
					System.exit(0);
				}
			}

			fR.close();
			return comptes;
		} 
		catch(IOException e) 
		{
			System.out.println("Erreur : " + e.getMessage());
		}

		return null;
	}

	// fonction qui sauvegarde les comptes dans le vecteur dans le fichier "serialisation.txt"
	public void sauvegarderComptes() 
	{
		// attrape l'exception
		try 
		{
			// fichier ou se trouvent les le mot de passe et les comptes
			File fichier = new File("serialisation.txt");
			BufferedWriter fW = new BufferedWriter(new FileWriter(fichier));

			fW.write(motDePasse, 0, motDePasse.length());
			fW.newLine();
			String nbrComptes = String.valueOf(listeComptes.size());
			fW.write(nbrComptes, 0, nbrComptes.length());

			// on sauvegarde selon le nombre de comptes dans le vecteur
			for(int i = 0; i < listeComptes.size(); i++) 
			{
				listeComptes.elementAt(i).sauvegarder(fW);
			}

			fW.close();
		} 
		catch(IOException e) 
		{
			System.out.println("Erreur : " + e.getMessage());
		}
	}

	// fonction qui permet de vérifier si 2 comptes sont identiques
	public boolean verificationSauvegarde() { return listeComptes.equals(chargerComptes()); }

	// fonction qui permet de remplir le tableau 2D pour le modele selon les valeurs des comptes
	public void remplirTableau(Object[][] tab) 
	{
		for(int i = 0; i < listeComptes.size(); i++) 
		{
			tab[i][0] = listeComptes.elementAt(i).getDescriptif();
			tab[i][1] = listeComptes.elementAt(i).getUtilisateur();
			tab[i][2] = listeComptes.elementAt(i).getMdp();
			tab[i][3] = listeComptes.elementAt(i).getDate();
			tab[i][4] = "Supprimer";
		}
	}

	// en cas d'ajout d'un compte il faut ajouter une ligne au tableau
	public void MAJAjout() 
	{
		Object[] ligne = { listeComptes.lastElement().getDescriptif(),
						   listeComptes.lastElement().getUtilisateur(),
						   listeComptes.lastElement().getMdp(),
						   listeComptes.lastElement().getDate(),
						   "Supprimer" };
	   	((Model)tableau.getModel()).addRow(ligne);
	}

	// actionneur
	public void actionPerformed(ActionEvent evenement)
	{
		// si le bouton ajouter est enfoncé
		if(evenement.getActionCommand().equals("bouton ajouter")) 
		{
			// creation de la fenetre ajouter
			Fenetre_ajouter window_ajouter = new Fenetre_ajouter("Ajouter un compte", listeComptes, this);	
		} 
		// si le bouton sauvegarder est enfoncé
		else if(evenement.getActionCommand().equals("bouton sauvegarder")) 
		{
			// on demande si il veut sauvegarder
			if( JOptionPane.showConfirmDialog(null, "Souhaitez-vous sauvegarder ?", "Quitter",
						JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE) == JOptionPane.YES_OPTION ) 
			{
				sauvegarderComptes();
			}
		} 
		else if(evenement.getActionCommand().equals("bouton rechercher")) 
		{
			// creation de la fenetre rechercher
			Fenetre_rechercher window_rechercher = new Fenetre_rechercher("Rechercher", listeComptes);
		}
	}
}