/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package games.rockola.musa.ws;

/**
 *
 * @author José Andrés Domínguez González
 */
public class Response {
    private boolean error;
    private int status;
    private String result;

    public Response(){}

    public Response(int status, String result, boolean error) {
        this.status = status;
        this.result = result;
        this.error = error;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
