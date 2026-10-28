
private void runCommand() {
    String cmd = input.getText().toString().trim();

    if (cmd.isEmpty()) return;

    ((android.view.inputmethod.InputMethodManager)
        getSystemService(INPUT_METHOD_SERVICE))
        .hideSoftInputFromWindow(input.getWindowToken(), 0);

    input.setText("");

    String c = cmd.toLowerCase(java.util.Locale.ROOT);

    // EXIT: tutup aplikasi sepenuhnya
    if (c.equals("exit")) {
        finishAndRemoveTask();
        return;
    }

    // CLEARC: bersihkan paparan command
    if (c.equals("clearc")) {
        output.removeAllViews();
        return;
    }

    // Paparkan command yang dimasukkan
    line("$ " + cmd);

    if (c.equals("help")) {
        line("LIFT TERMINAL COMMANDS");
        line("help    - Paparkan bantuan");
        line("clearc  - Bersihkan paparan command");
        line("exit    - Keluar daripada aplikasi");
        line("open-lift-ai - Buka Lift AI");
        return;
    }

    if (c.equals("open-lift-ai")) {
        openAI();
        return;
    }

    line("Command belum disokong: " + cmd);
    line("Taip help untuk melihat a
         rahan.");
}
