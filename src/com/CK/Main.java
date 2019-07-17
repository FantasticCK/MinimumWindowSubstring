package com.CK;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
//        String S = "cabwefgewcwaefgcf", T = "cae";
//        String S = "ADOBECODEBANC", T = "ABC";
        String S = "AA", T = "AA";
        Solution solution = new Solution();
        System.out.println(solution.minWindow(S, T));
    }
}

class Solution {
    public String minWindow(String s, String t) {
        if (s.length() < t.length() || s.length() == 0) return "";
        int left = 0, right = 0, leftMax = 0, rightMin = Integer.MAX_VALUE;
        if (!subStringFullyContainsT(s, t, 0, s.length() - 1)) return "";
        for (right = 0; right < s.length(); right++) {
            boolean curr = subStringFullyContainsT(s, t, left, right);
            if (curr) {
                if (((right - left) <= (rightMin - leftMax))) {
                    leftMax = left;
                    rightMin = right;
                }
                while (subStringFullyContainsT(s, t, left, right)) {
                    if ((right - left) <= (rightMin - leftMax)) {
                        leftMax = left;
                        rightMin = right;
                    }
                    left++;
                }

            }
        }


        return s.substring(leftMax, rightMin + 1);
    }

    private boolean subStringFullyContainsT(String s, String t, int bg, int ed) {
        if (s.length() < t.length()) return false;
        StringBuilder sb = new StringBuilder(s.substring(bg, ed + 1));
        StringBuilder tb = new StringBuilder(t);
        while (tb.length() > 0) {
            if (sb.indexOf(String.valueOf(tb.charAt(0))) == -1) return false;
            sb.deleteCharAt(sb.indexOf(String.valueOf(tb.charAt(0))));
            tb.deleteCharAt(0);
        }
        return true;
    }
}

//Two Pointers HashMap
class Solution2 {
    public String minWindow(String s, String t) {
        if (s == null || s.length() < t.length() || s.length() == 0) {
            return "";
        }
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        for (char c : t.toCharArray()) {
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }
        int left = 0;
        int minLeft = 0;
        int minLen = s.length() + 1;
        int count = 0;
        for (int right = 0; right < s.length(); right++) {
            if (map.containsKey(s.charAt(right))) {
                map.put(s.charAt(right), map.get(s.charAt(right)) - 1);
                if (map.get(s.charAt(right)) >= 0) {
                    count++;
                }
                while (count == t.length()) {
                    if (right - left + 1 < minLen) {
                        minLeft = left;
                        minLen = right - left + 1;
                    }
                    if (map.containsKey(s.charAt(left))) {
                        map.put(s.charAt(left), map.get(s.charAt(left)) + 1);
                        if (map.get(s.charAt(left)) > 0) {
                            count--;
                        }
                    }
                    left++;
                }
            }
        }
        if (minLen > s.length()) {
            return "";
        }

        return s.substring(minLeft, minLeft + minLen);
    }
}