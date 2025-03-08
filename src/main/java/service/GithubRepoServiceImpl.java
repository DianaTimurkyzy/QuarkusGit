package service;

import controller.res.ErrorResponse;
import controller.res.RepositoryResponse;
import entity.GithubRepository;
import exception.ExceptionHandler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import сlient.GitHubApiClient;

import java.util.List;

/**
 * Implementation of the repository service, responsible for fetching and processing
 * GitHub repository data asynchronously.
 */
@ApplicationScoped
public class GithubRepoServiceImpl implements GithubRepoService {

    private final GitHubApiClient gitHubApiClient;
    private final ExceptionHandler exceptionHandler;

    @Inject
    public GithubRepoServiceImpl(@RestClient GitHubApiClient gitHubApiClient, ExceptionHandler exceptionHandler) {
        this.gitHubApiClient = gitHubApiClient;
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public Uni<Response> getRepositories(String username) {
        return gitHubApiClient.getRepositories(username)
                .map(this::filterAndMapToBranchResponses)
                .flatMap(this::mergeBranchResponses)
                .map(response -> Response.ok(response).build())
                .onFailure()
                .recoverWithItem(exceptionHandler::handleApiFailure);
    }


    private List<Uni<RepositoryResponse>> filterAndMapToBranchResponses(List<GithubRepository> repositories) {
        return repositories.stream()
                .filter(repo -> !repo.fork())
                .map(this::createResponseFromBranches)
                .toList();
    }


    private Uni<RepositoryResponse> createResponseFromBranches(GithubRepository repo) {
        return gitHubApiClient.getBranches(repo.owner().login(), repo.name())
                .map(branches -> new RepositoryResponse(repo.name(), repo.owner().login(), branches));
    }

    private Uni<List<RepositoryResponse>> mergeBranchResponses(List<Uni<RepositoryResponse>> responseUnis) {
        return Uni.combine().all().unis(responseUnis)
                .combinedWith(list -> (List<RepositoryResponse>) list);
    }
}