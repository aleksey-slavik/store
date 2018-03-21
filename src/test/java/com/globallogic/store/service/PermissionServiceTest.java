package com.globallogic.store.service;

import com.globallogic.store.Workflow;
import com.globallogic.store.dao.GenericDao;
import com.globallogic.store.dao.criteria.SearchCriteria;
import com.globallogic.store.domain.permission.Permission;
import com.globallogic.store.domain.permission.PermissionName;
import com.globallogic.store.domain.product.Product;
import com.globallogic.store.exception.AlreadyExistException;
import com.globallogic.store.exception.NoContentException;
import com.globallogic.store.exception.NotFoundException;
import com.globallogic.store.security.service.PermissionService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PermissionServiceTest {

    @Mock
    private GenericDao<Permission> permissionDao;

    @InjectMocks
    private PermissionService permissionService;

    @Before
    public void before() {
        Workflow.loginUser();
    }

    @After
    public void after() {
        Workflow.logout();
    }

    @Test
    public void existPermissionTest() throws Exception {
        Permission permission = Workflow.createPermission(PermissionName.ADMINISTRATION);
        when(permissionDao.getEntity(any(SearchCriteria.class))).thenReturn(permission);
        assertTrue(permissionService.hasPrivilege(1L, "testClass", "ADMINISTRATION"));
    }

    @Test
    public void notExistPermissionTest() throws Exception {
        when(permissionDao.getEntity(any(SearchCriteria.class))).thenReturn(null);
        assertFalse(permissionService.hasPrivilege(1L, "testClass", "ADMINISTRATION"));
    }

    @Test
    public void findSidListTest() throws Exception {
        List<Permission> permissions = Workflow.createPermissionList();
        when(permissionDao.getEntityList(any(SearchCriteria.class))).thenReturn(permissions);
        List<String> actual = permissionService.getPrincipalList(Workflow.createDummyProduct(1L), PermissionName.UPDATE, Product.class);
        assertEquals(permissions.size(), actual.size());
    }

    @Test
    public void emptySidListTest() throws Exception {
        try {
            when(permissionDao.getEntityList(any(SearchCriteria.class))).thenReturn(null);
            permissionService.getPrincipalList(Workflow.createDummyProduct(1L), PermissionName.UPDATE, Product.class);
            fail("NoContentException don't thrown!");
        } catch (NoContentException e) {
            //expected
        }
    }

    @Test
    public void findIdListTest() throws Exception {
        List<Permission> permissions = Workflow.createPermissionList();
        when(permissionDao.getEntityList(any(SearchCriteria.class))).thenReturn(permissions);
        List<Long> actual = permissionService.getIdList("test", PermissionName.UPDATE, Product.class);
        assertEquals(permissions.size(), actual.size());
    }

    @Test
    public void emptyIdListTest() throws Exception {
        try {
            when(permissionDao.getEntityList(any(SearchCriteria.class))).thenReturn(null);
            permissionService.getIdList("test", PermissionName.UPDATE, Product.class);
            fail("NoContentException don't thrown!");
        } catch (NoContentException e) {
            //expected
        }
    }

    @Test
    public void addPermissionTest() throws Exception {
        Permission permission = Workflow.createPermission(PermissionName.UPDATE);
        when(permissionDao.getEntity(any(SearchCriteria.class))).thenReturn(null);
        when(permissionDao.createEntity(permission)).thenReturn(permission);
        permissionService.addPermission(Workflow.createDummyProduct(1L), PermissionName.UPDATE, Product.class);
    }

    @Test
    public void addPrincipalPermissionTest() throws Exception {
        Permission permission = Workflow.createPermission(PermissionName.UPDATE);
        when(permissionDao.getEntity(any(SearchCriteria.class))).thenReturn(null);
        when(permissionDao.createEntity(permission)).thenReturn(permission);
        permissionService.addPermission(Workflow.createDummyProduct(1L), "test", PermissionName.CREATE, Product.class);
    }

    @Test
    public void addExistedPermissionTest() throws Exception {
        try {
            Permission permission = Workflow.createPermission(PermissionName.READ);
            when(permissionDao.getEntity(any(SearchCriteria.class))).thenReturn(permission);
            permissionService.addPermission(Workflow.createDummyProduct(1L), PermissionName.READ, Product.class);
            fail("AlreadyExistException don't thrown!");
        } catch (AlreadyExistException e) {
            //expected
        }
    }

    @Test
    public void addExistedPrincipalPermissionTest() throws Exception {
        try {
            Permission permission = Workflow.createPermission(PermissionName.CREATE);
            when(permissionDao.getEntity(any(SearchCriteria.class))).thenReturn(permission);
            permissionService.addPermission(Workflow.createDummyProduct(1L), "test", PermissionName.CREATE, Product.class);
            fail("AlreadyExistException don't thrown!");
        } catch (AlreadyExistException e) {
            //expected
        }
    }

    @Test
    public void deletePermissionTest() throws Exception {
        Permission permission = Workflow.createPermission(PermissionName.UPDATE);
        when(permissionDao.getEntity(any(SearchCriteria.class))).thenReturn(permission);
        when(permissionDao.deleteEntity(permission)).thenReturn(permission);
        permissionService.deletePermission(Workflow.createDummyProduct(1L), "test", PermissionName.CREATE, Product.class);
    }

    @Test
    public void deleteNotExistedPermissionTest() throws Exception {
        try {
            when(permissionDao.getEntity(any(SearchCriteria.class))).thenReturn(null);
            permissionService.deletePermission(Workflow.createDummyProduct(1L), "test", PermissionName.CREATE, Product.class);
            fail("NotFoundException don't thrown!");
        } catch (NotFoundException e) {
            //expected
        }
    }

    @Test
    public void deleteAllPermissionsTest() throws Exception {
        List<Permission> permissions = Workflow.createPermissionList();
        when(permissionDao.getEntityList(any(SearchCriteria.class))).thenReturn(permissions);
        permissionService.deletePermission(Workflow.createDummyProduct(1L), Product.class);
    }

    @Test
    public void deleteNotExistedPermissionsTest() throws Exception {
        when(permissionDao.getEntityList(any(SearchCriteria.class))).thenReturn(null);
        permissionService.deletePermission(Workflow.createDummyProduct(1L), Product.class);
    }
}
