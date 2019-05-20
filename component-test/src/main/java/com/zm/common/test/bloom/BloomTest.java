package com.zm.common.test.bloom;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

public class BloomTest {
	public static void main(String[] args) {
		try {
			int size = 1000000;
			BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size, 0.0001);

			// System.out.println("二进制向量长度："+bloomFilter.hashCode);

			Instant now0 = Instant.now();
			for (int i = 0; i < size; i++) {
				bloomFilter.put(i);
			}
			Instant now1 = Instant.now();
			System.out.println("录入" + size + "条数据共耗时:" + (now1.toEpochMilli() - now0.toEpochMilli()));

			Instant now2 = Instant.now();
			for (int i = 0; i < size; i++) {
				if (!bloomFilter.mightContain(i)) {
					System.out.println("有漏掉的");
				}
			}
			Instant now3 = Instant.now();
			System.out.println("判断" + size + "条在过滤器中的数据共耗时:" + (now3.toEpochMilli() - now2.toEpochMilli()));

			Instant now4 = Instant.now();
			List<Integer> list = new ArrayList<Integer>(1000);
			for (int i = size + size / 10; i < size + (size * 2) / 10; i++) {
				if (bloomFilter.mightContain(i)) {
					list.add(i);
				}
			}
			Instant now5 = Instant.now();
			System.out.println("判断" + size / 10 + "条不在过滤器中的数据共耗时:" + (now5.toEpochMilli() - now4.toEpochMilli()));

			System.out.println("有误判的数量：" + list.size());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
