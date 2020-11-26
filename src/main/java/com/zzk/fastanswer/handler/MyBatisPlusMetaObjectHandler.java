package com.zzk.fastanswer.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zzk.fastanswer.common.constant.TableFieldConstants;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MyBatisPlus自动填充 创建时间和更新时间
 *
 * @author zzk
 * @create 2020-10-18 21:35
 */
//@Slf4j
@Component
public class MyBatisPlusMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
//        log.info("初始化数据的创建时间和修改时间....");
        this.setFieldValByName(TableFieldConstants.GMT_CREATE, LocalDateTime.now(), metaObject);
        this.setFieldValByName(TableFieldConstants.GMT_MODIFIED, LocalDateTime.now(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
//        log.info("更新数据的修改时间.....");
        this.setFieldValByName(TableFieldConstants.GMT_MODIFIED, LocalDateTime.now(), metaObject);
    }
}
