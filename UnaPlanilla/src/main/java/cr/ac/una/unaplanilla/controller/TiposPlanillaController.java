package cr.ac.una.unaplanilla.controller;

import cr.ac.una.unaplanilla.model.EmpleadoDto;
import cr.ac.una.unaplanilla.model.TipoPlanillaDto;
import cr.ac.una.unaplanilla.service.EmpleadoService;
import cr.ac.una.unaplanilla.service.TipoPlanillaService;
import cr.ac.una.unaplanilla.util.Formato;
import cr.ac.una.unaplanilla.util.Mensaje;
import cr.ac.una.unaplanilla.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author cbcar
 */
public class TiposPlanillaController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private TabPane tbpTipoPlanilla;
    @FXML
    private Tab tbpTipoPlanillas;
    @FXML
    private MFXTextField txtId;
    @FXML
    private MFXCheckbox chkActivo;
    @FXML
    private MFXTextField txtCodigo;
    @FXML
    private MFXTextField txtDescripcion;
    @FXML
    private MFXTextField txtPlanillasMes;
    @FXML
    private Tab tbpInclusionEmpleados;
    @FXML
    private MFXTextField txtIdEmpleado;
    @FXML
    private MFXTextField txtNombreEmpleado;
    @FXML
    private MFXButton btnAgregarEmpleado;
    @FXML
    private TableView<EmpleadoDto> tbvEmpleados;
    @FXML
    private TableColumn<EmpleadoDto, String> tbcCodigo;
    @FXML
    private TableColumn<EmpleadoDto, String> tbcNombre;
    @FXML
    private TableColumn<EmpleadoDto, Boolean> tbcEliminar;
    @FXML
    private MFXButton btnNuevo;
    @FXML
    private MFXButton btnBuscar;
    @FXML
    private MFXButton btnEliminar;
    @FXML
    private MFXButton btnGuardar;

    TipoPlanillaDto tipoPlanillaDto;
    EmpleadoDto empleadoDto;
    List<Node> requeridos = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtId.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        txtCodigo.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(4));
        txtDescripcion.delegateSetTextFormatter(Formato.getInstance().letrasFormat(40));
        txtPlanillasMes.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        this.tipoPlanillaDto = new TipoPlanillaDto();
        this.empleadoDto = new EmpleadoDto();
        nuevoTipoPlanilla();
        indicarRequeridos();
        
        txtIdEmpleado.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        txtNombreEmpleado.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(40));
        
        tbcCodigo.setCellValueFactory(cd-> cd.getValue().id);
        tbcNombre.setCellValueFactory(cd->cd.getValue().nombre);
        tbcEliminar.setCellValueFactory((TableColumn.CellDataFeatures<EmpleadoDto, Boolean> p) -> new SimpleBooleanProperty(p.getValue() != null));
        tbcEliminar.setCellFactory((TableColumn<EmpleadoDto, Boolean> p) -> new ButtonCell());
        
        tbvEmpleados.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends EmpleadoDto> observable, EmpleadoDto oldValue, EmpleadoDto newValue) -> {
            unbindEmpleado();
            if (newValue != null) {
                this.empleadoDto = newValue;
                bindEmpleado(false);
            }
        });
    }

    @Override
    public void initialize() {

    }

    private void nuevoTipoPlanilla() {
        unbindTipoPlanilla();
        this.tipoPlanillaDto = new TipoPlanillaDto();
        bindTipoPlanilla(true);
        txtId.clear();
        txtId.requestFocus();
        nuevoEmpleado();
        cargarEmpleados();
    }
    
    private void nuevoEmpleado() {
        unbindEmpleado();
        tbvEmpleados.getSelectionModel().select(null);
        this.empleadoDto = new EmpleadoDto();
        bindEmpleado(true);
        txtIdEmpleado.clear();
        txtIdEmpleado.requestFocus();
    }

    private void indicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtDescripcion, txtCodigo, txtPlanillasMes));
    }

    public String validarRequeridos() {
        Boolean validos = true;
        String invalidos = "";
        for (Node node : requeridos) {
            if (node instanceof MFXTextField && (((MFXTextField) node).getText()==null || ((MFXTextField) node).getText().isBlank())) {
                if (validos) {
                    invalidos += ((MFXTextField) node).getFloatingText();
                } else {
                    invalidos += "," + ((MFXTextField) node).getFloatingText();
                }
                validos = false;
            } else if (node instanceof MFXPasswordField && (((MFXPasswordField) node).getText()==null || ((MFXPasswordField) node).getText().isBlank())) {
                if (validos) {
                    invalidos += ((MFXPasswordField) node).getFloatingText();
                } else {
                    invalidos += "," + ((MFXPasswordField) node).getFloatingText();
                }
                validos = false;
            } else if (node instanceof MFXDatePicker && ((MFXDatePicker) node).getValue() == null) {
                if (validos) {
                    invalidos += ((MFXDatePicker) node).getFloatingText();
                } else {
                    invalidos += "," + ((MFXDatePicker) node).getFloatingText();
                }
                validos = false;
            } else if (node instanceof MFXComboBox && ((MFXComboBox) node).getSelectionModel().getSelectedIndex() < 0) {
                if (validos) {
                    invalidos += ((MFXComboBox) node).getFloatingText();
                } else {
                    invalidos += "," + ((MFXComboBox) node).getFloatingText();
                }
                validos = false;
            }
        }
        if (validos) {
            return "";
        } else {
            return "Campos requeridos o con problemas de formato [" + invalidos + "].";
        }
    }

    private void bindTipoPlanilla(Boolean nuevo) {
        if (!nuevo) {
            txtId.textProperty().bind(this.tipoPlanillaDto.id);
        }
        txtCodigo.textProperty().bindBidirectional(this.tipoPlanillaDto.codigo);
        txtDescripcion.textProperty().bindBidirectional(this.tipoPlanillaDto.descripcion);
        txtPlanillasMes.textProperty().bindBidirectional(this.tipoPlanillaDto.planillaPorMes);
        chkActivo.selectedProperty().bindBidirectional(this.tipoPlanillaDto.estado);
    }

    private void unbindTipoPlanilla() {
        txtId.textProperty().unbind();
        txtCodigo.textProperty().unbindBidirectional(this.tipoPlanillaDto.codigo);
        txtDescripcion.textProperty().unbindBidirectional(this.tipoPlanillaDto.descripcion);
        txtPlanillasMes.textProperty().unbindBidirectional(this.tipoPlanillaDto.planillaPorMes);
        chkActivo.selectedProperty().unbindBidirectional(this.tipoPlanillaDto.estado);
    }
    
    private void bindEmpleado(Boolean nuevo) {
        if (!nuevo) {
            txtIdEmpleado.textProperty().bind(this.empleadoDto.id);
        }
        txtNombreEmpleado.textProperty().bindBidirectional(this.empleadoDto.nombre);
    }

    private void unbindEmpleado() {
        txtIdEmpleado.textProperty().unbind();
        txtNombreEmpleado.textProperty().unbindBidirectional(this.empleadoDto.nombre);
    }

    @FXML
    private void onKeyPressTxtId(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && !txtId.getText().isBlank()) {
            cargarTipoPlanilla(Long.valueOf(txtId.getText()));
        }
    }

    private void cargarTipoPlanilla(Long id) {
        try {
            TipoPlanillaService service = new TipoPlanillaService();
            Respuesta respuesta = service.getTipoPlanilla(id);
            if (respuesta.getEstado()) {
                unbindTipoPlanilla();
                this.tipoPlanillaDto = (TipoPlanillaDto) respuesta.getResultado("TipoPlanilla");
                bindTipoPlanilla(false);
                validarRequeridos();
                cargarEmpleados();
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar Tipo Planilla", getStage(), respuesta.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(TiposPlanillaController.class.getName()).log(Level.SEVERE, "Error consultando el tipo de planilla.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar Tipo Planilla", getStage(), "Ocurrio un error consultando el tipo de planilla.");
        }
    }
    
    private void cargarEmpleados() {
        tbvEmpleados.getItems().clear();
        tbvEmpleados.setItems(this.tipoPlanillaDto.getEmpleados());
        tbvEmpleados.refresh();
    }

    @FXML
    private void onKeyPressedTxtCodigo(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER && !txtCodigo.getText().isBlank()) {
            cargarTipoPlanilla(Long.valueOf(txtCodigo.getText()));
        }
    }

    @FXML
    private void onKeyPressedTxtIdEmpleado(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && !txtIdEmpleado.getText().isBlank()) {
            cargarEmpleado(Long.valueOf(txtIdEmpleado.getText()));
        }
    }

    private void cargarEmpleado(Long id) {
        try {
            EmpleadoService service = new EmpleadoService();
            Respuesta respuesta = service.getEmpleado(id);
            if (respuesta.getEstado()) {
                unbindEmpleado();
                this.empleadoDto = (EmpleadoDto) respuesta.getResultado("Empleado");
                bindEmpleado(false);
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar empleado", getStage(), respuesta.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(TiposPlanillaController.class.getName()).log(Level.SEVERE, "Error consultando el empleado.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar Empleado", getStage(), "Ocurrio un error consultando el empleado.");
        }
    }

    @FXML
    private void onActionBtnAgregarEmpleado(ActionEvent event) {
        if (this.empleadoDto.getId() == null || this.empleadoDto.getNombre().isEmpty()) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Agregar empleado", getStage(), "Es necesario cargar un empleado para agregarlo a la lista.");
        } else if (tbvEmpleados.getItems() == null || !tbvEmpleados.getItems().stream().anyMatch(e -> e.equals(this.empleadoDto))) {
            this.empleadoDto.setModificado(true);
            tbvEmpleados.getItems().add(this.empleadoDto);
            tbvEmpleados.refresh();
        }

        nuevoEmpleado();
    }

    @FXML
    private void selectionChangeTabEmp(Event event) {
        if (tbpInclusionEmpleados.isSelected()) {
            if (this.tipoPlanillaDto.getId() == null) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Tipo planilla", getStage(), "Debe cargar el tipo de planilla al que desea agregar empleados.");
                tbpTipoPlanilla.getSelectionModel().select(tbpTipoPlanillas);
            }
        } else if (tbpTipoPlanillas.isSelected()) {

        }
    }

    @FXML
    private void onActionBtnNuevo(ActionEvent event) {
        if (tbpInclusionEmpleados.isSelected()) {
            nuevoEmpleado();
        } else if (tbpTipoPlanillas.isSelected()) {
            if (new Mensaje().showConfirmation("Limpiar tipo planilla", getStage(), "¿Está seguro que desea limpiar el registro?")) {
                nuevoTipoPlanilla();
            }
        }
    }

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {
        
    }

    @FXML
    private void onActionBtnEliminar(ActionEvent event) {
        try {
            if (this.tipoPlanillaDto.getId() == null) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar tipo planilla", getStage(), "Debe cargar el tipo de planilla que desea eliminar.");
            } else {
                TipoPlanillaService service = new TipoPlanillaService();
                Respuesta respuesta = service.eliminarTipoPlanilla(this.tipoPlanillaDto.getId());
                if (!respuesta.getEstado()) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar tipo planilla", getStage(), respuesta.getMensaje());
                } else {
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Eliminar tipo planilla", getStage(), "Tipo planilla eliminado correctamente.");
                    nuevoTipoPlanilla();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(TiposPlanillaController.class.getName()).log(Level.SEVERE, "Error eliminando el tipo planilla.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar tipo planilla", getStage(), "Ocurrio un error eliminando el tipo planilla.");
        }
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
        try {
            String invalidos = validarRequeridos();
            if (!invalidos.isEmpty()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Tipo Planilla", getStage(), invalidos);
            } else {
                TipoPlanillaService tipoPlanillaService = new TipoPlanillaService();
                Respuesta respuesta = tipoPlanillaService.guardarTipoPlanilla(tipoPlanillaDto);
                if (!respuesta.getEstado()) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Tipo Planilla", getStage(), respuesta.getMensaje());
                } else {
                    unbindTipoPlanilla();
                    this.tipoPlanillaDto = (TipoPlanillaDto) respuesta.getResultado("TipoPlanilla");
                    bindTipoPlanilla(false);
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar Tipo Planilla", getStage(), "Tipo Planilla actualizada correctamente.");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(EmpleadosController.class.getName()).log(Level.SEVERE, "Error guardando el tipo de planilla.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Tipo Planilla", getStage(), "Ocurrio un error guardando el tipo de planilla.");
        }
    }
    
    private class ButtonCell extends TableCell<EmpleadoDto, Boolean> {

        final Button cellButton = new Button();

        ButtonCell() {
            cellButton.setPrefWidth(500);
            cellButton.getStyleClass().add("jfx-btnimg-tbveliminar");

            cellButton.setOnAction((ActionEvent t) -> {
                EmpleadoDto emp = (EmpleadoDto) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
                tipoPlanillaDto.getEmpleadosEliminados().add(emp);
                tbvEmpleados.getItems().remove(emp);
                tbvEmpleados.refresh();
            });
        }

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(cellButton);
            }
        }
    }

}
