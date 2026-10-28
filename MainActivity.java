
private void runCommand() {
    String cmd = input.getText().toString().trim();

    if (cmd.isEmpty()) {
        return;
    }

    // Sembunyikan papan kekunci
    android.view.inputmethod.InputMethodManager imm =
        (android.view.inputmethod.InputMethodManager)
        getSystemService(INPUT_METHOD_SERVICE);

    if (imm != null) {
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }

    input.setText("");

    String c = cmd.toLowerCase(java.util.Locale.ROOT);

    // EXIT: tutup aplikasi
    if (c.equals("exit")) {
        finishAndRemoveTask();
        return;
    }

    // CLEARC: bersihkan paparan command
    if (c.equals("clearc")) {
        output.removeAllViews();
        line("Lift Terminal cleared.");
        return;
    }

    // Paparkan arahan
    line("$ " + cmd);

    // HELP: senarai arahan
    if (c.equals("help")) {
        line("=== LIFT TERMINAL ===");
        line("help          - Paparkan bantuan");
        line("clearc        - Bersihkan paparan command");
        line("exit          - Keluar daripada aplikasi");
        line("open-lift-ai  - Buka Lift AI");
        line("lift-wake-on  - Aktifkan wake lock");
        line("lift-wake-off - Nyahaktifkan wake lock");
        line("git clone     - Klon repository Git");
        line("lift install  - Pasang pakej");
        line("lift uninstall - Buang pakej");
        line("=====================");
        return;
    }

    // Buka Lift AI
    if (c.equals("open-lift-ai")) {
        openAI();
        return;
    }

    // Arahan yang belum disambungkan
    if (c.equals("lift-wake-on")
            || c.equals("lift-wake-off")) {
        line("Wake lock belum disambungkan.");
        return;
    }

    if (c.startsWith("lift install ")
            || c.startsWith("lift uninstall ")) {
        line("Package manager belum disambungkan.");
        return;
    }

    if (c.equals("git clone")
            || c.startsWith("git clone ")) {
        line("Git belum disambungkan.");
        return;
    }

    // Arahan tidak dikenali
    line("Command tidak dikenali: " + cmd);
    line("Taip help untuk
         bantuan.");
}
