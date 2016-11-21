package com.winning.monitor.data.transaction;

import com.winning.monitor.data.api.ILogin;
import com.winning.monitor.data.api.transaction.domain.LoginMessage;
import com.winning.monitor.data.api.transaction.vo.UsersVO;
import com.winning.monitor.data.storage.api.ITransactionDataStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sao something on 2016/11/21.
 */
@Service
public class Login implements ILogin {


    @Autowired
    private ITransactionDataStorage transactionDataStorage;

    @Override
    public LoginMessage login(String username, String password) {
        List<UsersVO> user = this.transactionDataStorage.findUsers(username, password);
        LoginMessage loginMessage = new LoginMessage();
        if(user != null){
            loginMessage.setState(true);
        }else{
            loginMessage.setState(false);
        }
        return loginMessage;
    }
}
