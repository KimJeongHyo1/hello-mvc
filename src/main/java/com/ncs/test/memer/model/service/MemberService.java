package com.ncs.test.memer.model.service;

import com.ncs.test.memer.model.dao.MemberDao;
import com.ncs.test.memer.model.vo.Member;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.http.HttpServlet;

import static com.sh.mvc.common.SqlSessionTemplate.getSqlSession;

public class MemberService extends HttpServlet {

    private MemberDao memberDao = new MemberDao();

    public int signUp(Member member) {
        SqlSession session = getSqlSession();
        int result = 0;
        try {
            result = memberDao.signUp(session, member);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw e;
        } finally {
            session.close();
        }
        return result;
    }
}
