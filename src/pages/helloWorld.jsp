<%@ taglib uri="/WEB-INF/tld/c-rt.tld" prefix="c" %>
<html>
	<header>
		<title>Hello World eddited</title>
	</header>
	<body>
			
		<form action="helloWorldStateless" method="POST">
			<input type="submit" value="Stateless Hello World"/>
			<c:choose>
				<c:when test="${empty helloWorldStatelessMessage}">
				</c:when>
				<c:otherwise>
					<c:out value="${helloWorldStatelessMessage}"/>
				</c:otherwise>
			</c:choose>
		</form>
		
		<form action="helloWorldStateful" method="POST">
			
			Hello Message:<input type="text" name="helloText"  value=""/>
			<input type="submit" value="Stateful Hello World"/>
			<c:choose>
				<c:when test="${empty helloWorldStatefulMessage}">
			    </c:when>
				<c:otherwise>
					<c:out value="${helloWorldStatefulMessage}"/>
				</c:otherwise>
			</c:choose>
			
		</form>
		
		
		<form action="helloWorldEntity" method="POST">
			
			Hello Message:<input type="text" name="helloText"  value=""/>
			<input type="submit" value="Entity Hello World"/>
			<c:choose>
				<c:when test="${empty helloWorldEntityMessage}">
				</c:when>
				<c:otherwise>
					<c:out value="${helloWorldEntityMessage}"/>
				</c:otherwise>
			</c:choose>
		</form>

		<form action="helloWorldMessageDriven" method="POST">
			
			Hello Message:<input type="text" name="helloText"  value=""/>
			<input type="submit" value="Message Driven Hello World"/>
			<c:choose>
				<c:when test="${empty helloWorldMessageDriven}">
				</c:when>
				<c:otherwise>
					<c:out value="${helloWorldMessageDriven}"/>
				</c:otherwise>
			</c:choose>
		</form>
		
		<br/>
		
		<c:choose>
			<c:when test="${empty systemError}">
				<div></div>	
			</c:when>
			
			<c:otherwise>
				<div>Error: <c:out value="${systemError}"/></div>
			</c:otherwise>
		
		</c:choose>
		
		
		
	</body>
	
</html>
