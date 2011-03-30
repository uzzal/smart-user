<%@page import="org.springframework.security.userdetails.UserDetails"%>
<%@page import="org.springframework.security.context.SecurityContextHolder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<c:catch var="exception">
  <c:set var="username" value="<%=((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).
                         getUsername()%>" scope="request" />
  <c:set var="names" value="${fn:split(username, '@')}" scope="request"></c:set>
  <c:choose>
    <c:when test="${names[0] == 'smartadmin'}">
      <c:redirect url='/orgs'/>
    </c:when>
    <c:when test="${names[0] == 'admin'}">
      <c:redirect url='/orgs/sn/${names[1]}'/>
    </c:when>
    <c:otherwise>
      <c:redirect url='/orgs/sn/${names[1]}/users/un/${names[0]}'/>
    </c:otherwise>

  </c:choose>
</c:catch>

<c:if test="${exception != null}">
  Server throw an exception in redirecting the page.
</c:if>


