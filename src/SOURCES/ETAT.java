/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SOURCES;

import java.util.Vector;

/**
 *
 * @author user
 */
public interface ETAT {
    public String getNom();
    public String getTitre();
    public String getInstitution(); 
    public String getChemin();
    public Vector getCriteres();
    public String getListeTitresColonne01();
    public Vector getTabValeursColonne01();
    
    public String getListeTitresColonne02();
    public Vector getTabValeursColonne02();
    public void produirePDF();
    public String getReperePage();
}
