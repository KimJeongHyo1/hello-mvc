package com.sh.mvc.ws.endpoint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sh.mvc.common.GsonConverter;
import com.sh.mvc.notification.model.entity.Notification;
import com.sh.mvc.ws.config.HelloMvcWebSocketConfigurator;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * 서버사이드 웹소켓 엔드포인트 (최초요청을 처리하는 곳)
 */
@ServerEndpoint(value = "/echo", configurator = HelloMvcWebSocketConfigurator.class)
public class EchoWebSocket {
    private static Gson gson;
    static {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, GsonConverter.LOCAL_DATE_SERIALIZER);
        builder.registerTypeAdapter(LocalDate.class, GsonConverter.LOCAL_DATE_DESERIALIZER);
        builder.registerTypeAdapter(LocalDateTime.class, GsonConverter.LOCAL_DATE_TIME_SERIALIZER);
        builder.registerTypeAdapter(LocalDateTime.class, GsonConverter.LOCAL_DATE_TIME_DESERIALIZER);
        Gson gson = builder.create();
        gson = builder.create();
    }

    /**
     * 웹소켓세션관리를 위한 맵객체
     * - id=session
     * - 멀티쓰레드환경을 위한 동기화(쓰레드간 이용순서가 정해진, 한번에 하나의 쓰레드만 접근가능한)처리된 맵이 필요하다.
     */
    private static Map<String, Session> clientMap = Collections.synchronizedMap(new HashMap<>());
    private static void addToClientMap(String memberId, Session session) {
        clientMap.put(memberId, session);
        log();
    }

    /**
     * 개인별 실시간 알림 메소드
     * @param noti
     */
    public static void sendNotification(Notification noti) {
        Session session = clientMap.get(noti.getMemberId());
        if(session != null) {
            RemoteEndpoint.Basic basicRemote = session.getBasicRemote();
            try {
                basicRemote.sendText(gson.toJson(noti));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(noti.getMemberId() + "에 실시간 알림을 전송했습니다...");
        }
        else {
            System.out.println(noti.getMemberId() + "는 접속중이 아닙니다...");
        }
    }

    private void removeFromClientMap(String memberId) {
        clientMap.remove(memberId);
        log();
    }
    private static void log() {
        System.out.println(clientMap.size() + "명 접속중... " + clientMap.keySet());
    }

    @OnOpen
    public void open(EndpointConfig config, Session session) {
        System.out.print("[open] ");
        Map<String, Object> props = config.getUserProperties();
        String memberId = (String) props.get("memberId");
        System.out.println(memberId + "님이 접속했습니다.");
        addToClientMap(memberId, session);

        // 연결해제시 map에서 사용자제거를 위해 session의 내부맵에도 사용자id를 등록
        Map<String, Object> sessionProps = session.getUserProperties();
        sessionProps.put("memberId", memberId);
    }

    @OnMessage
    public void message(String msg, Session session) {
        System.out.println("message : " + msg);

        Collection<Session> sessions = clientMap.values();
        for(Session sess : sessions) {
            RemoteEndpoint.Basic basic = sess.getBasicRemote();
            try {
                basic.sendText(msg);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @OnError
    public void error(Throwable e) {
        System.out.println("error");
        e.printStackTrace();
    }
    @OnClose
    public void close(Session session) {
        System.out.print("[close] ");
        Map<String, Object> sessionProps = session.getUserProperties();
        String memberId = (String) sessionProps.get("memberId");
        System.out.println(memberId + "님이 접속해제했습니다.");
        removeFromClientMap(memberId);
    }


}
