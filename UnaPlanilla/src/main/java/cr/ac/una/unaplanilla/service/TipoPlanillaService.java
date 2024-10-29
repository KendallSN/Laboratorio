package cr.ac.una.unaplanilla.service;

import cr.ac.una.unaplanilla.model.TipoPlanillaDto;
import cr.ac.una.unaplanilla.util.Request;
import cr.ac.una.unaplanilla.util.Respuesta;
import jakarta.ws.rs.core.GenericType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cbcar
 */
public class TipoPlanillaService {

    public Respuesta getTipoPlanilla(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("TipoPlanillaController/tipoplanilla", "/{id}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            TipoPlanillaDto tipoPlanillaDto = (TipoPlanillaDto) request.readEntity(TipoPlanillaDto.class);
            return new Respuesta(true, "", "", "TipoPlanilla", tipoPlanillaDto);
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Error obteniendo el tipo de planilla [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el tipo de planilla.", "getTipoPlanilla " + ex.getMessage());
        }
    }

    public Respuesta getTipoPlanillas(String codigo, String descripcion, String plaxmes, String idemp, String cedula) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("codigo", codigo);
            parametros.put("descripcion", descripcion);
            parametros.put("plaxmes", plaxmes);
            parametros.put("idemp", idemp);
            parametros.put("cedula", cedula);
            
            Request request = new Request("TipoPlanillaController/tipoplanilla", "/{codigo}/{descripcion}/{plaxmes}/{idemp}/{cedula}", parametros);
            request.get();
             if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            List<TipoPlanillaDto> tipoPlanillasDto = (List<TipoPlanillaDto>) request.readEntity(new GenericType<List<TipoPlanillaDto>>() {
            });
            return new Respuesta(true, "", "", "TipoPlanillas", tipoPlanillasDto);
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Error obteniendo tipos de planillas.", ex);
            return new Respuesta(false, "Error obteniendo tipos de planillas.", "getTipoPlanillas " + ex.getMessage());
        }
    }

    public Respuesta guardarTipoPlanilla(TipoPlanillaDto tipoPlanillaDto) {
        try {
            Request request = new Request("TipoPlanillaController/tipoplanilla");
            request.post(tipoPlanillaDto);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            TipoPlanillaDto tipoPlanilla = (TipoPlanillaDto) request.readEntity(TipoPlanillaDto.class);
            return new Respuesta(true, "", "", "TipoPlanilla", tipoPlanilla);
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Ocurrio un error al guardar el tipo de planilla.", ex);
            return new Respuesta(false, "Ocurrio un error al guardar el tipo de planilla.", "guardarTipoPlanilla " + ex.getMessage());
        }
    }

    public Respuesta eliminarTipoPlanilla(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("TipoPlanillaController/tipoplanilla", "/{id}", parametros);
            request.delete();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE, "Ocurrio un error al eliminar el tipo de planilla.", ex);
            return new Respuesta(false, "Ocurrio un error al eliminar el tipo de planilla.", "eliminarTipoPlanilla " + ex.getMessage());
        }
    }
}
