<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		
		<title><s:text name="title" /></title>
		
		<!-- Latest compiled and minified CSS -->
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

		<!-- jQuery library -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
		
		<!-- Latest compiled JavaScript -->
		<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
		<s:head/>
	</head>
	
	<body>
		<s:if test="hasActionErrors()">
			<s:actionerror />
		</s:if>
		<s:if test="hasActionMessages()">
			<s:actionmessage/>
		</s:if>
		
		<div class="container">
  			<div class="jumbotron">
  				<span class="col-4">
  				<s:text name="pleaseRegister" />
  				<s:form action="save" method="post">
  					<s:textfield key="account.login" autofocus="true" />
					<s:password key="account.password" />
					<s:password key="account.passwordConfirm" />
					<s:submit key="submit" />
				</s:form>
				</span>
				<span class="col-8"></span>
  			</div>
		</div>
	</body>
</html>
