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
public class Cancion {
    private Integer idCancion;
    private String nombre;
    private Integer idAlbum;
    private Integer idGenero;
    private Object cancion;
    private Integer duracion;

    public Cancion() {
    }

    public Cancion(Integer idCancion, String nombre, Integer idAlbum, Integer idGenero, Object cancion, Integer duracion) {
        this.idCancion = idCancion;
        this.nombre = nombre;
        this.idAlbum = idAlbum;
        this.idGenero = idGenero;
        this.cancion = cancion;
        this.duracion = duracion;
    }

    public Integer getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(Integer idCancion) {
        this.idCancion = idCancion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(Integer idAlbum) {
        this.idAlbum = idAlbum;
    }

    public Integer getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Integer idGenero) {
        this.idGenero = idGenero;
    }

    public Object getCancion() {
        return cancion;
    }

    public void setCancion(Object cancion) {
        this.cancion = cancion;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }
    
    
}
