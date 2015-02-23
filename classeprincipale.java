/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_java.AnalyseProfile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.annolab.tt4j.TokenHandler;
import org.annolab.tt4j.TreeTaggerException;
import org.annolab.tt4j.TreeTaggerWrapper;

public class classeprincipale {

    classeprincipale() {
        super();

    }
    static TreeMap<String, Integer> Occurence = new TreeMap<String, Integer>();
    static TreeMap<String, Integer> Corpus = new TreeMap<String, Integer>();
    static double C;
    static double M=0;

    

    static String[] segmentation(String ligne) {
        //String ligne1="";
        String str[] = {",", ".", "?", "!", "...", "…", "(", ")", ";", ":", "\n"};
        for (int k = 0; k < str.length; k++) {

            ligne = ligne.replace(str[k], " " + str[k]);

        }
        String str2[] = {"'"};
        for (int k = 0; k < str2.length; k++) {

            ligne = ligne.replace(str2[k], str2[k]+" ");

        }
        return ligne.split(" ");

    }

    public static void Analyse(String word) {
        Integer occ;
        occ = Occurence.get(word);
        if (occ == null) {
            Occurence.put(word, 1);
        } else {
            Occurence.put(word, occ + 1);
        }
    }
    
  
    
    public static void treetagger(String File) throws IOException {
        ArrayList<String> splitArray = new ArrayList<String>();
        BufferedReader lecteurAvecBuffer = null;
        String ligne;
        try {
             /* System.out.println("On essaie d'ouvrir " + File);*/
            lecteurAvecBuffer = new BufferedReader(new FileReader(File));
        } catch (IOException ioe) {
            System.out.println("Erreur d'ouverture");
        }
        while ((ligne = lecteurAvecBuffer.readLine()) != null) {

            List list1 = Arrays.asList(segmentation(ligne));
            splitArray.addAll(list1);

        }
        System.setProperty("treetagger.home", "C:\\treetagger");
        TreeTaggerWrapper tt = new TreeTaggerWrapper<String>();
        try {
            tt.setModel("C:\\TreeTagger\\lib\\french-utf8.par");
            tt.setHandler(new TokenHandler<String>() {
                public void token(String token, String pos, String lemma) {
                    /*System.out.println(token + "\t" + pos + "\t" + lemma);*/
                    Analyse(lemma);
                }
            });
            tt.process(splitArray);

        } catch (TreeTaggerException ex) {
            Logger.getLogger(classeprincipale.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            tt.destroy();
        }

/*        Set listKeys = Occurence.keySet();
        Iterator iterateur = listKeys.iterator();

        while (iterateur.hasNext()) {
            Object key = iterateur.next();
            System.out.println(key + "=>" + Occurence.get(key));
        }
*/
        lecteurAvecBuffer.close();
    }

    public static void findFiles(String directoryPath) throws IOException, Exception {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            System.out.println("Le fichier/répertoire '" + directoryPath + "' n'existe pas");
        } else if (!directory.isDirectory()) {
            System.out.println("Le chemin '" + directoryPath + "' correspond à un fichier et non à un répertoire");
        } else {
            File[] subfiles = directory.listFiles();
			//String message = "Le répertoire '"+directoryPath+"' contient "+ subfiles.length+" fichier"+(subfiles.length>1?"s":"");
            //System.out.println(message);
            for (int i = 0; i < subfiles.length; i++) {
                //System.out.println(subfiles[i].getName());
                try {
                C=compteur(subfiles[i]);
                M=M+C;
                    }
                catch(Exception e){
                e.printStackTrace();
                                   }
                  treetagger(directoryPath+"\\"+subfiles[i].getName());
                 
            }
        }
        Set listKeys = Occurence.keySet();
        Iterator iterateur = listKeys.iterator();
        while (iterateur.hasNext()) {
            Object key = iterateur.next();
            System.out.println(key + "=>" + (Occurence.get(key))/M);
            /*System.out.println(key + "=>" + Occurence.get(key)/c);*/
        }
    }
    
  public static int compteur(File f) throws Exception{
        Scanner input=new Scanner(f);
        int i=0;
        while(input.hasNext()){
        input.next();
        i++;
        }return i;
        }

    public static void main(String[] argv) throws IOException, Exception {

        classeprincipale.findFiles("C:\\Users\\boubacar\\Documents\\R\\NetBeansProjects\\AttributionText\\src\\projet_java\\AnalyseProfile\\Zola");

//a=classeprincipale.findFiles("C:\\Users\\boubacar\\Documents\\NetBeansProjects\\projet_java\\src\\Projet_java\\src\\projet_java\\Musso ");
        //classeprincipale.treetagger("C:\\Users\\boubacar\\Documents\\R\\NetBeansProjects\\AttributionText\\src\\projet_java\\AnalyseProfile\\alain_fournier.txt ");

    

    }

}
