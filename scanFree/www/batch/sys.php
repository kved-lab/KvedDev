<a href="./start.php">Start crawler</a>&nbsp;
<a href="./stop.php">Stop crawler</a>&nbsp;
<a href="./reboot.php">Reboot</a>&nbsp;

<a href="../sphider/search.php">Search</a>&nbsp;
<a href="../sphider/admin/admin.php">Admin Indexation</a>&nbsp;
<?php

echo '<h1>Process</h1>
<p><pre>';
$output = shell_exec('ps2');
 echo $output.'</pre></p>';

echo '<h1>Mem</h1><p><pre>';
$output = shell_exec('free -mt');
 echo $output.'</pre></p>';

echo '<h1>Disk</h1><p><pre>';
$output = shell_exec('df');
 echo $output.'</pre></p>';

echo '<h1>Last crawl</h1><p><pre>';
$output = shell_exec('tail -100 nohup.out');
 echo $output.'</pre></p>';

echo '<h1>Last index</h1><p><pre>';
$output = shell_exec('tail -100 /home/apache/classes/com/kved/main/indexSphider.log');
 echo $output.'</pre></p>';


?>

