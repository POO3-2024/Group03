module be.helha.poo3.projet.javafx.projetjavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;
    requires java.sql;

    opens be.helha.poo3.projet.javafx.projetjavafx to javafx.fxml;
    exports be.helha.poo3.projet.javafx.projetjavafx;
    exports be.helha.poo3.projet.javafx.projetjavafx.dbmanager;
}