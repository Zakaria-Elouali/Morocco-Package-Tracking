package com.morocco.mpt.domain;

import jakarta.persistence.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static jakarta.persistence.EnumType.STRING;

@MappedSuperclass
public class BaseEntity {

    @Transient
    private String applicationTimeZone = "Europe/Berlin";
    @Column(name = "status_code", nullable = true, updatable = true)
    @Enumerated(STRING)
    private StatusCodes statusCode;


    @Column(name = "created_at", nullable = true, updatable = false)
    private ZonedDateTime createdAt;

    @Column(name = "created_by", nullable = true, updatable = false)
    private String createdBy;

    @Column(name = "last_modified_at")
    private ZonedDateTime lastModifiedAt;

    @Column(name = "last_modified_by", nullable = true, updatable = true)
    private String lastModifiedBy;

    // getters and setters
    public StatusCodes getStatusCode () {
        return statusCode == null ? StatusCodes.ACTIVE : statusCode;
    }

    public void setStatusCode (StatusCodes statusCode) {
        this.statusCode = statusCode;
    }

    public ZonedDateTime getCreatedAt () {
        return createdAt;
    }

    public void setCreatedAt (ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy () {
        return createdBy;
    }

    public void setCreatedBy (String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getLastModifiedAt () {
        return lastModifiedAt;
    }

    public void setLastModifiedAt (ZonedDateTime lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public String getLastModifiedBy () {
        return lastModifiedBy;
    }

    public void setLastModifiedBy (String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @PrePersist
    public void prePersist () {
        this.createdAt = ZonedDateTime.now(ZoneId.of(applicationTimeZone));
        this.lastModifiedAt = ZonedDateTime.now(ZoneId.of(applicationTimeZone));
        this.statusCode = StatusCodes.ACTIVE;
    }

    @PreUpdate
    public void preUpdate () {
        this.lastModifiedAt = ZonedDateTime.now(ZoneId.of(applicationTimeZone));
        if (this.statusCode == null) {
            this.statusCode = StatusCodes.ACTIVE;
        }
    }

    public enum StatusCodes {ACTIVE, INACTIVE}
}

