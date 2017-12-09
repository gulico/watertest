package com.example.wxy.watertest10.View.SignFrament;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wxy.watertest10.Bean.ResolutionUtil;
import com.example.wxy.watertest10.Bean.StringUtil;
import com.example.wxy.watertest10.R;

/**
 * SignDialogFragment
 */
public class SignDialogFragment extends AppCompatDialogFragment {
    public static final String TAG = "SIGN";
    public static final String ARG_SCORE_NUMBER = "ARG_SCORE_NUMBER";
    private int score;
    private ResolutionUtil resolutionUtil;

    public SignDialogFragment() {
        resolutionUtil = ResolutionUtil.getInstance();
    }

    public static SignDialogFragment newInstance(int score) {
        SignDialogFragment signDialogFragment = new SignDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SCORE_NUMBER, score);
        signDialogFragment.setArguments(bundle);
        return signDialogFragment;
    }


    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View contentView = getActivity().getLayoutInflater().inflate(R.layout.dialog_sign, null);
        getParam();
        initView(contentView);

        AppCompatDialog appCompatDialog = new AppCompatDialog(getContext(), R.style.DialogNoTitle);
        appCompatDialog.setContentView(contentView, new ViewGroup.LayoutParams(resolutionUtil.formatHorizontal(961),
                ViewGroup.LayoutParams.WRAP_CONTENT));
        appCompatDialog.setCanceledOnTouchOutside(false);
        return appCompatDialog;
    }

    private void getParam() {
        if (getArguments() != null) {
            score = getArguments().getInt(ARG_SCORE_NUMBER, 0);
        }
    }

    private void initView(View root) {
        TextView tvMessage = (TextView) root.findViewById(R.id.dialog_sign_tv_message);
        TextView tvScoreNumber = (TextView) root.findViewById(R.id.dialog_sign_tv_score_number);
        AppCompatButton btnSure = (AppCompatButton) root.findViewById(R.id.dialog_sign_btn_sure);

        int scoreLength = StringUtil.getStringLength(score);
        String numberStr = String.format(getString(R.string.much_score_int), score);

        SpannableString spannableString = new SpannableString(numberStr);
        spannableString.setSpan(new AbsoluteSizeSpan(resolutionUtil.formatVertical(120)), 0, scoreLength, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(resolutionUtil.formatVertical(39)), scoreLength, numberStr.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tvScoreNumber.setText(spannableString);

        btnSure.setSupportBackgroundTintList(getResources().getColorStateList(R.color.color_user_button_submit));
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                if (onConfirmListener != null) {
                    onConfirmListener.onConfirm();
                }
            }
        });

        //------------------------------分辨率适配------------------------------------
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = resolutionUtil.formatVertical(67);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        tvMessage.setLayoutParams(layoutParams);
        tvMessage.setTextSize(TypedValue.COMPLEX_UNIT_PX, resolutionUtil.formatVertical(44));

        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = resolutionUtil.formatVertical(39);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        tvScoreNumber.setLayoutParams(layoutParams);

        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = resolutionUtil.formatVertical(61);
        layoutParams.bottomMargin = resolutionUtil.formatVertical(67);
        layoutParams.leftMargin = layoutParams.rightMargin = resolutionUtil.formatHorizontal(73);
        btnSure.setLayoutParams(layoutParams);
        btnSure.setTextSize(TypedValue.COMPLEX_UNIT_PX, resolutionUtil.formatVertical(48));
    }

    private OnConfirmListener onConfirmListener;

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }

    public interface OnConfirmListener {
        void onConfirm();
    }
}
