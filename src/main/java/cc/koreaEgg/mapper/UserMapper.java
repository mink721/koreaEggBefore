package cc.koreaEgg.mapper;

import cc.koreaEgg.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserMapper {
    List<User> selectUserByName(@Param("userName") String userName);
    int selectCountAllUser(@Param("userId") String userId,
                           @Param("userName") String userName,
                           @Param("phone") String phone,
                           @Param("shopName") String shopName,
                           @Param("address") String address,
                           @Param("role") String role);
    List<User> selectAllUser(@Param("cri") Criteria cri,
                             @Param("userId") String userId,
                             @Param("userName") String userName,
                             @Param("phone") String phone,
                             @Param("shopName") String shopName,
                             @Param("address") String address,
                             @Param("role") String role);
    List<User> selectRoleChangeList();
    void createUser(User user);
    User selectUserById(long id);
    List<User> selectUserByUserId(@Param("userId") String userId);
    void updateUserPwd(@Param("id") Long id, @Param("pwd") String pwd);
    void updateUser(User user);
    void updateShop(Shop shop);
    void updateUserRole(User user);
    void deleteUser(long id);


    void createUserRoleReq(UserRoleReq userRoleReq);
    List<UserRoleReq> selectAllUserRoleReqList(@Param("cri") Criteria cri, @Param("userId") String userId, @Param("userName") String userName,
                                               @Param("status") Integer status, @Param("reqName") String reqName, @Param("memo") String memo,
                                               @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    int selectCountAllUserRoleReqList( @Param("userId") String userId, @Param("userName") String userName,
                                       @Param("status") Integer status, @Param("reqName") String reqName, @Param("memo") String memo,
                                       @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    void updateUserRoleReq(UserRoleReq userRoleReq);    //입금 확인 후 업데이트(status, role, deposit 필수)

    void countUpVisit(long userId);

    Role selectCurrentUserRole(@Param("userId") long userId); //현재기준 유저 role 조회. 만료시 null

    void createAuthNum(AuthNum auth);
    AuthNum selectAuthNum(AuthNum auth);

    List<User> selectUserByPhone(String phone);

    void createShop(Shop shop);
    List<Shop> selectShopList(@Param("cri") Criteria cri,
                              @Param("lon") double lon,
                              @Param("lat") double lat,
                              @Param("role") String role);
}
