package br.gov.servidor.core.utils;

import org.apache.commons.lang3.StringUtils;

public class Func {

    public static String formatarQueryContem(String qry) {
        if (StringUtils.isEmpty(qry))
            return qry;

        qry = StringUtils.stripAccents(qry);
        return "%".concat(qry.toUpperCase().concat("%"));
    }
}
