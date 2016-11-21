package com.winning.monitor.data.storage.mongodb.po.transaction;

import com.winning.monitor.data.api.transaction.vo.UsersVO;

/**
 * Created by sao something on 2016/11/21.
 */
public class UsersPO {
    private String id;
    private String username;
    private String password;

    public UsersPO(){}

    public UsersPO(UsersVO usersVO){
       this.id = usersVO.getId();
        this.username = usersVO.getUsername();
        this.password = usersVO.getPassword();
    }

    public UsersVO toUserVO(){
        UsersVO usersVO = new UsersVO();
        usersVO.setId(id);
        usersVO.setUsername(username);
        usersVO.setPassword(password);
        return usersVO;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
