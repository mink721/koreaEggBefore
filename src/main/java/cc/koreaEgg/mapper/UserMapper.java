package cc.koreaEgg.mapper;

import cc.koreaEgg.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    User selectUserById(Integer id);
    List<User> selectUserByName(String name);
    List<User> selectAllUser();
    void createUser(User user);
}
