package com.secure.dex;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.secure.dex.data.gson.ConfigTemp;
import com.secure.dex.data.Constants;
import com.secure.dex.fastzip.FastZip;
import com.secure.dex.patchers.ManifestPatcher;
import com.secure.dex.signer.SignatureTool;
import com.secure.dex.utils.CommonUtils;
import com.secure.dex.patchers.DexCrypto;
import com.secure.dex.utils.FileUtils;
import com.secure.dex.utils.LoggerUtils;

import java.io.File;

public class Main {
    public static void main(String... args) {
        generateRandom();

        FileUtils.deleteDir(new File(Constants.OUTPUT_PATH));
        FileUtils.deleteDir(new File(Constants.RELEASE_PATH));
        FileUtils.delete(new File(Constants.LOG_PATH));

        LoggerUtils.writeLog("----------ApkProtector running----------------");

        LoggerUtils.writeLog("Default dir of ApkProtector" + System.getProperty("user.dir"));

        String cmd = args[0];
        if (!"b".equals(cmd)) {
            LoggerUtils.writeLog("Using: java -jar ApkProtector.jar b ApkPath");
            return;
        }

        String apkPath = args[1];
        LoggerUtils.writeLog("Path to APK: " + apkPath);

        File outputFolder = new File(Constants.OUTPUT_PATH);
        if (!outputFolder.exists()) {
            outputFolder.mkdir();
            LoggerUtils.writeLog("Dir created: " + outputFolder.getAbsolutePath());
        }

        File smaliFolder = new File(Constants.SMALI_PATH);
        if (!smaliFolder.exists()) {
            smaliFolder.mkdir();
            LoggerUtils.writeLog("Dir created: " + smaliFolder.getAbsolutePath());
        }

        File releaseFolder = new File(Constants.RELEASE_PATH);
        if (!releaseFolder.exists()) {
            releaseFolder.mkdir();
            LoggerUtils.writeLog("Dir created: " + releaseFolder.getAbsolutePath());
        }

        long time = System.currentTimeMillis();

        try {
            FastZip.extract(apkPath, Constants.OUTPUT_PATH);
            LoggerUtils.writeLog("Success unpack: " + apkPath);

            ManifestPatcher.parseManifest();

            DexCrypto.encodeDexes();
            LoggerUtils.writeLog("Dex files successful encrypted");

            FastZip.repack(apkPath, Constants.UNSIGNED_PATH);
            LoggerUtils.writeLog("Success compiled: " + Constants.UNSIGNED_PATH);

            if (!SignatureTool.sign(Constants.UNSIGNED_PATH, Constants.SIGNED_PATH)) {
                LoggerUtils.writeLog("APK signing error");
                FileUtils.deleteDir(new File(Constants.OUTPUT_PATH));
                return;
            }
            LoggerUtils.writeLog("APK success signed");
        } catch (Exception e) {
            LoggerUtils.writeLog("Error: " + e);
        }

        FileUtils.delete(new File(Constants.CONFIG_TEMP_PATH));
        FileUtils.deleteDir(new File(Constants.OUTPUT_PATH));
        FileUtils.delete(new File(Constants.UNSIGNED_PATH));
        FileUtils.deleteDir(new File(Constants.CACHE_PATH));
        FileUtils.deleteDir(new File(Constants.SMALI_PATH));

        LoggerUtils.writeLog("Work time: " + (System.currentTimeMillis() - time));
    }

    private static void generateRandom() {
        ConfigTemp config = new ConfigTemp();
        config.packageName = CommonUtils.generateRandomString(Constants.PACKAGE_NAME);
        config.dexFolder = CommonUtils.generateRandomString(Constants.DEX_DIR);
        config.dexPrefix = CommonUtils.generateRandomString(Constants.DEX_PREFIX);
        config.dexSuffix = CommonUtils.generateRandomString(Constants.DEX_SUFFIX);
        config.proxyApp = CommonUtils.generateRandomString(Constants.PROXY_APP);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        FileUtils.writeFile(Constants.CONFIG_TEMP_PATH, gson.toJson(config), false);
    }
}
