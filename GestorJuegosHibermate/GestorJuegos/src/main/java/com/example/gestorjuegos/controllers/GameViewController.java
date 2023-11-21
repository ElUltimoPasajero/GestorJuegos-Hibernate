package com.example.gestorjuegos.controllers;

import com.example.gestorjuegos.App;
import com.example.gestorjuegos.Session;
import com.example.gestorjuegos.domain.DAO;
import com.example.gestorjuegos.domain.HibernateUtil;
import com.example.gestorjuegos.domain.juego.Game;
import com.example.gestorjuegos.domain.juego.GameDAO;
import com.example.gestorjuegos.domain.usuario.User;
import com.example.gestorjuegos.domain.usuario.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;
import org.hibernate.Transaction;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameViewController implements Initializable {

    private final GameDAO gameDao = new GameDAO();
    @javafx.fxml.FXML
    private MenuItem mVolver;
    @javafx.fxml.FXML
    private MenuItem mCerrar;
    @javafx.fxml.FXML
    private Label gameInfo;
    @javafx.fxml.FXML
    private Label lblTittle;
    @javafx.fxml.FXML
    private TextField txtName;
    @javafx.fxml.FXML
    private Spinner<Integer> spinnerYear;
    @javafx.fxml.FXML
    private ChoiceBox<User> comboUser;
    @javafx.fxml.FXML
    private Button btnSave;
    @javafx.fxml.FXML
    private Button btnDelete;
    @javafx.fxml.FXML
    private Button btnReturn;
    @javafx.fxml.FXML
    private Spinner<Integer> spinnerPlayers;
    @javafx.fxml.FXML
    private ComboBox<String> comboCategory;
    @javafx.fxml.FXML
    private ComboBox<String> comboPlatform;
    @javafx.fxml.FXML
    private ComboBox<String> comboStudio;
    @javafx.fxml.FXML
    private ComboBox<String> comboEnterprise;
    @javafx.fxml.FXML
    private ComboBox<String> comboFormat;
    @javafx.fxml.FXML

    private ComboBox<String> combogameStatus;
    @javafx.fxml.FXML
    private ComboBox<String> comboBoxStatus;

    @javafx.fxml.FXML
    public void volver(ActionEvent actionEvent) {
        try {
            Session.setCurrentGame(null); //Ponemos el juego elegido antes en null

            App.changeScene("main-view.fxml", "Colección de videojuegos");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void salir(ActionEvent actionEvent) {
        try {
            App.changeScene("login-view.fxml", "Login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        gameInfo.setText(Session.getCurrentGame().toString());
        spinnerPlayers.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 4, Math.toIntExact(Session.getCurrentGame().getPlayers()), 1));
        spinnerYear.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1976, 2023, Math.toIntExact(Session.getCurrentGame().getYear()), 1));
        comboUser.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User user) {

                return (user != null) ? user.getUsername() : "";  //Me devuelve el nombre d eusuario


            }

            @Override
            public User fromString(String s) {
                return null;
            }
        });
        txtName.setText(Session.getCurrentGame().getName());
        // txtCategory.setText(Session.getCurrentGame().getCategory());
        // txtBoxStatus.setText(Session.getCurrentGame().getBoxStatus());
        //txtEnterprise.setText(Session.getCurrentGame().getEnterprise());
        //txtGameStatus.setText(Session.getCurrentGame().getGameStatus());
        //txtFormat.setText(Session.getCurrentGame().getFormat());
        //txtPlatform.setText(Session.getCurrentGame().getPlataform());
        //txtStudio.setText(Session.getCurrentGame().getStudio());
        lblTittle.setText(Session.getCurrentGame().getName());

        comboUser.getItems().addAll(new UserDAO().getAll());

        comboUser.setValue(Session.getCurrentUser());

        var categories = new GameDAO().getCategories();//Nos treamos las categorias y las metemos en el combo
        comboCategory.getItems().addAll(categories);
        comboCategory.setValue(Session.getCurrentGame().getCategory());

        var studio = new GameDAO().getStudio();
        comboStudio.getItems().addAll(studio);
        comboStudio.setValue(Session.getCurrentGame().getStudio());

        var enterprise = new GameDAO().getEnterprise();
        comboEnterprise.getItems().addAll(enterprise);
        comboEnterprise.setValue(Session.getCurrentGame().getEnterprise());

       /* var platform=new GameDAO().getPlatform();
        comboPlatform.getItems().addAll(platform);
        ;*/

        var gameStatus = new GameDAO().getGameStatus();
        combogameStatus.getItems().addAll(gameStatus);
        combogameStatus.setValue(Session.getCurrentGame().getGameStatus());

        var boxStatus = new GameDAO().getBoxStatus();
        comboBoxStatus.getItems().addAll(boxStatus);
        comboBoxStatus.setValue(Session.getCurrentGame().getBoxStatus());

        var format = new GameDAO().getFormat();
        comboFormat.getItems().addAll(format);
        comboFormat.setValue(Session.getCurrentGame().getFormat());

        comboPlatform.setValue(Session.getCurrentGame().getPlataform());


        comboPlatform.getItems().addAll(gameDao.getDistinctAttribute("plataform")); //Otra forma de hacerlo


    }

    @Deprecated
    public void returnbar(ActionEvent actionEvent) {
    }


    @javafx.fxml.FXML
    public void save(ActionEvent actionEvent) { //editamos el nombre del juego


        Game g = Session.getCurrentGame(); //Guardamos el juego a actualizar en una varuable

        if (txtName.getText().length() > 1) g.setName(txtName.getText());
        if (comboCategory.getValue().length() > 1) g.setCategory(comboCategory.getValue());
        if (comboStudio.getValue().length() > 1) g.setStudio(comboStudio.getValue());
        if (comboFormat.getValue().length() > 1) g.setFormat(comboFormat.getValue());
        if (comboBoxStatus.getValue().length() > 1) g.setBoxStatus(comboBoxStatus.getValue());
        if (comboPlatform.getValue().length() > 1) g.setPlataform(comboPlatform.getValue());

        g.setYear(Long.valueOf(spinnerYear.getValue()));
        g.setPlayers(Long.valueOf(spinnerPlayers.getValue()));



        if (g.getId() != null) {
            gameDao.update(g);

        } else {

            gameDao.save(g);
        }
        volver(null);//Volvemos al salvar la edicion del juego
    }


    @javafx.fxml.FXML
    public void delete(ActionEvent actionEvent) {  //Metodo para borrar un juego
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("¿Deseas borrasr " + Session.getCurrentGame().getName() + " del listado?");
        var result = alert.showAndWait().get();//me dice que boton se ha pulsado

        if (result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {            //Si el resultado de aceptar es OK DONE borramos el juego

            gameDao.delete(Session.getCurrentGame());
            volver(null);

        }

    }
}