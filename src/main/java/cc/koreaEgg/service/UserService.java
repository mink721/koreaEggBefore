package cc.koreaEgg.service;

import cc.koreaEgg.entity.User;
import cc.koreaEgg.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    UserMapper userMapper;

    public User getUserById(int id) {
        return userMapper.selectUserById(id);
    }

    public List<User> getAllUser() {
        return userMapper.selectAllUser();
    }

    public void addUser(User user) {
        userMapper.insertUser(user);
    }
}