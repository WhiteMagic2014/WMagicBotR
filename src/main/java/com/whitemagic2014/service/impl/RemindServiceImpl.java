package com.whitemagic2014.service.impl;

import com.whitemagic2014.dao.RemindDao;
import com.whitemagic2014.pojo.Remind;
import com.whitemagic2014.service.RemindService;
import com.whitemagic2014.util.MagicMsgSender;
import com.whitemagic2014.util.time.MagicTaskObserver;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Description: RemindService
 * @author: magic chen
 * @date: 2023/4/13 15:11
 **/
@Service
public class RemindServiceImpl implements RemindService {

    @Autowired
    private RemindDao remindDao;

    @Override
    public void loadTask() {
        List<Remind> reminds = remindDao.loadReminds(new Date().getTime());
        for (Remind r : reminds) {
            if (r.getGid() == -1L) {
                // 私聊备忘
                MagicMsgSender.sendFriendMsgTiming(r.getAtId(), new PlainText(r.getRemind()), new Date(r.getDateL()), r.getTaskKey());
            } else {
                // 群聊备忘
                MagicMsgSender.sendGroupMsgTiming(r.getGid(), new At(r.getAtId()).plus(r.getRemind()), new Date(r.getDateL()), r.getTaskKey());
            }
        }
    }

    @Override
    public String groupRemind(Long gid, Long atId, String msgStr, Date date) {
        String taskKey = MagicMsgSender.sendGroupMsgTiming(gid, new At(atId).plus(msgStr), date);
        Remind remind = new Remind();
        remind.setTaskKey(taskKey);
        remind.setGid(gid);
        remind.setAtId(atId);
        remind.setStatus(1);
        remind.setRemind(msgStr);
        remind.setDateL(date.getTime());
        remindDao.addRemind(remind);
        return taskKey;
    }


    @Override
    public String friendRemind(Long uid, String msgStr, Date date) {
        String taskKey = MagicMsgSender.sendFriendMsgTiming(uid, new PlainText(msgStr), date);
        Remind remind = new Remind();
        remind.setTaskKey(taskKey);
        remind.setGid(-1L);
        remind.setAtId(uid);
        remind.setStatus(1);
        remind.setRemind(msgStr);
        remind.setDateL(date.getTime());
        remindDao.addRemind(remind);
        return taskKey;
    }

    @Override
    public String groupCancelRemind(String taskKey) {
        remindDao.cancelRemind(taskKey);
        MagicTaskObserver.cancelTask(taskKey);
        return "已取消";
    }
}
