package com.zzk.fastanswer.service.impl;

import com.zzk.fastanswer.model.entity.User;
import com.zzk.fastanswer.mapper.UserMapper;
import com.zzk.fastanswer.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zzk
 * @since 2020-10-18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
