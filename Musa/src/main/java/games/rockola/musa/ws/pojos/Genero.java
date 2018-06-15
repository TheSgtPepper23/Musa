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
public class Genero {
    private Integer idGenero;
    private String genero;

    public Genero() {
    }

    public Genero(Integer idGenero, String genero) {
        this.idGenero = idGenero;
        this.genero = genero;
    }

    public Genero(String genero) {
        this.genero = genero;
    }

    public Integer getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Integer idGenero) {
        this.idGenero = idGenero;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    @Override
    public String toString(){
        return this.genero;
    }
}
