<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: songjuyong
  Date: 2017. 12. 3.
  Time: PM 5:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="kr">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<title>SLiPP Java Web Programming</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<link href="../css/bootstrap.min.css" rel="stylesheet">
	<!--[if lt IE 9]>
	<script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->
	<link href="../css/styles.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-fixed-top header">
	<div class="col-md-12">
		<div class="navbar-header">

			<a href="../index.html" class="navbar-brand">SLiPP</a>
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-collapse1">
				<i class="glyphicon glyphicon-search"></i>
			</button>

		</div>
		<div class="collapse navbar-collapse" id="navbar-collapse1">
			<form class="navbar-form pull-left">
				<div class="input-group" style="max-width:470px;">
					<input type="text" class="form-control" placeholder="Search" name="srch-term" id="srch-term">
					<div class="input-group-btn">
						<button class="btn btn-default btn-primary" type="submit"><i
								class="glyphicon glyphicon-search"></i></button>
					</div>
				</div>
			</form>
			<ul class="nav navbar-nav navbar-right">
				<li>
					<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="glyphicon glyphicon-bell"></i></a>
					<ul class="dropdown-menu">
						<li><a href="https://slipp.net" target="_blank">SLiPP</a></li>
						<li><a href="https://facebook.com" target="_blank">Facebook</a></li>
					</ul>
				</li>
				<li><a href="../user/list.html"><i class="glyphicon glyphicon-user"></i></a></li>
			</ul>
		</div>
	</div>
</nav>
<div class="navbar navbar-default" id="subnav">
	<div class="col-md-12">
		<div class="navbar-header">
			<a href="#" style="margin-left:15px;" class="navbar-btn btn btn-default btn-plus dropdown-toggle"
			   data-toggle="dropdown"><i class="glyphicon glyphicon-home" style="color:#dd1111;"></i> Home
				<small><i class="glyphicon glyphicon-chevron-down"></i></small>
			</a>
			<ul class="nav dropdown-menu">
				<li><a href="../user/profile.html"><i class="glyphicon glyphicon-user" style="color:#1111dd;"></i>
					Profile</a></li>
				<li class="nav-divider"></li>
				<li><a href="#"><i class="glyphicon glyphicon-cog" style="color:#dd1111;"></i> Settings</a></li>
			</ul>
		</div>
		<div class="collapse navbar-collapse" id="navbar-collapse2">
			<ul class="nav navbar-nav navbar-right">
				<li class="active"><a href="../index.html">Posts</a></li>
				<li><a href="../user/login.html" role="button">로그인</a></li>
				<li><a href="../user/form.html" role="button">회원가입</a></li>
				<li><a href="#" role="button">로그아웃</a></li>
				<li><a href="#" role="button">개인정보수정</a></li>
			</ul>
		</div>
	</div>
</div>

