package com.example.dailycarebe.auth.authorization.model;

import com.example.dailycarebe.base.orm.model.AbstractAuditingEntity;
import com.example.dailycarebe.base.orm.model.converter.JpaStringSetConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(indexes = {
  @Index(name = "uq1", unique = true, columnList = "roleId")
})
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@EqualsAndHashCode(callSuper = false, of = {"roleId"})
@ToString(callSuper = true, exclude = {"permissions"})
public class AppRole extends AbstractAuditingEntity {
    @NotNull
    @Column(length = 40)
    private String roleId;

    @Column()
    private String roleName;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = JpaStringSetConverter.class)
    private Set<String> permissions = new HashSet<>();

    public static AppRole of(long id) {
        AppRole appRole = new AppRole();
        appRole.setId(id);
        return appRole;
    }
}
