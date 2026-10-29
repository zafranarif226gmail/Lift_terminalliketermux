package app.lift.terminal;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

public class MainActivity extends Activity {
    private LinearLayout output;
    private EditText input;
    private boolean aiOpen = false;
    private final int BG = Color.rgb(9,15,12), GREEN = Color.rgb(112,245,159);
    private final int PANEL = Color.rgb(21,32,26), TEXT = Color.rgb(224,236,227);

    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        getWindow().setStatusBarColor(BG);
        getWindow().setNavigationBarColor(BG);
        splash();
        new android.os.Handler(getMainLooper()).postDelayed(this::terminal, 700);
    }
    private TextView txt(String s,int size,int color) {
        TextView t=new TextView(this); t.setText(s); t.setTextSize(size); t.setTextColor(color);
        t.setTypeface(Typeface.MONOSPACE); t.setPadding(10,6,10,6); return t;
    }
    private void splash() {
        LinearLayout l=new LinearLayout(this); l.setOrientation(1); l.setGravity(Gravity.CENTER); l.setBackgroundColor(BG);
        TextView logo=txt("LIFT",40,GREEN); logo.setGravity(Gravity.CENTER); logo.setTypeface(Typeface.MONOSPACE,Typeface.BOLD); l.addView(logo);
        TextView sub=txt("TERMINAL • Loading...",13,TEXT); sub.setGravity(Gravity.CENTER); l.addView(sub);
        l.addView(new ProgressBar(this)); setContentView(l);
    }
    private Button btn(String s) {
        Button b=new Button(this); b.setText(s); b.setTextColor(GREEN); b.setAllCaps(false);
        b.setBackgroundTintList(ColorStateList.valueOf(PANEL)); return b;
    }
    private void terminal() {
        aiOpen=false;
        LinearLayout root=new LinearLayout(this); root.setOrientation(1); root.setPadding(12,10,12,10); root.setBackgroundColor(BG);
        LinearLayout head=new LinearLayout(this); head.setGravity(Gravity.CENTER_VERTICAL);
        TextView title=txt("LIFT TERMINAL",19,GREEN); title.setTypeface(Typeface.MONOSPACE,Typeface.BOLD);
        head.addView(title,new LinearLayout.LayoutParams(0,-2,1));
        Button ai=btn("Lift AI"); ai.setOnClickListener(v->openAI()); head.addView(ai); root.addView(head);
        root.addView(txt("Local preview • Linux shell not connected",11,Color.LTGRAY));
        ScrollView scroll=new ScrollView(this); output=new LinearLayout(this); output.setOrientation(1); scroll.addView(output);
        root.addView(scroll,new LinearLayout.LayoutParams(-1,0,1));
        line("Welcome to Lift Terminal!"); line("Type help to list commands.");
        LinearLayout bar=new LinearLayout(this); bar.setGravity(Gravity.CENTER_VERTICAL);
        input=new EditText(this); input.setSingleLine(true); input.setTextColor(TEXT); input.setHintTextColor(Color.GRAY);
        input.setHint("Enter command..."); input.setTypeface(Typeface.MONOSPACE);
        input.setBackgroundTintList(ColorStateList.valueOf(GREEN)); bar.addView(input,new LinearLayout.LayoutParams(0,-2,1));
        Button run=btn("Run"); run.setOnClickListener(v->runCommand()); bar.addView(run); root.addView(bar);
        setContentView(root);
    }
    private void line(String s) { if(output!=null) output.addView(txt(s,13,TEXT)); }
    private void runCommand() {
        String cmd=input.getText().toString().trim(); if(cmd.isEmpty()) return;
        InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm!=null) imm.hideSoftInputFromWindow(input.getWindowToken(),0);
        input.setText(""); String c=cmd.toLowerCase(java.util.Locale.ROOT);
        if(c.equals("exit")) { finishAndRemoveTask(); return; }
        if(c.equals("clearc")) { output.removeAllViews(); return; }
        line("$ "+cmd);
        if(c.equals("help")) {
            line("help - show commands"); line("clearc - clear output"); line("exit - close app");
            line("open-lift-ai - open Lift AI preview");
            line("Note: real Linux commands/package installs are not connected.");
        } else if(c.equals("open-lift-ai")) openAI();
        else { line("Unknown command: "+cmd); line("This preview does not execute real Linux commands."); line("Type help."); }
    }
    private void openAI() {
        aiOpen=true; output.removeAllViews(); line("LIFT AI"); line("Lift AI screen preview");
        line("AI service is not connected yet."); line("Press Back to return to terminal.");
    }
    @Override public void onBackPressed() { if(aiOpen) terminal(); else super.onBackPressed(); }
  
}
