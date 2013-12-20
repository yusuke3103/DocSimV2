<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Contnt-Type" Content="text/html; charset=UTF-8">
</head>
<body>
	<form action="Start" method="post" target="left">
		<table>
			<tr>
				<td><input type="text" name=keyword></td>
				<td><input type="submit" value="検索"></td>
			</tr>
		</table>
	</form>
	<form action="Logout" method="post" target="_top">
		<input type="submit" value="ログアウト">
	</form>
</body>
</html>