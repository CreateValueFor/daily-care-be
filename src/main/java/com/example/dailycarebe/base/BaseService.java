package com.example.dailycarebe.base;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import com.example.dailycarebe.base.orm.IdSupport;
import com.example.dailycarebe.base.orm.repository.BaseRepository;
import com.example.dailycarebe.exception.NotExistKeyException;
import com.example.dailycarebe.user.model.User;
import com.example.dailycarebe.user.repository.UserRepository;
import com.example.dailycarebe.util.EntityUtils;
import com.example.dailycarebe.util.ObjectUtils;
import com.example.dailycarebe.util.SecurityContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@XRayEnabled
public abstract class BaseService<ENTITY extends IdSupport, REPOSITORY extends BaseRepository<ENTITY>> extends BaseComponent {

  @Autowired
  protected UserRepository userRepository;

  @Autowired
  protected REPOSITORY repository;

  protected ENTITY touchBeforeUpdate(ENTITY entity) {
    return entity;
  }

  private ENTITY getEntityForUpdate(long id) {
    ENTITY currentEntity = repository.findById(id)
      .orElseThrow(() -> new NotExistKeyException("id = " + id));
    return touchBeforeUpdate(currentEntity);
  }

  @Transactional
  public ENTITY saveDirectly(ENTITY entityToSave) {
    return repository.save(entityToSave);
  }

  @Transactional
  public ENTITY save(ENTITY entityToSave) {
    ENTITY oldEntity = null;
    Long requestedId = entityToSave.getId();

    if (Objects.nonNull(requestedId)) {
      ENTITY currentEntity = getEntityForUpdate(requestedId);
      oldEntity = ObjectUtils.deepClone(currentEntity);
      entityToSave = EntityUtils.mergeObject(entityToSave, currentEntity);
      onBeforeUpdate(oldEntity, entityToSave);
    } else {
      onBeforeInsert(entityToSave);
    }

    ENTITY storedEntity = repository.save(entityToSave);

    if (Objects.nonNull(requestedId)) {
      onAfterUpdate(oldEntity, storedEntity);
    } else {
      onAfterInsert(storedEntity);
    }

    return storedEntity;
  }

  @Transactional
  public ENTITY delete(long id) {
    ENTITY currentEntity = repository.findById(id)
      .orElseThrow(() -> new NotExistKeyException("id = " + id));

    return delete(currentEntity);
  }

  @Transactional
  public ENTITY delete(ENTITY entity) {
    onBeforeDelete(entity);
    repository.delete(entity);
    onAfterDelete(entity);
    return entity;
  }

  @Transactional(readOnly = true)
  public User getContextUser() {
    long userId = SecurityContextUtil.getUserId();

    if (userId == -1) {
      return null;
    }

    return userRepository.getById(userId);
  }

  protected void onBeforeUpdate(ENTITY oldEntity, ENTITY entityToSave) {
  }

  protected void onBeforeInsert(ENTITY entityToSave) {
  }

  protected void onAfterInsert(ENTITY savedEntity) {
  }

  protected void onAfterUpdate(ENTITY oldEntity, ENTITY savedEntity) {
  }

  protected void onBeforeDelete(ENTITY entityToDelete) {
  }

  protected void onAfterDelete(ENTITY deletedEntity) {
  }

//  @Transactional(readOnly = true)
//  protected ENTITY getOneById(long entityId) {
//    return repository.findById(entityId)
//      .orElseThrow(() -> new NotExistKeyException("id = " + entityId));
//  }
}
