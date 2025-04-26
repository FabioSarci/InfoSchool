package com.infoschool.infoschool;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.infoschool.infoschool.model.Role;
import com.infoschool.infoschool.repository.RoleRepository;
import com.infoschool.infoschool.service.RoleService;
import com.infoschool.infoschool.util.ERole;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class RoleServiceTest {

    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddRole() {
        Role role = new Role();
        role.setName(ERole.ROLE_USER);

        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.empty());
        when(roleRepository.save(role)).thenReturn(role);

        Role result = roleService.add(role);

        assertNotNull(result);
        assertEquals(ERole.ROLE_USER, result.getName());
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    void testAddRoleAlreadyExists() {
        Role role = new Role();
        role.setName(ERole.ROLE_USER);

        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(role));

        Role result = roleService.add(role);

        assertNull(result);
        verify(roleRepository, never()).save(any(Role.class));
    }

    @Test
    void testGetRoleById() {
        Role role = new Role();
        role.setId(1L);
        role.setName(ERole.ROLE_ADMIN);

        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        Optional<Role> result = roleService.getById(1L);

        assertTrue(result.isPresent());
        assertEquals(ERole.ROLE_ADMIN, result.get().getName());
        verify(roleRepository, times(1)).findById(1L);
    }

    @Test
    void testEditRole() {
        Role role = new Role();
        role.setId(1L);
        role.setName(ERole.ROLE_TEACHER);

        when(roleRepository.existsById(1L)).thenReturn(true);
        when(roleRepository.save(role)).thenReturn(role);

        Role result = roleService.edit(role);

        assertNotNull(result);
        assertEquals(ERole.ROLE_TEACHER, result.getName());
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    void testEditRoleNotFound() {
        Role role = new Role();
        role.setId(1L);
        role.setName(ERole.ROLE_TEACHER);

        when(roleRepository.existsById(1L)).thenReturn(false);

        Role result = roleService.edit(role);

        assertNull(result);
        verify(roleRepository, never()).save(any(Role.class));
    }

    @Test
    void testDeleteRoleById() {
        doNothing().when(roleRepository).deleteById(1L);

        roleService.deleteById(1L);

        verify(roleRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetRoleByName() {
        Role role = new Role();
        role.setName(ERole.ROLE_ADMIN);

        when(roleRepository.findByName(ERole.ROLE_ADMIN)).thenReturn(Optional.of(role));

        Role result = roleService.getByName(ERole.ROLE_ADMIN);

        assertNotNull(result);
        assertEquals(ERole.ROLE_ADMIN, result.getName());
        verify(roleRepository, times(1)).findByName(ERole.ROLE_ADMIN);
    }
}