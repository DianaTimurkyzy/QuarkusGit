import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
public class GithubRepoTest {

    @Test
    public void shouldFetchReposAndBranchesForValidUser(){
        // given
        String username = "some-random-github-user";

        // when
        var response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/repos/{username}", username);

        // then
        response.then()
                .contentType(ContentType.JSON)
                .body("size()", greaterThan(0))
                .body("[0].name", notNullValue())
                .body("[0].ownerLogin", equalTo(username))
                .body("[0].branches.size()", greaterThan(0))
                .body("[0].branches[0].name", notNullValue())
                .body("[0].branches[0].commit.sha", notNullValue());
    }
}

