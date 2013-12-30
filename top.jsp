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
			<td>検索テーマ</td>
			<td>
				<input type="radio" name=theme value="Q1">Q1
				<input type="radio" name=theme value="Q2">Q2
				<input type="radio" name=theme value="Q3">Q3
			</td>
		<tr>
			<td>検索回数</td>
			<td>
				<input type="radio" name=times value="1">1
				<input type="radio" name=times value="2">2
				<input type="radio" name=times value="3">3
			</td>
		</tr>
		<tr>
			<td><input type="text" name=keyword></td>
			<td><input type="submit" value="検索"></td>
	</table>
	</form>
	<form action="Logout" method="post" target="_top">
		<input type="submit" value="ログアウト">
	</form>
</body>
</html>