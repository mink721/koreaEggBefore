package cc.koreaEgg.mapper;

import cc.koreaEgg.entity.User;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class Mapper {

    public User mapUser(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUserName(rs.getString("username"));
        user.setPwd(rs.getString("password"));
        return user;
    }

}
