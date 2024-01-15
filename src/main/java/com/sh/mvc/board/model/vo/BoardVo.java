package com.sh.mvc.board.model.vo;

import com.sh.mvc.board.model.entity.Attachment;
import com.sh.mvc.board.model.entity.Board;
import com.sh.mvc.board.model.entity.BoardComment;
import com.sh.mvc.member.model.entity.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * vo클래스 value object
 * - immutable한 value객체
 * - entity클래스를 확장한 객체
 */
public class BoardVo extends Board {
    private Member member; // Member(javaType) member(property)
    private int attachCount; // 첨부파일 갯수
    private List<Attachment> attachments = new ArrayList<>(); // 껍데기뿐인 List를 만들어 놓고 추가되게 하기
    private List<Long> delFiles = new ArrayList<>();
    private List<BoardComment> comments;
    private int commentCount;

    public BoardVo() {
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
    //    public BoardVo(long id, String title, String memberId, String content, int readCount, LocalDateTime regDate, int attachCount) {
//        super(id, title, memberId, content, readCount, regDate);
//        this.attachCount = attachCount;
//    } // 안지우면 안만들어줌

    public BoardVo(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public int getAttachCount() {
        return attachCount;
    }

    public void setAttachCount(int attachCount) {
        this.attachCount = attachCount;
    }
    public void addAttachment(Attachment attachment) {
        this.attachments.add(attachment);
    }
    /* 편의메소드로 쓰려고 만듦 : .add(attachment) */

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public List<Long> getDelFiles() {
        return delFiles;
    }

    public void setDelFiles(List<Long> delFiles) {
        this.delFiles = delFiles;
    }

    public List<BoardComment> getComments() {
        return comments;
    }

    public void setComments(List<BoardComment> comments) {
        this.comments = comments;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public void setValue(String name, String value) {
        switch (name) {
            case "id" : setId(Long.parseLong(value)); break;
            case "title" : setTitle(value); break;
            case "memberId" : setMemberId(value); break;
            case "content" : setContent(value); break;
            case "delFile" : this.delFiles.add(Long.parseLong(value)); break; /* 삭제할 파일번호 */
            default: throw new RuntimeException("부적절한 name값 : " + name);
        }
    }

    @Override
    public String toString() {
        return "BoardVo{" +
                "member=" + member +
                ", attachCount=" + attachCount +
                ", attachments=" + attachments +
                ", delFiles=" + delFiles +
                ", comments=" + comments +
                ", commentCount=" + commentCount +
                "} " + super.toString();
    }
}
