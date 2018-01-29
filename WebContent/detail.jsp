<%@page import="index.boardBean"%>
<%@page import="java.util.Vector"%>
<%@page import="index.IndexBean"%>
<%@page import="index.detailBean"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:useBean id="mgr" class="index.IndexMgr" />
<jsp:useBean id="dmgr" class="index.detailMgr" />
<jsp:useBean id="bmgr" class="index.boardMgr" />
<%
	String index = request.getParameter("index");
	int idx = Integer.parseInt(index);
	detailBean bean = dmgr.getMember(idx);
	IndexBean pbean = mgr.getMember(idx);
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Detailed Single Post</title>
<!-- Behavioral Meta Data -->
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<link rel="icon" type="image/png" href="img/small-logo-01.png">
<link href='https://fonts.googleapis.com/css?family=Roboto:400,900,900italic,700italic,700,500italic,400italic,500,300italic,300' rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Pacifico' rel='stylesheet' type='text/css'>
<style>
/*-------------------------*/
/* FONT BDD */
/*-------------------------*/
@font-face {
	font-family: 'socialicoregular';
	src: url('font/socialico-webfont.eot');
	src: url('font/socialico-webfont.eot?#iefix')
		format('embedded-opentype'), url('font/socialico-webfont.woff2')
		format('woff2'), url('font/socialico-webfont.woff') format('woff'),
		url('font/socialico-webfont.ttf') format('truetype'),
		url('font/socialico-webfont.svg#socialicoregular') format('svg');
}
@font-face {
	font-family: 'socialico_plusregular';
	src: url('font/socialico_plus-webfont.eot');
	src: url('font/socialico_plus-webfont.eot?#iefix')
		format('embedded-opentype'), url('font/socialico_plus-webfont.woff2')
		format('woff2'), url('font/socialico_plus-webfont.woff')
		format('woff'), url('font/socialico_plus-webfont.ttf')
		format('truetype'),
		url('font/socialico_plus-webfont.svg#socialico_plusregular')
		format('svg');
}
.disable-hover {
	pointer-events: none;
}
img {
	vertical-align: bottom;
}
a {
	text-decoration: none;
}
ul {
	list-style-type: none;
	margin: 0;
	padding: 0;
}
.anim-1, .anim-2 {
	display: none;
}
/*-------------------------*/
/* MAIN TRANSITION FOR EFFECT HOVER*/
/*-------------------------*/
.object {
	transition: all 0.25s ease-in-out;
	-webkit-transition: all 0.25s ease-in-out; /** Chrome & Safari **/
	-moz-transition: all 0.25s ease-in-out; /** Firefox **/
	-o-transition: all 0.25s ease-in-out; /** Opera **/
}
/*----------------------------*/
/* BODY SETTINGS */
/*----------------------------*/
body, html {
	padding: 0;
	margin: 0;
	background-color: #F4F4F4;
}
.cache {
	width: 100%;
	height: 100%;
	position: absolute;
	background-color: #FFF;
	z-index: 1000;
}
/*----------------------------*/
/* CUSTOM SCROLLBAR  */
/*----------------------------*/
::-webkit-scrollbar {
	width: 9px;
}
::-webkit-scrollbar-track {
	background: #eee;
	border: thin solid lightgray;
	box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.1) inset;
}
::-webkit-scrollbar-thumb {
	background: #999;
	border: thin solid gray;
}
/*----------------------------*/
/* HEADER BLACK TOP WITH LOGO */
/*----------------------------*/
#wrapper-header {
	float: left;
	width: 105%;
	height: 65px;
	background-color: #2E2D30;
	opacity: 0;
}
#main-header {
	position: relative;
	width: 1200px;
	left: 50%;
	margin-left: -600px;
	height: auto;
}
#logo {
	position: absolute;
	margin-top: 13.5px;
	left: 15px;
	cursor: pointer;
}
#stripes {
	background: url(img/stripes.svg) no-repeat;
	position: absolute;
	width: 27px;
	height: 20px;
	right: 15px;
	top: 22.5px;
	cursor: pointer;
	display: none;
}
#logo img {
	height: 100%;
	width: 100%;
}
/*----------------------------*/
/* SUB NAV BAR */
/*----------------------------*/
#wrapper-navbar {
	float: left;
	width: 100%;
	height: 0px;
	background-color: #FFF;
	border-bottom: solid #EDEDED 1px;
	opacity: 0;
}
.navbar {
	position: relative;
	width: 1200px;
	left: 50%;
	margin-left: -600px;
	height: 100%;
	top: 0;
}
.top-rated, .recent, .oldies {
	float: left;
	text-align: left;
	font-family: 'Roboto', sans-serif;
	font-size: 13px;
	height: 40px;
	line-height: 40px;
	margin-top: 10px;
	cursor: pointer;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	text-transform: uppercase;
	letter-spacing: 1.5px;
}
.top-rated {
	color: #666;
	margin-left: 15px;
	font-weight: 500;
}
.recent, .oldies {
	color: #CCC;
	margin-left: 80px;
	font-weight: 500;
	margin-left: 40px;
}
.top-rated:hover, .recent:hover, .oldies:hover {
	color: #666;
	opacity: 1;
}
#fleche-nav-1, #fleche-nav-2, #fleche-nav-3 {
	float: left;
	margin-top: 26px;
	background: url(img/fleche-nav.svg) no-repeat;
	width: 5px;
	height: 10px;
	margin-left: 5px;
}
#fleche-nav-2, #fleche-nav-3 {
	opacity: 0.5;
}
#wrapper-bouton-icon {
	display: block;
}
#bouton-ai, #bouton-psd, #bouton-photo, #bouton-font, #bouton-theme,
	#bouton-premium {
	float: right;
	width: 28px;
	height: 28px;
	margin-top: 16px;
	margin-right: 15px;
	cursor: pointer;
	opacity: 1;
	transition: all 0.2s ease-in-out;
	-webkit-transition: all 0.2s ease-in-out;
	-moz-transition: all 0.2s ease-in-out;
	-o-transition: all 0.2s ease-in-out;
	-ms-transition: all 0.2s ease-in-out;
}
#bouton-ai:hover, #bouton-psd:hover, #bouton-photo:hover, #bouton-font:hover,
	#bouton-theme:hover, #bouton-premium:hover {
	opacity: 0.5;
	transform: scale(1.2);
}
#bouton-ai img, #bouton-psd img, #bouton-photo img, #bouton-font img,
	#bouton-theme img, #bouton-premium img {
	width: 100%;
	height: 100%;
}
/*----------------------------*/
/* MAIN DIV */
/*----------------------------*/
#wrapper-container {
	float: left;
	width: 100%;
	height: auto;
}
.container, .container-footer {
	position: relative;
	width: 1200px;
	min-height: 800px;
	margin-left: -600px;
	left: 50%;
}
/*----------------------------*/
/* MENU SIDE */
/*----------------------------*/
#main-container-menu {
	position: fixed;
	width: 100%;
	height: 100%;
	left: -100%;
	background-color: rgba(34, 34, 34, 0.95);
	z-index: 1000;
}
.container-menu {
	position: relative;
	height: 100%;
	width: 100%;
}
/*----------------------------*/
/* CROSS */
/*----------------------------*/
#main-cross {
	float: left;
	min-height: 53px;
	width: 100%;
	margin-bottom: 50px;
}
#cross-menu {
	position: absolute;
	width: 18px;
	height: 18px;
	right: 25px;
	margin-top: 25px;
	background: url(img/cross-menu.svg) no-repeat;
	cursor: pointer;
}
/*----------------------------*/
/* SMALL LOGO */
/*----------------------------*/
#main-small-logo {
	float: left;
	min-height: 53px;
	width: 100%;
	margin-bottom: 25px;
}
.small-logo {
	position: absolute;
	width: 131px;
	height: 53px;
	left: 50%;
	margin-left: -65.5px;
	background: url(img/small-logo.svg) no-repeat;
}
/*----------------------------*/
/* MAIN DIV WITH IMAGE */
/*----------------------------*/
#main-container-image {
	float: left;
	width: 100%;
	height: auto;
	background-color: #F4F4F4;
	margin-top: 25px;
	opacity: 0;
}
.work figure-single {
	float: left;
	width: 60%;
	margin-left: 7.5%;
	height: auto;
	padding: 0 !important;
	transition: all 0.3s ease-in-out;
	-webkit-transition: all 0.3s ease-in-out;
	-moz-transition: all 0.3s ease-in-out;
	-o-transition: all 0.3s ease-in-out;
	-ms-transition: all 0.3s ease-in-out;
	-webkit-box-shadow: 1px 1px 2px 0px rgba(0, 0, 0, 0.2);
	-moz-box-shadow: 1px 1px 2px 0px rgba(0, 0, 0, 0.2);
	box-shadow: 1px 1px 2px 0px rgba(0, 0, 0, 0.2);
	background-color: #FFF;
}
.work figure-single img {
	height: 95%;
	width: 95%;
	margin-left: 2.5%;
	margin-top: 2.5%;
}
#wrapper-part-info {
	position: relative;
	width: 100%;
	height: auto;
	background-color: #FFFFFF;
}
.part-info-image-single {
	float: left;
    max-width: 800px;
    text-align: center;
    margin: 17.5px 0;
    height: 150px;
    overflow: auto;
}
#wrapper-part-info .part-info-image img {
	height: 20%;
	width: 20%;
}
.wrapper-text-description {
	position: absolute;
	width: 20%;
	right: 7.5%;
	height: auto;
	margin-top: 20px;
}
.wrapper-view, .wrapper-file, .wrapper-weight, .wrapper-desc,
	.wrapper-download {
	float: left;
	width: 100%;
	border-bottom: solid #E0E0E0 1px;
}
.wrapper-file, .wrapper-weight, .wrapper-desc, .wrapper-download {
	border-top: solid #FFF 1px;
}
.wrapper-morefrom {
	float: left;
	width: 100%;
	border-top: solid #FFF 1px;
}
.wrapper-view, .wrapper-file, .wrapper-weigh, .wrapper-download,
	.wrapper-morefrom {
	height: 40px;
}
.wrapper-desc, .wrapper-morefrom {
	height: auto;
}
.icon-view, .icon-file, .icon-weight, .icon-desc, .icon-download {
	float: left;
	left: 0;
	margin-right: 25px;
}
.icon-view {
	margin-top: 10px;
}
.icon-file {
	margin-top: 9px;
	margin-left: 2px;
}
.icon-weight {
	margin-top: 8px;
	margin-left: 2px;
}
.icon-desc {
	margin-top: 7px;
}
.icon-download {
	margin-top: 6px;
	margin-left: 3px;
}
.icon-view img, .icon-file img, .icon-weight img, .icon-desc img,
	.icon-download img {
	height: 100%;
	width: 100%;
}
.text-view, .text-file, .text-weight, .text-desc, .text-download {
	float: left;
	text-align: left;
	font-family: Helvetica, sans-serif;
	font-size: 15px;
	font-weight: 400;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	color: #A1A1A1;
}
.text-view, .text-file, .text-weight, .text-download {
	height: 40px;
	line-height: 40px;
}
.text-desc {
	width: calc(100% - 49px);
	line-height: 130%;
	height: auto;
	margin-bottom: 25px;
	margin-top: 10px;
}
.text-download a {
	color: #A1A1A1;
	transition: all 0.2s ease-in-out;
	-webkit-transition: all 0.2s ease-in-out;
	-moz-transition: all 0.2s ease-in-out;
	-o-transition: all 0.2s ease-in-out;
}
.text-download a:hover {
	color: #666;
}
.text-morefrom {
	float: left;
	text-align: left;
	font-family: Helvetica, sans-serif;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	font-size: 16px;
	font-weight: 600;
	color: #686868;
	width: 100%;
	margin-left: 0;
	line-height: 130%;
	height: auto;
	margin-bottom: 0;
	margin-top: 20px;
}
.image-morefrom {
	float: left;
	width: 100%;
	margin-top: 20px;
	height: auto;
	margin-bottom: 35px;
}
.image-morefrom-1, .image-morefrom-2, .image-morefrom-3,
	.image-morefrom-4 {
	float: left;
	width: 47.5%;
	height: auto;
	-webkit-box-shadow: 1px 1px 2px 0px rgba(0, 0, 0, 0.2);
	-moz-box-shadow: 1px 1px 2px 0px rgba(0, 0, 0, 0.2);
	box-shadow: 1px 1px 2px 0px rgba(0, 0, 0, 0.2);
	background-color: #FFF;
	transition: all 0.2s ease-in-out;
	-webkit-transition: all 0.2s ease-in-out;
	-moz-transition: all 0.2s ease-in-out;
	-o-transition: all 0.2s ease-in-out;
	cursor: pointer;
}
.image-morefrom-1, .image-morefrom-3 {
	margin-right: 5%;
}
.image-morefrom-1, .image-morefrom-2 {
	margin-bottom: 12.5px;
}
.image-morefrom-1 img, .image-morefrom-2 img, .image-morefrom-3 img,
	.image-morefrom-4 img {
	width: 85%;
	height: auto;
	margin-left: 7.5%;
	padding-bottom: 7.5%;
	padding-top: 7.5%;
}
.image-morefrom-1:hover, .image-morefrom-2:hover, .image-morefrom-3:hover,
	.image-morefrom-4:hover {
	opacity: 0.5;
}
.post-reply, .post-reply-2 {
	float: left;
	width: 60%;
	margin-left: 4.5%;
	height: auto;
}
.post-reply {
	margin-top: 50px;
	margin-left: 4.5%;
}
.image-reply-post, .image-reply-post-2 {
	float: left;
	width: 65px;
	height: 65px;
	background: url(img/avatar.png) no-repeat;
	top: 0;
}
.image-reply-post {
	background: url(img/avatar.png) no-repeat;
}
.image-reply-post-2 {
	background: url(img/avatar-2.png) no-repeat;
}
.name-reply-post, .name-reply-post-2 {
	float: left;
	width: calc(100% - 95px);
	font-family: Helvetica, sans-serif;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	font-size: 16px;
	font-weight: 600;
	color: #686868;
	line-height: 65px;
	height: 65px;
	padding-left: 30px;
}
.text-reply-post, .text-reply-post-2 {
	float: left;
	text-align: left;
	height: auto;
	line-height: 140%;
	margin-left: 95px;
	width: calc(90% - 95px);
	font-family: Helvetica, sans-serif;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	font-size: 16px;
	font-weight: 400;
	color: #999;
	margin-top: -10px;
	border-bottom: solid #E9E9E9 1px;
	padding: 20px;
	margin-top: -20px;
}
.post-send {
	float: left;
	width: 60%;
	margin-left: 4.5%;
	height: auto;
	margin-bottom: 25px;
}
#main-post-send {
	float: left;
	width: calc(90% - 160px);
	margin-left: 28px;
	height: auto;
	margin-top: 0px;
}
#title-post-send {
	font-family: Helvetica, sans-serif;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	font-size: 16px;
	font-weight: 600;
	color: #686868;
	line-height: 65px;
	height: 65px;
	position: relative;
}
textarea {
	font-family: Helvetica, sans-serif;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	font-size: 16px;
	font-weight: 400;
	color: #999;
	width: 420px;
	height: 150px;
	background: #FFF;
	outline: none;
	border: solid 1px #DDDDDD;
	border-radius: 4px;
	padding-left: 10px;
	padding-top: 10px;
	float: left;
	margin-left: -15px;
	margin-top: -25px;
}
input[type=submit] {
	font-family: Helvetica, sans-serif;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	font-size: 16px;
	font-weight: 600;
	color: #FFF;
	cursor: pointer;
	width: 150px;
	height: 40px;
	padding: 5px 10px;
	border-radius: 4px;
	transition: all 0.5s ease-in-out;
	-webkit-transition: all 0.5s ease-in-out; /** Chrome & Safari **/
	-moz-transition: all 0.5s ease-in-out; /** Firefox **/
	-o-transition: all 0.5s ease-in-out; /** Opera **/
	background-color: #74BDEC;
	float: left;
	border: none;
}
fieldset {
	border: none;
}
textarea {
	resize: vertical;
	max-height: 50px;
	min-height: 50px;
}
textarea {
	color: #999;
}
textarea:-moz-placeholder {
	color: #999;
}
textarea::-webkit-input-placeholder {
	color: #999;
}
textarea.placeholder {
	color: #999;
}
.title-item {
	float: left;
	width: 92.5%;
	margin-left: 7.5%;
	height: 68px;
	margin-bottom: 10px;
}
.title-icon {
	float: left;
	width: 68px;
	height: 68px;
	background: url(img/title-icon-ps.svg) no-repeat;
	margin-right: 20px;
}
.title-text {
	font-family: Helvetica, sans-serif;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	font-size: 22px;
	font-weight: 600;
	color: #686868;
	line-height: 125%;
	height: auto;
	width: calc(100% - 88px);
	margin-top: 8px;
}
.title-text-2 {
	font-family: Helvetica, sans-serif;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	font-size: 15px;
	font-weight: 400;
	color: #A1A1A1;
	line-height: 125%;
	height: auto;
	width: calc(100% - 88px);
	margin-top: 8px;
}
/*----------------------------*/
/* BIG TITLE RESSOURCE FOR THE HIDDEN MENU */
/*----------------------------*/
#main-premium-ressource, #main-themes, #main-psd, #main-ai, #main-font,
	#main-photo {
	float: left;
	width: 100%;
	min-height: 36px;
}
.premium-ressource, .themes, .psd, .ai, .font, .photo {
	position: relative;
	text-align: center;
	font-family: Helvetica, sans-serif;
	font-size: 16px;
	font-weight: 400;
	height: 36px;
	line-height: 36px;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
}
.premium-ressource a, .themes a, .psd a, .ai a, .font a, .photo a {
	transition: all 0.2s ease-in-out;
	-webkit-transition: all 0.2s ease-in-out;
	-moz-transition: all 0.2s ease-in-out;
	-o-transition: all 0.2s ease-in-out;
}
.premium-ressource a, .themes a, .psd a, .photo a, .font a, .ai a {
	color: #999;
}
.premium-ressource a:hover {
	color: #CCC;
}
.themes a:hover {
	color: #66CC7A;
}
.psd a:hover {
	color: #2B9ED8;
}
.photo a:hover {
	color: #56514C;
}
.font a:hover {
	color: #B88DFF;
}
.ai a:hover {
	color: #FF9000;
}
/*----------------------------*/
/* THX PART AND OLDNEW   */
/*----------------------------*/
#wrapper-thank {
	float: left;
	width: 100%;
	height: 190px;
	background-color: #F2F2F2;
	margin-top: 40px;
}
#wrapper-oldnew {
	float: left;
	width: 100%;
	height: 80px;
	background-color: #F4F4F4;
	margin-top: 50px;
}
.thank, .oldnew {
	position: relative;
	width: 1200px;
	left: 50%;
	margin-left: -600px;
	height: 100%;
	top: 0;
}
.thank-text {
	float: left;
	text-align: center;
	font-family: 'Pacifico', cursive;
	font-size: 70px;
	height: 190px;
	line-height: 190px;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	color: #B3B3B3;
	font-weight: 400;
	width: 100%;
}
/*----------------------------*/
/* FOOTER */
/*----------------------------*/
#main-container-footer {
	float: left;
	width: 100%;
	height: auto;
	background-color: #F9F9F9;
	border-top: solid #F1F1F1 1px;
	-webkit-box-shadow: 0px 0px 2px 0px rgba(0, 0, 0, 0.1);
	-moz-box-shadow: 0px 0px 2px 0px rgba(0, 0, 0, 0.1);
	box-shadow: 0px 0px 2px 0px rgba(0, 0, 0, 0.1);
}
#row-1f, #row-2f, #row-3f, #row-4f {
	float: left;
	width: calc(100%/ 4);
	height: auto;
}
.text-row-1f, .text-row-2f, .text-row-3f, .text-row-4f {
	float: left;
	text-align: left;
	height: 140%;
	line-height: 140%;
	margin-left: 20px;
	width: 80%;
	text-align: left;
	font-family: Helvetica, sans-serif;
	font-size: 14px;
	font-weight: 400;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	color: #A1A1A1;
	margin-top: 80px;
}
.text-row-1f, .text-row-2f, .text-row-3f {
	margin-bottom: 100px;
}
/*----------------------------*/
/* NEWSLETTER*/
/*----------------------------*/
#main_tip_newsletter {
	float: left;
	width: 100%;
	height: auto;
	margin-top: 20px;
}
#tip_newsletter_input {
	font: 15px/1.6 Helvetica, sans-serif;
	color: #CCC;
	padding: 5px 5px 5px 46px;
	width: 170px;
	border: solid 2px #CCC;
	border-radius: 5px;
	-moz-appearance: none;
	-webkit-appearance: none;
	box-shadow: none;
	outline: 0;
	background: url(img/icon-newsletter.svg) no-repeat 10px 9px;
	margin-left: 20px;
	vertical-align: center;
	font-weight: 400;
}
/*----------------------------*/
/* SEARCH */
/*----------------------------*/
#main_tip_search {
	float: right;
	width: 170px;
	margin-top: 13px;
	height: auto;
	margin-right: 25px;
	display: block;
}
#tip_search_input {
	font: 16px/1.6 'Roboto', sans-serif;
	color: #686868;
	padding: 4px 4px 4px 46px;
	width: 170px;
	border: solid 1px #FFF;
	border-radius: 5px;
	-moz-appearance: none;
	-webkit-appearance: none;
	box-shadow: none;
	outline: 0;
	margin: 0;
	background: url(img/icon-search.svg) no-repeat 12px 7px;
	vertical-align: center;
	margin-left: -40px;
	background-color: #FFF;
}
#loupe {
	position: absolute;
	background: url(img/loupe.svg) no-repeat;
	width: 28px;
	height: 30px;
	right: 20px;
	top: 94px;
	cursor: pointer;
	opacity: 1;
}
/*----------------------------*/
/* COPYRIGHT PART IN BLACK DOWN*/
/*----------------------------*/
#wrapper-copyright {
	float: left;
	width: 100%;
	height: 70px;
	background-color: #2E2D30;
}
.copyright {
	position: relative;
	width: 1200px;
	left: 50%;
	margin-left: -600px;
	height: 100%;
	top: 0;
}
.copy-text {
	float: left;
	text-align: left;
	font-family: 'Roboto', sans-serif;
	font-size: 15px;
	height: 70px;
	line-height: 70px;
	cursor: pointer;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	color: #666;
	margin-left: 15px;
	font-weight: 500;
}
.wrapper-navbouton {
	position: absolute;
	height: 100%;
	right: 0;
}
.google, .facebook, .dribbble, .linkin {
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	text-align: right;
	font-family: 'socialicoregular';
	font-size: 44px;
	height: 70px;
	line-height: 70px;
	float: right;
	opacity: 0.5;
	cursor: pointer;
	float: right;
	color: #666;
	opacity: 1;
}
.dribbble, .facebook, .linkin, .google {
	padding-right: 20px;
}
.dribbble, .facebook, .linkin {
	padding-right: 12px;
}
.linkin:hover, .google:hover, .facebook:hover, .dribbble:hover {
	color: #CCC;
	transform: scale(1.2);
}
/*----------------------------*/
/* RWD */
/*----------------------------*/
/* Others wide screen -2 */
@media only screen and (max-width: 2000px) {
	#main-container-menu {
		display: none;
	}
}
/* Others wide screen */
@media only screen and (max-width: 1200px) {
	#main-header, .navbar, .container, .container-footer, .thank, .oldnew,
		.copyright {
		width: 940px;
		margin-left: -465px;
	}
	#main-container-menu {
		display: none;
	}
}
/* Small viewports — Old monitors, netbooks, tablets (landscape), etc. */
@media only screen and (max-width: 965px) {
	#main-header, .navbar, .container, .container-footer, .thank, .oldnew,
		.copyright {
		width: 835px;
		margin-left: -417.5px;
	}
	#main-container-menu {
		display: none;
	}
	#row-1f, #row-2f, #row-3f, #row-4f {
		width: 100%;
	}
	.text-row-1f, .text-row-2f, .text-row-3f, .text-row-4f {
		text-align: center;
		width: 70%;
		margin-left: 15%;
	}
	.text-row-1f {
		margin-top: 75px;
		margin-bottom: 35px;
	}
	.text-row-4f {
		margin-top: 0;
		margin-bottom: 0;
	}
	.text-row-2f, .text-row-3f {
		margin-bottom: 35px;
		margin-top: 0;
	}
	#tip_newsletter_input {
		position: relative;
		left: 50%;
		margin-left: -112.5px;
		margin-bottom: 75px;
	}
	.work figure, .post-reply, .post-reply-2, .title-item, .post-send {
		margin-left: 15px;
	}
	.wrapper-text-description {
		right: 15px;
		width: 30%;
	}
}
/* Smaller viewports — more tablets, old monitors */
@media only screen and (max-width: 860px) {
	#main-header, .navbar, .container, .container-footer, .thank, .oldnew,
		.copyright {
		width: 715px;
		margin-left: -357.5px;
	}
	#main-container-menu {
		display: none;
	}
	.part-info-image-single {
		margin-bottom: 11px;
	}
}
/* Even smaller viewports — more tablets, etc. */
@media only screen and (max-width: 740px) {
	#main-header, .navbar, .container, .container-footer, .thank, .oldnew,
		.copyright {
		width: 585px;
		margin-left: -292.5px;
	}
	#wrapper-bouton-icon, #main_tip_search, #text-search {
		display: none;
	}
	#stripes {
		display: block;
	}
	#main-container-menu {
		display: block;
	}
	.work figure {
		width: calc(100% - 30px);
		margin-left: 15px;
	}
	.wrapper-text-description {
		width: calc(100% - 30px);
		float: left;
		position: relative;
		right: 0;
		margin-left: 15px;
		margin-top: 25px;
	}
	.image-morefrom-1 img, .image-morefrom-2 img, .image-morefrom-3 img,
		.image-morefrom-4 img {
		width: 90%;
		height: auto;
		margin-left: 5%;
		padding-bottom: 5%;
		padding-top: 5%;
	}
	.post-send, .post-reply, .post-reply-2 {
		width: calc(100% - 30px);
		margin-left: 15px;
	}
}
/* Mobile phones (Landscape) and Tablets (Portrait) */
@media only screen and (max-width: 610px) {
	#main-header, .navbar, .container, .container-footer, .thank, .oldnew,
		.copyright {
		width: 455px;
		margin-left: -227.5px;
	}
	#wrapper-sorting {
		position: relative;
		width: 300px;
		left: 50%;
		margin-left: -165px;
	}
	.top-rated, .recent, .oldies {
		text-align: center;
		font-family: 'Roboto', sans-serif;
		font-size: 13px;
		height: 40px;
		line-height: 40px;
		margin-left: 25px;
	}
	.part-info-image-single {
		float: left;
	    max-width: 800px;
		margin-bottom: 17px;
	}
	#wrapper-part-info .part-info-image img {
		height: 92%;
		width: 92%;
		margin-left: 4%;
	}
	.work figure img {
		height: 92%;
		width: 92%;
		margin-left: 4%;
		margin-top: 4%;
	}
}
/* Mobile phones (Landscape) and Tablets (Portrait) */
@media only screen and (max-width: 480px) {
	#main-header, .navbar, .container, .container-footer, .thank, .oldnew,
		.copyright {
		width: 295px;
		margin-left: -147.5px;
	}
	.work figure {
		width: calc(100% - 30px);
	}
	.wrapper-navbouton {
		display: none;
	}
	.copy-text {
		text-align: center;
		width: 100%;
	}
	.thank-text {
		font-size: 50px;
	}
	.title-item {
		width: calc(100% - 30px);
		margin-left: 15px;
	}
	.title-icon {
		margin-right: 10px;
	}
	.title-text {
		font-size: 22px;
		width: 100%;
		margin-top: 8px;
	}
	.part-info-image-single {
		float: left;
	    max-width: 800px;
		margin-bottom: 15px;
	}
	#wrapper-part-info .part-info-image img {
		height: 90%;
		width: 90%;
		margin-left: 5%;
	}
	.work figure img {
		height: 90%;
		width: 90%;
		margin-left: 5%;
		margin-top: 5%;
	}
	.name-reply-post, .name-reply-post-2 {
		width: calc(100% - 75px);
		padding-left: 10px;
	}
	.text-reply-post, .text-reply-post-2 {
		margin-left: 15px;
		width: calc(100% - 30px);
		padding-top: 0;
		padding-left: 0;
		padding-right: 0;
		padding-bottom: 25px;
		margin-top: 20px;
	}
	.post-reply-2 {
		margin-top: 25px;
	}
	#main-post-send {
		width: calc(100% - 30px);
		margin-left: 15px;
	}
	textarea {
		width: calc(100% + 15px);
	}
}
/* Mobile phones (iphone4/5)*/
@media only screen and (max-width: 320px) {
}
</style>
</head>
<body>
	<a name="ancre"></a>
	<!-- CACHE -->
	<div class="cache"></div>
	<!-- HEADER -->
	<div id="wrapper-header">
		<div id="main-header" class="object">
			<div class="logo">
			<a href="./index.jsp">
				<img src="img/logo-burst.png" alt="logo platz" width="90">
			</a>	
			</div>
		</div>
	</div>
	<!-- PORTFOLIO -->
	<div id="wrapper-container">
		<div class="container object">
			<div id="main-container-image">
				<a href="<%=pbean.getUrl()%>" target="_blank">
				<div class="title-item">
					<div class="title-icon" style="background: url(img/pic/<%=pbean.getIcon()%>) no-repeat; background-size:68px;"></div>
					<div class="title-text"><%=pbean.getTitle()%></div>
					<div class="title-text-2">Since <%=pbean.getRegdate()%></div>
				</div>
				</a>
				<div class="work">
					<figure class="white" style="text-align: center;">
						<img id="demo-0" src="img/pic/<%=pbean.getMainpic() %>" style="max-width:1100px;"/> 
						<div id="wrapper-part-info">
							<div class="part-info-image-single">
							<%String pic[] = bean.getPic().split(","); 
							for(int i=0;i<pic.length;i++){%>
								<img src="img/pic/<%=pic[i] %>" style="max-width: 250px;"/> 
							<%} %>
							</div>
						</div>
					</figure>
					<div class="wrapper-text-description">
						<div class="wrapper-view">
							<div class="icon-view">
								<img src="img/icon-eye.svg" width="21" height="21"/>
							</div>
							<div class="text-view"><%=pbean.getView()%> views</div>
						</div>
						<div class="wrapper-file">
							<div class="icon-file">
								<img src="img/pic/<%=pbean.getIcon()%>" style="width: 21px;"/>
							</div>
							<div class="text-file"><%=pbean.getCate() %></div>
						</div>
						<div class="wrapper-desc">
							<div class="icon-desc">
								<img src="img/icon-desc.svg" alt="" width="24" height="24" />
							</div>
							<div class="text-desc"><%=pbean.getContent() %></div>
						</div>
						<%if(bean.getFile()!=null) {%>
						<div class="wrapper-download">
							<div class="icon-download">
								<img src="img/icon-download.svg" alt="" width="19" height="26" />
							</div>
							<div class="text-download">
								<a href="<%=bean.getFile()%>" target="_blank"><b>Download</b></a>
							</div>
						</div>
						<%} %>
						<%Vector<IndexBean> clist = mgr.getCateList(pbean.getCate()); 
							int a=1;
							int b=0;
							for(int i=0;i<clist.size();i++){
								if(mgr.hasDetail(clist.get(i).getIdx())&&clist.get(i).getIdx()!=idx){
									if(a==1){b=1;%>
						<div class="wrapper-morefrom">
							<div class="text-morefrom">More about <b><%=pbean.getCate() %></b></div>
							<div class="image-morefrom">
						<%} %>
								<a href="detail.jsp?index=<%=clist.get(i).getIdx()%>">
									<div class="image-morefrom-<%=a%>">
										<img src="img/pic/<%=clist.get(i).getMainpic() %>" alt="" width="430" height="330" />
									</div>
								</a> 
						<%a++;}} if(b==1) {%>
							</div>
						</div>
						<%} %>
					</div>
					<%Vector<boardBean> blist = bmgr.getRepleList(idx); 
					for(int i=0;i<blist.size();i++){%>
					<div class="post-reply<%if(i!=0){%>-2<%}%>">
						<div class="image-reply-post"></div>
						<div class="name-reply-post"><%=blist.get(i).getWriter() %></div>
						<div class="text-reply-post"><%=blist.get(i).getContent() %></div>
					</div>
					<%} %>
					<div class="post-send">
						<div class="image-reply-post"></div>
						<div id="main-post-send">
							<div id="title-post-send">Add your comment</div>
							<form id="contact" method="post" action="./board_proc.jsp">
								<input name="parent" type="hidden" value="<%=idx%>">
								<input name="writer" type="text" placeholder="writer" style=" color: #999; width: 150px; height: 25px; border: solid 1px #DDDDDD; border-radius: 4px; padding-left: 10px; margin: -6px 0; font: 400 15.3333px Arial; ">
								<fieldset>
									<p><textarea id="message" name="content" maxlength="500" placeholder="Add Comment" tabindex="5" cols="30" rows="4"></textarea></p>
								</fieldset>
								<div style="text-align: center;">
									<input type="submit" name="envoi" value="Add" />
								</div>
							</form>
						</div>
					</div>
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
	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery.scrollTo.min.js"></script>
	<script type="text/javascript" src="js/jquery.localScroll.min.js"></script>
	<script type="text/javascript" src="js/jquery-animate-css-rotate-scale.js"></script>
	<script type="text/javascript" src="js/fastclick.min.js"></script>
	<script type="text/javascript" src="js/jquery.flip.min.js"></script>
	<script type="text/javascript" src="js/jquery.animate-colors-min.js"></script>
	<script type="text/javascript" src="js/jquery.animate-shadow-min.js"></script>
	<script type="text/javascript" src="jquery.loupe.min.js"></script>
	<script>
