package com.whitemagic2014.command.impl.everywhere.engage;

import com.whitemagic2014.command.impl.everywhere.BaseEveryWhereCommand;
import com.whitemagic2014.dao.EngageBattleDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 火纹续战分享码
 * @author: magic chen
 * @date: 2023/1/29 14:40
 **/
public abstract class BattleKeyCommand extends BaseEveryWhereCommand {

    @Autowired
    EngageBattleDao dao;

    protected String help() {
        return "续战查询功能:\n"
                + "查询可用续战: 续战查询\n"
                + "共享一个续战: 续战登记 abcd12 困难森林\n"
                + "关闭一个续战: 续战结束 abcd12\n"
                + "认领一个续战开始战斗: 续战认领 abcd12\n"
                + "打完认领的续战交接: 续战交接 abcd12";
    }

}
