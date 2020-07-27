package com.jmendoza.wallet.configuration.controller.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmendoza.wallet.application.rest.request.authentication.SignInRequest;
import com.jmendoza.wallet.configuration.ConfigurationApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import static com.jmendoza.wallet.configuration.controller.constants.AuthenticationFieldsTest.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ConfigurationApplication.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class SignInTest {
    @Autowired
    private ObjectMapper objectMapper;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)).build();
    }

    @Test
    public void signIn() throws Exception {

        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmail("jmendoza1@gmail.com");
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
