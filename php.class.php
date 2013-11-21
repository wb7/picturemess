<?php

class picturemess {

	private $dir; //root dir
	private $content; //Content of XML file (albums)
	private $xml; //simple xml

	public function __construct($dir = ".")
	{

		$this->$dir = $dir;

	}

	public function init()
	{

		if (@fopen($this->dir . "albums.xml", "r"))
		{
			$this->content = file_get_contents($this->dir . "albums.xml");
			$this->xml = simplexml_load_string($this->content);
			return true;
		}
		else
		{
			return false;
		}

	}

	public function createFiles($album = "--all")
	{
		
		foreach($this->xml as $row)
		{
			if ($album == "--all")
			{
				$content = "<album>\r\n";
				$content .= $this->createDescXML($this->dir . "images/" . $row->folder);
				$content .= "</album>";

				if(@$handle = fopen($this->dir . "desc/" . $row->folder . ".xml", "w"))
				{
					fwrite($handle, $content);
					fclose($handle);
					echo "(Over-)wrote description files for album \"" . $row->title . "\".\r\n";			
				}				
				
			}
			else
			{
				if($row->folder == $album)
				{
					$content = "<album>\r\n";
					$content .= $this->createDescXML($this->dir . "images/" . $row->folder);
					$content .= "</album>";
				
					if(@$handle = fopen($this->dir . "desc/" . $row->folder . ".xml", "w"))
					{
						fwrite($handle, $content);
						fclose($handle);
						echo "(Over-)wrote description files for album \"" . $row->title . "\".\r\n";			
					}				
				}
			}
		}
	
		if ($album == ""){echo "Please give one album or --all as argument.\r\n";}
	
	}

	private function createDescXML($path)
	{
	
		$files = scandir($path);
		unset($files[0]);
		unset($files[1]);

		$file = "";

		foreach ($files as $row)
		{
			$file .= "  <file>\r\n";
			$file .= "    <filename>" . $row . "</filename>\r\n";
			$file .= "    <description></description>\r\n";
			$file .= "  </file>\r\n";
		}
		
		return $file;

	}

	public function createHTML()
	{
	
		// index file	
	
		$index_file = file_get_contents($this->dir . "tpl/index.tpl");
		$index = "";		
		
		foreach($this->xml as $row)
		{
			$index .= $row->title . " - " . $row->desc . "<br />";		
		}
	
		$index_file = str_replace("{LIST-DESC}", $index, $index_file);
		echo $index_file;
	
	}

}

?>
