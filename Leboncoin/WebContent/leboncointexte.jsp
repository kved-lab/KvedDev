<%@ page import="com.indexat.leboncoin.*" language="java" contentType="text/html; charset=ISO-8859-1"

 


    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>leboncoin mode texte</title>
</head>
<body>

<form action="./leboncointexte.jsp">

<input type="text" class="searchtext" id="searchtext" maxlength="500" size="30" value="<%=request.getParameter("q") %>" name="q">

<select class="search_category" id="search_category" onchange="SearchCrit(false, get_radio_value(f.st), 12, 's');showClothingSize('clothing_typed','null2');" name="c">
		<option value="0">Toutes catégories</option>
        <option value="1" style="background-color:#dcdcc3;">-- VEHICULES --</option><option value="2">Voitures</option>
    <option value="3">Motos</option>
    <option value="4">Caravaning</option>
    <option value="5">Utilitaires</option>
    <option value="6">Equipement Auto</option>
    <option value="44">Equipement Moto</option>
    <option value="50">Equipement Caravaning</option>
    <option value="7">Nautisme</option>
    <option value="51">Equipement Nautisme</option>
    <option value="8" style="background-color:#dcdcc3;">-- IMMOBILIER --</option><option value="9">Ventes immobilières</option>
    <option value="10">Locations</option>
    <option value="11">Colocations</option>
    <option value="12">Locations de vacances</option>
    <option value="13">Bureaux &amp; Commerces</option>
    <option value="14" style="background-color:#dcdcc3;">-- MULTIMEDIA --</option><option value="15">Informatique</option>
    <option value="43">Consoles &amp; Jeux vidéo</option>
    <option value="16">Image &amp; Son</option>
    <option value="17">Téléphonie</option>
    <option value="18" style="background-color:#dcdcc3;">-- MAISON --</option><option value="19">Ameublement</option>
    <option value="20">Electroménager</option>
    <option value="45">Arts de la table</option>
    <option value="39">Décoration</option>
    <option value="46">Linge de maison</option>
    <option value="21">Bricolage</option>
    <option value="52">Jardinage</option>
    <option value="22">Vêtements</option>
    <option value="53">Chaussures</option>
    <option value="47">Accessoires &amp; Bagagerie</option>
    <option value="42">Montres &amp; Bijoux</option>
    <option value="23">Equipement bébé</option>
    <option value="54">Vêtements bébé</option>
    <option value="24" style="background-color:#dcdcc3;">-- LOISIRS --</option><option value="25">DVD / Films</option>
    <option value="26">CD / Musique</option>
    <option value="27">Livres</option>
    <option value="28">Animaux</option>
    <option value="55">Vélos</option>
    <option value="29">Sports &amp; Hobbies</option>
    <option value="30">Instruments de musique</option>
    <option value="40">Collection</option>
    <option value="41">Jeux &amp; Jouets</option>
    <option value="48">Vins &amp; Gastronomie</option>
    <option value="56" style="background-color:#dcdcc3;">-- MATERIEL PROFESSIONNEL --</option><option value="57">Matériel Agricole</option>
    <option value="58">Transport - Manutention</option>
    <option value="59">BTP - Chantier Gros-oeuvre</option>
    <option value="60">Outillage - Matériaux 2nd-oeuvre</option>
    <option value="32">Équipements Industriels</option>
    <option value="61">Restauration - Hôtellerie</option>
    <option value="62">Fournitures de Bureau</option>
    <option value="63">Commerces &amp; Marchés</option>
    <option value="64">Matériel Médical</option>
    <option value="31" style="background-color:#dcdcc3;">-- EMPLOI &amp; SERVICES --</option><option value="33">Emploi</option>
    <option value="34">Services</option>
    <option value="35">Billetterie</option>
    <option value="49">Evénements</option>
    <option value="36">Cours particuliers</option>
    
		<option value="37" style="background-color:#dcdcc3;">--</option><option value="38">Autres</option>
    
        </select>


<input type="submit" >
</form>


<%



AnnonceParser p = new AnnonceParser();
String url = "http://www.leboncoin.fr/motos/offres/ile_de_france/paris/?f=a&th=1";
String query=null;
if (request.getParameter("q")==null)
	query = ""; else query=request.getParameter("q");
if (request.getParameter("c")!=null)
	query=query+"&c="+request.getParameter("c");

%>

<%=p.report(url, query )%>

</body>
</html>