package com.agro.service.impl;

import com.agro.org.n3r.idworker.Sid;
import com.agro.pojo.entity.Message;
import com.agro.pojo.entity.User;
import com.agro.service.MessageService;
import com.agro.utils.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@ServerEndpoint("/webSocket/{userId}")
public class WebSocket {

    private static String MSG = "MSG:";



//    static Log log=LogFactory.get(WebSocketServer.class);
    /**静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。*/
    private static int onlineCount = 0;
    /**concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。*/
    private static Map<String,WebSocket> webSocketMap = new ConcurrentHashMap<>();
    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;
    /**接收userId*/
    private String userId;

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        System.out.println("userId = "+userId);
        if(webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
            webSocketMap.put(userId,this);
            //加入set中
        }else{
            webSocketMap.put(userId,this);
            //加入set中
            addOnlineCount();
            //在线数加1
        }

        log.info("用户连接:"+userId+",当前在线人数为:" + getOnlineCount());

        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("用户:"+userId+",网络异常!!!!!!");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if(webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
            //从set中删除
            subOnlineCount();
        }
        log.info("用户退出:"+userId+",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("用户消息:"+userId+",报文->:"+message);
        //可以群发消息
        //消息保存到数据库、redis
        if(StringUtils.isNotBlank(message)){
            try {
                //解析发送的报文
                JSONObject jsonObject = JSON.parseObject(message);
                //追加发送人(防止串改)
                jsonObject.put("sendId",this.userId);
                String receiveId=jsonObject.getString("receiveId");

                UserService userService = (UserServiceImpl)SpringUtil.getBean("userService");
                User user = userService.selectById(this.userId);

                MessageService messageService = (MessageService) SpringUtil.getBean("messageServiceImpl");

                Message msgs = new Message();
                Sid sid = new Sid();
                msgs.setId(sid.nextShort());
                msgs.setFromUserId(this.userId);
                msgs.setToUserId(receiveId);
                msgs.setMsg(jsonObject.getString("msg"));
                msgs.setSendtime(new Date());
                msgs.setIsread(0);
                msgs.setPastDue(new Date());
                messageService.add(msgs);


                jsonObject.put("headImg",user.getHeadImg());
                jsonObject.put("username",user.getUsername());
//                jsonObject.put("time",new Date());
                jsonObject.put("read",0);

                //传送给对应toUserId用户的websocket
                if(StringUtils.isNotBlank(receiveId)&&webSocketMap.containsKey(receiveId)){

                    webSocketMap.get(receiveId).sendMessage(jsonObject.toJSONString());

                }else{
                    log.error("请求的userId:"+receiveId+"不在该服务器上");
                    //否则不在这个服务器上，发送到mysql或者redis
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:"+this.userId+",原因:"+error.getMessage());
        error.printStackTrace();
    }
    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }



    /**
     * 发送自定义消息
     * */
    //public static void
    public  void sendInfo(String message,@PathParam("userId") String userId) throws IOException {
        System.out.println("用户："+this.userId+"发送到：" +userId);
        log.info("发送消息到:"+userId+"，报文:"+message);
        if(StringUtils.isNotBlank(userId)&&webSocketMap.containsKey(userId)){
            webSocketMap.get(userId).sendMessage(message);
        }else{
            log.error("用户"+userId+",不在线！");
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocket.onlineCount--;
    }






    /*private Session session;

    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet();

    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSocketSet.add(this);
        log.info("[webSocket消息] 有新的连接，总数{}",webSocketSet.size());
    }
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
        log.info("[webSocket消息] 连接断开，总数{}",webSocketSet.size());
    }
    @OnMessage
    public void onMessage(String messge){
        log.info("[webSocket消息] 有新的消息，总数{}",webSocketSet.size());
    }
*/
    /*public void sendMessage(Object message){
        for (WebSocket webSocket : webSocketSet) {
            log.info("[webSocket消息] 广播消息{}",message);
            try {
                webSocket.session.getBasicRemote().sendObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                e.printStackTrace();
            }
        }
    }*/
    /*public void sendMessage(String message){
        for (WebSocket webSocket : webSocketSet) {
            log.info("[webSocket消息] 广播消息{}",message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/


}
