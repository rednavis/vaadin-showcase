package com.rednavis.vaadin.showcase.backend.service.user;

import static com.rednavis.vaadin.showcase.backend.db.DbConstantUtils.ID;
import static com.rednavis.vaadin.showcase.backend.db.DbConstantUtils.ROLE_PREFIX_ID;

import com.rednavis.vaadin.showcase.backend.entity.RoleEntity;
import com.rednavis.vaadin.showcase.backend.entity.UserEntity;
import java.util.HashSet;
import java.util.Map;
import org.jdbi.v3.core.result.LinkedHashMapRowReducer;
import org.jdbi.v3.core.result.RowView;

/**
 * Row reducer for {@link UserEntitySqlObject}. It is used to aggregate nested entities ({@link RoleEntity}) into collection per {@link
 * UserEntity}.
 */
public class UserReducer implements LinkedHashMapRowReducer<Long, UserEntity> {

  /**
   * The method provides implementation of reduction. It is called for every row in the resultSet and complete roles for UserEntity.
   *
   * @param map: key - primary key of UserEntity; value - UserEntity
   * @param rowView: current row in the resultSet
   */
  @Override
  public void accumulate(Map<Long, UserEntity> map, RowView rowView) {
    UserEntity userEntity = map.computeIfAbsent(rowView.getColumn(ID, Long.class),
        id -> rowView.getRow(UserEntity.class));

    if (rowView.getColumn(ROLE_PREFIX_ID, Integer.class) != null) {

      if (userEntity.getRoleSet() == null) {
        userEntity.setRoleSet(new HashSet<>());
      }

      userEntity.getRoleSet().add(rowView.getRow(RoleEntity.class));
    }
  }
}
