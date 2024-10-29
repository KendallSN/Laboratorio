package cr.ac.una.unaplanillaws.controller;

import cr.ac.una.unaplanillaws.model.EmpleadoDto;
import cr.ac.una.unaplanillaws.service.EmpleadoService;
import cr.ac.una.unaplanillaws.util.CodigoRespuesta;
import cr.ac.una.unaplanillaws.util.JwTokenHelper;
import cr.ac.una.unaplanillaws.util.Respuesta;
import cr.ac.una.unaplanillaws.util.Secure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//siempre esto:
@Secure
@Path("/EmpleadoController")
@Tag(name = "Empleados", description = "Operaciones sobre empleados")
@SecurityRequirement(name = "jwt-auth") //para poder utilizar la interfaz
public class EmpleadoController {

    @EJB //me crea una instancia para inyectarlo donde nosotros la vamos a necesitar
    EmpleadoService empleadoService;
    @Context
    SecurityContext securityContext;

    @GET
    @Path("/usuario/{usuario}/{clave}") //ponemos los parametros
    @Consumes(MediaType.APPLICATION_JSON) //recibe
    @Produces(MediaType.APPLICATION_JSON) //revuelve 
    //Documentar
    @Operation(description = "Autentica un usuario")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario Autenticado", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = EmpleadoDto.class))),
        @ApiResponse(responseCode = "404", description = "Usuario No Autenticado", content = @Content(mediaType = MediaType.TEXT_PLAIN)),
        @ApiResponse(responseCode = "500", description = "Error interno durante autenticación del usuario", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })

    //Web service siempre devuelve un objeto Response
    public Response validarUsuario(@Parameter(description = "Codigo del usuario") @PathParam("usuario") String usuario, @Parameter(description = "Contraseña del usuario") @PathParam("clave") String clave) {
        try {
            Respuesta res = empleadoService.validarUsuario(usuario, clave);//Necesitamos llamar service de empleado
            if (!res.getEstado()) {
                return Response.status(res.getCodigoRespuesta().getValue()).entity(res.getMensaje()).build();
            }
            EmpleadoDto empleadoDto = (EmpleadoDto) res.getResultado("Empleado");
            empleadoDto.setToken(JwTokenHelper.getInstance().generatePrivateKey(usuario));
            return Response.ok(empleadoDto).build();

        } catch (Exception ex) {
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo el usuario").build();
        }
    }

    @GET
    @Path("/renovar")
    @Operation(description = "Genera un nuevo token a partir de un token de renovación")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Renovación del token exitosa", content = @Content(mediaType = MediaType.TEXT_PLAIN)),
        @ApiResponse(responseCode = "401", description = "No se pudo renovar el token.", content = @Content(mediaType = MediaType.TEXT_PLAIN)),
        @ApiResponse(responseCode = "500", description = "Error renovando el token", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    public Response renovarToken() {
        try {
            String usuarioRequest = securityContext.getUserPrincipal().getName();
            if (usuarioRequest != null && !usuarioRequest.isEmpty()) {
                return Response.ok(JwTokenHelper.getInstance().generatePrivateKey(usuarioRequest)).build();
            } else {
                return Response.status(CodigoRespuesta.ERROR_PERMISOS.getValue()).entity("No se pudo renovar el token.").build();
            }
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error renovando el token").build();
        }
    }

    @GET
    @Path("/empleado/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmpleado(@PathParam("id") Long id) {
        try {
            Respuesta res = empleadoService.getEmpleado(id);
            if (!res.getEstado()) {
                return Response.status(res.getCodigoRespuesta().getValue()).entity(res.getMensaje()).build();
            }
            EmpleadoDto empleadoDto = (EmpleadoDto) res.getResultado("Empleado");
            return Response.ok(empleadoDto).build();

        } catch (Exception ex) {
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo el usuario").build();
        }
    }

    @GET
    @Path("/empleados/{cedula}/{nombre}/{pApellido}/{sApellido}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getEmpleados(@PathParam("cedula") String cedula, @PathParam("nombre") String nombre, @PathParam("pApellido") String pApellido, @PathParam("sApellido") String sApellido) {
        try {
            Respuesta res = empleadoService.getEmpleados(cedula, nombre, pApellido, sApellido);
            if (!res.getEstado()) {
                return Response.status(res.getCodigoRespuesta().getValue()).entity(res.getMensaje()).build();
            }
            return Response.ok(new GenericEntity<List<EmpleadoDto>>((List<EmpleadoDto>) res.getResultado("Empleados")) { //Le paso la lista
            }).build();
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo el usuario").build();
        }
    }

    @POST
    @Path("/empleado")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response guardarEmpleado(EmpleadoDto empleadoDto) {
        try {
            Respuesta res = empleadoService.guardarEmpleado(empleadoDto);
            if (!res.getEstado()) {
                return Response.status(res.getCodigoRespuesta().getValue()).entity(res.getMensaje()).build();
            }
            return Response.ok((EmpleadoDto) res.getResultado("Empleado")).build();

        } catch (Exception ex) {
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error guardando el usuario").build();
        }
    }

    @DELETE
    @Path("/empleado/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response eliminarEmpleado(@PathParam("id") Long id) {
        try {
            Respuesta res = empleadoService.eliminarEmpleado(id);
            if (!res.getEstado()) {
                return Response.status(res.getCodigoRespuesta().getValue()).entity(res.getMensaje()).build();
            }
            return Response.ok((EmpleadoDto) res.getResultado("Empleado")).build();

        } catch (Exception ex) {
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error eliminando el usuario").build();
        }
    }

}
