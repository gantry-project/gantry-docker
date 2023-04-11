package org.gantry.apiserver.domain.docker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class ContainerLogHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("WebSocket Connection was established : %s".formatted(session.getId()));
//        String username = (String) session.getAttributes().get("username");
        sessions.put(session.getId(), session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("Log tailing will be started soon : %s".formatted(session.getId()));
        String containerId = message.getPayload();
        while (true) {
            String msg = session.getId() + ":" + containerId + ":" + Instant.now().toString();
            System.out.println(msg);
            session.sendMessage(new TextMessage(msg));
            Thread.sleep(1000);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("WebSocket Connection will be closed : %s".formatted(session.getId()));
        sessions.remove(session.getId());
    }
}
