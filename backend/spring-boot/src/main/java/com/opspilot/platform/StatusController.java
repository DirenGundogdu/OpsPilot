package com.opspilot.platform;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/status")
public class StatusController {

    @GetMapping
    public StatusResponse getStatus(){
        return new StatusResponse("OpsPilot","running");
    } 

    public record StatusResponse(String application, String status) {
    }
}
