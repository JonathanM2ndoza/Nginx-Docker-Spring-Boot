package com.jmendoza.wallet.configuration.controller.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmendoza.wallet.application.rest.request.authentication.SignInRequest;
import com.jmendoza.wallet.application.rest.request.authentication.SignUpRequest;
import com.jmendoza.wallet.configuration.ConfigurationApplication;
import com.jmendoza.wallet.domain.model.authorization.Role;
import com.jmendoza.wallet.domain.model.authorization.Roles;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;
import java.util.Set;

import static com.jmendoza.wallet.configuration.controller.constants.AuthenticationFieldsTest.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ConfigurationApplication.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthenticationControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)).build();
    }

    @Test
    @Order(1)
    public void signUp() throws Exception {

        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setFirstName("Jonathan");
        signUpRequest.setLastName("Mendoza");
        signUpRequest.setEmail("jmendoza@gmail.com");
        signUpRequest.setPassword("123456789");

        Set<Role> roles = new HashSet<>();
        Role roleAdmin = new Role();
        roleAdmin.setRoleId("1");
        roleAdmin.setRoleName(Roles.ROLE_ADMIN);
        roles.add(roleAdmin);

        Role roleUser = new Role();
        roleUser.setRoleId("2");
        roleUser.setRoleName(Roles.ROLE_USER);
        roles.add(roleUser);

        signUpRequest.setRoles(roles);

        ResultActions result = this.mockMvc
                .perform(RestDocumentationRequestBuilders.post("/api/v1/auth/signup").contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("auth/signup", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),

                        requestFields(
                                fieldWithPath(FIRST_NAME.getField()).description(FIRST_NAME.getDescription()),
                                fieldWithPath(LAST_NAME.getField()).description(LAST_NAME.getDescription()),
                                fieldWithPath(EMAIL.getField()).description(EMAIL.getDescription()),
                                fieldWithPath(PASSWORD.getField()).description(PASSWORD.getDescription()),
                                subsectionWithPath(ROLES.getField()).attributes().description(ROLES.getDescription())),

                        requestFields(
                                beneathPath(ROLES.getField()).withSubsectionId(ROLES.getField()),
                                fieldWithPath(ROLE_ID.getField()).description(ROLE_ID.getDescription()),
                                fieldWithPath(ROLE_NAME.getField()).description(ROLE_NAME.getDescription())),

                        responseFields(
                                fieldWithPath(CUSTOMER_ID.getField()).description(CUSTOMER_ID.getDescription()))
                ));

        Assertions.assertNotNull(result.andReturn().getResponse().getContentAsString());
    }

    @Test
    @Order(2)
    public void signIn() throws Exception {

        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmail("jmendoza@gmail.com");
        signInRequest.setPassword("123456789");

        ResultActions result = this.mockMvc
                .perform(RestDocumentationRequestBuilders.post("/api/v1/auth/signin").contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(signInRequest)))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("auth/signin", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),

                        requestFields(fieldWithPath(EMAIL.getField()).description(EMAIL.getDescription()),
                                fieldWithPath(PASSWORD.getField()).description(PASSWORD.getDescription())),

                        responseFields(
                                fieldWithPath(TOKEN.getField()).description(TOKEN.getDescription()),
                                fieldWithPath(TOKEN_EXPIRATION.getField()).description(TOKEN_EXPIRATION.getDescription()),
                                fieldWithPath(CUSTOMER_ID.getField()).description(CUSTOMER_ID.getDescription()),
                                fieldWithPath(FIRST_NAME.getField()).description(FIRST_NAME.getDescription()),
                                fieldWithPath(LAST_NAME.getField()).description(LAST_NAME.getDescription()),
                                fieldWithPath(EMAIL.getField()).description(EMAIL.getDescription()),
                                subsectionWithPath(ROLES.getField()).attributes().description(ROLES.getDescription()),
                                fieldWithPath(CREATED_AT.getField()).description(CREATED_AT.getDescription()),
                                fieldWithPath(UPDATED_AT.getField()).description(UPDATED_AT.getDescription())),

                        responseFields(
                                beneathPath(ROLES.getField()).withSubsectionId(ROLES.getField()),
                                fieldWithPath(ROLE_ID.getField()).description(ROLE_ID.getDescription()),
                                fieldWithPath(ROLE_NAME.getField()).description(ROLE_NAME.getDescription()))

                ));
        Assertions.assertNotNull(result.andReturn().getResponse().getContentAsString());
    }
}
