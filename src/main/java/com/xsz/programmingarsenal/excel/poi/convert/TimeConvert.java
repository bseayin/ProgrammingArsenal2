package com.xsz.programmingarsenal.excel.poi.convert;

import com.xsz.programmingarsenal.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeConvert implements ExportConvert {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public String handler(Object val) {
        try {
            if (val == null)
                return "";
            else {
                return DateUtil.formatCSTTime(val.toString(), "yyyy-MM-dd HH:mm:ss");
            }
        } catch (Exception e) {
            log.error("时间转换异常", e);
            return "";
        }
    }

}
