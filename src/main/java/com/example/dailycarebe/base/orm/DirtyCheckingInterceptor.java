package com.example.dailycarebe.base.orm;

import com.example.dailycarebe.base.orm.model.AbstractEntity;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;

public class DirtyCheckingInterceptor extends EmptyInterceptor {

    @Override
    public int[] findDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        if (entity instanceof AbstractEntity) {
            for (int i = 0; i < propertyNames.length; i++) {
                String propertyName = propertyNames[i];
                if (!ObjectUtils.nullSafeEquals(previousState[i], currentState[i])) {
                    ((AbstractEntity<?>) entity).addDirtyField(propertyName);
                }
            }
        }
        return super.findDirty(entity, id, currentState, previousState, propertyNames, types);
    }
}