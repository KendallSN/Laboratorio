package cr.ac.una.unaplanillaws.controller;

import cr.ac.una.unaplanillaws.model.TipoPlanillaDto;
import cr.ac.una.unaplanillaws.service.TipoPlanillaService;
import cr.ac.una.unaplanillaws.util.CodigoRespuesta;
import cr.ac.una.unaplanillaws.util.Respuesta;
import cr.ac.una.unaplanillaws.util.Secure;
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
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Secure
@Path("/TipoPlanillaController")
@Tag(name = "TipoPlanilla", description = "Operaciones sobre tipo planilla")
@SecurityRequirement(name = "jwt-auth") //para poder utilizar la interfaz

public class TipoPlanillaController {

    @EJB
    TipoPlanillaService tipoPlanillaService;

    @GET
    @Path("/tipoplanilla/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getTipoPlanilla(@PathParam("id") Long id) {
        try {
            Respuesta res = tipoPlanillaService.getTipoPlanilla(id);
            if (!res.getEstado()) {
                return Response.status(res.getCodigoRespuesta().getValue()).entity(res.getMensaje()).build();
            }
            TipoPlanillaDto tipoPlanillaDto = (TipoPlanillaDto) res.getResultado("TipoPlanilla");
            return Response.ok(tipoPlanillaDto).build();
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo el tipo planilla").build();
        }
    }

    @GET
    @Path("tipoplanilla/{codigo}/{descripcion}/{plaxmes}/{idemp}/{cedula}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTipoPlanillas(@PathParam("codigo") String codigo, @PathParam("descripcion") String descripcion, @PathParam("plaxmes") String plaxmes, @PathParam("idemp") String idemp, @PathParam("cedula") String cedula) {
        try {
            Respuesta res = tipoPlanillaService.getTipoPlanillas(codigo, descripcion, plaxmes, idemp, cedula);
            if (!res.getEstado()) {
                return Response.status(res.getCodigoRespuesta().getValue()).entity(res.getMensaje()).build();
            }
            return Response.ok(new GenericEntity<List<TipoPlanillaDto>>((List<TipoPlanillaDto>) res.getResultado("TipoPlanilla")) { //Le paso la lista
            }).build();
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo el usuario").build();
        }
    }

    @POST
    @Path("/tipoplanilla")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response guardarTipoPlanilla(TipoPlanillaDto tipoPlanillaDto) {
        try {
            Respuesta res = tipoPlanillaService.guardarTipoPlanilla(tipoPlanillaDto);
            if (!res.getEstado()) {
                return Response.status(res.getCodigoRespuesta().getValue()).entity(res.getMensaje()).build();
            }
            return Response.ok((TipoPlanillaDto) res.getResultado("TipoPlanilla")).build();

        } catch (Exception ex) {
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error guardando el tipo planilla").build();
        }
    }

    @DELETE
    @Path("/tipoplanilla/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response eliminarTipoPlanilla(@PathParam("id") Long id) {
        try {
            Respuesta res = tipoPlanillaService.eliminarTipoPlanilla(id);
            if (!res.getEstado()) {
                return Response.status(res.getCodigoRespuesta().getValue()).entity(res.getMensaje()).build();
            }
            return Response.ok((TipoPlanillaDto) res.getResultado("TipoPlanilla")).build();

        } catch (Exception ex) {
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error eliminando el tipo planilla").build();
        }
    }

}
