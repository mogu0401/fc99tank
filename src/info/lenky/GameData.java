/***************************************************************************
 *   Copyright (C) 2013~2013 by Lenky0401                                  *
 *   Email: lenky0401@gmail.com                                            *
 *   WebSite: http://lenky.info/                                           *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 *   This program is distributed in the hope that it will be useful,       *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   You should have received a copy of the GNU General Public License     *
 *   along with this program; if not, write to the                         *
 *   Free Software Foundation, Inc.,                                       *
 *   51 Franklin St, Fifth Floor, Boston, MA 02110-1301, USA.              *
 ***************************************************************************/

package info.lenky;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.os.Environment;

public class GameData {

	public static boolean saveData(String fileName, int data[]) 
	{
		int i;
		FileOutputStream fos = null;
		DataOutputStream dos = null;
		
		try {
			if (Environment.getExternalStorageState() != null && 
					Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
			{ 
				String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
				File path = new File(rootPath + "/fc99tank");  
				File file = new File(rootPath + "/fc99tank/" + fileName);
				if (!path.exists())
					path.mkdirs();
				if (!file.exists())
					file.createNewFile();
				fos = new FileOutputStream(file);
			} else {
				if ((fos = MainActivity.instance.openFileOutput(fileName, Context.MODE_PRIVATE)) == null)
					return false;
			}
			dos = new DataOutputStream(fos);
			dos.writeInt(data.length);
			for (i = 0; i < data.length; i ++)
				dos.writeInt(data[i]);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
					fos = null;
				}
				if (dos != null) {
					dos.close();
					dos = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	private static int[] readData(InputStream in) 
	{
	    int i, count;
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(in);
            count = dis.readInt();
            int data[] = new int[count];
            for (i = 0; i < count; i ++)
                data[i] = dis.readInt();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dis != null) {
                    dis.close();
                    dis = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
	}
	
	public static int[] readData(int fileResId) 
    {
	    return readData(MainActivity.instance.getResources().openRawResource(fileResId));  
    }
	
	public static int[] readData(String fileName) 
	{
		FileInputStream fis = null;
		try {
			if (Environment.getExternalStorageState() != null && 
					Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
			{ 
				String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
				File file = new File(rootPath + "/fc99tank/" + fileName);
				if (!file.exists())
					return null;
				fis = new FileInputStream(file);
			} else {
				if ((fis = MainActivity.instance.openFileInput(fileName)) == null)
					return null;
			}
			return readData(fis);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
					fis = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
