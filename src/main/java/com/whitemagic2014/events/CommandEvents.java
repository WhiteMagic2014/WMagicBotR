package com.whitemagic2014.events;

import com.whitemagic2014.command.*;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.FriendMessageEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.MessageEvent;
import net.mamoe.mirai.message.TempMessageEvent;
import net.mamoe.mirai.message.data.Message;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 指令消息事件, 其实本质是一个消息事件, 在这个消息事件中进一步封装了指令
 * @author: magic chen
 * @date: 2020/8/20 15:46
 **/
public class CommandEvents extends SimpleListenerHost {

    private static Logger logger = LoggerFactory.getLogger(CommandEvents.class);


    /**
     * 指令头 区分正常消息 和 指令消息
     */
    private Set<String> commandHeads = new HashSet<>();

    /**
     * 已注册的指令, [指令名, 指令对象]
     */
    private Map<String, Command> everywhereCommands = new HashMap<>();
    private Map<String, Command> friendCommands = new HashMap<>();
    private Map<String, Command> groupCommands = new HashMap<>();
    private Map<String, Command> tempMsgCommands = new HashMap<>();


    /**
     * @Name: registerCommandHeads
     * @Description: 注册指令头
     * @Param: heads
     * @Return: void
     * @Author: magic chen
     * @Date: 2020/8/21 10:56
     **/
    public void registerCommandHeads(String... heads) {
        for (String head : heads) {
            commandHeads.add(head);
        }
    }

    /**
     * @Name: registerCommands
     * @Description: 批量注册指令
     * @Param: commands
     * @Author: magic chen
     * @Date: 2020/8/21 00:04
     **/
    public void registerCommands(Command... commands) {
        for (Command command : commands) {
            registerCommand(command);
        }
    }

    /**
     * @Name: registerCommand
     * @Description: 注册指令
     * @Param: command
     * @Author: magic chen
     * @Date: 2020/8/21 00:02
     **/
    private void registerCommand(Command command) {
        // 构建 临时指令组
        Map<String, Command> tempCommans = new HashMap<>();
        tempCommans.put(command.properties().getName().toLowerCase(), command);
        command.properties().getAlias().forEach(alias -> tempCommans.put(alias.toLowerCase(), command));

        // 根据事件类型分配指令监听组
        if (command instanceof FriendCommand) {
            friendCommands.putAll(tempCommans);
        } else if (command instanceof GroupCommand) {
            groupCommands.putAll(tempCommans);
        } else if (command instanceof TempMessageCommand) {
            tempMsgCommands.putAll(tempCommans);
        } else {
            everywhereCommands.putAll(tempCommans);
        }

    }


    /**
     * @Name: getArgs
     * @Description: 从消息体中获得 用空格分割的参数
     * @Param: msg
     * @Return: java.util.ArrayList<java.lang.String>
     * @Author: magic chen
     * @Date: 2020/8/21 16:40
     **/
    private ArrayList<String> getArgs(String msg) {
        String[] args = msg.trim().split(" ");
        ArrayList<String> list = new ArrayList<>();
        for (String arg : args) {
            if (StringUtils.isNotBlank(arg)) list.add(arg);
        }
        list.remove(0);
        return list;
    }

    /**
     * @Name: isCommand
     * @Description: 判断是否带有指令头
     * @Param: msg
     * @Return:
     * @Author: magic chen
     * @Date: 2020/8/21 11:10
     **/
    private boolean isCommand(String msg) {
        return commandHeads.stream().anyMatch(head -> msg.startsWith(head));
    }

