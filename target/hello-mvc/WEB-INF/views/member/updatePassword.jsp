<%--
  Created by IntelliJ IDEA.
  User: user1
  Date: 2023-12-15
  Time: 오전 9:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="flex min-h-full flex-col justify-center px-6 py-12 lg:px-8">
  <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
    <h1 class="mb-6 text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl">
      비밀번호 변경
    </h1>
    <form name="memberPasswordUpdateFrm" class="space-y-6" method="POST">
      <div>
        <label for="oldPassword" class="block text-sm font-medium leading-6 text-gray-900">현재 비밀번호</label>
        <div class="mt-2">
          <input id="oldPassword" name="oldPassword" type="password" value="" required class="px-4 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
        </div>
      </div>
      <div>
        <div class="flex items-center justify-between">
          <label for="newPassword" class="block text-sm font-medium leading-6 text-gray-900">비밀번호</label>
        </div>
        <div class="mt-2">
          <input id="newPassword" name="newPassword" type="password" value="" required class="px-4 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
        </div>
      </div>
      <div>
        <div class="flex items-center justify-between">
          <label for="newPasswordConfirmation" class="block text-sm font-medium leading-6 text-gray-900">비밀번호</label>
        </div>
        <div class="mt-2">
          <input id="newPasswordConfirmation" type="password" value="" required class="px-4 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
        </div>
      </div>
      <div>
        <button
                type="submit" class="flex w-full justify-center rounded-md bg-rose-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-rose-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-rose-600">저장</button>
      </div>
    </form>
  </div>
</div>
<script src="${pageContext.request.contextPath}/js/member/updatePassword.js"></script>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>