package com.pros.crudmongo.db;

import com.mongodb.*;
import com.pros.crudmongo.model.Equipo;

import java.util.ArrayList;
import java.util.List;

public class ConexionMongoDB {
    private static final String DATABASE_USER = "simonzdjgtz";
    private static final String DATABASE_PASSWORD = "60dObDyjzDlOa2Tv";
    private static final String DATABASE_NAME = "Equipo";

    // METODO PARA CREAR LA CONEXION A MONGODB
    public  MongoClient crearConexion() {

            MongoClientURI uri = new MongoClientURI("mongodb+srv://" + DATABASE_USER + ":" + DATABASE_PASSWORD
                    + "@cluster0.c9dmxm6.mongodb.net/" + DATABASE_NAME + "?retryWrites=true&w=majority");
            MongoClient mongoClient = new MongoClient(uri);
            return mongoClient;

    }

    // MUESTRA TODOS LOS DOCUMENTOS DE LA COLECCION USUARIOS
    public List<Equipo> mostrarColeccion(DB db, String coleccion) {
        DBCollection colec = db.getCollection(coleccion);
        DBCursor cursor = colec.find();

        List<Equipo> equipos = new ArrayList<>();
        while(cursor.hasNext()) {
            Equipo e = new Equipo();
            DBObject document = cursor.next();
            e.setId(String.valueOf(document.get("_id")));
            e.setEntrenador(String.valueOf(document.get("entrenador")));
            e.setLiga(String.valueOf(document.get("liga")));
            e.setNombre(String.valueOf(document.get("nombre")));
            e.setCapitan(String.valueOf(document.get("capitan")));
            e.setGoles(Integer.parseInt(document.get("goles").toString()));
            e.setPais(String.valueOf(document.get("pais")));
            equipos.add(e);
        }
        return equipos;
    }

}
