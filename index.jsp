<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DocSim V2 -ログイン画面-</title>
</head>
<body>
	<Form Action="Login" method="post">
		<table>
			<tr><td colspan="2">学籍番号を2回入力してください<br>例）s12008</td></tr>
			<tr><td>ユーザ名</td><td><input type="text" name="userName"></td></tr>
			<tr><td>パスワード</td><td><input type="password" name="password"></td></tr>
			<tr><td></td><td align="right"><input type="submit" value="ログイン"></td></tr>
		</table>
	</Form>
</body>
</html>