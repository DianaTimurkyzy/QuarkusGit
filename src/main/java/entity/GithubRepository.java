package entity;

public record GithubRepository(String name, Owner owner, boolean fork) {
}
