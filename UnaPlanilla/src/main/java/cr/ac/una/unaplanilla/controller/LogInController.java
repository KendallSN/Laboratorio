package cr.ac.una.unaplanilla.controller;

import cr.ac.una.unaplanilla.model.EmpleadoDto;
import cr.ac.una.unaplanilla.service.EmpleadoService;
import cr.ac.una.unaplanilla.util.AppContext;
import cr.ac.una.unaplanilla.util.FlowController;
import cr.ac.una.unaplanilla.util.Mensaje;
import cr.ac.una.unaplanilla.util.Respuesta;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;

public class LogInController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private MFXTextField txfUsuario;
    @FXML
    private MFXPasswordField psfClave;
    @FXML
    private MFXButton btnCancelar;
    @FXML
    private MFXButton btnIngresar;
    @FXML
    private ImageView imvFondo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imvFondo.fitHeightProperty().bind(root.heightProperty());
        imvFondo.fitWidthProperty().bind(root.widthProperty());
    }

    @FXML
    private void onActionBtnCancelar(ActionEvent event) {
        ((Stage) btnCancelar.getScene().getWindow()).close();
    }

    @FXML
    private void onActionBtnIngresar(ActionEvent event) {
        try {
            if (txfUsuario.getText() == null || txfUsuario.getText().isBlank()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Validación Usuario", getStage(),
                        "Es necesario digitar un usuario para ingresar al sistema.");
            } else if (psfClave.getText() == null || psfClave.getText().isBlank()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Validación Usuario", getStage(),
                        "Es necesario digitar una clave para ingresar al sistema.");
            } else {
                EmpleadoService empleadoService = new EmpleadoService();
                Respuesta respuesta = empleadoService.getUsuario(txfUsuario.getText(), psfClave.getText());
                if (respuesta.getEstado()) {
                    EmpleadoDto empleadoDto = (EmpleadoDto) respuesta.getResultado("Usuario");
                    AppContext.getInstance().set("Token", empleadoDto.getToken());
                    AppContext.getInstance().set("Usuario", empleadoDto);
                    if (getStage().getOwner() == null) {
                    
                    FlowController.getInstance().goMain();
                    }
                    getStage().close();
                } else {
                    new Mensaje().show(Alert.AlertType.ERROR, "Validación Usuario", respuesta.getMensaje());
                }

            }
        } catch (Exception ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, "Error Ingresando", ex);
            new Mensaje().show(Alert.AlertType.ERROR, "LogIn", "Error ingresando al sistema.");
        }
    }

    @Override
    public void initialize() {
        txfUsuario.clear();
        psfClave.clear();
    }
}
