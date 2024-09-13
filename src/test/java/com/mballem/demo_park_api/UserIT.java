package com.mballem.demo_park_api;

import com.mballem.demo_park_api.web.dto.UserCreaterDTO;
import com.mballem.demo_park_api.web.dto.UserResponseDTO;
import com.mballem.demo_park_api.web.exception.ErrorMsg;
import org.hibernate.annotations.processing.SQL;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
@Sql(scripts = "/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql.User/user-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql.User/user-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createUser_WithValidData_ReturnUserStatus201(){
       UserResponseDTO responseBody =  testClient
                .post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreaterDTO("tody@email.com","123456"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserResponseDTO.class)
                .returnResult().getResponseBody();
       org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
       org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
       org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("tody@email.com");
       org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENT");
       //SENHA NAO VOLTA NO RESPONSE
    }

    @Test
    public void createUser_WithInvalidData_ReturnError422(){
        ErrorMsg responseBody =  testClient
                .post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreaterDTO("","123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMsg.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody =  testClient
                .post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreaterDTO("tody@email.","123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMsg.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody =  testClient
                .post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreaterDTO("tody@","123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMsg.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

    }
}
