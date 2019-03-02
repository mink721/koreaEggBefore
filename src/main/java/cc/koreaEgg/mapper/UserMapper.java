package cc.koreaEgg.mapper;

import cc.koreaEgg.entity.Criteria;
import cc.koreaEgg.entity.User;
import cc.koreaEgg.entity.UserRoleReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserMapper {
    List<User> selectUserByName(@Param("userName") String userName);
    int selectCountAllUser(@Param("userId") String userId,
                           @Param("userName") String userName,
                           @Param("mobile") String mobile,
                           @Param("shopName") String shopName,
                           @Param("address") String address,
                           @Param("role") String role);
    List<User> selectAllUser(@Param("cri") Criteria cri,
                             @Param("userId") String userId,
                             @Param("userName") String userName,
                             @Param("mobile") String mobile,
                             @Param("shopName") String shopName,
                             @Param("address") String address,
                             @Param("role") String role);
    void createUser(User user);
    User selectUserById(long id);
    List<User> selectUserByUserId(@Param("userId") String userId);
    void updateUserPwd(@Param("id") Long id, @Param("pwd") String pwd);
    void updateUser(User user);
    void deleteUser(long id);


    void createUserRoleReq(UserRoleReq userRoleReq);
    List<UserRoleReq> selectAllUserRoleReq(@Param("userId") String userId, @Param("status") Integer status, @Param("reqName") String reqName, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    void updateUserRoleReq(UserRoleReq userRoleReq);    //입금 확인 후 업데이트(status, role, deposit 필수)
}
