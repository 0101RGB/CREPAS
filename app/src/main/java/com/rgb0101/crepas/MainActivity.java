package com.rgb0101.crepas;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends FragmentActivity {
    // for fragment manage
    private FragmentTransaction mFrgTransaction= null;
    // fragments
    private MainFragment mMain= null;
    private FindMusicFragment mFindMusic= null;

    private int mCurrentFrg= -1;

    // for backpressed
    public long backpressedTime;
    public Toast toast= null;

    // for password
    private String mCrepasOutput= "";
    private String mCurrentSelected= "";
    private byte[] fromMusic= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    protected void onResume(){
        super.onResume();
        makeView();
        moveFragment(Constants.MAIN);
    }
    @Override
    protected void onPause(){
        super.onPause();
    }

    private void makeView(){
        mMain= new MainFragment();
    }
    public void moveFragment(int type){
        mCurrentFrg= type;

        mFrgTransaction= getSupportFragmentManager().beginTransaction();
        switch(type){
            case Constants.MAIN: mFrgTransaction.replace(R.id.container, mMain); break;
            case Constants.FINDMUSIC:
                if(mFindMusic == null) mFindMusic= new FindMusicFragment();
                mFrgTransaction.replace(R.id.container, mFindMusic);
                break;
            default:
        }
        mFrgTransaction.commit();
    }

    public void selectFile(String file) {
        mCurrentSelected= file;
        try {
            File f = new File(mCurrentSelected);
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(f));
            fromMusic= new byte[(int)f.length()];
            buf.read(fromMusic, 0, fromMusic.length);
            buf.close();
        } catch (FileNotFoundException e) { e.printStackTrace();
        } catch (IOException e) { e.printStackTrace(); }
        mMain.setSelectedFile(mCurrentSelected.substring(mCurrentSelected.lastIndexOf('/')));
    }
    public byte[] getFromMusic(){ return fromMusic; }
    public void finishCREPAS(String pw){ mCrepasOutput= pw; }

    public void copyPW(boolean onlyCopy){
        if(mCrepasOutput != null && mCrepasOutput.length() > 0){
            if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(mCrepasOutput);
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Clip",mCrepasOutput);
                clipboard.setPrimaryClip(clip);
            }
            Toast.makeText(getApplicationContext(), getResources().getString(onlyCopy ? R.string.copiedPW : R.string.generatedPW), Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.notYetGenerated), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis()-backpressedTime < 2000) {
            toast.cancel();
            finish();
        }
        else {
            if(mCurrentFrg == Constants.FINDMUSIC) {
                moveFragment(Constants.MAIN);
                return ;
            }
            if(toast == null) toast= Toast.makeText(getApplicationContext(), getResources().getString(R.string.backpressedString), Toast.LENGTH_SHORT);
            toast.show();
            backpressedTime= System.currentTimeMillis();
        }
    }
}
