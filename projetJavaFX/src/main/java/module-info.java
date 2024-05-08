module be.helha.poo3.projet.javafx.projetjavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;
    requires java.sql;

    exports  be.helha.poo3.projet.javafx.projetjavafx;
    exports be.helha.poo3.projet.javafx.projetjavafx.dbmanager;
    exports be.helha.poo3.projet.javafx.projetjavafx.Application;
    exports be.helha.poo3.projet.javafx.projetjavafx.controleurs;

    opens be.helha.poo3.projet.javafx.projetjavafx.Application to javafx.fxml;
    opens be.helha.poo3.projet.javafx.projetjavafx to javafx.fxml;
}