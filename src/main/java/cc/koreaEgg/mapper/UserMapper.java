package cc.koreaEgg.mapper;

import cc.koreaEgg.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> selectUserByName(@Param("userName") String userName);
    List<User> selectAllUser(@Param("status") Integer status, @Param("userName") String userName, @Param("shopName") String shopName);
    void createUser(User user);
    User selectUserById(long id);
    User selectUserByUserId(@Param("userId") String userId);
    void updateUserPwd(@Param("id") Long id, @Param("pwd") String pwd);
    void updateUser(User user);
    void deleteUser(long id);
}
