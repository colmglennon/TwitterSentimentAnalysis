<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="twitterSentiment.Launcher" %>
<%@ page import="twitterSentiment.Emailtest" %>

<%!Launcher test = new Launcher();
   Emailtest email=new Emailtest();%>
	
	<%test.doGet(request, response);
	 email.start(request.getParameter("email"), request.getParameter("query"), request.getParameter("number"), test.getPositive(), test.getNegative());
	  
	%>
<script>
var chart = AmCharts.makeChart("chartdivnew", {
    "type": "pie",
	"theme": "none",
	"colors":["#52DA3C",
	           "#DA3E27"
	           ],
    "dataProvider": [
{		"sentiment": "Positive",
        "percentage": <%out.println(test.getPositive()); %>
    },
{		color:"#00FF00",
        "sentiment": "Negative",
        "percentage": <%out.println(test.getNegative()); %>
    }],
    "valueField": "percentage",
    "titleField": "sentiment",
	"exportConfig":{	
      menuItems: [{
      icon: '/lib/3/images/export.png',
      format: 'png'	  
      }]  
	}
});



</script>





    
    
 <div id="firstpage">
 
 
   




<div id="resulttext"><p>Of the <% out.println(request.getParameter("number"));%> tweets selected for <%if(request.getParameter("query")==(null)) out.println(test.advancedQuery(request.getParameter("all"), request.getParameter("exact"), request.getParameter("or"), request.getParameter("none"), 
		request.getParameter("hashtag"), request.getParameter("from"), request.getParameter("to"), request.getParameter("mention"))+":");


		else out.println(request.getParameter("query")+":");%></p>


<p>
	<% out.print((int)test.getPositive());%> Were Positive</p>
<p><%out.print((int)test.getNegative()); %> Were Negative</p>
<p> <%if(!request.getParameter("email").matches(""))
	out.print("An email was sent to " +request.getParameter("email")+" with your results!");
	%></div>
}



 <div id="chartdivnew"></div>
 
 
 <div id="showtweetsinfo"><a href="#topRetweets">Click here for More Stats</a>
	</div>
	
<div id="searchagain">
	<a href="http://104.131.25.234:8080/cglennon/">Search Again</a>
</div>	
 
 </div> 
<script>
var chart = AmCharts.makeChart("followerschart", {
	"type": "serial",
	"pathToImages": "http://cdn.amcharts.com/lib/3/images/",
"categoryField": "followers",
	"rotate": true,
	"startDuration": 1,
	"categoryAxis": {
		"gridPosition": "start",
		"position": "left"
	},
	"trendLines": [],
	"graphs": [
		{
			"balloonText": "Positive",
			"fillAlphas": 0.8,
			"id": "AmGraph-1",
			"lineAlpha": 0.2,
			"title": "Income",
			"type": "column",
			"valueField": "positive"
		},
		{
			"balloonText": "Negative",
			"fillAlphas": 0.8,
			"id": "AmGraph-2",
			"lineAlpha": 0.2,
			"title": "Expenses",
			"type": "column",
			"valueField": "negative"
		}
	],
	"titles": [
	   		{
	   			"text": "Chart Title",
	   			"size": 15
	   		}
	   	],
	"guides": [],
	"valueAxes": [
		{
			"id": "ValueAxis-1",
			"position": "top",
			"axisAlpha": 0
		}
	],
	"allLabels": [],
	"amExport": {
		"right": 20,
		"top": 20
	},
	"balloon": {},
	"titles": [],
	"dataProvider": [
		{
			"followers": "0-200",
			"positive": <%out.println(test.getFollowers(0, 0));%>,
			"negative": <%out.println(test.getFollowers(0, 1));%>
		},
		{
			"followers": "201-500",
			"positive": <%out.println(test.getFollowers(1, 0));%>,
			"negative": <%out.println(test.getFollowers(1, 1));%>
		},
		{
			"followers":"501-1k",
			"positive": <%out.println(test.getFollowers(2, 0));%>,
			"negative": <%out.println(test.getFollowers(2, 1));%>
		},
		{
			"followers": "1k-5k",
			"positive": <%out.println(test.getFollowers(3, 0));%>,
			"negative": <%out.println(test.getFollowers(3, 1));%>
		},
		{
			"followers": "5k-10k",
			"positive": <%out.println(test.getFollowers(4, 0));%>,
			"negative": <%out.println(test.getFollowers(4, 1));%>
		},
		{
			"followers": "10k+",
			"positive": <%out.println(test.getFollowers(5, 0));%>,
			"negative": <%out.println(test.getFollowers(5, 1));%>
		}
	]
	
});


</script>

 <div id="followersdiv"><p>Sentiment based on number of followers</p></div>
 <div id="followerschart"></div>
 <div id="footer"></div>

	
<%test = new Launcher(); %>	
</BODY>
</HTML>