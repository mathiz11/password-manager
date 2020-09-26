import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.text.*;

// classe Fenetre_ajouter heritant de JFrame et implementant ActionListener et WindowListener
public class Fenetre_ajouter extends JFrame
							 implements ActionListener, WindowListener 
{
	// nom des champs de la creation d'un compte
	private Label label_des, label_uti, label_mdp, label_date;
	// champs textes
	private JTextField champ_des, champ_uti;
	// champs mot de passe
	private JPasswordField champ_mdp;
	// spinner pour choisir le jour , mois et date
	private JSpinner spinner_jour, spinner_mois, spinner_annee;
	// bouton valider pour ajouter un compte au vecteur ou annuler l'ajout et revenir a accueil
	private JButton bouton_valider, bouton_annuler;
	// vecteur compte de l'accueil
	private Vector<Compte> comptes;
	// fenetre accueil
	private Fenetre_accueil win_acc;

	// constructeur de Fenetre_ajouter
	public Fenetre_ajouter(String str, Vector<Compte> listeComptes, Fenetre_accueil win) 
	{
		// initialisation des attributs de la fenetre
		super(str);
		setSize(600, 450);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);
		addWindowListener(this);

		comptes = listeComptes;
		win_acc = win;

		// creation des labels positionnés 
		label_des = new Label("Description");
		label_uti = new Label("Utilisateur");
		label_mdp = new Label("Mot de passe");
		label_date = new Label("Date d'expiration");
		label_des.setBounds(100, 50, 100, 40);
		label_uti.setBounds(100, 120, 100, 40);
		label_mdp.setBounds(100, 190, 100, 40);
		label_date.setBounds(100, 260, 110, 40);

		// creation des spinners positionnés 
		spinner_jour = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
		spinner_mois = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
		spinner_annee = new JSpinner(new SpinnerNumberModel(2000, 2000, 3000, 1));
		spinner_jour.setBounds(260, 260, 50, 30);
		spinner_mois.setBounds(320, 260, 50, 30);
		spinner_annee.setBounds(380, 260, 80, 30);

		// creation des champs positionnés 
		champ_des = new JTextField("");
		champ_uti = new JTextField("");
		champ_mdp = new JPasswordField("");
		champ_des.setBounds(240, 50, 250, 40);
		champ_uti.setBounds(240, 120, 250, 40);
		champ_mdp.setBounds(240, 190, 250, 40);

		// creation des boutons positionnés 
		bouton_valider = new JButton("Valider");
		bouton_valider.setBounds(380, 330, 100, 40);
		bouton_valider.setActionCommand("bouton valider");
	 	bouton_valider.addActionListener(this);

	  	bouton_annuler = new JButton("Annuler");
		bouton_annuler.setBounds(110, 330, 100, 40);
		bouton_annuler.setActionCommand("bouton annuler");
	 	bouton_annuler.addActionListener(this);

		add(label_des);
		add(label_uti);
		add(label_mdp);
		add(label_date);
		add(champ_des);
		add(champ_uti);
		add(champ_mdp);
		add(spinner_jour);
		add(spinner_mois);
		add(spinner_annee);
		add(bouton_valider);
		add(bouton_annuler);
		
		setVisible(true);
	}

	// permet de tester si une date existe
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

	// actionneur
	public void actionPerformed(ActionEvent evenement)
	{
		// si c'est valider
		if(evenement.getActionCommand().equals("bouton valider"))
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

			String date_expiration = jour + "/" + mois + "/" + spinner_annee.getValue().toString();

			// on récupère tous les attributs pour créer un nouveau compte et l'ajouter au vecteur
			// si la date est valide sinon boit de dialogue d'erreur
			if(dateValide(date_expiration))
			{
				Compte c = new Compte(champ_des.getText(), champ_uti.getText(), new String(champ_mdp.getPassword()), date_expiration);
				comptes.add(c);
				// appel de la methode de Fenetre_accueil permettant d'ajouter une ligne au tableau
				win_acc.MAJAjout();
				dispose();
			}
			else
			{
				JOptionPane jop = new JOptionPane();
				jop.showMessageDialog(null, "Date invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		} 
		else if(evenement.getActionCommand().equals("bouton annuler"))
		{
			// on revient sur accueil
			setVisible(false);
			dispose();
		}
	} 

	// ferme seulement la fenetre ajouter
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