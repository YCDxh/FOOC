package com.YCDxh.utils;

import java.util.BitSet;

public class MyBloomFilter {
    private final BitSet bitSet; // 位数组
    private final int[] seeds; // 哈希函数种子（控制哈希多样性）
    private final int bitSize; // 位数组长度

    // 构造函数：初始化参数（示例参数：m=1<<20，k=3）
    public MyBloomFilter() {
        this.bitSize = 1 << 20; // 1MB（约10^6位）
        this.seeds = new int[]{31, 37, 41}; // 3个哈希函数种子
        this.bitSet = new BitSet(bitSize);
    }

    // 插入元素
    public void add(String value) {
        for (int seed : seeds) {
            int index = hash(value, seed);
            bitSet.set(index);
        }
    }

    // 判断元素是否可能存在于集合中
    public boolean mightContain(String value) {
        for (int seed : seeds) {
            int index = hash(value, seed);
            if (!bitSet.get(index)) {
                return false; // 若某位为0，则一定不存在
            }
        }
        return true; // 所有位为1，可能存在（但可能误判）
    }

    // 简单哈希函数（使用种子和字符串hashCode）
    private int hash(String value, int seed) {
        int hashCode = value.hashCode();
        // 位运算优化：当 bitSize 是 2 的幂时，模运算可用位与替代
        return (hashCode * seed) & (this.bitSize - 1);
    }
}
