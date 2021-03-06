/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package games.rockola.musa.ws.pojos;

/**
 *
 * @author lalo
 */
public class Artista {
    private Integer idArtista;
    private String nombre;
    private String biografia;
    private Integer idGenero;
    private String correoElectronico;
    private String password;

    public Artista() {
    }

    public Artista(Integer idArtista, String nombre, String biografia, Integer idGenero, String correoElectronico, String password) {
        this.idArtista = idArtista;
        this.nombre = nombre;
        this.biografia = biografia;
        this.idGenero = idGenero;
        this.correoElectronico = correoElectronico;
        this.password = password;
    }

    public Artista(String nombre, String biografia, Integer idGenero, String correoElectronico, String password) {
        this.nombre = nombre;
        this.biografia = biografia;
        this.idGenero = idGenero;
        this.correoElectronico = correoElectronico;
        this.password = password;
    }

    public Integer getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(Integer idArtista) {
        this.idArtista = idArtista;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biogafia) {
        this.biografia = biogafia;
    }

    public Integer getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Integer idGenero) {
        this.idGenero = idGenero;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
