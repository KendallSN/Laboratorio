package cr.ac.una.unaplanilla.controller;

import cr.ac.una.unaplanilla.model.EmpleadoDto;
import cr.ac.una.unaplanilla.util.AppContext;
import cr.ac.una.unaplanilla.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author cbcar
 */
public class PrincipalController extends Controller implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private MFXButton btnEmpleados;
    @FXML
    private MFXButton btnTiposPlanilla;
    @FXML
    private Label lblUsuario;
    @FXML
    private Label lblNombreU;
    @FXML
    private MFXButton btnCerrarSesion;
    @FXML
    private MFXButton btnSalir;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EmpleadoDto usuario = (EmpleadoDto) AppContext.getInstance().get("Usuario");
        if (Objects.nonNull(usuario)) {
            lblUsuario.setText(usuario.getUsuario().toUpperCase());
            lblNombreU.setText(usuario.getNombre().concat(" ").concat(usuario.getPrimerApellido()).toUpperCase());
        }
    }

    @Override
    public void initialize() {

    }

    @FXML
    private void onActionBtnEmpleados(ActionEvent event) {
        FlowController.getInstance().goView("EmpleadosView");
    }

    @FXML
    private void onActionBtnTiposPlanilla(ActionEvent event) {
        FlowController.getInstance().goView("TiposPlanillaView");
    }

    @FXML
    private void onActionBtnCerrarSesion(ActionEvent event) {
        FlowController.getInstance().initialize();
        FlowController.getInstance().goViewInWindow("LogInView");
        ((Stage) btnCerrarSesion.getScene().getWindow()).close();
    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
        FlowController.getInstance().salir();
    }

}
