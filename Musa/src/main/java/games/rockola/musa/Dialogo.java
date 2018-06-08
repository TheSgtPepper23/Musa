package games.rockola.musa;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;

public class Dialogo extends Alert{
    
    public Dialogo (int estado, boolean error, String contentText, ButtonType... buttons) { 
        super(AlertType.NONE, contentText, buttons);
        
        //Se aplica estilo a los diálogos para mantener un aspecto consistente
        DialogPane dialog = this.getDialogPane();
        dialog.getStylesheets().add(
           getClass().getResource("/styles/Styles.css").toExternalForm());
        dialog.getStyleClass().add("Dialogos");
        
        if (estado != 200) {
            this.setAlertType(AlertType.ERROR);
            this.setHeaderText("¡Error!");
            this.setTitle("¡Error!"); 
        } else if (error) {
            this.setAlertType(AlertType.WARNING);
            this.setHeaderText("¡Atención!");
            this.setTitle("¡Error!");
        } else {
            this.setAlertType(AlertType.INFORMATION);
            this.setHeaderText("¡Éxito!");
            this.setTitle("¡Éxito!");
        }
        
        this.setResizable(false); 
        this.initStyle(StageStyle.UNDECORATED);
    }
}
