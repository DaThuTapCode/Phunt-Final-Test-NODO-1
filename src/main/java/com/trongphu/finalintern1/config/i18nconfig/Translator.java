package com.trongphu.finalintern1.config.i18nconfig;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Created by Trong Phu on 29/08/2024 22:12
 *
 * @author Trong Phu
 */
@Component
public class Translator {
    private static ResourceBundleMessageSource messageSource;

    public Translator(ResourceBundleMessageSource messageSource){
        Translator.messageSource = messageSource;
    }

    public static String toLocale(String msgCode){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(msgCode, null, locale);
    }

    public static String toLocale(String msgCode, Object[] objects){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(msgCode, objects, locale);
    }

}
