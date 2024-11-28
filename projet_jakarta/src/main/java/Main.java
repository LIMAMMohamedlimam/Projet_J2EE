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
        String json = etudiantDAO.getAllStudents() ;
        //String json = "{" ;
        //List<Etudiant> etudiants = new ArrayList<>();
        //etudiants = etudiantDAO.getAllStudents() ;
        //for(Etudiant etu : etudiants){
        //    System.out.println(etu.toString());
        //    json = json + parseSingleInput(etu.toString()) + ", " ;
        //}
        //json = json+ "}" ;
        System.out.println(json);
    }
}
