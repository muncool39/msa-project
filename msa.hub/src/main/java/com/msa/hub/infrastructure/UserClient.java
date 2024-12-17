package com.msa.hub.infrastructure;


import com.msa.hub.application.service.UserService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface UserClient extends UserService {
    @DeleteMapping("/users/belong-hub")
    Boolean detachBelongHub(@RequestParam Long userId);

}
