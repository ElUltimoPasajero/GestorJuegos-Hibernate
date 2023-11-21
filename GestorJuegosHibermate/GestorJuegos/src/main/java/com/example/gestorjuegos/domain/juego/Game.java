package com.example.gestorjuegos.domain.juego;


import com.example.gestorjuegos.domain.usuario.User;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name="coleccionjuegos")
public class Game implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name="nombre")
  private String name;

  @Column(name="anio")
  private Long year;

  @Column(name="num_jugadores")
  private Long players;

  @Column(name="categoria")
  private String category;

  @Column(name="plataforma")
  private String plataform;

  @Column(name="estudio")
  private String studio;

  @Column(name="empresa")
  private String enterprise;

  @Column(name="formato")
  private String format;

  @Column(name="estado_juego")
  private String gameStatus;

  @Column(name="estado_caja")
  private String boxStatus;

  //@Column(name="usuario_id")
  //private Long usuarioId;

  @ManyToOne
  @JoinColumn( name = "usuario_id")
  private User user;

  public Game() {//Hacemos el constructor para inicializar lso campos numericos para que no de excepciones
    this.players=1L;
    this.year=1980L;
  }

  @Override
  public String toString() {
    return "Game{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", year=" + year +
            ", players=" + players +
            ", category='" + category + '\'' +
            ", plataform='" + plataform + '\'' +
            ", studio='" + studio + '\'' +
            ", enterprise='" + enterprise + '\'' +
            ", format='" + format + '\'' +
            ", gameStatus='" + gameStatus + '\'' +
            ", boxStatus='" + boxStatus + '\'' +
            ", user=" + user.getUsername() +
            '}';
  }


  public static void merge(Game origen, Game destino) { //Con este metodo escribimos los datos en origen
    destino.setName(origen.getName());
    destino.setCategory(origen.getGameStatus());
    destino.setStudio(origen.getStudio());
    destino.setEnterprise(origen.getEnterprise());
    destino.setPlataform(origen.getPlataform());
    destino.setBoxStatus(origen.getBoxStatus());
    destino.setGameStatus(origen.getGameStatus());
    destino.setFormat(origen.getFormat());
    destino.setPlayers(origen.getPlayers());
    destino.setUser(origen.getUser());
    destino.setYear(origen.getYear());
  }


}
