package com.yangfan.api;

import com.yangfan.common.base.IBaseService;
import com.yangfan.common.pojo.ResultBean;
import com.yangfan.entity.TUser;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
public interface IUserService extends IBaseService<TUser> {
    /**
     * 检查用户名是否唯一
     * @param username
     * @return
     */
    public ResultBean checkUsernameIsExists(String username);
    /**
     * 检查手机号是否存在
     * @param phone
     * @return
     */
    public ResultBean checkPhoneIsExists(String phone);
    /**
     * 检查邮箱是否存在
     * @param email
     * @return
     */
    public ResultBean checkEmailIsExists(String email);
}
