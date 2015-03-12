<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Alto Book Reader Viewer</title>
    <link rel="stylesheet" type="text/css" href="./BookReader/BookReader.css"/>
    <script type="text/javascript" src="./BookReader/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="./BookReader/jquery-ui-1.8.5.custom.min.js"></script>
    <script type="text/javascript" src="./BookReader/dragscrollable.js"></script>
    <script type="text/javascript" src="./BookReader/jquery.colorbox-min.js"></script>
    <script type="text/javascript" src="./BookReader/jquery.ui.ipad.js"></script>
    <script type="text/javascript" src="./BookReader/jquery.bt.min.js"></script>

	<script type="text/javascript" src="./dependencies/jquery.accordion.source.js"  charset="utf-8"></script>
    <script type="text/javascript" src="./BookReader/BookReader.js"></script>
</head>
<body style="background-color: ##939598;">

	<div id="BookReader">


<form action="javascript:br.search($("#textSrch").val());" id="booksearch" >
	<input type="search" id="textSrch" name="textSrch" val="" placeholder="Search inside">
	<input type="hidden" >
	<button type="submit" id="btnSrch" name="btnSrch">GO</button>
</form>
    <noscript>
    <p>
        The BookReader requires JavaScript to be enabled. Please check that your browser supports JavaScript and that it is enabled in the browser settings.  You can also try one of the <a href="http://www.archive.org/details/goodytwoshoes00newyiala"> other formats of the book</a>.
    </p>
    </noscript>
</div>
<script type="text/javascript">
var firstPage = '${page}';
var linkToFile = '';
var pageWight = ${pageWight};
var pageHight = ${pageHight};
var leafMap = ${leafMap};
var pageNumbers = 1;
var bookTitle= '${bookTitle}';
var filePath = '${filePath}';
var extension = '${extension}';
var dvs = '${ie_dvs}';
var repPid= '${rep_pid}';
var pageProgression ='${pageProgression}';
var hideSearch ='${hideSearch}';
var sideBar = "${sideBar}";
//sideBar =sideBar.substring(1,sideBar.length-1);

var fileArray = new Array();
var title_zoom_in = '<fmt:message key="title_zoom_in"/>';
var title_zoom_out = '<fmt:message key="title_zoom_out"/>';
var title_onepg = '<fmt:message key="title_onepg"/>';
var title_twopg = '<fmt:message key="title_twopg"/>';
var title_thumb = '<fmt:message key="title_thumb"/>';
var title_book_left = '<fmt:message key="title_book_left"/>';
var title_book_right = '<fmt:message key="title_book_right"/>';
var title_BRdn = '<fmt:message key="title_BRdn"/>';
var title_play = '<fmt:message key="title_play"/>';
var title_pause = '<fmt:message key="title_pause"/>';
var title_fullscreen = '<fmt:message key="title_fullscreen"/>';


</script>
<script type="text/javascript" src="AltoBookReader.js"></script>

	<script type="text/javascript">
		// <![CDATA[
		$(document).ready(function () {
		//	sideBar='<div class="menuWrap"><div class="pageContent"><ul class="accordion"><li><a>מדינת היהודים</a><ul><li><a>FRONT</a><div><a href="javascript:BookReader.prototype.menuBar()">דברים אחדים</a><a href="javascript:BookReader.prototype.menuBar()">אל הקוראים העברים</a><a href="javascript:BookReader.prototype.menuBar()">הקדמה</a></div></li><li><a>MAIN</a><div><a href="javascript:BookReader.prototype.menuBar()">פתח דבר</a><a href="javascript:BookReader.prototype.menuBar()">החלק הכללי</a><a href="javascript:BookReader.prototype.menuBar()">החברה היהודית</a><a href="javascript:BookReader.prototype.menuBar()">מטות הערים</a><a href="javascript:BookReader.prototype.menuBar()">אגודת היהודים</a><a href="javascript:BookReader.prototype.menuBar()">אחרית דבר</a></div></li></ul></li></ul></div></div>';
				$("#BookReader").append(sideBar);
			$('ul').accordion();
		});
		// ]]>
	</script>
</body>
</html>
