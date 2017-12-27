package indi.liudalei.eidea.core.i18n;

import indi.liudalei.eidea.core.entity.bo.MsgBo;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 刘大磊 on 2017/1/6 15:10.
 * 国际化才用多例模式
 */
public class DbResourceBundle {
    private static Map<String, DbResourceBundle> _CACHE_RESOURCE_BUNDLE = new HashMap<>();
    private String langCode;
    private Map<String, MsgBo> labelResourceMap;
    private Map<String, MsgBo> messageResourceMap;

    public DbResourceBundle(String langCode, Map<String, MsgBo> labelResourceMap, Map<String, MsgBo> messageResourceMap) {
        this.langCode = langCode;
        this.labelResourceMap = labelResourceMap;
        this.messageResourceMap = messageResourceMap;
        _CACHE_RESOURCE_BUNDLE.put(langCode, this);
    }

    /**
     * 根据语言，查看国际化标签是否已经有值，如果有，则直接从缓存中取值
     *
     * @param langCode
     * @return
     */
    public static DbResourceBundle getDbResourceBundle(String langCode) {
        return _CACHE_RESOURCE_BUNDLE.get(langCode);
    }

    public String getLabel(String key, Object... value) {
        String label = getLabel(key);
        if (label == null) {
            return null;
        }
        return MessageFormat.format(label, value);
    }

    public String getLabel(String key) {
        MsgBo labelValueBo = labelResourceMap.get(key);
        if (labelValueBo == null) {
            return null;
        }
        if (labelValueBo.getValue() == null) {
            return labelValueBo.getDefaultValue();
        }
        return labelValueBo.getValue();
    }

    public String getMessage(String key, Object... value) {
        String label = getMessage(key);
        if (label == null) {
            return null;
        }
        return MessageFormat.format(label, value);
    }

    public String getMessage(String key) {
        MsgBo messageValueBo = messageResourceMap.get(key);
        if (messageValueBo == null) {
            return null;
        }
        if (messageValueBo.getValue() == null) {
            return messageValueBo.getDefaultValue();
        }
        return messageValueBo.getValue();
    }

    public static void removeMessage(String key) {
        _CACHE_RESOURCE_BUNDLE.values().forEach(e -> {
            e.messageResourceMap.remove(key);
        });
    }

    public static void updateMessage(String key, String value, String defaultValue, String lang) {
        DbResourceBundle _cachedResource = _CACHE_RESOURCE_BUNDLE.get(lang);
        if (_cachedResource != null) {
            MsgBo msgBo = _cachedResource.messageResourceMap.get(key);
            if (msgBo == null) {
                msgBo = new MsgBo();
                msgBo.setDefaultValue(defaultValue);
                msgBo.setValue(value);
                _cachedResource.messageResourceMap.put(key, msgBo);
            } else {
                msgBo.setValue(value);
                msgBo.setDefaultValue(defaultValue);
            }
        }
    }

    public static void removeLabel(String key) {
        _CACHE_RESOURCE_BUNDLE.values().forEach(e -> {
            e.labelResourceMap.remove(key);
        });
    }

    public static void updateLabel(String key, String value, String defaultValue, String lang) {
        DbResourceBundle _cachedResource = _CACHE_RESOURCE_BUNDLE.get(lang);
        if (_cachedResource != null) {
            MsgBo msgBo = _cachedResource.labelResourceMap.get(key);
            if (msgBo == null) {
                msgBo = new MsgBo();
                msgBo.setDefaultValue(defaultValue);
                msgBo.setValue(value);
                _cachedResource.labelResourceMap.put(key, msgBo);
            } else {
                msgBo.setValue(value);
                msgBo.setDefaultValue(defaultValue);
            }

        }
    }
}
