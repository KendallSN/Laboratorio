/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.unaplanillaws.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Carlos
 */
@Schema(description = "Empleado")
public class EmpleadoDto {

    @Schema(description = "Id empleado", example = "1")
    private Long id;
    @Schema(description = "Nombre empleado", example = "Maria")
    private String nombre;
    @Schema(description = "Primer apellido empleado", example = "Ortiz")
    private String primerApellido;
    @Schema(description = "Segundo apellido empleado", example = "Mora")
    private String segundoApellido;
    @Schema(description = "Cedula empleado", example = "12233344")
    private String cedula;
    @Schema(description = "Genero empleado(F:femenino, M:masculino)", example = "F",allowableValues = "F,M")
    private String genero;
    @Schema(description = "Correo empleado", example = "mari@gmail.com")
    private String correo;
    @Schema(description = "Indica si el empleado es administrador(S:si, N:no)", example = "S", allowableValues = "S,N")
    private String administrador;
    @Schema(description = "Usuario empleado", example = "xxxx")
    private String usuario;
    @Schema(description = "Clave empleado", example = "xxxx")
    private String clave;
    @Schema(description = "Fecha de ingreso empleado")
    private LocalDate fechaIngreso;
    @Schema(description = "Fecha de salida empleado")
    private LocalDate fechaSalida;
    @Schema(description = "Indica el estado del empleado(A:activo, I:inactivo)", example = "A", allowableValues = "A,I")
    private String estado;
    @Schema(description = "Version del registro", example = "1")
    private Long version;
    @Schema(description = "Indica si el registro fue modificado", example = "true")
    private Boolean modificado;
    // TODO
    private LocalDateTime fecha;
    private String token;

    public EmpleadoDto() {
        this.modificado = false;
        this.fecha = LocalDateTime.now();
    }

    public EmpleadoDto(Empleado empleado) {
        this();
        this.id = empleado.getId();
        this.nombre = empleado.getNombre();
        this.primerApellido = empleado.getPrimerApellido();
        this.segundoApellido = empleado.getSegundoApellido();
        this.cedula = empleado.getCedula();
        this.genero = empleado.getGenero();
        this.correo = empleado.getCorreo();
        this.administrador = empleado.getAdministrador();
        this.usuario = empleado.getUsuario();
        this.clave = empleado.getClave();
        this.fechaIngreso = empleado.getFechaIngreso();
        if (empleado.getFechaSalida() != null) {
            this.fechaSalida = empleado.getFechaSalida();
        } else {
            this.fechaSalida = null;
        }
        this.estado = empleado.getEstado();
        this.version = empleado.getVersion();
        this.fecha = LocalDateTime.now();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getAdministrador() {
        return administrador;
    }

    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Boolean getModificado() {
        return modificado;
    }

    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EmpleadoDto other = (EmpleadoDto) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "EmpleadoDto{" + "id=" + id + ", nombre=" + nombre + ", primerApellido=" + primerApellido + ", segundoApellido=" + segundoApellido + ", cedula=" + cedula + '}';
    }

}
