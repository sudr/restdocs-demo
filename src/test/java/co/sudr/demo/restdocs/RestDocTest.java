package co.sudr.demo.restdocs;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

import javax.servlet.http.HttpServletResponse;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.restassured3.RestDocumentationFilter;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RestDocTest {

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @Test
    public void test() {
        RestDocumentationFilter documentationFilter = document("restdoc-test", preprocessResponse(prettyPrint()));
        
        RequestSpecification spec = new RequestSpecBuilder()
                                            .addFilter(documentationConfiguration(restDocumentation))
                                            .addFilter(documentationFilter)
                                            .build();
                        
        RestAssured.given(spec) 
        //.accept("application/json") 
                    .when()
                        .get("https://www.google.com")
                        .then()
                            .assertThat()
                                .statusCode(equalTo(Integer.valueOf(HttpServletResponse.SC_OK)));
    }
}
