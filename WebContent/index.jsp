<%@ page import="java.util.*" %>

<html>
<head>
<title>Twitter Sentiment Analysis</title>
<link rel="stylesheet" type="text/css" href="style.css">
<link rel="stylesheet" type="text/css" href="jquery.qtip.min.css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="jquery.qtip.min.js"></script>

<script>


$(document).ready(function(){
	$(".searchbox2").hide();
	$(".gobutton").hide();
	$(".twitter_load").hide();
	$(".emailbox").hide();
	
	$("#advancedsearch").hide();	
	
	
	
	
	
/*	$("input:text").each(function ()
{
    // store default value
    var v = this.value;

    $(this).blur(function ()
    {
        // if input is empty, reset value to default 
        if (this.value.length == 0) this.value = v;
    }).focus(function ()
    {
        // when input is focused, clear its contents
        this.value = "";
    }); 
});*/

$(document).ready(function() {
	  $(window).keydown(function(event){
	    if(event.keyCode == 13) {
	      event.preventDefault();
	      return false;
	    }
	  });
	});

	
  $(".searchbox1").keypress(function(){
    $(".searchbox2").fadeIn();
	$(".emailbox").fadeIn();
    $('.searchbox2').qtip({ // Grab some elements to apply the tooltip to
        content: {
            text: 'Choose how many tweets you wish to search for'},
            	position: {
            		adjust:{
            			x:-262
            		},
                     // Position my top left...
                    at: 'top left', // at the bottom right of...
                    target: $('.searchbox2') // my target
        }
            
        
       
    	
           
    })
  });
  
  

  $("button").click(function(){
	  $(".button").hide(300);
	  $(".searchbox2").hide(300);
	  $(".emailbox").hide(300);
	  $("#logoandtitle").hide(300);
	  $(".searchbox1").hide(300);
	  $("#advancedsearch").show(500);
	  $(".gobutton").hide(300);
	  $(".return").hide();
	 $("#main").attr('.emailbox', '.emailbox2').show(500);

	  
	  
  });
  
  $(".searchbox2a").click(function(){
	    $(".return").fadeIn();
	  });
	  
 
  $(".searchbox2").click(function(){
    $(".gobutton").fadeIn();
  });
  
  
  $(".gobutton").click(function(){
	$(".gobutton").fadeOut();
	$(".searchbox1").fadeOut();
	$(".searchbox2").fadeOut();
	$("#logoandtitle").fadeOut();
	$(".emailbox").fadeOut();
	$(".twitter_load").show(2000);
  	$(".button").fadeOut();
	  
  })
  
  $(".return").click(function(){
	$("#advancedsearch").hide(300);
	$(".gobutton").hide(300);
	$(".twitter_load").show(2000);
	
	  
	  
  })

});
 

</script>


</head>

<body>

<div id="advancedsearch">
<h1 align="center">Advanced Search</h1>

<div id="formwrapper">
<form action="hello.jsp" method="POST">
  <input class="fullwidth" type ="text" placeholder="All of these words" name="all">
<input class="fullwidth" type ="text" placeholder="These exact words" name="exact" >
<input class="fullwidth" type ="text" placeholder="Any of these words" name="any">
<input class="fullwidth" type ="text" placeholder="None of these words" name="none" >
<input class="fullwidth" type ="text" placeholder="Hashtags" name="hashtag" >
<div class="searchbox2a"><input type="radio" name="number" value="20">
	20
       <input type="radio" name="number" value="50">50<input type="radio" name="number" value="100">100
<input type="radio" name="number" value="200">200

	</div>
    
    <input class="emailbox2" type="text" name ="email" placeholder="(Optional)Enter your Email here">
    
<input type="submit" class="return" align="center" value="OK">
</form>
</div>
</div>




 
<div id="logoandtitle"><img class ="logo" src="Logo.png" width="489" height="368">
  <img class ="title" src="Untitled-4.png" width="1350" height="50" alt="Title"></div>
<div id ="Main">
  
 
  <form method="post" action="hello.jsp">
  <input class="searchbox1" type="text" name="query" placeholder="Enter your query here!"  value="" autocomplete="off">
  
  <button class="button" type="button">Advanced Search</button>
   
	<div class="searchbox2">
	<input type="radio" name="number" value="50">50
	<input type="radio" name="number" value="80">80
	<input type="radio" name="number" value="100">100
<input type="radio" name="number" value="200">200

	</div>
    
    
    
    <input class="emailbox" type="text" name ="email" placeholder="(Optional)Enter your Email here">
	<input class="gobutton" type="submit" value="GO!" />
</form>

  <div class="twitter_load">
   <img src="Twitter_Bird_Mascot.gif"  width="193" height="204" alt="Twitter_bird_loaing">
      <p> Fetching Tweets...</p>
      
      
    </div>

 	</div>
    
    

    
</div>







</body>
</html>