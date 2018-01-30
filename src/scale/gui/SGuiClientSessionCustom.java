/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package scale.gui;

import sa.lib.SLibConsts;
import sa.lib.SLibUtils;
import sa.lib.gui.SGuiClient;
import sa.lib.gui.SGuiSessionCustom;

/**
 *
 * @author Sergio Flores
 */
public final class SGuiClientSessionCustom implements SGuiSessionCustom {

    private SGuiClient miClient;

    private int[] manLocalCountryKey;
    private int[] manLocalCurrencyKey;
    private int[] manLocalLanguageKey;
    private String msLocalCountry;
    private String msLocalCountryCode;
    private String msLocalCurrency;
    private String msLocalCurrencyCode;
    private String msLocalLanguage;

    private int mnTerminal;

    public SGuiClientSessionCustom(SGuiClient client, int terminal) {
        miClient = client;

        manLocalCountryKey = new int[] {};
        manLocalCurrencyKey = new int[] {};
        manLocalLanguageKey = new int[] {};
        msLocalCountry = "MÉXICO";
        msLocalCountryCode = "MEX";
        msLocalCurrency = "PESOS MEXICANOS";
        msLocalCurrencyCode = "MXN";
        msLocalLanguage = SLibConsts.LAN_ISO639_ES;

        mnTerminal = terminal;
    }

    /*
     * Private methods
     */

    /*
     * Public methods
     */

    public void setLocalCurrencyCode(String s) { msLocalCurrencyCode = s; }

    public int[] getLocalCountryKey() { return manLocalCountryKey; }
    public String getLocalCountry() { return msLocalCountry; }
    public String getLocalCountryCode() { return msLocalCountryCode; }
    public boolean isLocalCountry(final int[] key) { return SLibUtils.compareKeys(key, manLocalCountryKey); }
    public int[] getLocalCurrencyKey() { return manLocalCurrencyKey; }
    public String getLocalCurrency() { return msLocalCurrency; }
    public String getLocalCurrencyCode() { return msLocalCurrencyCode; }
    public boolean isLocalCurrency(final int[] key) { return SLibUtils.compareKeys(key, manLocalCurrencyKey); }
    public String getLocalLanguage() { return msLocalLanguage; }

    public String getCountry(final int[] key) { return !SLibUtils.compareKeys(key, manLocalCountryKey) ? "n/d" : msLocalCountry; }
    public String getCountryCode(final int[] key) { return !SLibUtils.compareKeys(key, manLocalCountryKey) ? "n/d" : msLocalCountryCode; }
    public String getCurrency(final int[] key) { return !SLibUtils.compareKeys(key, manLocalCurrencyKey) ? "n/d" : msLocalCurrency; }
    public String getCurrencyCode(final int[] key) { return !SLibUtils.compareKeys(key, manLocalCurrencyKey) ? "n/d" : msLocalCurrencyCode; }
    public String getLanguage(final int[] key) { return !SLibUtils.compareKeys(key, manLocalLanguageKey) ? "n/d" : msLocalLanguage; }

    public int getTerminal() { return mnTerminal; }
}
