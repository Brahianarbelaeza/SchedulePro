    package co.edu.uniquindio.ShedulePro.Config;

    import co.edu.uniquindio.ShedulePro.dto.jws.MensajeDTO;
    import co.edu.uniquindio.ShedulePro.model.enums.Cargo;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import io.jsonwebtoken.Claims;
    import io.jsonwebtoken.ExpiredJwtException;
    import io.jsonwebtoken.Jws;
    import io.jsonwebtoken.MalformedJwtException;
    import io.jsonwebtoken.security.SignatureException;
    import jakarta.servlet.FilterChain;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Component;
    import org.springframework.web.filter.OncePerRequestFilter;
    import java.io.IOException;



    @Component
    @RequiredArgsConstructor
    public class FiltroToken extends OncePerRequestFilter {

        private final JWTUtils jwtUtils;
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


            // Configuración de cabeceras para CORS
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, Content-Type, Authorization");

            if (request.getMethod().equals("OPTIONS")) {
                response.setStatus(HttpServletResponse.SC_OK);
            }else {

                //Obtener la URI de la petición que se está realizando
                String requestURI = request.getRequestURI();
                //Se obtiene el token de la petición del encabezado del mensaje HTTP
                String token = getToken(request);
                boolean error = true;

                try {
                    //Si la petición es para la ruta /api/cliente se verifica que el token exista y que el rol sea CLIENTE
                    if (requestURI.startsWith("/api/usuario/empleado")) {
                        error = validarToken(token, Cargo.EMPLEADO);
                    } else if (requestURI.startsWith("/api/usuario/administrador")) {
                        error = validarToken(token, Cargo.ADMINISTRADOR);

                    } else {
                        error = false;
                    }

                    //Agregar la validación para las peticiones que sean de los administradores

                    //Si hay un error se crea una respuesta con el mensaje del error
                    if(error){
                        crearRespuestaError("No tiene permisos para acceder a este recurso", HttpServletResponse.SC_FORBIDDEN, response);
                    }


                } catch (MalformedJwtException | SignatureException e) {
                    crearRespuestaError("El token es incorrecto", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response);
                } catch (ExpiredJwtException e) {
                    crearRespuestaError("El token está vencido", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response);
                } catch (Exception e) {
                    crearRespuestaError(e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response);
                }


                //Si no hay errores se continúa con la petición
                if (!error) {
                    filterChain.doFilter(request, response);
                }
            }


        }
        private String getToken(HttpServletRequest req) {
            String header = req.getHeader("Authorization");
            return header != null && header.startsWith("Bearer ") ? header.replace("Bearer ", "") : null;
        }


        private void crearRespuestaError(String mensaje, int codigoError, HttpServletResponse response) throws IOException {

            MensajeDTO<String> dto = new MensajeDTO<>(true, mensaje);

            response.setContentType("application/json");
            response.setStatus(codigoError);
            response.getWriter().write(new ObjectMapper().writeValueAsString(dto));
            response.getWriter().flush();
            response.getWriter().close();
        }


        private boolean validarToken(String token, Cargo cargo){
            boolean error = true;
            System.out.println( token);
            if (token != null) {
                Jws<Claims> jws = jwtUtils.parseJwt(token);
                System.out.println(jws);
                if (Cargo.valueOf(jws.getPayload().get("rol").toString()) == cargo) {
                    error = false;
                }
            }
            return error;
        }
    }
