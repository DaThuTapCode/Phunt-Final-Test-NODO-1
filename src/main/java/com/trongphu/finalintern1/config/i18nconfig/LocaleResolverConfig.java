package com.trongphu.finalintern1.config.i18nconfig;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.List;
import java.util.Locale;

/**
 * Created by Trong Phu on 29/08/2024 21:51
 *
 * @author Trong Phu
 */
@Configuration
public class LocaleResolverConfig
//        extends AcceptHeaderLocaleResolver
        implements WebMvcConfigurer {
//    private static final Locale DEFAULT_LOCALE = new Locale("en");

//    @Override
//    public Locale resolveLocale(HttpServletRequest request) {
//        String languageHeader = request.getHeader("Accept-Language");
//        if (StringUtils.hasLength(languageHeader)) {
//            try {
//                List<Locale.LanguageRange> languageRanges = Locale.LanguageRange.parse(languageHeader);
//                Locale resolvedLocale = Locale.lookup(languageRanges, List.of(new Locale("vi"), new Locale("en")));
//
//                if (resolvedLocale != null && resolvedLocale.equals(new Locale("vi")) && resolvedLocale.equals(new Locale("en"))) {
//                    return resolvedLocale;
//                } else {
//                    return DEFAULT_LOCALE;
//                }
//            } catch (Exception e) {
//                return DEFAULT_LOCALE;
//            }
//        }
//        return DEFAULT_LOCALE;
//    }

    @Bean
    public ResourceBundleMessageSource bundleMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setCacheSeconds(3600);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
