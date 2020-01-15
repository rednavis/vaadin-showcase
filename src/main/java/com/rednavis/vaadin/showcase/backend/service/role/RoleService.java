package com.rednavis.vaadin.showcase.backend.service.role;

import com.rednavis.vaadin.showcase.backend.dto.RoleDto;
import java.util.Set;

/**
 * RoleService.
 */
public interface RoleService {

  Set<RoleDto> getUserRoles(long userId);
}