$(document).ready(function() {
		$.localScroll();
		$(".cache").delay(1000).fadeOut(500);
		$("#wrapper-header").delay(1500).animate({opacity:'1',width:'100%'},500);
		$("#wrapper-navbar").delay(2000).animate({opacity:'1',height:'60px'},500);
		$("#main-container-image").delay(2500).animate({opacity:'1'},500);
	});
/*TRANSITION PAGE*/
var speed = 'slow';
var i=0;

$('.part-info-image-single > img').click(function () {
	console.log($(this));
	var prev = $(this).attr("src");
	$('#demo-0').attr("src",prev);
	$('.loupe > img').attr("src",prev);
	$('.loupe > img').css("zoom", "110%");
});

function zoom() {
	console.log($('.loupe > img'));
	i+=30;
	$('.loupe > img').css("zoom",((110+i)+"%"));
	if(i>0) i=-30;
};

$('html, body').hide();
$('#demo-0').loupe({
	width: 1000, // width of magnifier
	height: 700, // height of magnifier
    loupe: 'loupe' // css class for magnifier
});
$(".loupe").attr('onclick', 'zoom()');

$(document).ready(function() {
    $('html, body').fadeIn(speed, function() {
        $('a[href], button[href]').click(function(event) {
            var url = $(this).attr('href');
            if (url.indexOf('#') == 0 || url.indexOf('javascript:') == 0) return;
            event.preventDefault();
            $('html, body').fadeOut(speed, function() {
                window.location = url;
            });
        });
    });
});
/* DISABLE HOVER EFFECT WHILE YOU SCROLL FOR A SMOOTHY NAVIGATION */
var body = document.body,
	timer;
