package com.whitemagic2014.pojo;

/**
 * @Description: 备忘
 * @author: magic chen
 * @date: 2023/4/13 16:03
 **/
public class Remind {

    private String taskKey; // remind的 key
    private Long gid; // 群号
    private Long atId; // @的成员
    private String remind; // 备忘内容
    private Integer status; // 0 = 已取消 ，1 = 未取消
    private Long dateL; // 触发时间点

    public String getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public Long getAtId() {
        return atId;
    }

    public void setAtId(Long atId) {
        this.atId = atId;
    }

    public String getRemind() {
        return remind;
    }

    public void setRemind(String remind) {
        this.remind = remind;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getDateL() {
        return dateL;
    }

    public void setDateL(Long dateL) {
        this.dateL = dateL;
    }


    @Override
    public String toString() {
        return "Remind{" +
                "taskKey='" + taskKey + '\'' +
                ", gid=" + gid +
                ", atId=" + atId +
                ", remind='" + remind + '\'' +
                ", status=" + status +
                ", dateL=" + dateL +
                '}';
    }
}
