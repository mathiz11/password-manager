import javax.swing.table.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.DefaultCellEditor;

// classe DeleteButtonEditor qui herite de DefaultCellEditor
public class DeleteButtonEditor extends DefaultCellEditor 
{
    // bouton de suppression
    protected JButton button;
    // ecouteur du bouton
    private DeleteButtonListener bListener = new DeleteButtonListener();
    // vecteur compte de Fenetre_accueil pour supprimer des Comptes
    private Vector<Compte> comptes;

    // constructeur de la classe DeleteButtonEditor 
    public DeleteButtonEditor(JCheckBox checkBox, Vector<Compte> listeComptes) 
    {
        super(checkBox);
        comptes = listeComptes;
        // création du bouton mis sur ecoute
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(bListener);
    }

    // permet d'initialiser la case du bouton 
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) 
    {
        bListener.setRow(row);
        bListener.setTable(table);
        button.setText((value == null) ? "" : value.toString());

        return button;
    }

    // classe DeleteButtonListener qui permet d'ecouter les actions du bouton
    class DeleteButtonListener implements ActionListener 
    {
        // indice de la colonne du JTable
        private int row;
        // tableau contenant l'ensemble des comptes
        private JTable table;

        // setter : modifie la colonne
        public void setRow(int row) {this.row = row;}
        // setter : modifie le tableau
        public void setTable(JTable table) {this.table = table;}

        // Actions si le bouton est enclenche : 
        public void actionPerformed(ActionEvent event) 
        {
            // on supprime que si le tableau n'est pas vide
            if(table.getRowCount() > 0) 
            {
                // suppresion du compte lié à la ligne
                comptes.remove(this.row);
                // suppression de la ligne du tableau
                ((Model)table.getModel()).removeRow(this.row);
            }
        }
    }        
}