<input type="hidden" id="question-id-hidden" value="${question.questionId}"/>
<div class="container" id="main">
	<div class="col-md-12 col-sm-12 col-lg-12">
		<div class="panel panel-default">
			<header class="qna-header">
				<h2 class="qna-title">${question.title}</h2>
			</header>
			<div class="content-main">
				<article class="article">
					<div class="article-header">
						<div class="article-header-thumb">
							<img src="https://graph.facebook.com/v2.3/100000059371774/picture"
							     class="article-author-thumb" alt="">
						</div>
						<div class="article-header-text">
							<a href="/users/92/kimmunsu" class="article-author-name">${question.writer}</a>
							<a href="/questions/413" class="article-header-time" title="퍼머링크">
								${question.createdDate}
								<i class="icon-link"></i>
							</a>
						</div>
					</div>
					<div class="article-doc">
						${question.contents}
					</div>
					<div class="article-util">
						<ul class="article-util-list">
							<li>
								<a class="link-modify-article" href="/questions/423/form">수정</a>
							</li>
							<li>
								<form class="form-delete" action="/questions/423" method="POST">
									<input type="hidden" name="_method" value="DELETE">
									<button class="link-delete-article" type="submit">삭제</button>
								</form>
							</li>
							<li>
								<a class="link-modify-article" href="/index.html">목록</a>
							</li>
						</ul>
					</div>
				</article>

				<div class="qna-comment">
					<div class="qna-comment-slipp">
						<p class="qna-comment-count"><strong>${answer.length}</strong>개의 의견</p>
						<div class="qna-comment-slipp-articles" id="answer-list">

							<c:forEach items="${answers}" var="answer">
								<article class="article" id="answer-${answer.answerId}">
									<div class="article-header">
										<div class="article-header-thumb">
											<img src="https://graph.facebook.com/v2.3/1324855987/picture"
											     class="article-author-thumb" alt="">
										</div>
										<div class="article-header-text">
											<a href="/users/1/자바지기" class="article-author-name">${answer.writer}</a>
											<a href="#answer-1434" class="article-header-time" title="퍼머링크">
													${answer.createDate}
											</a>
										</div>
									</div>
									<div class="article-doc comment-doc">
											${answer.contents}
									</div>
									<div class="article-util">
										<ul class="article-util-list">
											<li>
												<a class="link-modify-article" href="/questions/413/answers/1405/form">수정</a>
											</li>
											<li>
												<form class="form-delete" action="/questions/413/answers/1405"
												      method="POST">
													<input type="hidden" name="_method" value="DELETE">
													<button type="button" onclick="doDelete('${answer.answerId}')" class="link-delete-article">삭제</button>
												</form>
											</li>
										</ul>
									</div>
								</article>
							</c:forEach>

						</div>
						<form class="submit-write">
							<div class="form-group" style="padding:14px;">
									<textarea id="answer-contents" class="form-control"
									          placeholder="Add Comment"></textarea>
							</div>
							<button id="doAnswer" class="btn btn-success pull-right" type="submit">Add Comment
							</button>
							<div class="clearfix"/>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="empty-answer-template" style="display:none;">
	<article class="article" id="answer-%answerId%">
		<div class="article-header">
			<div class="article-header-thumb">
				<img src="https://graph.facebook.com/v2.3/1324855987/picture"
				     class="article-author-thumb" alt="">
			</div>
			<div class="article-header-text">
				<a href="/users/1/자바지기" class="article-author-name">%writer%</a>
				<a href="#answer-1434" class="article-header-time" title="퍼머링크">
					%createDate%
				</a>
			</div>
		</div>
		<div class="article-doc comment-doc">
			%contents%
		</div>
		<div class="article-util">
			<ul class="article-util-list">
				<li>
					<a class="link-modify-article" href="/questions/413/answers/1405/form">수정</a>
				</li>
				<li>
					<form class="form-delete" action="/questions/413/answers/1405"
					      method="POST">
						<input type="hidden" name="_method" value="DELETE">
						<button type="button" onclick="doDelete('%answerId%')" class="link-delete-article">삭제</button>
					</form>
				</li>
			</ul>
		</div>
	</article>
</div>

<!-- script references -->
<script src="../js/jquery-2.2.0.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/scripts.js"></script>
<script type="text/javascript">
	function format(htmlString, jsonData) {
		var renderedString = htmlString;
		for (var key in jsonData) {
			renderedString = renderedString.replace(new RegExp('%' + key + '%', 'gi'), jsonData[key]);
		}

		return renderedString;
	}

</script>
<script type="text/javascript">
	var questionId = $('#question-id-hidden').val();
	var $contents = $('#answer-contents');
	var template = $('#empty-answer-template').html();
	var $answerList = $('#answer-list');

	$('#doAnswer').click(function (event) {
		event.preventDefault();

		var answerBody = {
			questionId: questionId,
			writer: 'writer1',
			contents: $contents.val()
		};

		$.ajax({
			url: '/api/qna/answers',
			method: 'POST',
			data: JSON.stringify(answerBody),
			contentType: 'application/json;charset=UTF-8',
			success: function (successData) {
				var renderedHtmlString = format(template, successData);
				$answerList.append(renderedHtmlString);
				$contents.val('');
			},
			error: function (errorData) {
				alert('댓글 작성에 실패 했습니다.');
			}
		});
	});

	function doDelete(answerId){
		$.ajax({
			url : '/api/qna/answers?' + $.param({aid : answerId}),
			method : 'DELETE',
			success : function(successData){
				$('#answer-' + answerId).remove();
			},
			error : function(){
				alert('답변 삭제 실패');
			}
		});
	}
</script>
</body>
</html>