package com.ck.spring.filter;

import java.util.HashSet;
import java.util.Set;

import com.alibaba.fastjson.serializer.PropertyFilter;

/**
 * Description: 日志打印过滤
 *
 * @author: xieweili
 * @since: 2017年1月18日	
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public class JsonPropertyFilter implements PropertyFilter {

    private final Set<String> excludes = new HashSet<String>();

    public JsonPropertyFilter(String... properties) {
        for (String item : properties) {
            if (item != null) {
                this.excludes.add(item);
            }
        }
    }

    public Set<String> getExcludes() {
        return excludes;
    }

    @Override
    public boolean apply(Object object, String name, Object value) {

        if (this.excludes.contains(name)) {
            return false;
        }
        return true;
    }
}
