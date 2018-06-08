package games.rockola.musa.ws.pojos;

public class Mensaje {
    private boolean error;
    private String mensaje;
    private Integer estado;

    public Mensaje(){}

    public Mensaje(boolean error, String mensaje, Integer estado) {
        this.error = error;
        this.mensaje = mensaje;
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }
}