    /**
     * @Name: getCommand
     * @Description: 获得指令
     * @Param: msg
     * @Param: commandMap
     * @Return: com.whitemagic2014.command.Command
     * @Author: magic chen
     * @Date: 2020/8/21 11:56
     **/
    private Command getCommand(String msg, Map<String, Command> commandMap) {
        String temp[] = msg.split(" ");
        // 带头指令
        String headcommand = temp[0];
        // 获得去除指令头的 指令str
        List<String> temps = commandHeads.stream()
                .filter(head -> headcommand.startsWith(head) && StringUtils.isNotBlank(head))
                .map(head -> headcommand.replaceFirst(head, ""))
                .collect(Collectors.toList());

        String commandStr;
        if (temps.isEmpty()) {
            commandStr = headcommand;
        } else {
            commandStr = temps.get(0);
        }

        if (commandMap.containsKey(commandStr.toLowerCase())) {
            return commandMap.get(commandStr.toLowerCase());
        } else {
            return null;
        }
    }


    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        // 貌似无法捕获异常
        logger.error("MessageEvents Error:", exception.getMessage());
    }


    /**
     * @Name: onMessage
     * @Description: 任何形式的消息处理
     * @Param: event
     * @Return: net.mamoe.mirai.event.ListeningStatus
     * @Author: magic chen
     * @Date: 2020/8/21 15:00
     **/
    @NotNull
    @EventHandler(priority = Listener.EventPriority.LOW)
    public ListeningStatus onMessage(@NotNull MessageEvent event) throws Exception { // 可以抛出任何异常, 将在 handleException 处理
        String oriMsg = event.getMessage().contentToString();
        if (isCommand(oriMsg)) {
            EverywhereCommand command = (EverywhereCommand) getCommand(oriMsg, everywhereCommands);
            if (command != null) {
                Message result = command.execute(event.getSender(), getArgs(oriMsg), event.getMessage(), event.getSubject());
                if (result != null) {
                    event.getSubject().sendMessage(result);
                }
            } else {
                // 单纯的带有指令头的消息 未注册的指令
            }
        } else {
            // 非指令 暂时不处理
        }
        return ListeningStatus.LISTENING; // 表示继续监听事件
    }


    /**
     * @Name: onFriendMessage
     * @Description: 好友私聊消息事件处理 如果是指令则执行 详见 FriendCommand
     * @Param: event  详见 FriendMessageEvent
     * @Return: 是否继续监听 详见 ListeningStatus
     * @Author: magic chen
     * @Date: 2020/8/21 11:59
     **/
    @NotNull
    @EventHandler(priority = Listener.EventPriority.NORMAL)
    public ListeningStatus onFriendMessage(@NotNull FriendMessageEvent event) throws Exception {
        String oriMsg = event.getMessage().contentToString();
        if (isCommand(oriMsg)) {
            FriendCommand command = (FriendCommand) getCommand(oriMsg, friendCommands);
            if (command != null) {
                Message result = command.execute(event.getSender(), getArgs(oriMsg), event.getMessage(), event.getSubject());
                if (result != null) {
                    event.getSubject().sendMessage(result);
                }
                //事件拦截 防止公共消息事件再次处理
                event.intercept();
            } else {
                // 单纯的带有指令头的消息 未注册的指令
            }
        } else {
            // 非指令 暂时不处理
        }
        return ListeningStatus.LISTENING;
    }


    /**
     * @Name: onGroupMessage
     * @Description: 群聊消息事件处理 如果是指令则执行 详见 GroupCommand
     * @Param: event
     * @Return: net.mamoe.mirai.event.ListeningStatus
     * @Author: magic chen
     * @Date: 2020/8/21 14:32
     **/
    @NotNull
    @EventHandler(priority = Listener.EventPriority.NORMAL)
    public ListeningStatus onGroupMessage(@NotNull GroupMessageEvent event) throws Exception {
        String oriMsg = event.getMessage().contentToString();
        if (isCommand(oriMsg)) {
            GroupCommand command = (GroupCommand) getCommand(oriMsg, groupCommands);
            if (command != null) {
                Message result = command.execute(event.getSender(), getArgs(oriMsg), event.getMessage(), event.getSubject());
                if (result != null) {
                    event.getSubject().sendMessage(result);
                }
                //事件拦截 防止公共消息事件再次处理
                event.intercept();
            } else {
                // 单纯的带有指令头的消息 未注册的指令
            }
        } else {
            // 非指令 暂时不处理
        }
        return ListeningStatus.LISTENING;
    }


    /**
     * @Name: onTempMessage
     * @Description: 群临时消息事件处理 如果是指令则执行 详见 TempMessageCommand
     * @Param: event
     * @Return: net.mamoe.mirai.event.ListeningStatus
     * @Author: magic chen
     * @Date: 2020/8/21 14:57
     **/
    @NotNull
    @EventHandler(priority = Listener.EventPriority.NORMAL)
    public ListeningStatus onTempMessage(@NotNull TempMessageEvent event) throws Exception {
        String oriMsg = event.getMessage().contentToString();
        if (isCommand(oriMsg)) {
            TempMessageCommand command = (TempMessageCommand) getCommand(oriMsg, tempMsgCommands);
            if (command != null) {
                Message result = command.execute(event.getSender(), getArgs(oriMsg), event.getMessage(), event.getSubject());
                if (result != null) {
                    event.getSubject().sendMessage(result);
                }
                //事件拦截 防止公共消息事件再次处理
                event.intercept();
            } else {
                // 单纯的带有指令头的消息 未注册的指令
            }
        } else {
            // 非指令 暂时不处理
        }
        return ListeningStatus.LISTENING;
    }

}
