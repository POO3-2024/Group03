module be.helha.poo3.projet.javafx.projetjavafx {

    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;
    requires be.helha.lib.poo3;
    requires java.sql;

    exports  be.helha.poo3.projet.javafx.projetjavafx;
    exports be.helha.poo3.projet.javafx.projetjavafx.controleurs;

    opens be.helha.poo3.projet.javafx.projetjavafx to javafx.fxml;
}