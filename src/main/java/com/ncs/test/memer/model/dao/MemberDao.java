package com.ncs.test.memer.model.dao;

import com.ncs.test.memer.model.vo.Member;
import org.apache.ibatis.session.SqlSession;

public class MemberDao {
    public int signUp(SqlSession session, Member member) {
        return session.insert("member.signUp", member);
    }
}
