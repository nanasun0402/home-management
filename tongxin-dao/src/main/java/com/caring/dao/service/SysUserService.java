package com.caring.dao.service;

import static com.caring.dao.config.ModelConst.User.ADMIN;
import com.caring.dao.model.User;
import com.caring.dao.model.query.Page;
import com.caring.dao.model.query.PageParam;
import com.caring.dao.model.query.filter.UserFilter;
import com.caring.dao.repository.UserRepository;
import static com.caring.dao.service.ServiceUtils.equalsNotNull;
import static com.caring.dao.service.ServiceUtils.notEquals;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author james
 */
@Service
public class SysUserService extends BaseService {

    @Autowired
    private UserRepository userRepository;

    public Page<User> findUsers(PageParam<UserFilter> pageParam) {
        StringBuilder SQL = new StringBuilder("SELECT u FROM User u");
        StringBuilder SQLcount = new StringBuilder("SELECT COUNT(1) FROM User u");
        return executePageQuery(pageParam, SQL, SQLcount);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    public User findByUserId(Long userId) {
        return userRepository.findOne(userId);
    }

    public User saveUser(User user) {
        if (StringUtils.isEmpty(user.getUsername())) {
            throw new RuntimeException("用户名不能为空");
        }
        if (ADMIN.equalsIgnoreCase(user.getUsername())) {
            User admin = userRepository.findByUsernameIgnoreCase(ADMIN);
            if (notEquals(admin.getId(), user.getId())) {
                throw new RuntimeException("admin 为超级管理员保留用户名");
            }
        }
        if (user.getId() != null) {
            // Checking if modify admin username
            User savedUser = userRepository.findOne(user.getId());
            if (!ADMIN.equalsIgnoreCase(user.getUsername())
                    && ADMIN.equalsIgnoreCase(savedUser.getUsername())) {
                throw new RuntimeException("admin用户名不能被修改");
            }
        }
        User findByUsername = userRepository.findByUsernameIgnoreCase(user.getUsername());
        if (findByUsername == null
                || equalsNotNull(user.getId(), findByUsername.getId())) {
            if (user.getId() != null && user.getPassword() == null) {
                // Merge password here if user password is null ***********
                User savedUser = userRepository.findOne(user.getId());
                user.setPassword(savedUser.getPassword());
            }
            return userRepository.save(user);
        }
        throw new TongxinDaoException("用户名不能重复");
    }

    @Transactional
    public User deleteUser(Long userId) {
        User savedUser = userId != null ? userRepository.findOne(userId) : null;
        if (savedUser != null) {
            if (savedUser.getUsername().equals("admin")) {
                throw new TongxinDaoException("超级管理员不能被删除");
            }
            userRepository.delete(userId);
        } else {
            LOG.error("Invalid user Id: {} for deleting", userId);
            throw new TongxinDaoException("无效的用户Id：" + userId);
        }
        return savedUser;
    }
}
