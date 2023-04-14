package com.whitemagic2014.service.impl;

import com.whitemagic2014.dao.RemindDao;
import com.whitemagic2014.pojo.Remind;
import com.whitemagic2014.service.RemindService;
import com.whitemagic2014.util.MagicMsgSender;
import com.whitemagic2014.util.time.MagicTaskObserver;
import net.mamoe.mirai.message.data.At;
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
            MagicMsgSender.sendGroupMsgTiming(r.getGid(), new At(r.getAtId()).plus(r.getRemind()), new Date(r.getDateL()), r.getTaskKey());
        }
    }

    @Override
    public String groupRemind(Long gid, Long atId, String msgStr, Date date) {
        String taskKey = MagicMsgSender.sendGroupMsgTiming(gid, new At(atId).plus(msgStr), new Date(date.getTime()));
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
    public String groupCancelRemind(String taskKey) {
        remindDao.cancelRemind(taskKey);
        MagicTaskObserver.cancelTask(taskKey);
        return "已取消";
    }
}
