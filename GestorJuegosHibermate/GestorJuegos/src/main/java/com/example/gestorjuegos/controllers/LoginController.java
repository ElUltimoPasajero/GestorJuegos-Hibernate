package com.example.gestorjuegos.controllers;

import com.example.gestorjuegos.App;
import com.example.gestorjuegos.Session;
import com.example.gestorjuegos.domain.HibernateUtil;
import com.example.gestorjuegos.domain.usuario.User;
import com.example.gestorjuegos.domain.usuario.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button btnLogin;
    @FXML
    private Button btnCancel;
    @FXML
    private Label info;
    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPassword;

    @FXML
    public void login(ActionEvent actionEvent) {
        String user = txtUser.getText();
        String pass = txtPassword.getText();

        if( user.length()<4 || pass.length()<4 ){
            info.setText("Introduce los datos");
            info.setStyle("-fx-background-color:red; -fx-text-fill: white;");

        } else{

            /* ACCESO A BASE DE DATOS PARA LA VALIDACION */
            User u = (new UserDAO()).validateUser( user, pass );

            if(u==null){
                info.setText("Usuario no encontrado");
                info.setStyle("-fx-background-color:red; -fx-text-fill: white;");
            }else {
                info.setText("Usuario " + user + "(" + pass + ") correcto");
                info.setStyle("-fx-background-color:green; -fx-text-fill: white;");

                Session.setCurrentUser(u);

                /* Guardar usuario en sesión e ir a la proxima ventana */

                try {
                    App.changeScene("main-view.fxml","Colección de videojuegos");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }


    }

    @FXML
    public void cancel(ActionEvent actionEvent) {
        txtPassword.setText("");
        txtUser.setText("");
        info.setText("");
        info.setStyle("-fx-background-color: transparent;");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}