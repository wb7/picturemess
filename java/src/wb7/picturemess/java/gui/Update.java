/*
 *  Copyright 2013, 2014 vilaureu
 *   
 *     This file is part of picturemess.
 *
 *  picturemess is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  picturemess is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with picturemess.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package wb7.picturemess.java.gui;

public class Update {

	private VariablesCollection variablesCollection;

	public Update(VariablesCollection variablesCollection) {
		
		this.variablesCollection = variablesCollection;
		
	}
	
	public boolean update() {
		
		boolean ret = variablesCollection.initGui.init();
		
		variablesCollection.path = variablesCollection.initGui.path;
		variablesCollection.width = variablesCollection.initGui.width;
		variablesCollection.fileDescrMap = variablesCollection.initGui.fileDescrMap;
		variablesCollection.folderTitleMap = variablesCollection.initGui.folderTitleMap;
		
		variablesCollection.paneController.update();
		
		if (ret) {
			System.out.println("update successfully.");
		} else {
			System.out.println("update failed.");
		}
		
		return ret;
		
	}
	
}
