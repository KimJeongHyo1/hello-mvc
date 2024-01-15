package com.sh.mvc.ws.config;

import com.sh.mvc.member.model.entity.Member;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.Map;

public class HelloMvcWebSocketConfigurator extends ServerEndpointConfig.Configurator {

    /**
     *  소켓연결을 수립하기 위한 과정이 Handshake이다.
     *  - HandshakeRequest 웹소켓연결 요청 관련
     *  - HandshakeResponse 웹소켓연결 응답 관련
     *
     *  ServerEndpointConfig객체
     *  - 필요한 설정정보를 작성해두면 Endpoint클래스의 @Onopen에서 참조할수 있다.
     *  - 내부적으로 userProperties라는 맵객체를 통해 정보관리
     *
     *  @param sec
     *  @param request
     *  @param response
     */
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        // HttpSession을 통해 로그인한 사용자정보 가져오기
        HttpSession session = (HttpSession) request.getHttpSession();
        Member loginMember = (Member) session.getAttribute("loginMember");
        String memberId = loginMember.getId();

        // 설정객체의 내부맵에 저장
        Map<String, Object> props = sec.getUserProperties();
        props.put("memberId", memberId);

    }
}
