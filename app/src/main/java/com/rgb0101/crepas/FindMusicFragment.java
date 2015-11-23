package com.rgb0101.crepas;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by noirCynical on 2015. 11. 21..
 */
public class FindMusicFragment extends Fragment{
    private View wholeView= null;

    private String mRootDir= null;
    private String mCurrentDir= null;
    private ArrayList<FileListItem> mCurrentFileList= null;

    private ListView mFileListView= null;
    private FileListAdapter mFileListAdapter= null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        wholeView= inflater.inflate(R.layout.fragment_findmusic, null);
        return wholeView;
    }
    @Override
    public void onResume(){
        super.onResume();
        makeView();
    }
    @Override
    public void onPause(){
        super.onPause();
    }

    private void makeView(){
        mFileListView= (ListView)wholeView.findViewById(R.id.listFragmentFindMusic);

        if(mCurrentDir == null) getFileList();
        initFileList();
        viewFileList(getFileList(mRootDir));
    }

    private void getFileList() {
        if (!sdExist()) getActivity().finish();

        mCurrentDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        mRootDir= Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    private boolean sdExist(){
        String ext= Environment.getExternalStorageState();
        if(!ext.equals(Environment.MEDIA_MOUNTED)){
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.noSDCardText), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private ArrayList<FileListItem> getFileList(String path){
        File file= new File(path);
        if(!file.isDirectory()) return null;
        mCurrentDir= path;

        ArrayList<FileListItem> temp= new ArrayList<FileListItem>();
        File[] files= (new File(path)).listFiles();
        for(int i=0; i<files.length; i++){
            File tempfile= files[i];
            if(tempfile.getName().contains("com.")) continue;
            if(tempfile.getName().charAt(0) == '.') continue;
            if(tempfile.isDirectory()) temp.add(new FileListItem(true, tempfile.getName()+"/"));
            else temp.add(new FileListItem(false, tempfile.getName()));
        }

        return temp;
    }

    private void initFileList(){
        mCurrentFileList= new ArrayList<FileListItem>();
        mFileListAdapter= new FileListAdapter(getActivity(), R.layout.item_filelist, mCurrentFileList);

        mFileListView.setAdapter(mFileListAdapter);
        mFileListView.setOnItemClickListener(itemClick);
    }

    private String getAbsolutePath(String file){
        if(file.equalsIgnoreCase("..") || file.equalsIgnoreCase("../")) return mCurrentDir.substring(0, mCurrentDir.lastIndexOf("/"));
        else return mCurrentDir+"/"+file;
    }

    private void viewFileList(ArrayList<FileListItem> list){
        if(list == null) return ;
        mCurrentFileList.clear();

        if(mRootDir.length() < mCurrentDir.length()) mCurrentFileList.add(new FileListItem(true, ".."));
        for(int i=0; i<list.size(); i++) mCurrentFileList.add(list.get(i));
        mFileListAdapter.notifyDataSetChanged();
    }

    AdapterView.OnItemClickListener itemClick= new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            String selectedItem= mCurrentFileList.get(position).getFilename();
            String path= getAbsolutePath(selectedItem);
            if(mCurrentFileList.get(position).isDir()) viewFileList(getFileList(path));
            else if(selectedItem.substring(selectedItem.length()-4).equalsIgnoreCase(".mp3")) {
                ((MainActivity)getActivity()).selectFile(path);
                ((MainActivity)getActivity()).moveFragment(Constants.MAIN);
            }
            else Toast.makeText(getActivity(), getResources().getString(R.string.notMP3Selected), Toast.LENGTH_SHORT).show();
        }
    };
}