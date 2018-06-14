/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package games.rockola.musa.ws.pojos;

/**
 *
 * @author José Andrés Domínguez González
 */
public class FotoArtista {
    
    private Integer idFoto;
    private String foto;
    private Integer idArtista;

    public FotoArtista() {
    }

    public FotoArtista(Integer idFoto, String foto, Integer idArtista) {
        this.idFoto = idFoto;
        this.foto = foto;
        this.idArtista = idArtista;
    }

    public FotoArtista(String foto, Integer idArtista) {
        this.foto = foto;
        this.idArtista = idArtista;
    }

    public Integer getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(Integer idFoto) {
        this.idFoto = idFoto;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Integer getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(Integer idArtista) {
        this.idArtista = idArtista;
    }
}
