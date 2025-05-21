package org.example.tltravel.entities;

import jakarta.persistence.*;

import java.util.Objects;

@MappedSuperclass
public class BaseEntity extends AuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID",nullable = false)
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                ", isActive=" + isActive +
                ", createdBy='" + createdBy + '\'' +
                ", createdOn=" + createdOn +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedOn=" + updatedOn +
                '}';
    }
}
