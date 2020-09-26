import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.text.*;

// classe Fenetre_rechercher heritant de JFrame et implemente ActionListener et WindowListener
public class Fenetre_rechercher extends JFrame 
                                implements ActionListener, WindowListener 
{
	// label date
	private Label label_date;
	// bouton rechercher et bouton qui reset le tableau
	private JButton bouton_rechercher, bouton_reset;
	// spinner pour choisir le jour, mois et annee
	private JSpinner spinner_jour, spinner_mois, spinner_annee;
	// vector compte de l'accueil et un autre de recherche
	private Vector<Compte> comptes, rec;
	// model pour gerer les donnees du tableau
	private Model model;
	// tableau
	private JTable tab;

	// constructeur de la classe Fenetre_rechercher
	public Fenetre_rechercher(String str, Vector<Compte> listeComptes) 
	{
		// initialisation des attributs de la fenetre
		super(str);
		setSize(600, 450);
		getContentPane().setBackground(new Color(153, 255, 255));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		addWindowListener(this);

		comptes = listeComptes;
		// initialisation du vecteur a vide car 0 recherche effectue
		rec = new Vector<Compte>();

		// creation label
		label_date = new Label("Date : ");

		// creation spinner
		spinner_jour = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
		spinner_mois = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
		spinner_annee = new JSpinner(new SpinnerNumberModel(2000, 2000, 3000, 1));

		// creation des boutons
		bouton_rechercher = new JButton("Rechercher");
		bouton_rechercher.setActionCommand("bouton rechercher");
		bouton_rechercher.addActionListener(this);

		bouton_reset = new JButton("Reset");
		bouton_reset.setActionCommand("bouton reset");
		bouton_reset.addActionListener(this);

		// creation du tableau d'Object qui correspond au case du tableau
		// initialisation de lignes (nombre de comptes) et colonnes (descriptif et date d'expiration)
		Object[][] donnees = new Object[comptes.size()][2];
		// remplissage du tableau d'Object
		remplirObject(donnees);
		// titre du tableau (descriptif et date d'expiration)
		String titre[] = {"Descriptif", "Date d'expiration"};
		// initialisation du modele
		model = new Model(donnees, titre);
		// initialisation du tableau à partir du modèle
		tab = new JTable(model);

		// ajout du label, des spinners et des boutons dans le panel
		JPanel panel = new JPanel();
		panel.add(label_date);
		panel.add(spinner_jour);
		panel.add(spinner_mois);
		panel.add(spinner_annee);
		panel.add(bouton_rechercher);
		panel.add(bouton_reset);

		// ajout du panel au nord
		getContentPane().add(panel, BorderLayout.NORTH);
		// ajout du tableau au mileu
		getContentPane().add(new JScrollPane(tab), BorderLayout.CENTER);

		setVisible(true);
	}

	// recupere la date d'expiration des spinners
	public String getDateExpiration() 
	{
		String jour = spinner_jour.getValue().toString(), mois = spinner_mois.getValue().toString();

		if((int)spinner_jour.getValue() < 10) 
		{
			jour = "0" + spinner_jour.getValue();
		} 
		if((int)spinner_mois.getValue() < 10) 
		{
			mois = "0" + spinner_mois.getValue();
		}

		return jour + "/" + mois + "/" + spinner_annee.getValue();
	}

	// verifie si une date est valide ou pas au format "dd/MM/yyyy"
	public boolean dateValide(String date) 
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date d = new Date();

        try 
        {
            d = sdf.parse(date);
            String t = sdf.format(d);
            
            return !(t.compareTo(date) !=  0);
        } 
        catch (Exception e) 
        {
            System.out.println("Exception");
        }

        return false;
	}

	// ajoute au vecteur recherche les comptes dont la date d'expiration 
	// est inferieur a la date passer en parametre
	public void rechercherDate(String date) 
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		// on supprime tout avant d'ajouter
		rec.removeAllElements();

		try 
		{
			Date d1 = sdf.parse(date);

			for(int i = 0; i < comptes.size(); i++) 
			{
				Date d2 = sdf.parse(comptes.elementAt(i).getDate());

				if(d1.compareTo(d2) >= 0) 
				{
					rec.add(comptes.elementAt(i));
				}
			}
		} 
		catch(ParseException p)
		{
			System.out.println("Erreur : " + p.getMessage());
		}
	}

	// vide toutes les lignes du tableau
	public void viderTableau() 
	{
		int taille = model.getRowCount();

		for(int i = 0; i < taille; i++) 
		{
			((Model)tab.getModel()).removeRow(0);
		}
	}

	// remplie le tableau avec tous les comptes du vecteur passe en parametre
	public void remplirTableau(Vector<Compte> vec) 
	{
		for(int i = 0; i < vec.size(); i++) 
		{
			Object[] ligne = { vec.elementAt(i).getDescriptif(), vec.elementAt(i).getDate() };
	   		((Model)tab.getModel()).addRow(ligne);
		}
	}

	// initialise le tableau 2D du model
	public void remplirObject(Object[][] tab) 
	{
		for(int i = 0; i < comptes.size(); i++) 
		{
			tab[i][0] = comptes.elementAt(i).getDescriptif();
			tab[i][1] = comptes.elementAt(i).getDate();
		}
	}

	// actionneur
	public void actionPerformed(ActionEvent evenement) 
	{	
		// si le bouton rechercher est appuye
		if(evenement.getActionCommand().equals("bouton rechercher")) 
		{
			// on recuper la date
			String dateExpiration = getDateExpiration();
			// si elle est valide on rentre sinon erreur boite de dialog
			if(dateValide(dateExpiration)) 
			{
				viderTableau();
				rechercherDate(dateExpiration);
				remplirTableau(rec);
			} 
			else 
			{
				JOptionPane jop = new JOptionPane();
				jop.showMessageDialog(null, "Date invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		} 
		else if(evenement.getActionCommand().equals("bouton reset")) 
		{
			// on rempli avec tous les comptes de l'accueil
			viderTableau();
			remplirTableau(comptes);
		}
	}

	// fermer seulement la fenetre rechercher
	public void windowClosed(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowClosing(WindowEvent e)
	{
		setVisible(false);
        dispose();
	}
}