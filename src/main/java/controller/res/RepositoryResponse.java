package controller.res;

import entity.Branch;

import java.util.List;

public record RepositoryResponse(String name, String ownerLogin, List<Branch> branches) {
}
