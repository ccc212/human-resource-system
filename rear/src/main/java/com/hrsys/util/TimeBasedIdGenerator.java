package com.hrsys.util;

public class TimeBasedIdGenerator {
    private static long lastTimestamp = -1L;
    private static int sequence = 0;

    public static synchronized long generateId() {
        long timestamp = System.currentTimeMillis();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id");
        }

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & 0xFFFFF; // 保证序列号不超过20位
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;

        // 组合成64位的ID
        return ((timestamp - 1480166465631L) << 20) | (sequence);
    }

    private static long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
        }
    }
}
