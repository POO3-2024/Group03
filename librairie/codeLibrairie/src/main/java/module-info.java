module be.helha.lib.poo3 {
    requires transitive java.sql;
    requires transitive com.google.gson;


    exports be.helha.lib.poo3.dao;
    exports be.helha.lib.poo3.daoImpl;
    exports be.helha.lib.poo3.domaine;

}
