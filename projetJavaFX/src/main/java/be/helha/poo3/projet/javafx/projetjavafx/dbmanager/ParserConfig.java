package be.helha.poo3.projet.javafx.projetjavafx.dbmanager;

import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

/**
 * La classe ParserConfig sert à lire le fichier de config JSON et de le mettre dans un objet Persistance
 *
 * @author Diesbecq Aaron
 * @see be.helha.poo3.projet.javafx.projetjavafx.dbmanager.ParserConfig
 */
public class ParserConfig {

    /**
     * Méthode qui lis le fichier de config JSON, crée un objet Persistance et l'incrémente avec ce qu'il a lu dans le fichier de config
     * @param fichierConfig Chemin d'accès vers le fichier de config
     * @return Un objet de type Persistance
     * @throws Exception
     */
    public static Persistance lireConfig(String fichierConfig) throws Exception {
        FileReader fileReader = new FileReader(fichierConfig, StandardCharsets.UTF_8);
        JsonReader jsonReader = new JsonReader(fileReader);
        Gson gson = new Gson();
        Persistance persistance = gson.fromJson(jsonReader, Persistance.class);
        jsonReader.close();
        fileReader.close();
        verif(persistance);
        return persistance;
    }

    /**
     * Vérifie si les champs nécessaire du JSON ne sont pas manquant grâce à l'objet Persistance
     * @param persistance Objet qui contient les infos du fichier de config
     * @throws Exception
     */
    public static void verif(Persistance persistance) throws Exception{
        if (persistance.getType() == null)
            throw new Exception("Il manque le champ <ConnectionType>");
        if (persistance.getPath() == null)
            throw new Exception("Il manque le champ <DBPath>");
    }
}
