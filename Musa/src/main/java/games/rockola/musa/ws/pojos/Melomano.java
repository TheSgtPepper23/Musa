package games.rockola.musa.ws.pojos;

public class Melomano {
    private Integer idMelomano;
    private String nombreMelomano;
    private String nombre;
    private String apellidos;
    private String password;
    private byte [] fotoPerfil;
    private String correoElectronico;

    public Melomano() {
    }

    public Melomano(Integer idMelomano, String nombreMelomano, String nombre, String apellidos, String password, byte[] fotoPerfil, String correoElectronico) {
        this.idMelomano = idMelomano;
        this.nombreMelomano = nombreMelomano;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.password = password;
        this.fotoPerfil = fotoPerfil;
        this.correoElectronico = correoElectronico;
    }

    public Integer getIdMelomano() {
        return idMelomano;
    }

    public void setIdMelomano(Integer idMelomano) {
        this.idMelomano = idMelomano;
    }

    public String getNombreMelomano() {
        return nombreMelomano;
    }

    public void setNombreMelomano(String nombreMelomano) {
        this.nombreMelomano = nombreMelomano;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(byte[] fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
}
