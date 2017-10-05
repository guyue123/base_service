package com.base.common.string;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author: <a href="luzaoyang@base.com.cn">Lu Zaoyang</a>
 * @version: 1.0, 2011-4-29
 * 
 */
public class StringUtil {

    private static Logger log = Logger.getLogger(StringUtil.class);

    private StringUtil() {
    }

    /**
     * 把字符串数组变成用逗号连起来的字符串
     * 
     * @param values
     * @return
     */
    public static final String toString(String[] values) {
        if (values == null || values.length == 0) {
            return "";
        }

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < values.length - 1; i++) {
            buffer.append(values[i]);
            buffer.append(",");
        }
        buffer.append(values[values.length - 1]);

        return buffer.toString();
    }

    public static final String[] split(String string, String delimiter) {
        int count = 0;
        for (int index = 0; index < string.length() && index != -1; count++) {
            index = string.indexOf(delimiter, index);
            if (index == -1)
                continue;
            index += delimiter.length();
        }

        String[] result = new String[count];
        for (int i = 0, begin = 0, end = 0; i < count; i++) {
            end = string.indexOf(delimiter, begin);
            if (end == -1)
                end = string.length();
            result[i] = new String(string.substring(begin, end));
            begin = end + delimiter.length();
        }

        return result;
    }

    public static final String replace(String source, String find, String replaceString) {
        final int findLen = find.length();
        final int replaceLen = replaceString.length();

        String text = source;

        int pos;
        String leftString;
        String rightString;
        pos = text.indexOf(find);

        while (pos != -1) {
            leftString = text.substring(0, pos);
            rightString = text.substring(pos + findLen);
            text = leftString + replaceString + rightString;
            pos = text.indexOf(find, pos + replaceLen);
        }
        return text;
    }

    /**
     * 全拆分 12345 = 1 2 3 4 5 12 23 34 45 123 234 345 1234 2345 12345
     * 
     * @param text
     * @return
     */
    public static final String splitTextFull(String text) {
        HashMap<String, String> map = new HashMap<String, String>();

        for (int i = 1; i < text.length() + 1; i++) {
            String key = text.substring(0, i);
            if (!map.containsKey(key)) {
                map.put(key, key);
            }

            _splitText0(map, text.substring(i - 1, text.length()));
        }

        String[] values = (String[]) map.values().toArray(new String[0]);
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            buf.append(values[i]);
            buf.append(" ");
        }
        return buf.toString();

    }

    private static final void _splitText0(HashMap<String, String> buf, String text) {
        for (int i = 1; i < text.length() + 1; i++) {
            String key = text.substring(0, i);
            if (!buf.containsKey(key)) {
                buf.put(key, key);
            }
        }
    }

    /**
     * 简单拆分 12345 = 1 12 123 1234 12345
     * 
     * @param text
     * @return
     */
    public static final String splitTextForward(String text) {
        StringBuilder buf = new StringBuilder();
        buf.append(text);
        for (int i = 0; i < text.length(); i++) {
            buf.append(text.substring(0, i));
            buf.append(" ");
        }
        return buf.toString();
    }

    /**
     * 简单拆分 12345 = 12345 2345 345 45 5
     * 
     * @param text
     * @return
     */
    public static final String splitTextBackward(String text) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            buf.append(text.substring(i));
            buf.append(" ");
        }
        return buf.toString();
    }

    public static final String splitTextSimple(String text) {
        int matchMaxLength = 10;
        if (text.length() > matchMaxLength) {
            String tmp = text.substring(text.length() - matchMaxLength);
            return text + " " + splitTextBackward(tmp);
        } else {
            return splitTextBackward(text);
        }
    }

    /**
     * 是否是字母或者数字的字符串
     * 
     * @param text
     * @return
     */
    public static final boolean isLetterOrNumber(String text) {
        if (text == null) {
            return false;
        }

        text = text.trim();

        if ("".equals(text)) { // 空字符串
            return false;
        }

        if (text.length() == 1) { // 状态字段
            return false;
        }

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9')) {

            } else {
                return false;
            }
        }

        return true;
    }

    /**
     * 字符串是否为空
     * 
     * @param target
     * @return
     */
    public static boolean isEmptyOrNull(String target) {
        if (target == null || target.trim().isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 替换为制定字符串
     * 
     * @param orgin
     * @param subStr
     * @return
     */
    public static String replaceSubString(String origin, String subStr, String replace) {
        if (origin == null) {
            return null;
        }

        return origin.replaceAll(subStr, replace);
    }

    /**
     * 过滤掉html标签
     * 
     * @param htmlStr
     * @return
     */
    public static String getText(String htmlStr) {
        return getText(htmlStr, true);
    }

    /**
     * 过滤掉html标签
     * 
     * @param htmlStr
     * @return
     */
    public static String getText(String htmlStr, boolean filterHtml) {
        if (htmlStr == null || "".equals(htmlStr)) {
            return "";
        }

        String textStr = "";
        Pattern pattern;
        Matcher matcher;

        try {
            String regEx_remark = "<!--.+?-->";
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
                                                                                                  // //
                                                                                                  // }
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            String regEx_html1 = "<[^>]+";
            String regEx_cdata_start = "<!\\[CDATA\\["; // CDATA
            String regEx_cdata_end = "\\]\\]>"; // CDATA
            htmlStr = htmlStr.replaceAll("\n", "");
            htmlStr = htmlStr.replaceAll("\t", "");
            pattern = Pattern.compile(regEx_remark);// 过滤注释标签
            matcher = pattern.matcher(htmlStr);
            htmlStr = matcher.replaceAll("");

            pattern = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(htmlStr);
            htmlStr = matcher.replaceAll(""); // 过滤script标签

            pattern = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(htmlStr);
            htmlStr = matcher.replaceAll(""); // 过滤style标签

            pattern = Pattern.compile(regEx_cdata_start, Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(htmlStr);
            htmlStr = matcher.replaceAll(""); // 过滤cdata标签

            pattern = Pattern.compile(regEx_cdata_end, Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(htmlStr);
            htmlStr = matcher.replaceAll(""); // 过滤cdata标签

            if (filterHtml) {
                pattern = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
                matcher = pattern.matcher(htmlStr);
                htmlStr = matcher.replaceAll(""); // 过滤html标签

                pattern = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
                matcher = pattern.matcher(htmlStr);
                htmlStr = matcher.replaceAll(""); // 过滤html标签
            }

            textStr = htmlStr.trim();

        } catch (Exception e) {
            log.info("获取HTML中的text出错:");
            return htmlStr;
        }

        return textStr;
    }

    /**
     * 统计字符串中某个字符的数量
     * 
     * @param src
     * @param subStr
     * @return
     */
    public static int getCharCountOfString(String src, char cf) {
        if (isEmptyOrNull(src)) {
            return 0;
        }

        int count = 0;
        char[] chars = src.toCharArray();
        for (char c : chars) {
            if (c == cf) {
                count++;
            }
        }

        return count;
    }

    /**
     * 判断字符是否英文半角字符或标点 32 空格 33-47 标点 48-57 0~9 58-64 标点 65-90 A~Z 91-96 标点
     * 97-122 a~z 123-126 标点
     * 
     * @param c
     * @return
     */
    public static boolean isHalfWidth(char c) {
        int i = (int) c;
        return i >= 32 && i <= 126;
    }

    /**
     * 判断字符是否全角字符或标点 <para>全角字符 - 65248 = 半角字符</para> <para>全角空格例外</para>
     * 
     * @param c
     * @return
     */
    public static boolean isFullWidth(char c) {
        if (c == '\u3000')
            return true;

        int i = (int) c - 65248;
        if (i < 32)
            return false;
        return isHalfWidth((char) i);
    }

    /**
     * 将字符串中的全角字符转换为半角
     * 
     * @param s
     * @return
     */
    public static String convert2HalfWidth(String s) {
        if (s == null || s.trim().isEmpty())
            return s;

        StringBuilder sb = new StringBuilder(s.length());
        char[] chars = s.toCharArray();
        for (char achar : chars) {
            if (achar == '\u3000')
                sb.append('\u0020');
            else if (isFullWidth(achar))
                sb.append((char) ((int) achar - 65248));
            else
                sb.append(achar);
        }

        return sb.toString();
    }

    /**
     * MD5加密数据
     * 
     * @param input
     * @return
     */
    public static String md5(String plainText) {
        if (plainText == null) {
            plainText = "";
        }

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }

                buf.append(Integer.toHexString(i));
            }

            return buf.toString();
        } catch (Exception e) {
            log.warn(e);
        }
        return null;
    }

    /**
     * 将字符串转码
     * 
     * @param str
     *            字符串
     * @param oldEncoding
     *            原编码
     * @param newEncoding
     *            新的编码
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String changeEncoding(String str, String oldEncoding, String newEncoding) {
        if (isEmptyOrNull(str)) {
            return str;
        }

        if (isEmptyOrNull(oldEncoding) || isEmptyOrNull(newEncoding) || oldEncoding.equalsIgnoreCase(newEncoding)) {
            return str;
        }

        try {
            str = new String(str.getBytes(newEncoding), newEncoding);
        } catch (UnsupportedEncodingException e) {
            log.error("StringUtils->changeEncoding:字符串" + str + "无法转码!", e);
            return str;
        }

        return str;
    }

    /**
     * 判断字符串是否是身份证
     * 
     * @param str
     * @return
     */
    public static boolean isIDCard(String id) {
        if (isEmptyOrNull(id) || (id.trim().length() != 15 && id.trim().length() != 18)) {
            return false;
        }

        if (id.endsWith("X") || id.endsWith("x")) {
            id = id.replace("X", "0");
            id = id.replace("x", "0");
        }

        /*
         * 11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海"
         * ,32:"江苏",33:"浙江",34:"
         * 安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"
         * 湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州"
         * ,53:"云南",54:"西藏",61
         * :"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"
         */
        List<String> provinces = Arrays.asList("11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34",
                "35", "36", "37", "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63",
                "64", "65", "71", "81", "82", "91");
        String province = id.substring(0, 2);
        // 身份证前两位标识省份信息
        if (!provinces.contains(province)) {
            return false;
        }

        // 15位身份证
        id = id.trim();
        String idCard15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
        if (id.length() == 15) {
            return Pattern.matches(idCard15, id);
        }

        // 18位身份证
        String idCard18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$";
        return Pattern.matches(idCard18, id);
    }

    /**
     * 判断字符串是否是手机号码
     * 
     * @param no
     * @return
     */
    public static boolean isMobileNo(String no) {
        if (isEmptyOrNull(no)) {
            return false;
        }

        if (no.startsWith("+86")) {
            no = no.replaceFirst("\\+86", "");
        }

        String reg = "^1[3-8]\\d{9}$";
        return Pattern.matches(reg, no);
    }

    /**
     * 转义正则表达式中的特殊字符
     * 
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static String convertRegExSpecialStr(String str) {
        // 清除掉所有特殊字符
        String regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、?]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 判断字符串是否是数字和字母组合
     * 
     * @param str
     *            字符串
     * @return
     */
    public static boolean isAlphaNum(String str) {
        String regEx = "[a-zA-Z0-9]+";

        return Pattern.matches(regEx, str);
    }

    /**
     * 从开始位置获得指定长度的字符串
     * 
     * @param target
     *            原字符串
     * @param length
     *            长度
     * @return 子字符串
     */
    public static String getSubString(String target, int length) {
        if (isEmptyOrNull(target)) {
            return null;
        }

        if (target.length() > length) {
            return target.substring(0, length);
        }

        return target;
    }

    /**
     * 如果目标值为空或null，则使用默认值
     * 
     * @param target
     *            原字符
     * @param def
     *            默认值
     * @return 字符
     */
    public static String getString(String target, String def) {
        if (isEmptyOrNull(target)) {
            return def;
        }

        return target;
    }

    /**
     * 填充字符串到指定长度
     * 
     * @param target
     *            字符串
     * @param len
     *            长度
     * @param c
     *            填充内容
     * @param pos
     *            填充位置 true:前面 false:后面
     * @return
     */
    public static String getFullLengthString(String target, final int len, final char c, final boolean pos) {
        int tLen = 0;
        if (target != null) {
            tLen = target.length();

            if (tLen > len) {
                return target;
            }
        } else {
            target = "";
        }

        String tmp = "";
        for (int i = 0; i < len - tLen; i++) {
            tmp += c;
        }

        if (pos) {
            return tmp + target;
        }

        return target + tmp;
    }

    /**
     * 将字符串按照指定格式转成Map
     * 
     * @param str
     *            字符串
     * @param splitGroup
     *            一级分隔符
     * @param split
     *            二级分隔符
     * @return Map
     */
    public static Map<String, String> String2Map(String str, String splitGroup, String split) {
        Map<String, String> map = new HashMap<String, String>();
        if (!StringUtil.isEmptyOrNull(str)) {
            String[] files = str.split(splitGroup);

            for (String file : files) {
                String[] kv = file.split(split);

                if (kv.length == 2) {
                    map.put(kv[0].trim(), kv[1].trim());
                }
            }
        }

        return map;
    }
}
