<%@page import="index.IndexBean"%>
<%@page import="java.util.Vector"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<jsp:useBean id="mgr" class="index.IndexMgr" />
<%
	Vector<IndexBean> vlist = mgr.getCartList();
	Vector<String> iconlist = mgr.geticonList();
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ME - Lee Seong Soo</title>
<!-- Behavioral Meta Data -->
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<link rel="icon" type="image/png" href="img/small-logo-01.png">
<link href='http://fonts.googleapis.com/css?family=Roboto:400,900,900italic,700italic,700,500italic,400italic,500,300italic,300' rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Pacifico' rel='stylesheet' type='text/css'>
<link href='style.css' rel='stylesheet' type='text/css'>
<script src="https://use.fontawesome.com/9a677a982f.js"></script>
</head>
<body>
	<a name="ancre"></a>
	<!-- CACHE -->
	<div class="cache"></div>
	<!-- HEADER -->
	<div id="wrapper-header">
		<div id="main-header" class="object">
			<div class="logo">
				<img src="img/logo-burst.png" alt="logo platz" height="38"
					width="90">
			</div>
		</div>
	</div>
	<!-- NAVBAR -->
	<div id="wrapper-navbar">
		<div class="navbar object">
			<div id="wrapper-sorting">
				<div id="wrapper-title-1">
					<div class="top-rated object">Top-rated</div>
					<div id="fleche-nav-1"></div>
				</div>
			</div>
			<div id="wrapper-bouton-icon">
				<%
					for (int i = 0; i < iconlist.size(); i++) {
				%>
				<div id="bouton-ai">
					<img class="sel" src="img/pic/<%=iconlist.get(i)%>" height="28" width="28">
				</div>
				<%
					}
				%>
				<div id="bouton-ai">
					<img class="sel" src="img/pic/reload.png" height="28" width="28" style="display:none">
				</div>
			</div>
		</div>
	</div>
	<!-- FILTER -->
	<div id="main-container-menu" class="object">
		<div class="container-menu">

			<div id="main-cross">
				<div id="cross-menu"></div>
			</div>

			<div id="main-small-logo">
				<div class="small-logo"></div>
			</div>

			<div id="main-premium-ressource">
				<div class="premium-ressource">
					<a href="#">Premium resources</a>
				</div>
			</div>

			<div id="main-themes">
				<div class="themes">
					<a href="#">Latest themes</a>
				</div>
			</div>

			<div id="main-psd">
				<div class="psd">
					<a href="#">PSD goodies</a>
				</div>
			</div>

			<div id="main-ai">
				<div class="ai">
					<a href="#">Illustrator freebies</a>
				</div>
			</div>

			<div id="main-font">
				<div class="font">
					<a href="#">Free fonts</a>
				</div>
			</div>

			<div id="main-photo">
				<div class="photo">
					<a href="#">Free stock photos</a>
				</div>
			</div>

		</div>
	</div>
	<!-- PORTFOLIO -->
	<div id="wrapper-container">

		<div class="container object">
			<div id="main-container-image">

				<section class="work">
					<%
						for (int i = 0; i < vlist.size(); i++) {
					%>
					<figure class="white">
						<a
							onclick="javascript:window.open('count.jsp?index=<%=vlist.get(i).getIdx()%>');"
							href="<%=vlist.get(i).getUrl()%>" target="_blank"> <img src="img/pic/<%=vlist.get(i).getMainpic()%>" alt="" />
							<dl>
								<dt><%=vlist.get(i).getTitle()%></dt>
								<dd><%=vlist.get(i).getContent()%></dd>
							</dl>
						</a>
						<div id="wrapper-part-info">
							<div class="part-info-image">
								<img class="sel_tar" src="img/pic/<%=vlist.get(i).getIcon()%>" alt="" width="28" height="28" />
							</div>
							<div id="part-info"><%=vlist.get(i).getTitle()%></div>
					<%if(mgr.hasDetail(vlist.get(i).getIdx())) {%>
							<div class="part-info-image">
								<a href="detail.jsp?index=<%=vlist.get(i).getIdx()%>">
									<img class="sel_tar" src="img/pic/more.png" style="width:28px"/>
								</a>
							</div>
					<%} %>
						</div>
					</figure>
					<%
						}
					%>
				</section>

			</div>

		</div>

		<div id="wrapper-oldnew">
			<div class="oldnew">
				<div class="wrapper-oldnew-prev">
					<div id="oldnew-prev"></div>
				</div>
				<div class="wrapper-oldnew-next">
					<div id="oldnew-next"></div>
				</div>
			</div>
		</div>

		<div id="wrapper-thank">
			<div class="thank">
				<div class="thank-text">ME</div>
			</div>
		</div>
		<div id="wrapper-copyright">
			<div class="copyright">
				<div class="copy-text object">
					Copyright © 2016. Template by <a style="color: #D0D1D4;"
						href="https://dcrazed.com/">Dcrazed</a>
				</div>

				<div class="wrapper-navbouton">
					<a href="https://plus.google.com/u/0/105120990146838786351">
					<div class="facebook object"><i class="fa fa-google-plus-official" aria-hidden="true"></i></div>
					</a> 
					<a href="https://www.linkedin.com/in/%EC%84%B1%EC%88%98-%EC%9D%B4-589582126/">
					<div class="facebook object"><i class="fa fa-linkedin-square" aria-hidden="true"></i></div>
					</a> 
					<a href="https://www.youtube.com/channel/UCPcgjmoBF7FcSfRXgsFCXtQ">
					<div class="facebook object"><i class="fa fa-youtube-play" aria-hidden="true"></i></div>
					</a>
					<a href="https://github.com/Soo92">
					<div class="facebook object"><i class="fa fa-github" aria-hidden="true"></i></div>
					</a> 
				</div>
			</div>
		</div>
	</div>
	<!-- SCRIPT -->
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery.scrollTo.min.js"></script>
	<script type="text/javascript" src="js/jquery.localScroll.min.js"></script>
	<script type="text/javascript" src="js/jquery-animate-css-rotate-scale.js"></script>
	<script type="text/javascript" src="js/fastclick.min.js"></script>
	<script type="text/javascript" src="js/jquery.animate-colors-min.js"></script>
	<script type="text/javascript" src="js/jquery.animate-shadow-min.js"></script>
	<script type="text/javascript" src="js/main.js"></script>
	<script>
		/* PRELOADER */
		function preloader() {
			if (document.images) {
				var img1 = new Image();
				var img2 = new Image();
				var img3 = new Image();
				var img4 = new Image();
				var img5 = new Image();
				var img6 = new Image();
				var img7 = new Image();
				var img8 = new Image();
				var img9 = new Image();
				var img10 = new Image();
				var img11 = new Image();
				var img12 = new Image();
				var img13 = new Image();
				var img14 = new Image();
				var img15 = new Image();
				var img16 = new Image();
				var img17 = new Image();
				var img18 = new Image();
				var img19 = new Image();
				var img20 = new Image();
				img1.src = "img/psd-4.jpg";
				img2.src = "img/font-1.jpg";
				img3.src = "img/psd-1.jpg";
				img4.src = "img/psd-2.jpg";
				img5.src = "img/ai-1.jpg";
				img6.src = "img/theme-2.jpg";
				img7.src = "img/psd-3.jpg";
				img8.src = "img/font-2.jpg";
				img9.src = "img/font-3.jpg";
				img10.src = "img/ai-2.jpg";
				img11.src = "img/icons-1.jpg";
				img12.src = "img/ui-1.jpg";
				img13.src = "img/font-5.jpg";
				img14.src = "img/theme-2.jpg";
				img15.src = "img/psd-5.jpg";
				img16.src = "img/icons-3.jpg";
				img17.src = "img/font-4.jpg";
				img18.src = "img/theme-3.jpg";
				img19.src = "img/font-6.jpg";
				img20.src = "img/theme-4.jpg";
			}
		}
		function addLoadEvent(func) {
			var oldonload = window.onload;
			if (typeof window.onload != 'function') {
				window.onload = func;
			} else {
				window.onload = function() {
					if (oldonload) {
						oldonload();
					}
					func();
				}
			}
		}
		addLoadEvent(preloader);
		var speed=300;
		$('.sel').click(function() {
			var sel = $(this).attr('src')
			$('.sel_tar').each(function() {
				var sel_tar = $(this).attr('src')
				if (sel == sel_tar) {
					$(this).parent().parent().siblings('a').children().show(speed);
				} else {
					if(sel=="img/pic/reload.png"){
						$(this).parent().parent().siblings('a').children().show(speed);
						$('.sel').last().hide(speed);
					}else{
						$(this).parent().parent().siblings('a').children().hide(speed);
						$('.sel').last().show(speed);
					}
				}
			})
			console.log();
		})
	</script>
</body>
</html>
