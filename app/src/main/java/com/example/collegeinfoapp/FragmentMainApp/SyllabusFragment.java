package com.example.collegeinfoapp.FragmentMainApp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.collegeinfoapp.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class SyllabusFragment extends Fragment {
   WebView webView;
   View view;

    public SyllabusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_syllabus, container, false);

        setView(view);
        return view;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setView(View view){
        webView = view.findViewById(R.id.webviewsyllabus);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        try {
            String url ="";
            String pdf ="https://firebasestorage.googleapis.com/v0/b/collegeinfo-8048e.appspot.com/o/Syllabus.pdf?alt=media&token=a63f0460-63ab-4809-9d95-dc4014a654ec";
            url= URLEncoder.encode(pdf,"UTF-8");
            webView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + url);
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}