package controller;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.GithubRepoService;


@Path("/repos")
@Produces(MediaType.APPLICATION_JSON)
public class GithubRepoController {

    private final GithubRepoService githubRepoService;

    @Inject
    public GithubRepoController(GithubRepoService githubRepoService) {
        this.githubRepoService = githubRepoService;
    }

    @GET
    @Path("/{username}")
    public Uni<Response> getRepositories(@PathParam("username") String username) {
        return githubRepoService.getRepositories(username);
    }

}