class User{
    constructor(pub_key,amount)
    {
        this.pub_key = pub_key;
        this.amount = amount;
    }
}

$(document).ready(function(){
  
    var channels = ["freecodecamp", "storbeck", "terakilobyte", "habathcx","RobotCaleb","thomasballinger","noobs2ninjas","beohoff" ,"medrybw", "monstercat"];
    
    var str = 'https://api.twitch.tv/kraken/streams/';
    
    
  
    channels.forEach(function(n){
        
      $.getJSON(str+n , function(json){
      
        if(json.stream != null)
        {
           console.log();
          var html = "<a href=\""+json.stream.channel.url+"\"><div class=\"jumbotron a b\" >"
          
          html+=("<img src=\""+(json.stream.channel.logo+"")+"\" class=\"img-circle img-responsive images\" height=\"75\" width=\"75\">");
          html+=("<h3 class=\"images\">"+(json.stream.channel.display_name+"")+"</h3>");
          html+=("<p>"+json.stream.game+"</p>");
          html+="</div>"
          $("#channels").append(html);
          
        }
        else
          {
            $.getJSON(json._links.channel , function(json1){
              
              console.log(json1);
               var html = "<a href=\""+json1.url+"\"><div class=\"jumbotron c b\" >"
          if(json1.logo!=null)
          {
          html+=("<img src=\""+(json1.logo+"")+"\" class=\"img-circle img-responsive images\" height=\"75\" width=\"75\">");
          }
              else
                {
                  html+=("<img src=\"https://i.warosu.org/data/g/img/0488/82/1436268496494.png\" class=\"img-circle img-responsive images\" height=\"75\" width=\"75\">");
                  
                }
          html+=("<h3 class=\"images\">"+(json1.display_name+"")+"</h3>");
          html+="</div>"
          $("#channels").append(html);
              
            });
          }
      
    });
      
    });
    
    
  });