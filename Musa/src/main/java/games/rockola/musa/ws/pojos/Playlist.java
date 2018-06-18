package games.rockola.musa.ws.pojos;

public class Playlist {
    private Integer idPlaylist;
    private String nombre;
    private String portada;
    private Integer idMelomano;

    public Playlist() {
    }

    public Playlist(String nombre, String portada, Integer idMelomano) {
        this.nombre = nombre;
        this.portada = portada;
        this.idMelomano = idMelomano;
    }

    public Integer getIdPlaylist() {
        return idPlaylist;
    }

    public void setIdPlaylist(Integer idPlaylist) {
        this.idPlaylist = idPlaylist;
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

    public Integer getIdMelomano() {
        return idMelomano;
    }

    public void setIdMelomano(Integer idMelomano) {
        this.idMelomano = idMelomano;
    }
}
