/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package games.rockola.musa.ws.pojos;

import java.sql.Date;

/**
 *
 * @author lalo
 */
public class Album {
    private Integer idAlbum;
    private String nombre;
    private String portada;
    private String fechaLanzamiento;
    private String companiaDiscografica;
    private Integer idArtista;

    public Album() {
    }

    public Album(String nombre, String portada, String fechaLanzamiento, String companiaDiscografica, Integer idArtista) {
        this.nombre = nombre;
        this.portada = portada;
        this.fechaLanzamiento = fechaLanzamiento;
        this.companiaDiscografica = companiaDiscografica;
        this.idArtista = idArtista;
    }

    public Integer getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(Integer idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }

    public String getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(String fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public String getCompaniaDiscografica() {
        return companiaDiscografica;
    }

    public void setCompaniaDiscografica(String companiaDiscografica) {
        this.companiaDiscografica = companiaDiscografica;
    }

    public Integer getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(Integer idArtista) {
        this.idArtista = idArtista;
    }
    
    
}
