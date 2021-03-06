package games.rockola.musa.servicios;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;

public class Dialogo extends Alert{
    //Mensajes
    //5 - Todo PERFECTO
    //7 - Todo mal
    //1 - Contraseña incorrecta
    //2 - El usuario no existe
    //3 - Lista vacia
    //4 - Usuario registrado
    //6 - El usuario ya existe
    //300 - Contraseñas no coinciden
    //800 - no hay siguiente
    //700 - no hay anterior
    //1010 - album correcto
    //1011 - album error

    
    public Dialogo (String estado, ButtonType... buttons) { 
        super(AlertType.NONE, "", buttons);
        
        //Se aplica estilo a los diálogos para mantener un aspecto consistente
        DialogPane dialog = this.getDialogPane();
        dialog.getStylesheets().add(
           getClass().getResource("/styles/Styles.css").toExternalForm());
        dialog.getStyleClass().add("Dialogos");
        
        if (estado.equals("5") || estado.equals("4") || estado.equals("16") || estado.equals("12") || estado.equals("1010")) {
            this.setAlertType(AlertType.INFORMATION);
            this.setHeaderText("¡Éxito!");
            this.setTitle("¡Éxito!"); 
        } else if (estado.equals("1") || estado.equals("2") || estado.equals("3") || 
                estado.equals("6") || estado.equals("300") || estado.equals("13") || 
                estado.equals("700") || estado.equals("800") || estado.equals("1011")) {
            this.setAlertType(AlertType.WARNING);
            this.setHeaderText("¡Atención!");
            this.setTitle("¡Atención!");
        }
        
        switch (estado) {
            case "2":
                this.setContentText("Usuario o contraseña incorrectos");
                break;
            case "300":
                this.setContentText("Las contraseñas no coinciden");
                break;
            case "4":
                this.setContentText("Usuario registrado correctamente");
                break;
            case "16":
                this.setContentText("Datos actualizados correctamente");
                break;
            case "17":
                this.setContentText("Error al actualizar los datos");
                break;
            case "12":
                this.setContentText("Artista registrado correctamente");
                break;
            case "13":
                this.setContentText("Ocurrió un error al guardar al artista");
                break;
            case "700":
                this.setContentText("No hay ninguna canción anterior");
                break;
            case "800":
                this.setContentText("No hay ninguna canción por reproducir");
                break;
            case "1010":
                this.setContentText("El álbum se guardó correctamente");
                break;
            case "1011":
                this.setContentText("Ocurrió un error al guardar el álbum");
                break;
            default:
                this.setContentText("El mensaje " + estado + " no está registrado");
                break;
        }
        this.setResizable(false);
        this.initStyle(StageStyle.UNDECORATED);
    }
}
