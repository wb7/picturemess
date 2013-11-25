<?php

include('php.class.php');

//set args to empty string
if(!isset($argv[1])){$argv[1] = "";}
if(!isset($argv[2])){$argv[2] = "";}

$pm = new picturemess(".");
$init = $pm->init();

// check init
if ($init){echo "albums.xml loaded.\n";}else{echo "Something went wrong! :("; die;}

// check if user wants to create desc files for each album
if ($argv[1] == "--create-files"){$pm->createFiles($argv[2]);}

// export html
if ($argv[1] == "--export"){$pm->create();}

?>
