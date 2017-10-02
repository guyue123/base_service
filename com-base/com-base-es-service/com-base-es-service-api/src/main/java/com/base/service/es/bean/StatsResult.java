/**
 * 
 */
package com.base.service.es.bean;

import java.io.Serializable;

/**
 * @author base
 *
 */
public class StatsResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 总数
	 */
	private long count;
	
	/**
	 * 平均数
	 */
	private double avg;
	
	/**
	 * 最大数
	 */
	private double max;
	
	/**
	 * 最小数
	 */
	private double min;
	
	/**
	 * 和
	 */
	private double sum;

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	@Override
	public String toString() {
		return "StatsResult [count=" + count + ", avg=" + avg + ", max=" + max + ", min=" + min + ", sum=" + sum + "]";
	}
	

}
