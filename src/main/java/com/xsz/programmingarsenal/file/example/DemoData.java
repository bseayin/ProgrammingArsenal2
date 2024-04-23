package com.xsz.programmingarsenal.file.example;

import com.xsz.programmingarsenal.annotation.ExportConfig;
import lombok.Data;

@Data
public class DemoData {
    @ExportConfig(value = "username")
    String username;
    @ExportConfig(value = "password")
    Long password;
    @ExportConfig(value = "isvalid")
    Boolean isvalid;
}
