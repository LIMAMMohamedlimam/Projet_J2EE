import com.cytech.projet_jakarta.dao.EtudiantDAO;
import com.cytech.projet_jakarta.dao.UtilisateurDAO;
import com.cytech.projet_jakarta.model.Etudiant;
import com.cytech.projet_jakarta.model.Utilisateur;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import static com.cytech.projet_jakarta.utility.JsonParser.parseSingleInput;

public class Main {

    public static void main(String[] args) {
        EtudiantDAO etudiantDAO = new EtudiantDAO();
        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
        //List<Etudiant> etudiants ;
        //Utilisateur uti = utilisateurDAO.findByNameAndPronoun("limam" , "mohamed") ;
        //etudiants = etudiantDAO.findByNameAndPronoun("limam" , "mohamed") ;
        //Utilisateur uti = utilisateurDAO.findByEmailAndPassword("medlimamuia@gmail.com3" , "617628716") ;
        //System.out.println("Etduiant: "+etudiants.toString() + " Utilisateur: "+uti.toString());
        Utilisateur utilisateur = new Utilisateur();
        //utilisateur.setNom("John Dossse");
        //utilisateur.setPrenom("Dosse");
        //utilisateur.setEmail("john.dossse@gmail.com");
        //utilisateur.setPassword("johnsdose");
        //utilisateur.setRole("etudiant");
        //System.out.println("adding utilisateur");
        //utilisateurDAO.saveData(utilisateur);
        //System.out.println("utilisateur added");
        utilisateur = utilisateurDAO.findByNameAndPronoun("hello", "mohamed limam");

        System.out.println("utilisateur found: " + utilisateur);
        Etudiant etudiant = new Etudiant();
        //etudiant.setNom(utilisateur.getNom());
        //etudiant.setPrenom(utilisateur.getPrenom());
        //etudiant.setEtudUtiFk(utilisateur.getId());
        //etudiant.setUtilisateur(utilisateur);
        System.out.println("etudiant: " + etudiant);
        System.out.println("adding etudiant...");
        int res = etudiantDAO.saveStudentData(utilisateur);
        System.out.println(res);
        System.out.println("etudiant added");

    }
}
