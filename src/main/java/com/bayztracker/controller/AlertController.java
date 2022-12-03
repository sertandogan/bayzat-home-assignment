package com.bayztracker.controller;

import com.bayztracker.model.request.CreateAlertRequest;
import com.bayztracker.model.request.PutAlertRequest;
import com.bayztracker.model.response.CreateAlertResponse;
import com.bayztracker.model.response.GetAlertResponse;
import com.bayztracker.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/v1/users/{userId}")
@RequiredArgsConstructor
public class AlertController {
    private final AlertService alertService;

    @GetMapping("/alerts/{alertId}")
    public GetAlertResponse get(@PathVariable @Valid @Min(value = 1, message = "api.validation.constraints.userId.message") Long userId,
                                @PathVariable @Valid @Min(value = 1, message = "api.validation.constraints.alertId.message") Long alertId) {
        return alertService.getById(userId, alertId);
    }

    @GetMapping("/alerts")
    public List<GetAlertResponse> getAll(@PathVariable @Valid @Min(value = 1, message = "api.validation.constraints.userId.message") Long userId) {
        return alertService.getAllByUserId(userId);
    }

    @PostMapping("/alerts")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateAlertResponse save(@PathVariable @Valid @Min(value = 1, message = "api.validation.constraints.userId.message") Long userId,
                                    @RequestBody @Valid CreateAlertRequest createUserRequest) {
        createUserRequest.setUserId(userId);
        return alertService.save(createUserRequest);
    }

    @PutMapping("/alerts/{alertId}")
    public void put(@RequestBody @Valid PutAlertRequest putAlertRequest, @PathVariable Long userId, @PathVariable Long alertId) {
        putAlertRequest.setUserId(userId);
        alertService.update(putAlertRequest, alertId);
    }

    @DeleteMapping("/alerts/{alertId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long userId, @PathVariable Long alertId) {
        alertService.delete(userId, alertId);
    }
}
