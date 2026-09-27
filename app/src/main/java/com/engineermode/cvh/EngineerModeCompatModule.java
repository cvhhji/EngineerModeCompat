package com.engineermode.cvh;

import android.util.Log;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import io.github.libxposed.api.XposedInterface;
import io.github.libxposed.api.XposedModule;
import io.github.libxposed.api.XposedModuleInterface.ModuleLoadedParam;
import io.github.libxposed.api.XposedModuleInterface.PackageReadyParam;

public final class EngineerModeCompatModule extends XposedModule {
    private static final String TAG = "EngineerModeCompat";
    private static final String TARGET_PACKAGE = "com.oplus.engineermode";
    private static final String BASE_FRAGMENT =
            "com.oplus.engineermode.entrance.EngineerFragmentCompat";
    private static final Set<String> RESTORED_KEYS = Set.of(
            "write_key_online",
            "deivce_calibration_status",
            "write_log_test",
            "clean_f_status",
            "nfc_clear_se",
            "sensoroffset_preference");
    private static final Set<String> LOGGED_KEYS = ConcurrentHashMap.newKeySet();

    @Override
    public void onModuleLoaded(ModuleLoadedParam param) {
        log(Log.INFO, TAG, "Module loaded in " + param.getProcessName());
    }

    @Override
    public void onPackageReady(PackageReadyParam param) {
        if (!param.isFirstPackage() || !TARGET_PACKAGE.equals(param.getPackageName())) {
            return;
        }

        try {
            Class<?> type = Class.forName(BASE_FRAGMENT, false, param.getClassLoader());
            Method method = type.getDeclaredMethod("removeUnnecessaryPreference", String.class);
            method.setAccessible(true);
            hook(method)
                    .setExceptionMode(XposedInterface.ExceptionMode.PROTECTIVE)
                    .intercept(chain -> {
                        Object value = chain.getArg(0);
                        if (value instanceof String && RESTORED_KEYS.contains(value)) {
                            String key = (String) value;
                            if (LOGGED_KEYS.add(key)) {
                                log(Log.INFO, TAG, "Kept engineering-mode preference: " + key);
                            }
                            return null;
                        }
                        return chain.proceed();
                    });
            log(Log.INFO, TAG, "Preference filter hook installed for " + TARGET_PACKAGE);
        } catch (Throwable error) {
            log(Log.ERROR, TAG, "Could not install preference filter hook", error);
        }
    }
}
