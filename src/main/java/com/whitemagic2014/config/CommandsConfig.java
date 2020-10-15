package com.whitemagic2014.config;

import com.whitemagic2014.command.Command;
import com.whitemagic2014.command.impl.everywhere.CanEatCommand;
import com.whitemagic2014.command.impl.everywhere.HelpCommand;
import com.whitemagic2014.command.impl.everywhere.pcr.PcrAskJJC;
import com.whitemagic2014.command.impl.friend.admin.RefreshPcrNick;
import com.whitemagic2014.command.impl.friend.admin.SwithCommand;
import com.whitemagic2014.command.impl.everywhere.HelloCommand;
import com.whitemagic2014.command.impl.friend.admin.CheckDBCommand;
import com.whitemagic2014.command.impl.everywhere.pcr.PcrAskJJCImage;
import com.whitemagic2014.command.impl.group.funny.*;
import com.whitemagic2014.command.impl.group.pcr.operation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 主要注册的指令集合
 * @author: magic chen
 * @date: 2020/8/21 00:10
 **/
@Configuration
public class CommandsConfig {

    @Autowired
    HelpCommand helpCommand;

    @Autowired
    SwithCommand swithCommand;

    @Autowired
    HelloCommand hello;

    @Autowired
    DailyLuckCommand luck;

    @Autowired
    RollCommand roll;

    @Autowired
    PlanCommand plan;

    @Autowired
    CheckDBCommand checkdb;

    @Autowired
    DecodeCommand decode;

    @Autowired
    EncodeCommand encode;

    @Autowired
    CanEatCommand caneat;


    // pcr commands
    @Autowired
    CreateGuild createGuild;

    @Autowired
    InitGuildData initGuildData;

    @Autowired
    DelGuild delGuild;

    @Autowired
    AddMember addMember;

    @Autowired
    AddMemberAll addMemberAll;

    @Autowired
    AttackKnife attackKnife;

    @Autowired
    EndKnife endKnife;

    @Autowired
    SL sl;

    @Autowired
    SLcheck sLcheck;

    @Autowired
    CancelKnife cancelKnife;

    @Autowired
    CheckBoss checkBoss;

    @Autowired
    UpdateBoss updateBoss;

    @Autowired
    CheckKnife checkKnife;

    @Autowired
    OnTree onTree;

    @Autowired
    CheckTree checkTree;

    @Autowired
    OrderBoss orderBoss;

    @Autowired
    CancelOrder cancelOrder;

    @Autowired
    CheckOrder checkOrder;

    @Autowired
    RequestAttack requestAttack;

    @Autowired
    LockBoss lockBoss;

    @Autowired
    RemoveLock removeLock;


    // pcr jjc
    @Autowired
    PcrAskJJC pcrAskJJC;

    @Autowired
    PcrAskJJCImage pcrAskJJCImage;

    @Autowired
    RefreshPcrNick refreshPcrNick;

    @Bean(name = "initCommandHeads")
    public String[] initCommandHeads() {
        return new String[]{
                "#", "$", "!", "！", ""
        };
    }

    @Bean(name = "initCommands")
    public Command[] initCommands() {
        return new Command[]{
                swithCommand, helpCommand,
                hello,
                checkdb,
                luck, roll, plan, decode, encode, caneat,
                createGuild, initGuildData, delGuild,
                addMember, addMemberAll,
                attackKnife, endKnife, cancelKnife, checkBoss, updateBoss, checkKnife,
                sl, sLcheck,
                onTree, checkTree,
                orderBoss, cancelOrder, checkOrder,
                requestAttack, lockBoss, removeLock,
                pcrAskJJC, refreshPcrNick, pcrAskJJCImage
        };
    }

}
