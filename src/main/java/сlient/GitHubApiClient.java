package сlient;


import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import entity.Branch;
import entity.GithubRepository;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(configKey = "github-api")
@ClientHeaderParam(name = "Accept", value = "application/vnd.github.v3+json")
@ClientHeaderParam(name = "Authorization", value = "${github.token}")
@Path("/users")
public interface GitHubApiClient {

    @GET
    @Path("/{username}/repos")
    Uni<List<GithubRepository>> getRepositories(@PathParam("username") String username);

    @GET
    @Path("/../repos/{username}/{repo}/branches")
    Uni<List<Branch>> getBranches(@PathParam("username") String username, @PathParam("repo") String repo);
}
