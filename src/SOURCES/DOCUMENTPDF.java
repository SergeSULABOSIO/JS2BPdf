
package SOURCES;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Gateway
 */
public class DOCUMENTPDF {
    public static String OUPUT_S2B = "Output_S2B";
    private Document document = new Document();
    private static Font Font_Titre1 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK);
    private static Font Font_Titre2 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD, BaseColor.BLACK);
    private static Font Font_Titre3 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK);
    private static Font Font_TexteSimple = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL, BaseColor.BLACK);
    private static Font Font_TexteSimple_Souligne = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.UNDERLINE, BaseColor.BLACK);
    private static Font Font_TexteSimple_Gras = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD, BaseColor.BLACK);
    private static Font Font_TexteSimple_Italique = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.ITALIC, BaseColor.BLACK);
    private static Font Font_TexteSimple_Italique_spec = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.ITALIC, BaseColor.BLACK);
    private static Font Font_TexteSimple_Gras_Italique = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLDITALIC, BaseColor.BLACK);

    //public String signatureCARTESIEN = "\n\nA.D.F                                                                                   Ass. A.D.F\n\n\n";
    
    

    public DOCUMENTPDF(Vector<ETAT> etats, String signature) {
        try {
            initialiser();
            if (etats.size() == 1) {
                construirePDF(etats.firstElement(), signature);
            } else {
                int idRapport = 1;
                for (int i = 0; i < etats.size(); i++) {
                    idRapport ++;
                    if (i == (etats.size() - 1)) {
                        construirePDF(etats.elementAt(i), signature);
                    }else{
                        construirePDF(etats.elementAt(i), "");
                    }
                    //On insère le séparateur avant d'ajouter le rapport suivant
                    if (etats.size() > 1) { //si la liste contient plusieurs elements
                        if (i < (etats.size() - 1)) {     //Si l'indice se trouve entre 1 inclusif et l'avant dernier rapport (inclusif)
                            AjouterSeparateur("Rapport n°"+(idRapport));   //On ajoute le séparateur
                        }
                    }
                }
            }
            ouvrirDocumentPDF();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DOCUMENTPDF(ETAT etat, String signature) {
        try {
            initialiser();
            construirePDF(etat, signature);
            ouvrirDocumentPDF();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void supprimerOutputSiExiste() {
        File fic = new File(DOCUMENTPDF.OUPUT_S2B + ".pdf");
        if (fic.exists() == true) {
            fic.delete();
        }
    }

    private void initialiser() throws Exception {
        supprimerOutputSiExiste();
        PdfWriter.getInstance(document, new FileOutputStream(DOCUMENTPDF.OUPUT_S2B + ".pdf"));
        document.open();
    }

    private void ouvrirDocumentPDF() throws Exception {
        document.close();   //On ferme d'abord le document au cas où il est encore Ouvert
        Desktop.getDesktop().open(new File(DOCUMENTPDF.OUPUT_S2B + ".pdf"));
    }

    private void construirePDF(ETAT etat, String signature) {
        try {
            addPage(etat, signature);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private void addTitlePage(ETAT etat) throws DocumentException {
        Paragraph preface = new Paragraph();
        preface.add(getParagraphe(etat.getInstitution(), Font_Titre2, false, false, true));
        preface.add(getParagraphe(etat.getTitre(), Font_Titre1, false, false, true));
        preface.add(getParagraphe(etat.getCriteres(), Font_TexteSimple_Italique, false, false, true));
        preface.add(getParagraphe("Document produit le "+  (new Date()).toLocaleString()  +"\nRésultat : " + etat.getReperePage(), Font_TexteSimple, false, false, true));
        addEmptyLine(preface, 1);
        document.add(preface);
    }

    private Paragraph getParagraphe(String texte, Font font, boolean isleft, boolean isCenter, boolean isRight) {
        Paragraph par = new Paragraph(texte, font);
        if (isleft == true) {
            par.setAlignment(Element.ALIGN_LEFT);
        } else if (isCenter == true) {
            par.setAlignment(Element.ALIGN_CENTER);
        } else if (isRight == true) {
            par.setAlignment(Element.ALIGN_RIGHT);
        }
        return par;
    }

    private Paragraph getParagraphe(Vector tabTextes, Font font, boolean isleft, boolean isCenter, boolean isRight) {
        Paragraph par = null;
        if (tabTextes.size() != 0) {
            par = new Paragraph(tabTextes.firstElement() + "", font);
            int i = 0;
            for (Object ligne : tabTextes) {
                if (i != 0) {
                    par.add(new Phrase("\n" + ligne + ""));
                }
                i++;
            }
            if (isleft == true) {
                par.setAlignment(Element.ALIGN_LEFT);
            } else if (isCenter == true) {
                par.setAlignment(Element.ALIGN_CENTER);
            } else if (isRight == true) {
                par.setAlignment(Element.ALIGN_RIGHT);
            }
            return par;
        } else {
            return par;
        }
    }

    private void addPage(ETAT etat, String signature) throws Exception {
        //boolean withInst = false;
        //if (signature.length() == 0) {
        //    withInst = true;
        //}
        addTitlePage(etat); //Titre du document à produire
        PdfPTable table = new PdfPTable(5); //Tableau contenant les informations
        int[] tabdim = {100, 280, 500, 250, 500};
        table.setWidths(tabdim);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setLockedWidth(false);
        for (int i = 0; i < etat.getTabValeursColonne01().size(); i++) {
            try {
                addLigne(etat, table, (i + 1), etat.getTabValeursColonne01().elementAt(i) + "", etat.getTabValeursColonne02().elementAt(i) + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        document.add(table);    //On ajoute le tableau rempli dans le document pdf
        if (signature.length() != 0) {
            AjouterSignature(signature);
        }
    }

    private void AjouterSignature(String signature) throws Exception {
        Paragraph preface = new Paragraph();
        preface.add(getParagraphe(signature, Font_TexteSimple_Gras, false, true, false));
        addEmptyLine(preface, 1);
        document.add(preface);
    }

    private void AjouterSeparateur(String texte) throws Exception {
        Paragraph preface = new Paragraph();
        String trait = "-------------------------";
        preface.add(getParagraphe("\n" + trait + texte + trait, Font_TexteSimple_Italique_spec, false, true, false));
        addEmptyLine(preface, 1);
        document.add(preface);
    }

    private void addLigne(ETAT etat, PdfPTable table, int numero, String valCol1, String valCol2) throws Exception {
        table.addCell(getCellule(numero, "(" + (numero) + ")", Font_TexteSimple_Gras, true, false, false));
        table.addCell(getCellule(numero, etat.getListeTitresColonne01(), Font_TexteSimple_Italique, false, false, true));
        table.addCell(getCellule(numero, valCol1, Font_TexteSimple_Gras, true, false, false));
        table.addCell(getCellule(numero, etat.getListeTitresColonne02(), Font_TexteSimple_Italique, false, false, true));
        table.addCell(getCellule(numero, valCol2, Font_TexteSimple_Gras, true, false, false));
    }

    private PdfPCell getCellule(int id, String texte, Font font, boolean isLeft, boolean isCenter, boolean isRight) {
        PdfPCell cellule = new PdfPCell(getParagraphe(texte, font, isLeft, isCenter, isRight));
        if ((id % 2) == 1) {
            cellule.setBackgroundColor(new BaseColor(230, 230, 230));
        }
        if (isLeft == true) {
            cellule.setHorizontalAlignment(Element.ALIGN_LEFT);
        } else if (isCenter == true) {
            cellule.setHorizontalAlignment(Element.ALIGN_CENTER);
        } else if (isRight == true) {
            cellule.setHorizontalAlignment(Element.ALIGN_RIGHT);
        }

        int num = -1;
        boolean isNum = true;
        try {
            num = Integer.parseInt(texte.trim());
            isNum = true;
        } catch (Exception e) {
            isNum = false;
        }
        //if(isNum == true){
        cellule.setNoWrap(false);
        //}
        cellule.setBorderWidthBottom(0);
        cellule.setBorderWidthLeft(0);
        cellule.setBorderWidthRight(0);
        cellule.setBorderWidthTop(1);
        return cellule;
    }

    public static void main(String[] a) {
        
    }
}
