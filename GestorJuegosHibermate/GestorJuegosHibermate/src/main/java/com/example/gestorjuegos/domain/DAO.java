package com.example.gestorjuegos.domain;

import java.util.ArrayList;

public interface DAO<T> {//Tenemos que pasarle en el operador diamante de que tipo va a ser el DAO

    public ArrayList<T> getAll(); //Aqui hariamos un gettAll de juegos, usuarios, etc ...
    public T get(Long id);
    public T save(T data);
    public void update(T data);
    public void delete(T data);

}
