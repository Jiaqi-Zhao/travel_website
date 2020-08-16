package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {

    public User fidByUsername(String username);
    public void save(User user);

    User fidByCode(String code);

    void updateStatus(User user);

    User findByUsernameAndPassword(String username, String password);
}
