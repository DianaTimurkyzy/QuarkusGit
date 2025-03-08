package exception;

import controller.res.ErrorResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class ExceptionHandler {

        public Response handleApiFailure(Throwable throwable) {
        var msg = throwable.getMessage().toLowerCase();
        if (msg.contains("404") || msg.contains("not found")) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(404, "GitHub user not found"))
                    .build();
        }
        return Response.serverError()
                .entity(new ErrorResponse(500, "Internal server error: " + throwable.getMessage()))
                .build();
    }

}
