package cr.ac.una.unaplanillaws;

import cr.ac.una.unaplanillaws.controller.JsonbContextResolver;
import cr.ac.una.unaplanillaws.controller.SecurityFilter;
import jakarta.ws.rs.ApplicationPath;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("ws")// TODO
public class JakartaRestConfiguration extends ResourceConfig {
    
    private void registerCustomProviders() {
        Set<String> providerClassNames = new HashSet() {
            {
                add(JsonbContextResolver.class.getName());
//                add(ConstraintViolationExceptionMapper.class.getName());
                add(SecurityFilter.class.getName());
            }
        };
     
        Map<String, Object> properties = new HashMap() {
            {
                put("jersey.config.server.provider.classnames", providerClassNames.stream().collect(Collectors.joining(",")));
            }
        };
     
        addProperties(Collections.unmodifiableMap(properties));
    }
    
    public JakartaRestConfiguration() {
        super();
        packages("cr.ac.una.unaplanillaws.controller");
        packages("cr.ac.una.unaplanillaws.controller", "io.swagger.v3.jaxrs2.integration.resources");
        registerCustomProviders();
    }
}
