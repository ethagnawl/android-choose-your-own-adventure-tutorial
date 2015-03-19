package com.pdoherty926.interactivestory.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pdoherty926.interactivestory.R;
import com.pdoherty926.interactivestory.model.Page;
import com.pdoherty926.interactivestory.model.Story;


public class StoryActivity extends Activity {

    public static final String TAG = StoryActivity.class.getSimpleName();
    private Story mStory = new Story();
    private ImageView mImageView;
    private TextView mTextView;
    private Button mChoice1;
    private Button mChoice2;
    private String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        Intent intent = getIntent();
        mName = intent.getStringExtra(getString(R.string.key_name));

        if (mName== null) {
            mName = "Friend";
        }

        Log.d(TAG, mName);

        mImageView = (ImageView) findViewById(R.id.storyImageView);
        mTextView = (TextView) findViewById(R.id.storyTextView);
        mChoice1 = (Button) findViewById(R.id.choiceButton1);
        mChoice2 = (Button) findViewById(R.id.choiceButton2);

        loadPage(0);

    }

    private void loadPage(int pageNumber) {
        final Page page = mStory.getPage(pageNumber);

        Drawable drawable = getResources().getDrawable(page.getImageId());
        mImageView.setImageDrawable(drawable);

        String pageText = page.getText();
        pageText = String.format(pageText, mName);
        mTextView.setText(pageText);

        if (page.isFinal()) {

            mChoice1.setVisibility(View.INVISIBLE);
            mChoice2.setText("Play Again");
            mChoice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

        } else {

            mChoice1.setText(page.getChoice1().getText());

            mChoice1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int nextPage = page.getChoice1().getNextPage();
                    loadPage(nextPage);
                }
            });

            mChoice2.setText(page.getChoice2().getText());

            mChoice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int nextPage = page.getChoice2().getNextPage();
                    loadPage(nextPage);
                }
            });

        }

    }

}
