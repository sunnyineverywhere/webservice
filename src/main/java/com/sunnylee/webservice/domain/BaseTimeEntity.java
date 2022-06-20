package com.sunnylee.webservice.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@MappedSuperclass // basetimeentity를 상속할 경우 필드들도 컬럼으로 인식하도록 함
@EntityListeners(AuditingEntityListener.class) // auditing 기능 포함
public class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

}
