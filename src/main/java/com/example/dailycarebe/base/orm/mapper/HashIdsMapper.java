package com.example.dailycarebe.base.orm.mapper;

import com.example.dailycarebe.util.HashidsUtil;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
  unmappedTargetPolicy = ReportingPolicy.IGNORE,
  componentModel = "spring", uses = {})
public interface HashIdsMapper {
  default Long uuidToId(String uuid) {
    if (uuid == null) {
      return null;
    }
    return HashidsUtil.decodeNumber(uuid);
  }

  default String idToUuid(Long id) {
    if (id == null) {
      return null;
    }
    return HashidsUtil.encodeNumber(id);
  }

  List<String> idsToUuids(List<Long> ids);
}
