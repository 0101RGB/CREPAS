package com.rgb0101.crepas;

/**
 * Created by noirCynical on 2015. 11. 21..
 */
public class FileListItem {
    private boolean isDir= false;
    private String mFilename= null;

    public FileListItem(){}
    public FileListItem(boolean isdir, String file){
        isDir= isdir;
        mFilename= file;
    }

    public void setIsDir(boolean isdir){ isDir= isdir; }
    public boolean isDir(){ return isDir; }

    public void setFilename(String file){ mFilename= file; }
    public String getFilename(){ return mFilename; }
}