package be.helha.poo3.projet.springboot.projetjavaspringboot.daoImpl;

import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;

/**
 * La classe ParserConfig sert à lire le fichier de config JSON et de le mettre dans un objet Persistance
 *
 * @author Diesbecq Aaron
 * @see ParserConfig
 */
public class ParserConfig {

    /**
     * Méthode qui lis le fichier de config JSON, crée un objet Persistance et l'incrémente avec ce qu'il a lu dans le fichier de config
     * @param fichierConfig Chemin d'accès vers le fichier de config
     * @return Un objet de type Persistance
     * @throws Exception
     */
    public static Persistance lireConfig(String fichierConfig) throws Exception {
        try {
            Path path = Paths.get(fichierConfig);
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            Persistance persistance = gson.fromJson(reader, Persistance.class);
            reader.close();
            verif(persistance);
            return persistance;
        } catch (Exception e){
            System.out.println("mesage erreur");
            System.out.println(e.getMessage() + e.getClass());
            return null;
        }
    }

    /**
     * Vérifie si les champs nécessaire du JSON ne sont pas null grâce à l'objet Persistance
     * @param persistance Objet qui contient les infos du fichier de config
     * @throws Exception
     */
    public static void verif(Persistance persistance) throws Exception{
        if (persistance.getConnectionType() == null)
            throw new Exception("Il manque le champ <ConnectionType>");
        if (persistance.getDBPath() == null)
            throw new Exception("Il manque le champ <DBPath>");
    }
}
