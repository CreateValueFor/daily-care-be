package com.example.dailycarebe.base.orm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.dailycarebe.base.orm.IdSupport;
import com.example.dailycarebe.util.ObjectUtils;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@MappedSuperclass
@EqualsAndHashCode(of = {"id"})
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public abstract class AbstractEntity<ENTITY> implements IdSupport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    @Setter(AccessLevel.PROTECTED)
    @JsonIgnore
    @Builder.Default
    private boolean isSupportOldObj = false;

    @Transient
    @JsonIgnore
    private ENTITY oldObj;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Transient
    @JsonIgnore
    public ENTITY getOldObj() {
        return oldObj;
    }

    @PostLoad
    private void onLoad() {
        if (isSupportOldObj)
            oldObj = (ENTITY) ObjectUtils.deepClone(this);
    }

    @Transient
    @JsonIgnore
    private Set<String> dirtyFields;

    @Transient
    @JsonIgnore
    public Set<String> getDirtyFields() {
        if (dirtyFields == null) {
            dirtyFields = new HashSet<>();
        }
        return dirtyFields;
    }

    public void addDirtyField(String propertyName) {
        if (dirtyFields == null) {
            dirtyFields = new HashSet<>();
        }
        dirtyFields.add(propertyName);
    }

    @JsonIgnore
    protected void cloneTo(AbstractEntity entity) {
        entity.setId(id);
        entity.setSupportOldObj(isSupportOldObj);
    }

    @JsonIgnore
    public boolean containsDirtyField(String fieldName) {
        return getDirtyFields().contains(fieldName);
    }
}
