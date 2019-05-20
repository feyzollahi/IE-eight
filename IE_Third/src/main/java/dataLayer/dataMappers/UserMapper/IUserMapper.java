package dataLayer.dataMappers.UserMapper;

import dataLayer.dataMappers.IMapper;
import model.User.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserMapper extends IMapper<User, String> {

    // other methods

    List<User> findWithFirstName(String prefixUserName) throws SQLException;
    List<User> findWithLastName(String prefixLastName) throws SQLException;
    List<User> findWithJobTitle(String prefixJobTitle) throws SQLException;
    List<User> findLoginUsers() throws SQLException;
    //...
}
