package com.hrsys.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FilePathEnum {

    AVATER("avater/", "头像图片文件路径", ".png");

    private final String path;

    private final String desc;

    private final String ext;

}
