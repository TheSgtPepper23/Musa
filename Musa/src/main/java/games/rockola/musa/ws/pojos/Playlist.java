package games.rockola.musa.ws.pojos;

public class Playlist {
    private Integer idPlaylist;
    private String nombre;
    private String portada;

    public Playlist() {
    }

    public Playlist(Integer idPlaylist, String nombre, String portada) {
        this.idPlaylist = idPlaylist;
        this.nombre = nombre;
        this.portada = portada;
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
}
