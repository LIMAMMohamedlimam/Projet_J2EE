import com.cytech.projet_jakarta.dao.UtilisateurDAO;
import com.cytech.projet_jakarta.model.UtilisateurEntity;

public class Main {

    public static void main(String[] args) {
        UtilisateurEntity utilisateur = new UtilisateurEntity();
        utilisateur.setNom("hello");
        utilisateur.setPrenom("mohamed limam");
        utilisateur.setEmail("medlimamuia@gmdail.com");
        utilisateur.setPassword("123456789");
        utilisateur.setRole("admin");
        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
        System.out.println("ok");
        utilisateurDAO.saveData(utilisateur);
    }
}
