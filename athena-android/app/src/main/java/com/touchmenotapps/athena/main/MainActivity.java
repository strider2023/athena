package com.touchmenotapps.athena.main;

import android.animation.Animator;
import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.touchmenotapps.athena.R;
import com.touchmenotapps.athena.imageRecognition.ImageRecognitionActivity;
import com.touchmenotapps.athena.main.adapters.ChatListAdapter;
import com.touchmenotapps.athena.main.dao.MessageDao;
import com.touchmenotapps.athena.main.dao.enums.ChatSelectListener;
import com.touchmenotapps.athena.ocr.OcrCaptureActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements ChatSelectListener, RecognitionListener {

    private static final int RC_OCR_CAPTURE = 9003;
    private static final int IMAGE_CAPTURE = 9004;
    private static final String TAG = "OcrActivity";

    @BindView(R.id.chatList)
    RecyclerView chatList;
    @BindView(R.id.options_view)
    CardView optionsView;
    @BindView(R.id.options_button_container)
    LinearLayout optionsButtons;
    @BindView(R.id.userInput)
    AppCompatEditText userInput;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private boolean flag = true;

    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;
    private boolean isListening = false;

    private List<MessageDao> messageDaos = new ArrayList<>();
    private ChatListAdapter chatListAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        optionsView.setVisibility(View.INVISIBLE);

        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        mSpeechRecognizer.setRecognitionListener(this);

        MessageDao message = new MessageDao(MainActivity.this,
                userInput.getText().toString().trim(), true);
        message.setUrl("https://developer.android.com/training/camera/photobasics.html");
        messageDaos.add(message);

        chatListAdapter = new ChatListAdapter(this);
        linearLayoutManager = new LinearLayoutManager(this);
        chatList.setLayoutManager(linearLayoutManager);
        chatList.setAdapter(chatListAdapter);
        chatListAdapter.setData(messageDaos);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.show_input_options:
                showOptionsView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.search_by_text_scan)
    public void launchOCRCapturer() {
        showOptionsView();
        Intent intent = new Intent(this, OcrCaptureActivity.class);
        intent.putExtra(OcrCaptureActivity.AutoFocus, true);
        intent.putExtra(OcrCaptureActivity.UseFlash, false);
        startActivityForResult(intent, RC_OCR_CAPTURE);
    }

    @OnClick(R.id.search_by_image)
    public void launchImageCapture() {
        showOptionsView();
        Intent intent = new Intent(this, ImageRecognitionActivity.class);
        startActivityForResult(intent, IMAGE_CAPTURE);
    }

    @OnClick(R.id.search_by_audio)
    public void onVoiceInput() {

    }

    @OnClick(R.id.sendInput)
    public void enterData() {
        if(userInput.getText().toString().trim().length() > 0) {
            MessageDao message = new MessageDao(MainActivity.this,
                    userInput.getText().toString().trim(), true);
            messageDaos.add(message);
            chatListAdapter.setData(messageDaos);
            userInput.getEditableText().clear();
            //TODO API call here
        }
    }

    public void showOptionsView() {
        int cx = (optionsView.getLeft() + optionsView.getRight());
        int cy = optionsView.getTop();
        int radius = Math.max(optionsView.getWidth(), optionsView.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(optionsView, cx, cy, 0, radius);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(400);

        Animator animator_reverse = ViewAnimationUtils.createCircularReveal(optionsView, cx, cy, radius, 0);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(400);

        if (flag) {
            optionsView.setVisibility(View.VISIBLE);
            anim.start();
            flag = false;
        } else {
            animator_reverse.addListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animator) { }

                @Override
                public void onAnimationEnd(Animator animator) {
                    optionsView.setVisibility(View.INVISIBLE);
                    flag = true;
                }

                @Override
                public void onAnimationCancel(Animator animator) { }

                @Override
                public void onAnimationRepeat(Animator animator) { }
            });
            animator_reverse.start();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RC_OCR_CAPTURE:
                if (resultCode == CommonStatusCodes.SUCCESS) {
                    if (data != null) {
                        String text = data.getStringExtra(OcrCaptureActivity.TextBlockObject);
                        MessageDao message = new MessageDao(MainActivity.this, text, true);
                        messageDaos.add(message);
                        chatListAdapter.setData(messageDaos);
                        Log.d(TAG, "Text read: " + text);
                    } else {
                        Snackbar.make(chatList, R.string.ocr_failure, Snackbar.LENGTH_LONG).show();
                        Log.d(TAG, "No Text captured, intent data is null");
                    }
                } else {
                    Snackbar.make(chatList, String.format(getString(R.string.ocr_error),
                            CommonStatusCodes.getStatusCodeString(resultCode)), Snackbar.LENGTH_LONG).show();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    public void onURLSelection(String url) {

    }

    @Override
    public void onReadyForSpeech(Bundle params) { }

    @Override
    public void onBeginningOfSpeech() { }

    @Override
    public void onRmsChanged(float rmsdB) { }

    @Override
    public void onBufferReceived(byte[] buffer) { }

    @Override
    public void onEndOfSpeech() { }

    @Override
    public void onError(int error) {
        isListening = false;
        mSpeechRecognizer.stopListening();
        switch (error) {
            case SpeechRecognizer.ERROR_NETWORK:
                Snackbar.make(chatList, "Unable to connect to the internet.", Snackbar.LENGTH_LONG).show();
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                Snackbar.make(chatList, "Unable to connect to the internet.", Snackbar.LENGTH_LONG).show();
                break;
            default:
                Snackbar.make(chatList, "Something went wrong. Please try again!", Snackbar.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if(matches.size() > 0) {
            for(String value : matches) {
                Log.d("Test", "Match : " + value);
            }
        }
        mSpeechRecognizer.stopListening();
        isListening = false;
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        ArrayList<String> matches = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if(matches.size() > 0) {
            for(String value : matches) {
                Log.d("Test", "Partial Match : " + value);
                userInput.setText(userInput.getText().toString().trim() + " " + value);
            }
        }
    }

    @Override
    public void onEvent(int eventType, Bundle params) { }
}
