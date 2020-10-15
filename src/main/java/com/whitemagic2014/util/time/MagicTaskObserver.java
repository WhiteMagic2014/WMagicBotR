package com.whitemagic2014.util.time;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

/**
 * @Description: 用来管理定时 延时任务的 对象
 * @author: magic chen
 * @date: 2020/9/30 11:42
 **/
public class MagicTaskObserver {

    private static final Map<String, TimerTask> taskMap = new HashMap<>();

    /**
     * @Name: addTask
     * @Description: observer 注册 task
     * @Param: key
     * @Param: task
     * @Return: void
     * @Author: magic chen
     * @Date: 2020/9/30 17:28
     **/
    public static void addTask(String key, TimerTask task) {
        taskMap.put(key, task);
    }

    /**
     * @Name: removeTask
     * @Description: observer 移除 task
     * @Param: key
     * @Return: void
     * @Author: magic chen
     * @Date: 2020/9/30 17:29
     **/
    public static void removeTask(String key) {
        taskMap.remove(key);
    }

    /**
     * @Name: checkTask
     * @Description: observer 检查 task 是否存在
     * @Param: key
     * @Return: boolean
     * @Author: magic chen
     * @Date: 2020/9/30 17:29
     **/
    public static boolean checkTask(String key) {
        return taskMap.containsKey(key);
    }

    /**
     * @Name: showSchedule
     * @Description: 列出所有即将执行的 task
     * @Param:
     * @Return: java.util.Set<java.lang.String>
     * @Author: magic chen
     * @Date: 2020/9/30 17:30
     **/
    public static Set<String> showSchedule() {
        return taskMap.keySet();
    }

}
