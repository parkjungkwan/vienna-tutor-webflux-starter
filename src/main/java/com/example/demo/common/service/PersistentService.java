package com.example.demo.common.service;
import java.time.LocalDateTime;

import com.example.demo.user.domain.Username;
public interface PersistentService {

    String getId();

    void setId(String id);

    Username getCreatedBy();

    void setCreatedBy(Username username);

    Username getLastModifiedBy();

    void setLastModifiedBy(Username username);

    LocalDateTime getCreatedDate();

    void setCreatedDate(LocalDateTime createdDate);

    LocalDateTime getLastModifiedDate();

    void setLastModifiedDate(LocalDateTime lastModifiedDate);

    
}
