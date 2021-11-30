package com.xingin.recyclerviewtest;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.xingin.recyclerviewtest.Fragment.FragmentOne;
import com.xingin.recyclerviewtest.Fragment.FragmentThree;
import com.xingin.recyclerviewtest.Fragment.FragmentTwo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private InputStream resourceAsStream;
    private ViewPager viewPager;
    private List<Fragment> fragments;
    private List<String> strings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        viewPager = (ViewPager) findViewById(R.id.vp);
        fragments = Arrays.asList(new FragmentOne(), new FragmentTwo(), new FragmentThree());
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager(), fragments));
        strings = Arrays.asList("one", "two", "three");
        //  testIOStream();
    }

    private class MyAdapter extends FragmentPagerAdapter {
        List<Fragment> data;

        public MyAdapter(FragmentManager fm, List<Fragment> data) {
            super(fm);
            this.data = data;

        }

        @Override
        public Fragment getItem(int position) {
            return data.get(position);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return strings.get(position);
        }
    }


    private void testIOStream() {
        Drawable drawable = getDrawable(R.drawable.ic_launcher_background);
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        File filesDir = this.getFilesDir();
        File file = new File(String.format("%s/%s.jpeg", filesDir.getPath(), "a"));
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
