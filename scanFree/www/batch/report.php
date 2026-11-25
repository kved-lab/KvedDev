
<form action="./report.php">
<INPUT type="text" name="recherche"/>
</form>

<?php
$page= $_GET['page'];
$recherche = $_GET['recherche'];
if ($recherche<>null) {
	echo ("<a href='./report.php'>Tout</a><br>");
	echo ('Recherche:'.$recherche."<br>");
	$splity=explode("+",$recherche);
	$id=0;
}

$con = mysql_connect("192.168.0.4","webuser","webuser");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

mysql_select_db("crawler", $con);
$sql='SELECT COUNT(1) AS total FROM url';
if ($recherche<>null) {
	$id=0;
	while( isset ( $splity[$id] ) ) {
	if ($id!=0) 
		$sql = $sql . " and content like '%".$splity[$id]."%'";
	else
		$sql = $sql . " where content like '%".$splity[$id]."%'";

	$id++;
	}

}

$retour_total=mysql_query($sql); 
$donnees_total=mysql_fetch_assoc($retour_total); 
$total=$donnees_total['total']; 
$messagesParPage=50;
$nombreDePages=ceil($total/$messagesParPage);

if(isset($_GET['page'])) 
{
     $pageActuelle=intval($page);
     
     if($pageActuelle>$nombreDePages) // Si la valeur de $pageActuelle (le numéro de la page) est plus grande que $nombreDePages...
     {
          $pageActuelle=$nombreDePages;
     }
}
else // Sinon
{
     $pageActuelle=1; // La page actuelle est la n°1    
}

$premiereEntree=($pageActuelle-1)*$messagesParPage; // On calcul la première entrée à lire



// La requête sql pour récupérer les messages de la page actuelle.
$sql='SELECT id,url,title,description,keyword FROM url ';

if ($recherche<>null) {
	$id=0;
	while( isset ( $splity[$id] ) ) {
	if ($id!=0) 
		$sql = $sql . " and content like '%".$splity[$id]."%'";
	else
		$sql = $sql . " where content like '%".$splity[$id]."%'";

	$id++;
	}
}

$sql=$sql.' ORDER BY id ASC LIMIT '.$premiereEntree.', '.$messagesParPage.'';
//echo $sql;

$retour_messages=mysql_query($sql);

echo "<table>";
  echo "<tr><td>Id</td><td>Url</td><td>Title</td><td>Description</td><td>Keywords</td></tr>";

while($row=mysql_fetch_assoc($retour_messages)) {
  echo "<tr><td><a href=\"#\" onclick=\"JavaScript:window.open('./content.php?id=".$row['id']."');\">" . $row['id']. "</a>" . " </td><td><a href=\"#\" onclick=\"JavaScript:window.open('http://".$row['url']."');\">" . $row['url']. "</a> </td><td>" . $row['title']. "</td><td> " . $row['description']. "</td><td> " . $row['keyword']."</td></tr>";
  }
echo "</table>";

echo '<p align="center">Page : '; //Pour l'affichage, on centre la liste des pages
for($i=1; $i<=$nombreDePages; $i++) //On fait notre boucle
{
     //On va faire notre condition
     if($i==$pageActuelle) //Si il s'agit de la page actuelle...
     {
          echo ' [ '.$i.' ] '; 
     }	
     else //Sinon...
     {
if ($recherche<>null) {
$paramrech='&recherche='.$recherche;
}else{
$paramrech='';
}
          if($i<5 || $i>$nombreDePages-4) 
		echo ' <a href="report.php?page='.$i.$paramrech.'">'.$i.'</a> ';
	   if($i==6 && $nombreDePages<>6) echo '...';	
     }
}
echo '</p>';
mysql_close($con);
?>