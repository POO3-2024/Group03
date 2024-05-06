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
     * Fonction qui lis le fichier de config JSON, crée un objet Persistance et l'incrémente avec ce qu'il a lu dans le fichier de config
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
        return persistance;
    }
}
