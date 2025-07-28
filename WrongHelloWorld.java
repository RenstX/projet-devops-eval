public class WrongHelloWorld {

    public static void main(String[] args) {
        // Affiche un message de bienvenue
        System.out.println("Démonstration d'une erreur pour SonarQube !");
        
        // Crée deux objets String qui ont le même contenu
        String s1 = new String("test");
        String s2 = new String("test");

        // ERREUR VOLONTAIRE : On compare les objets et non leur contenu.
        // SonarQube devrait détecter que cette condition est toujours fausse.
        if (s1 == s2) {
            System.out.println("Les chaînes sont identiques.");
        } else {
            System.out.println("Les chaînes ne sont PAS identiques.");
        }
    }
}