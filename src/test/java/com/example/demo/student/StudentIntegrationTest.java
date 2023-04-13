package com.example.demo.student;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentIntegrationTest {


    @Autowired
    public TestRestTemplate restTemplate;

    public static final String url = "/get";


    WireMockServer wm;

    @BeforeEach
    void setUp() {
        wm = new WireMockServer(options().port(8002));
        wm.start();
    }


    @Test
    public void testGetStudentByRest(){
        //arrange
        wm.stubFor(get(urlEqualTo("/otherServiceStudent"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "    \"email\": \"jane@gmail.com\", \n" +
                                "    \"gender\": \"2\", \n" +
                                "    \"name\": \"Jane\"\n" +
                                "  }")));

        //act
        ResponseEntity<Student> actual = restTemplate.getForEntity("/get", Student.class);
        //assert
        assertThat(actual.getBody().getName()).isEqualTo("Jane");

    }

}
