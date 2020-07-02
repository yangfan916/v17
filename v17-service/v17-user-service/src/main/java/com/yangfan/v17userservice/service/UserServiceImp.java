package com.yangfan.v17userservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.yangfan.api.IUserService;
import com.yangfan.common.base.IBaseDao;
import com.yangfan.common.base.IBaseServiceImp;
import com.yangfan.common.pojo.ResultBean;
import com.yangfan.entity.TUser;
import com.yangfan.mapper.TUserMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@Service
public class UserServiceImp extends IBaseServiceImp<TUser> implements IUserService {

    @Autowired
    private TUserMapper userMapper;

    @Override
    public ResultBean checkUsernameIsExists(String username) {
        return null;
    }

    @Override
    public ResultBean checkPhoneIsExists(String phone) {
        return null;
    }

    @Override
    public ResultBean checkEmailIsExists(String email) {
        return null;
    }

    @Override
    public IBaseDao<TUser> getBaseDao() {
        return null;
    }
}
