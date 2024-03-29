package facebookprepare;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by yangw on 2019/6/29.
 * 对字符串进行从左到右的遍历，如果出现左括号“（”，记录下来，如果右括号在左括号之前出现，
 * 加入左括号的count中，并且把右括号的个数-1,最后return 左右括号的个数。
 对字符串进行又一次的遍历，如果字符串既不是“（”也不是“）”则跳过，否则选择要还是不要。
 */
public class removeInvalidParentheses {

    char[][] patterns = { {'(', ')'}, {')', '('} };

    public List<String> removeInvalidParentheses(String s) {
        List<String> ret = new ArrayList<>();
        dfs(s, 0, 0, patterns[0], ret);
        return ret;
    }

    private void dfs(String s, int start, int lastRemove, char[] pattern, List<String> ret) {
        int count = 0, n = s.length();
        for (int i = start; i < n; i ++) {
            if (s.charAt(i) == pattern[0])            {
                count ++;
            }
            if (s.charAt(i) == pattern[1])            {
                count --;
            }
            if (count < 0)            {
                for (int j = lastRemove; j <= i; j ++)
                {
                    // when lastRemove is a more ( or ) and we remove duplicated.
                    if (s.charAt(j) == pattern[1] && (j == lastRemove || s.charAt(j) != s.charAt(j - 1)))
                    {
                        dfs(s.substring(0, j) + s.substring(j + 1), i, j, pattern, ret);
                    }
                }
                return;
            }
        }
        s = new StringBuilder(s).reverse().toString();
        if (pattern[0] == patterns[0][0])        {
            dfs(s, 0, 0, patterns[1], ret);
        }
        else        {
            ret.add(s);
        }
    }

    // Facebook version . 一般就是返回一个valid String
    // 扫两次的方法， 第一次检查 左边和右边match的情况，如果（有多 删除掉
    // 第二次从右边开始往左边扫。 如果） 有多 删除掉右侧括号
    public String removeInvalidParenthesesFacebookVersion(String s) {
        if ( s == null || s.length() == 0) return s;
        int l = 0 ;
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0 ; i < sb.length() ; i++){
            if (sb.charAt(i) == '(' ){
                l++;
            }else if(s.charAt(i) == ')'){
                if (l == 0){
                    sb.deleteCharAt(i);
                }else {
                    l--;
                }
            }else {
                continue;
            }
        }

        int r = 0;
        for ( int i = sb.length() - 1 ; i >= 0 ; i--){
            if (sb.charAt(i) == ')'){
                r++;
            }else if(sb.charAt(i) == '('){
                if (r == 0){
                    sb.deleteCharAt(i);
                }else{
                    r--;
                }
            }else {
                continue;
            }
        }
        return sb.toString();

    }

    // one loop

    public static String removeInvalidOnePass(String input) {
        Stack<Integer> stack = new Stack<Integer>();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '(') {
                stack.push(i);
            } else if (c == ')') {
                if (stack.isEmpty() || input.charAt(stack.peek()) != '(') {
                    stack.push(i);
                } else {
                    stack.pop();
                }
            }
        }

        StringBuilder sb = new StringBuilder(input);
        while (!stack.isEmpty()) {
            sb.deleteCharAt(stack.pop());
        }
        return sb.toString();
    }
}
