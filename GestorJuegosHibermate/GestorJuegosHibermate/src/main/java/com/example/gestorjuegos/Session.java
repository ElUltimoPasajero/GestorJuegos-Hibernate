package com.example.gestorjuegos;

import com.example.gestorjuegos.domain.juego.Game;
import com.example.gestorjuegos.domain.usuario.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class Session {
    @Getter
    @Setter
    private static User currentUser;//Guardamos el usuario en la sesion

    @Getter
    @Setter
    private static Game currentGame;//Guardamos el juego en la sesion

}
