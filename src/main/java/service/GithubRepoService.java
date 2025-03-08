package service;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;

public interface GithubRepoService {

    Uni<Response> getRepositories(String username);

}
