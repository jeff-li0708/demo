package algorithm;

/**
 * 给定 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，
 * 垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0)。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 * Created by liangl on 2019/5/5.
 */
public class MaxArea {

    public int maxArea(int[] height) {
        int max = 0,area = 0;
        for (int i = 0,len = height.length; i < len; i++) {
            for (int j = len; j > i && j > 0; j--) {
                area = (j-i-1) * (height[j-1] > height[i] ? height[i] : height[j-1]);
                max = max < area ? area: max;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int[] arr = {1,8,6,2,5,4,8,3,7};
        System.out.println(new MaxArea().maxArea(arr));
    }
}
