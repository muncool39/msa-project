package com.msa.hub.application.service;


import org.springframework.web.bind.annotation.RequestParam;

public interface UserService {
    Boolean detachBelongHub(@RequestParam Long userId);
}
