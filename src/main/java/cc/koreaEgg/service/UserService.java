package cc.koreaEgg.service;

import cc.koreaEgg.entity.Criteria;
import cc.koreaEgg.entity.User;
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

        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                //return singletonList(new SimpleGrantedAuthority(role));
                return grantedAuthorityList;
            }

            @Override
            public String getPassword() {
                return user.getPwd();
            }

            @Override
            public String getUsername() {
                return username;
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }

    public User loadUserEntityByUsername(String username) {
        List<User> users = userMapper.selectUserByName(username);
        if (users == null || users.size() < 1) {
            return null;
        } else {
            return users.get(0);
        }
    }

    public boolean existId(String userId){
        User user = userMapper.selectUserByUserId(userId);
        if(user == null){
            return true;
        }else{
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
}
