package com.example.demo.post.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.demo.common.service.PersistentService;
import com.example.demo.user.domain.Username;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
@Document("posts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostModel implements PersistentService, Serializable {
    @Id
    private String id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @Builder.Default
    private Status status = Status.DRAFT;

    @CreatedDate
    private LocalDateTime createdDate;

    @CreatedBy
    private Username createdBy;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @LastModifiedBy
    private Username lastModifiedBy;

    public enum Status {

        DRAFT, PUBLISHED

    }
}
