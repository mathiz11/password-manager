import javax.swing.table.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.AbstractTableModel;

// class Model qui herite de AbstractTableModel
public class Model extends AbstractTableModel 
{
    // Tableau 2D representant le tableau avec ses cases
    private Object[][] data;
    // premiere ligne du tableau
    private String[] title;

    // constructeur du model en remplissant les attributs
    public Model(Object[][] data, String[] title)
    {
        this.data = data;
        this.title = title;
    }

    // recupere le nom de la colonne
	public String getColumnName(int col) { return this.title[col]; }

    // recupere la classe de la colonne
	public Class getColumnClass(int col) { return this.data[0][col].getClass(); }

    // renvoie le nombre de colonnes
    public int getColumnCount() { return this.title.length; }

    // renvoie le nombre de lignes
    public int getRowCount() { return this.data.length; }

    // recupere un element a une endroit dans le tableau
    public Object getValueAt(int row, int col) { return this.data[row][col]; }

    // modifier une variable particuliere dans le model
    public void setValueAt(Object value, int row, int col) { this.data[row][col].getClass(); }

    // permet de supprimer une ligne
    public void removeRow(int position) 
    {
    	int indice = 0, indice2 = 0;
    	int nbRow = this.getRowCount()-1, nbCol = this.getColumnCount();
        // on creer le nouveau tableau 2D 
    	Object temp[][] = new Object[nbRow][nbCol];

        // on l'initialise
    	for(Object[] value : this.data) 
        {
    		if(indice != position) 
            {
    			temp[indice2++] = value;
    		}

    		indice++;
    	}

        // on opere les changements
    	this.data = temp;
    	temp = null;
    	this.fireTableDataChanged();
    }

    // ajouter une ligne au modele
    public void addRow(Object[] data) 
    {
    	int indice = 0, nbRow = this.getRowCount(), nbCol = this.getColumnCount();
    	Object temp[][] = this.data;
        // on aggrandit le tableau de 2D d'un ligne 
    	this.data = new Object[nbRow+1][nbCol];

    	for(Object[] value: temp)
        {
    		this.data[indice++] = value;
        }

        // et on insere la derniere, on opere les changements
    	this.data[indice] = data;
    	temp = null;
    	this.fireTableDataChanged();
    }

    // si une case est editable toujours vrai pour modifier
    public boolean isCellEditable(int row, int col) { return true; }    
}
