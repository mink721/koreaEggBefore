package cc.koreaEgg.service;

import cc.koreaEgg.entity.*;
import cc.koreaEgg.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
@Slf4j
@Transactional
public class UserService implements UserDetailsService {

    private Properties businessFunctions;

    @Autowired
    UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createUser(User user) {

        String pwd = passwordEncoder.encode(user.getPwd());
        user.setPwd(pwd);
        userMapper.createUser(user);
        log.debug("유저 등록 완료" + user.toString());
    }

    /* security customer user */
    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {
        User user = loadUserEntityByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + " not found");
        }

        businessFunctions = new Properties();
        String resourceName = "role-to-bf.properties"; // could also be a constant
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try(InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
            businessFunctions.load(resourceStream);
        } catch (IOException ex) {
            // handle error
        }

        Set<String> sourceSet =  StringUtils.commaDelimitedListToSet(businessFunctions.getProperty(user.getRole().toString()));

        List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();
        for(String userRole  : sourceSet){
            grantedAuthorityList.add(new SimpleGrantedAuthority(userRole));
        }

        user.setAuthorities(grantedAuthorityList);
        return user;
    }

    public User loadUserEntityByUsername(String userId) {
        List<User> users = userMapper.selectUserByUserId(userId);

        if (users == null || users.size() < 1) {
            return null;
        } else if (users.size() > 1){
            /*TODO 아이디가 중복되는 말도 안되는 일이!!??*/
            return null;
        }else {
            User user = users.get(0);
            if(user.isRoleChange()){
                userMapper.updateUserRole(user);
            }
            return user;
        }
    }

    public void refreshRole(){

        List<User> roleList = userMapper.selectRoleChangeList();

        Iterator<User> itr = roleList.iterator();

        while( itr.hasNext() )
        {
            userMapper.updateUserRole(itr.next());
        }
    }

    public boolean existId(String userId){
        List<User> users = userMapper.selectUserByUserId(userId);
        if (users == null || users.size() < 1) {
            return true;
        }else {
            return false;
        }
    }

    public List<User> selectAllUser(Criteria cri, String userId, String userName, String mobile, String shopName, String address, String role){
        return userMapper.selectAllUser(cri, userId, userName, mobile, shopName, address, role);
    }

    public int selectCountAllUser(String userId, String userName, String mobile, String shopName, String address, String role) {
        return userMapper.selectCountAllUser( userId, userName, mobile, shopName, address, role);
    }

    public User selectUserById(long id){
        return userMapper.selectUserById(id);
    }

    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    public void deleteUser(long id) {
        userMapper.deleteUser(id);
    }

    public void countUpVisit(long userId) {
        userMapper.countUpVisit(userId);
    }

    public void createUserRoleReq(UserRoleReq userRoleReq){
        userMapper.createUserRoleReq(userRoleReq);
    }
    public List<UserRoleReq> selectAllUserRoleReqList(Criteria cri, String userId, String userName, Integer status,  String reqName, String memo,  Date startDate, Date endDate){
       return userMapper.selectAllUserRoleReqList(cri, userId, userName, status, reqName, memo, startDate, endDate);
    }
    public int selectCountAllUserRoleReqList(String userId, String userName, Integer status,  String reqName,String memo, Date startDate, Date endDate){
        return userMapper.selectCountAllUserRoleReqList(userId, userName, status, reqName, memo, startDate, endDate);
    }
    public void updateUserRoleReq(UserRoleReq userRoleReq){
        userMapper.updateUserRoleReq(userRoleReq);
    }


}
