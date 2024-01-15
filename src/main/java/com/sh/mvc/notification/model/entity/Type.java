package com.sh.mvc.notification.model.entity;

public enum Type {
    NEW_COMMENT, // 내 게시글에 댓글이 달렸을때
    DM, // DM이 온 경우
    NEW_FOLLOWER, // 새 팔로워가 생겼을때
    NEW_CONTENT; // 팔로잉하는 회원의 새게시글 등록시
}
