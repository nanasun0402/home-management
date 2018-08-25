//package com.caring.dao.service;
//
//import com.caring.dao.model.Role;
//import com.caring.dao.model.query.Page;
//import com.caring.dao.model.query.PageFilter;
//import com.caring.dao.model.query.PageParam;
//import com.caring.dao.repository.RoleRepository;
//import static com.caring.dao.service.BaseService.map;
//import org.dozer.DozerBeanMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// *
// * @author james
// */
//@Service
//public class RoleService {
//
//    @Autowired
//    private RoleRepository roleRepository;
//    @Autowired
//    private DozerBeanMapper daoDozerBeanMapper;
//
//    public Page<Role> findRoles(PageParam<PageFilter> pageParam) {
//        return Page.create(roleRepository.findAll());
//    }
//
//    public Role findRoleByKey(String key) {
//        return roleRepository.findOne(key);
//    }
//
//    @Transactional
//    public Role saveRole(Role role) {
//        if (role.getKey() != null) {
//            Role savedRole = roleRepository.findOne(role.getKey());
//            if (map(daoDozerBeanMapper, role, savedRole)) {
//                savedRole = roleRepository.save(savedRole);
//                return roleRepository.findOne(savedRole.getKey());
//            }
//        }
//        return roleRepository.save(role);
//    }
//
//    public void deleteRoleByKey(String key) {
//        roleRepository.delete(key);
//    }
//
//}
