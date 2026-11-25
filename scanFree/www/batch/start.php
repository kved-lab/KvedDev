<?php
$output = shell_exec('nohup ./start.sh >nohup.out');
header('Location: ./sys.php');
?>

