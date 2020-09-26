import java.util.*;
import java.io.*;

// Classe Compte
public class Compte 
{
	// attributs : descriptif du compte, nom d'utilisateur, mot de passe du compte et la date d'expiration du mot de passe
	private String descriptif, utilisateur, mdp, date_expiration;

	// getter : qui retourne le descriptif
	public String getDescriptif() { return descriptif; }
	// getter : qui retourne le nom d'utilisateur
	public String getUtilisateur() { return utilisateur; }
	// getter : qui retourne le mot de passe
	public String getMdp() { return mdp; }
	// getter : qui retourne la date d'expiration
	public String getDate() { return date_expiration; }

	// constructeur de la classe Compte qui initialise tous les attributs
	public Compte(String _descriptif, String _utilisateur, String _mdp, String _date_expiration) 
	{
		descriptif = _descriptif;
		utilisateur = _utilisateur;
		mdp = _mdp;
		date_expiration = _date_expiration;
	}

	// fonction permettant la création d'un Compte a partir d'un fichier (lecture)
	public static Compte charger(BufferedReader bR) 
	{
		// attrape l'exception si erreur lors de la lecture
		try 
		{
			String des = bR.readLine();
			String uti = bR.readLine();
			String mot = bR.readLine();
			String date = bR.readLine();

			// retourne l'instance d'un nouveau compte
			return new Compte(des, uti, mot, date);
		} 
		catch(IOException e) 
		{
			System.out.println("Erreur : " + e.getMessage());
		}

		// retourne aucun compte si exception
		return null;
	}

	// fonction permettant de stocker un compte dans un fichier (ecriture)
	public void sauvegarder(BufferedWriter bW) {
		// attrape l'exception si erreur lors de l'ecriture
		try 
		{
			bW.newLine();
			bW.write(descriptif, 0, descriptif.length());
			bW.newLine();
			bW.write(utilisateur, 0, utilisateur.length());
			bW.newLine();
			bW.write(mdp, 0, mdp.length());
			bW.newLine();
			bW.write(date_expiration, 0, date_expiration.length());
		} 
		catch(IOException e) 
		{
			System.out.println("Erreur : " + e.getMessage());
		}
	}

	// fonction permettant de comparer 2 comptes entre eux
	public boolean equals(Object o) 
	{
		if(o instanceof Compte) 
		{
			// on caste l'objet en Compte
			Compte c = (Compte) o;

			// on compare chacun des attributs entre les 2 comptes
			return (descriptif.equals(c.getDescriptif()) && utilisateur.equals(c.getUtilisateur()) &&
				    mdp.equals(c.getMdp()) && date_expiration.equals(c.getDate()));
		}
		
		// sinon il ne sont pas identique
		return false;
	}
}