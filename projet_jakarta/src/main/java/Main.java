import com.cytech.projet_jakarta.dao.UtilisateurDAO;
import com.cytech.projet_jakarta.model.UtilisateurEntity;

public class Main {

    public static void main(String[] args) {
        UtilisateurEntity utilisateur = new UtilisateurEntity();
        utilisateur.setNom("limam");
        utilisateur.setPrenom("mohamed limam");
        utilisateur.setEmail("medlimamuia@gmail.com");
        utilisateur.setMotDePasse("123456789");
        utilisateur.setRole("admin");
        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
        System.out.println("ok");
        utilisateurDAO.saveData(utilisateur);
    }
}
