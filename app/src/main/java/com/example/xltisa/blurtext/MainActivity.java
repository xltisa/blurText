package com.example.xltisa.blurtext;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Author: Rewnosor Xltisa
 * This app is my Android curriculum design
 * Instructor: wuzhihui
 * Author own all of the UI design
 * Thanks a lot for the part of animation @小瓶盖_tl
 * post: http://blog.csdn.net/qq_25193681/article/details/52005375
 */

public class MainActivity extends Activity {

    private View inputLayout;
    private TextView btnLogin, btnCannel;
    private View progress;
    private float mWidth, mHeight;
    private LinearLayout mName, mPsw;
    private EditText userName, userPassword;
    private Switch switchSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();
    }

    /**
     * Initialization all of the program
     * Link controls
     * Set button listener
     */
    private void initView() {

        inputLayout = findViewById(R.id.input_layout);
        btnLogin = (TextView) findViewById(R.id.main_btn_login);
        btnCannel = (TextView) findViewById(R.id.main_btn_cannel);
        switchSave = (Switch) findViewById(R.id.switchSave);
        progress = findViewById(R.id.layout_progress);
        mName = (LinearLayout) findViewById(R.id.input_layout_name);
        mPsw = (LinearLayout) findViewById(R.id.input_layout_psw);
        userName = (EditText) findViewById(R.id.userName);
        userPassword = (EditText) findViewById(R.id.userPassword);

        btnLogin.setOnClickListener(new animationOnClickListener(this, btnLogin, btnCannel));
        btnCannel.setOnClickListener(new animationOnClickListener(this, btnLogin, btnCannel));
    }

/************************************* Begin Of Animation *****************************************/

    /**
     * The animation of input box
     * @param view control
     * @param width width
     * @param height Height
     */
    private void inputAnimator(final View view, float width, float height) {

        switchSave.setVisibility(View.GONE);

        AnimatorSet set = new AnimatorSet();

        final ValueAnimator animator = ValueAnimator.ofFloat(0, width);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view
                        .getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(inputLayout,
                "scaleX", 1f, 0.5f);
        set.setDuration(1000);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
        set.start();
        set.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                /**
                 * 动画结束后，先显示加载的动画，然后再隐藏输入框
                 */
                progress.setVisibility(View.VISIBLE);
                progressAnimator(progress);
                inputLayout.setVisibility(View.INVISIBLE);
                //recovery();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        });
    }

    /**
     * progress animation
     *
     * @param view progress
     */
    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(10);
        animator3.setInterpolator(new JellyInterpolator());
        animator3.start();
    }

/************************************ End Of Animation ********************************************/

    /**
     * recover activity
     */
    private void recovery() {
        progress.setVisibility(View.GONE);
        inputLayout.setVisibility(View.VISIBLE);
        mName.setVisibility(View.VISIBLE);
        mPsw.setVisibility(View.VISIBLE);
        switchSave.setVisibility(View.VISIBLE);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) inputLayout.getLayoutParams();
        params.leftMargin = 0;
        params.rightMargin = 0;
        inputLayout.setLayoutParams(params);


        ObjectAnimator animator2 = ObjectAnimator.ofFloat(inputLayout, "scaleX", 0.5f,1f );
        animator2.setDuration(500);
        animator2.setInterpolator(new AccelerateDecelerateInterpolator());
        animator2.start();
    }

    /**
     * Toast prompt while exit
     * User have to press twice to exit
     */
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(MainActivity.this,"再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Listener class
     */
    class animationOnClickListener implements View.OnClickListener{
        private Context context;
        private TextView btnLogin;
        private TextView btnCannel;


        public animationOnClickListener(Context context, TextView btnLogin, TextView btnCannel){
            this.btnLogin = btnLogin;
            this.btnCannel = btnCannel;
            this.context = context;
        }

        @Override
        public void onClick(View view){

            if(btnLogin.getVisibility() == View.VISIBLE){

                btnCannel.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.GONE);

                // 计算出控件的高与宽
                mWidth = btnLogin.getMeasuredWidth();
                mHeight = btnLogin.getMeasuredHeight();
                // 隐藏输入框
                mName.setVisibility(View.INVISIBLE);
                mPsw.setVisibility(View.INVISIBLE);

                inputAnimator(inputLayout, mWidth, mHeight);
            }
            else if(btnCannel.getVisibility() == View.VISIBLE){
                btnLogin.setVisibility(View.VISIBLE);
                btnCannel.setVisibility(View.GONE);

                recovery();
                Toast toast = Toast.makeText(context, "用户已取消", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }
}
