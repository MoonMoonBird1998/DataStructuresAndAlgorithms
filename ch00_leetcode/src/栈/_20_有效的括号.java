package 栈;

import java.util.Stack;

/**
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 *
 * 有效字符串需满足：
 *
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 注意空字符串可被认为是有效字符串。
 *
 * 示例 1:
 *
 * 输入: "()"
 * 输出: true
 * 示例 2:
 *
 * 输入: "()[]{}"
 * 输出: true
 * 链接：https://leetcode-cn.com/problems/valid-parentheses
 */
public class _20_有效的括号 {

    public static void main(String[] args) {
        System.out.println(isValid("{{[]}}"));
    }

    public static boolean isValid(String s) {
        Stack<Character> left = new Stack<>();
        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if ('('== c || '['== c || '{'== c){
                left.push(c);
            } else {
                if (left.isEmpty()) return false;
                if (')' == c){
                    if (!(left.pop() == '(')) return false;
                }
                if ('}' == c){
                    if (!(left.pop() == '{')) return false;
                }
                if (']' == c){
                    if (!(left.pop() == '[')) return false;
                }
            }
        }
        return left.isEmpty();
    }
}
