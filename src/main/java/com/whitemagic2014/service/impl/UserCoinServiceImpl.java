package com.whitemagic2014.service.impl;

import com.whitemagic2014.dao.CoinLogDao;
import com.whitemagic2014.dao.UserCoinDao;
import com.whitemagic2014.dic.CoinType;
import com.whitemagic2014.dic.ReturnCode;
import com.whitemagic2014.pojo.currency.CoinLog;
import com.whitemagic2014.pojo.currency.UserCoin;
import com.whitemagic2014.service.UserCoinService;
import com.whitemagic2014.util.DateFormatUtil;
import com.whitemagic2014.vo.PrivateModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

/**
 * @Description: UserCoinService
 * @author: magic chen
 * @date: 2020/12/2 17:56
 **/
@Service
public class UserCoinServiceImpl implements UserCoinService {

    private static final Logger logger = LoggerFactory.getLogger(UserCoinServiceImpl.class);

    @Autowired
    CoinLogDao coinLogDao;

    @Autowired
    UserCoinDao userCoinDao;

    @Override
    public PrivateModel<String> createAccount(Long uid, Long gid) {
        String time = DateFormatUtil.sdf.format(new Date());
        UserCoin uc = userCoinDao.findByUid(uid);
        if (uc != null) {
            if (uc.getAvailable()) {
                return new PrivateModel<>(ReturnCode.FAIL, "已经有账户了，请不要重复开户");
            } else {
                // 重启账户
                UserCoin update = new UserCoin();
                update.setUid(uid);
                update.setAvailable(true);
                userCoinDao.updateByUid(update);

                CoinLog log = new CoinLog();
                log.setUid(uid);
                log.setGid(gid);
                log.setType(CoinType.magicCoin);
                log.setAmount(0L);
                log.setRemark(time + " 用户：" + uid + " 在群：" + gid + "重启账户，资金为注销前金额");
                log.setTime(time);
                return new PrivateModel<>(ReturnCode.SUCCESS, "重新启用账户");
            }
        }

        UserCoin newUc = new UserCoin();
        newUc.setUid(uid);
        newUc.setAvailable(true);
        newUc.setMagicCoin(100L); // 初始金额 100
        newUc.setTime(time);
        userCoinDao.insert(newUc);

        CoinLog log = new CoinLog();
        log.setUid(uid);
        log.setGid(gid);
        log.setType(CoinType.magicCoin);
        log.setAmount(100L);
        log.setBefore(0L);
        log.setAfter(100L);
        log.setRemark(time + " 用户：" + uid + " 在群：" + gid + "开户，初始资金: " + CoinType.magicCoin.getDesc() + " 100枚");
        log.setTime(time);
        coinLogDao.insert(log);
        return new PrivateModel<>(ReturnCode.SUCCESS, "创建账户成功", "开户成功，初始资金: " + CoinType.magicCoin.getDesc() + " 100枚");
    }

    @Override
    public PrivateModel<String> updateCoin(Long uid, Long gid, CoinType type, Long amount, String remark) {
        UserCoin uc = userCoinDao.findByUid(uid);
        if (uc == null) {
            return new PrivateModel<>(ReturnCode.FAIL, "还没有账户，请先开户");
        }

        String time = DateFormatUtil.sdf.format(new Date());
        if (type == CoinType.magicCoin) {
            Long oldAmount = uc.getMagicCoin();
            Long newAmount = oldAmount + amount;

            UserCoin update = new UserCoin();
            update.setUid(uid);
            update.setMagicCoin(newAmount);
            userCoinDao.updateByUid(update);

            CoinLog log = new CoinLog();
            log.setUid(uid);
            log.setGid(gid);
            log.setType(CoinType.magicCoin);
            log.setAmount(amount);
            log.setBefore(oldAmount);
            log.setAfter(newAmount);
            log.setRemark(remark);
            log.setTime(time);
            coinLogDao.insert(log);

            return new PrivateModel<>(ReturnCode.SUCCESS, "success");
        } else {
            return new PrivateModel<>(ReturnCode.FAIL, "暂不支持该货币");
        }

    }

    @Override
    public PrivateModel<List<CoinLog>> checkLog(Long uid, Long gid, CoinType type) {
        String typeStr = null;
        if (type != null) {
            typeStr = type.name();
        }
        List<CoinLog> result = coinLogDao.selectByCondition(uid, gid, typeStr);
        return new PrivateModel<>(ReturnCode.SUCCESS, "", result);
    }


    /**
     * @Name: checkRestCoin
     * @Description: 查询余额是否足够
     * @Param: uid
     * @Param: type
     * @Param: amount
     * @Return: PrivateModel<Boolean>
     * @Author: magic chen
     * @Date: 2020/12/4 15:20
     **/
    private PrivateModel<Boolean> checkRestCoin(Long uid, CoinType type, Long amount) {
        UserCoin userCoin = userCoinDao.findByUid(uid);
        if (userCoin == null) {
            return new PrivateModel<>(ReturnCode.FAIL, "账户不存在", false);
        }
        try {
            Field dataField = userCoin.getClass().getDeclaredField(type.name());
            dataField.setAccessible(true);
            Long rest = dataField.getLong(userCoin);
            if (rest < amount) {
                return new PrivateModel<>(ReturnCode.SUCCESS, "", false);
            } else {
                return new PrivateModel<>(ReturnCode.SUCCESS, "", true);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.error("error", e);
            return new PrivateModel<>(ReturnCode.FAIL, e.getMessage(), false);
        }
    }


}
