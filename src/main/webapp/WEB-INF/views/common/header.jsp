<%--
  Created by IntelliJ IDEA.
  User: user1
  Date: 2023-12-11
  Time: 오후 4:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.sh.mvc.member.model.entity.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hello MVC</title>
    <link rel="presessionect" href="https://fonts.googleapis.com">
    <link rel="presessionect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Cute+Font&family=Gaegu:wght@300;400;700&family=Gowun+Dodum&family=Sunflower:wght@300;500;700&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
          integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g=="
          crossorigin="anonymous" referrerpolicy="no-referrer" />
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="${pageContext.request.contextPath}/js/tailwind.config.js"></script>
    <script
            src="https://code.jquery.com/jquery-3.7.1.js"
            integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4="
            crossorigin="anonymous"></script>
    <script>
        const contextPath = '${pageContext.request.contextPath}';
        <c:if test="${msg != null}">
            alert(`${msg}`); // 여러줄 입력이 가능하도록 ``(백택)으로 감싸야한다.
            <%-- session 속성 msg 제거해서 1회만 출력되도록 한다. --%>
            <%-- session.removeAttribute("msg"); --%>
            <c:remove var="msg" scope="session" />
        </c:if>
    </script>

    <c:if test="${loginMember != null}">
        <script src="${pageContext.request.contextPath}/js/ws/ws.js">
        </script>
    </c:if>
</head>
<body>
<div class="3xl:container">
    <header>
        <nav class="bg-white border-gray-200">
            <div class="flex flex-wrap justify-between items-center mx-auto max-w-screen-xl p-4">
                <a href="${pageContext.request.contextPath}" class="flex items-center space-x-3 rtl:space-x-reverse">
                    <img src="${pageContext.request.contextPath}/images/logo.svg" class="h-8" alt="Hello MVC Logo"/>
                    <span class="self-center text-2xl font-semibold whitespace-nowrap">Hello MVC</span>
                </a>
                <div class="flex items-center space-x-6 rtl:space-x-reverse">
                    <%-- c:if로 분기처리 --%>
                    <c:if test="${loginMember == null}">
                    <%-- mvc --%>
                    <a href="${pageContext.request.contextPath}/member/memberLogin" class="text-md text-gray-600 hover:underline">로그인</a>
                    <a href="${pageContext.request.contextPath}/member/memberRegister" class="text-md text-gray-500 hover:underline">회원가입</a>
                    </c:if>

                    <c:if test="${loginMember != null}">
                        <%-- 알림관련 --%>
                        <div class="relative group">
                            <span id="notification" class="fa-solid fa-bell text-slate-600"></span>
                                <ul
                                    id="notification-container"
                                    class="absolute border border-3 rounded-lg hidden group-hover:block sm:-translate-x-20 w-48 text-sm font-medium text-gray-900 bg-white border border-gray-200 rounded-lg">
                                <c:if test="${notification != null}">
                                    <c:forEach items="${notification}" var="noti">
                                        <li class="w-full px-4 py-2 border-b border-gray-200 rounded-t-lg">
                                            <a href="#" class="hover:underline text-blue-500">${noti.content}</a>
                                        </li>
                                    </c:forEach>
                                </c:if>

                                    <c:if test="${notifications == null}">
                                        <li class="w-full px-4 py-2 border-b border-gray-200 rounded-t-lg">
                                            새로운 알림이 없습니다.
                                        </li>
                                    </c:if>
                                </ul>
                        </div>
                    <a href="${pageContext.request.contextPath}/member/memberDetail" class="text-md text-gray-600 hover:underline">${loginMember.id}</a>님, 안녕하세요😊
                    <a href="${pageContext.request.contextPath}/member/memberLogout" class="text-md text-gray-500 hover:underline">로그아웃</a>
                    </c:if>

                </div>
            </div>
        </nav>

        <nav class="bg-gray-50">
            <div class="max-w-screen-xl px-4 py-3 mx-auto">
                <div class="flex items-center">
                    <ul class="flex flex-row font-medium mt-0 space-x-8 rtl:space-x-reverse text-sm">
                        <li>
                            <a href="${pageContext.request.contextPath}" class="text-gray-900 hover:underline" aria-current="page">Home</a>
                        </li>
                        <li>
                            <a href="#" class="text-gray-900 hover:underline">About</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/board/boardList" class="text-gray-900 hover:underline">Board</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/photo/photoList" class="text-gray-900 hover:underline">Photo</a>
                        </li>

                        <%-- 관리자로 로그인한 경우만 노출하기 --%>

                        <c:if test="${loginMember.role == Role.A}">
                            <li>
                                <a href="${pageContext.request.contextPath}/admin/memberList" class="text-gray-900 hover:underline">Admin-Member</a>
                            </li>
                        </c:if>
                    </ul>
                </div>
            </div>
        </nav>
    </header>
    <main class="w-full min-h-[80vh]">