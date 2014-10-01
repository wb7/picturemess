<?php

class picturemess {

	private $dir; //root dir
	private $content; //Content of XML file (albums)
	private $xml; //simple xml
	private $gallerytitle;
	private $version;

	public function __construct($dir = ".", $title = "picturemess", $version = "php-git")
	{

		$this->$dir = $dir;
		$this->gallerytitle = $title;
		$this->version = $version;

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
	
		$xmlfile = explode("/", $path);
		$xmlfile = $this->dir . "desc/" . $xmlfile[count($xmlfile) - 1] . ".xml";
		
		$xml = simplexml_load_file($xmlfile);
		
		//print_r($xml);
		
		foreach ($files as $row)
		{
			
			$desc = "";
			
			foreach($xml->file as $tmp){
				
				if ($tmp->filename == $row){$desc = $tmp->description;}
				
			}
			
			$file .= "  <file>\r\n";
			$file .= "    <filename>" . $row . "</filename>\r\n";
			$file .= "    <description>" . $desc . "</description>\r\n";
			$file .= "  </file>\r\n";
			
			$desc = "";
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
		$file = str_replace("{VERSION}", $this->version, $file);
		
		return $file;
	
	}

	private function getIncludes()
	{
		
		$file = file_get_contents($this->dir . "tpl/inc.tpl");
		
		return $file;
	
	}

	private function templateStrings($tpl, $array)
	{
	
		$file = file_get_contents($this->dir . "tpl/" . $tpl . ".tpl");
		
		foreach($array as $k => $v)
		{
			$file = str_replace("{" . $k . "}", $v, $file);		
		}
	
		$file = str_replace("{INCLUDES}", $this->getIncludes(), $file);
		$file = str_replace("{FOOTER}", $this->getFooter(), $file);
		return $file;
	
	}

	public function create()
	{
	
		if(file_exists($this->dir . "output"))
		{
			$dir = scandir($this->dir . "output");

			unset($dir[0]);
			unset($dir[1]);

			foreach($dir as $row)
			{
				if(filetype($this->dir . "output/" . $row) == "dir")
				{
					$files = scandir($this->dir . "output/" . $row);
					unset($files[0]);
					unset($files[1]);
		
					foreach($files as $file)
					{
						if(filetype($this->dir . "output/" . $row . "/" . $file) == "dir")
						{
							$folder = scandir($this->dir . "output/" . $row . "/" . $file);
							unset($folder[0]);
							unset($folder[1]);
							foreach($folder as $fold)
							{
								unlink($this->dir . "output/" . $row . "/" . $file . "/" . $fold);				
							}
							rmdir($this->dir . "output/" . $row . "/" . $file);
						}
						else
						{
							unlink($this->dir . "output/" . $row . "/" . $file);						
						}
					}
					rmdir($this->dir . "output/" . $row);
				}else {
					unlink($this->dir . "output/" . $row);
				}
			}
			rmdir($this->dir . "output");
			echo "Old files deleted.\r\n";
		}	
	
		mkdir($this->dir . "output");	
	
		// create and copy html files
		$this->createHTML();	
		
		// create and copy image files
		$this->copyImages();
		
		// create and copy other included files
		$this->copyFiles();
		
		echo "Done.\r\n";
	}

	private function createHTML()
	{
	
		// START index file
		$index = "";
		
		foreach($this->xml as $row)
		{
			$array = array(
				'DESCRIPTION' => $row->description,
				'TITLE' => $row->title,
				'LINK' => $row->folder . ".html",
			);
			$index .= $this->templateStrings("index_tile", $array);
		}
		
		$index = array(
			'LIST' => $index,
			'GALLERYTITLE' => $this->gallerytitle,
		);
		$index = $this->templateStrings("index", $index);
		
		$handle = fopen($this->dir . "output/index.html", "w");
		fwrite($handle, $index);
		fclose($handle);
		echo "Wrote index.html.\r\n";
		// END index file
		
		// START gallery page
		foreach($this->xml as $row)
		{
			$pictures = "";
			
			$xml = simplexml_load_file($this->dir . "desc/" . $row->folder . ".xml");
			
			foreach($xml as $file)
			{
				$array = array(
					'FOLDER' => $row->folder,
					'FILENAME' => $file->filename,
					'DESCRIPTION' => $file->description,
				);

				$pictures .= $this->templateStrings("page_tile", $array);				
				
			}			
			
			$array = array(
				'TITLE' => $row->title,
				'DESCRIPTION' => $row->description,
				'PICTURES' => $pictures,
				'GALLERYTITLE' => $this->gallerytitle,
			);

			$page = $this->templateStrings("page", $array);
			
			$handle = fopen($this->dir . "output/" . $row->folder . ".html", "w");
			fwrite($handle, $page);
			fclose($handle);		
			echo "Wrote " . $row->folder . ".html.\r\n";
			
		}		
				
		
		// END gallery page		
		
	}

	private function copyImages()
	{
	
		if(!file_exists($this->dir . "output/images/"))
		{
			mkdir($this->dir . "output/images");		
		}
	
		if(!file_exists($this->dir . "output/thumbs/"))
		{
			mkdir($this->dir . "output/thumbs");		
		}
	
		foreach($this->xml as $row)
		{
			
			$xml = simplexml_load_file($this->dir . "desc/" . $row->folder . ".xml");
			
			foreach($xml as $file)
			{
				if(!file_exists($this->dir . "output/images/" . $row->folder))
				{
					mkdir($this->dir . "output/images/" . $row->folder);
					mkdir($this->dir . "output/thumbs/" . $row->folder);
					echo "Created directory output/images/" . $row->folder . ".\r\n";
				}

				copy($this->dir . "images/" . $row->folder . "/" . $file->filename, $this->dir . "output/images/" . $row->folder . "/" . $file->filename);
				echo "Copied image " . $row->folder . "/" . $file->filename . ".\r\n";
				
				$filename = $this->dir . "images/" . $row->folder . "/" . $file->filename;
				$file_info = pathinfo($filename);
				$width = 150;
				list($width_orig, $height_orig) = getimagesize($filename);
				$ratio_orig = $width_orig/$height_orig;
				$height = $width/$ratio_orig;

				$image_p = imagecreatetruecolor($width, $height);

				if($file_info['extension'] == "jpeg" OR $file_info['extension'] == "jpg" OR $file_info['extension'] == "JPG" OR $file_info['extension'] == "JPEG")
				{
					$image = imagecreatefromjpeg($filename);
				}
				elseif($file_info['extension'] == "png" OR $file_info['extension'] == "PNG")
				{
					$image = imagecreatefrompng($filename);
				}
			
				imagecopyresampled($image_p, $image, 0, 0, 0, 0, $width, $height, $width_orig, $height_orig);
				imagejpeg($image_p, $this->dir . "output/thumbs/" . $row->folder . "/" . $file->filename, 95);
			
			}
		
		}
	
	}

	private function copyFiles()
	{

		$dir = scandir($this->dir . "inc");
		unset($dir[0]);
		unset($dir[1]);
		
		foreach($dir as $row)
		{
			if(filetype($this->dir . "inc/" . $row) != "dir")
			{
				copy($this->dir . "inc/" . $row, $this->dir . "output/" . $row);
				echo "Copied " . $row . ".\r\n";
			}
			else
			{
				mkdir($this->dir . "output/" . $row);
				$folder = scandir($this->dir . "inc/" . $row);
				unset($folder[0]);
				unset($folder[1]);
				foreach($folder as $fold)
				{
					
					if(filetype($this->dir . "inc/" . $row . "/" . $fold) == "file") {
					copy($this->dir . "inc/" . $row . "/" . $fold, $this->dir . "output/" . $row . "/" . $fold);
					echo "Copied " . $row . "/" . $fold . ".\r\n";		
					}
					else
					{
						$temp = scandir($this->dir . "inc/" . $row . "/" . $fold);
						unset($temp[0]);
						unset($temp[1]);

						mkdir($this->dir . "output/" . $row . "/" . $fold);					
					
						foreach($temp as $value)
						{
							copy($this->dir . "inc/" . $row . "/" . $fold . "/" . $value, $this->dir . "output/" . $row . "/" . $fold . "/" . $value);					
							echo "Copied " . $row . "/" . $fold . "/" . $value . ".\r\n";
						}
					}
				}
			}
		}
	}

	public function createAlbum($folder) {
	
		if (!file_exists($this->dir . "images/" . $folder)){
			$file = file_get_contents($this->dir . "albums.xml");
			$file = str_replace("</albums>", "", $file);
		
			$block = "  <album>\r\n";
			$block .= "    <title>New Album - " . $folder . "</title>\r\n";
			$block .= "    <folder>" . $folder . "</folder>\r\n";
			$block .= "    <date>" . date("d F Y") . "</date>\r\n";
			$block .= "    <description>An awesome album with great pictures.</description>\r\n";
			$block .= "  </album>\r\n";
		
			$block .= "</albums>";

			$handle = fopen($this->dir . "albums.xml", "w");
			fwrite($handle, $file . $block);
			
			mkdir($this->dir . "images/" . $folder);
			echo "Created folder and album '" . $folder . "' successful.\r\n";
		}
		else {
			echo "Folder already exists!\r\n";
		}
	}

	public function removeAlbum($folder) {

                if (file_exists($this->dir . "images/" . $folder)) {
			echo "Will remove $folder.\r\n";
			unlink($this->dir . "desc/" . $folder . ".xml");
			$temp = scandir($this->dir . "images/" . $folder);
			unset($temp[0]);
			unset($temp[1]);

			foreach($temp as $tmp) {
				unlink($this->dir . "images/" . $folder . "/" . $tmp);
				echo "Removed " . $tmp . ".";
			}

			rmdir($this->dir . "images/" . $folder);

                        $file = file_get_contents($this->dir . "albums.xml");
			$xml = simplexml_load_string($file);
			$block = "<albums>\r\n"; // Contains the string of the new file

			foreach($xml->album as $album) {
				If ($album->folder != $folder) {
					$block .= "  <album>\r\n";
                        		$block .= "    <title>" . $album->title . "</title>\r\n";
                        		$block .= "    <folder>" . $album->folder . "</folder>\r\n";
                        		$block .= "    <date>" . $album->date . "</date>\r\n";
                        		$block .= "    <description>" . $album->description . "</description>\r\n";
                        		$block .= "  </album>\r\n";
				}
			}

			$block .= "</albums>\r\n";
			$handle = fopen($this->dir . "albums.xml", "w");
			fwrite($handle, $block);
			echo "Wrote new albums.xml";
			fclose($handle);
		}

	}

}
?>
