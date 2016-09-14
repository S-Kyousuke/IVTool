package com.skyousuke.ivtool.windows;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TextViewCompat;
import android.text.Editable;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.skyousuke.ivtool.IVCalculator;
import com.skyousuke.ivtool.IVResult;
import com.skyousuke.ivtool.InputData;
import com.skyousuke.ivtool.Phrase;
import com.skyousuke.ivtool.Pokemon;
import com.skyousuke.ivtool.Pokemon.StatType;
import com.skyousuke.ivtool.R;
import com.skyousuke.ivtool.Team;
import com.skyousuke.ivtool.util.TextFormat;
import com.skyousuke.ivtool.util.WindowManagerUtils;
import com.skyousuke.ivtool.view.BackKeyListener;
import com.skyousuke.ivtool.view.CustomRelativeLayout;
import com.skyousuke.ivtool.view.DefaultOnItemSelectedListener;
import com.skyousuke.ivtool.view.DefaultTextChangedListener;
import com.skyousuke.ivtool.view.HintSpinnerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author S.Kyousuke <surasek@gmail.com>
 */
public class MainWindow implements BackKeyListener, InputData.Listener {

    private static final int DEFAULT_FLAG = LayoutParams.FLAG_NOT_TOUCH_MODAL | LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
    private static final int CHANGE_FLAG = LayoutParams.FLAG_NOT_FOCUSABLE;

