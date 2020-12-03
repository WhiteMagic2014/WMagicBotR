package com.whitemagic2014.service;

import com.whitemagic2014.dic.CoinType;
import com.whitemagic2014.pojo.currency.CoinLog;
import com.whitemagic2014.vo.PrivateModel;

import java.util.List;

/**
 * @Description: 用户货币系统
 * @author: magic chen
 * @date: 2020/12/2 17:49
 **/
public interface UserCoinService {

    /**
     * @Name: createAccount
     * @Description: 用户开户
     * @Param: uid
     * @Param: gid 开户群
     * @Return: PrivateModel<String>
     * @Author: magic chen
     * @Date: 2020/12/2 17:50
     **/
    PrivateModel<String> createAccount(Long uid,Long gid);


    /**
     * @Name: updateCoin
     * @Description: 变更类型
     * @Param: uid  用户账户
     * @Param: gid  发生在那个群（可以为空）
     * @Param: type 哪一种货币变更
     * @Param: amount 金额 为 -就是减少
     * @Param: remark 备注
     * @Return: PrivateModel<String>
     * @Author: magic chen
     * @Date: 2020/12/2 17:52
     **/
    PrivateModel<String> updateCoin(Long uid, Long gid, CoinType type, Long amount, String remark);


    /**
     * @Name: checkLog
     * @Description: 根据条件查账
     * @Param: uid
     * @Param: gid
     * @Param: type
     * @Return: PrivateModel<List<CoinLog>>
     * @Author: magic chen
     * @Date: 2020/12/2 18:14
     **/
    PrivateModel<List<CoinLog>> checkLog(Long uid, Long gid, CoinType type);

}
