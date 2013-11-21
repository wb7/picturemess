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

	private function getFooter()
	{
	
		$time = date("d.m.Y H:i");
		$year = date("Y");
		
		$file = file_get_contents($this->dir . "tpl/footer.tpl");
		$file = str_replace("{YEAR}", $year, $file);
		$file = str_replace("{TIME}", $time, $file);
		
		return $file;
	
	}

	private function templateStrings($tpl, $array)
	{
	
		$file = file_get_contents($this->dir . "tpl/" . $tpl . ".tpl");
		
		foreach($array as $k => $v)
		{
			$file = str_replace("{" . $k . "}", $v, $file);		
		}
	
		$file = str_replace("{FOOTER}", $this->getFooter(), $file);
		return $file;
	
	}

	public function create()
	{
	
		// create and copy html files
		$this->createHTML();	
		
		// create and copy image files
		
		// create and copy other included files
	
	}

	private function createHTML()
	{
	
		// START index file
		$index = "";
		
		foreach($this->xml as $row)
		{
			$array = array('DESCRIPTION' => $row->description,
				'TITLE' => $row->title,
			);
			$index .= $this->templateStrings("index_tile", $array);
		}
		
		$index = array('LIST' => $index);
		$index = $this->templateStrings("index", $index);
		
		$handle = fopen($this->dir . "output/index.html", "w");
		fwrite($handle, $index);
		fclose($handle);
		echo "Wrote index.html.\r\n";
		// END index file
		
	}

}

?>