    private final WindowManager.LayoutParams layoutParams =
            new WindowManager.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    LayoutParams.TYPE_PHONE, DEFAULT_FLAG,
                    PixelFormat.TRANSLUCENT);

    private int screenHeight;
    private int screenWidth;

    private final Context context;
    private final WindowManager windowManager;
    private final Handler handler;

    private ResultWindow resultWindow;

    private CustomRelativeLayout layout;
    private AutoCompleteTextView pokemonView;
    private AutoCompleteTextView dustView;
    private AutoCompleteTextView cpView;
    private AutoCompleteTextView hpView;
    private TextView maxIVTextView;
    private TextView minIVTextView;
    private TextView message;
    private ImageView pokemonValidation;
    private ImageView dustValidation;
    private ImageView cpValidation;
    private ImageView hpValidation;
    private ImageView powerUpValidation;
    private Button seeResultButton;
    private Button appraisalButton;

    private int appraisalColor;
    private LinearLayout appraisalLayout;
    private Spinner teamSpinner;
    private Spinner rangePhraseSpinner;
    private Spinner bestStatSpinner;
    private Spinner statPhraseSpinner;
    private TextView matchedFirstStat;
    private TextView matchedSecondStat;
    private CheckBox checkMatchedFirstStat;
    private CheckBox checkMatchedSecondStat;
    private LinearLayout teamSelectLayout;
    private LinearLayout bestStatSelectLayout;
    private LinearLayout matchedStatSelectLayout;
    private TextView appraisalWrong;

    private HintSpinnerAdapter teamSpinnerAdapter;
    private HintSpinnerAdapter rangePhraseSpinnerAdapter;
    private HintSpinnerAdapter bestStatSpinnerAdapter;
    private HintSpinnerAdapter statPhraseSpinnerAdapter;

    private Set<Integer> cpSet;
    private Set<Integer> hpSet;
    private List<IVResult> ivResults = new ArrayList<>();
    private InputData inputData = new InputData();

    private boolean cpMatchUpHp;

    @SuppressLint("InflateParams")
    public MainWindow(final Context context, WindowManager windowManager, Handler handler) {
        this.context = context;
        this.windowManager = windowManager;
        this.handler = handler;
        inputData.setListener(this);

        layout = (CustomRelativeLayout) LayoutInflater.from(context).inflate(R.layout.tool_window, null);
        pokemonView = (AutoCompleteTextView) layout.findViewById(R.id.pokemon);
        dustView = (AutoCompleteTextView) layout.findViewById(R.id.dust);
        cpView = (AutoCompleteTextView) layout.findViewById(R.id.cp);
        hpView = (AutoCompleteTextView) layout.findViewById(R.id.hp);
        maxIVTextView = (TextView) layout.findViewById(R.id.max_iv);
        minIVTextView = (TextView) layout.findViewById(R.id.min_iv);
        final CheckBox powerUpCheck = (CheckBox) layout.findViewById(R.id.check_powered);
        TextView textCheck = (TextView) layout.findViewById(R.id.powered);
        message = (TextView) layout.findViewById(R.id.message);
        pokemonValidation = (ImageView) layout.findViewById(R.id.image_validation_pokemon);
        dustValidation = (ImageView) layout.findViewById(R.id.image_validation_dust);
        cpValidation = (ImageView) layout.findViewById(R.id.image_validation_cp);
        hpValidation = (ImageView) layout.findViewById(R.id.image_validation_hp);
        powerUpValidation = (ImageView) layout.findViewById(R.id.image_validation_power_up);
        seeResultButton = (Button) layout.findViewById(R.id.button_tool);
        appraisalButton = (Button) layout.findViewById(R.id.button_appraisal);

        appraisalLayout = (LinearLayout) layout.findViewById(R.id.appraisal);
        teamSelectLayout = (LinearLayout) layout.findViewById(R.id.team_select);
        teamSpinner = (Spinner) teamSelectLayout.findViewById(R.id.spinner_team);
        rangePhraseSpinner = (Spinner) layout.findViewById(R.id.spinner_iv_phrase);
        statPhraseSpinner = (Spinner) layout.findViewById(R.id.spinner_stats_phrase);
        appraisalWrong = (TextView) layout.findViewById(R.id.appraisal_wrong);

        layoutParams.gravity = Gravity.TOP | Gravity.CENTER;

        enableBackKey(true);
        setTouchListener();

        pokemonView.setAdapter(new ArrayAdapter<>(context, R.layout.simple_dropdown, Pokemon.values));
        pokemonView.addTextChangedListener(new DefaultTextChangedListener() {
            @Override
            public void afterTextChanged(Editable editable) {
                inputData.setPokemon(editable.toString());
                updateValidation();
            }
        });
        pokemonView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> p, View v, int pos, long id) {
                if (inputData.isDustUnset()) focusDustView();
            }
        });
        pokemonView.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    pokemonView.setText("");
                }
            }
        });
        pokemonView.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    focusDustView();
                    return true;
                }
                return false;
            }
        });
        dustView.setAdapter(new ArrayAdapter<>(context, R.layout.simple_dropdown, IVCalculator.dustList));
        dustView.addTextChangedListener(new DefaultTextChangedListener() {
            @Override
            public void afterTextChanged(Editable editable) {
                inputData.setDust(editable.toString());
                updateValidation();
            }
        });
        dustView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> p, View v, int pos, long id) {
                if (inputData.isCpUnset()) focusCpView();
            }
        });
        dustView.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    dustView.setText("");
                }
            }
        });
        dustView.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    focusCpView();
                    return true;
                }
                return false;
            }
        });

        cpView.addTextChangedListener(new DefaultTextChangedListener() {
            @Override
            public void afterTextChanged(Editable editable) {
                inputData.setCp(editable.toString());
                updateValidation();
            }
        });
        cpView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> p, View v, int pos, long id) {
                if (inputData.isHpUnset()) focusHpView();
            }
        });
        cpView.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    cpView.setText("");
                }
            }
        });
        cpView.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    focusHpView();
                    return true;
                }
                return false;
            }
        });
        hpView.addTextChangedListener(new DefaultTextChangedListener() {
            @Override
            public void afterTextChanged(Editable editable) {
                inputData.setHp(editable.toString());
                updateValidation();
            }
        });
        hpView.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    hpView.setText("");
                }
            }
        });
        powerUpCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                inputData.setPoweredUp(isChecked);
                updateValidation();
            }
        });
        textCheck.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                powerUpCheck.toggle();
            }
        });
        seeResultButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (resultWindow != null) {
                    resultWindow.show();
                    enableBackKey(false);
                }
            }
        });

        hideAppraisal();
    }

    private void focusDustView() {
        dustView.setFocusableInTouchMode(true);
        dustView.requestFocus();
    }

    private void focusHpView() {
        hpView.setFocusableInTouchMode(true);
        hpView.requestFocus();
    }

    private void focusCpView() {
        cpView.setFocusableInTouchMode(true);
        cpView.requestFocus();
    }

    public void updateScreenSize(int width, int height) {
        screenWidth = width;
        screenHeight = height;

        if (hasShown()) {
            layoutParams.width = screenWidth;
            WindowManagerUtils.keepInScreen(layout, layoutParams, width, height);
            windowManager.updateViewLayout(layout, layoutParams);
        }
    }

    private void updateMessage() {
        if (!cpMatchUpHp) {
            if (inputData.isPoweredUp()) {
                message.setText(R.string.no_data);
            } else message.setText(R.string.no_data_no_power);
        } else if (!inputData.foundPokemon()) {
            if (inputData.isPokemonUnset()) {
                message.setText(R.string.pokemon_select);
                updateMessageTextColor(ContextCompat.getColor(context, R.color.black));
            } else {
                message.setText(R.string.pokemon_incorrect);
                updateMessageTextColor(ContextCompat.getColor(context, R.color.light_red));
            }
        } else if (!inputData.foundDust()) {
            if (inputData.isDustUnset()) {
                message.setText(R.string.dust_enter);
                updateMessageTextColor(ContextCompat.getColor(context, R.color.black));
            } else {
                message.setText(R.string.dust_incorrect);
                updateMessageTextColor(ContextCompat.getColor(context, R.color.light_red));
            }
        } else if (!inputData.foundCp(cpSet)) {
            if (inputData.isCpUnset()) {
                message.setText(R.string.cp_enter);
                updateMessageTextColor(ContextCompat.getColor(context, R.color.black));
            } else {
                if (!inputData.isPoweredUp())
                    message.setText(R.string.cp_incorrect_no_power);
                else message.setText(R.string.cp_incorrect);
                updateMessageTextColor(ContextCompat.getColor(context, R.color.light_red));
            }
        } else if (!inputData.foundHp(hpSet)) {
            if (inputData.isHpUnset()) {
                message.setText(R.string.hp_enter);
                updateMessageTextColor(ContextCompat.getColor(context, R.color.black));
            } else {
                if (!inputData.isPoweredUp())
                    message.setText(R.string.hp_incorrect_no_power);
                else message.setText(R.string.hp_incorrect);
                updateMessageTextColor(ContextCompat.getColor(context, R.color.light_red));
            }
        } else {
            message.setText("");
        }
    }

    private void updateMessageTextColor(int color) {
        if (message.getCurrentTextColor() != color) {
            message.setTextColor(color);
        }
    }

    private void updateValidationImage() {
        updateImageResource(pokemonValidation, R.color.transparent);
        updateImageResource(dustValidation, R.color.transparent);
        updateImageResource(cpValidation, R.color.transparent);
        updateImageResource(hpValidation, R.color.transparent);
        updateImageResource(powerUpValidation, R.color.transparent);

        if (!cpMatchUpHp) {
            updateImageResource(dustValidation, R.drawable.wonder);
            updateImageResource(cpValidation, R.drawable.wonder);
            updateImageResource(hpValidation, R.drawable.wonder);
            if (!inputData.isPoweredUp())
                updateImageResource(powerUpValidation, R.drawable.wonder);
            return;
        }

        if (inputData.isPokemonUnset()) {
            updateImageResource(pokemonValidation, R.drawable.wonder);
            return;
        }
        if (!inputData.foundPokemon()) {
            updateImageResource(pokemonValidation, R.drawable.incorrect);
            return;
        }

        if (inputData.isDustUnset()) {
            updateImageResource(dustValidation, R.drawable.wonder);
            return;
        }
        if (!inputData.foundDust()) {
            updateImageResource(dustValidation, R.drawable.incorrect);
            return;
        }

        if (inputData.isCpUnset()) {
            updateImageResource(cpValidation, R.drawable.wonder);
            return;
        }
        if (!inputData.foundCp(cpSet)) {
            updateImageResource(dustValidation, R.drawable.wonder);
            updateImageResource(cpValidation, R.drawable.wonder);
            if (!inputData.isPoweredUp())
                updateImageResource(powerUpValidation, R.drawable.wonder);
            return;
        }

        if (inputData.isHpUnset()) {
            updateImageResource(hpValidation, R.drawable.wonder);
            return;
        }
        if (!inputData.foundHp(hpSet)) {
            updateImageResource(dustValidation, R.drawable.wonder);
            updateImageResource(hpValidation, R.drawable.wonder);
            if (!inputData.isPoweredUp())
                updateImageResource(powerUpValidation, R.drawable.wonder);
        }
    }

    private void updateImageResource(ImageView view, int resId) {
        Integer tag = (Integer) view.getTag();
        if (tag == null || tag != resId) {
            view.setImageResource(resId);
            view.setTag(resId);
        }
    }

    private void updateValidation() {
        updateValidationImage();
        updateMessage();
    }

    private void lostFocus(final long delay) {
        handler.post(new Runnable() {
            boolean change = false;

            @Override
            public void run() {
                if (!change) {
                    layoutParams.flags = CHANGE_FLAG;
                    if (layout.getParent() != null) {
                        windowManager.updateViewLayout(layout, layoutParams);
                        change = true;
                    }
                    handler.postDelayed(this, delay);
                } else {
                    layoutParams.flags = DEFAULT_FLAG;
                    if (layout.getParent() != null) {
                        windowManager.updateViewLayout(layout, layoutParams);
                    } else handler.postDelayed(this, delay);
                }
            }
        });
    }

    private void setTouchListener() {
        layout.setOnTouchListener(new View.OnTouchListener() {
            private WindowManager.LayoutParams latestParams = layoutParams;
            int x, y;
            float touchX, touchY;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        x = latestParams.x;
                        y = latestParams.y;

                        touchX = motionEvent.getRawX();
                        touchY = motionEvent.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        latestParams.x = (int) (x + motionEvent.getRawX() - touchX);
                        latestParams.y = (int) (y + motionEvent.getRawY() - touchY);

                        WindowManagerUtils.keepInScreen(layout, latestParams, screenWidth, screenHeight);
                        windowManager.updateViewLayout(layout, latestParams);
                        break;
                    case MotionEvent.ACTION_OUTSIDE:
                        lostFocus(500);
                        break;
                }
                return false;
            }
        });
    }

    private void updateCpAndHpSet() {
        Integer[] cpArray = new Integer[0];
        Integer[] hpArray = new Integer[0];

        if (inputData.foundPokemon() && inputData.foundDust()) {
            cpSet = IVCalculator.buildCpSet(inputData.getPokemon(), inputData.getDust(), inputData.isPoweredUp());
            hpSet = IVCalculator.buildHpSet(inputData.getPokemon(), inputData.getDust(), inputData.isPoweredUp());

            cpArray = cpSet.toArray(new Integer[cpSet.size()]);
            hpArray = hpSet.toArray(new Integer[hpSet.size()]);
        }
        cpView.setAdapter(new ArrayAdapter<>(context, R.layout.simple_dropdown, cpArray));
        hpView.setAdapter(new ArrayAdapter<>(context, R.layout.simple_dropdown, hpArray));
    }


    public void show() {
        layoutParams.width = screenWidth;
        windowManager.addView(layout, layoutParams);
    }

    public void hide() {
        windowManager.removeView(layout);
    }

    public boolean hasShown() {
        return layout.getParent() != null;
    }

    public void toggleVisibility() {
        if (hasShown()) {
            hide();
        } else show();
    }

    @Override
    public void onBackKeyPressed() {
        hide();
    }

    public void enableBackKey(boolean enable) {
        if (enable) layout.setBackKeyListener(this);
        else layout.setBackKeyListener(null);
    }

    private void showAppraisal() {
        appraisalButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Long Press to Disable Appraisal", Toast.LENGTH_SHORT).show();
                Vibrator vb = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(100);
            }
        });
        appraisalButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                lostFocus(500);
                if (hasAppraisalShow()) hideAppraisal();
                return true;
            }
        });
        showTeamSelect();
        appraisalButton.setText(R.string.no_appraisal);
    }

    private void hideAppraisal() {
        hideTeamSelect();
        hideRangePhraseSelect();
        hideBestStatSelect();
        hideMatchedStatSelect();
        hideStatSelect();

        appraisalButton.setText(R.string.appraisal);
        appraisalButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                lostFocus(500);
                if (!hasAppraisalShow()) showAppraisal();
                return true;
            }
        });
        appraisalButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                lostFocus(500);
                Vibrator vb = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(25);
                if (!hasAppraisalShow()) showAppraisal();
            }
        });
    }

    private void hideTeamSelect() {
        if (teamSelectLayout != null) {
            teamSelectLayout.setVisibility(View.GONE);
            inputData.unsetTeam();
        }
    }

    private void hideRangePhraseSelect() {
        if (rangePhraseSpinner != null) {
            rangePhraseSpinner.setVisibility(View.GONE);
            inputData.unsetRangePhrase();
        }
    }

    private void hideStatSelect() {
        if (statPhraseSpinner != null) {
            statPhraseSpinner.setVisibility(View.GONE);
            inputData.unsetStatPhrase();
        }
    }

    private void hideBestStatSelect() {
        if (bestStatSelectLayout != null) {
            bestStatSelectLayout.setVisibility(View.GONE);
            inputData.unsetBestStat();
        }
    }

    private void hideMatchedStatSelect() {
        if (matchedStatSelectLayout != null) {
            matchedStatSelectLayout.setVisibility(View.GONE);
            resetMatchedStatSelect();
        }
    }

    private boolean hasAppraisalShow() {
        return teamSelectLayout.getVisibility() == View.VISIBLE;
    }

    private void showTeamSelect() {
        teamSpinnerAdapter = new HintSpinnerAdapter(context, Team.values,
                "Select Team", ContextCompat.getColor(context, R.color.dark_gray));
        teamSpinner.setAdapter(teamSpinnerAdapter);
        teamSpinner.setOnItemSelectedListener(new DefaultOnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = teamSpinner.getItemAtPosition(position);
                if (teamSpinnerAdapter.isEqualHint(item)) {
                    teamSpinnerAdapter.hideHint();
                    teamSpinner.getLayoutParams().width =
                            (int) context.getResources().getDimension(R.dimen.spinner_team_width);
                } else {
                    teamSpinner.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    inputData.setTeam((Team) item);
                    inputData.unsetRangePhrase();
                    setTeamLayout();
                    showRangePhraseSelect();
                }
            }
        });
        teamSpinnerAdapter.showHint(teamSpinner);
        teamSelectLayout.setVisibility(View.VISIBLE);
    }

    private void showRangePhraseSelect() {
        rangePhraseSpinnerAdapter = new HintSpinnerAdapter(context, inputData.getTeam().getIVPhrases(),
                "Select IV Range Phrase", appraisalColor);
        rangePhraseSpinner.setAdapter(rangePhraseSpinnerAdapter);
        rangePhraseSpinner.setOnItemSelectedListener(new DefaultOnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = rangePhraseSpinner.getItemAtPosition(position);
                if (rangePhraseSpinnerAdapter.isEqualHint(item)) {
                    rangePhraseSpinnerAdapter.hideHint();
                    hideBestStatSelect();
                    hideMatchedStatSelect();
                    hideStatSelect();
                } else {
                    inputData.setRangePhrase((Phrase) item);
                    inputData.unsetBestStat();
                    if (isCorrectAppraisal()) showBestStatSelect();
                    else {
                        hideBestStatSelect();
                        hideMatchedStatSelect();
                        hideStatSelect();
                    }
                }
            }
        });
        rangePhraseSpinnerAdapter.showHint(rangePhraseSpinner);
        rangePhraseSpinner.setVisibility(View.VISIBLE);
    }

    private void showBestStatSelect() {
        bestStatSpinnerAdapter = new HintSpinnerAdapter(context, StatType.values(), "Select Stat", appraisalColor);
        bestStatSpinner.setAdapter(bestStatSpinnerAdapter);
        bestStatSpinner.setOnItemSelectedListener(new DefaultOnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = bestStatSpinner.getItemAtPosition(position);
                if (bestStatSpinnerAdapter.isEqualHint(item)) {
                    bestStatSpinnerAdapter.hideHint();
                    bestStatSpinner.getLayoutParams().width =
                            (int) context.getResources().getDimension(R.dimen.spinner_stat_width);
                    hideMatchedStatSelect();
                    hideStatSelect();
                } else {
                    bestStatSpinner.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    resetMatchedStatSelect();
                    inputData.setBestStat((StatType) item);
                    if (isCorrectAppraisal()) {
                        StatType statType = (StatType) item;
                        if (statType.equals(StatType.NONE)) {
                            hideMatchedStatSelect();
                            showStatPhraseSelect();

                        } else {
                            hideStatSelect();
                            showMatchedStatSelect(statType);
                            showStatPhraseSelect();
                        }
                    } else {
                        hideMatchedStatSelect();
                        hideStatSelect();
                    }
                }
            }
        });
        bestStatSpinnerAdapter.showHint(bestStatSpinner);
        bestStatSelectLayout.setVisibility(View.VISIBLE);
    }

    private void showMatchedStatSelect(StatType statType) {
        StatType[] types = statType.getOthers();
        matchedFirstStat.setTag(types[0]);
        matchedSecondStat.setTag(types[1]);

        switch (inputData.getTeam()) {
            case VALOR:
                matchedFirstStat.setText(context.getString(R.string.matched_stat_valor, types[0]));
                matchedSecondStat.setText(context.getString(R.string.matched_stat_valor, types[1]));
                break;
            case INSTINCT:
                matchedFirstStat.setText(context.getString(R.string.matched_stat_instinct, types[0]));
                matchedSecondStat.setText(context.getString(R.string.matched_stat_instinct, types[1]));
                break;
            case MYSTIC:
                matchedFirstStat.setText(context.getString(R.string.matched_stat_mystic, types[0]));
                matchedSecondStat.setText(context.getString(R.string.matched_stat_mystic, types[1]));
                break;
        }
        matchedStatSelectLayout.setVisibility(View.VISIBLE);
    }

    private void resetMatchedStatSelect() {
        checkMatchedFirstStat.setChecked(false);
        checkMatchedSecondStat.setChecked(false);
        inputData.unsetEqualStat1();
        inputData.unsetEqualStat2();
    }

    private void showStatPhraseSelect() {
        statPhraseSpinnerAdapter = new HintSpinnerAdapter(context, inputData.getTeam().getStatPhrases(),
                "Select Stat Range Phrase", appraisalColor);
        statPhraseSpinner.setAdapter(statPhraseSpinnerAdapter);
        statPhraseSpinner.setOnItemSelectedListener(new DefaultOnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = statPhraseSpinner.getItemAtPosition(position);
                if (statPhraseSpinnerAdapter.isEqualHint(item)) {
                    statPhraseSpinnerAdapter.hideHint();
                } else {
                    inputData.setStatPhrase((Phrase) item);
                }
            }
        });
        statPhraseSpinnerAdapter.showHint(statPhraseSpinner);
        statPhraseSpinner.setVisibility(View.VISIBLE);
    }

    public void setResultWindow(ResultWindow resultWindow) {
        this.resultWindow = resultWindow;
    }

    private boolean isCorrectAppraisal() {
        return appraisalWrong.getVisibility() != View.VISIBLE;
    }

    private boolean hasParent(View view) {
        return view.getParent() != null;
    }

    private void setTeamLayout() {
        int bestStatIndex = appraisalLayout.indexOfChild(rangePhraseSpinner) + 1;
        int matchedStatIndex = appraisalLayout.indexOfChild(rangePhraseSpinner) + 2;

        if (bestStatSelectLayout != null && hasParent(bestStatSelectLayout))
            appraisalLayout.removeViewAt(bestStatIndex);

        if (matchedStatSelectLayout != null && hasParent(matchedStatSelectLayout))
            appraisalLayout.removeViewAt(matchedStatIndex - 1);

        switch (inputData.getTeam()) {
            case VALOR:
                appraisalColor = ContextCompat.getColor(context, R.color.light_red);
                bestStatSelectLayout = (LinearLayout) LayoutInflater.from(context)
                        .inflate(R.layout.best_stat_select_valor, appraisalLayout, false);
                matchedStatSelectLayout = (LinearLayout) LayoutInflater.from(context)
                        .inflate(R.layout.matched_stat_select_valor, appraisalLayout, false);
                break;
            case INSTINCT:
                appraisalColor = ContextCompat.getColor(context, R.color.dark_yellow);
                bestStatSelectLayout = (LinearLayout) LayoutInflater.from(context)
                        .inflate(R.layout.best_stat_select_instinct, appraisalLayout, false);
                matchedStatSelectLayout = (LinearLayout) LayoutInflater.from(context)
                        .inflate(R.layout.matched_stat_select_instinct, appraisalLayout, false);
                break;
            case MYSTIC:
                appraisalColor = ContextCompat.getColor(context, R.color.blue);
                bestStatSelectLayout = (LinearLayout) LayoutInflater.from(context)
                        .inflate(R.layout.best_stat_select_mystic, appraisalLayout, false);
                matchedStatSelectLayout = (LinearLayout) LayoutInflater.from(context)
                        .inflate(R.layout.matched_stat_select_mystic, appraisalLayout, false);
                break;
        }
        teamSpinnerAdapter.setColor(appraisalColor);

        bestStatSpinner = (Spinner) bestStatSelectLayout.findViewById(R.id.spinner_best_stat);
        checkMatchedFirstStat = (CheckBox) matchedStatSelectLayout.findViewById(R.id.check_matched_first_stats);
        checkMatchedSecondStat = (CheckBox) matchedStatSelectLayout.findViewById(R.id.check_matched_second_stats);
        matchedFirstStat = (TextView) matchedStatSelectLayout.findViewById(R.id.matched_first_stats);
        matchedSecondStat = (TextView) matchedStatSelectLayout.findViewById(R.id.matched_second_stats);

        matchedFirstStat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMatchedFirstStat.toggle();
            }
        });
        matchedSecondStat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMatchedSecondStat.toggle();
            }
        });
        checkMatchedFirstStat.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (appraisalColor == ContextCompat.getColor(context, R.color.dark_yellow)) {
                        TextViewCompat.setTextAppearance(matchedFirstStat, R.style.ShadowText);
                    } else TextViewCompat.setTextAppearance(matchedFirstStat, R.style.NoShadowText);
                    matchedFirstStat.setTextColor(appraisalColor);
                    inputData.setEqualStat1((StatType) matchedFirstStat.getTag());
                } else {
                    TextViewCompat.setTextAppearance(matchedFirstStat, R.style.NoShadowText);
                    matchedFirstStat.setTextColor(ContextCompat.getColor(context, R.color.black));
                    inputData.unsetEqualStat1();
                }
            }
        });
        checkMatchedSecondStat.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (appraisalColor == ContextCompat.getColor(context, R.color.dark_yellow)) {
                        TextViewCompat.setTextAppearance(matchedSecondStat, R.style.ShadowText);
                    } else TextViewCompat.setTextAppearance(matchedSecondStat, R.style.NoShadowText);
                    matchedSecondStat.setTextColor(appraisalColor);
                    inputData.setEqualStat2((StatType) matchedSecondStat.getTag());
                } else {
                    TextViewCompat.setTextAppearance(matchedSecondStat, R.style.NoShadowText);
                    matchedSecondStat.setTextColor(ContextCompat.getColor(context, R.color.black));
                    inputData.unsetEqualStat2();
                }
            }
        });
        appraisalLayout.addView(bestStatSelectLayout, bestStatIndex);
        appraisalLayout.addView(matchedStatSelectLayout, matchedStatIndex);
    }

    public void updateIV() {
        String minIV = "Min IV: ";
        String maxIV = "Max IV: ";
        cpMatchUpHp = true;
        ivResults.clear();

        boolean hasResultBeforeFilter = false;

        if (inputData.readyToCalculateIV(cpSet, hpSet)) {
            ivResults = IVCalculator.findPossibleIV(inputData.getPokemon(), inputData.getDust(),
                    inputData.getCp(), inputData.getHp(), inputData.isPoweredUp());

            if (!ivResults.isEmpty()) {
                hasResultBeforeFilter = true;
                IVCalculator.filterIV(ivResults, inputData.getRangePhrase(), inputData.getStatPhrase(),
                        inputData.getBestStat(), inputData.getEqualStat1(), inputData.getEqualStat2());

                if (!ivResults.isEmpty()) {
                    IVCalculator.sort(ivResults);
                    final IVResult[] minMax = IVCalculator.findMinMax(ivResults);
                    String minPercent = TextFormat.formatDecimal(minMax[0].getPercent(), 1) + " %";
                    String maxPercent = TextFormat.formatDecimal(minMax[1].getPercent(), 1) + " %";
                    minIV += minPercent;
                    maxIV += maxPercent;
                    if (resultWindow != null)
                        resultWindow.updateResult(inputData.getPokemon(), inputData.getCp(), inputData.getHp(),
                                (int) minMax[0].getPercent(), (int) minMax[1].getPercent(),
                                minPercent, maxPercent, ivResults);
                }
            } else cpMatchUpHp = false;
        }

        if (ivResults.isEmpty()) {
            minIV += "N/A";
            maxIV += "N/A";
            seeResultButton.setVisibility(View.GONE);

            if (!hasResultBeforeFilter) {
                appraisalButton.setVisibility(View.GONE);
                appraisalWrong.setVisibility(View.GONE);
                if (hasAppraisalShow())
                    hideAppraisal();
            } else {
                appraisalWrong.setVisibility(View.VISIBLE);
            }
        } else {
            seeResultButton.setVisibility(View.VISIBLE);
            appraisalButton.setVisibility(View.VISIBLE);
            appraisalWrong.setVisibility(View.GONE);
        }
        minIVTextView.setText(minIV);
        maxIVTextView.setText(maxIV);
    }

    @Override
    public void onDataChange(boolean needNewSet) {
        Log.d("Data", inputData.toString());
        if (needNewSet) updateCpAndHpSet();
        updateIV();
    }
}
