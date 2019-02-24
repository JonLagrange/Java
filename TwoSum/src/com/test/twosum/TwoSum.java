package com.test.twosum;
import java.util.HashMap;
import java.util.Map;

public class TwoSum {
	public int[] twoSum(int[] nums, int target) {
		Map<Integer, Integer> map = new HashMap<>();
		int[] result = new int[2];
		for(int i = 0; i < nums.length; i++) {
			int elem = target - nums[i];
			if(map.containsKey(elem))
			{
				result[0] = map.get(elem);
				result[1] = i;
			}
			map.put(nums[i], i);
		}
		return result;
	}
}
