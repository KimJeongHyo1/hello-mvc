<%--
  Created by IntelliJ IDEA.
  User: user1
  Date: 2023-12-19
  Time: 오후 12:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.sh.mvc.member.model.entity.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div class="xl:container p-8">
    <div class="w-full p-6 bg-white border border-gray-200 rounded-lg shadow">
        <h5 class="mb-2 text-2xl font-semibold tracking-tight text-gray-900 ">${fn:escapeXml(board.title)}</h5>
        <p class="mb-3 font-normal text-gray-500">${board.member.name} (${board.memberId})</p>
        <p class="mb-3 font-normal text-gray-700">${board.content}</p>
        <c:forEach items="${board.attachments}" var="attach">
            <a href="${pageContext.request.contextPath}/upload/board/${attach.renamedFilename}"
               download="${attach.originalFilename}" class="flex items-center text-blue-600 hover:underline">
                <img src="../images/file.png" class="w-[16px] mr-1">
                    ${attach.originalFilename}
            </a>
        </c:forEach>
        <%--        <a href="#" class="flex items-center text-blue-600 hover:underline">--%>
        <%--            <img src="../images/file.png" class="w-[16px] mr-1">--%>
        <%--            첨부파일--%>
        <%--        </a>--%>
        <div class="text-sm mt-2 font-medium text-gray-400">
            조회수 <span>${board.readCount}</span>
        </div>
        <div class="text-sm mt-2 font-medium text-gray-400">
            작성일
            <span>
            <fmt:parseDate value="${board.regDate}" pattern="yyyy-MM-dd'T'HH:mm" var="regDate"/>
            <fmt:formatDate value="${regDate}" pattern="yy/MM/dd HH:mm"/>
            </span>
        </div>
        <%-- 작성자 본인과 관리자에게만 노출 --%>
        <c:if test="${loginMember.id eq board.memberId || loginMember.role eq Role.A}">
        <div class="flex justify-end">
            <button type="button"
                    onclick="location.href = '${pageContext.request.contextPath}/board/boardUpdate?id=${board.id}';"
                    class="px-5 py-2.5 mt-4 mr-4 sm:mt-6 text-sm font-medium text-center text-white bg-primary-700 rounded-lg focus:ring-4 focus:ring-primary-200">
                수정
            </button>
            <button type="button"
                    class="px-5 py-2.5 mt-4 sm:mt-6 text-sm font-medium text-center text-white bg-red-700 rounded-lg focus:ring-4 focus:ring-primary-200">
                삭제
            </button>
        </div>
        </c:if>
    </div>
    <!-- 댓글 폼 -->
    <div class="w-full my-2">
        <form
                name="boardCommentCreateFrm"
                action="${pageContext.request.contextPath}/board/boardCommentCreate"
                method="post">
            <input type="hidden" name="boardId" value="${board.id}">
            <input type="hidden" name="memberId" value="${loginMember.id}">
            <input type="hidden" name="commentLevel" value="1">
            <div class="w-full mb-4 border border-gray-200 rounded-lg bg-gray-50">
                <div class="px-4 py-2 bg-white rounded-t-lg">
                    <label for="content" class="sr-only">댓글 작성하기</label>
                    <textarea id="content"
                              name="content"
                              rows="4"
                              onclick="'${loginMember.id}' || alert('로그인후 댓글을 작성하세요');"
                              class="resize-none w-full px-0 text-sm text-gray-900 bg-white border-0"
                              placeholder="댓글을 작성하세요" required></textarea>
                </div>
                <div class="flex items-center justify-end px-3 py-2 border-t">
                    <button type="submit"
                            class="inline-flex items-center py-2.5 px-4 text-xs font-medium text-center text-white bg-blue-700 rounded-lg focus:ring-4 focus:ring-blue-200 hover:bg-blue-800">
                        댓글 등록
                    </button>
                </div>
            </div>
        </form>
    </div>

    <!-- 댓글 테이블 -->
    <div class="relative my-8 shadow-xl sm:rounded-lg">
        <table class="w-full text-sm text-left rtl:text-right text-gray-500">
            <tbody>
            <c:forEach items="${board.comments}" var="comment" varStatus="vs">
                <c:if test="${comment.commentLevel eq 1}">
                    <%-- 댓글 tr --%>
                    <tr class="bg-white border-b hover:bg-gray-50">
                        <td scope="row" colspan="2" class="w-4/6 px-6 py-4 font-medium text-gray-800">
                            <sub class="text-gray-500">${comment.memberId}</sub>
                            <sub class="text-gray-400">
                                <fmt:parseDate value="${comment.regDate}" pattern="yyyy-MM-dd'T'HH:mm" var="regDate"/>
                                <fmt:formatDate value="${regDate}" pattern="yy/MM/dd HH:mm"/>
                            </sub>
                            <p class="mt-2">
                                    ${comment.content}
                            </p>
                        </td>
                        <td class="px-6 py-4">
                            <c:if test="${loginMember.id eq comment.memberId || loginMember.role eq Role.A}">
                                <div class="flex">
                                    <a href="javascript:confirm('정말 삭제하시겠습니까? 😲') && document.boardCommentDeleteFrm${comment.id}.submit();" class="font-medium text-red-600 hover:underline ms-3">Remove</a>
                                </div>
                                <form name="boardCommentDeleteFrm${comment.id}" action="${pageContext.request.contextPath}/board/boardCommentDelete" method="post">
                                    <input type="hidden" name="id" value="${comment.id}">
                                    <input type="hidden" name="boardId" value="${board.id}">
                                </form>
                            </c:if>
                        </td>
                        <td class="px-4 py-4">
                            <button
                                    type="button"
                                    value="${comment.id}"
                                    data-context-path="${pageContext.request.contextPath}"
                                    data-board-id="${board.id}"
                                    data-login-member-id="${loginMember.id}"
                                    class="btn-reply w-14 inline-flex items-center py-2.5 px-4 text-xs font-medium text-center text-white bg-blue-700 rounded-lg focus:ring-4 focus:ring-blue-200 hover:bg-blue-800">
                                답글
                            </button>
                        </td>
                    </tr>
                </c:if>
                <c:if test="${comment.commentLevel eq 2}">

                    <%-- 대댓글 tr --%>
                    <tr class="bg-white border-b hover:bg-gray-50">
                        <td class="pl-6 pr-2 w-10">⎣</td>
                        <td scope="row" class="w-4/6 py-4 font-medium text-gray-600">
                            <sub class="text-gray-500">${comment.memberId}</sub>
                            <sub class="text-gray-400">
                                <fmt:parseDate value="${comment.regDate}" pattern="yyyy-MM-dd'T'HH:mm" var="regDate"/>
                                <fmt:formatDate value="${regDate}" pattern="yy/MM/dd HH:mm"/>
                            </sub>
                            <p class="mt-2">
                                    ${comment.content}
                            </p>
                        </td>
                        <td class="px-6 py-4">
                            <c:if test="${loginMember.id eq comment.memberId || loginMember.role eq Role.A}">
                                <div class="flex">
                                    <a href="javascript:confirm('정말 삭제하시겠습니까? 😲') && document.boardCommentDeleteFrm${comment.id}.submit();" class="font-medium text-red-600 hover:underline ms-3">Remove</a>
                                </div>
                                <form name="boardCommentDeleteFrm${comment.id}" action="${pageContext.request.contextPath}/board/boardCommentDelete" method="post">
                                    <input type="hidden" name="id" value="${comment.id}">
                                    <input type="hidden" name="boardId" value="${board.id}">
                                </form>
                            </c:if>
                        </td>
                        <td class="px-6 py-4"></td>
                    </tr>
                </c:if>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<script>
    <%--const contextPath = '${pageContext.request.contextPath}';--%>
    <%--const boardId = '${board.id}';--%>
    <%--const loginMemberId = '${loginMember.id}';--%>
</script>
<script src="${pageContext.request.contextPath}/js/board/boardDetail.js"></script>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>