<?php

include('php.class.php');

$pm = new picturemess(".");
$init = $pm->init();

// check init
if ($init){echo "albums.xml loaded.\n";}else{echo "Something went wrong! :("; die;}

// check if user wants to create desc files for each album
if ($argv[1] == "--create-files"){$pm->createFiles();}
?>
