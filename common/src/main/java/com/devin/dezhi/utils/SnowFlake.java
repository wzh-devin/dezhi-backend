package com.devin.dezhi.utils;

/**
 * 2025/4/25 22:28.
 *
 * <p>
 *     雪花算法的id生成工具
 * </p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
public class SnowFlake {
    /**
     * 起始的时间戳.
     */
    private static final long START_STAMP = 1480166465631L;

    /**
     * 序列号占用的位数.
     */
    private static final long SEQUENCE_BIT = 12;

    /**
     * 机器标识占用的位数.
     */
    private static final long MACHINE_BIT = 5;

    /**
     * 数据中心占用的位数.
     */
    private static final long DATA_CENTER_BIT = 5;

    /**
     * 每一部分的最大值.
     */
    private static final long MAX_DATA_CENTER_NUM = ~(-1L << DATA_CENTER_BIT);

    private static final long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);

    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移.
     */
    private static final long MACHINE_LEFT = SEQUENCE_BIT;

    private static final long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;

    private static final long TIMESTAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;

    //数据中心
    private final long dataCenterId;

    //机器标识
    private final long machineId;

    //序列号
    private long sequence;

    //上一次时间戳
    private long lastStamp = -1L;

    public SnowFlake(final long dataCenterId, final long machineId) {
        if (dataCenterId > MAX_DATA_CENTER_NUM || dataCenterId < 0) {
            throw new IllegalArgumentException("dataCenterId can't be greater than MAX_DATA_CENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个ID.
     *
     * @return long
     */
    public synchronized long nextId() {
        long currStamp = getNewStamp();
        if (currStamp < lastStamp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStamp == lastStamp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStamp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStamp = currStamp;

        //时间戳部分
        return (currStamp - START_STAMP) << TIMESTAMP_LEFT
                //数据中心部分
                | dataCenterId << DATA_CENTER_LEFT
                //机器标识部分
                | machineId << MACHINE_LEFT
                //序列号部分
                | sequence;
    }

    private long getNextMill() {
        long mill = getNewStamp();
        while (mill <= lastStamp) {
            mill = getNewStamp();
        }
        return mill;
    }

    private long getNewStamp() {
        return System.currentTimeMillis();
    }
}