window.addEventListener('scroll', function() {
	clearTimeout(timer);
	if(!body.classList.contains('disable-hover'))
		body.classList.add('disable-hover')
	timer = setTimeout(function(){
		body.classList.remove('disable-hover')
	}, 200);
}, false);
 
  /* BOUTON MENU */
$(document).on('touchstart mouseover', '#stripes', function(event){
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {
			
			   $("#stripes").stop().animate({scale:'1.2',opacity:'0.5'},300);	
            event.handled = true;
        } else {
            return false;
        }
});
$(document).on('touchend mouseout', '#stripes', function(event){
	
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {
			
			   $("#stripes").stop().animate({scale:'1',opacity:'1'},300);	
            event.handled = true;
        } else {
            return false;
        }
});
/* MENU SIDE OPEN */
var MENUSIDEOPEN = document.getElementById('stripes');
MENUSIDEOPEN.addEventListener('click', function() {
			   $("#main-container-menu").stop().animate({left:'0'},300);	
}); 
/* BOUTON CROSS */
$(document).on('touchstart mouseover', '#cross-menu', function(event){
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {
			
			   $("#cross-menu").stop().animate({scale:'1.2',opacity:'0.5'},300);	
            event.handled = true;
        } else {
            return false;
        }
});
$(document).on('touchend mouseout', '#cross-menu', function(event){
	
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {
			
			   $("#cross-menu").stop().animate({scale:'1',opacity:'1'},300);	
            event.handled = true;
        } else {
            return false;
        }
});
/* MENU SIDE CLOSE */
var MENUSIDECLOSE = document.getElementById('cross-menu');
MENUSIDECLOSE.addEventListener('click', function() {
			   $("#main-container-menu").stop().animate({'left':'-100%'},300);	
}); 
/* BOUTON MENU ARROW-2 */
$(document).on('touchstart mouseover', '#wrapper-title-2', function(event){
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {
			
			   $("#fleche-nav-2").stop().animate({rotate: '90deg',opacity:'1'},300);	
            event.handled = true;
        } else {
            return false;
        }
});
$(document).on('touchend mouseout', '#wrapper-title-2', function(event){
	
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {
			
			   $("#fleche-nav-2").stop().animate({rotate: '0deg',opacity:'0.5'},300);	
            event.handled = true;
        } else {
            return false;
        }
});
/* BOUTON MENU ARROW-3 */
$(document).on('touchstart mouseover', '#wrapper-title-3', function(event){
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {
			
			   $("#fleche-nav-3").stop().animate({rotate: '90deg',opacity:'1'},300);	
            event.handled = true;
        } else {
            return false;
        }
});
$(document).on('touchend mouseout', '#wrapper-title-3', function(event){
	
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {
			
			   $("#fleche-nav-3").stop().animate({rotate: '0deg',opacity:'0.5'},300);	
            event.handled = true;
        } else {
            return false;
        }
});
/* BOUTON MENU */
$(document).on('touchstart mouseover', '#stripes', function(event){
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {
			
			   $("#stripes").stop().animate({scale:'1.2',opacity:'0.5'},300);	
            event.handled = true;
        } else {
            return false;
        }
});
$(document).on('touchend mouseout', '#stripes', function(event){
	
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {
			
			   $("#stripes").stop().animate({scale:'1',opacity:'1'},300);	
            event.handled = true;
        } else {
            return false;
        }
});
/* BOUTON NEXT */
$(document).on('touchstart mouseover', '#oldnew-next', function(event){
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {
			
			   $("#oldnew-next").stop().animate({scale:'1.2',opacity:'0.5'},300);	
            event.handled = true;
        } else {
            return false;
        }
});
$(document).on('touchend mouseout', '#oldnew-next', function(event){
	
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {
			
			   $("#oldnew-next").stop().animate({scale:'1',opacity:'1'},300);	
            event.handled = true;
        } else {
            return false;
        }
});
/* BOUTON PREV */
$(document).on('touchstart mouseover', '#oldnew-prev', function(event){
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {
			
			   $("#oldnew-prev").stop().animate({scale:'1.2',opacity:'0.5'},300);	
            event.handled = true;
        } else {
            return false;
        }
});
$(document).on('touchend mouseout', '#oldnew-prev', function(event){
	
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {
			
			   $("#oldnew-prev").stop().animate({scale:'1',opacity:'1'},300);	
            event.handled = true;
        } else {
            return false;
        }
});
/* BOUTON PREV */
$(document).on('touchstart mouseover', '#logo', function(event){
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {
		   $("#logo").stop().animate({scale:'1.1',opacity:'0.5'},300);	
            event.handled = true;
        } else {
            return false;
        }
});
$(document).on('touchend mouseout', '#logo', function(event){
	
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {
			
			   $("#logo").stop().animate({scale:'1',opacity:'1'},300);	
            event.handled = true;
        } else {
            return false;
        }
});

/*FORMULAIRE NEWSLETTER*/
$("form").on("submit", function(event) {
  event.preventDefault();
  $.post("/burstfly/form-burstfly-modified.asp",$("form").serialize(), function(data) {//alert(data);
	});
});
</script>
</body>
</html>
