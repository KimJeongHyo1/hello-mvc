package com.sh.mvc.notification.model.service;

import com.sh.mvc.notification.model.entity.Notification;
import com.sh.mvc.notification.model.entity.Type;
import org.assertj.core.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class NotificationServiceTest {
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        this.notificationService = new NotificationService();
    }

    @Disabled
    @DisplayName("한 행의 알림 데이터를 기록한다.")
    @ParameterizedTest
    @CsvSource({
            "qwerty, NEW_COMMENT, 마약시작하면 바로 끝 게시글에 댓글이 달렸습니다",
            "abcde, NEW_COMMENT, 안녕 잘있어! 게시글에 댓글이 달렸습니다"})
    void test1(String memberId, Type type, String content, Boolean checked) {
        // given
        assertThat(memberId).isNotNull();
        assertThat(type).isNotNull();
        assertThat(content).isNotNull();
//        assertThat(checked).isNotNull();

        // when
        Notification noti = new Notification();
        noti.setMemberId(memberId);
        noti.setType(type);
        noti.setContent(content);
//        noti.setChecked(checked);
        int result = notificationService.insertNotification(noti);

        // then
        assertThat(result).isGreaterThan(0);
    }

    @DisplayName("특정회원의 확인 안한 알림내열을 조회한다.")
    @ParameterizedTest
    @ValueSource(strings = {"qwerty", "abcde"})
    void test2(String memberId) {
        // given
        assertThat(memberId).isNotNull();

        // when
        List<Notification> notifications = notificationService.findByMemberId(memberId);
        System.out.println(notifications);

        // then
        assertThat(notifications)
                .isNotNull()
                .allSatisfy((noti) -> {
                    assertThat(noti.getId()).isNotNull().isNotZero();
                    assertThat(noti.getMemberId()).isEqualTo(memberId);
                    assertThat(noti.getType()).isNotNull();
                    assertThat(noti.getContent()).isNotNull();
                    assertThat(noti.getChecked()).isNotNull();
                    assertThat(noti.getRegDate()).isNotNull();
                });
    }

}