package com.example.dailycarebe.base.orm.repository;

import com.example.dailycarebe.exception.InvalidKeyException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Collection;
import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, Long>/*, QuerydslPredicateExecutor<T> */ {
    @Modifying
    @Query("DELETE FROM #{#entityName} e WHERE e.id in :ids")
    int deleteByIds(Collection<Long> ids);

    default List<T> findAllByIdsAndThrowIfNotExist(Collection<Long> ids) {
        List<T> entities = findAllById(ids);

        if (entities.size() != ids.size()) {
            throw new InvalidKeyException("존재하지 않은 값으로 요청." + ids);
        }
        return entities;
    }
}
