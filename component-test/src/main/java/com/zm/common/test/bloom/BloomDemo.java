package com.zm.common.test.bloom;

import java.nio.charset.Charset;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;
import com.google.common.hash.PrimitiveSink;

public class BloomDemo {

	// 预计元素个数
	private int size = 1024 * 1024;

	private BloomFilter<String> bloom = BloomFilter.create(new Funnel<String>() {
		@Override
		public void funnel(String from, PrimitiveSink into) {
			// 自定义过滤条件 此处不做任何过滤
			into.putString((CharSequence) from, Charset.forName("UTF-8"));
		}
	}, size);

	// private BloomFilter<String> bloom =
	// BloomFilter.create(Funnels.stringFunnel(Charset.forName("UTF-8")), size);

	// private BloomFilter<String> bloom =
	// BloomFilter.create(Funnels.stringFunnel(Charset.forName("UTF-8")), size,
	// 0.3);

	public void fun() {
		// 往过滤器中添加元素
		bloom.put("布隆过滤器");
		bloom.put("布隆过滤器1");
		bloom.put("布隆过滤器2");
		// 查询
		boolean mightContain = bloom.mightContain("布隆过滤器");
		System.out.println(mightContain);
	}

	public static void main(String[] args) {
		BloomDemo blBoolmTest = new BloomDemo();
		blBoolmTest.fun();
	}

}
