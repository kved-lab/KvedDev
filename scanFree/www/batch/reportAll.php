
<form action="./report.php">
<INPUT type="text" name="recherche"/>
</form>

<?php


$con = mysql_connect("192.168.0.4","webuser","webuser");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

mysql_select_db("crawler", $con);
$lettre = $_GET['lettre'];

if ($lettre<>null) 
	$sql="SELECT url FROM url where url like '".$lettre."%'";
else
	$sql="SELECT url FROM url";


$retour_messages=mysql_query($sql);
$fichier = fopen('/home/apache/'.$lettre.'.html','w+');

while($row=mysql_fetch_assoc($retour_messages)) {
//  echo "<a href=http://".$row['url'].">".$row['url']."</a><br>";
  fputs($fichier,"<a href=".$row['url'].">".$row['url']."</a><br>");
  }

fclose($fichier);
mysql_close($con);
?>