package games.rockola.musa.ws.pojos;

public class ListaCancion {
    private String album;
    private String artista;
    private Integer duracion;
    private Integer idCancion;
    private String nombre;

    public ListaCancion() {
    }

    public ListaCancion(String album, String artista, Integer duracion, Integer idCancion, String nombre) {
        this.album = album;
        this.artista = artista;
        this.duracion = duracion;
        this.idCancion = idCancion;
        this.nombre = nombre;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
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
}
