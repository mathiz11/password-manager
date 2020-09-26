import java.util.*;
import java.io.*;

// classe Principal contenant le main
public class Principal 
{
	// fonction main
	public static void main(String [] args) {
		File fichier = new File("serialisation.txt");
		
		// si le fichier existe on lance accueil car compte principal deja cree
		if(!fichier.exists()) 
		{
			Fenetre_creation window_creation = new Fenetre_creation("Gestionnaire de mots de passe");
		} 
		else 
		{
			Fenetre_connexion window_connexion = new Fenetre_connexion("Gestionnaire de mots de passe");
		}
		// sinon creation du mot de passe du compte principal grace a cette fenetre

	}
}