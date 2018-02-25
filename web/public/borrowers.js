//Read in public keys and amounts
var pub_keys = ["12345", "59202", "83490"];
var amounts = ["50", "23", "90"];


//Print out pub_keys and amounts
for(var i = 0; i < 3; i++)
{
    document.write("<div class=\"w3-light-gray w3-container\">");
    document.write("<b>Public key:\t</b>"+pub_keys[i]+ "<br>");
    document.write("<b>Amount:\t</b>"+amounts[i]+"<br>");
    document.write("</div>");
    document.write("<br>");
}