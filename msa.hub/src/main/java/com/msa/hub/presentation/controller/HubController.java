package com.msa.hub.presentation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hubs")
public class HubController {

    @GetMapping("/verify")
    public Boolean verifyHub(@RequestParam(value = "hub_id") String hubId) {
        return true;
    }
}
