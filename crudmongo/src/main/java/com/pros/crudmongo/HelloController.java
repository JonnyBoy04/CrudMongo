package com.pros.crudmongo;

import com.mongodb.*;
import com.pros.crudmongo.db.ConexionMongoDB;
import com.pros.crudmongo.model.Equipo;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnBorrar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private TableColumn<Equipo, String> colEntrenador;

    @FXML
    private TableColumn<Equipo, String> colId;

    @FXML
    private TableColumn<Equipo, Integer> colNoGoles;

    @FXML
    private TableColumn<Equipo, String> colNombre;

    @FXML
    private TableView<Equipo> tblEquipo;

    @FXML
    private TextField txtCapitan;

    @FXML
    private TextField txtEntrenador;

    @FXML
    private TextField txtLiga;

    @FXML
    private TextField txtNoGoles;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPais;
    DB db;

    ConexionMongoDB connMongo;

    ObservableList<Equipo> observableList;
    private int equipoEnTabla;

    // METODO PARA INSERTAR UN DOCUMENTO (REGISTRO)
    public void registrarEquipo() throws Exception {
        DBCollection colec = db.getCollection("equipos");
        String nombre = txtNombre.getText();
        String capitan = txtCapitan.getText();
        String entrenador = txtEntrenador.getText();
        String liga = txtLiga.getText();
        int goles = Integer.parseInt(txtNoGoles.getText());
        String pais = txtPais.getText();

        BasicDBObject documento = new BasicDBObject();
        documento.put("nombre", nombre);
        documento.put("capitan", capitan);
        documento.put("entrenador", entrenador);
        documento.put("liga", liga);
        documento.put("goles", goles);
        documento.put("pais", pais);

        colec.insert(documento);
        refrescarTabla();
    }

    public void modificarEquipo() throws Exception {
        DBCollection colec = db.getCollection("equipos");
        String nombre = txtNombre.getText();
        String capitan = txtCapitan.getText();
        String entrenador = txtEntrenador.getText();
        String liga = txtLiga.getText();
        int goles = Integer.parseInt(txtNoGoles.getText());
        String pais = txtPais.getText();

        // SENTENCIA CON LA INFORMACION A REMPLAZAR
        BasicDBObject actualizarDocumento = new BasicDBObject("$set", new BasicDBObject()
                .append("nombre", nombre)
                .append("capitan", capitan)
                .append("entrenador", entrenador)
                .append("liga", liga)
                .append("goles", goles)
                .append("pais", pais));

        // BUSCA EL DOCUMENTO EN LA COLECCION
        BasicDBObject buscarPorNombre = new BasicDBObject();
        buscarPorNombre.append("nombre", nombre);

        // REALIZA EL UPDATE
        colec.updateMulti(buscarPorNombre, actualizarDocumento);
        refrescarTabla();
    }

    // METODO PARA ELIMINAR UN DOCUMENTO (REGISTRO)
    public  void borrarDocumento() throws Exception {
        DBCollection colec = db.getCollection("equipos");
        String nombre = txtNombre.getText();

        colec.remove(new BasicDBObject().append("nombre", nombre));
        refrescarTabla();
    }

    private final ListChangeListener<Equipo> selectorTabla = new ListChangeListener<Equipo>() {
        @Override
        public void onChanged(Change<? extends Equipo> c) {
            colocarEquipoSel();
        }
    };

    public Equipo getTablaEquipo(){
        if(tblEquipo != null){
            List<Equipo> tabla = tblEquipo.getSelectionModel().getSelectedItems();
            if(tabla.size() == 1){
                final Equipo EquipoSel = tabla.get(0);
                return EquipoSel;
            }
        }
        return null;
    }

    public void limpiar(){
        txtNombre.setText("");
        txtCapitan.setText("");
        txtEntrenador.setText("");
        txtLiga.setText("");
        txtPais.setText("");
        txtNoGoles.setText("");
    }

    public void colocarEquipoSel(){
        final Equipo equipo = getTablaEquipo();
        equipoEnTabla = observableList.indexOf(equipo);

        if(equipo != null){
            txtNombre.setText(equipo.getNombre());
            txtCapitan.setText(equipo.getCapitan());
            txtEntrenador.setText(equipo.getEntrenador());
            txtLiga.setText(equipo.getLiga());
            txtPais.setText(equipo.getPais());
            txtNoGoles.setText(String.valueOf(equipo.getGoles()));
        }
    }

    public void refrescarTabla() throws Exception {
        colNombre.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getNombre()));
        colEntrenador.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getEntrenador()));
        colNoGoles.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getGoles()));
        colId.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getId()));

        observableList = FXCollections.observableArrayList(connMongo.mostrarColeccion(db,"equipos"));
        tblEquipo.setItems(observableList);
        limpiar();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connMongo = new ConexionMongoDB();
        MongoClient mongo = connMongo.crearConexion();
        if (mongo!=null){
            db = mongo.getDB("equipo");
        }
        try {
            refrescarTabla();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        final ObservableList<Equipo> tablaJugadorSel = tblEquipo.getSelectionModel().getSelectedItems();
        tablaJugadorSel.addListener(selectorTabla);
    }
}
