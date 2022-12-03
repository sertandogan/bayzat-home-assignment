package com.bayztracker.controller;


import com.bayztracker.base.TestBase;
import com.bayztracker.configuration.I18nConfiguration;
import com.bayztracker.enums.ErrorType;
import com.bayztracker.exceptions.AlertNotFoundException;
import com.bayztracker.model.request.CreateAlertRequest;
import com.bayztracker.model.response.CreateAlertResponse;
import com.bayztracker.model.response.GetAlertResponse;
import com.bayztracker.service.AlertService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Locale;
import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.ACCEPT_LANGUAGE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = {AlertController.class, I18nConfiguration.class})
public class AlertControllerTests extends TestBase {

    private static final String ALERT_GET_ENDPOINT = "/v1/users/{userId}/alerts/{alertId}";
    private static final String ALERT_POST_ENDPOINT = "/v1/users/{userId}/alerts";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MessageSource messageSource;

    @MockBean
    private AlertService alertService;

    @Test
    void it_should_return_ok_when_getting_alert_succeed() throws Exception {
        var alertId = 1L;
        var userId = 1L;
        var getAlertResponse = GetAlertResponse.builder().id(alertId).build();

        when(alertService.getById(userId, alertId)).thenReturn(getAlertResponse);

        String responseJson = mapper.writeValueAsString(getAlertResponse);
        mockMvc.perform(get(ALERT_GET_ENDPOINT.replace("{userId}", String.valueOf(userId))
                        .replace("{alertId}", String.valueOf(alertId))))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }

    @Test
    void it_should_return_not_found_when_alert_not_found() throws Exception {
        var alertId = 1L;
        var userId = 1L;

        var message = messageSource.getMessage(ErrorType.ALERT_NOT_FOUND_EXCEPTION.getErrorMessageKey(), null, Locale.ENGLISH);
        when(alertService.getById(userId, alertId)).thenThrow(AlertNotFoundException.class);

        mockMvc.perform(get(ALERT_GET_ENDPOINT.replace("{userId}", String.valueOf(userId))
                        .replace("{alertId}", String.valueOf(alertId))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$[0].message").value(message));
    }

    @Test
    void it_should_return_created_when_alert_creation_succeed() throws Exception {
        var createAlertRequest =  dataGenerator.nextObject(CreateAlertRequest.class);
        var payload = mapper.writeValueAsString(createAlertRequest);
        var userId = new Random().nextLong();
        var alertId = new Random().nextLong();

        CreateAlertResponse createAlertResponse = new CreateAlertResponse(alertId);

        when(alertService.save(any(CreateAlertRequest.class))).thenReturn(createAlertResponse);

        mockMvc.perform(post(ALERT_POST_ENDPOINT.replace("{userId}", String.valueOf(userId)))
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(createAlertResponse)));
    }

    //TODO: More test cases...


}
