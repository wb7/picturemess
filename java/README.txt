How to use the java part
===========

Make sure that you are in the java directory.

Compile for only core:
	md bin
	javac -d ./bin/ @files_to_compile_core.txt
Start for only core:
	cd bin
	java Main_core <arguments>
	
Compile for gui:
	md bin
	javac -d ./bin/ @files_to_compile_gui.txt
Start for only core:
	cd bin
	java bin\Main_gui
