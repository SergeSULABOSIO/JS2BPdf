/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TEST;



import SOURCES.DOCUMENTPDF;
import SOURCES.ETAT;
import java.util.Vector;

/**
 *
 * @author HP Pavilion
 */
public class ModelObjetProduit implements ETAT{
    Vector<ObjetProduit> data = new Vector<>();

    public ModelObjetProduit() {
        actualiser();
    }
    
    public void actualiser() {
        for (int i = 0; i < 20; i++) {
            ObjetProduit prod = new ObjetProduit(i + 23, "PRODUIT #" + i, "RAS SUR LE PRODUIT #" + i, 1, 150, "Serge SULA BOSIO", "01/10/2018 23:21", 21);
            data.add(prod);
        }
    }

    @Override
    public String getNom() {
        return "NOMDELALISTE";
    }

    @Override
    public String getTitre() {
        return "TITREDUDOCUMENT";
    }

    @Override
    public String getInstitution() {
        return "S2B-SEP Congo";
    }

    @Override
    public String getChemin() {
        return "F:/";
    }

    @Override
    public Vector getCriteres() {
        Vector lis = new Vector();
        lis.add("NOM = SERGE");
        lis.add("POST-NOM = BOSIO");
        lis.add("GRADE = MANAGER");
        return lis;
    }

    @Override
    public String getListeTitresColonne01() {
        return "Nom\nPostnom";
    }
    
    @Override
    public Vector getTabValeursColonne01() {
        Vector val01 = new Vector();
        val01.add("SULA\nBOSIO");
        val01.add("KALALA\nKAZADI");
        val01.add("MUTA\nKANKUNGWALA");
        val01.add("DEO\nKASHONGWE");
        return val01;
    }
    
    

    @Override
    public String getListeTitresColonne02() {
        return "Prenom\nPoste";
    }

    @Override
    public Vector getTabValeursColonne02() {
        Vector val01 = new Vector();
        val01.add("Serge\nAccount Manager");
        val01.add("Serge\nAccount Manager");
        val01.add("Serge\nAccount Manager");
        val01.add("Serge\nAccount Manager");
        return val01;
    }

    @Override
    public void produirePDF() {
        DOCUMENTPDF documentpdf = new DOCUMENTPDF(this, "Pour UAP RDC Sarl\nSerge SULA BOSIO");//
    }

    @Override
    public String getReperePage() {
        return "8 Objet(s)/8, Page 1/1";
    }
}
