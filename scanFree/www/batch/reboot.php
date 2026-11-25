<h1>reboot system</h1>
<?php
echo '<pre>';
$output = shell_exec('shutdown -r now');
echo $output;
echo '</pre>';
?>

