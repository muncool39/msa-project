package com.msa.order.infrastructure;

import org.springframework.stereotype.Component;

import com.msa.order.application.client.dto.UserData;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "User-Client")
@Component("userClientFallback")
public class UserClientFallback implements UserClient {

  @Override
  public UserData getUserInfo(Long userId) {
    return null;
  }
}
