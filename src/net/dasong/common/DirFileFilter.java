package net.dasong.common;

import java.io.File;
import java.io.FileFilter;

public class DirFileFilter implements FileFilter
{

    @Override
    public boolean accept(File dir)
    {
        // TODO Auto-generated method stub
        return dir.isDirectory();
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // TODO Auto-generated method stub

    }

}
