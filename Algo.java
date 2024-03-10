import java.util.*;

public class Algo {

    List<String> langage = List.of("v", "!", "|", "&", "(", ")","e");
    List<String> negation_langage = List.of("!", "v", "&", "|", "(", ")","o"); // on travaille avec les mêmes indices

    public List<String> negation(String fbf) {
        List<String> resultat1 = new ArrayList<>();
    
        StringBuilder nouveauCar = new StringBuilder();
    
        for (int j = 0; j < fbf.length(); j++) {
            String chari = Character.toString(fbf.charAt(j)); 
            String negatedChar = chari; 
    
            if (langage.contains(chari)) {
                int index = langage.indexOf(chari);
                negatedChar = negation_langage.get(index);
            }
    
            nouveauCar.append(negatedChar);
        }
        resultat1.add(nouveauCar.toString());
        
        List<String> substrings = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        char delimiter = 'o';
        String str = resultat1.get(0);

        for (int i = 0; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            if (currentChar == delimiter) {
                substrings.add(sb.toString());
                sb = new StringBuilder();
            } else {
                sb.append(currentChar);
            }
        }
        substrings.add(sb.toString());
        return substrings;
    }
   
    public String validite_clause(String clause){
        if (clause.isEmpty()){ return "";}
        else{
        String first = Character.toString(clause.charAt(0));
        String last = Character.toString(clause.charAt(clause.length() - 1));
        for (int i = 2; i <= langage.size();i++ ){
            if (first.equals(langage.get(i))){
                clause = clause.substring(1);
                return clause;
            }
            else if (last.equals(langage.get(i))){
                clause = clause.substring(0, clause.length() - 1);
                return clause;
            }
        }
        return clause; }
    }

    public String resolvantof2clauses(String clause1, String clause2) {//notre clause résolvante
        String clause3 = "";
        List<String> langage_resolvant = List.of("v", "!");

        for (int i = 0; i < clause1.length() - 1; i++) {
            String char1 = Character.toString(clause1.charAt(i));
            String litt1 = Character.toString(clause1.charAt(i + 1));
            
            for (int j = 0; j < clause2.length() - 1; j++) {
                String char2 = Character.toString(clause2.charAt(j));
                String litt2 = Character.toString(clause2.charAt(j + 1));
                
                if (langage_resolvant.contains(char1) && langage_resolvant.contains(char2) && !char1.equals(char2) && litt1.equals(litt2)) {
                    int index1 = clause1.indexOf(char1);
                    int index2 = clause2.indexOf(char2);
                    
                    // Supprimer char1, litt1, char2 et litt2
                    String resultat1 = clause1.substring(0, index1) + clause1.substring(index1 + 2); // 2 pour retirer char1 et litt1
                    String resultat2 = clause2.substring(0, index2) + clause2.substring(index2 + 2); // 2 pour retirer char2 et litt2
                    
                    clause3 = resultat1 + resultat2;
                    String result = validite_clause(clause3);
                    return result;
                }
            }
        }
        String result = validite_clause(clause3);
        return result;
    }

    private boolean containsEmptyClauses(List<String> Liste){
        for( int i =0 ; i< Liste.size(); i++){
            if (Liste.get(i).equals("")){
                return true;
            }
        }
        return false;
    }

    private boolean validite(String fbf){
        List<String> negatedFbf = negation(fbf);
        int i = 1;
        String fixedClause = negatedFbf.get(0);
        String array[] = {fixedClause};
        List<String> ResolvedList = Arrays.asList(array);
        List<String> arraylist = new ArrayList<>(ResolvedList);
        while ( !containsEmptyClauses(arraylist) && i < negatedFbf.size() ){
            arraylist.add(resolvantof2clauses(fixedClause, negatedFbf.get(i)));
            i+=1;
        }
        if (containsEmptyClauses(arraylist)){
            System.out.println("F est valide");
            return true;
        }
        else{ System.out.println("F est invalide");
    return false;}
    }



    public static void main(String[] args) {
        Algo algo = new Algo();
        //La définition du langage
        System.out.println("Bonjour, je suppose que t'es ici pour vérifier si ton FBF est valide ou invalide..\n Je te présente ma version du Algorithme de résolution: \n Je te présente tout d'abord mon langage:\n ");
        System.out.println("Si tu veux exprimer : \nLa vérité :'v'\nLa négation : '!'\nL'union : '|'\nL'intersection : '&'\nLes parenthèses c'est bien connu ;)\nSi tu veux exprimer une intersection en dehors des parenthèses je te prie d'utiliser : 'e' et 'o' pour l'union(mais celle-ci t'en auras pas besoin normalement)\n Et l'intérieur des parenthèses contiendra que des unions (|) ");
        //Le scanner
        Scanner scanner = new Scanner(System.in);
        System.out.print("Veuillez saisir votre fbf: ");
        String fbf = scanner.nextLine();
        scanner.close();
        if (fbf.contains("o")){
            System.out.println("L'expression de ton fbf est invalide.");
        }
        //String car = "(vP&vQ&!R)e(vR)e(!P)e(vT&!Q)e(!T)"; pour test
        boolean resultat =  algo.validite(fbf);
        System.out.println("le résultat est : "+ resultat); 
    }
}
