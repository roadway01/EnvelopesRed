<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>空气检测</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta name="viewport"
          content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
	<style type="text/css">
		html{
			width: 100%;
			height: 100%;
			margin: 0;
			padding: 0;
		}
		body{
			width: 100%;
			min-height: 100%;
			margin: 0;
			padding: 0;
			background-image:url("images/background.png");
			background-size:100% 100%;
		}
		p{
			margin:0;
			padding:0;
		}
		.mainContainer{
			position: relative;
			padding-top: 14%;
			padding-left: 18px;
			padding-right: 18px;
			font-size: 0;
			font-family: "PingFangSC";
			background-size:100% 100%;
			padding-bottom: 10px;
		}
		.addBtn{
			position: absolute;
			right: 18px;
			top: 25px;
			height:36px;
			width:36px;
			background-image:url("images/add_icon.png");
			background-size:100% 100%;
		}
		.airQualityNumView{
			width: 172px;
			height: 160px;
			line-height: 160px;
			text-align: center;
			font-size: 64px;
			font-weight: bold;
			color: white;
			margin-left: auto;
			margin-right:auto;
			background-image:url("images/ring1.png");
			background-size: 100% 100%;
		}
		.airQualityTextView{
			font-size: 28px;
			color: #F8F8F8;
			width: 100%;
			text-align: center;
			margin-top: -16px;
		}
		.triangle{
			height: 10px;
			width: 9px;
			position:relative;
			background-image:url('images/triangle.png');
			background-size: 100% 100%;
			margin-top: 31px;
			top:4px;
			right:4px;
		}
		.airQualityBarView{
			width: 100%;						
		}
		.airQualityBarOnePieceView{
			display: inline-block;
			text-align: center;
			color: white;
			font-size: 14px;
			margin-right:1px;
			width:16.24%;
		}
		.airQualityBarOnePieceColorBarView{
			width: 100%;
			height: 12px;
			margin-bottom:4px;
		}
		.green{
			background-color:#A5D76B;
		}
		.yellow{
			background-color:#FACB5A;
		}
		.orange{
			background-color:#F59556;
		}
		.red{
			background-color:#E25A5A;
		}
		.purple{
			background-color:#683939;
		}
		.black{
			background-color:#383131;
		}
		.airQualityBarOnePieceNumView{
			margin-bottom:8px;
		}
		.addressBar{
			height: 32px;
			line-height: 32px;
			width: 99.5%;
			margin-top: 18px;
			overflow: hidden;
		}
		.backOpaBar{
			background-color:rgba(0,0,0,0.2);
		}
		.addressBar span{
			display:inline-block; 
		}
		.addressIcon{
			height: 14px;
			width: 11px;
			background-image: url("images/address_icon.png");
			background-size: 100% 100%;
			margin-left: 12px;
			margin-right:8px;
			margin-bottom:9px;
		}
		.addressText{
			font-size:16px;
			color:white;
			width: 80%;
			text-overflow : ellipsis;
			overflow: hidden;
		}
		.addressNextIcon{
			height:14px;
			width:8px;
			float:right;
			margin-right:12px;
			margin-top:9px;
			background-image:url("images/next_icon.png");
			background-size: 100% 100%;
		}
		.airQualityMeasureResultView{
			width: 100%;
			margin-top:20px;
		}
		.airQualityMeasureResultOnePieceView{
			width: 32%;
			height:75px;
			display: inline-block;
			color:white;
			text-align:center;
			margin-bottom:6px;
		}
		.marginRight6{
			margin-right: 1.7%;
		}
		.airQualityMeasureResultOnePieceTextType1Line1View{
			font-size:22px;
			margin-top:5px;
			height:22px;
			line-height:22px;
		}
		.airQualityMeasureResultOnePieceTextType1Line2View{
			font-size:14px;
			margin-top:4px;
			margin-bottom:6px;
			height:14px;
			line-height:14px;
		}
		.airQualityMeasureResultOnePieceTextType1Line3View{
			font-size:18px;
			height:18px;
			line-height:18px;
		}
		.airQualityMeasureResultOnePieceTextType2Line1View{
			font-size:28px;
			height: 28px;
			line-height: 28px;
			margin-top:8px;
		}
		.airQualityMeasureResultOnePieceTextType2Line2View{
			font-size:16px;
			height: 16px;
			line-height: 16px;
			margin-top:6px;
		}
		.upRightUnit{
			font-size:8px;
			display:inline-block;
			position:relative;
			bottom:4px;
		}
		.rightUnit{
			font-size:18px;
			display:inline-block;
			position:relative;
			
		}
	</style>
	<script type="text/javascript">
		window.onload = function() {
			var mainHeight = parseInt(document.getElementsByClassName("mainContainer")[0].clientHeight);
			if(parseInt(window.screen.availHeight)<mainHeight){
				var mainContainer = document.getElementsByClassName("mainContainer")[0];
				var bodyElement = document.getElementsByTagName("body")[0];
				bodyElement.style.backgroundImage = "url('')";
				mainContainer.style.backgroundImage = "url('images/background.png')";
			}
			var measureValue = parseInt(${ch2oData.measureValue});
			var airQualityNumView = document.getElementsByClassName("airQualityNumView")[0];
			airQualityNumView.style.backgroundImage = "url('images/ring"+parseInt(measureValue/50+1)+".png')";
			var airQualityText = ['优','良','轻度','中度','重度','严重'];
			var airQualityTextView = document.getElementsByClassName("airQualityTextView")[0];
			airQualityTextView.innerHTML = airQualityText[parseInt(measureValue/50)];
			var valuePercent = parseFloat(${ch2oData.measureValue}) % 50 / 50;
			var barPieceWidth = parseInt(document.getElementsByClassName("airQualityBarOnePieceView")[0].clientWidth);
			document.getElementsByClassName("triangle")[0].style.left = parseInt(parseInt(${ch2oData.measureValue}) / 50) * (barPieceWidth + 1) + valuePercent*barPieceWidth + "px";
		}
	</script>
  </head>
  
  
  <body>
  	<div class="mainContainer">
  		<div class="addBtn"></div>
  		<div class="airQualityNumView">${ch2oData.measureValue}</div>
  		<div class="airQualityTextView">优</div>
  		<div class="triangle"></div>
  		<div class="airQualityBarView">
  			<div class="airQualityBarOnePieceView">
  				<div class="airQualityBarOnePieceColorBarView green"></div>
  				<p class="airQualityBarOnePieceNumView">50</p>
  				<p>优</p>
  			</div>
  			<div class="airQualityBarOnePieceView">
  				<div class="airQualityBarOnePieceColorBarView yellow"></div>
  				<p class="airQualityBarOnePieceNumView">100</p>
  				<p>良</p>
  			</div>
  			<div class="airQualityBarOnePieceView">
  				<div class="airQualityBarOnePieceColorBarView orange"></div>
  				<p class="airQualityBarOnePieceNumView">150</p>
  				<p>轻度</p>
  			</div>
  			<div class="airQualityBarOnePieceView">
  				<div class="airQualityBarOnePieceColorBarView red"></div>
  				<p class="airQualityBarOnePieceNumView">200</p>
  				<p>中度</p>
  			</div>
  			<div class="airQualityBarOnePieceView">
  				<div class="airQualityBarOnePieceColorBarView purple"></div>
  				<p class="airQualityBarOnePieceNumView">300</p>
  				<p>重度</p>
  			</div>
  			<div class="airQualityBarOnePieceView">
  				<div class="airQualityBarOnePieceColorBarView black"></div>
  				<p class="airQualityBarOnePieceNumView">500</p>
  				<p>严重</p>
  			</div>
  		</div>
  		<div class="addressBar backOpaBar">
  		<nobr>
  			<span class="addressIcon"></span>
  			<span class="addressText">${sensorData.introduce}</span>
  			<span class="addressNextIcon"></span>
  			</nobr>
  		</div>
  		<div class="airQualityMeasureResultView">
  			<div class="airQualityMeasureResultOnePieceView backOpaBar marginRight6">
  				<p class="airQualityMeasureResultOnePieceTextType1Line1View">${pm2p5Data==null?"-":pm2p5Data.measureValue}</p>
  				<p class="airQualityMeasureResultOnePieceTextType1Line2View">μg/m<span class="upRightUnit">3</span></p>
  				<p class="airQualityMeasureResultOnePieceTextType1Line3View">PM2.5</p>
  			</div>
  			<div class="airQualityMeasureResultOnePieceView backOpaBar marginRight6">
  				<p class="airQualityMeasureResultOnePieceTextType1Line1View">${tvocData==null?"-":tvocData.measureValue}</p>
  				<p class="airQualityMeasureResultOnePieceTextType1Line2View">mg/m<span class="upRightUnit">3</span></p>
  				<p class="airQualityMeasureResultOnePieceTextType1Line3View">TVOC</p>
  			</div>
  			<div class="airQualityMeasureResultOnePieceView backOpaBar">
  				<p class="airQualityMeasureResultOnePieceTextType1Line1View">${eco2Data==null?"-":eco2Data.measureValue}</p>
  				<p class="airQualityMeasureResultOnePieceTextType1Line2View">mg/m<span class="upRightUnit">3</span></p>
  				<p class="airQualityMeasureResultOnePieceTextType1Line3View">eCO2</p>
  			</div>
  			<div class="airQualityMeasureResultOnePieceView backOpaBar  marginRight6">
  				<p class="airQualityMeasureResultOnePieceTextType2Line1View">${ch2oData==null?"-":ch2oData.measureValue}<span class="rightUnit">ppb</span></p>
  				<p class="airQualityMeasureResultOnePieceTextType2Line2View">甲醛</p>
  			</div>
  			<div class="airQualityMeasureResultOnePieceView backOpaBar  marginRight6">
  				<p class="airQualityMeasureResultOnePieceTextType2Line1View">${humidityData==null?"-":humidityData.measureValue}<span class="rightUnit">%</span></p>
  				<p class="airQualityMeasureResultOnePieceTextType2Line2View">湿度</p>
  			</div>
  			<div class="airQualityMeasureResultOnePieceView backOpaBar">
  				<p class="airQualityMeasureResultOnePieceTextType2Line1View">${tempData==null?"-":tempData.measureValue}<span class="rightUnit">℃</span></p>
  				<p class="airQualityMeasureResultOnePieceTextType2Line2View">温度</p>
  			</div>
  		</div>
  	</div>
  
  	<%-- <h2>传感器位置：</h2>
  	<h4>${sensorData.introduce}</h4>
    <h2>最新甲醛含量：</h2>
    <h4>${ch2oData.measureValue}ppb</h4>
    <h2>测量时间：</h2>
    <h4>${timeStr}</h4> --%>
  </body>
</html>
